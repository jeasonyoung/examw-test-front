package com.examw.test.front.controllers;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.test.front.model.product.FrontCategoryInfo;
import com.examw.test.front.service.ICategoryService;
import com.examw.test.front.service.IUserService;

/**
 * 题库首页
 * @author fengwei.
 * @since 2014年9月4日 上午10:30:26.
 */
@Controller
public class IndexController{
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
}