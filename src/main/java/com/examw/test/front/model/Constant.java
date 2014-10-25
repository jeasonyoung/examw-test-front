package com.examw.test.front.model;
/**
 * 常量类[必须与后台的值保持一致]
 * @author fengwei.
 * @since 2014年9月13日 上午10:59:39.
 */
public class Constant {
	/**
	 * 类型－单选。
	 */
	public static final int TYPE_SINGLE = 1;
	/**
	 * 类型－多选。
	 */
	public static final int TYPE_MULTY = 2;
	/**
	 * 类型－不定向选。
	 */
	public static final int 	TYPE_UNCERTAIN =3;
	/**
	 * 类型－判断题。
	 */
	public static final int TYPE_JUDGE = 4;
	/**
	 * 类型－问答题。
	 */
	public static final int TYPE_QANDA = 5;
	/**
	 * 类型－共享题干题。
	 */
	public static final int TYPE_SHARE_TITLE = 6;
	/**
	 * 类型－共享答案题。
	 */
	public static final int TYPE_SHARE_ANSWER = 7;
	
	/**
	 * 判断题答案[Type=TYPE_JUDGE] 1-正确。
	 */
	public static final int ANSWER_JUDGE_RIGTH = 1;
	/**
	 * 判断题答案[Type=TYPE_JUDGE] 0-错误。
	 */
	public static final int ANSWER_JUDGE_WRONG = 0;
	/**
	 * 答题状态[userStatus] -1:答错
	 */
	public static final int STATUS_WRONG = -1;
	/**
	 * 答题状态[userStatus] 0:没答
	 */
	public static final int STATUS_NULL = 0;
	/**
	 * 答题状态[userStatus] 1:答对
	 */
	public static final int STATUS_RIGHT = 1;
	/**
	 * 答题状态[userStatus] 2:少选了
	 */
	public static final int STATUS_LESS = 2;
	/**
	 * 试卷答题状态[record status] 1:完成
	 */
	public static final int STATUS_DONE = 1;	//完成
	/**
	 * 试卷答题状态[record status] 2:未完成
	 */
	public static final int STATUS_UNDONE = 0;	//未完成
	
	//试卷类型
	/*
	 * <entry key="1" value="历年真题" />
				<entry key="2" value="模拟试题" />
				<entry key="3" value="预测试题" />
				<entry key="4" value="练习题" />
				<entry key="5" value="章节练习" />
				<entry key="6" value="每日一练" />
	 */
	/*** 历年真题 */
	public static final int PAPER_TYPE_REAL = 1;
	/*** 模拟试题 */
	public static final int PAPER_TYPE_SIMU = 2;
	/*** 预测试题 */
	public static final int PAPER_TYPE_FORECAS = 3;
	/*** 练习题 */
	public static final int PAPER_TYPE_PRACTICE = 4;
	/*** 章节练习 */
	public static final int PAPER_TYPE_CHAPTER = 5;
	/*** 每日一练 */
	public static final int PAPER_TYPE_DAILY = 6;
}
