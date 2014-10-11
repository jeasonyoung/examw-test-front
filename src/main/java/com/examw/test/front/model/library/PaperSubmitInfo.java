package com.examw.test.front.model.library;

import java.io.Serializable;

import org.springframework.util.StringUtils;

/**
 * 试卷提交信息
 * @author fengwei.
 * @since 2014年9月25日 下午3:45:44.
 */
public class PaperSubmitInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String userId,paperId,productId,chooseAnswer,textAnswer;
	private Integer limitTime,model;
	/**
	 * 获取 用户ID
	 * @return userId
	 * 用户ID
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置 用户ID
	 * @param userId
	 * 用户ID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取 试卷ID
	 * @return paperId
	 * 试卷ID
	 */
	public String getPaperId() {
		return paperId;
	}
	/**
	 * 设置 试卷ID
	 * @param paperId
	 * 试卷ID
	 */
	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}
	/**
	 * 获取 产品ID
	 * @return productId
	 * 产品ID
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * 设置 产品ID
	 * @param productId
	 * 产品ID
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	/**
	 * 获取 选择题答案
	 * @return chooseAnswer
	 * 选择题答案
	 */
	public String getChooseAnswer() {
		return chooseAnswer;
	}
	/**
	 * 设置 选择题答案	structureItemId=answers
	 * @param chooseAnswer
	 * 选择题答案
	 */
	public void setChooseAnswer(String chooseAnswer) {
		this.chooseAnswer = chooseAnswer;
	}
	/**
	 * 获取 文字题答案
	 * @return textAnswer
	 * 文字题答案
	 */
	public String getTextAnswer() {
		return textAnswer;
	}
	/**
	 * 设置 文字题答案
	 * @param textAnswer
	 * 文字题答案
	 */
	public void setTextAnswer(String textAnswer) {
		this.textAnswer = textAnswer;
	}
	/**
	 * 获取 剩余时间[秒]
	 * @return limitTime
	 * 剩余时间
	 */
	public Integer getLimitTime() {
		return limitTime;
	}
	/**
	 * 设置 剩余时间
	 * @param limitTime
	 * 剩余时间
	 */
	public void setLimitTime(Integer limitTime) {
		this.limitTime = limitTime;
	}
	/**
	 * 获取 提交模式
	 * @return model
	 * 提交模式
	 */
	public Integer getModel() {
		return model;
	}
	/**
	 * 设置 提交模式
	 * @param model
	 * 提交模式
	 */
	public void setModel(Integer model) {
		this.model = model;
	}
	
	public boolean isSafe(){
		if(StringUtils.isEmpty(this.paperId) || StringUtils.isEmpty(this.userId) || StringUtils.isEmpty(this.productId))
			return false;
		return true;
	}
}
