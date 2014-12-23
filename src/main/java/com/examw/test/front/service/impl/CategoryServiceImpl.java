package com.examw.test.front.service.impl;

import java.util.List;

import org.springframework.util.StringUtils;

import com.examw.test.front.model.product.FrontCategoryInfo;
import com.examw.test.front.service.ICategoryService;
import com.examw.test.front.service.IRemoteService;
import com.examw.test.front.support.JSONUtil;

/**
 * 考试分类数据接口实现类
 * @author fengwei.
 * @since 2014年9月5日 上午9:43:15.
 */
public class CategoryServiceImpl implements ICategoryService{
	private String api_url;
	
	
	/**
	 * 设置 数据接口请求地址
	 * @param api_url
	 * 
	 */
	public void setApi_url(String api_url) {
		this.api_url = api_url;
	}
	private IRemoteService remoteService;
	/**
	 * 设置 远程服务
	 * @param remoteService
	 * 
	 */
	public void setRemoteService(IRemoteService remoteService) {
		this.remoteService = remoteService;
	}

	/*
	 * 加载所有的考试类型-考试
	 * @see com.examw.test.front.service.ICategoryService#loadAllCategoryAndExams()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FrontCategoryInfo> loadAllCategoryAndExams() throws Exception {
		String xml = remoteService.httpRequest(api_url,"GET",null,"utf-8");
		if(!StringUtils.isEmpty(xml)){
			return JSONUtil.JsonToCollection(xml,List.class,FrontCategoryInfo.class);
		}
		return null;
	}
}
