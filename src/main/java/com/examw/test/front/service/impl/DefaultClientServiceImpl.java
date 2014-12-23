package com.examw.test.front.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.examw.model.Json;
import com.examw.test.front.service.IRemoteService;
import com.examw.test.front.support.DigestClientUtil;
import com.examw.test.front.support.JSONUtil;

/**
 * 默认的远程服务类
 * @author fengwei.
 * @since 2014年12月23日 上午9:12:22.
 */
public class DefaultClientServiceImpl implements IRemoteService{
	private String username;
	private String password;
	
	public DefaultClientServiceImpl(String username,String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public String httpRequest(String url, String method, String data,
			String charset) throws Exception{
		return DigestClientUtil.sendDigestRequest(this.username, this.password, method, url, data);
	}
	
	public Json upload(String requestUrl, Object data) throws Exception {
		String post = JSONUtil.ObjectToJson(data);
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-type","application/json;charset=UTF-8");
		String callback = DigestClientUtil.sendDigestRequest(this.username, this.password,headers, "POST", requestUrl, post);
		Json json = JSONUtil.JsonToObject(callback, Json.class);
		if(json == null){
			throw new Exception("反馈收据转换失败！callback=>" + callback);
		}
		return json;
	}
}
