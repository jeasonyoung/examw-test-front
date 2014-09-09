package com.examw.test.front.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.front.model.ProductInfo;
import com.examw.test.front.service.IProductService;
import com.examw.test.front.support.HttpUtil;
import com.examw.test.front.support.JSONUtil;

/**
 * 产品服务接口实现类
 * @author fengwei.
 * @since 2014年8月12日 下午3:38:24.
 */
public class ProductServiceImpl implements IProductService{
	private static final Logger logger = Logger.getLogger(ProductServiceImpl.class);
	private String api_product_list_url;
	private String api_product_detail_url;
	/**
	 * 设置 数据接口请求地址
	 * @param api_url
	 * 
	 */
	public void setApi_product_list_url(String api_product_list_url) {
		this.api_product_list_url = api_product_list_url;
	}
	
	/**
	 * 设置 产品详情请求地址
	 * @param api_product_detail_url
	 * 
	 */
	public void setApi_product_detail_url(String api_product_detail_url) {
		this.api_product_detail_url = api_product_detail_url;
	}

	/*
	 * 根据考试加载产品
	 * @see com.examw.test.front.service.IProductService#loadProducts(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductInfo> loadProducts(String examId) throws IOException {
		if(logger.isDebugEnabled()) logger.debug("加载产品列表信息...");
		if(StringUtils.isEmpty(examId))
		return null;
		String xml = HttpUtil.httpRequest(api_product_list_url,"GET","examId="+examId,"utf-8");
		if(!StringUtils.isEmpty(xml)){
			return JSONUtil.JsonToCollection(xml,List.class,ProductInfo.class);
		}
		return null;
	}
	
	@Override
	public ProductInfo loadProduct(String id) throws IOException {
		if(logger.isDebugEnabled()) logger.debug("加载选中产品信息....");
		if(StringUtils.isEmpty(id))
		return null;
		String url = String.format(this.api_product_detail_url,id);
		String xml = HttpUtil.httpRequest(url,"GET",null,"utf-8");
		if(!StringUtils.isEmpty(xml)){
			return JSONUtil.JsonToObject(xml,ProductInfo.class);
		}
		return null;
	}
}
