package com.examw.test.front.model;

import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.model.Paging;
/**
 * 大纲信息。
 * @author lq.
 * @since 2014-08-06.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SyllabusInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String pid,fullName,id,title,subId,subName,examId,examName;
	private Set<SyllabusInfo> children;
	private Integer code;
	/**
	 * 获取上级大纲ID。
	 * @return 上级大纲ID。
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * 设置上级大纲ID。
	 * @param pid
	 * 上级大纲ID。
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
	/**
	 * 获取要点全称。
	 * @return 要点全称。
	 */
	public String getFullName() {
		return fullName;
	}
	/**
	 * 设置要点全称。
	 * @param fullName
	 * 要点全称。
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	/**
	 * 获取大纲ID。
	 * @return 大纲ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置大纲ID。
	 * @param id
	 * 大纲ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取大纲代码。
	 * @return 大纲代码。
	 */
	public Integer getCode() {
		return code;
	}
	/**
	 * 设置大纲代码。
	 * @param code
	 * 大纲代码。
	 */
	public void setCode(Integer code) {
		this.code = code;
	}
	/**
	 * 获取大纲标题。
	 * @return 大纲标题。
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置大纲标题。
	 * @param title
	 * 大纲标题。
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取所属科目ID。
	 * @return 所属科目ID。
	 */
	public String getSubId() {
		return subId;
	}
	/**
	 * 设置所属科目ID。
	 * @param subId
	 * 所属科目ID。
	 */
	public void setSubId(String subId) {
		this.subId = subId;
	}
	/**
	 * 获取所属科目名称。
	 * @return 所属科目名称。
	 */
	public String getSubName() {
		return subName;
	}
	/**
	 * 设置所属科目名称。
	 * @param subName
	 * 所属科目名称。
	 */
	public void setSubName(String subName) {
		this.subName = subName;
	}
	/**
	 * 获取子大纲集合。
	 * @return 子大纲集合。
	 */
	public Set<SyllabusInfo> getChildren() {
		return children;
	}
	/**
	 * 设置子大纲集合。
	 * @param children
	 * 子大纲集合。
	 */
	public void setChildren(Set<SyllabusInfo> children) {
		this.children = children;
	}
	/**
	 * 获取所属考试类别ID。
	 * @return 所属考试类别ID。
	 */
	public String getExamId() {
		return examId;
	}
	/**
	 * 设置所属考试类别ID。
	 * @param cateId
	 * 所属考试类别ID。
	 */
	public void setExamId(String examId) {
		this.examId = examId;
	}
	/**
	 * 获取所属考试类别名称。
	 * @return 所属考试类别名称。
	 */
	public String getExamName() {
		return examName;
	}
	/**
	 * 设置所属考试类别名称。
	 * @param cateName
	 * 所属考试类别名称。
	 */
	public void setExamName(String examName) {
		this.examName = examName;
	}
}