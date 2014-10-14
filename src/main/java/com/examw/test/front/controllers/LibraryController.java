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
import com.examw.test.front.model.Constant;
import com.examw.test.front.model.library.FrontItemInfo;
import com.examw.test.front.model.library.FrontPaperInfo;
import com.examw.test.front.model.library.PaperInfo;
import com.examw.test.front.model.product.FrontProductInfo;
import com.examw.test.front.model.record.Collection;
import com.examw.test.front.model.record.UserPaperRecordInfo;
import com.examw.test.front.service.ICollectionService;
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
	@Resource
	private ICollectionService collectionService;
	
	//产品题库中心
	@RequestMapping(value = "/{productId}", method = {RequestMethod.GET,RequestMethod.POST})
	public String library(@PathVariable String productId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载题库界面...");
		try{
			FrontProductInfo info = productService.loadProduct(productId);
			model.addAttribute("PRODUCT", info);
			model.addAttribute("PRODUCTID",productId);
		}catch(Exception e){
			e.printStackTrace();
			if(logger.isDebugEnabled()) logger.debug("加载product异常...");
		}
		return "exam_center";
	}
	
	//收藏列表
	@RequestMapping(value = "/collection/{productId}", method = {RequestMethod.GET,RequestMethod.POST})
	public String collection(@PathVariable String productId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载收藏界面...");
		model.addAttribute("PRODUCTID", productId);
		try{
			String userId = this.getUserId(null);
			model.addAttribute("SUBJECTLIST", this.collectionService.loadCollectionSubjects(productId, userId));
		}catch(Exception e){
			e.printStackTrace();
		}
		return "collections_list";
	}
	
	@RequestMapping(value = "/collection/{productId}/items/{subjectId}", method = {RequestMethod.GET,RequestMethod.POST})
	public String collectionDetail(@PathVariable String productId,@PathVariable String subjectId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载错题详细界面...");
		model.addAttribute("PRODUCTID", productId);
		Collection info = new Collection();
		info.setProductId(productId);
		info.setUserId(this.getUserId(null));
		info.setSubjectId(subjectId);
		try{
			model.addAttribute("ITEMLIST",this.collectionService.loadCollectionItems(info));
		}catch(Exception e){
			e.printStackTrace();
		}
		return "error_records";
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
	
	/**
	 * 获取每日一练
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/daily/{productId}", method = RequestMethod.GET)
	public String daily(@PathVariable String productId,Model model){
		return "daily_practice";
	}
	
	/**
	 * 获取模拟考场
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/simulate", method = RequestMethod.GET)
	public String simulate(Model model){
		return "simulate";
	}
	
	/**
	 * 获取章节练习
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/chapter/{productId}", method = RequestMethod.GET)
	public String chapter(@PathVariable String productId,Model model){
		model.addAttribute("PRODUCTID",productId);
		return "chapter_list";
	}
	
	/**
	 * 获取练习记录
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/record/{productId}", method = {RequestMethod.GET,RequestMethod.POST})
	public String records(@PathVariable String productId,PaperInfo info,Model model){
		try{
			String userId = this.getUserId(null);
			//包含科目集合
			model.addAttribute("SUBJECTLIST", this.productService.loadProductSubjects(productId));
			DataGrid<UserPaperRecordInfo> datagrid = this.paperService.recordDataGrid(userId, productId,info);
			//试卷类型
			model.addAttribute("PAPERTYPE", this.paperService.loadPaperType());
			model.addAttribute("PAPERLIST",datagrid.getRows());
			model.addAttribute("PRODUCTID",productId);
			if(!StringUtils.isEmpty(info.getSubjectId())){
				model.addAttribute("CURRENT_SUBJECT_ID", info.getSubjectId());
			}
			if(info.getStatus()!=null){
				model.addAttribute("CURRENT_STATUS", info.getStatus());
			}
			if(!StringUtils.isEmpty(info.getType())){
				model.addAttribute("CURRENT_TYPE", info.getType());
			}
			//页码
			model.addAttribute("PAGE",info.getPage()==null?1:info.getPage());
			//总条数
			model.addAttribute("TOTAL",datagrid.getTotal());
			model.addAttribute("STATUS_DONE",Constant.STATUS_DONE);
			model.addAttribute("STATUS_UNDONE",Constant.STATUS_UNDONE);
		}catch(Exception e){
			e.printStackTrace();
			if(logger.isDebugEnabled()) logger.debug("加载考试记录数据异常...");
		}
		return "practice_records";
	}
	
	/**
	 * 获取题库首页
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/center/{productId}", method = RequestMethod.GET)
	public String center(@PathVariable String productId,Model model){
		model.addAttribute("PRODUCTID",productId);
		return "exam_center";
	}
	
	private String getUserId(HttpServletRequest request){
		return "34c5421a-a629-4884-9b85-48609028e30b";
	}
}
