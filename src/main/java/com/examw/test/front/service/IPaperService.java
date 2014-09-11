package com.examw.test.front.service;

import java.io.IOException;
import java.util.Map;

import com.examw.test.front.model.PaperInfo;

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
}
