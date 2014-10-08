package com.examw.test.front.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.test.front.model.library.FrontPaperInfo;
import com.examw.test.front.model.library.PaperInfo;
import com.examw.test.front.model.library.PaperPreview;
import com.examw.test.front.model.library.PaperRecordInfo;
import com.examw.test.front.model.library.PaperSubmitInfo;
import com.examw.test.front.model.library.StructureInfo;
import com.examw.test.front.model.library.StructureItemInfo;
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
	private String api_paperanalysis_url;
	private String api_papertype_url;
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
	
	/**
	 * 设置 试卷解析地址
	 * @param api_paperanalysis_url
	 * 
	 */
	public void setApi_paperanalysis_url(String api_paperanalysis_url) {
		this.api_paperanalysis_url = api_paperanalysis_url;
	}
	/**
	 * 设置 试卷类型地址
	 * @param api_papertype_url
	 * 
	 */
	public void setApi_papertype_url(String api_papertype_url) {
		this.api_papertype_url = api_papertype_url;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> loadPaperType() throws IOException {
		String xml = HttpUtil.httpRequest(this.api_papertype_url, "GET", null, "utf-8");
		if(StringUtils.isEmpty(xml)) return null;
		return JSONUtil.JsonToCollection(xml, Map.class, String.class,String.class);
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
	
	/*
	 * 加载试卷详情[带试题]
	 * @see com.examw.test.front.service.IPaperService#loadPaperDetail(java.lang.String)
	 */
	@Override
	public PaperPreview loadPaperDetail(String paperId,String userId,String productId) throws IOException {
		if(logger.isDebugEnabled()) logger.debug("加载模拟考场试卷基本信息...");
		//if(StringUtils.isEmpty(paperId) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(productId))
		//return null;
		String url = String.format(this.api_paperitem_url,paperId);
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
	public java.util.List<com.examw.test.front.model.library.StructureItemInfo> loadItemsList(PaperPreview paper) throws IOException {
		if(paper == null) return null;
		List<StructureItemInfo> result = new ArrayList<StructureItemInfo>();
		List<StructureInfo> structures = paper.getStructures();
		if(structures == null || structures.size()==0) return result;
		for(StructureInfo s:structures){
			result.addAll(s.getItems());
		}
		return result;
	};
	/*
	 * 设置共享题题干
	 */
//	private List<StructureItemInfo> setCommonItemTile(StructureItemInfo item,boolean isSetCommonTitle) {
//		List<StructureItemInfo> list = new ArrayList<StructureItemInfo>();
//		TreeSet<StructureItemInfo> set = new TreeSet<StructureItemInfo>(
//				new Comparator<ItemScoreInfo>() {
//					@Override
//					public int compare(ItemScoreInfo o1,
//							ItemScoreInfo o2) {
//						return o1.getOrderNo() - o2.getOrderNo();
//					}
//				});
//		set.addAll(item.getItem().getChildren());
//		for(ItemScoreInfo info : set){
//			if(isSetCommonTitle)	info.setParentContent(item.getContent());
//			info.setStructureItemId(item.getId()+"#"+info.getId());
//			list.add(info);
//		}
//		return list;
//	}
//	@Override
//	public List<ItemScoreInfo> loadItemsList(PaperPreview paper,boolean isSetCommonTitle) {
//		if(paper == null)	return null;
//		List<ItemScoreInfo> result = new ArrayList<ItemScoreInfo>();
//		List<StructureInfo> structures = paper.getStructures();
//		if(structures == null || structures.size()==0) return result;
//		for(StructureInfo s:structures){
//			TreeSet<StructureItemInfo> items = new TreeSet<StructureItemInfo>(
//					new Comparator<StructureItemInfo>() {
//						@Override
//						public int compare(StructureItemInfo o1,
//								StructureItemInfo o2) {
//							return o1.getOrderNo() - o2.getOrderNo();
//						}
//					});
//			items.addAll(s.getItems());
//			if(items == null || items.size()==0) continue;
//			for(StructureItemInfo item : items){
//				if(item.getItem() == null) continue;
//				if(item.getType().equals(Constant.TYPE_SHARE_ANSWER)||item.getType().equals(Constant.TYPE_SHARE_TITLE)){
//					result.addAll(setCommonItemTile(item,isSetCommonTitle)); //设置题干
//				}
//				else{
//					item.getItem().setStructureItemId(item.getId());
//					result.add(item.getItem());
//				}
//			}
//		}
//		return result;
//	}
	
//	
//	@Override
//	public Json sumbitPaper(PaperSubmitInfo info)throws IOException {
//		if(logger.isDebugEnabled()) logger.debug("加载模拟考场试卷基本信息...");
//		if(!info.isSafe())
//		return null;
//		String xml = HttpUtil.httpRequest(this.api_papersubmit_url,"POST",info.createSubmitData(),"utf-8");
//		if(!StringUtils.isEmpty(xml)){
//			return JSONUtil.JsonToObject(xml, Json.class);
//		}
//		return null;
//	}
//	
//	@Override
//	public PaperRecordInfo loadPaperAnalysis(String paperId, String userId,String productId)
//			throws IOException {
//		if(logger.isDebugEnabled()) logger.debug("加载试卷解析...");
//		if(StringUtils.isEmpty(paperId) || StringUtils.isEmpty(userId))
//		return null;
//		String url = String.format(this.api_paperanalysis_url,paperId,userId);
//		String xml = HttpUtil.httpRequest(url,"GET",("productId="+productId),"utf-8");
//		if(!StringUtils.isEmpty(xml)){
//			return JSONUtil.JsonToObject(xml, PaperRecordInfo.class);
//		}
//		return null;
//	}
	/**
	 * 前端分页条件查询
	 * @param info
	 * @param list
	 * @return
	 */
	private DataGrid<FrontPaperInfo> dataGrid(FrontPaperInfo info,List<FrontPaperInfo> list){
		DataGrid<FrontPaperInfo> datagrid = new DataGrid<FrontPaperInfo>();
		List<FrontPaperInfo> result = new ArrayList<FrontPaperInfo>();
		for(FrontPaperInfo paper : list){
			boolean flag = true;
			if(flag && !StringUtils.isEmpty(info.getSubjectId())){
				flag = paper.getSubjectId().equalsIgnoreCase(info.getSubjectId());
			}
			if(flag && !StringUtils.isEmpty(info.getAreaId())){
				flag = paper.getAreaId().equalsIgnoreCase(info.getAreaId());
			}
			if(flag && info.getType() != null){
				flag = paper.getType().equals(info.getType());
			}
			if(flag && info.getYear() != null){
				flag = paper.getYear().equals(info.getYear());
			}
			if(flag){
				result.add(paper);
			}
		}
		int total = result.size();
		int page = 1;//info.getPage()==null?0:info.getPage();
		int rows = 10;//info.getRows()==null?0:info.getRows();
		if(total > 0){
			datagrid.setTotal((long) total);
			Integer totalPage = total%rows==0?total/rows:(total/rows+1);
			page = page > totalPage?totalPage:page;
			if(list.size() <= rows)
			{
				datagrid.setRows(result);
			}else
				datagrid.setRows(result.subList((page-1)*rows, page*rows>total?total:page*rows));
		}
		return datagrid;
	}
	@Override
	public Json sumbitPaper(PaperSubmitInfo info) throws IOException {
		
		return null;
	}
	@Override
	public PaperRecordInfo loadPaperAnalysis(String paperId, String userId,
			String productId) throws IOException {
		
		return null;
	}
}
