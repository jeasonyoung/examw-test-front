package com.examw.test.front.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
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
import com.examw.test.front.model.library.PaperSubmitInfo;
import com.examw.test.front.model.library.StructureInfo;
import com.examw.test.front.model.library.StructureItemInfo;
import com.examw.test.front.model.product.FrontProductInfo;
import com.examw.test.front.model.record.ItemRecord;
import com.examw.test.front.model.record.UserItemFavoriteInfo;
import com.examw.test.front.model.record.UserItemRecordInfo;
import com.examw.test.front.model.record.UserPaperRecordInfo;
import com.examw.test.front.service.IPaperService;
import com.examw.test.front.service.IRemoteService;
import com.examw.test.front.support.DateUtil;
import com.examw.test.front.support.JSONUtil;
import com.examw.test.front.support.MethodCacheHelper;

/**
 * 试卷服务接口实现类
 * @author fengwei.
 * @since 2014年9月11日 上午8:26:24.
 */
public class PaperServiceImpl implements IPaperService{
	private static final Logger logger = Logger.getLogger(PaperServiceImpl.class);
	//试卷列表数据接口地址
	private String api_paperlist_url;
	//试卷详情数据远程地址
	private String api_paper_detail_url;
	//试卷类型数据远程地址
	private String api_papertype_url;
	//用户考试记录集合数据远程地址
	private String api_user_paper_records_url;
	//用户考试记录数据远程地址
	private String api_user_paper_lasted_record_url;
	//用户考试记录数据远程地址
	private String api_user_paper_record_url;
	//用户考试记录数据添加远程地址
	private String api_user_paper_record_add_url;
	//用户收藏数据集合远程地址
	private String api_user_collections_url;
	//每日一练试卷列表
	private String api_daily_papers_url;
	//今日一练未做的个数
	private String api_daily_papers_undone_number_url;
	//web终端代码
	private Integer web_terminal_code;
	//数据缓存帮助类
	private MethodCacheHelper cacheHelper;
	private IRemoteService remoteService;
	/**
	 * 设置 远程服务
	 * @param remoteService
	 * 
	 */
	public void setRemoteService(IRemoteService remoteService) {
		this.remoteService = remoteService;
	}
	/**
	 * 设置 试卷列表数据接口地址
	 * @param api_paperlist_url
	 * 
	 */
	public void setApi_paperlist_url(String api_paperlist_url) {
		this.api_paperlist_url = api_paperlist_url;
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
	 * @param api_user_paper_lasted_record_url
	 * 
	 */
	public void setApi_user_paper_lasted_record_url(
			String api_user_paper_lasted_record_url) {
		this.api_user_paper_lasted_record_url = api_user_paper_lasted_record_url;
	}

	/**
	 * 设置  用户试卷记录地址
	 * @param api_user_paper_record_url
	 * 
	 */
	public void setApi_user_paper_record_url(String api_user_paper_record_url) {
		this.api_user_paper_record_url = api_user_paper_record_url;
	}

	/**
	 * 设置 每日一练试卷的地址
	 * @param api_daily_papers_url
	 * 
	 */
	public void setApi_daily_papers_url(String api_daily_papers_url) {
		this.api_daily_papers_url = api_daily_papers_url;
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
	 * 设置 用户收藏地址
	 * @param api_user_collections_url
	 * 
	 */
	public void setApi_user_collections_url(String api_user_collections_url) {
		this.api_user_collections_url = api_user_collections_url;
	}
	/**
	 * 设置 
	 * @param api_daily_papers_undone_number_url
	 * 
	 */
	public void setApi_daily_papers_undone_number_url(
			String api_daily_papers_undone_number_url) {
		this.api_daily_papers_undone_number_url = api_daily_papers_undone_number_url;
	}

	/**
	 * 设置 web终端代码
	 * @param web_terminal_code
	 * 
	 */
	public void setWeb_terminal_code(Integer web_terminal_code) {
		if(logger.isDebugEnabled()) logger.debug(String.format("注入web终端代码[%1$s]...",web_terminal_code));
		this.web_terminal_code = web_terminal_code;
	}
	/**
	 * 设置 缓存辅助类
	 * @param cacheHelper
	 * 
	 */
	public void setCacheHelper(MethodCacheHelper cacheHelper) {
		this.cacheHelper = cacheHelper;
	}
	/*
	 * 加载试卷类型数据
	 * @see com.examw.test.front.service.IPaperService#loadPaperType()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> loadPaperType() throws Exception {
		String xml = remoteService.httpRequest(this.api_papertype_url, "GET", null, "utf-8");
		if(StringUtils.isEmpty(xml)) return null;
		return JSONUtil.JsonToCollection(xml, Map.class, String.class,String.class);
	}
	/*
	 * 加载试卷列表
	 * @see com.examw.test.front.service.IPaperService#loadPaperList(java.lang.String, com.examw.test.front.model.PaperInfo)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FrontPaperInfo> loadPaperList(String productId) throws Exception {
		if(logger.isDebugEnabled()) logger.debug("加载模拟考场试卷列表...");
		if(StringUtils.isEmpty(productId))
		return null;
		String url = String.format(this.api_paperlist_url,productId);
		String xml = remoteService.httpRequest(url,"GET",null,"utf-8");
		if(!StringUtils.isEmpty(xml)){
			return JSONUtil.JsonToCollection(xml, List.class, FrontPaperInfo.class);
		}
		return null;
	}
	/*
	 * 加载试卷的年份
	 * @see com.examw.test.front.service.IPaperService#loadPaperYear(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> loadPaperYear(String productId) throws Exception {
		List<FrontPaperInfo> list = (List<FrontPaperInfo>) this.cacheHelper.getCache(FrontPaperInfo.class.getName(), this.getClass().getName()+"loadPaperList", new Object[]{productId});
		if(list == null){
			list = this.loadPaperList(productId);
			if(list!=null)
				this.cacheHelper.putCache(FrontProductInfo.class.getName(), this.getClass().getName()+"loadPaperList", new Object[]{productId}, list);
		}
		List<Integer> result = new ArrayList<Integer>();
		if(list == null || list.size()==0) return result;
		Map<Integer,Integer> map = new HashMap<Integer,Integer>();
		for(FrontPaperInfo info:list){
			if(info==null) continue;
			map.put(info.getYear(), 1);
		}
		result.addAll(map.keySet());
		return result;
	}
	/**
	 * 加载用户最新试卷记录
	 * @param userId
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<UserPaperRecordInfo> findUserPaperRecords(String userId,String productId) throws Exception{
		if(logger.isDebugEnabled()) logger.debug("加载模拟考场用户试卷记录列表...");
		if(StringUtils.isEmpty(productId))
		return null;
		String url = String.format(this.api_user_paper_records_url,userId,productId);
		String xml = remoteService.httpRequest(url,"GET",null,"utf-8");
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
	public PaperPreview loadPaperInfo(String paperId) throws Exception {
		if(logger.isDebugEnabled()) logger.debug("加载模拟考场试卷基本信息...");
		return this.findPaperDetail(paperId);
	}
	/**
	 * 试卷详细情况
	 * @param paperId
	 * @return
	 * @throws Exception
	 */
	public PaperPreview findPaperDetail(String paperId)throws Exception{
		if(logger.isDebugEnabled()) logger.debug("加载模拟考场试卷基本信息...");
		if(StringUtils.isEmpty(paperId))
		return null;
		PaperPreview paper = (PaperPreview) this.cacheHelper.getCache(PaperPreview.class.getName(), this.getClass().getName()+"loadPaperDetail", new String[]{paperId});
		if(paper == null){
			String url = String.format(this.api_paper_detail_url,paperId);
			String xml = remoteService.httpRequest(url,"GET",null,"utf-8");
			if(!StringUtils.isEmpty(xml)){
				paper = JSONUtil.JsonToObject(xml, PaperPreview.class);
			}
			if(paper!=null){
				this.cacheHelper.putCache(PaperPreview.class.getName(), this.getClass().getName()+"loadPaperDetail", new String[]{paperId}, paper);
			}
		}
		return paper;
	}
	/*
	 * 加载试卷详情[带试题]
	 * @see com.examw.test.front.service.IPaperService#loadPaperDetail(java.lang.String)
	 */
	@Override
	public PaperPreview findPaperDetail(String paperId,String userId,String productId) throws Exception {
		PaperPreview paper = this.findPaperDetail(paperId);
		//查询用户试卷考试记录,没有记录的话添加记录
		UserPaperRecordInfo info = this.findLastedRecord(userId, paperId);
		if(info != null && info.getStatus().equals(Constant.STATUS_UNDONE)){
			//附上用户的答案
			setUserAnswer(paper,info,null);	
		}else{
			info = changeModel(paper);
			info.setProductId(productId);
			info.setUserId(userId);
			//提交记录
			remoteService.upload(this.api_user_paper_record_add_url, info);
			//试卷有缓存,清除对象题目的答案
			clearUserAnswer(paper);
			
		}
		paper.setPaperRecordId(info.getId());	//设置试卷考试记录ID
		if(paper.getTime()!=null)
			paper.setLeftTime(paper.getTime()*60 - info.getUsedTime());	//设置剩余考试时间
		else
			paper.setLeftTime(info.getUsedTime());
		return paper;
	}
	/**
	 * 获取最新的考试记录		[去掉了缓存]
	 * @param userId
	 * @param paperId
	 * @return
	 * @throws Exception
	 */
	public UserPaperRecordInfo findLastedRecord(String userId,String paperId)throws Exception{
		//UserPaperRecordInfo info = (UserPaperRecordInfo) this.cacheHelper.getCache(UserPaperRecordInfo.class.getName(), this.getClass().getName()+"loadLastedRecord", new String[]{userId,paperId});
		UserPaperRecordInfo info = null;
		if(info == null){
			String url = String.format(this.api_user_paper_lasted_record_url,userId,paperId);
			String xml = remoteService.httpRequest(url,"GET",null,"utf-8");
			Json json = null;
			if(!StringUtils.isEmpty(xml)){
				json = JSONUtil.JsonToObject(xml, Json.class);
			}
			if(json.isSuccess())
				info = JSONUtil.JsonToObject((String)json.getData(), UserPaperRecordInfo.class);
//			if(info!=null){
//				this.cacheHelper.putCache(UserPaperRecordInfo.class.getName(), this.getClass().getName()+"loadLastedRecord", new String[]{userId,paperId}, info);
//			}
		}
		return info;
	}
	public UserPaperRecordInfo findRecordById(String userId,String recordId)throws Exception{
		//UserPaperRecordInfo info = (UserPaperRecordInfo) this.cacheHelper.getCache(UserPaperRecordInfo.class.getName(), this.getClass().getName()+"loadLastedRecord", new String[]{userId,paperId});
		if(StringUtils.isEmpty(recordId)) return null;
		UserPaperRecordInfo info = null;
		if(info == null){
			String url = String.format(this.api_user_paper_record_url,userId,recordId);
			String xml = remoteService.httpRequest(url,"GET",null,"utf-8");
			Json json = null;
			if(!StringUtils.isEmpty(xml)){
				json = JSONUtil.JsonToObject(xml, Json.class);
			}
			if(json.isSuccess())
				info = JSONUtil.JsonToObject((String)json.getData(), UserPaperRecordInfo.class);
//			if(info!=null){
//				this.cacheHelper.putCache(UserPaperRecordInfo.class.getName(), this.getClass().getName()+"loadLastedRecord", new String[]{userId,paperId}, info);
//			}
		}
		return info;
	}
	/*
	 * 查询产品下的最新考试记录
	 * @see com.examw.test.front.service.IPaperService#findProductLastedRecord(java.lang.String, java.lang.String)
	 */
	@Override
	public UserPaperRecordInfo findProductLastedRecord(String userId,
			String productId) throws Exception {
		if(logger.isDebugEnabled()) logger.debug("加载产品下最新的试卷记录");
		List<UserPaperRecordInfo> list =findUserPaperRecords(userId,productId);
		if(list !=null && list.size()>0)
		{
			return list.get(0);
		}
		return null;
	}
	//设置用户的答案
	private void setUserAnswer(PaperPreview paper, UserPaperRecordInfo info,List<UserItemFavoriteInfo> favors) {
		List<StructureInfo> structures = paper.getStructures();
		TreeSet<UserItemRecordInfo> itemRecords = new TreeSet<>();
		if(info.getItems() == null || info.getItems().size() ==0 ) return;
		itemRecords.addAll(info.getItems());
		for(StructureInfo s:structures){
			this.setUserAnswer(s, itemRecords, favors);
		}
	}
	//设置用户答案
	private void setUserAnswer(StructureInfo info,TreeSet<UserItemRecordInfo> itemRecords,List<UserItemFavoriteInfo> favors)
	{
		if(info.getChildren()!=null && info.getChildren().size()>0)
		{
			for(StructureInfo child:info.getChildren())
			{
				this.setUserAnswer(child, itemRecords, favors);
			}
		}else
		{
			Set<StructureItemInfo> items = info.getItems();
			if(items!=null)
			{
				for(StructureItemInfo item:items){
					setUserAnswer(item,itemRecords,favors);
				}
			}
		}
	}
	//设置用户答案
	private void setUserAnswer(StructureItemInfo item,
			TreeSet<UserItemRecordInfo> itemRecords,List<UserItemFavoriteInfo> favors) {
		switch(item.getType()){
		case Constant.TYPE_SHARE_TITLE:
			setShareTitleItemUserAnswer(item, itemRecords,favors);
			break;
		case Constant.TYPE_SHARE_ANSWER:
			setShareAnswerItemUserAnswer(item, itemRecords,favors);
			break;
		default:
			for(UserItemRecordInfo info : itemRecords)
			{
				if(item.getId().equalsIgnoreCase(info.getItemId())){
					item.setUserAnswer(info.getAnswer());	//设置用户答案
					item.setUserScore(info.getScore());
					item.setAnswerStatus(info.getStatus());
					item.setRecordId(info.getId());
					break;
				}
			}
			//判断是否被收藏
			isCollected(item.getId(), item, favors);
			break;
		}
	}
	//设置共享题干题的用户答案
	private void setShareTitleItemUserAnswer(StructureItemInfo item,
			Set<UserItemRecordInfo> itemRecords,List<UserItemFavoriteInfo> favors){
		Set<StructureItemInfo> children = item.getChildren();
		for(StructureItemInfo child:children){
			for(UserItemRecordInfo info : itemRecords)
			{
				if((item.getId()+"#"+child.getId()).equalsIgnoreCase(info.getItemId())){
					child.setUserAnswer(info.getAnswer());	//设置用户答案
					child.setUserScore(info.getScore());
					child.setAnswerStatus(info.getStatus());
					child.setRecordId(info.getId());
					break;
				}
			}
			//判断是否被收藏
			isCollected(item.getId()+"#"+child.getId(), child, favors);
		}
	}
	//设置共享答案题的用户答案
	private void setShareAnswerItemUserAnswer(StructureItemInfo item,
			Set<UserItemRecordInfo> itemRecords,List<UserItemFavoriteInfo> favors){
		TreeSet<StructureItemInfo> set = new TreeSet<>();
		set.addAll(item.getChildren());
		Set<StructureItemInfo> children = set.last().getChildren();
		for(StructureItemInfo child:children){
			for(UserItemRecordInfo info : itemRecords)
			{
				if((item.getId()+"#"+child.getId()).equalsIgnoreCase(info.getItemId())){
					child.setUserAnswer(info.getAnswer());	//设置用户答案
					child.setUserScore(info.getScore());
					child.setAnswerStatus(info.getStatus());
					child.setRecordId(info.getId());
					break;
				}
			}
			//判断是否被收藏
			isCollected(item.getId()+"#"+child.getId(), child, favors);
		}
	}
	//清除用户的答案
	private void clearUserAnswer(PaperPreview paper){
		if(logger.isDebugEnabled()) logger.debug("再做一次,清除用户的答案...");
		List<StructureInfo> structures = paper.getStructures();
		for(StructureInfo s:structures){
			this.clearUserAnswer(s);
		}
	}
	//清除用户的答案
	private void clearUserAnswer(StructureInfo info)
	{
		if(info.getChildren()!=null && info.getChildren().size()>0)
		{
			for(StructureInfo child:info.getChildren())
			{
				this.clearUserAnswer(child);
			}
		}else
		{
			Set<StructureItemInfo> items = info.getItems();
			if(items != null)
			{
				for(StructureItemInfo item:items){
					this.clearUserAnswer(item);
				}
			}
		}
	}
	//清除用户的答案
	private void clearUserAnswer(StructureItemInfo item){
		switch(item.getType()){
		case Constant.TYPE_SHARE_TITLE:	//共享题干题
			Set<StructureItemInfo> children = item.getChildren();
			for(StructureItemInfo child:children){
				child.setUserAnswer(null);	//清除用户答案
				child.setUserScore(null);
				child.setAnswerStatus(null);
				child.setRecordId(null);
			}
			break;
		case Constant.TYPE_SHARE_ANSWER: //共享答案题
			TreeSet<StructureItemInfo> set = new TreeSet<>();
			set.addAll(item.getChildren());
			Set<StructureItemInfo> children2 = set.last().getChildren();
			for(StructureItemInfo child:children2){
				child.setUserAnswer(null);	//清除用户答案
				child.setUserScore(null);
				child.setAnswerStatus(null);
				child.setRecordId(null);
			}
			break;
		default:
			item.setUserAnswer(null);	//清除用户答案
			item.setUserScore(null);
			item.setAnswerStatus(null);
			item.setRecordId(null);
			break;
		}
	}
	//判断是否被收藏
	private void isCollected(String itemId,StructureItemInfo item,List<UserItemFavoriteInfo> favors){
		if(favors == null || favors.size() == 0) return ;
		for(UserItemFavoriteInfo favor:favors){
			if(itemId.equalsIgnoreCase(favor.getItemId())){
				item.setIsCollected(true);
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
		info.setTerminalCode(web_terminal_code);
		info.setStatus(Constant.STATUS_UNDONE); //刚加入未完成
		info.setScore(BigDecimal.ZERO);
		return info;
	}

	/*
	 * 从试卷详情中把试题集合择出来
	 * @see com.examw.test.front.service.IPaperService#loadItemsList(com.examw.test.front.model.PaperPreview)
	 */
	@Override
	public java.util.List<com.examw.test.front.model.library.StructureItemInfo> findItemsList(PaperPreview paper) throws Exception {
		if(paper == null) return null;
		List<StructureItemInfo> result = new ArrayList<StructureItemInfo>();
		List<StructureInfo> structures = paper.getStructures();
		if(structures == null || structures.size()==0) return result;
		for(StructureInfo s:structures){
			if(s == null) continue;
			this.getStructureItems(result, s, false);
		}
		return result;
	};
	private void getStructureItems(List<StructureItemInfo> result,StructureInfo info,boolean isBig)
	{
		if(info.getChildren()!=null && info.getChildren().size()>0)
		{
			for(StructureInfo child:info.getChildren())
			{
				this.getStructureItems(result, child,isBig);
			}
		}else{
			TreeSet<StructureItemInfo> items = new TreeSet<StructureItemInfo>();
			if(info.getItems() == null || info.getItems().size()==0) return;
			items.addAll(info.getItems());
			for(StructureItemInfo item : items){
				if(isBig)
				{
					result.add(item);
				}else{
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
		}
	}
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
	public List<StructureItemInfo> findBigItemsList(PaperPreview paper)
			throws Exception {
		if(paper == null) return null;
		List<StructureItemInfo> result = new ArrayList<StructureItemInfo>();
		List<StructureInfo> structures = paper.getStructures();
		if(structures == null || structures.size()==0) return result;
		for(StructureInfo s:structures){
			if(s == null) continue;
			this.getStructureItems(result, s, true);
		}
		return result;
	}
//	
//	@Override
//	public Json sumbitPaper(PaperSubmitInfo info)throws Exception {
//		if(logger.isDebugEnabled()) logger.debug("加载模拟考场试卷基本信息...");
//		if(!info.isSafe())
//		return null;
//		String xml = remoteService.httpRequest(this.api_papersubmit_url,"POST",info.createSubmitData(),"utf-8");
//		if(!StringUtils.isEmpty(xml)){
//			return JSONUtil.JsonToObject(xml, Json.class);
//		}
//		return null;
//	}
//	
	@Override
	public PaperPreview findPaperAnalysis(String paperId, String userId,String productId) throws Exception {
		UserPaperRecordInfo info = this.findLastedRecord(userId, paperId);
		if(info == null){
			throw new RuntimeException("没有考试记录");
		}else if(info.getStatus().equals(Constant.STATUS_UNDONE)){
			if(!(info.getPaperType().equals(Constant.PAPER_TYPE_DAILY)||info.getPaperType().equals(Constant.PAPER_TYPE_CHAPTER)) )
			throw new RuntimeException("考试未完成");
		}
		PaperPreview paper = this.findPaperDetail(paperId);
		List<UserItemFavoriteInfo> list = this.loadItemFavorites(userId);
		setUserAnswer(paper,info,list);	//附上用户答案
		paper.setUserScore(info.getScore());
		return paper;
	}
	
	public PaperPreview findPaperAnalysis(String paperId,String recordId,String userId,String productId) throws Exception {
		UserPaperRecordInfo info = this.findRecordById(userId,recordId);
		if(info == null){
			throw new RuntimeException("没有考试记录");
		}else if(info.getStatus().equals(Constant.STATUS_UNDONE)){
			if(!(info.getPaperType().equals(Constant.PAPER_TYPE_DAILY)||info.getPaperType().equals(Constant.PAPER_TYPE_CHAPTER)))
			throw new RuntimeException("考试未完成");
		}
		PaperPreview paper = this.findPaperDetail(paperId);
		List<UserItemFavoriteInfo> list = this.loadItemFavorites(userId);
		setUserAnswer(paper,info,list);	//附上用户答案
		paper.setUserScore(info.getScore());
		return paper;
	}
	@SuppressWarnings("unchecked")
	private List<UserItemFavoriteInfo> loadItemFavorites(String userId) throws Exception{
		if(logger.isDebugEnabled()) logger.debug("加载用户收藏...");
		if(StringUtils.isEmpty(userId))
			return null;
		List<UserItemFavoriteInfo> list = (List<UserItemFavoriteInfo>) this.cacheHelper.getCache(UserItemFavoriteInfo.class.getName(), this.getClass().getName()+"loadItemFavorites", new String[]{userId});
			if(list == null){
				String url = String.format(this.api_user_collections_url,userId);
				String xml = remoteService.httpRequest(url,"GET",null,"utf-8");
				if(!StringUtils.isEmpty(xml)){
					list = JSONUtil.JsonToCollection(xml, List.class, UserItemFavoriteInfo.class);
				}
				if(list!=null){
					this.cacheHelper.putCache(UserItemFavoriteInfo.class.getName(), this.getClass().getName()+"loadItemFavorites", new String[]{userId}, list);
				}
			}
		return list;
	}
	/**
	 * 前端分页条件查询
	 * @param info
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public DataGrid<FrontPaperInfo> dataGrid(String productId,PaperInfo info,String userId)throws Exception{
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
		//从缓存中取用户试卷最新记录 [取消缓存]
		/*List<UserPaperRecordInfo> records = (List<UserPaperRecordInfo>) this.cacheHelper.getCache(UserPaperRecordInfo.class.getName(), this.getClass().getName()+"loadUserPaperRecords", new Object[]{userId,productId});
		if(records == null){
			records = this.findUserPaperRecords(userId, productId);
			if(records!=null)
				this.cacheHelper.putCache(UserPaperRecordInfo.class.getName(), this.getClass().getName()+"loadUserPaperRecords", new Object[]{userId,productId}, records);
		}*/
		List<UserPaperRecordInfo> records = this.findUserPaperRecords(userId, productId);
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
		//排序，创建时间倒序
		if(result.size() > 0){
			Collections.sort(result, new Comparator<FrontPaperInfo>(){
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				@Override
				public int compare(FrontPaperInfo o1, FrontPaperInfo o2) {
					try{
						final Date d1 = dateFormat.parse(o1.getCreateTime());
						final Date d2 = dateFormat.parse(o2.getCreateTime());
						return d2.compareTo(d1);
					}catch(Exception e){}
					return o2.getCreateTime().compareToIgnoreCase(o1.getCreateTime());
				}
			});
		}
		//
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
			this.setUserRecordInfo(info, records);
		}
	}
	//设置用户的考试记录
	private void setUserRecordInfo(FrontPaperInfo info,List<UserPaperRecordInfo> records)
	{
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
	/*
	 * 提交试卷
	 * @see com.examw.test.front.service.IPaperService#sumbitPaper(com.examw.test.front.model.library.PaperSubmitInfo)
	 */
	@Override
	public Json sumbitPaper(PaperSubmitInfo info) throws Exception {
		if(logger.isDebugEnabled()) logger.debug("提交试卷...");
		PaperPreview paper = this.findPaperDetail(info.getPaperId());
		UserPaperRecordInfo record = this.findLastedRecord(info.getUserId(), info.getPaperId());
		if(record == null) {
			throw new RuntimeException("找不到记录");
		}
		Integer model = info.getModel();
		record.setStatus(model);
		if(paper.getTime()!=null)
			record.setUsedTime((long)(paper.getTime() * 60 - info.getLimitTime())); // 使用时间
		else
			record.setUsedTime((long)info.getLimitTime());
		String chooseAnswers = info.getChooseAnswers();
		Set<UserItemRecordInfo> itemRecords = new TreeSet<UserItemRecordInfo>();
		Set<UserItemRecordInfo> itemRecordsNeedSave = new TreeSet<UserItemRecordInfo>();
		if (!StringUtils.isEmpty(chooseAnswers)) {
			if(logger.isDebugEnabled()) logger.debug("解析选择题答案字符串...."+chooseAnswers);
			try {
				chooseAnswers = URLDecoder.decode(chooseAnswers, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			String[] answers = chooseAnswers.split("&");// 拆分题目与答案
			for (String s : answers) {
				String[] arr = s.split("=");
				UserItemRecordInfo itemRecord = null;
				if (!arr[0].contains("#"))
				{
					itemRecord = this.changeModel(this.getStructureItemInfo(paper, arr[0]),null);
				}
				else
				{
					String[] p_c_id = arr[0].split("#");
					itemRecord = this.changeModel(this.getStructureItemInfo(paper, p_c_id[0]),p_c_id[1]);
				}
				if(itemRecord!=null){
					itemRecord.setId(UUID.randomUUID().toString());
					itemRecord.setLastTime(new Date());
					if (arr.length > 1) { // 没有答案
						itemRecord.setAnswer(arr[1]);
					}
					itemRecords.add(itemRecord);
					if(isAnswerChanged(itemRecord,record.getItems())){
						itemRecordsNeedSave.add(itemRecord);
					}
				}
			}
		}
		if (!StringUtils.isEmpty(info.getTextAnswers())) {
			String textAnswers = info.getTextAnswers();
			if(logger.isDebugEnabled()) logger.debug("解析问答题答案字符串...."+textAnswers);
			String[] answers = textAnswers.split("&");// 拆分题目与答案
			for (String s : answers) {
				String[] arr = s.split("=");
				UserItemRecordInfo itemRecord = null;
				if (!arr[0].contains("#"))
				{
					itemRecord = this.changeModel(this.getStructureItemInfo(paper, arr[0]),null);
				}
				else
				{
					String[] p_c_id = arr[0].split("#");
					itemRecord = this.changeModel(this.getStructureItemInfo(paper, p_c_id[0]),p_c_id[1]);
				}
				
				if(itemRecord!=null){
					itemRecord.setId(UUID.randomUUID().toString());
					itemRecord.setLastTime(new Date());
					itemRecord.setScore(BigDecimal.ZERO); //设置分数为0[问答题没办法判分]	
					if (arr.length > 1) { // 没有答案
						itemRecord.setAnswer(URLDecoder.decode(arr[1], "utf-8"));
					}
					itemRecords.add(itemRecord);
					if(isAnswerChanged(itemRecord,record.getItems())){
						itemRecordsNeedSave.add(itemRecord);
					}
				}
			}
		}
		record.setItems(itemRecordsNeedSave);
		judgePaper(paper,record, itemRecords);
		if (!model.equals(Constant.STATUS_DONE)) {
			record.setScore(null);
		}
		//如果是章节练习或者每日一练
		if(paper.getType().equals(Constant.PAPER_TYPE_CHAPTER) || paper.getType().equals(Constant.PAPER_TYPE_DAILY)){
			if(itemRecords.size() == paper.getTotal().intValue())
				record.setStatus(Constant.STATUS_DONE);
			else
				record.setStatus(Constant.STATUS_UNDONE);
			record.setRightNum(record.getScore().intValue());
			record.setScore(new BigDecimal(itemRecords.size()));
		}
		//时间提交过去有问题
		record.setCreateTime(null);
		record.setLastTime(null);
		return remoteService.upload(api_user_paper_record_add_url, record);
	}
	//根据ID获取试题信息
	private StructureItemInfo getStructureItemInfo(PaperPreview paper,String itemId){
		List<StructureInfo> structures = paper.getStructures();
		for(StructureInfo s:structures){
			StructureItemInfo result = this.findStructureItemInfo(s, itemId);
			if(result == null) continue;
			else return result;
		}
		return null;
	}
	//获取试卷结构试题
	private StructureItemInfo findStructureItemInfo(StructureInfo info,String itemId)
	{
		if(info.getChildren()!=null && info.getChildren().size()>0)
		{
			for(StructureInfo child : info.getChildren())
			{
				StructureItemInfo result = this.findStructureItemInfo(child, itemId);
				if(result == null) continue;
				else return result;
			}
			return null;
		}else{
			Set<StructureItemInfo> items = info.getItems();
			if(items == null || items.size()==0) return null;
			for(StructureItemInfo item:items){
				if(item.getId().equalsIgnoreCase(itemId)){
					return item;
				}
			}
			return null;
		}
	}
	/**
	 * 模型转换
	 * @param info
	 * @param childItemId
	 * @return
	 */
	private UserItemRecordInfo changeModel(StructureItemInfo info,String childItemId){
		if(info == null) return null;
		if(childItemId == null)
		{
			UserItemRecordInfo data = new UserItemRecordInfo();
			BeanUtils.copyProperties(info, data, new String[]{"createTime","lastTime"});
			data.setItemId(info.getId());
			data.setItemContent(JSONUtil.ObjectToJson(info));
			data.setTerminalCode(web_terminal_code);
			return data;
		}
		else{
			Set<StructureItemInfo> items = info.getChildren();	//子题目
			if(info.getType().equals(Constant.TYPE_SHARE_TITLE)){
				for(StructureItemInfo item:items){
					if(item.getId().equalsIgnoreCase(childItemId)){
						item.setParentContent(info.getContent());
						UserItemRecordInfo result = this.changeModel(item, null);
						result.setStructureId(info.getStructureId());
						result.setItemId(info.getId()+"#"+item.getId());
						return result;
					}
				}
			}else if(info.getType().equals(Constant.TYPE_SHARE_ANSWER)){
				TreeSet<StructureItemInfo> set = new TreeSet<StructureItemInfo>();
				set.addAll(info.getChildren());
				Set<StructureItemInfo> children = set.last().getChildren();	//子题目
				String content = getShareAnswerContent(info,set);
				for(StructureItemInfo item:children){
					if(item.getId().equalsIgnoreCase(childItemId)){
						item.setParentContent(content);
						UserItemRecordInfo result = this.changeModel(item, null);
						result.setStructureId(info.getStructureId());
						result.setItemId(info.getId()+"#"+item.getId());
						return result;
					}
				}
			}
			return null;
		}
	}
	//判断答案是否改变
	private boolean isAnswerChanged(UserItemRecordInfo info,Set<UserItemRecordInfo> items){
		if(items == null || items.size()==0) return true;
		for(UserItemRecordInfo item:items){
			if(item.getItemId().equalsIgnoreCase(info.getItemId())){
				if(item.getAnswer().equalsIgnoreCase(info.getAnswer()))
					return false;
				else
					return true;
			}
		}
		return true;
	}
	//获取共享答案题的题目内容,选项的汇总
	private String getShareAnswerContent(StructureItemInfo info,TreeSet<StructureItemInfo> set) {
		StringBuilder builder = new StringBuilder();
		builder.append(info.getContent());
		set.remove(set.last());
		int i = 65;
		for(StructureItemInfo s:set){
			builder.append((char)(i++)).append(s.getContent()).append(" <br/>");
		}
		return builder.toString();
	}

	//判分
	private PaperPreview judgePaper(PaperPreview paper,UserPaperRecordInfo record,Set<UserItemRecordInfo> itemRecordList) throws Exception {
		List<StructureInfo> structures = paper.getStructures();
		if (structures == null || structures.size() == 0)
			return null;
		BigDecimal paperTotalScore = BigDecimal.ZERO; // 试卷得总分
		BigDecimal paperScore = paper.getScore(); //试卷总分
		for (StructureInfo s : structures) {
			// 评分规则
			BigDecimal actualRuleTotal = this.calculateStructrueScore(s, itemRecordList);
			//得分比例[只有顶级大题才有比例]
			BigDecimal ratio = (s.getRatio()==null||s.getRatio().compareTo(BigDecimal.ZERO)==0||s.getRatio().compareTo(BigDecimal.TEN.multiply(BigDecimal.TEN))==0)?BigDecimal.ONE:(s.getRatio().divide(BigDecimal.TEN.multiply(BigDecimal.TEN),2,RoundingMode.HALF_UP)); //所占比例
			if(ratio.compareTo(BigDecimal.ONE)!=0 && actualRuleTotal.compareTo(BigDecimal.ZERO)>0)
			{
				if(s.getScore()!=null && BigDecimal.ZERO.compareTo(s.getScore())!=0)
					actualRuleTotal = actualRuleTotal.multiply(ratio).multiply(paperScore).divide(new BigDecimal(s.getTotal()).multiply(s.getScore()),2,RoundingMode.HALF_UP);
				else
					actualRuleTotal = actualRuleTotal.multiply(ratio).multiply(paperScore).divide(new BigDecimal(s.getTotal()),2,RoundingMode.HALF_UP);
			}
			if (actualRuleTotal.compareTo(BigDecimal.ZERO) == -1) {
				actualRuleTotal = BigDecimal.ZERO;
			}
			paperTotalScore = paperTotalScore.add(actualRuleTotal); // 试卷总分
		}
		record.setScore(paperTotalScore);
		return paper;
	}
	/**
	 * 计算大题分数
	 * @param structure			大题
	 * @param itemRecordList	试题考试记录
	 * @return
	 */
	private BigDecimal calculateStructrueScore(StructureInfo structure,Set<UserItemRecordInfo> itemRecordList)
	{
		BigDecimal actualRuleTotal = BigDecimal.ZERO;
		if(structure.getChildren()!=null && structure.getChildren().size()>0)
		{
			for(StructureInfo s : structure.getChildren())
			{
				actualRuleTotal = actualRuleTotal.add(this.calculateStructrueScore(s, itemRecordList));
			}
		}else
		{
			Set<StructureItemInfo> items = structure.getItems();
			if (items == null || items.size() == 0)
				return actualRuleTotal;
			BigDecimal min = structure.getMin(); // 最低得分
			BigDecimal per = structure.getScore(); // 每题得分
			min = (min == null ? BigDecimal.ZERO : min);
			per = (per == null ? BigDecimal.ZERO : per);
			for (StructureItemInfo item : items) { // 结构题目
				for (UserItemRecordInfo itemRecord : itemRecordList) {
					if (!itemRecord.getItemId().contains("#")) { // 不包含#,是单题
						if (item.getId().equals(itemRecord.getItemId())) {
							// 判断对错
							judgeItemIsRight(item, itemRecord, min, per);
							actualRuleTotal = actualRuleTotal.add(itemRecord.getScore());
							break;
						}
					} else { // 复合题
						if(item.getType().equals(Constant.TYPE_SHARE_TITLE))
						{
							Set<StructureItemInfo> children = item.getChildren();
							for (StructureItemInfo child : children) {
								if ((item.getId()+"#"+child.getId()).equals(itemRecord.getItemId())) {
									// 判断对错
									judgeItemIsRight(child, itemRecord, min, per);
									actualRuleTotal = actualRuleTotal.add(itemRecord.getScore());
									break;
								}
							}
						}else if(item.getType().equals(Constant.TYPE_SHARE_ANSWER)){
							TreeSet<StructureItemInfo> set = new TreeSet<StructureItemInfo>();
							set.addAll(item.getChildren());
							Set<StructureItemInfo> children = set.last().getChildren();	//子题目
							for(StructureItemInfo i:children){
								if((item.getId()+"#"+i.getId()).equalsIgnoreCase(itemRecord.getItemId())){
									//判断对错
									judgeItemIsRight(i, itemRecord, min, per);
									actualRuleTotal = actualRuleTotal.add(itemRecord.getScore());
									break;
								}
							}
						}
					}
				}
			}
		}
		return actualRuleTotal;
	}
	/*
	* 判断题目是对是错
	*/
	private boolean judgeItemIsRight(StructureItemInfo item,UserItemRecordInfo itemRecord, BigDecimal min, BigDecimal per) {
		//用户没有作答
		if (StringUtils.isEmpty(itemRecord.getAnswer())) {
			itemRecord.setScore(min); // 得0分或者负分
			itemRecord.setStatus(ItemRecord.STATUS_NULL); // 没有作答
			return false;
		}
		//题目没有答案
		if(StringUtils.isEmpty(item.getAnswer())){	//没有正确答案(题目有问题)
			itemRecord.setScore(min); // 得0分或者负分
			itemRecord.setStatus(ItemRecord.STATUS_WRONG); // 算答错
			return false;
		}
		//单选题或判断题
		if(item.getType().equals(Constant.TYPE_SINGLE) || item.getType().equals(Constant.TYPE_JUDGE)) {
			if (item.getAnswer().equals(itemRecord.getAnswer())) // 答对
			{
				itemRecord.setScore(per); // 得标准分
				itemRecord.setStatus(ItemRecord.STATUS_RIGHT); // 答对
				return true;
			} else {
				itemRecord.setScore((min.compareTo(BigDecimal.ZERO) == 1) ? BigDecimal.ZERO	: min); // 得0分或者负分
				itemRecord.setStatus(ItemRecord.STATUS_WRONG); // 答错
				return false;
			}
		}
		//多选或不定项
		if(item.getType().equals(Constant.TYPE_MULTY) || item.getType().equals(Constant.TYPE_UNCERTAIN)) {
			String[] arr = itemRecord.getAnswer().split(",");
			String answer = item.getAnswer();
			int total = 0;
			for (String a : arr) {
				if (answer.indexOf(a) == -1) { // 包含有错误答案
					itemRecord.setScore((min.compareTo(BigDecimal.ZERO) == 1) ? BigDecimal.ZERO : min); // 得0分或者负分
					itemRecord.setStatus(ItemRecord.STATUS_WRONG); // 答错
					return false;
				} else {
					total++;
				}
			}
			if (total == answer.split(",").length) { // 全对,得满分
				itemRecord.setScore(per); // 得标准分
				itemRecord.setStatus(ItemRecord.STATUS_RIGHT); // 答对
				return true;
			} else { 
				itemRecord.setScore((min.compareTo(BigDecimal.ZERO) == -1) ? BigDecimal.ZERO : min.multiply(new BigDecimal(total))); // 得0分或者负分
				if(itemRecord.getScore().compareTo(per)==1) itemRecord.setScore(per); //得分不能超过该题总分
				itemRecord.setStatus(ItemRecord.STATUS_LESS); // 少选
				return false;
			}
		}
		return true;
	}
	/*
	 * 查询考试记录
	 * @see com.examw.test.front.service.IPaperService#recordDataGrid(java.lang.String, java.lang.String, com.examw.test.front.model.library.PaperInfo)
	 */
	@Override
	public DataGrid<UserPaperRecordInfo> recordDataGrid(String userId,
			String productId, PaperInfo info) throws Exception {
		if(logger.isDebugEnabled()) logger.debug("加载考试记录分页列表信息...");
		Integer page = info.getPage()==null?1:info.getPage();
		Integer rows = info.getRows()==null?10:info.getRows();
		//从缓存中取用户试卷最新记录	[不使用缓存]
		/*List<UserPaperRecordInfo> records = (List<UserPaperRecordInfo>) this.cacheHelper.getCache(UserPaperRecordInfo.class.getName(), this.getClass().getName()+"loadUserPaperRecords", new Object[]{userId,productId});
		if(records == null){
			records = this.findUserPaperRecords(userId, productId);
			if(records!=null)
				this.cacheHelper.putCache(UserPaperRecordInfo.class.getName(), this.getClass().getName()+"loadUserPaperRecords", new Object[]{userId,productId}, records);
		}*/
		List<UserPaperRecordInfo> records = this.findUserPaperRecords(userId, productId);
		DataGrid<UserPaperRecordInfo> datagrid = new DataGrid<UserPaperRecordInfo>();
		List<UserPaperRecordInfo> result = new ArrayList<UserPaperRecordInfo>();
		if(records == null || records.size()==0){
			datagrid.setRows(result);
			datagrid.setTotal(0L);
			return datagrid;
		}
		//按条件筛选
		for(UserPaperRecordInfo paper : records){
			boolean flag = true;
			if(flag && !StringUtils.isEmpty(info.getSubjectId())){
				flag = paper.getSubjectId().equalsIgnoreCase(info.getSubjectId());
			}
			if(flag && info.getType() != null){
				flag = paper.getPaperType().equals(info.getType());
			}
			if(flag && info.getStatus()!=null){
				flag = paper.getStatus().equals(info.getStatus());
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
			if(result.size() <= rows)
			{
				datagrid.setRows(result);
			}else{
				datagrid.setRows(result.subList((page-1)*rows, page*rows>total?total:page*rows));
			}
		}
		return datagrid;
	}
	/**
	 * 查询每日一练试卷列表 带用户信息
	 */
	@Override
	public List<FrontPaperInfo> findDailyPaperList(String productId,Calendar calendar,String userId)
			throws Exception {
		if(logger.isDebugEnabled()) logger.debug("加载模拟考场试卷列表...");
		if(StringUtils.isEmpty(productId))
		return null;
		List<FrontPaperInfo> list = this.findDailyPaperList(productId);
		//查出当天的记录
		if(list == null||list.size()==0) return null;
		List<FrontPaperInfo> result = new ArrayList<FrontPaperInfo>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<UserPaperRecordInfo> records = this.findUserPaperRecords(userId, productId);
		boolean flag = (records == null || records.size() == 0);
		for(FrontPaperInfo paper:list){
			if(paper == null) continue;
			Date paperDate = format.parse(paper.getCreateTime());
			if(logger.isDebugEnabled()) logger.debug("试卷的日期:"+format.format(paperDate)+"  选取的日期:"+DateUtil.format(calendar.getTime())+" 比较的结果:"+paperDate.compareTo(calendar.getTime()));
			if(paperDate.compareTo(calendar.getTime())==1){
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				if(logger.isDebugEnabled()) logger.debug("试卷的日期:"+format.format(paperDate)+"  选取的日期:"+DateUtil.format(calendar.getTime())+" 比较的结果:"+paperDate.compareTo(calendar.getTime()));
				if(paperDate.compareTo(calendar.getTime())==-1)
				{
					//查询练习记录,附上用户记录信息
					if(!flag) this.setUserRecordInfo(paper, records);
					result.add(paper);
				}
				calendar.add(Calendar.DAY_OF_MONTH, -1);
			}
		}
		return result;
	}
	/**
	 * 查询每日一练的试卷列表(纯试卷不带用户信息)[加入到缓存中]
	 */
	@SuppressWarnings("unchecked")
	private List<FrontPaperInfo> findDailyPaperList(String productId) throws Exception{
		String today = DateUtil.format(new Date());
		List<FrontPaperInfo> list = (List<FrontPaperInfo>) this.cacheHelper.getCache(FrontPaperInfo.class.getName(), this.getClass().getName()+"loadPaperList", new Object[]{productId,today});
		if(list == null){
			String url = String.format(this.api_daily_papers_url,productId);
			String xml = remoteService.httpRequest(url,"GET",null,"utf-8");
			if(!StringUtils.isEmpty(xml)){
				list = JSONUtil.JsonToCollection(xml, List.class, FrontPaperInfo.class);
			}
			if(list!=null)
				this.cacheHelper.putCache(FrontProductInfo.class.getName(), this.getClass().getName()+"loadPaperList", new Object[]{productId}, list);
		}
		return list;
	}
	/*
	 * 查询当天未做的每日一练数量
	 * @see com.examw.test.front.service.IPaperService#findUndoneDailyPaperNumber(java.lang.String, java.lang.String)
	 */
	@Override
	public Json findUndoneDailyPaperNumber(String userId, String productId)
			throws Exception {
		if(logger.isDebugEnabled()) logger.debug("加载模拟考场试卷列表...");
		if(StringUtils.isEmpty(productId))
		return null;
		String url = String.format(this.api_daily_papers_undone_number_url,userId,productId);
		String xml = remoteService.httpRequest(url,"GET",null,"utf-8");
		if(!StringUtils.isEmpty(xml)){
			return JSONUtil.JsonToObject(xml, Json.class);
		}
		return null;
	}
}
