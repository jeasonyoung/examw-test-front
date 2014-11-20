package com.examw.test.front.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.front.model.product.AreaInfo;
import com.examw.test.front.model.product.FrontProductInfo;
import com.examw.test.front.model.product.SubjectInfo;
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
	private String api_product_list_url_category;
	private String api_product_detail_url;
	private String api_product_subjects_url;
	private String api_product_areas_url;
	/**
	 * 设置 数据接口请求地址
	 * @param api_url
	 * 
	 */
	public void setApi_product_list_url(String api_product_list_url) {
		this.api_product_list_url = api_product_list_url;
	}
	/**
	 * 设置 据接口请求地址
	 * @param api_product_list_url_category
	 * 
	 */
	public void setApi_product_list_url_category(
			String api_product_list_url_category) {
		this.api_product_list_url_category = api_product_list_url_category;
	}

	/**
	 * 设置 产品详情请求地址
	 * @param api_product_detail_url
	 * 
	 */
	public void setApi_product_detail_url(String api_product_detail_url) {
		this.api_product_detail_url = api_product_detail_url;
	}
	
	
	/**
	 * 设置 产品包含科目请求地址
	 * @param api_product_subjects_url
	 * 
	 */
	public void setApi_product_subjects_url(String api_product_subjects_url) {
		this.api_product_subjects_url = api_product_subjects_url;
	}
	
	/**
	 * 设置 产品包含地区请求地址
	 * @param api_product_areas_url
	 * 
	 */
	public void setApi_product_areas_url(String api_product_areas_url) {
		this.api_product_areas_url = api_product_areas_url;
	}

	/*
	 * 根据考试加载产品
	 * @see com.examw.test.front.service.IProductService#loadProducts(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FrontProductInfo> loadProducts(String examId,String categoryId) throws IOException {
		if(logger.isDebugEnabled()) logger.debug("加载产品列表信息...");
		if(StringUtils.isEmpty(examId) && StringUtils.isEmpty(categoryId))
		return null;
		String url = null;
		if(StringUtils.isEmpty(categoryId))
			url = String.format(this.api_product_list_url, examId);
		else
			url = String.format(this.api_product_list_url_category, categoryId);
		String xml = HttpUtil.httpRequest(url,"GET",null,"utf-8");
		if(!StringUtils.isEmpty(xml)){
			return JSONUtil.JsonToCollection(xml,List.class,FrontProductInfo.class);
		}
		return null;
	}
	/*
	 * 加载选中产品的信息
	 * @see com.examw.test.front.service.IProductService#loadProduct(java.lang.String)
	 */
	@Override
	public FrontProductInfo loadProduct(String id) throws IOException {
		if(logger.isDebugEnabled()) logger.debug("加载选中产品信息....");
		if(StringUtils.isEmpty(id))
		return null;
		String url = String.format(this.api_product_detail_url,id);
		String xml = HttpUtil.httpRequest(url,"GET",null,"utf-8");
		if(!StringUtils.isEmpty(xml)){
			return JSONUtil.JsonToObject(xml,FrontProductInfo.class);
		}
		return null;
	}
	/*
	 * 加载产品包含的科目信息
	 * @see com.examw.test.front.service.IProductService#loadProductSubjects(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SubjectInfo> loadProductSubjects(String productId)
			throws IOException {
		if(logger.isDebugEnabled()) logger.debug("加载产品包含的科目信息....");
		if(StringUtils.isEmpty(productId))
		return null;
		String url = String.format(this.api_product_subjects_url,productId);
		String xml = HttpUtil.httpRequest(url,"GET",null,"utf-8");
		if(!StringUtils.isEmpty(xml)){
			return JSONUtil.JsonToCollection(xml,List.class,SubjectInfo.class);
		}
		return null;
	}
	/*
	 * 加载产品的地区信息
	 * @see com.examw.test.front.service.IProductService#loadProductAreas(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AreaInfo> loadProductAreas(String productId) throws IOException {
		if(logger.isDebugEnabled()) logger.debug("加载产品的地区信息....");
		if(StringUtils.isEmpty(productId))
		return null;
		String url = String.format(this.api_product_areas_url,productId);
		String xml = HttpUtil.httpRequest(url,"GET",null,"utf-8");
		if(!StringUtils.isEmpty(xml)){
			return JSONUtil.JsonToCollection(xml,List.class,AreaInfo.class);
		}
		return null;
	}
}
