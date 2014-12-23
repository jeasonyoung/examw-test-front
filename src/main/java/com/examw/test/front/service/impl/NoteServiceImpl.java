package com.examw.test.front.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.test.front.model.library.FrontPaperInfo;
import com.examw.test.front.model.product.FrontProductInfo;
import com.examw.test.front.model.record.NoteInfo;
import com.examw.test.front.service.INoteService;
import com.examw.test.front.service.IRemoteService;
import com.examw.test.front.support.JSONUtil;
import com.examw.test.front.support.MethodCacheHelper;

/**
 * 笔记服务接口实现类
 * @author fengwei.
 * @since 2014年9月23日 下午2:51:25.
 */
public class NoteServiceImpl implements INoteService{
	private static final Logger logger = Logger.getLogger(NoteServiceImpl.class);
	private String api_item_notes_url;
	private String api_add_note_url;
	private MethodCacheHelper cacheHelper;
	private IRemoteService remoteService;
	/**
	 * 设置 远程服务
	 * @param remoteService
	 * 
	 */
	public void setRemoteService(IRemoteService remoteService) {
		this.remoteService = remoteService;
	}
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
	
	/**
	 * 设置 缓存帮助类
	 * @param cacheHelper
	 * 
	 */
	public void setCacheHelper(MethodCacheHelper cacheHelper) {
		this.cacheHelper = cacheHelper;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataGrid<NoteInfo> findNotes(NoteInfo info) throws Exception {
		if(logger.isDebugEnabled()) logger.debug("加载笔记信息...");
		Integer page = info.getPage()==null?1:info.getPage();
		Integer rows = info.getRows()==null?10:info.getRows();
		List<NoteInfo> list = (List<NoteInfo>) this.cacheHelper.getCache(FrontPaperInfo.class.getName(), this.getClass().getName(), new Object[]{info.getItemId(),info.getUserId()});
		if(list == null){
			list = this.findNotes(info.getItemId(),info.getUserId());
			if(list!=null)
				this.cacheHelper.putCache(FrontProductInfo.class.getName(), this.getClass().getName(), new Object[]{info.getItemId(),info.getUserId()}, list);
		}
		DataGrid<NoteInfo> datagrid = new DataGrid<NoteInfo>();
		int total = list.size();
		if(total > 0){
			datagrid.setTotal((long) total);
			Integer totalPage = total%rows==0?total/rows:(total/rows+1);
			page = page > totalPage?totalPage:page;
			if(list.size() <= rows)
			{
				datagrid.setRows(list);
			}else
				datagrid.setRows(list.subList((page-1)*rows, page*rows>total?total:page*rows));
		}
		return datagrid;
	}
	
	@SuppressWarnings("unchecked")
	public List<NoteInfo> findNotes(String itemId,String userId) throws Exception{
		if(logger.isDebugEnabled()) logger.debug("加载笔记信息...");
		String url = String.format(this.api_item_notes_url,itemId);
		String data = null;
		if(!StringUtils.isEmpty(userId)){
			data = "userId="+userId;
		}
		String xml = remoteService.httpRequest(url,"GET",data,"utf-8");
		if(!StringUtils.isEmpty(xml)){
			return JSONUtil.JsonToCollection(xml, List.class, NoteInfo.class);
		}
		return null;
	}
	public Json addNote(NoteInfo info) throws Exception{
		if(logger.isDebugEnabled()) logger.debug("添加笔记...");
		String url = String.format(this.api_add_note_url,info.getItemId());
		return remoteService.upload(url, info);
	}
}
