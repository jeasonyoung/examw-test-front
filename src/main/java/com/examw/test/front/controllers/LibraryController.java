package com.examw.test.front.controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.test.front.model.Constant;
import com.examw.test.front.model.library.FrontPaperInfo;
import com.examw.test.front.model.library.PaperInfo;
import com.examw.test.front.model.library.StructureItemInfo;
import com.examw.test.front.model.product.FrontProductInfo;
import com.examw.test.front.model.product.SubjectInfo;
import com.examw.test.front.model.record.Collection;
import com.examw.test.front.model.record.UserPaperRecordInfo;
import com.examw.test.front.model.user.User;
import com.examw.test.front.service.ICollectionService;
import com.examw.test.front.service.IErrorItemService;
import com.examw.test.front.service.IPaperService;
import com.examw.test.front.service.IProductService;
import com.examw.test.front.support.DateUtil;
import com.examw.test.front.support.ItemTypeUtil;
import com.examw.test.front.support.PaperTypeUtil;
/**
 * 题库控制器
 * @author fengwei.
 * @since 2014年9月9日 上午10:12:35.
 */
@Controller
@RequestMapping("/library")
public class LibraryController {
	private static final Logger logger = Logger.getLogger(LibraryController.class);
	//产品服务接口
	@Resource
	private IProductService productService;
	//试卷服务接口
	@Resource
	private IPaperService paperService;
	//错题服务接口
	@Resource
	private IErrorItemService errorItemService;
	//收藏服务接口
	@Resource
	private ICollectionService collectionService;
	
