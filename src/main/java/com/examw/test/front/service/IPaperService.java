package com.examw.test.front.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.test.front.model.library.FrontPaperInfo;
import com.examw.test.front.model.library.PaperInfo;
import com.examw.test.front.model.library.PaperPreview;
import com.examw.test.front.model.library.PaperSubmitInfo;
import com.examw.test.front.model.library.StructureItemInfo;
import com.examw.test.front.model.record.UserPaperRecordInfo;

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
	 * 加载所有试题的年份
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	List<Integer> loadPaperYear(String productId) throws Exception;
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
	PaperPreview findPaperDetail(String paperId,String userId,String productId)throws Exception;
	/**
	 * 加载试卷试题的集合
	 * @param paper
	 * @return
	 */
	/**
	 * 下次再做的提交
	 * @param info 提交试卷答案信息
	 */
	Json sumbitPaper(PaperSubmitInfo info)throws Exception;
	/**
	 * 加载试卷试题解析
	 * @param paperId
	 * @param userId
	 * @return
	 * @throws IOException
	 */
	PaperPreview findPaperAnalysis(String paperId,String userId,String productId)throws Exception;
	PaperPreview findPaperAnalysis(String paperId,String recordId,String userId,String productId)throws Exception;
	/**
	 * 提取试题的集合
	 * @param paper	试卷信息
	 * @return
	 * @throws IOException
	 */
	public List<StructureItemInfo> findItemsList(PaperPreview paper)throws IOException;
	public List<StructureItemInfo> findBigItemsList(PaperPreview paper)throws IOException;
	/**
	 * 试卷基本信息[分页]
	 * @param info
	 * @param list
	 * @return
	 * @throws IOException
	 */
	public DataGrid<FrontPaperInfo> dataGrid(String productId,PaperInfo info,String userId)throws Exception;
	/**
	 * 试卷详细信息
	 * @param paperId
	 * @return
	 * @throws IOException
	 */
	PaperPreview findPaperDetail(String paperId)throws IOException;
	/**
	 * 试卷最新记录
	 * @param userId
	 * @param paperId
	 * @return
	 * @throws Exception
	 */
	UserPaperRecordInfo findLastedRecord(String userId,String paperId)throws Exception;
	UserPaperRecordInfo findProductLastedRecord(String userId,String productId)throws Exception;
	/**
	 * 产品下所有试卷记录
	 * @param userId
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	List<UserPaperRecordInfo> findUserPaperRecords(String userId,String productId)throws Exception;
	/**
	 * 分页数据
	 * @param userId
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	DataGrid<UserPaperRecordInfo> recordDataGrid(String userId,String productId,PaperInfo info)throws Exception;
	/**
	 * 根据条件加载每日一练试卷信息
	 * @param info
	 * @return
	 */
	List<FrontPaperInfo> findDailyPaperList(String productId,Calendar date,String userId)throws Exception;
}
