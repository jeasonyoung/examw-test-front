package com.examw.test.front.controllers;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.test.front.service.IProductService;

/**
 * 产品控制器
 * @author fengwei.
 * @since 2014年9月9日 上午9:01:08.
 */
@Controller
@RequestMapping("/product")
public class ProductController {	
	private static final Logger logger = Logger.getLogger(IndexController.class);
	//产品服务接口
	@Resource
	private IProductService productService;
	/**
	 * 根据所选考试加载产品列表
	 * @param examId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/all/{categoryId}", method = {RequestMethod.GET,RequestMethod.POST})
	public String productsAll(@PathVariable String categoryId,String examId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载产品列表页面...");
		try{
			model.addAttribute("CATEGORYID", categoryId);
			model.addAttribute("EXAMID", examId);
			model.addAttribute("PRODUCTLIST",this.productService.loadProducts(examId,categoryId));
		}catch(Exception e){
			e.printStackTrace();
		}
		return "products";
	}
	@RequestMapping(value = "/{examId}", method = {RequestMethod.GET,RequestMethod.POST})
	public String products(@PathVariable String examId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载产品列表页面...");
		try{
			model.addAttribute("EXAMID", examId);
			model.addAttribute("PRODUCTLIST",this.productService.loadProducts(examId,null));
		}catch(Exception e){
			e.printStackTrace();
		}
		return "products";
	}
}
