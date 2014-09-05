package com.examw.test.front.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.util.StringUtils;

import com.examw.test.front.model.CategoryFrontInfo;
import com.examw.test.front.service.ICategoryService;
import com.examw.test.front.support.HttpUtil;
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

	@SuppressWarnings("unchecked")
	@Override
	public List<CategoryFrontInfo> loadAllCategoryAndExams() throws IOException {
		String xml = HttpUtil.httpRequest(api_url,"GET",null,"utf-8");
		if(!StringUtils.isEmpty(xml)){
			return JSONUtil.JsonToCollection(xml,List.class,CategoryFrontInfo.class);
		}
		return null;
	}
	
}
