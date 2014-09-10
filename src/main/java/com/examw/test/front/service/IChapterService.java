package com.examw.test.front.service;

import java.util.Map;

/**
 * 章节练习服务接口
 * @author fengwei.
 * @since 2014年9月9日 下午4:09:16.
 */
public interface IChapterService {
	/**
	 * 加载考试,科目和章节的信息
	 * @param examId	考试ID
	 * @return 一个数据的映射 
	 * @throws Exception
	 */
	Map<String,Object> loadExamAndChapterInfo(String examId,String subjectId)throws Exception;
	/**
	 * 加载章节详情
	 * @param pid	上级章节ID
	 * @param id	选中章节ID
	 * @return	
	 * @throws Exception
	 */
	Map<String,Object> loadChapterDetail(String pid ,String id)throws Exception;
}
