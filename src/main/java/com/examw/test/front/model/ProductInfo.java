package com.examw.test.front.model;

import java.math.BigDecimal;

import com.examw.model.Paging;

/**
 * 产品信息
 * @author fengwei.
 * @since 2014年8月12日 上午11:37:23.
 */
public class ProductInfo extends Paging{
	private static final long serialVersionUID = 1L;
	private String id,name,content;
	private String examId,examName;
	private String[] subjectId;
	private String subjectName;
	private BigDecimal price,discount;
	private String createTime,lastTime;
	private Integer status;
	private String statusName;
	private Integer code;
	/**
	 * 获取产品ID。
	 * @return 产品ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置产品ID。
	 * @param id 
	 *	 产品ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取产品代码。
	 * @return 产品代码。
	 */
	public Integer getCode() {
		return code;
	}
	/**
	 * 设置产品代码。
	 * @param code 
	 *	  产品代码。
	 */
	public void setCode(Integer code) {
		this.code = code;
	}
	/**
	 * 获取产品名称。
	 * @return 产品名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置产品名称。
	 * @param name 
	 *	  产品名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取产品介绍。
	 * @return 产品介绍。
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置产品介绍。
	 * @param content 
	 *	  产品介绍。
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取 所属考试ID
	 * @return examId
	 * 
	 */
	public String getExamId() {
		return examId;
	}
	/**
	 * 设置  所属考试ID
	 * @param examId
	 * 
	 */
	public void setExamId(String examId) {
		this.examId = examId;
	}
	/**
	 * 获取  所属考试名称
	 * @return examName
	 * 
	 */
	public String getExamName() {
		return examName;
	}
	/**
	 * 设置 所属考试名称
	 * @param examName
	 * 
	 */
	public void setExamName(String examName) {
		this.examName = examName;
	}
	/**
	 * 获取 包含科目IDs
	 * @return subjectId
	 * 
	 */
	public String[] getSubjectId() {
		return subjectId;
	}
	/**
	 * 设置 包含科目IDs
	 * @param subjectId
	 * 
	 */
	public void setSubjectId(String[] subjectId) {
		this.subjectId = subjectId;
	}
	/**
	 * 获取 原价
	 * @return price
	 * 
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * 设置 原价
	 * @param price
	 * 
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取 优惠价
	 * @return discount
	 * 
	 */
	public BigDecimal getDiscount() {
		return discount;
	}
	/**
	 * 设置 优惠价
	 * @param discount
	 * 
	 */
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	/**
	 * 获取 创建时间
	 * @return createTime
	 * 
	 */
	public String getCreateTime() {
		return createTime;
	}
	/**
	 * 设置 创建时间
	 * @param createTime
	 * 
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取 最后修改时间
	 * @return lastTime
	 * 
	 */
	public String getLastTime() {
		return lastTime;
	}
	/**
	 * 设置 最后修改时间
	 * @param lastTime
	 * 
	 */
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
	/**
	 * 获取 状态
	 * @return status
	 * 
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置 状态
	 * @param status
	 * 
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取 状态名称
	 * @return statusName
	 * 
	 */
	public String getStatusName() {
		return statusName;
	}
	/**
	 * 设置 状态名称
	 * @param statusName
	 * 
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	/**
	 * 获取 包含科目名称
	 * @return subjectName
	 * 
	 */
	public String getSubjectName() {
		return subjectName;
	}
	/**
	 * 设置 包含科目名称
	 * @param subjectName
	 * 
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	
}
