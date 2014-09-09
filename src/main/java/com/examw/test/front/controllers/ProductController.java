package com.examw.test.front.controllers;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.test.front.model.ProductInfo;
import com.examw.test.front.service.IProductService;

/**
 * 产品控制器
 * @author fengwei.
 * @since 2014年9月9日 上午9:01:08.
 */
@Controller
@RequestMapping("/products")
public class ProductController {	
	private static final Logger logger = Logger.getLogger(IndexController.class);
	@Resource
	private IProductService productService;
	
	@RequestMapping(value = {"","/"}, method = {RequestMethod.GET,RequestMethod.POST})
	public String products(String examId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载products...");
		logger.debug(examId);
		try{
			List<ProductInfo> list = this.productService.loadProducts(examId);
			model.addAttribute("PRODUCTLIST", list);
		}catch(Exception e){
			e.printStackTrace();
			if(logger.isDebugEnabled()) logger.debug("加载products异常...");
		}
		return "products";
	}
}
