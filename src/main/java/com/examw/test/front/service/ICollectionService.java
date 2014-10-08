package com.examw.test.front.service;

import java.io.IOException;

import com.examw.model.Json;
import com.examw.test.front.model.record.Collection;

/**
 * 收藏服务接口
 * @author fengwei.
 * @since 2014年9月23日 下午2:51:13.
 */
public interface ICollectionService {
	/**
	 * 收藏或取消收藏
	 * @param structureItemId
	 * @param itemId
	 * @param userId
	 * @return
	 * @throws IOException 
	 */
	Json collectOrCancel(Collection info) throws IOException;
	/**
	 * 加载收藏题目的集合
	 * @param info
	 * @return
	 * @throws IOException
	 */
	//List<ItemScoreInfo> loadCollectionItems(Collection info) throws IOException;
}
