package com.examw.test.front.service;

import java.io.IOException;
import java.util.Map;

import com.examw.model.DataGrid;
import com.examw.test.front.model.library.StructureItemInfo;

/**
 * 
 * @author fengwei.
 * @since 2014年10月10日 下午2:50:48.
 */
public interface IErrorItemService {
	/**
	 * 查询数据
	 * @param productId
	 * @param info
	 * @param userId
	 * @return
	 * @throws IOException
	 */
	DataGrid<StructureItemInfo> dataGrid(String productId,StructureItemInfo info,String userId) throws Exception;
	/**
	 * 加载具体的题目
	 * @param productId
	 * @param itemId
	 * @return
	 */
	Map<String, Object> loadItemDetail(String productId,String subjectId,String userId, String itemId)throws Exception;
	/**
	 * 统计产品下的错题
	 * @param productId
	 * @param info
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	//Long totalErrorItemService(String productId,StructureItemInfo info,String userId)throws Exception;
}
