package com.examw.test.front.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.examw.model.Json;
import com.examw.test.front.model.library.ItemScoreInfo;
import com.examw.test.front.model.library.PaperFrontInfo;
import com.examw.test.front.model.library.PaperInfo;
import com.examw.test.front.model.library.PaperPreview;
import com.examw.test.front.model.library.PaperSubmitInfo;

/**
 * 试卷服务接口
 * @author fengwei.
 * @since 2014年9月11日 上午8:26:13.
 */
public interface IPaperService {
	/**
	 * 根据条件加载试卷信息
	 * @param info
	 * @return
	 */
	Map<String,Object> loadPaperList(String productId,PaperInfo info,String userId)throws IOException;
	/**
	 * 根据试卷ID加载试卷详情
	 * @param paperId 试卷id
	 * @return
	 * @throws IOException
	 */
	PaperPreview loadPaperInfo(String paperId)throws IOException;
	/**
	 * 根据试卷ID加载试卷详情
	 * @param paperId
	 * @return
	 * @throws IOException
	 */
	PaperPreview loadPaperDetail(String paperId,String userId,String productId)throws IOException;
	/**
	 * 加载试卷试题的集合
	 * @param paper
	 * @return
	 */
	List<ItemScoreInfo> loadItemsList(PaperPreview paper,boolean isSetCommonTitle);
	/**
	 * 下次再做的提交
	 * @param info 提交试卷答案信息
	 */
	Json sumbitPaper(PaperSubmitInfo info)throws IOException;
	/**
	 * 加载试卷试题解析
	 * @param paperId
	 * @param userId
	 * @return
	 * @throws IOException
	 */
	PaperFrontInfo loadPaperAnalysis(String paperId,String userId,String productId)throws IOException;
}
