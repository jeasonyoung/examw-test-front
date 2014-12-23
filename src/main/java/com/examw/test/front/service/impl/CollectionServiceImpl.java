package com.examw.test.front.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.model.Json;
import com.examw.test.front.model.Constant;
import com.examw.test.front.model.library.PaperPreview;
import com.examw.test.front.model.library.StructureInfo;
import com.examw.test.front.model.library.StructureItemInfo;
import com.examw.test.front.model.product.FrontSubjectInfo;
import com.examw.test.front.model.record.Collection;
import com.examw.test.front.model.record.UserItemFavoriteInfo;
import com.examw.test.front.service.ICollectionService;
import com.examw.test.front.service.IPaperService;
import com.examw.test.front.service.IRemoteService;
import com.examw.test.front.support.JSONUtil;
import com.examw.test.front.support.MethodCacheHelper;

/**
 * 收藏服务接口
 * @author fengwei.
 * @since 2014年9月23日 下午2:51:51.
 */
public class CollectionServiceImpl implements ICollectionService{
	private static final Logger logger = Logger.getLogger(CollectionServiceImpl.class);
	//收藏|取消收藏地址
	private String api_collection_url;
	//带收藏个数的科目信息地址
	private String api_collection_subject_list_url;
	//收藏的题目信息
	private String api_collection_item_list_url;
	//试卷服务接口
	private IPaperService paperService;	
	//web终端代码
	private Integer web_terminal_code;
	//缓存帮助类
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
	 * 设置 笔记数据查询数据接口地址
	 * @param api_item_notes_url
	 * 
	 */
	public void setApi_collection_url(String api_collection_url) {
		this.api_collection_url = api_collection_url;
	}
	/**
	 * 设置试卷服务接口
	 * @param paperService
	 */
	public void setPaperService(IPaperService paperService) {
		this.paperService = paperService;
	}
	
	/**
	 * 设置 收藏按科目收藏信息地址
	 * @param api_collection_subject_list_url
	 * 
	 */
	public void setApi_collection_subject_list_url(
			String api_collection_subject_list_url) {
		this.api_collection_subject_list_url = api_collection_subject_list_url;
	}
	/**
	 * 设置  收藏试题信息地址
	 * @param api_collection_item_list_url
	 * 
	 */
	public void setApi_collection_item_list_url(String api_collection_item_list_url) {
		this.api_collection_item_list_url = api_collection_item_list_url;
	}
	
	/**
	 * 设置 web终端代码
	 * @param web_terminal_code
	 * 
	 */
	public void setWeb_terminal_code(Integer web_terminal_code) {
		this.web_terminal_code = web_terminal_code;
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
	 *收藏或取消收藏
	 * @see com.examw.test.front.service.ICollectionService#collectOrCancel(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Json collectOrCancel(Collection info) throws Exception {
		if(logger.isDebugEnabled()) logger.debug("收藏或取消收藏...");
		if(StringUtils.isEmpty(info.getItemId()) || StringUtils.isEmpty(info.getUserId())) 
			return null;
		String url = String.format(this.api_collection_url,info.getUserId());
		if(StringUtils.isEmpty(info.getPaperId())){
			UserItemFavoriteInfo favor = new UserItemFavoriteInfo();
			favor.setItemId(info.getItemId());
			favor.setUserId(info.getUserId());
			favor.setTerminalCode(this.web_terminal_code);
			if(!StringUtils.isEmpty(info.getId())){	//从错题记录中收藏
				StructureItemInfo item = JSONUtil.JsonToObject(info.getId(),StructureItemInfo.class);
				favor.setItemContent(info.getId());
				favor.setItemType(item.getType());
				favor.setSubjectId(item.getSubjectId());
				favor.setRemarks(info.getRemarks());
			}
			return remoteService.upload(url, favor);
		}
		PaperPreview paper = this.paperService.findPaperDetail(info.getPaperId());
		UserItemFavoriteInfo favor = null;
		if(info.getItemId().contains("#")){
			String[] p_c_id = info.getItemId().split("#");
			favor = this.changeModel(this.getStructureItemInfo(paper, p_c_id[0]),p_c_id[1],info);
		}else{
			favor = this.changeModel(this.getStructureItemInfo(paper, info.getItemId()),null,info);
		}
		return remoteService.upload(url, favor);
	}

