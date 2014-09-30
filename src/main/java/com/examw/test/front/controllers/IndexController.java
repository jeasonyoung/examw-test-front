package com.examw.test.front.controllers;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.Json;
import com.examw.test.front.model.product.FrontCategoryInfo;
import com.examw.test.front.model.user.User;
import com.examw.test.front.service.ICategoryService;
import com.examw.test.front.service.IUserService;
import com.examw.test.front.support.TaoBaoMD5;

/**
 * 题库首页
 * @author fengwei.
 * @since 2014年9月4日 上午10:30:26.
 */
@Controller
public class IndexController {
	private static final Logger logger = Logger.getLogger(IndexController.class);
	@Resource
	private ICategoryService categoryService;
	@Resource
	private IUserService userService;
	
	/**
	 * 获取首页。
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"","/index","/"}, method = RequestMethod.GET)
	public String index(Model model,HttpServletResponse response){
		if(logger.isDebugEnabled()) logger.debug("加载index...");
		try{
			List<FrontCategoryInfo> list = this.categoryService.loadAllCategoryAndExams();
			model.addAttribute("CATEGORYLIST", list);
		}catch(Exception e){
			e.printStackTrace();
			if(logger.isDebugEnabled()) logger.debug("加载index获取考试分类-考试数据异常...");
		}
		return "index";
	}
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model){
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Json login(User user,HttpServletRequest request){
		Json json = new Json();
		try{
			User stu = this.userService.login(user);
			if(stu == null){
				json.setMsg("登录失败,用户名或密码错误");
			}else{
				json.setSuccess(true);
				json.setMsg("登录成功");
				request.getSession().setAttribute("USER", stu);
			}
		}catch(Exception e){
			e.printStackTrace();
			json.setMsg("登录失败");
		}
		return json;
	}
	/**
	 * 获取每日一练
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/daily", method = RequestMethod.GET)
	public String daily(Model model){
		return "daily_practice";
	}
	
	/**
	 * 获取模拟考场
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/simulate", method = RequestMethod.GET)
	public String simulate(Model model){
		return "simulate";
	}
	
	/**
	 * 获取章节练习
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/chapter", method = RequestMethod.GET)
	public String chapter(Model model){
		return "chapter_list";
	}
	
	/**
	 * 获取练习记录
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/record", method = RequestMethod.GET)
	public String records(Model model){
		return "practice_records";
	}
	
	/**
	 * 获取题库首页
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/center", method = RequestMethod.GET)
	public String center(Model model){
		return "exam_center";
	}
	
	@RequestMapping(value="/getUserInfo" , method={RequestMethod.GET,RequestMethod.POST})
	public String getUserInfo(HttpServletRequest request,HttpServletResponse response,Model model)
	{
		try{
			String Md5Key = "4q3i07f12u5i8R1nU";
			String users = request.getParameter("Users");
			users = new String(users.getBytes("ISO-8859-1"),"GBK");
			String key = request.getParameter("KeyStr");
			String key2 = TaoBaoMD5.sign(users, Md5Key, "GBK");
			String url = request.getParameter("Url");
			url = URLDecoder.decode(url,"utf-8");
			Cookie[] cookies = request.getCookies();
			if(cookies!=null){
			for(Cookie c:cookies){
				System.out.println(c.getName()+"   ====   "+c.getValue());
			}}
			if(key2.equals(key)){
				response.setHeader("P3P","CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'");
				response.setHeader("Set-Cookie","Examwww="+URLEncoder.encode(users, "utf-8")+"#"+key);
				User user = createUser(users);
				request.getSession().setAttribute("USER", user);
			}
			return "redirect:/";
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return null;
	}
	private User createUser(String users){
		if(StringUtils.isEmpty(users)) return null;
		//fw121fw42$462144$2$0$普通会员$10$$$
		String[] arr = users.split("$");
		try{
			User user = new User();
			user.setId(arr[1]);
			user.setUsername(arr[0]);
			user.setCoin(Integer.valueOf(arr[2]));
		}catch(Exception e){
			
		}
		return null;
	}
//	@RequestMapping(value="/getStr" , method={RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public String getStr(){
//		String url2 = "http://sc.100xuexi.com/GetBookInfo.ashx?act=GetBookInfo&BookId=40805&PlatNum=1";
//		String xml2;
//		try {
//			xml2 = HttpUtil.httpRequest(url2, "GET", null, "utf-8");
//		System.out.println(xml2);
//		String xml3 = URLEncoder.encode(xml2,"GBK");
//		System.out.println(xml3);
//		System.out.println(URLDecoder.decode(xml3, "gbk"));
//		return URLEncoder.encode(xml2,"GBK");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return "";
//	}
	
//	@RequestMapping(value="/putStr" , method={RequestMethod.GET,RequestMethod.POST})
//	public String putStr(HttpServletRequest request){
//		Cookie[] cookies = request.getCookies();
//	    String users = null;
//	    if(cookies!=null){
//	    	for(Cookie c:cookies){
//	    		if("Examwww".equals(c.getName())){
//	    			users = c.getValue();
//	    			if(logger.isDebugEnabled())
//	    				logger.debug(users);
//	    		}
//	    	}
//	    }
//		return "";
//	}
	
	@RequestMapping(value="/logout" , method={RequestMethod.GET,RequestMethod.POST})
	public String logout(HttpServletRequest request,HttpServletResponse response){
		//删除cookie
	    response.setHeader("Set-Cookie","Examwww=");
		return "";
	}
	
}