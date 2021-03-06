package com.examw.test.front.controllers;

import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.test.front.model.user.User;
import com.examw.test.front.service.IUserService;
import com.examw.test.front.support.TaoBaoMD5;

/**
 * 用户控制器
 * @author fengwei.
 * @since 2014年10月20日 下午2:00:29.
 */
@Controller
public class UserController {
	private static final Logger logger = Logger.getLogger(UserController.class);
	//用户服务接口
	@Resource
	private IUserService userService;
	
	/**
	 * 获取用户信息	保存至session与cookie中
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/getUserInfo" , method={RequestMethod.GET,RequestMethod.POST})
	public String getUserInfo(HttpServletRequest request,HttpServletResponse response,Model model)
	{
		try{
			if(logger.isDebugEnabled()) logger.debug("url=" + request.getQueryString());
			String md5Key = this.userService.getMd5Key();
			if(logger.isDebugEnabled()) logger.debug("md5key=" + md5Key);
			String users = request.getParameter("Users");
			if(logger.isDebugEnabled()) logger.debug("users=" + users);
			
			users = new String(users.getBytes("ISO-8859-1"),"GBK");
			if(logger.isDebugEnabled()) logger.debug("users(GBK)=" + users);
			String key = request.getParameter("KeyStr");
			if(logger.isDebugEnabled()) logger.debug("KeyStr(key1)=" + key);
			String key2 = TaoBaoMD5.sign(users, md5Key, "GBK");
			if(logger.isDebugEnabled()) logger.debug("KeyStr(key2)=" + key2);
			if(key2.equals(key)){
				if(logger.isDebugEnabled()) logger.debug("key1==key2");
				User user = this.userService.verifyUser(users);
				request.getSession().setAttribute("USER", user);
				users = user.getProductUserId()+"$"+users;
				if(logger.isDebugEnabled()) logger.debug("users(cookie)="+ users);
				key = TaoBaoMD5.sign(users, md5Key, "GBK");
				if(logger.isDebugEnabled()) logger.debug("users(cookie key)="+ key);
				//创建cookie
				response.setHeader("P3P","CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'");
				response.setHeader("Set-Cookie","Examwww="+URLEncoder.encode(users, "utf-8")+"#"+key);
			}
			String lastPage = "/";
			Cookie[] cookies = request.getCookies();
		    if(cookies!=null){
		    	for(Cookie c:cookies){
		    		if("LastPage".equals(c.getName())){
		    			lastPage = c.getValue();
		    			break;
		    		}
		    	}
		    }
		    if(logger.isDebugEnabled()) logger.debug("redirect:lastpage="+ lastPage);
			return "redirect:"+lastPage;
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return null;
	}
	/**
	 * 用户退出,清除缓存
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/logout" , method={RequestMethod.GET,RequestMethod.POST})
	public String logout(HttpServletRequest request,HttpServletResponse response){
		//删除cookie
	    response.setHeader("Set-Cookie","Examwww=");
	    //删除session中的值
	    request.getSession().removeAttribute("USER");
	    request.getSession().removeAttribute("PRODUCTNAME");
	    //清除缓存??
		return "redirect:http://test.examw.com/user/Login/CheckUser.asp?CheckType=Logout";
	}
	/**
	 * 一个测试接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/testCookie", method={RequestMethod.GET,RequestMethod.POST})
	public String testCookie(HttpServletRequest request,HttpServletResponse response,Model model)
	{
		Cookie[] cookies = request.getCookies();
	    String users = null;
	    if(cookies!=null){
	    	for(Cookie c:cookies){
	    		if("ExamwCom".equals(c.getName())){
	    			users = c.getValue();
	    			if(logger.isDebugEnabled())
	    				logger.debug(users);
	    		}
	    	}
	    }
	    model.addAttribute("ExamwCom",users);
	    return "test";
	}
}
