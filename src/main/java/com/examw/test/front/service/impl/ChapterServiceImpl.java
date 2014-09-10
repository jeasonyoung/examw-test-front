package com.examw.test.front.service.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.front.service.IChapterService;
import com.examw.test.front.support.HttpUtil;
import com.examw.test.front.support.JSONUtil;

/**
 * 章节练习服务接口实现类
 * @author fengwei.
 * @since 2014年9月9日 下午4:12:10.
 */
public class ChapterServiceImpl implements IChapterService{
	private static final Logger logger = Logger.getLogger(ProductServiceImpl.class);
	private String api_list_url;
	private String api_detail_url;
	/**
	 * 设置 章节列表数据接口地址
	 * @param api_url
	 * 
	 */
	public void setApi_list_url(String api_list_url) {
		this.api_list_url = api_list_url;
	}
	
	/**
	 * 设置 章节详情数据接口地址
	 * @param api_detail_url
	 * 
	 */
	public void setApi_detail_url(String api_detail_url) {
		this.api_detail_url = api_detail_url;
	}


	/*
	 * 加载考试,科目和章节的信息
	 * @see com.examw.test.front.service.IChapterService#loadExamAndChapterInfo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> loadExamAndChapterInfo(String examId,String subjectId)
			throws Exception {
		if(logger.isDebugEnabled()) logger.debug("加载章节信息....");
		if(StringUtils.isEmpty(examId))
		return null;
		String url = String.format(this.api_list_url,examId);
		String xml = HttpUtil.httpRequest(url,"GET","subjectId="+subjectId,"utf-8");
		if(!StringUtils.isEmpty(xml)){
			return JSONUtil.JsonToCollection(xml, Map.class, String.class,Object.class);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> loadChapterDetail(String pid, String id)
			throws Exception {
		if(logger.isDebugEnabled()) logger.debug("加载章节详情信息....");
		if(StringUtils.isEmpty(pid)||StringUtils.isEmpty(id))
		return null;
		String url = String.format(this.api_detail_url,pid,id);
		String xml = HttpUtil.httpRequest(url,"GET",null,"utf-8");
		if(!StringUtils.isEmpty(xml)){
			return JSONUtil.JsonToCollection(xml, Map.class, String.class,Object.class);
		}
		return null;
	}
}
