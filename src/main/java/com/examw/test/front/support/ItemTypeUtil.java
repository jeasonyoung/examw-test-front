package com.examw.test.front.support;

import org.springframework.ui.Model;

import com.examw.test.front.model.Constant;

/**
 * 题型帮助
 * @author fengwei.
 * @since 2014年10月18日 下午2:26:16.
 */
public class ItemTypeUtil {
	public static void loadItemType(Model model){
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
	}
}
