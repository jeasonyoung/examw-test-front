package com.examw.test.front.model.user;

import java.io.Serializable;

/**
 * 
 * @author fengwei.
 * @since 2014年9月29日 上午11:09:48.
 */
public class ExamwUser implements Serializable{
	private static final long serialVersionUID = 1L;
	/*
	 * UserName":"fw121fw42",
	 * "uid":"462144",
	 * "ExamwB":"2",
	 * "UserMoney":"0",
	 * "GroupTitle":"普通会员",
	 * "GroupID":"10",
	 * "GroupExpiry":"",
	 * "RegTime":"",
	 * "LoginTime":""
	 * 
	 */
	private String UserName,uid,ExamwB,UserMoney,GroupTitle,GroupID,GroupExpiry,RegTime,LoginTime;

	/**
	 * 获取 
	 * @return userName
	 * 
	 */
	public String getUserName() {
		return UserName;
	}

	/**
	 * 设置 
	 * @param userName
	 * 
	 */
	public void setUserName(String userName) {
		UserName = userName;
	}

	/**
	 * 获取 
	 * @return uid
	 * 
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * 设置 
	 * @param uid
	 * 
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * 获取 
	 * @return examwB
	 * 
	 */
	public String getExamwB() {
		return ExamwB;
	}

	/**
	 * 设置 
	 * @param examwB
	 * 
	 */
	public void setExamwB(String examwB) {
		ExamwB = examwB;
	}

	/**
	 * 获取 
	 * @return userMoney
	 * 
	 */
	public String getUserMoney() {
		return UserMoney;
	}

	/**
	 * 设置 
	 * @param userMoney
	 * 
	 */
	public void setUserMoney(String userMoney) {
		UserMoney = userMoney;
	}

	/**
	 * 获取 
	 * @return groupTitle
	 * 
	 */
	public String getGroupTitle() {
		return GroupTitle;
	}

	/**
	 * 设置 
	 * @param groupTitle
	 * 
	 */
	public void setGroupTitle(String groupTitle) {
		GroupTitle = groupTitle;
	}

	/**
	 * 获取 
	 * @return groupID
	 * 
	 */
	public String getGroupID() {
		return GroupID;
	}

	/**
	 * 设置 
	 * @param groupID
	 * 
	 */
	public void setGroupID(String groupID) {
		GroupID = groupID;
	}

	/**
	 * 获取 
	 * @return groupExpiry
	 * 
	 */
	public String getGroupExpiry() {
		return GroupExpiry;
	}

	/**
	 * 设置 
	 * @param groupExpiry
	 * 
	 */
	public void setGroupExpiry(String groupExpiry) {
		GroupExpiry = groupExpiry;
	}

	/**
	 * 获取 
	 * @return regTime
	 * 
	 */
	public String getRegTime() {
		return RegTime;
	}

	/**
	 * 设置 
	 * @param regTime
	 * 
	 */
	public void setRegTime(String regTime) {
		RegTime = regTime;
	}

	/**
	 * 获取 
	 * @return loginTime
	 * 
	 */
	public String getLoginTime() {
		return LoginTime;
	}

	/**
	 * 设置 
	 * @param loginTime
	 * 
	 */
	public void setLoginTime(String loginTime) {
		LoginTime = loginTime;
	}
	
}
