package com.examw.test.front.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.front.model.Constant;
import com.examw.test.front.model.library.PaperPreview;
import com.examw.test.front.model.library.StructureInfo;
import com.examw.test.front.model.library.StructureItemInfo;
import com.examw.test.front.model.record.UserItemFavoriteInfo;
import com.examw.test.front.model.record.UserItemRecordInfo;
import com.examw.test.front.model.record.UserPaperRecordInfo;
import com.examw.test.front.service.IPaperAnalysisService;
import com.examw.test.front.service.IPaperService;
import com.examw.test.front.support.HttpUtil;
import com.examw.test.front.support.JSONUtil;
import com.examw.test.front.support.MethodCacheHelper;

/**
 * 
 * @author fengwei.
 * @since 2014年10月13日 下午3:36:42.
 */
public class PaperAnalysisServiceImpl implements IPaperAnalysisService{
	private static final Logger logger = Logger.getLogger(PaperAnalysisServiceImpl.class);
	private IPaperService paperService;
	private String api_user_collections_url;
	private MethodCacheHelper cacheHelper;
	
	/**
	 * 设置 试卷服务接口
	 * @param paperService
	 * 
	 */
	public void setPaperService(IPaperService paperService) {
		this.paperService = paperService;
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
	 * 设置 缓存帮助类
	 * @param cacheHelper
	 * 
	 */
	public void setCacheHelper(MethodCacheHelper cacheHelper) {
		this.cacheHelper = cacheHelper;
	}

	/*
	 * 试卷解析
	 * @see com.examw.test.front.service.IPaperAnalysisService#loadPaperAnalysis(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public PaperPreview loadPaperAnalysis(String paperId, String userId,
			String productId) throws Exception {
		if(logger.isDebugEnabled()) logger.debug("加载试卷的解析...");
		PaperPreview paper = this.paperService.findPaperDetail(paperId);
		UserPaperRecordInfo record = this.paperService.findLastedRecord(userId, paperId);
		if(record == null || record.getStatus().equals(Constant.STATUS_UNDONE)){
			throw new RuntimeException("考试记录不存在,或者没有考完");
		}
		List<UserItemFavoriteInfo> favors = this.loadItemFavorites(userId);
		setUserAnswer(paper,record,favors);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private List<UserItemFavoriteInfo> loadItemFavorites(String userId) throws IOException{
		if(logger.isDebugEnabled()) logger.debug("加载用户收藏...");
		if(StringUtils.isEmpty(userId))
			return null;
		List<UserItemFavoriteInfo> list = (List<UserItemFavoriteInfo>) this.cacheHelper.getCache(UserItemFavoriteInfo.class.getName(), this.getClass().getName()+"loadItemFavorites", new String[]{userId});
			if(list == null){
				String url = String.format(this.api_user_collections_url,userId);
				String xml = HttpUtil.httpRequest(url,"GET",null,"utf-8");
				if(!StringUtils.isEmpty(xml)){
					list = JSONUtil.JsonToCollection(xml, List.class, UserItemFavoriteInfo.class);
				}
				if(list!=null){
					this.cacheHelper.putCache(UserItemFavoriteInfo.class.getName(), this.getClass().getName()+"loadItemFavorites", new String[]{userId}, list);
				}
			}
		return list;
	}
	
	//设置用户的答案
		private void setUserAnswer(PaperPreview paper, UserPaperRecordInfo info,List<UserItemFavoriteInfo> favors) {
			List<StructureInfo> structures = paper.getStructures();
			Set<UserItemRecordInfo> itemRecords = info.getItems();
			if(itemRecords == null || itemRecords.size() ==0 ) return;
			for(StructureInfo s:structures){
				Set<StructureItemInfo> items = s.getItems();
				for(StructureItemInfo item:items){
					setUserAnswer(item,itemRecords,favors);
				}
			}
		}
		//设置用户答案
		private void setUserAnswer(StructureItemInfo item,
				Set<UserItemRecordInfo> itemRecords,List<UserItemFavoriteInfo> favors) {
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
						break;
					}
				}
				//判断是否被收藏
				isCollected(item.getId()+"#"+child.getId(), child, favors);
			}
		}
		private void isCollected(String itemId,StructureItemInfo item,List<UserItemFavoriteInfo> favors){
			if(favors == null || favors.size() == 0) return ;
			for(UserItemFavoriteInfo favor:favors){
				if(itemId.equalsIgnoreCase(favor.getItemId())){
					item.setIsCollected(true);
				}
			}
		}
}
