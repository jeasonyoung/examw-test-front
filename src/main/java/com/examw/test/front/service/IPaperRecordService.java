package com.examw.test.front.service;

import java.io.IOException;
import java.util.List;

import com.examw.test.front.model.library.PaperRecordInfo;

/**
 * 
 * @author fengwei.
 * @since 2014年9月30日 下午2:45:26.
 */
public interface IPaperRecordService {
	/**
	 * 加载考试记录
	 * @param info
	 * @return
	 * @throws IOException 
	 */
	List<PaperRecordInfo> loadPaperRecords(PaperRecordInfo info) throws IOException;
}
