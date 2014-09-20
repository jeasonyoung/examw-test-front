package com.examw.test.front.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.examw.model.Json;
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
	PaperPreview loadPaperDetail(String paperId,String userId)throws IOException;
	/**
	 * 加载试卷试题的集合
	 * @param paper
	 * @return
	 */
	List<ItemScoreInfo> loadItemsList(PaperPreview paper,boolean isSetCommonTitle);
	/**
	 * 下次再做的提交
	 * @param limitTime 	剩余时间[秒]
	 * @param chooseAnswers	选择题答案  id=answer&id=answer 形式
	 * @param textAnswers	文字题答案
	 * @param model			提交模式[0:下次做,1:交卷]	
	 * @param paperId		试卷的ID
	 * @param userId		用户的ID
	 */
	Json sumbitPaper(Integer limitTime, String chooseAnswers,
			String textAnswers,Integer model, String paperId,String userId)throws IOException;
}
