package com.examw.test.front.service.impl;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.front.model.PaperInfo;
import com.examw.test.front.model.PaperPreview;
import com.examw.test.front.service.IPaperService;
import com.examw.test.front.support.HttpUtil;
import com.examw.test.front.support.JSONUtil;

/**
 * 试卷服务接口实现类
 * @author fengwei.
 * @since 2014年9月11日 上午8:26:24.
 */
public class PaperServiceImpl implements IPaperService{
	private static final Logger logger = Logger.getLogger(PaperServiceImpl.class);
	private String api_paperlist_url;
	private String api_paperinfo_url;
	private String api_paperitem_url;
	/**
	 * 设置 试卷列表数据接口地址
	 * @param api_paperlist_url
	 * 
	 */
	public void setApi_paperlist_url(String api_paperlist_url) {
		this.api_paperlist_url = api_paperlist_url;
	}
	/**
	 * 设置 试卷基本信息数据接口地址
	 * @param api_paperinfo_url
	 * 
	 */
	public void setApi_paperinfo_url(String api_paperinfo_url) {
		this.api_paperinfo_url = api_paperinfo_url;
	}
	/**
	 * 设置 试卷试题数据接口地址
	 * @param api_paperitem_url
	 * 
	 */
	public void setApi_paperitem_url(String api_paperitem_url) {
		this.api_paperitem_url = api_paperitem_url;
	}
	/*
	 * 加载试卷列表
	 * @see com.examw.test.front.service.IPaperService#loadPaperList(java.lang.String, com.examw.test.front.model.PaperInfo)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> loadPaperList(String productId, PaperInfo info) throws IOException {
		if(logger.isDebugEnabled()) logger.debug("加载模拟考场试卷列表...");
		if(StringUtils.isEmpty(productId))
		return null;
		String url = String.format(this.api_paperlist_url,productId);
		StringBuilder data = new StringBuilder();
		if(info!=null){
			//科目
			if(!StringUtils.isEmpty(info.getSubjectId())){
				data.append("subjectId=").append(info.getSubjectId()).append("&");
			}
			//试卷类型
			if(!StringUtils.isEmpty(info.getType())){
				data.append("type=").append(info.getType()).append("&");
			}
			//年份
			if(!StringUtils.isEmpty(info.getYear())){
				data.append("year=").append(info.getYear()).append("&");
			}
			//地区
//			if(!StringUtils.isEmpty(info.getAreaId())){
//				data = data + "areaId="+info.getAreaId()+"&";
//			}
			//页码 默认一页10条
			if(!StringUtils.isEmpty(info.getPage())){
				data.append("page=").append(info.getPage()).append("&");
				data.append("rows=10");
			}else{
				//默认第一页
				data.append("page=1&rows=10");
			}
		}
		String xml = HttpUtil.httpRequest(url,"POST",data.toString(),"utf-8");
		if(!StringUtils.isEmpty(xml)){
			return JSONUtil.JsonToCollection(xml, Map.class, String.class,Object.class);
		}
		return null;
	}
	/*
	 * 加载试卷的基本信息
	 * @see com.examw.test.front.service.IPaperService#loadPaperInfo(java.lang.String)
	 */
	@Override
	public PaperPreview loadPaperInfo(String paperId) throws IOException {
		if(logger.isDebugEnabled()) logger.debug("加载模拟考场试卷基本信息...");
		if(StringUtils.isEmpty(paperId))
		return null;
		String url = String.format(this.api_paperinfo_url,paperId);
		String xml = HttpUtil.httpRequest(url,"GET",null,"utf-8");
		if(!StringUtils.isEmpty(xml)){
			return JSONUtil.JsonToObject(xml, PaperPreview.class);
		}
		return null;
	}
	
	@Override
	public PaperPreview loadPaperDetail(String paperId) throws IOException {
		if(logger.isDebugEnabled()) logger.debug("加载模拟考场试卷基本信息...");
		if(StringUtils.isEmpty(paperId))
		return null;
		String url = String.format(this.api_paperitem_url,paperId);
		String xml = HttpUtil.httpRequest(url,"GET",null,"utf-8");
		if(!StringUtils.isEmpty(xml)){
			return JSONUtil.JsonToObject(xml, PaperPreview.class);
		}
		return null;
	}
}
