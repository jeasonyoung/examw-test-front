package com.examw.test.front.controllers;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.test.front.service.IChapterService;

/**
 * 章节控制器 [章节练习,章节内容]
 * @author fengwei.
 * @since 2014年9月9日 下午3:23:08.
 */
@Controller
@RequestMapping("/library/chapter")
public class ChapterController {
	private static final Logger logger = Logger.getLogger(ChapterController.class);
	
	@Resource
	private IChapterService chapterService;
	/**
	 * 章节联系界面
	 * @param examId
	 * @param subjectId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"","/"}, method = {RequestMethod.GET,RequestMethod.POST})
	public String chapters(String examId,String subjectId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载章节练习...");
		try{
			Map<String,Object> map = this.chapterService.loadExamAndChapterInfo(examId,subjectId);
			model.addAttribute("EXAMID", examId);
			model.addAttribute("SUBJECTLIST", map.get("SUBJECTLIST"));
			model.addAttribute("CHAPTERLIST", map.get("CHAPTERLIST"));
		}catch(Exception e){
			e.printStackTrace();
			if(logger.isDebugEnabled()) logger.debug("加载章节信息异常...");
		}
		return "chapter_list";
	}
	/**
	 * 章节详情
	 * @param pid
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/detail", method = {RequestMethod.GET,RequestMethod.POST})
	public String chapterDetail(String pid,String id,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载章节练习...");
		try{
			Map<String,Object> map = this.chapterService.loadChapterDetail(pid,id);
			model.addAttribute("CHAPTER", map.get("CHAPTER"));
			model.addAttribute("KNOWLEDGE", map.get("KNOWLEDGE"));
		}catch(Exception e){
			e.printStackTrace();
			if(logger.isDebugEnabled()) logger.debug("加载章节信息异常...");
		}
		return "chapter_knowledge";
	}
}
