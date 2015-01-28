package com.examw.test.front.controllers;

import javax.annotation.Resource;

import org.springframework.util.StringUtils;

import com.examw.test.front.model.product.FrontProductInfo;
import com.examw.test.front.service.IProductService;

/**
 * 产品控制器基类
 * @author fengwei.
 * @since 2015年1月28日 上午10:10:55.
 */
public class BaseProductController {
	@Resource
	private IProductService productService;
	
	public FrontProductInfo loadProductInfo(String productId) throws Exception
	{
		if(StringUtils.isEmpty(productId))return null;
		return productService.loadProduct(productId);
	}
}
