package com.examw.test.front.service;

import java.io.IOException;
import java.util.List;

import com.examw.model.DataGrid;
import com.examw.test.front.model.product.FrontProductInfo;


/**
 * 产品服务接口
 * @author fengwei.
 * @since 2014年8月12日 下午3:36:33.
 */
public interface IProductService{
	/**
	 * 根据选择的考试加载旗下所有产品
	 * @param examId	考试ID(一个)
	 * @return
	 * @throws IOException
	 */
	List<FrontProductInfo> loadProducts(String examId) throws IOException;
	/**
	 * 分页数据
	 * @param info
	 * @return
	 * @throws IOException
	 */
	DataGrid<FrontProductInfo> dataGrid(FrontProductInfo info) throws IOException;
	/**
	 * 根据ID加载产品信息
	 * @param id
	 * @return
	 * @throws IOException
	 */
	FrontProductInfo loadProduct(String id)throws IOException;
}
