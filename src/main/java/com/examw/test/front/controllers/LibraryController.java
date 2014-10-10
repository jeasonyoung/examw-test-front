package com.examw.test.front.controllers;

import java.util.Calendar;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.model.DataGrid;
import com.examw.test.front.model.library.FrontItemInfo;
import com.examw.test.front.model.library.FrontPaperInfo;
import com.examw.test.front.model.library.PaperInfo;
import com.examw.test.front.model.product.ProductInfo;
import com.examw.test.front.service.IErrorItemService;
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
	@Resource
	private IErrorItemService errorItemService;
	
	@RequestMapping(value = "/{productId}", method = {RequestMethod.GET,RequestMethod.POST})
	public String library(@PathVariable String productId,Model model){
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
	
	@RequestMapping(value = "/collection/{productId}", method = {RequestMethod.GET,RequestMethod.POST})
	public String collection(@PathVariable String productId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载收藏界面...");
		model.addAttribute("PRODUCTID", productId);
		return "collections_list";
	}
	
	@RequestMapping(value = "/error/{productId}", method = {RequestMethod.GET,RequestMethod.POST})
	public String error(@PathVariable String productId,FrontItemInfo info,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载错题界面...");
		try{
			String userId = this.getUserId(null);
			DataGrid<FrontItemInfo> data = this.errorItemService.dataGrid(productId, info, userId);
			//productId
			model.addAttribute("PRODUCTID", productId);
			if(!StringUtils.isEmpty(info.getSubjectId())){
				model.addAttribute("CURRENT_SUBJECT_ID", info.getSubjectId());
			}
			model.addAttribute("PAGE",info.getPage()==null?1:info.getPage());
			model.addAttribute("ITEMLIST",data.getRows());
			model.addAttribute("TOTAL",data.getTotal());
			model.addAttribute("SUBJECTLIST", this.productService.loadProductSubjects(productId));
			model.addAttribute("");
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return "error_records";
	}
	
	@RequestMapping(value = "/error/{productId}/item/{itemId}", method = {RequestMethod.GET,RequestMethod.POST})
	public String errorDetail(@PathVariable String productId,@PathVariable String itemId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载错题详细界面...");
		model.addAttribute("PRODUCTID", productId);
		return "error_records";
	}
	
	
	@RequestMapping(value = "/simulate/{productId}", method = {RequestMethod.GET,RequestMethod.POST})
	public String simulate(@PathVariable String productId,PaperInfo info,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载模拟考试界面[试卷列表]...");
		try{
			String userId = this.getUserId(null);
			DataGrid<FrontPaperInfo> data = this.paperService.dataGrid(productId, info, userId);
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
			if(!StringUtils.isEmpty(info.getAreaId())){
				model.addAttribute("CURRENT_AREA_ID", info.getAreaId());
			}
			//包含科目集合
			model.addAttribute("SUBJECTLIST", this.productService.loadProductSubjects(productId));
			//包含地区
			model.addAttribute("AREALIST", this.productService.loadProductAreas(productId));
			//试卷集合
			model.addAttribute("PAPERLIST", data.getRows());
			//试卷类型
			model.addAttribute("PAPERTYPE", this.paperService.loadPaperType());
			//页码
			model.addAttribute("PAGE",info.getPage()==null?1:info.getPage());
			//总条数
			model.addAttribute("TOTAL",data.getTotal());
			model.addAttribute("THIS_YEAR",Calendar.getInstance().get(Calendar.YEAR));
		}catch(Exception e){
			e.printStackTrace();
			if(logger.isDebugEnabled()) logger.debug("加载模拟考试[试卷列表]异常...");
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
