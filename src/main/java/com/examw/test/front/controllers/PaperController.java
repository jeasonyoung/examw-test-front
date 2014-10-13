package com.examw.test.front.controllers;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.Json;
import com.examw.test.front.model.Constant;
import com.examw.test.front.model.library.PaperPreview;
import com.examw.test.front.model.library.PaperSubmitInfo;
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
	@RequestMapping(value ="/{productId}/{paperId}", method = {RequestMethod.GET,RequestMethod.POST})
	public String paperInfo(@PathVariable String productId,@PathVariable String paperId,HttpServletRequest request,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载试卷基本信息...");
		try{
			PaperPreview info = this.paperService.loadPaperInfo(paperId);
			model.addAttribute("PAPER", info);
			model.addAttribute("PRODUCTID",productId);
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
	@RequestMapping(value ="/{productId}/do/multi/{paperId}", method = {RequestMethod.GET,RequestMethod.POST})
	public String paperDetail(@PathVariable String paperId,@PathVariable String productId,Model model,HttpServletRequest request){
		if(logger.isDebugEnabled()) logger.debug("加载试卷试题详情...");
		//TODO 模拟一个用户ID
		String userId = getUserId(null);
		//TODO 判断是否有过做题记录,没有记录,要跳转到上一个页面[试卷基本信息界面]
		try{
			PaperPreview info = this.paperService.loadPaperDetail(paperId,userId,productId);
			model.addAttribute("PAPER", info);
			model.addAttribute("ITEMLIST",this.paperService.loadItemsList(info));
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
			//productID
			model.addAttribute("PRODUCTID",productId);
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
	@RequestMapping(value ="/{productId}/do/single/{paperId}", method = {RequestMethod.GET,RequestMethod.POST})
	public String paperDetailSigleModel(@PathVariable String paperId,@PathVariable String productId,Model model,HttpServletRequest request){
		if(logger.isDebugEnabled()) logger.debug("加载试卷试题详情...");
		//TODO 模拟一个用户ID
		String userId = getUserId(null);
		//TODO 判断是否有过做题记录,没有记录,要跳转到上一个页面[试卷基本信息界面]
		try{
			PaperPreview info = this.paperService.loadPaperDetail(paperId,userId,productId);
			model.addAttribute("PAPER", info);
			model.addAttribute("ITEMS",this.paperService.loadBigItemsList(info));
			model.addAttribute("ITEMLIST",this.paperService.loadItemsList(info));
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
			
			model.addAttribute("PRODUCTID",productId);
		}catch(Exception e){
			e.printStackTrace();
			if(logger.isDebugEnabled()) logger.debug("加载试卷试题详情异常...");
		}
		return "single_mode";
	}
	
	//保存答案 [下次再做]
	@RequestMapping(value ="/submit", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Json sumbitForNextTime(PaperSubmitInfo info,HttpServletRequest request){
		if(logger.isDebugEnabled()) logger.debug("试卷下次再做提交答案...");
		if(info == null) return null;
		String userId = getUserId(request);
		try{
			info.setUserId(userId);
			return this.paperService.sumbitPaper(info);
		}catch(Exception e){
			e.printStackTrace();
			if(logger.isDebugEnabled()) logger.debug("试卷下次再做提交答案异常...");
		}
		return null;
	}
	
	@RequestMapping(value ="/{productId}/analysis/{paperId}", method = {RequestMethod.GET,RequestMethod.POST})
	public String paperAnalysis(@PathVariable String paperId,@PathVariable String productId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载试卷试题解析详情...");
		//TODO 模拟一个用户ID
		String userId = getUserId(null);
		try{
			PaperPreview info = this.paperService.loadPaperAnalysis(paperId,userId,productId);
			model.addAttribute("ITEMLIST",this.paperService.loadItemsList(info));
			model.addAttribute("PAPER", info);
			model.addAttribute("ITEMLIST",this.paperService.loadItemsList(info));
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
			//答对
			model.addAttribute("STATUS_RIGHT", Constant.STATUS_RIGHT);
			//答错
			model.addAttribute("STATUS_WRONG", Constant.STATUS_WRONG);
			
			model.addAttribute("PRODUCTID",productId);
		}catch(Exception e){
			e.printStackTrace();
			if(logger.isDebugEnabled()) logger.debug("加载试卷试题详情异常...");
		}
		return "multi_mode_showanswer";
	}
	
	private String getUserId(HttpServletRequest request){
		return "34c5421a-a629-4884-9b85-48609028e30b";
	}
}
