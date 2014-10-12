package com.examw.test.front.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.test.front.model.Constant;
import com.examw.test.front.model.library.FrontPaperInfo;
import com.examw.test.front.model.library.PaperInfo;
import com.examw.test.front.model.library.PaperPreview;
import com.examw.test.front.model.library.PaperRecordInfo;
import com.examw.test.front.model.library.PaperSubmitInfo;
import com.examw.test.front.model.library.StructureInfo;
import com.examw.test.front.model.library.StructureItemInfo;
import com.examw.test.front.model.product.FrontProductInfo;
import com.examw.test.front.model.record.UserItemRecordInfo;
import com.examw.test.front.model.record.UserPaperRecordInfo;
import com.examw.test.front.service.IPaperService;
import com.examw.test.front.support.HttpUtil;
import com.examw.test.front.support.JSONUtil;
import com.examw.test.front.support.MethodCacheHelper;

/**
 * 试卷服务接口实现类
 * @author fengwei.
 * @since 2014年9月11日 上午8:26:24.
 */
public class PaperServiceImpl implements IPaperService{
	private static final Logger logger = Logger.getLogger(PaperServiceImpl.class);
	private String api_paperlist_url;
	private String api_paper_detail_url;
	private String api_papersubmit_url;
	private String api_paperanalysis_url;
	private String api_papertype_url;
	private String api_user_paper_records_url;
	private String api_user_paper_record_url;
	private String api_user_paper_record_add_url;
	private MethodCacheHelper cacheHelper;
	/**
	 * 设置 试卷列表数据接口地址
	 * @param api_paperlist_url
	 * 
	 */
	public void setApi_paperlist_url(String api_paperlist_url) {
		this.api_paperlist_url = api_paperlist_url;
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
	
	/**
	 * 设置 用户试卷记录地址
	 * @param api_user_paper_records_url
	 * 
	 */
	public void setApi_user_paper_records_url(String api_user_paper_records_url) {
		this.api_user_paper_records_url = api_user_paper_records_url;
	}
	
	/**
	 * 设置 试卷[带结构带题目]的地址
	 * @param api_paper_detail_url
	 * 
	 */
	public void setApi_paper_detail_url(String api_paper_detail_url) {
		this.api_paper_detail_url = api_paper_detail_url;
	}
	
	/**
	 * 设置 用户试卷记录地址
	 * @param api_user_paper_record_url
	 * 
	 */
	public void setApi_user_paper_record_url(String api_user_paper_record_url) {
		this.api_user_paper_record_url = api_user_paper_record_url;
	}

	/**
	 *  设置 用户试卷记录添加地址
	 * @param api_user_paper_record_add_url
	 */
	public void setApi_user_paper_record_add_url(
			String api_user_paper_record_add_url) {
		this.api_user_paper_record_add_url = api_user_paper_record_add_url;
	}

	/**
	 * 设置 缓存辅助类
	 * @param cacheHelper
	 * 
	 */
	public void setCacheHelper(MethodCacheHelper cacheHelper) {
		this.cacheHelper = cacheHelper;
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
	public List<FrontPaperInfo> loadPaperList(String productId) throws IOException {
		if(logger.isDebugEnabled()) logger.debug("加载模拟考场试卷列表...");
		if(StringUtils.isEmpty(productId))
		return null;
		String url = String.format(this.api_paperlist_url,productId);
		String xml = HttpUtil.httpRequest(url,"GET",null,"utf-8");
		if(!StringUtils.isEmpty(xml)){
			return JSONUtil.JsonToCollection(xml, List.class, FrontPaperInfo.class);
		}
		return null;
	}
	/**
	 * 加载用户最新试卷记录
	 * @param userId
	 * @param productId
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public List<UserPaperRecordInfo> loadUserPaperRecords(String userId,String productId) throws IOException{
		if(logger.isDebugEnabled()) logger.debug("加载模拟考场用户试卷记录列表...");
		if(StringUtils.isEmpty(productId))
		return null;
		String url = String.format(this.api_user_paper_records_url,userId,productId);
		String xml = HttpUtil.httpRequest(url,"GET",null,"utf-8");
		if(!StringUtils.isEmpty(xml)){
			return JSONUtil.JsonToCollection(xml, List.class, UserPaperRecordInfo.class);
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
		PaperPreview paper = (PaperPreview) this.cacheHelper.getCache(PaperPreview.class.getName(), this.getClass().getName()+"loadPaperDetail", new String[]{paperId});
		if(paper == null){
			paper = this.loadPaperDetail(paperId);
			if(paper!=null){
				this.cacheHelper.putCache(PaperPreview.class.getName(), this.getClass().getName()+"loadPaperDetail", new String[]{paperId}, paper);
			}
		}
		return paper;
	}
	/**
	 * 试卷详细情况
	 * @param paperId
	 * @return
	 * @throws IOException
	 */
	private PaperPreview loadPaperDetail(String paperId)throws IOException{
		if(logger.isDebugEnabled()) logger.debug("加载模拟考场试卷基本信息...");
		if(StringUtils.isEmpty(paperId))
		return null;
		String url = String.format(this.api_paper_detail_url,paperId);
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
	public PaperPreview loadPaperDetail(String paperId,String userId,String productId) throws Exception {
		PaperPreview paper = this.loadPaperDetail(paperId);
		//查询用户试卷考试记录,没有记录的话添加记录
		String url = String.format(this.api_user_paper_record_url,userId,paperId);
		String xml = HttpUtil.httpRequest(url,"GET",null,"utf-8");
		Json json = null;
		if(!StringUtils.isEmpty(xml)){
			json = JSONUtil.JsonToObject(xml, Json.class);
		}
		if(json.isSuccess())
		{
			UserPaperRecordInfo info = JSONUtil.JsonToObject((String)json.getData(), UserPaperRecordInfo.class);
			if(info != null){
				//附上用户的答案
				setUserAnswer(paper,info);	
			}else{
				info = changeModel(paper);
				info.setProductId(productId);
				//提交记录
				HttpUtil.upload(this.api_user_paper_record_add_url, info);
			}
		}
		return paper;
	}
	//设置用户的答案
	private void setUserAnswer(PaperPreview paper, UserPaperRecordInfo info) {
		List<StructureInfo> structures = paper.getStructures();
		Set<UserItemRecordInfo> itemRecords = info.getItems();
		if(itemRecords == null || itemRecords.size() ==0 ) return;
		for(StructureInfo s:structures){
			Set<StructureItemInfo> items = s.getItems();
			for(StructureItemInfo item:items){
				setUserAnswer(item,itemRecords);
			}
		}
	}
	//设置用户答案
	private void setUserAnswer(StructureItemInfo item,
			Set<UserItemRecordInfo> itemRecords) {
		switch(item.getType()){
		case Constant.TYPE_SHARE_TITLE:
			setShareTitleItemUserAnswer(item, itemRecords);
			break;
		case Constant.TYPE_SHARE_ANSWER:
			setShareAnswerItemUserAnswer(item, itemRecords);
			break;
		default:
			for(UserItemRecordInfo info : itemRecords)
			{
				if(item.getId().equalsIgnoreCase(info.getItemId())){
					item.setUserAnswer(info.getAnswer());	//设置用户答案
					break;
				}
			}
			break;
		}
	}
	//设置共享题干题的用户答案
	private void setShareTitleItemUserAnswer(StructureItemInfo item,
			Set<UserItemRecordInfo> itemRecords){
		Set<StructureItemInfo> children = item.getChildren();
		for(StructureItemInfo child:children){
			for(UserItemRecordInfo info : itemRecords)
			{
				if(child.getId().equalsIgnoreCase(info.getItemId())){
					child.setUserAnswer(info.getAnswer());	//设置用户答案
					break;
				}
			}
		}
	}
	//设置共享答案题的用户答案
	private void setShareAnswerItemUserAnswer(StructureItemInfo item,
			Set<UserItemRecordInfo> itemRecords){
		TreeSet<StructureItemInfo> set = new TreeSet<>();
		set.addAll(item.getChildren());
		Set<StructureItemInfo> children = set.last().getChildren();
		for(StructureItemInfo child:children){
			for(UserItemRecordInfo info : itemRecords)
			{
				if(child.getId().equalsIgnoreCase(info.getItemId())){
					child.setUserAnswer(info.getAnswer());	//设置用户答案
					break;
				}
			}
		}
	}
	//数据模型转换
	private UserPaperRecordInfo changeModel(PaperPreview paper) {
		if(paper == null)
		return null;
		UserPaperRecordInfo info = new UserPaperRecordInfo();
		BeanUtils.copyProperties(paper, info,new String[]{"id","structures","createTime","lastTime"});
		info.setId(UUID.randomUUID().toString());
		info.setPaperId(paper.getId());	//试卷Id
		info.setUsedTime(0L);
		info.setTerminalCode(123456);
		info.setStatus(Constant.STATUS_UNDONE); //刚加入未完成
		paper.setPaperRecordId(info.getId());	//设置试卷考试记录ID
		return info;
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
			TreeSet<StructureItemInfo> items = new TreeSet<StructureItemInfo>(
					new Comparator<StructureItemInfo>() {
						@Override
						public int compare(StructureItemInfo o1,
								StructureItemInfo o2) {
							return o1.getOrderNo() - o2.getOrderNo();
						}
					});
			items.addAll(s.getItems());
			if(items == null || items.size()==0) continue;
			for(StructureItemInfo item : items){
				if(item.getType().equals(Constant.TYPE_SHARE_TITLE)){
					result.addAll(getShareTitleSortedChildrenList(item)); 
				}
				else if(item.getType().equals(Constant.TYPE_SHARE_ANSWER)){
					result.addAll(getShareAnswerSortedChildrenList(item));
				}
				else{
					result.add(item);
				}
			}
		}
		return result;
	};
	/*
	 * 获取共享题干题按序子题集合
	 */
	private List<StructureItemInfo> getShareTitleSortedChildrenList(StructureItemInfo item) {
		List<StructureItemInfo> list = new ArrayList<StructureItemInfo>();
		TreeSet<StructureItemInfo> set = new TreeSet<StructureItemInfo>();
		set.addAll(item.getChildren());
		for(StructureItemInfo info : set){
			list.add(info);
		}
		return list;
	}
	/*
	 * 获取共享答案题按序子题集合
	 */
	private List<StructureItemInfo> getShareAnswerSortedChildrenList(StructureItemInfo item) {
		List<StructureItemInfo> list = new ArrayList<StructureItemInfo>();
		TreeSet<StructureItemInfo> set = new TreeSet<StructureItemInfo>();
		set.addAll(item.getChildren());
		StructureItemInfo last = set.last();	//最后一个
		set.clear();
		set.addAll(last.getChildren());
		for(StructureItemInfo info : set){
			info.setPid(last.getPid());
			list.add(info);
		}
		return list;
	}
	/*
	 * 加载按题型的子题集合
	 * @see com.examw.test.front.service.IPaperService#loadBigItemsList(com.examw.test.front.model.library.PaperPreview)
	 */
	@Override
	public List<StructureItemInfo> loadBigItemsList(PaperPreview paper)
			throws IOException {
		if(paper == null) return null;
		List<StructureItemInfo> result = new ArrayList<StructureItemInfo>();
		List<StructureInfo> structures = paper.getStructures();
		if(structures == null || structures.size()==0) return result;
		for(StructureInfo s:structures){
			TreeSet<StructureItemInfo> items = new TreeSet<StructureItemInfo>(
					new Comparator<StructureItemInfo>() {
						@Override
						public int compare(StructureItemInfo o1,
								StructureItemInfo o2) {
							return o1.getOrderNo() - o2.getOrderNo();
						}
					});
			items.addAll(s.getItems());
			if(items == null || items.size()==0) continue;
			for(StructureItemInfo item : items){
				result.add(item);
			}
		}
		return result;
	}
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
	@Override
	public PaperRecordInfo loadPaperAnalysis(String paperId, String userId,String productId)
			throws IOException {
		if(logger.isDebugEnabled()) logger.debug("加载试卷解析...");
		if(StringUtils.isEmpty(paperId) || StringUtils.isEmpty(userId))
		return null;
		String url = String.format(this.api_paperanalysis_url,paperId,userId);
		String xml = HttpUtil.httpRequest(url,"GET",("productId="+productId),"utf-8");
		if(!StringUtils.isEmpty(xml)){
			return JSONUtil.JsonToObject(xml, PaperRecordInfo.class);
		}
		return null;
	}
	
	/**
	 * 前端分页条件查询
	 * @param info
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public DataGrid<FrontPaperInfo> dataGrid(String productId,PaperInfo info,String userId)throws IOException{
		if(logger.isDebugEnabled()) logger.debug("加载试卷分页列表信息...");
		Integer page = info.getPage()==null?1:info.getPage();
		Integer rows = info.getRows()==null?10:info.getRows();
		//从缓存中取所有的试卷
		List<FrontPaperInfo> list = (List<FrontPaperInfo>) this.cacheHelper.getCache(FrontPaperInfo.class.getName(), this.getClass().getName()+"loadPaperList", new Object[]{productId});
		if(list == null){
			list = this.loadPaperList(productId);
			if(list!=null)
				this.cacheHelper.putCache(FrontProductInfo.class.getName(), this.getClass().getName()+"loadPaperList", new Object[]{productId}, list);
		}
		//从缓存中取用户试卷最新记录
		List<UserPaperRecordInfo> records = (List<UserPaperRecordInfo>) this.cacheHelper.getCache(UserPaperRecordInfo.class.getName(), this.getClass().getName()+"loadUserPaperRecords", new Object[]{userId,productId});
		if(records == null){
			records = this.loadUserPaperRecords(userId, productId);
			if(records!=null)
				this.cacheHelper.putCache(UserPaperRecordInfo.class.getName(), this.getClass().getName()+"loadUserPaperRecords", new Object[]{userId,productId}, records);
		}
		DataGrid<FrontPaperInfo> datagrid = new DataGrid<FrontPaperInfo>();
		List<FrontPaperInfo> result = new ArrayList<FrontPaperInfo>();
		//按条件筛选
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
		if(total > 0){
			datagrid.setTotal((long) total);
			Integer totalPage = total%rows==0?total/rows:(total/rows+1);
			page = page > totalPage?totalPage:page;
			if(list.size() <= rows)
			{
				setUserRecordInfo(result,records);
				datagrid.setRows(result);
			}else{
				List<FrontPaperInfo> subList = result.subList((page-1)*rows, page*rows>total?total:page*rows);
				setUserRecordInfo(subList,records);
				datagrid.setRows(subList);
			}
		}
		return datagrid;
	}
	
	/**
	 * 设置用户考试记录信息
	 * @param result
	 * @param records
	 */
	private void setUserRecordInfo(List<FrontPaperInfo> result,List<UserPaperRecordInfo> records) {
		if(result == null || result.size() == 0) return;
		if(records == null || records.size() == 0) return;
		for(FrontPaperInfo info:result){
			for(UserPaperRecordInfo record:records){
				if(info.getId().equalsIgnoreCase(record.getPaperId())){
					info.setUserScore(record.getScore());//用户得分
					info.setUsedTime(record.getUsedTime()); //使用时间
					info.setExamTime(record.getCreateTime());
					info.setExamStatus(record.getStatus());
					break;
				}
			}
		}
	}
	@Override
	public Json sumbitPaper(PaperSubmitInfo info) throws Exception {
		//评判分
		return HttpUtil.upload(api_papersubmit_url, info);
	}
}
