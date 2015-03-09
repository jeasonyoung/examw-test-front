package com.examw.test.front.model.product;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.Paging;

/**
 * 考试设置信息
 * @author fengwei.
 * @since 2014年8月6日 下午3:05:16.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ExamInfo extends Paging{
	private static final long serialVersionUID = 1L;
	private String id,name,abbr;
	private String categoryId,categoryName;
	private String[] areaId,areaName;
	private Integer code;
	private String imageUrl; //图片地址 add by fw 2014.11.10
	/**
	 * 获取考试ID。
	 * @return 考试ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置考试ID。
	 * @param id
	 * 考试ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取考试代码。
	 * @return 考试代码。
	 */
	public Integer getCode() {
		return code;
	}
	/**
	 * 设置考试代码。
	 * @param code
	 * 考试代码。
	 */
	public void setCode(Integer code) {
		this.code = code;
	}
	/**
	 * 获取考试名称。
	 * @return 考试名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置考试名称。
	 * @param name
	 * 考试名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取EN简称。
	 * @return EN简称。
	 */
	public String getAbbr() {
		return abbr;
	}
	/**
	 * 设置EN简称。
	 * @param abbr
	 * EN简称。
	 */
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
	/**
	 * 获取 分类ID
	 * @return categoryId
	 * 分类ID
	 */
	public String getCategoryId() {
		return categoryId;
	}
	/**
	 * 设置 分类ID
	 * @param categoryId
	 * 分类ID
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * 获取 分类名称
	 * @return categoryName
	 * 分类名称
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * 设置 分类名称
	 * @param categoryName
	 * 分类名称
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	/**
	 * 获取 地区ID
	 * @return areaId
	 * 地区ID
	 */
	public String[] getAreaId() {
		return areaId;
	}
	/**
	 * 设置 地区ID
	 * @param areaId
	 * 地区ID
	 */
	public void setAreaId(String[] areaId) {
		this.areaId = areaId;
	}
	/**
	 * 获取 地区名称
	 * @return areaName
	 * 地区名称
	 */
	public String[] getAreaName() {
		return areaName;
	}
	/**
	 * 设置 地区名称
	 * @param areaName
	 * 地区名称
	 */
	public void setAreaName(String[] areaName) {
		this.areaName = areaName;
	}
	/**
	 * 获取 图片地址
	 * @return imageUrl
	 * 图片地址
	 */
	public String getImageUrl() {
		return imageUrl;
	}
	/**
	 * 设置 图片地址
	 * @param imageUrl
	 * 图片地址
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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
