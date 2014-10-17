package com.examw.test.front.service;

import java.io.IOException;
import java.util.List;

import com.examw.test.front.model.product.AreaInfo;
import com.examw.test.front.model.product.FrontProductInfo;
import com.examw.test.front.model.product.SubjectInfo;


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
	 * 根据ID加载产品信息
	 * @param id
	 * @return
	 * @throws IOException
	 */
	FrontProductInfo loadProduct(String id)throws IOException;
	/**
	 * 根据产品ID查找包含的科目信息
	 * @param productId
	 * @return
	 * @throws IOException
	 */
	List<SubjectInfo> loadProductSubjects(String productId) throws IOException;
	/**
	 * 查询产品地区集合
	 * @param productId
	 * @return
	 */
	List<AreaInfo> loadProductAreas(String productId) throws IOException;
}
