package com.examw.test.front.service;

import java.io.IOException;

import com.examw.model.DataGrid;
import com.examw.test.front.model.library.FrontItemInfo;

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
	DataGrid<FrontItemInfo> dataGrid(String productId,FrontItemInfo info,String userId) throws IOException;
}
