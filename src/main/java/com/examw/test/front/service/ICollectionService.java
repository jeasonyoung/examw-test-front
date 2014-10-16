package com.examw.test.front.service;

import java.io.IOException;
import java.util.List;

import com.examw.model.Json;
import com.examw.test.front.model.product.FrontSubjectInfo;
import com.examw.test.front.model.record.Collection;
import com.examw.test.front.model.record.UserItemFavoriteInfo;

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
	Json collectOrCancel(Collection info) throws Exception;
	/**
	 * 加载收藏题目的集合
	 * @param info
	 * @return
	 * @throws IOException
	 */
	List<UserItemFavoriteInfo> loadCollectionItems(Collection info) throws IOException;
	
	List<FrontSubjectInfo> loadCollectionSubjects(String productId,String userId)throws Exception;
}