	/**
	 * 产品题库中心
	 * @param productId		产品ID
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{productId}", method = {RequestMethod.GET,RequestMethod.POST})
	public String library(@PathVariable String productId,HttpServletRequest request,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载题库界面...");
		try{
			String userId = this.getUserId(request);
			FrontProductInfo data = productService.loadProduct(productId);
			if(data == null)
				return "redirect:/404";
			request.getSession().setAttribute("SESSIONPRODUCT", data);
			model.addAttribute("PRODUCT", data);
			model.addAttribute("PRODUCTID",productId);
			
			//2015.01.05 将产品ID放入session中
//			request.getSession().setAttribute("PRODUCTID", productId);
			//2015.01.05
			
			//计算错题个数
			List<SubjectInfo> subjectList = this.productService.loadProductSubjects(productId);
			StructureItemInfo info = new StructureItemInfo();
			info.setSubjectId("");
			if(subjectList!=null && subjectList.size()>0){
				 for(SubjectInfo s:subjectList){
					 info.setSubjectId(info.getSubjectId()+s.getId()+",");
				}
			}
			model.addAttribute("TOTALERROR",this.errorItemService.dataGrid(productId, info, userId).getTotal());
			//计算收藏个数
			model.addAttribute("SUBJECTLIST", this.collectionService.findCollectionSubjects(productId, userId));
		}catch(Exception e){
			e.printStackTrace();
			if(logger.isDebugEnabled()) logger.debug("加载product异常...");
		}
		return "exam_center";
	}
	
	/**
	 * 收藏界面
	 * @param productId		产品ID
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/collection/{productId}", method = {RequestMethod.GET,RequestMethod.POST})
	public String collection(@PathVariable String productId,Model model,HttpServletRequest request){
		if(logger.isDebugEnabled()) logger.debug("加载收藏界面...");
		try{
			FrontProductInfo data = productService.loadProduct(productId);
			if(data == null)
				return "redirect:/404";
			model.addAttribute("PRODUCTID", productId);
			String userId = this.getUserId(request);
			model.addAttribute("SUBJECTLIST", this.collectionService.findCollectionSubjects(productId, userId));
		}catch(Exception e){
			e.printStackTrace();
		}
		return "collections_list";
	}
	/**
	 * 收藏试题的列表
	 * @param productId		产品ID
	 * @param subjectId		科目ID
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/collection/{productId}/items/{subjectId}", method = {RequestMethod.GET,RequestMethod.POST})
	public String collectionDetail(@PathVariable String productId,@PathVariable String subjectId,Model model,HttpServletRequest request){
		if(logger.isDebugEnabled()) logger.debug("加载收藏试题详细界面...");
		model.addAttribute("PRODUCTID", productId);
		Collection info = new Collection();
		info.setProductId(productId);
		info.setUserId(this.getUserId(request));
		info.setSubjectId(subjectId);
		try{
			model.addAttribute("ITEMLIST",this.collectionService.findCollectionItemList(info));
			//题型
			ItemTypeUtil.loadItemType(model);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "collections_detail";
	}
	
	/**
	 * 错题列表
	 * @param productId		产品ID
	 * @param info			查询信息
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/error/{productId}", method = {RequestMethod.GET,RequestMethod.POST})
	public String error(@PathVariable String productId,StructureItemInfo info,Model model,HttpServletRequest request){
		if(logger.isDebugEnabled()) logger.debug("加载错题界面...");
		try{
			String userId = this.getUserId(request);
			List<SubjectInfo> subjectList = this.productService.loadProductSubjects(productId);
			model.addAttribute("SUBJECTLIST", subjectList);
			if(!StringUtils.isEmpty(info.getSubjectId())){
				model.addAttribute("CURRENT_SUBJECT_ID", info.getSubjectId());
			}else{
				info.setSubjectId("");
				 if(subjectList!=null && subjectList.size()>0){
					 for(SubjectInfo s:subjectList){
						 info.setSubjectId(info.getSubjectId()+s.getId()+",");
					 }
				 }
			}
			DataGrid<StructureItemInfo> data = this.errorItemService.dataGrid(productId, info, userId);
			//productId
			model.addAttribute("PAGE",info.getPage()==null?1:info.getPage());
			model.addAttribute("ITEMLIST",data.getRows());
			model.addAttribute("TOTAL",data.getTotal());
			model.addAttribute("PRODUCTID", productId);
			model.addAttribute("");
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return "error_records";
	}
	/**
	 * 错题详细
	 * @param productId 	产品ID
	 * @param itemId		题目ID
	 * @param flag			是否加载答案
	 * @param model			
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/error/{productId}/item/{itemId}", method = {RequestMethod.GET,RequestMethod.POST})
	public String errorDetail(@PathVariable String productId,@PathVariable String itemId,String flag,Model model,HttpServletRequest request){
		if(logger.isDebugEnabled()) logger.debug("加载错题详细界面...");
		
		try{
			FrontProductInfo data = productService.loadProduct(productId);
			if(data == null)
				return "redirect:/404";
			model.addAttribute("PRODUCTID", productId);
			String userId = this.getUserId(request);
			List<SubjectInfo> subjectList = this.productService.loadProductSubjects(productId);
			String subjectId = "";
			if(subjectList!=null && subjectList.size()>0){
				for(SubjectInfo s:subjectList){
					subjectId = subjectId+s.getId()+",";
				}
			}
			//带有缓存的
			Map<String,Object> map = this.errorItemService.loadItemDetail(productId,subjectId,userId,itemId);
			model.addAttribute("LAST_ITEM_ID", map.get("LAST_ITEM_ID"));
			model.addAttribute("NEXT_ITEM_ID",map.get("NEXT_ITEM_ID"));
			model.addAttribute("ITEM",map.get("ITEM"));
			model.addAttribute("ITEMCONTENT", map.get("ITEMCONTENT"));
			//题型
			ItemTypeUtil.loadItemType(model);
			//是否显示答案
			model.addAttribute("IS_SHOW_ANSWER",StringUtils.isEmpty(flag)?true:false);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "errors_detail";
	}
	
	/**
	 * 模拟考场界面[试卷列表]
	 * @param productId		产品ID
	 * @param info			查询信息
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/simulate/{productId}", method = {RequestMethod.GET,RequestMethod.POST})
	public String simulate(@PathVariable String productId,PaperInfo info,Model model,HttpServletRequest request){
		if(logger.isDebugEnabled()) logger.debug("加载模拟考试界面[试卷列表]...");
		try{
			if(productService.loadProduct(productId) == null)
				return "redirect:/404";
			String userId = this.getUserId(request);
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
			//model.addAttribute("AREALIST", this.productService.loadProductAreas(productId));
			//包含年份
			model.addAttribute("YEARLIST", this.paperService.loadPaperYear(productId));
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
	/**
	 * 上一次考试
	 * @param productId		产品ID
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/last-exam/{productId}", method = {RequestMethod.GET,RequestMethod.POST})
	public String lastExam(@PathVariable String productId,Model model,HttpServletRequest request){
		if(logger.isDebugEnabled()) logger.debug("加载错题界面...");
		String userId = this.getUserId(request);
		try{
			if(productService.loadProduct(productId) == null)
				return "redirect:/404";
			model.addAttribute("PRODUCTID", productId);
			UserPaperRecordInfo record = this.paperService.findProductLastedRecord(userId, productId);
			if(record == null){
				return "redirect:/simulate/"+productId;
			}
			if(record.getStatus().equals(Constant.STATUS_DONE)){
				return "redirect:/library/paper/"+productId+"/analysis/"+record.getPaperId();
			}else{
				return "redirect:/library/paper/"+productId+"/do/multi/"+record.getPaperId();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:/simulate/"+productId;
	}
	
	/**
	 * 获取每日一练
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/daily/{productId}/{date}", method = RequestMethod.GET)
	public String daily(@PathVariable String productId,@PathVariable String date,HttpServletRequest request,Model model){
		if(logger.isDebugEnabled()) logger.debug("获取每日一练试卷...");
		try{
			if(productService.loadProduct(productId) == null)
				return "redirect:/404";
			Date dateParam = DateUtil.parse(date);
			//解析错误就加载今天的数据
			Calendar calendar = Calendar.getInstance();
			if(dateParam == null){
				calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
				date = DateUtil.format(calendar.getTime());
			}else
			{
				calendar.setTime(dateParam);
				date = DateUtil.format(calendar.getTime());
			}
			model.addAttribute("PAPERLIST", this.paperService.findDailyPaperList(productId, calendar, this.getUserId(request)));
			model.addAttribute("CURRENT_DATE",date);
			model.addAttribute("TODAY", DateUtil.format(new Date()));
			model.addAttribute("STATUS_DONE",Constant.STATUS_DONE);
			model.addAttribute("PRODUCTID", productId);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "daily_practice";
	}
	/**
	 * 查询每日一练剩余未做个数
	 * @param productId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/daily/{productId}/rest/num", method = RequestMethod.GET)
	@ResponseBody
	public Json dailyRestNumber(@PathVariable String productId,HttpServletRequest request){
		try{
			if(productService.loadProduct(productId) == null)
				return null;
			String userId = this.getUserId(request);
			return this.paperService.findUndoneDailyPaperNumber(userId, productId);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
					
	}
	
	/**
	 * 获取练习记录
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/record/{productId}", method = {RequestMethod.GET,RequestMethod.POST})
	public String records(@PathVariable String productId,PaperInfo info,Model model,HttpServletRequest request){
		try{
			if(productService.loadProduct(productId) == null)
				return "redirect:/404";
			
			String userId = this.getUserId(request);
			
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
			//试卷类型
			PaperTypeUtil.loadItemType(model);
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
	//获取用户的ID
	private String getUserId(HttpServletRequest request){
		return ((User)(request.getSession().getAttribute("USER"))).getProductUserId();
	}
	
}
