package com.examw.test.front.controllers;

import java.util.Calendar;
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

import com.examw.model.DataGrid;
import com.examw.test.front.model.Constant;
import com.examw.test.front.model.library.FrontPaperInfo;
import com.examw.test.front.model.library.PaperInfo;
import com.examw.test.front.model.library.StructureItemInfo;
import com.examw.test.front.model.product.FrontProductInfo;
import com.examw.test.front.model.product.SubjectInfo;
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
			String userId = getUserId(null);
			FrontProductInfo data = productService.loadProduct(productId);
			model.addAttribute("PRODUCT", data);
			model.addAttribute("PRODUCTID",productId);
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
			model.addAttribute("SUBJECTLIST", this.collectionService.loadCollectionSubjects(productId, userId));
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
	//题目列表
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
		return "collections_detail";
	}
	//错题列表
	@RequestMapping(value = "/error/{productId}", method = {RequestMethod.GET,RequestMethod.POST})
	public String error(@PathVariable String productId,StructureItemInfo info,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载错题界面...");
		try{
			String userId = this.getUserId(null);
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
	//错题详细
	@RequestMapping(value = "/error/{productId}/item/{itemId}", method = {RequestMethod.GET,RequestMethod.POST})
	public String errorDetail(@PathVariable String productId,@PathVariable String itemId,String flag,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载错题详细界面...");
		model.addAttribute("PRODUCTID", productId);
		String userId = this.getUserId(null);
		try{
			List<SubjectInfo> subjectList = this.productService.loadProductSubjects(productId);
			String subjectId = "";
			if(subjectList!=null && subjectList.size()>0){
				for(SubjectInfo s:subjectList){
					subjectId = subjectId+s.getId()+",";
				}
			}
			Map<String,Object> map = this.errorItemService.loadItemDetail(productId,subjectId,userId,itemId);
			model.addAttribute("LAST_ITEM_ID", map.get("LAST_ITEM_ID"));
			model.addAttribute("NEXT_ITEM_ID",map.get("NEXT_ITEM_ID"));
			model.addAttribute("ITEM",map.get("ITEM"));
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
			model.addAttribute("IS_SHOW_ANSWER",StringUtils.isEmpty(flag)?true:false);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "errors_detail";
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
	
	@RequestMapping(value = "last-exam/{productId}", method = {RequestMethod.GET,RequestMethod.POST})
	public String lastExam(@PathVariable String productId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载错题界面...");
		String userId = this.getUserId(null);
		try{
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
