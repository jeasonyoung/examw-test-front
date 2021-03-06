package com.examw.test.front.model.product;

import java.math.BigDecimal;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.Paging;

/**
 * 产品信息
 * @author fengwei.
 * @since 2014年8月12日 上午11:37:23.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ProductInfo extends Paging implements Comparable<ProductInfo>{
	private static final long serialVersionUID = 1L;
	private String id,name,image,content,categoryId,categoryName,examId,examName,areaId,areaName,statusName,analysisTypeName,realTypeName;
	private String[] subjectId,subjectName;
	private Integer orderNo,status,analysisType,realType,paperTotal,itemTotal;
	private BigDecimal price,discount;
	private String createTime,lastTime;
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
	 * 获取所属考试类别ID。
	 * @return 所属考试类别ID。
	 */
	public String getCategoryId() {
		return categoryId;
	}
	/**
	 * 设置所属考试类别ID。
	 * @param catalogId 
	 *	  所属考试类别ID。
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * 获取所属考试类别名称。
	 * @return 所属考试类别名称。
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * 设置所属考试类别名称。
	 * @param categoryName 
	 *	  所属考试类别名称。
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	/**
	 * 获取所属考试ID。
	 * @return 所属考试ID。
	 */
	public String getExamId() {
		return examId;
	}
	/**
	 * 设置所属考试ID。
	 * @param examId
	 * 所属考试ID。
	 */
	public void setExamId(String examId) {
		this.examId = examId;
	}
	/**
	 * 获取所属考试名称。
	 * @return 所属考试名称。
	 */
	public String getExamName() {
		return examName;
	}
	/**
	 * 设置所属考试名称。
	 * @param examName
	 * 所属考试名称。
	 */
	public void setExamName(String examName) {
		this.examName = examName;
	}
	/**
	 * 获取所属地区ID。
	 * @return 所属地区ID。
	 */
	public String getAreaId() {
		return areaId;
	}
	/**
	 * 设置所属地区ID。
	 * @param areaId 
	 *	  所属地区ID。
	 */
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	/**
	 * 获取所属地区名称。
	 * @return areaName
	 */
	public String getAreaName() {
		return areaName;
	}
	/**
	 * 设置所属地区名称。
	 * @param areaName 
	 *	  所属地区名称。
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	/**
	 * 获取包含科目ID。
	 * @return 包含科目ID。
	 */
	public String[] getSubjectId() {
		return subjectId;
	}
	/**
	 * 设置包含科目ID。
	 * @param subjectId
	 * 包含科目ID。
	 */
	public void setSubjectId(String[] subjectId) {
		this.subjectId = subjectId;
	}
	/**
	 * 获取原价。
	 * @return 原价。
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * 设置原价。
	 * @param price
	 * 原价。
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取优惠价。
	 * @return 优惠价。
	 */
	public BigDecimal getDiscount() {
		return discount;
	}
	/**
	 * 设置优惠价。
	 * @param discount
	 * 优惠价。
	 */
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	/**
	 * 获取创建时间。
	 * @return 创建时间。
	 */
	public String getCreateTime() {
		return createTime;
	}
	/**
	 * 设置创建时间。
	 * @param createTime
	 * 创建时间。
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取最后修改时间。
	 * @return 最后修改时间。
	 */
	public String getLastTime() {
		return lastTime;
	}
	/**
	 * 设置最后修改时间。
	 * @param lastTime
	 * 最后修改时间。
	 */
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
	/**
	 * 获取状态。
	 * @return 状态。
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置状态。
	 * @param status
	 * 状态。
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取状态名称。
	 * @return 状态名称。
	 */
	public String getStatusName() {
		return statusName;
	}
	/**
	 * 设置状态名称。
	 * @param statusName
	 * 状态名称。
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	/**
	 * 获取排序号。
	 * @return 排序号。
	 */
	public Integer getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置排序号。
	 * @param orderNo 
	 *	  排序号。
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * 获取包含科目名称。
	 * @return 包含科目名称。
	 */
	public String[] getSubjectName() {
		return subjectName;
	}
	/**
	 * 设置包含科目名称。
	 * @param subjectName
	 * 包含科目名称。
	 */
	public void setSubjectName(String[] subjectName) {
		this.subjectName = subjectName;
	}
	/*
	 * 排序比较。
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(ProductInfo o) {
		if(this == o) return 0;
		int index = this.getOrderNo() - o.getOrderNo();
		if(index == 0){
			index = this.getName().compareToIgnoreCase(o.getName());
			if(index == 0){
				index = this.getId().compareToIgnoreCase(this.getId());
			}
		}
		return index;
	}
	/**
	 * 获取 图片
	 * @return image
	 * 
	 */
	public String getImage() {
		return image;
	}
	/**
	 * 设置 图片
	 * @param image
	 * 
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * 获取 解析类型名称
	 * @return analysisTypeName
	 * 
	 */
	public String getAnalysisTypeName() {
		return analysisTypeName;
	}
	/**
	 * 设置  解析类型名称
	 * @param analysisTypeName
	 * 
	 */
	public void setAnalysisTypeName(String analysisTypeName) {
		this.analysisTypeName = analysisTypeName;
	}
	/**
	 * 获取 是否真题
	 * @return realTypeName
	 * 
	 */
	public String getRealTypeName() {
		return realTypeName;
	}
	/**
	 * 设置 是否真题
	 * @param realTypeName
	 * 
	 */
	public void setRealTypeName(String realTypeName) {
		this.realTypeName = realTypeName;
	}
	/**
	 * 获取 
	 * @return analysisType
	 * 
	 */
	public Integer getAnalysisType() {
		return analysisType;
	}
	/**
	 * 设置 
	 * @param analysisType
	 * 
	 */
	public void setAnalysisType(Integer analysisType) {
		this.analysisType = analysisType;
	}
	/**
	 * 获取 
	 * @return realType
	 * 
	 */
	public Integer getRealType() {
		return realType;
	}
	/**
	 * 设置 
	 * @param realType
	 * 
	 */
	public void setRealType(Integer realType) {
		this.realType = realType;
	}
	/**
	 * 获取 试卷总数
	 * @return paperTotal
	 * 
	 */
	public Integer getPaperTotal() {
		return paperTotal;
	}
	/**
	 * 设置 试卷总数
	 * @param paperTotal
	 * 
	 */
	public void setPaperTotal(Integer paperTotal) {
		this.paperTotal = paperTotal;
	}
	/**
	 * 获取 题目总数
	 * @return itemTotal
	 * 
	 */
	public Integer getItemTotal() {
		return itemTotal;
	}
	/**
	 * 设置 题目总数
	 * @param itemTotal
	 * 
	 */
	public void setItemTotal(Integer itemTotal) {
		this.itemTotal = itemTotal;
	}
	//2015.03.09添加属性
	private String title,keywords,description;
	/**
	 * 获取 标题
	 * @return title
	 * 标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置 标题
	 * @param title
	 * 标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取 关键字
	 * @return keywords
	 * 关键字
	 */
	public String getKeywords() {
		return keywords;
	}
	/**
	 * 设置 关键字
	 * @param keywords
	 * 关键字
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	/**
	 * 获取 描述
	 * @return description
	 * 描述
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置 描述
	 * @param description
	 * 描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}