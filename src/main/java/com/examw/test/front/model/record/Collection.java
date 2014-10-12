package com.examw.test.front.model.record;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户题目收藏
 * @author fengwei.
 * @since 2014年9月17日 下午3:58:23.
 */
public class Collection implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,userId,itemId,userAnswer,productId,examId,subjectId;
	/**
	 * 获取 
	 * @return subjectId
	 * 
	 */
	public String getSubjectId() {
		return subjectId;
	}
	/**
	 * 设置 
	 * @param subjectId
	 * 
	 */
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	private Date createTime;
	/**
	 * 获取 ID
	 * @return id
	 * ID
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置 ID
	 * @param id
	 * ID
	 */
	public void setId(String id) {
		this.id = id;
	}
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
	 * 获取 题目ID
	 * @return itemId
	 * 题目ID
	 */
	public String getItemId() {
		return itemId;
	}
	/**
	 * 设置 题目ID
	 * @param itemId
	 * 题目ID
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	/**
	 * 获取 创建时间
	 * @return createTime
	 * 创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置 创建时间
	 * @param createTime
	 * 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取 用户答案
	 * @return userAnswer
	 * 
	 */
	public String getUserAnswer() {
		return userAnswer;
	}
	/**
	 * 设置 用户答案
	 * @param userAnswer
	 * 
	 */
	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
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
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	
}
