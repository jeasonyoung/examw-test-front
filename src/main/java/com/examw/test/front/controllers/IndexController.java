package com.examw.test.front.controllers;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.test.front.model.CategoryFrontInfo;
import com.examw.test.front.service.ICategoryService;

/**
 * 题库首页
 * @author fengwei.
 * @since 2014年9月4日 上午10:30:26.
 */
@Controller
@RequestMapping()
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
			List<CategoryFrontInfo> list = this.categoryService.loadAllCategoryAndExams();
			model.addAttribute("CATEGORYLIST", list);
		}catch(Exception e){
			e.printStackTrace();
			if(logger.isDebugEnabled()) logger.debug("加载index获取考试分类-考试数据异常...");
		}
		return "index";
	}
}