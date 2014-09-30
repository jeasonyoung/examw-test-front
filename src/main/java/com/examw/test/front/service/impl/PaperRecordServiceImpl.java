package com.examw.test.front.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.front.model.library.PaperRecordInfo;
import com.examw.test.front.service.IPaperRecordService;
import com.examw.test.front.support.HttpUtil;
import com.examw.test.front.support.JSONUtil;

/**
 * 
 * @author fengwei.
 * @since 2014年9月30日 下午2:46:40.
 */
public class PaperRecordServiceImpl implements IPaperRecordService{
	private static final Logger logger = Logger.getLogger(PaperRecordServiceImpl.class);
	private String api_paper_records_url;
	
	/**
	 * 设置 试卷记录数据接口地址
	 * @param api_paper_records_url
	 * 
	 */
	public void setApi_paper_records_url(String api_paper_records_url) {
		this.api_paper_records_url = api_paper_records_url;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<PaperRecordInfo> loadPaperRecords(PaperRecordInfo info) throws IOException {
		if(logger.isDebugEnabled()) logger.debug("加载笔记信息...");
		String url = this.api_paper_records_url;
		String data = "";
		String xml = HttpUtil.httpRequest(url,"GET",data,"utf-8");
		if(!StringUtils.isEmpty(xml)){
			return JSONUtil.JsonToCollection(xml, List.class, PaperRecordInfo.class);
		}
		return null;
	}
}
