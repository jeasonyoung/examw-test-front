package com.examw.test.front.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.examw.test.front.model.ItemScoreInfo;
import com.examw.test.front.model.PaperInfo;
import com.examw.test.front.model.PaperPreview;

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
	Map<String,Object> loadPaperList(String productId,PaperInfo info)throws IOException;
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
	PaperPreview loadPaperDetail(String paperId)throws IOException;
	/**
	 * 加载试卷试题的集合
	 * @param paper
	 * @return
	 */
	List<ItemScoreInfo> loadItemsList(PaperPreview paper,boolean isSetCommonTitle);
}
