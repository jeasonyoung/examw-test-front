package com.examw.test.front.model.library;

import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 题目信息。
 * @author yangyong
 * @since 2014年8月7日
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ItemInfo extends BaseItemInfo<ItemInfo> {
	private static final long serialVersionUID = 1L;
	private String userId,userName;
	private Set<ItemInfo> children;
	/**
	 * 获取所属用户ID。
	 * @return 所属用户ID。
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置所属用户ID。
	 * @param userId 
	 *	  所属用户ID。
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取所属用户名称。
	 * @return 所属用户名称。
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 设置所属用户名称。
	 * @param userName 
	 *	  所属用户名称。
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/*
	 * 获取子题目集合。
	 * @see com.examw.test.model.library.BaseItemInfo#getChildren()
	 */
	@Override
	public Set<ItemInfo> getChildren() {
		return children;
	}
	/*
	 * 设置子题目集合。
	 * @see com.examw.test.model.library.BaseItemInfo#setChildren(java.util.Set)
	 */
	@Override
	public void setChildren(Set<ItemInfo> children) {
		this.children = children;
	}
}