package com.examw.test.front.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.model.Json;
import com.examw.test.front.model.library.FrontItemInfo;
import com.examw.test.front.model.record.Collection;
import com.examw.test.front.service.ICollectionService;
import com.examw.test.front.support.HttpUtil;

/**
 * 收藏服务接口
 * @author fengwei.
 * @since 2014年9月23日 下午2:51:51.
 */
public class CollectionServiceImpl implements ICollectionService{
	private static final Logger logger = Logger.getLogger(CollectionServiceImpl.class);
	private String api_collection_url;
	/**
	 * 设置 笔记数据查询数据接口地址
	 * @param api_item_notes_url
	 * 
	 */
	public void setApi_collection_url(String api_collection_url) {
		this.api_collection_url = api_collection_url;
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
		String url = String.format(this.api_collection_url,info.getItemId());
		return HttpUtil.upload(url, info);
	}
	
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<ItemScoreInfo> loadCollectionItems(Collection info)
//			throws IOException {
//		if(logger.isDebugEnabled()) logger.debug("收藏的试题集合...");
//		if(StringUtils.isEmpty(info.getUserId())) 
//			return null;
//		String url = String.format(this.api_collection_url);
//		String data = "itemId="+info.getItemId()+"&userId="+info.getUserId()+"&productId="+info.getProductId();
//		String xml = HttpUtil.httpRequest(url,"GET",data,"utf-8");
//		if(!StringUtils.isEmpty(xml)){
//			return JSONUtil.JsonToCollection(xml, List.class,ItemScoreInfo.class);
//		}
//		return null;
//	}
	@Override
	public List<FrontItemInfo> loadCollectionItems(Collection info)
			throws IOException {
		
		return null;
	}
}
