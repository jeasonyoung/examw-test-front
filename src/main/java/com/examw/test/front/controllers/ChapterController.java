package com.examw.test.front.controllers;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.test.front.model.product.SubjectInfo;
import com.examw.test.front.service.IChapterService;
import com.examw.test.front.service.IProductService;

/**
 * 章节控制器 [章节练习,章节内容]
 * @author fengwei.
 * @since 2014年9月9日 下午3:23:08.
 */
@Controller
@RequestMapping("/library/chapter")
public class ChapterController extends BaseProductController{
	private static final Logger logger = Logger.getLogger(ChapterController.class);
	//章节服务接口
	@Resource
	private IChapterService chapterService;
	//产品服务接口
	@Resource
	private IProductService productService;
	/**
	 * 章节练习界面
	 * @param productId		产品ID
	 * @param subjectId		科目ID
	 * @param subSubjectId	子科目ID
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list/{productId}", method = {RequestMethod.GET,RequestMethod.POST})
	public String chapters(@PathVariable String productId,String subjectId,String subSubjectId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载章节练习...");
		try{
			if(this.loadProductInfo(productId)==null)
			{
				return "redirect:/404";
			}
			List<SubjectInfo> list = this.productService.loadProductSubjects(productId);
			model.addAttribute("PRODUCTID", productId);
			model.addAttribute("SUBJECTLIST", list);
			if(StringUtils.isEmpty(subjectId)){
				if(list!=null && list.size()>0)
					subjectId = list.get(0).getId();
			}
			if(StringUtils.isEmpty(subSubjectId)){
				if(list!=null && list.size()>0)
				{
					SubjectInfo info = list.get(0);
					if(info.getChildren()!=null && info.getChildren().size()>0)
					{
						subSubjectId = info.getChildren().get(0).getId();
					}
				}
			}
			model.addAttribute("CHAPTERLIST", this.chapterService.loadChapterInfo(subjectId));
			model.addAttribute("CURRENT_SUBJECT_ID", subjectId);
			model.addAttribute("CURRENT_CHILD_SUBJECT_ID", subSubjectId);
		}catch(Exception e){
			e.printStackTrace();
			if(logger.isDebugEnabled()) logger.debug("加载章节信息异常...");
		}
		return "chapter_list";
	}
	/**
	 * 章节详情
	 * @param pid	章节父ID
	 * @param id	章节子ID
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/detail/{pid}/{id}", method = {RequestMethod.GET,RequestMethod.POST})
	public String chapterDetail(@PathVariable String pid,@PathVariable String id,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载章节练习...");
		try{
			model.addAttribute("CHAPTER", this.chapterService.loadKnowledges(pid));
			model.addAttribute("KNOWLEDGE", this.chapterService.loadKnowledgeDetail(id));
		}catch(Exception e){
			e.printStackTrace();
			if(logger.isDebugEnabled()) logger.debug("加载章节信息异常...");
		}
		return "chapter_knowledge";
	}
}
