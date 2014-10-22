package com.examw.test.front.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.front.model.syllabus.KnowledgeInfo;
import com.examw.test.front.model.syllabus.SyllabusInfo;
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
	private String api_knowledge_detail_url;
	private String api_chapter_detail_url;
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
	public void setApi_knowledge_detail_url(String knowledge_detail_url) {
		this.api_knowledge_detail_url = knowledge_detail_url;
	}

	/**
	 * 设置 知识点集合数据接口地址
	 * @param api_knowledge_list_url
	 * 
	 */
	public void setApi_chapter_detail_url(String api_chapter_detail_url) {
		this.api_chapter_detail_url = api_chapter_detail_url;
	}

	/*
	 * 加载考试,科目和章节的信息
	 * @see com.examw.test.front.service.IChapterService#loadExamAndChapterInfo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SyllabusInfo> loadChapterInfo(String subjectId)
			throws Exception {
		if(logger.isDebugEnabled()) logger.debug("加载章节信息....");
		if(StringUtils.isEmpty(subjectId))
		return null;
		String url = String.format(this.api_list_url,subjectId);
		String xml = HttpUtil.httpRequest(url,"GET",null,"utf-8");
		if(!StringUtils.isEmpty(xml)){
			return JSONUtil.JsonToCollection(xml, List.class, SyllabusInfo.class);
		}
		return null;
	}
	
	/*
	 * 加载章节详情
	 * @see com.examw.test.front.service.IChapterService#loadKnowledgeDetail(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public KnowledgeInfo loadKnowledgeDetail(String id) throws Exception {
		if(logger.isDebugEnabled()) logger.debug("加载知识点详情信息....");
		if(StringUtils.isEmpty(id))
		return null;
		String url = String.format(this.api_knowledge_detail_url,id);
		String xml = HttpUtil.httpRequest(url,"GET",null,"utf-8");
		if(!StringUtils.isEmpty(xml)){
			List<KnowledgeInfo> list = JSONUtil.JsonToCollection(xml,List.class, KnowledgeInfo.class);
			if(list!=null && list.size()>0){
				return list.get(0);
			}
		}
		return null;
	}
	
	/*
	 * 加载某小节信息
	 * @see com.examw.test.front.service.IChapterService#loadKnowledges(java.lang.String)
	 */
	@Override
	public SyllabusInfo loadKnowledges(String chapterId)
			throws Exception {
		if(logger.isDebugEnabled()) logger.debug("加载知识点集合信息....");
		if(StringUtils.isEmpty(chapterId))
		return null;
		String url = String.format(this.api_chapter_detail_url,chapterId);
		String xml = HttpUtil.httpRequest(url,"GET",null,"utf-8");
		if(!StringUtils.isEmpty(xml)){
			return JSONUtil.JsonToObject(xml, SyllabusInfo.class);
		}
		return null;
	}
}
