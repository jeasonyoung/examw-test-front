package com.examw.test.front.controllers;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.Json;
import com.examw.test.front.model.Constant;
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
	@RequestMapping(value ="/do/multi", method = {RequestMethod.GET,RequestMethod.POST})
	public String paperDetail(String paperId,Model model,HttpServletRequest request){
		if(logger.isDebugEnabled()) logger.debug("加载试卷试题详情...");
		//TODO 模拟一个用户ID
		String userId = getUserId(null);
		//TODO 判断是否有过做题记录,没有记录,要跳转到上一个页面[试卷基本信息界面]
		try{
			PaperPreview info = this.paperService.loadPaperDetail(paperId,userId);
			model.addAttribute("PAPER", info);
			model.addAttribute("ITEMLIST",this.paperService.loadItemsList(info,false));
			//单选
			model.addAttribute("TYPE_SINGLE_VALUE", Constant.TYPE_SINGLE);
			//多选
			model.addAttribute("TYPE_MULTY_VALUE", Constant.TYPE_MULTY);
			//不定向选
			model.addAttribute("TYPE_UNCERTAIN_VALUE", Constant.TYPE_UNCERTAIN);
			//判断
			model.addAttribute("TYPE_JUDGE_VALUE", Constant.TYPE_JUDGE);
			//问答
			model.addAttribute("TYPE_QANDA_VALUE", Constant.TYPE_QANDA);
			//共提干
			model.addAttribute("TYPE_SHARE_TITLE_VALUE", Constant.TYPE_SHARE_TITLE);
			//共答案
			model.addAttribute("TYPE_SHARE_ANSWER_VALUE", Constant.TYPE_SHARE_ANSWER);
			//判断[正确]
			model.addAttribute("ANSWER_JUDGE_RIGTH",Constant.ANSWER_JUDGE_RIGTH);
			//判断[错误]
			model.addAttribute("ANSWER_JUDGE_WRONG",Constant.ANSWER_JUDGE_WRONG);
			//是否显示答案
			model.addAttribute("IS_SHOW_ANSWER",false);
			
		}catch(Exception e){
			e.printStackTrace();
			if(logger.isDebugEnabled()) logger.debug("加载试卷试题详情异常...");
		}
		return "multi_mode";
	}
	/**
	 * 单题模式
	 * @param paperId
	 * @param model
	 * @return
	 */
	@RequestMapping(value ="/do/single", method = {RequestMethod.GET,RequestMethod.POST})
	public String paperDetailSigleModel(String paperId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载试卷试题详情...");
		//TODO 模拟一个用户ID
		String userId = getUserId(null);
		//TODO 判断是否有过做题记录,没有记录,要跳转到上一个页面[试卷基本信息界面]
		try{
			PaperPreview info = this.paperService.loadPaperDetail(paperId,userId);
			model.addAttribute("PAPER", info);
			model.addAttribute("ITEMLIST",this.paperService.loadItemsList(info,true));
			//单选
			model.addAttribute("TYPE_SINGLE_VALUE", Constant.TYPE_SINGLE);
			//多选
			model.addAttribute("TYPE_MULTY_VALUE", Constant.TYPE_MULTY);
			//不定向选
			model.addAttribute("TYPE_UNCERTAIN_VALUE", Constant.TYPE_UNCERTAIN);
			//判断
			model.addAttribute("TYPE_JUDGE_VALUE", Constant.TYPE_JUDGE);
			//问答
			model.addAttribute("TYPE_QANDA_VALUE", Constant.TYPE_QANDA);
			//共提干
			model.addAttribute("TYPE_SHARE_TITLE_VALUE", Constant.TYPE_SHARE_TITLE);
			//共答案
			model.addAttribute("TYPE_SHARE_ANSWER_VALUE", Constant.TYPE_SHARE_ANSWER);
			//判断[正确]
			model.addAttribute("ANSWER_JUDGE_RIGTH",Constant.ANSWER_JUDGE_RIGTH);
			//判断[错误]
			model.addAttribute("ANSWER_JUDGE_WRONG",Constant.ANSWER_JUDGE_WRONG);
			//是否显示答案
			model.addAttribute("IS_SHOW_ANSWER",false);
		}catch(Exception e){
			e.printStackTrace();
			if(logger.isDebugEnabled()) logger.debug("加载试卷试题详情异常...");
		}
		return "single_mode";
	}
	
	//保存答案 [下次再做]
	@RequestMapping(value ="/submit", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Json sumbitForNextTime(HttpServletRequest request){
		if(logger.isDebugEnabled()) logger.debug("试卷下次再做提交答案...");
		String limitTime = request.getParameter("limitTime");
		String chooseAnswers = request.getParameter("chooseAnswers");
		String textAnswers = request.getParameter("textAnswers");
		String paperId = request.getParameter("paperId");
		String userId = getUserId(request);
		try{
			return this.paperService.sumbitPaper(Integer.valueOf(limitTime),chooseAnswers,textAnswers,0,paperId,userId);
		}catch(Exception e){
			e.printStackTrace();
			if(logger.isDebugEnabled()) logger.debug("试卷下次再做提交答案异常...");
		}
		return null;
	}
	private String getUserId(HttpServletRequest request){
		return "34c5421a-a629-4884-9b85-48609028e30b";
	}
}
