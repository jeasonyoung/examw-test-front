package com.examw.test.front.interceptor;

import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.core.NamedThreadLocal;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.examw.aware.IUserAware;
import com.examw.test.front.model.user.User;
import com.examw.test.front.support.TaoBaoMD5;
/**
 * 用户认证拦截器。
 * @author yangyong.
 * @since 2014-05-15.
 */
public class UserAuthenticationInterceptor extends HandlerInterceptorAdapter {
	private static Logger logger = Logger.getLogger(UserAuthenticationInterceptor.class);
	private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<>("StopWatch-StartTime");
	private String loginUrl;
	private List<String> safeUrl;
	private String remoteRedirectUrl;
	private String md5Str;
	/**
	 * 设置 登录地址
	 * @param loginUrl
	 * 
	 */
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}
	
	/**
	 * 设置 需要跳过检查的地址
	 * @param safeUrl
	 * 
	 */
	public void setSafeUrl(List<String> safeUrl) {
		this.safeUrl = safeUrl;
	}
	
	/**
	 * 设置 远程跳转地址
	 * @param remoteRedirectUrl
	 * 
	 */
	public void setRemoteRedirectUrl(String remoteRedirectUrl) {
		this.remoteRedirectUrl = remoteRedirectUrl;
	}
	
	/**
	 * 获取 md5加密字符串
	 * @return md5Str
	 * 
	 */
	public String getMd5Str() {
		return md5Str;
	}

	/**
	 * 设置 
	 * @param md5Str
	 * 
	 */
	public void setMd5Str(String md5Str) {
		this.md5Str = md5Str;
	}

	/*
	 * 在业务处理之前被调用。
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("开始前台业务处理..."+request.getServletPath());
			this.startTimeThreadLocal.set(System.currentTimeMillis());//线程绑定开始时间(该数据只有当前请求的线程可见)。
		}
		 //1、请求到登录页面 放行  
	    if(request.getServletPath().startsWith(loginUrl)) {  
	        return true;  
	    }  
	          
	    //2、TODO 比如退出、首页等页面无需登录，即此处要放行 允许游客的请求  静态资源
	    if(safeUrl!=null && safeUrl.size()>0){
	    	for(String url:safeUrl)
	    	{
	    		if(!StringUtils.isEmpty(url)){
	    			if(url.contains("*") && request.getServletPath().startsWith(url.replaceAll("[*]", "")))
	    		    {
	    		    	return true;
	    		    }else if(!url.contains("*") && request.getServletPath().equalsIgnoreCase(url)){
	    		    	return true;
	    		    }
	    		}
	    	}
	    }
	    //3、如果用户已经登录 放行    
	    Cookie[] cookies = request.getCookies();
	    String users = null;
	    if(cookies!=null){
	    	for(Cookie c:cookies){
	    		if("Examwww".equals(c.getName())){
	    			users = c.getValue();
	    			if(logger.isDebugEnabled())
	    				logger.debug(users);
	    		}
	    	}
	    }
	    if(!StringUtils.isEmpty(users)) {  
	        //更好的实现方式的使用cookie  
	    	String[] arr = users.split("#");
	    	//String md5key = "4q3i07f12u5i8R1nU";
	    	String userStr = URLDecoder.decode(arr[0], "utf-8");
	    	try{
	    	if(arr[1].equals(TaoBaoMD5.sign(userStr, md5Str, "gbk"))){
	    		//表示已经登陆
	    		User user = this.createUser(userStr);
	    		if(user!=null){
	    			request.getSession().setAttribute("USER", user);
	    			if(handler instanceof HandlerMethod){
	    				HandlerMethod hm = (HandlerMethod)handler;
	    				if(hm != null && (hm.getBean() instanceof IUserAware)){
	    					IUserAware userAware = (IUserAware)hm.getBean();
	    					if(logger.isDebugEnabled())logger.debug("准备注入用户信息...");
	    					userAware.setUserId(user.getId());
	    					userAware.setUserName(user.getName());
	    					if(logger.isDebugEnabled())logger.debug(String.format("注入[%1$s]用户信息:id=%2$s;name=%3$s;nick=%4$s", user.getUsername(), user.getId(), user.getName()));
	    				}
	    			}
	    		}
	    		return true;
	    	}}catch(Exception e){
	    		
	    	}
	    }
	    //4、非法请求 即这些请求需要登录后才能访问  
	    //重定向到登录页面  
	    response.sendRedirect(remoteRedirectUrl);
	    return false; 
	}
	    private User createUser(String users){
			if(StringUtils.isEmpty(users)) return null;
			//fw121fw42$462144$2$0$普通会员$10$$$
			String[] arr = users.split("[$]");
			try{
				User user = new User();
				user.setId(arr[1]);
				user.setUsername(arr[0]);
				user.setCoin(Integer.valueOf(arr[2]));
				return user;
			}catch(Exception e){
				e.printStackTrace();
			}
			return null;
		}
	/*
	 * 业务处理完全处理完后被调用。
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception{
		super.afterCompletion(request, response, handler, ex);
		if(logger.isDebugEnabled()){
			long consumeTime = System.currentTimeMillis() - this.startTimeThreadLocal.get();
			logger.debug("前台业务"+request.getServletPath()+"处理完成，耗时：" + consumeTime + "  " + ((consumeTime > 500) ? "[较慢]" : "[正常]"));
		}
	}
}