package com.examw.test.front.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.test.front.model.library.FrontPaperInfo;
import com.examw.test.front.model.library.PaperInfo;
import com.examw.test.front.model.library.PaperPreview;
import com.examw.test.front.model.library.PaperRecordInfo;
import com.examw.test.front.model.library.PaperSubmitInfo;
import com.examw.test.front.model.library.StructureItemInfo;

/**
 * 试卷服务接口
 * @author fengwei.
 * @since 2014年9月11日 上午8:26:13.
 */
public interface IPaperService {
	/**
	 * 获取试卷的类型
	 * @return
	 * @throws IOException
	 */
	Map<String,String> loadPaperType() throws IOException;
	/**
	 * 根据条件加载试卷信息
	 * @param info
	 * @return
	 */
	List<FrontPaperInfo> loadPaperList(String productId)throws IOException;
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
	PaperRecordInfo loadPaperAnalysis(String paperId,String userId,String productId)throws IOException;
	/**
	 * 提取试题的集合
	 * @param paper	试卷信息
	 * @return
	 * @throws IOException
	 */
	public List<StructureItemInfo> loadItemsList(PaperPreview paper)throws IOException;
	public List<StructureItemInfo> loadBigItemsList(PaperPreview paper)throws IOException;
	/**
	 * 试卷基本信息[分页]
	 * @param info
	 * @param list
	 * @return
	 * @throws IOException
	 */
	public DataGrid<FrontPaperInfo> dataGrid(String productId,PaperInfo info,String userId)throws IOException;
}
