package com.examw.test.front.service;

import java.io.IOException;
import java.util.List;

import com.examw.test.front.model.ProductInfo;


/**
 * 产品服务接口
 * @author fengwei.
 * @since 2014年8月12日 下午3:36:33.
 */
public interface IProductService{
	/**
	 * 根据选择的考试加载旗下所有产品
	 * @param examId	考试ID(一个或多个)
	 * @return
	 * @throws IOException
	 */
	List<ProductInfo> loadProducts(String examId) throws IOException;
	/**
	 * 根据ID加载产品信息
	 * @param id
	 * @return
	 * @throws IOException
	 */
	ProductInfo loadProduct(String id)throws IOException;
}
