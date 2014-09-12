package com.examw.test.front.controllers;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.test.front.model.PaperPreview;
import com.examw.test.front.service.IPaperService;

/**
 * 试卷控制器
 * @author fengwei.
 * @since 2014年9月12日 下午2:04:52.
 */
@Controller
@RequestMapping("/library/paper")
public class PaperController {
	private static final Logger logger = Logger.getLogger(PaperController.class);
	@Resource
	private IPaperService paperService;
	/**
	 * 加载试卷基本信息
	 * @param paperId
	 * @param model
	 * @return
	 */
	@RequestMapping(value ="", method = {RequestMethod.GET,RequestMethod.POST})
	public String paperInfo(String paperId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载试卷基本信息...");
		try{
			PaperPreview info = this.paperService.loadPaperInfo(paperId);
			model.addAttribute("PAPER", info);
		}catch(Exception e){
			e.printStackTrace();
			if(logger.isDebugEnabled()) logger.debug("加载试卷基本信息...");
		}
		return "paper_info";
	}
	/**
	 * 加载试卷详细数据
	 * @param paperId
	 * @param model
	 * @return
	 */
	@RequestMapping(value ="/do", method = {RequestMethod.GET,RequestMethod.POST})
	public String paperDetail(String paperId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载试卷试题详情...");
		//TODO 判断是否有过做题记录,没有记录,要跳转到上一个页面[试卷基本信息界面]
		try{
			PaperPreview info = this.paperService.loadPaperDetail(paperId);
			model.addAttribute("PAPER", info);
		}catch(Exception e){
			e.printStackTrace();
			if(logger.isDebugEnabled()) logger.debug("加载试卷试题详情异常...");
		}
		return "multi_mode";
	}
}
