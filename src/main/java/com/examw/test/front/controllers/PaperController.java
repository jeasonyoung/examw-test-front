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
import com.examw.test.front.model.user.User;
import com.examw.test.front.service.IPaperService;
import com.examw.test.front.support.ItemTypeUtil;

/**
 * 试卷控制器
 * @author fengwei.
 * @since 2014年9月12日 下午2:04:52.
 */
@Controller
@RequestMapping("/library/paper")
public class PaperController {
	private static final Logger logger = Logger.getLogger(PaperController.class);
	//试卷服务接口
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
		String userId = this.getUserId(request);
		//判断是否有过做题记录,没有记录,要跳转到上一个页面[试卷基本信息界面]
		try{
			PaperPreview info = this.paperService.findPaperDetail(paperId,userId,productId);
			model.addAttribute("PAPER", info);
			model.addAttribute("ITEMLIST",this.paperService.findItemsList(info));
			//题型
			ItemTypeUtil.loadItemType(model);
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
		//模拟一个用户ID
		String userId = this.getUserId(request);
		//判断是否有过做题记录,没有记录,要跳转到上一个页面[试卷基本信息界面]
		try{
			PaperPreview info = this.paperService.findPaperDetail(paperId,userId,productId);
			model.addAttribute("PAPER", info);
			model.addAttribute("ITEMS",this.paperService.findBigItemsList(info));
			model.addAttribute("ITEMLIST",this.paperService.findItemsList(info));
			//题型
			ItemTypeUtil.loadItemType(model);
			//是否显示答案
			model.addAttribute("IS_SHOW_ANSWER",false);
			
			model.addAttribute("PRODUCTID",productId);
		}catch(Exception e){
			e.printStackTrace();
			if(logger.isDebugEnabled()) logger.debug("加载试卷试题详情异常...");
		}
		return "single_mode";
	}
	
	/**
	 * 保存答案 [下次再做]
	 * @param info			试卷提交信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value ="/submit", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Json sumbitForNextTime(PaperSubmitInfo info,HttpServletRequest request){
		if(logger.isDebugEnabled()) logger.debug("试卷下次再做提交答案...");
		if(info == null) return null;
		String userId = this.getUserId(request);
		try{
			info.setUserId(userId);
			return this.paperService.sumbitPaper(info);
		}catch(Exception e){
			e.printStackTrace();
			if(logger.isDebugEnabled()) logger.debug("试卷下次再做提交答案异常...");
		}
		return null;
	}
	/**
	 * 获取试卷的解析
	 * @param paperId		试卷ID
	 * @param productId		产品ID
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value ="/{productId}/analysis/{paperId}", method = {RequestMethod.GET,RequestMethod.POST})
	public String paperAnalysis(@PathVariable String paperId,@PathVariable String productId,Model model,HttpServletRequest request){
		if(logger.isDebugEnabled()) logger.debug("加载试卷试题解析详情...");
		//模拟一个用户ID
		String userId = this.getUserId(request);
		try{
			PaperPreview info = this.paperService.findPaperAnalysis(paperId,userId,productId);
			model.addAttribute("ITEMLIST",this.paperService.findItemsList(info));
			model.addAttribute("PAPER", info);
			model.addAttribute("ITEMLIST",this.paperService.findItemsList(info));
			//题型
			ItemTypeUtil.loadItemType(model);
			//答对
			model.addAttribute("STATUS_RIGHT", Constant.STATUS_RIGHT);
			//答错
			model.addAttribute("STATUS_WRONG", Constant.STATUS_WRONG);
			
			model.addAttribute("PRODUCTID",productId);
		}catch(Exception e){
			e.printStackTrace();
			if(logger.isDebugEnabled()) logger.debug("加载试卷解析详情异常..."+e.getMessage());
			if(e.getMessage().equals("考试未完成"))
				return "redirect:/library/paper/"+productId+"/do/multi/"+paperId;
			return "redirect:/library/paper/"+productId+"/"+paperId;
		}
		return "multi_mode_showanswer";
	}
	
	/**
	 * 获取试卷的解析 [根据试卷记录的ID查找记录]
	 * @param paperId			试卷ID
	 * @param recordId			考试记录ID
	 * @param productId			产品ID
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value ="/{productId}/analysis/{paperId}/{recordId}", method = {RequestMethod.GET,RequestMethod.POST})
	public String paperRecordAnalysis(@PathVariable String paperId,@PathVariable String recordId,@PathVariable String productId,Model model,HttpServletRequest request){
		if(logger.isDebugEnabled()) logger.debug("加载试卷试题解析详情...");
		//TODO 模拟一个用户ID
		String userId = this.getUserId(request);
		try{
			PaperPreview info = this.paperService.findPaperAnalysis(paperId,recordId,userId,productId);
			model.addAttribute("ITEMLIST",this.paperService.findItemsList(info));
			model.addAttribute("PAPER", info);
			model.addAttribute("ITEMLIST",this.paperService.findItemsList(info));
			//题型
			ItemTypeUtil.loadItemType(model);
			//答对
			model.addAttribute("STATUS_RIGHT", Constant.STATUS_RIGHT);
			//答错
			model.addAttribute("STATUS_WRONG", Constant.STATUS_WRONG);
				
			model.addAttribute("PRODUCTID",productId);
		}catch(Exception e){
			e.printStackTrace();
			if(logger.isDebugEnabled()) logger.debug("加载试卷解析详情异常..."+e.getMessage());
			if(e.getMessage().equals("考试未完成"))
				return "redirect:/library/paper/"+productId+"/do/multi/"+paperId;
			return "redirect:/library/paper/"+productId+"/"+paperId;
		}
		return "multi_mode_showanswer";
	}
	
	private String getUserId(HttpServletRequest request){
		return ((User)(request.getSession().getAttribute("USER"))).getProductUserId();
	}
}
