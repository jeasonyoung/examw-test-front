package com.examw.test.front.service;

import java.io.IOException;

import com.examw.test.front.model.library.PaperPreview;

/**
 * 试卷解析服务接口
 * @author fengwei.
 * @since 2014年10月13日 下午3:34:32.
 */
public interface IPaperAnalysisService {
	/**
	 * 加载试卷试题解析
	 * @param paperId
	 * @param userId
	 * @return
	 * @throws IOException
	 */
	PaperPreview loadPaperAnalysis(String paperId,String userId,String productId)throws Exception;
}
