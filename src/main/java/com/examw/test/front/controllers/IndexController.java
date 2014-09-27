package com.examw.test.front.controllers;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.test.front.model.product.FrontCategoryInfo;
import com.examw.test.front.service.ICategoryService;

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
	
	/**
	 * 获取首页。
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"","/index","/"}, method = RequestMethod.GET)
	public String index(Model model){
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
}