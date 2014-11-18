package com.examw.test.front.service.impl;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.front.model.library.ItemErrorRecorveryInfo;
import com.examw.test.front.service.IItemErrorRecorveryService;
import com.examw.test.front.support.HttpUtil;

/**
 * 试题纠错服务接口实现类
 * @author fengwei.
 * @since 2014年11月5日 下午5:09:09.
 */
public class ItemErrorRecorveryServiceImpl implements IItemErrorRecorveryService{
	private static final Logger logger = Logger.getLogger(ItemErrorRecorveryServiceImpl.class);
	//提交地址
	private String api_errorrecorvery_url;
	//web终端代码
	private Integer web_terminal_code;
	
	/**
	 * 设置 提交地址
	 * @param api_errorrecorvery_url
	 * 
	 */
	public void setApi_errorrecorvery_url(String api_errorrecorvery_url) {
		this.api_errorrecorvery_url = api_errorrecorvery_url;
	}

	/**
	 * 设置 web终端代码
	 * @param web_terminal_code
	 * 
	 */
	public void setWeb_terminal_code(Integer web_terminal_code) {
		this.web_terminal_code = web_terminal_code;
	}

	@Override
	public void add(ItemErrorRecorveryInfo info) throws Exception {
		if(logger.isDebugEnabled()) logger.debug("收藏或取消收藏...");
		if(StringUtils.isEmpty(info.getItemId()) || StringUtils.isEmpty(info.getUserId())) 
			return;
		String url = String.format(this.api_errorrecorvery_url,info.getUserId());
		info.setTerminalCode(this.web_terminal_code);
		HttpUtil.upload(url, info);
	}
	
}
