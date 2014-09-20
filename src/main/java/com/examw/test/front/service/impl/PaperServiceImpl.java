package com.examw.test.front.service.impl;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.model.Json;
import com.examw.test.front.model.Constant;
import com.examw.test.front.model.ItemScoreInfo;
import com.examw.test.front.model.PaperInfo;
import com.examw.test.front.model.PaperPreview;
import com.examw.test.front.model.StructureInfo;
import com.examw.test.front.model.StructureItemInfo;
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
	private String api_papersubmit_url;
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
	
	/**
	 * 设置 试卷提交数据接口地址
	 * @param api_papersubmit_url
	 * 
	 */
	public void setApi_papersubmit_url(String api_papersubmit_url) {
		this.api_papersubmit_url = api_papersubmit_url;
	}
	/*
	 * 加载试卷列表
	 * @see com.examw.test.front.service.IPaperService#loadPaperList(java.lang.String, com.examw.test.front.model.PaperInfo)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> loadPaperList(String productId, PaperInfo info,String userId) throws IOException {
		if(logger.isDebugEnabled()) logger.debug("加载模拟考场试卷列表...");
		if(StringUtils.isEmpty(productId))
		return null;
		String url = String.format(this.api_paperlist_url,productId,userId);
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
	
	/*
	 * 加载试卷详情[带试题]
	 * @see com.examw.test.front.service.IPaperService#loadPaperDetail(java.lang.String)
	 */
	@Override
	public PaperPreview loadPaperDetail(String paperId,String userId) throws IOException {
		if(logger.isDebugEnabled()) logger.debug("加载模拟考场试卷基本信息...");
		if(StringUtils.isEmpty(paperId) || StringUtils.isEmpty(userId))
		return null;
		String url = String.format(this.api_paperitem_url,paperId,userId);
		String xml = HttpUtil.httpRequest(url,"GET",null,"utf-8");
		if(!StringUtils.isEmpty(xml)){
			return JSONUtil.JsonToObject(xml, PaperPreview.class);
		}
		return null;
	}
	/*
	 * 从试卷详情中把试题集合择出来
	 * @see com.examw.test.front.service.IPaperService#loadItemsList(com.examw.test.front.model.PaperPreview)
	 */
	@Override
	public List<ItemScoreInfo> loadItemsList(PaperPreview paper,boolean isSetCommonTitle) {
		if(paper == null)	return null;
		List<ItemScoreInfo> result = new ArrayList<ItemScoreInfo>();
		List<StructureInfo> structures = paper.getStructures();
		if(structures == null || structures.size()==0) return result;
		for(StructureInfo s:structures){
			Set<StructureItemInfo> items = s.getItems();
			if(items == null || items.size()==0) continue;
			for(StructureItemInfo item : items){
				if(item.getItem() == null) continue;
				if(item.getType().equals(Constant.TYPE_SHARE_ANSWER)||item.getType().equals(Constant.TYPE_SHARE_TITLE)){
					if(isSetCommonTitle){
						setCommonItemTile(item); //设置题干
					}
					result.addAll(item.getItem().getChildren());
				}
				else{
					item.getItem().setStructureItemId(item.getId());
					result.add(item.getItem());
				}
			}
		}
		return result;
	}
	/*
	 * 设置共享题题干
	 */
	private void setCommonItemTile(StructureItemInfo item) {
		Set<ItemScoreInfo> set = item.getItem().getChildren();
		for(ItemScoreInfo info : set){
			info.setParentContent(item.getContent());
			info.setStructureItemId(item.getId()+"#"+info.getId());
		}
		item.getItem().setChildren(set);
	}
	
	@Override
	public Json sumbitPaper(Integer limitTime, String chooseAnswers,
			String textAnswers,Integer model, String paperId, String userId)throws IOException {
		if(logger.isDebugEnabled()) logger.debug("加载模拟考场试卷基本信息...");
		if(StringUtils.isEmpty(paperId) || StringUtils.isEmpty(userId))
		return null;
		String url = String.format(this.api_papersubmit_url,paperId,userId);
		StringBuilder data = new StringBuilder();
		data.append("model=").append(model);
		data.append("&limitTime=").append(limitTime);
		data.append("&chooseAnswers=").append(chooseAnswers==null?"":URLEncoder.encode(chooseAnswers,"utf-8"));
		data.append("&textAnswers=").append(textAnswers==null?"":textAnswers);
		String xml = HttpUtil.httpRequest(url,"POST",data.toString(),"utf-8");
		if(!StringUtils.isEmpty(xml)){
			return JSONUtil.JsonToObject(xml, Json.class);
		}
		return null;
	}
}
