package com.examw.test.front.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.model.Json;
import com.examw.test.front.model.Constant;
import com.examw.test.front.model.library.FrontItemInfo;
import com.examw.test.front.model.library.PaperPreview;
import com.examw.test.front.model.library.StructureInfo;
import com.examw.test.front.model.library.StructureItemInfo;
import com.examw.test.front.model.product.FrontSubjectInfo;
import com.examw.test.front.model.record.Collection;
import com.examw.test.front.model.record.UserItemFavoriteInfo;
import com.examw.test.front.service.ICollectionService;
import com.examw.test.front.service.IPaperService;
import com.examw.test.front.support.HttpUtil;
import com.examw.test.front.support.JSONUtil;

/**
 * 收藏服务接口
 * @author fengwei.
 * @since 2014年9月23日 下午2:51:51.
 */
public class CollectionServiceImpl implements ICollectionService{
	private static final Logger logger = Logger.getLogger(CollectionServiceImpl.class);
	private String api_collection_url;
	private String api_collection_list_url;
	private IPaperService paperService;
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
	 * 设置 
	 * @param api_collection_list_url
	 * 
	 */
	public void setApi_collection_list_url(String api_collection_list_url) {
		this.api_collection_list_url = api_collection_list_url;
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
		PaperPreview paper = this.paperService.findPaperDetail(info.getPaperId());
		UserItemFavoriteInfo favor = null;
		if(info.getItemId().contains("#")){
			String[] p_c_id = info.getItemId().split("#");
			favor = this.changeModel(this.getStructureItemInfo(paper, p_c_id[0]),p_c_id[1],info);
		}else{
			favor = this.changeModel(this.getStructureItemInfo(paper, info.getItemId()),null,info);
		}
		return HttpUtil.upload(url, favor);
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
	private UserItemFavoriteInfo changeModel(StructureItemInfo info,String childItemId,Collection c){
		if(info == null) return null;
		if(childItemId == null)
		{
			UserItemFavoriteInfo data = new UserItemFavoriteInfo();
			BeanUtils.copyProperties(info, data, new String[]{"createTime","lastTime"});
			data.setItemId(info.getId());
			info.setUserAnswer(c.getUserAnswer());
			data.setItemContent(JSONUtil.ObjectToJson(info));
			//TODO 改终端代码
			data.setTerminalCode(123456);
			data.setUserId(c.getUserId());
			data.setItemType(info.getType());
			data.setSubjectId(info.getSubjectId());
			data.setId(null);
			return data;
		}
		else{
			Set<StructureItemInfo> items = info.getChildren();	//子题目
			if(info.getType().equals(Constant.TYPE_SHARE_TITLE)){
				for(StructureItemInfo item:items){
					if(item.getId().equalsIgnoreCase(childItemId)){
						UserItemFavoriteInfo result = this.changeModel(item, null,c);
						result.setItemId(info.getId()+"#"+item.getId());
						return result;
					}
				}
			}else if(info.getType().equals(Constant.TYPE_SHARE_ANSWER)){
				TreeSet<StructureItemInfo> set = new TreeSet<StructureItemInfo>();
				set.addAll(info.getChildren());
				Set<StructureItemInfo> children = set.last().getChildren();	//子题目
				for(StructureItemInfo item:children){
					if(item.getId().equalsIgnoreCase(childItemId)){
						UserItemFavoriteInfo result = this.changeModel(item, null,c);
						result.setItemId(info.getId()+"#"+item.getId());
						return result;
					}
				}
			}
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<FrontItemInfo> loadCollectionItems(Collection info)
			throws IOException {
		if(logger.isDebugEnabled()) logger.debug("收藏的试题集合...");
		if(StringUtils.isEmpty(info.getUserId())) 
			return null;
		String url = String.format(this.api_collection_url);
		String data = "subjectId="+info.getSubjectId()+"&userId="+info.getUserId()+"&productId="+info.getProductId();
		String xml = HttpUtil.httpRequest(url,"GET",data,"utf-8");
		if(!StringUtils.isEmpty(xml)){
			return JSONUtil.JsonToCollection(xml, List.class,FrontItemInfo.class);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FrontSubjectInfo> loadCollectionSubjects(String productId,
			String userId) throws Exception {
		if(logger.isDebugEnabled()) logger.debug("收藏的试题科目列表...");
		String url = String.format(this.api_collection_list_url,productId,userId);
		String xml = HttpUtil.httpRequest(url,"GET",null,"utf-8");
		if(!StringUtils.isEmpty(xml)){
			return JSONUtil.JsonToCollection(xml, List.class,FrontSubjectInfo.class);
		}
		return null;
	}
}
