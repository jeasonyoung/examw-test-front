package com.examw.test.front.controllers;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.test.front.model.record.Collection;
import com.examw.test.front.model.record.NoteInfo;
import com.examw.test.front.model.user.User;
import com.examw.test.front.service.ICollectionService;
import com.examw.test.front.service.INoteService;

/**
 * 试题相关控制器
 * @author fengwei.
 * @since 2014年9月23日 下午2:52:30.
 */
@Controller
@RequestMapping("/library/item")
public class ItemController {
	private static final Logger logger = Logger.getLogger(PaperController.class);
	@Resource
	private INoteService noteService;
	@Resource
	private ICollectionService collectionService;
	
	@RequestMapping(value ="collect", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Json collectOrCancel(Collection info,HttpSession session){
		if(logger.isDebugEnabled()) logger.debug("收藏或取消收藏...");
		if(info == null) return null;
		String userId = this.getUserId(session);
		try{
			info.setUserId(userId);
			return this.collectionService.collectOrCancel(info);
		}catch(Exception e){
			e.printStackTrace();
			if(logger.isDebugEnabled()) logger.debug("收藏或取消收藏失败...");
		}
		return null;
	}
	
	@RequestMapping(value ="notes", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public DataGrid<NoteInfo> findNotes(NoteInfo info,String model){
		if(logger.isDebugEnabled()) logger.debug("查询笔记数据...");
		String userId = getUserId(null);
		try{
			if("all".equals(model))
				return this.noteService.findNotes(info);
			else{
				info.setUserId(userId);
				return this.noteService.findNotes(info);
			}
				
		}catch(Exception e){
			e.printStackTrace();
			if(logger.isDebugEnabled()) logger.debug("查询笔记数据...");
		}
		return null;
	}
	
	@RequestMapping(value ="addnote", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Json addNote(NoteInfo info){
		if(logger.isDebugEnabled()) logger.debug("添加笔记数据...");
		String userId = getUserId(null);
		try{
			info.setUserId(userId);
			info.setUsername("username");
			return this.noteService.addNote(info);
		}catch(Exception e){
			e.printStackTrace();
			if(logger.isDebugEnabled()) logger.debug("添加笔记数据...");
		}
		return null;
	}
	
	private String getUserId(HttpSession session){
		return ((User)(session.getAttribute("USER"))).getProductUserId();
	}
}
