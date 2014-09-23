package com.examw.test.front.service.impl;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.model.Json;
import com.examw.test.front.model.Note;
import com.examw.test.front.model.NoteInfo;
import com.examw.test.front.service.INoteService;
import com.examw.test.front.support.HttpUtil;
import com.examw.test.front.support.JSONUtil;

/**
 * 笔记服务接口实现类
 * @author fengwei.
 * @since 2014年9月23日 下午2:51:25.
 */
public class NoteServiceImpl implements INoteService{
	private static final Logger logger = Logger.getLogger(NoteServiceImpl.class);
	private String api_item_notes_url;
	private String api_add_note_url;
	/**
	 * 设置 笔记数据查询数据接口地址
	 * @param api_item_notes_url
	 * 
	 */
	public void setApi_item_notes_url(String api_item_notes_url) {
		this.api_item_notes_url = api_item_notes_url;
	}
	
	/**
	 * 设置 添加笔记接口地址
	 * @param api_add_note_url
	 * 
	 */
	public void setApi_add_note_url(String api_add_note_url) {
		this.api_add_note_url = api_add_note_url;
	}


	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> findNotes(NoteInfo info) throws IOException {
		if(logger.isDebugEnabled()) logger.debug("加载笔记信息...");
		String url = String.format(this.api_item_notes_url,info.getStructureItemId());
		String data = "itemId="+info.getItemId();
		if(!StringUtils.isEmpty(info.getUserId())){
			data = "&userId="+info.getUserId();
		}
		String xml = HttpUtil.httpRequest(url,"GET",data,"utf-8");
		if(!StringUtils.isEmpty(xml)){
			return JSONUtil.JsonToCollection(xml, Map.class, String.class,Object.class);
		}
		return null;
	}
	
	public Json addNote(Note info) throws IOException{
		if(logger.isDebugEnabled()) logger.debug("添加笔记...");
		String url = String.format(this.api_add_note_url,info.getStructureItemId(),info.getUserId());
		String data = "itemId="+info.getItemId()+"&content="+URLEncoder.encode(info.getContent(), "utf-8");
		String xml = HttpUtil.httpRequest(url,"GET",data,"utf-8");
		if(!StringUtils.isEmpty(xml)){
			return JSONUtil.JsonToObject(xml, Json.class);
		}
		return null;
	}
}