	/*
	 * 构造对象
	 */
	private StructureItemInfo getStructureItemInfo(PaperPreview paper,String itemId){
		List<StructureInfo> structures = paper.getStructures();
		for(StructureInfo s:structures){
			Set<StructureItemInfo> items = s.getItems();
			for(StructureItemInfo item:items){
				if(item.getId().equalsIgnoreCase(itemId)){
					return item;
				}
			}
		}
		return null;
	}
	//数据模型转化
	private UserItemFavoriteInfo changeModel(StructureItemInfo info,String childItemId,Collection c){
		if(info == null) return null;
		if(childItemId == null)
		{
			UserItemFavoriteInfo data = new UserItemFavoriteInfo();
			BeanUtils.copyProperties(info, data, new String[]{"createTime","lastTime"});
			data.setItemId(info.getId());
			info.setUserAnswer(c.getUserAnswer());
			info.setRemarks(c.getRemarks());	//设置收藏备注
			data.setItemContent(JSONUtil.ObjectToJson(info));
			data.setTerminalCode(web_terminal_code);
			data.setUserId(c.getUserId());
			data.setItemType(info.getType());
			data.setSubjectId(info.getSubjectId());
			data.setRemarks(c.getRemarks());
			data.setId(null);
			return data;
		}
		else{
			Set<StructureItemInfo> items = info.getChildren();	//子题目
			if(info.getType().equals(Constant.TYPE_SHARE_TITLE)){
				for(StructureItemInfo item:items){
					if(item.getId().equalsIgnoreCase(childItemId)){
						item.setParentContent(info.getContent());
						item.setSubjectId(info.getSubjectId());
						UserItemFavoriteInfo result = this.changeModel(item, null,c);
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
						item.setSubjectId(info.getSubjectId());
						item.setParentContent(content);
						item.setPid(info.getId()); //重置一下pid[仅用于收藏]
						item.setChildren(set); //选项
						UserItemFavoriteInfo result = this.changeModel(item, null,c);
						result.setItemId(info.getId()+"#"+item.getId());
						return result;
					}
				}
			}
			return null;
		}
	}
	//获取共享答案题目的内容
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
	/*
	 * 加载收藏题目集合数据
	 * @see com.examw.test.front.service.ICollectionService#loadCollectionItems(com.examw.test.front.model.record.Collection)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserItemFavoriteInfo> findCollectionItems(Collection info)
			throws Exception {
		if(logger.isDebugEnabled()) logger.debug("收藏的试题集合...");
		if(StringUtils.isEmpty(info.getUserId())) 
			return null;
		String url = String.format(this.api_collection_item_list_url,info.getUserId());
		String data = "subjectId="+info.getSubjectId()+"&userId="+info.getUserId()+"&productId="+info.getProductId();
		String xml = remoteService.httpRequest(url,"GET",data,"utf-8");
		if(!StringUtils.isEmpty(xml)){
			return JSONUtil.JsonToCollection(xml, List.class,UserItemFavoriteInfo.class);
		}
		return null;
	}
	/*
	 * 加载被收藏的考试题目的集合
	 * @see com.examw.test.front.service.ICollectionService#loadCollectionItemList(com.examw.test.front.model.record.Collection)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StructureItemInfo> findCollectionItemList(Collection info) throws Exception {
		List<UserItemFavoriteInfo> list = (List<UserItemFavoriteInfo>) this.cacheHelper.getCache(StructureItemInfo.class.getName(), this.getClass()+".loadCollectionItemList", new String[]{info.getUserId(),info.getProductId(),info.getSubjectId()});
		if(list==null){
			list = this.findCollectionItems(info);
			if(list!=null){
				this.cacheHelper.putCache(StructureItemInfo.class.getName(), this.getClass()+".loadCollectionItemList", new String[]{info.getUserId(),info.getProductId(),info.getSubjectId()}, list);
			}
		}
		return this.changeModel(list);
	}
	
	//模型转化
	private List<StructureItemInfo> changeModel(List<UserItemFavoriteInfo> favorItemList) throws JsonParseException, JsonMappingException, IOException {
		if(favorItemList == null || favorItemList.size() ==0)
		return null;
		List<StructureItemInfo> result = new ArrayList<StructureItemInfo>();
		for(UserItemFavoriteInfo info:favorItemList){
			if(info == null)continue;
			StructureItemInfo data = new StructureItemInfo();
			data = JSONUtil.JsonToObject(info.getItemContent(), StructureItemInfo.class);
			data.setRemarks(info.getRemarks());
			result.add(data);
		}
		return result;
	}
	/*
	 * 加载带收藏数量的科目的集合
	 * @see com.examw.test.front.service.ICollectionService#loadCollectionSubjects(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FrontSubjectInfo> findCollectionSubjects(String productId,
			String userId) throws Exception {
		if(logger.isDebugEnabled()) logger.debug("收藏的试题科目列表...");
		String url = String.format(this.api_collection_subject_list_url,productId,userId);
		String xml = remoteService.httpRequest(url,"GET",null,"utf-8");
		if(!StringUtils.isEmpty(xml)){
			return JSONUtil.JsonToCollection(xml, List.class,FrontSubjectInfo.class);
		}
		return null;
	}
}
