package com.examw.test.front.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.model.DataGrid;
import com.examw.test.front.model.product.AreaInfo;
import com.examw.test.front.model.product.FrontProductInfo;
import com.examw.test.front.model.product.SubjectInfo;
import com.examw.test.front.service.IProductService;
import com.examw.test.front.support.HttpUtil;
import com.examw.test.front.support.JSONUtil;
import com.examw.test.front.support.MethodCacheHelper;

/**
 * 产品服务接口实现类
 * @author fengwei.
 * @since 2014年8月12日 下午3:38:24.
 */
public class ProductServiceImpl implements IProductService{
	private static final Logger logger = Logger.getLogger(ProductServiceImpl.class);
	private String api_product_list_url;
	private String api_product_detail_url;
	private String api_product_subjects_url;
	private String api_product_areas_url;
	private MethodCacheHelper cacheHelper;
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

	/**
	 * 获取 缓存帮助类
	 * @return cacheHelper
	 * 
	 */
	public MethodCacheHelper getCacheHelper() {
		return cacheHelper;
	}

	/**
	 * 设置 
	 * @param cacheHelper
	 * 
	 */
	public void setCacheHelper(MethodCacheHelper cacheHelper) {
		this.cacheHelper = cacheHelper;
	}

	/*
	 * 根据考试加载产品
	 * @see com.examw.test.front.service.IProductService#loadProducts(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FrontProductInfo> loadProducts(String examId) throws IOException {
		if(logger.isDebugEnabled()) logger.debug("加载产品列表信息...");
		if(StringUtils.isEmpty(examId))
		return null;
		String url = String.format(this.api_product_list_url, examId);
		String xml = HttpUtil.httpRequest(url,"GET",null,"utf-8");
		if(!StringUtils.isEmpty(xml)){
			return JSONUtil.JsonToCollection(xml,List.class,FrontProductInfo.class);
		}
		return null;
	}
	
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
	
	@SuppressWarnings("unchecked")
	@Override
	public DataGrid<FrontProductInfo> dataGrid(FrontProductInfo info)throws IOException {
		if(logger.isDebugEnabled()) logger.debug("加载产品分页列表信息...");
		Integer page = info.getPage()==null?1:info.getPage();
		Integer rows = info.getRows()==null?10:info.getRows();
		List<FrontProductInfo> list = (List<FrontProductInfo>) this.cacheHelper.getCache(FrontProductInfo.class.getName(), this.getClass().getName()+"loadProducts", new Object[]{info.getExamId()});
		if(list == null){
			list = this.loadProducts(info.getExamId());
			if(list!=null)
				this.cacheHelper.putCache(FrontProductInfo.class.getName(), this.getClass().getName()+"loadProducts", new Object[]{info.getExamId()}, list);
		}
		DataGrid<FrontProductInfo> datagrid = new DataGrid<FrontProductInfo>();
		if(list != null && list.size()>0){
			int total = list.size();
			datagrid.setTotal((long) total);
			Integer totalPage = total%rows==0?total/rows:(total/rows+1);
			page = page > totalPage?totalPage:page;
			if(list.size() <= rows)
			{
				datagrid.setRows(list);
			}else
				datagrid.setRows(list.subList((page-1)*rows, page*rows>total?total:page*rows));
		}
		return datagrid;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SubjectInfo> loadProductSubjects(String productId)
			throws IOException {
		if(logger.isDebugEnabled()) logger.debug("加载选中产品信息....");
		if(StringUtils.isEmpty(productId))
		return null;
		String url = String.format(this.api_product_subjects_url,productId);
		String xml = HttpUtil.httpRequest(url,"GET",null,"utf-8");
		if(!StringUtils.isEmpty(xml)){
			return JSONUtil.JsonToCollection(xml,List.class,SubjectInfo.class);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AreaInfo> loadProductAreas(String productId) throws IOException {
		if(logger.isDebugEnabled()) logger.debug("加载选中产品信息....");
		if(StringUtils.isEmpty(productId))
		return null;
		String url = String.format(this.api_product_areas_url,productId);
		String xml = HttpUtil.httpRequest(url,"GET",null,"utf-8");
		if(!StringUtils.isEmpty(xml)){
			return JSONUtil.JsonToCollection(xml,List.class,SubjectInfo.class);
		}
		return null;
	}
}
