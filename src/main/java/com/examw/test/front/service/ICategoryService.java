package com.examw.test.front.service;

import java.util.List;

import com.examw.test.front.model.product.FrontCategoryInfo;
/**
 * 考试分类服务接口
 * @author fengwei
 * @since 2014年9月5日 09:50:02
 */
public interface ICategoryService {
	/**
	 * 加载所有的考试类型-考试
	 * @return
	 */
	List<FrontCategoryInfo> loadAllCategoryAndExams() throws Exception;
}
