package com.examw.test.front.service;

import com.examw.model.Json;


/**
 * 
 * @author fengwei.
 * @since 2014年12月23日 上午9:11:40.
 */
public interface IRemoteService {
	
	
	String httpRequest(String url,String method,String data,String charset)throws Exception;
	
	Json upload(String requestUrl,Object data) throws Exception;
}
