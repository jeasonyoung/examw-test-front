package com.examw.test.front.controllers;

import java.util.Calendar;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.test.front.model.PaperInfo;
import com.examw.test.front.model.ProductInfo;
import com.examw.test.front.service.IPaperService;
import com.examw.test.front.service.IProductService;
/**
 * 题库控制器
 * @author fengwei.
 * @since 2014年9月9日 上午10:12:35.
 */
@Controller
@RequestMapping("/library")
public class LibraryController {
	private static final Logger logger = Logger.getLogger(LibraryController.class);
	
	@Resource
	private IProductService productService;
	@Resource
	private IPaperService paperService;
	
	@RequestMapping(value = {"","/"}, method = {RequestMethod.GET,RequestMethod.POST})
	public String library(String productId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载题库界面...");
		try{
			ProductInfo info = productService.loadProduct(productId);
			model.addAttribute("PRODUCT", info);
		}catch(Exception e){
			e.printStackTrace();
			if(logger.isDebugEnabled()) logger.debug("加载product异常...");
		}
		return "exam_center";
	}
	
	@RequestMapping(value = "/collection", method = {RequestMethod.GET,RequestMethod.POST})
	public String collection(String productId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载收藏界面...");
		return "collections_list";
	}
	
	@RequestMapping(value = "error", method = {RequestMethod.GET,RequestMethod.POST})
	public String error(String productId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载错题界面...");
		return "error_records";
	}
	
	@RequestMapping(value = "simulate", method = {RequestMethod.GET,RequestMethod.POST})
	public String simulate(String productId,PaperInfo info,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载模拟考试界面...");
		try{
			String userId = this.getUserId(null);
			Map<String,Object> map = this.paperService.loadPaperList(productId, info,userId);
			//productId
			model.addAttribute("PRODUCTID", productId);
			if(!StringUtils.isEmpty(info.getSubjectId())){
				model.addAttribute("CURRENT_SUBJECT_ID", info.getSubjectId());
			}
			if(!StringUtils.isEmpty(info.getYear())){
				model.addAttribute("CURRENT_YEAR", info.getYear());
			}
			if(!StringUtils.isEmpty(info.getType())){
				model.addAttribute("CURRENT_TYPE", info.getType());
			}
//			if(!StringUtils.isEmpty(info.getAreaId())){
//				model.addAttribute("CURRENT_AREA_ID", info.getAearId());
//			}
			//包含科目集合
			model.addAttribute("SUBJECTLIST", map.get("SUBJECTLIST"));
			//包含地区
			model.addAttribute("AREALIST", map.get("AREALIST"));
			//试卷集合
			model.addAttribute("PAPERLIST", map.get("PAPERLIST"));
			//试卷类型
			model.addAttribute("PAPERTYPE", map.get("PAPERTYPE"));
			//页码
			model.addAttribute("PAGE",info.getPage()==null?1:info.getPage());
			//总条数
			model.addAttribute("TOTAL",map.get("TOTAL"));
			model.addAttribute("THIS_YEAR",Calendar.getInstance().get(Calendar.YEAR));
		}catch(Exception e){
			e.printStackTrace();
			if(logger.isDebugEnabled()) logger.debug("加载模拟考试异常...");
		}
		return "simulate";
	}
	
	@RequestMapping(value = "last-exam", method = {RequestMethod.GET,RequestMethod.POST})
	public String lastExam(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载错题界面...");
		return "multi_mode";
	}
	
	@RequestMapping(value = "daily", method = {RequestMethod.GET,RequestMethod.POST})
	public String daily(String productId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载题库界面...");
		return "daily_practice";
	}
	
	private String getUserId(HttpServletRequest request){
		return "34c5421a-a629-4884-9b85-48609028e30b";
	}
}
