package com.examw.test.front.controllers;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.test.front.model.ProductInfo;
import com.examw.test.front.service.IProductService;
/**
 * 题库控制器
 * @author fengwei.
 * @since 2014年9月9日 上午10:12:35.
 */
@Controller
@RequestMapping("/library")
public class LibraryController {
	private static final Logger logger = Logger.getLogger(LibraryController.class);
	
	@Resource
	private IProductService productService;
	
	@RequestMapping(value = {"","/"}, method = {RequestMethod.GET,RequestMethod.POST})
	public String library(String productId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载题库界面...");
		try{
			ProductInfo info = productService.loadProduct(productId);
			model.addAttribute("PRODUCT", info);
		}catch(Exception e){
			e.printStackTrace();
			if(logger.isDebugEnabled()) logger.debug("加载product异常...");
		}
		return "exam_center";
	}
	
	@RequestMapping(value = "/collection", method = {RequestMethod.GET,RequestMethod.POST})
	public String collection(String productId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载收藏界面...");
		return "collections_list";
	}
	
	@RequestMapping(value = "error", method = {RequestMethod.GET,RequestMethod.POST})
	public String error(String productId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载错题界面...");
		return "error_records";
	}
	
	@RequestMapping(value = "simulate", method = {RequestMethod.GET,RequestMethod.POST})
	public String simulate(String productId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载错题界面...");
		return "simulate";
	}
	
	@RequestMapping(value = "last-exam", method = {RequestMethod.GET,RequestMethod.POST})
	public String lastExam(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载错题界面...");
		return "multi_mode";
	}
	
	@RequestMapping(value = "daily", method = {RequestMethod.GET,RequestMethod.POST})
	public String daily(String productId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载题库界面...");
		return "daily_practice";
	}
}
