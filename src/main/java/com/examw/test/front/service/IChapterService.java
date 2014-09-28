package com.examw.test.front.service;

import java.util.List;

import com.examw.test.front.model.syllabus.KnowledgeInfo;
import com.examw.test.front.model.syllabus.SyllabusInfo;

/**
 * 章节练习服务接口
 * @author fengwei.
 * @since 2014年9月9日 下午4:09:16.
 */
public interface IChapterService {
	/**
	 * 加载考试,科目和章节的信息
	 * @param examId	考试ID
	 * @return 章节信息的集合
	 * @throws Exception
	 */
	List<SyllabusInfo> loadChapterInfo(String subjectId)throws Exception;
	/**
	 * 加载章节详情
	 * @param pid	上级章节ID
	 * @param id	选中章节ID
	 * @return	
	 * @throws Exception
	 */
	KnowledgeInfo loadKnowledgeDetail(String id)throws Exception;
	/**
	 * 加载某小节信息
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	SyllabusInfo loadKnowledges(String chapterId) throws Exception;
}
