package com.examw.test.front.support;

import org.springframework.ui.Model;

import com.examw.test.front.model.Constant;

/**
 * 试卷类型
 * @author fengwei.
 * @since 2014年10月25日 下午4:38:54.
 */
public class PaperTypeUtil {
	public static void loadItemType(Model model){
		//真题
		model.addAttribute("PAPER_TYPE_REAL", Constant.PAPER_TYPE_REAL);
		//模拟题
		model.addAttribute("PAPER_TYPE_SIMU", Constant.PAPER_TYPE_SIMU);
		//预测题
		model.addAttribute("PAPER_TYPE_FORECAS", Constant.PAPER_TYPE_FORECAS);
		//练习题
		model.addAttribute("PAPER_TYPE_PRACTICE", Constant.PAPER_TYPE_PRACTICE);
		//章节练习
		model.addAttribute("PAPER_TYPE_CHAPTER", Constant.PAPER_TYPE_CHAPTER);
		//每日一练
		model.addAttribute("PAPER_TYPE_DAILY", Constant.PAPER_TYPE_DAILY);
	}
}
