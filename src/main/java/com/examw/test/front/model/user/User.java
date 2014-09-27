package com.examw.test.front.model.user;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @author fengwei.
 * @since 2014年9月27日 下午4:37:28.
 */
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id,username,password,name;
	private BigDecimal cash;
	private Integer coin;
	/**
	 * 获取 用户ID
	 * @return id
	 * 用户ID
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置 用户ID
	 * @param id
	 * 用户ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取 用户名
	 * @return username
	 * 用户名
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 设置 用户名
	 * @param username
	 * 用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 获取 密码
	 * @return password
	 * 密码
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置 密码
	 * @param password
	 * 密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * 获取 用户姓名
	 * @return name
	 * 用户姓名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置 用户姓名
	 * @param name
	 * 用户姓名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取 现金金额
	 * @return cash
	 * 现金金额
	 */
	public BigDecimal getCash() {
		return cash;
	}
	/**
	 * 设置 现金金额
	 * @param cash
	 * 现金金额
	 */
	public void setCash(BigDecimal cash) {
		this.cash = cash;
	}
	/**
	 * 获取 考试币
	 * @return coin
	 * 考试币
	 */
	public Integer getCoin() {
		return coin;
	}
	/**
	 * 设置 考试币
	 * @param coin
	 * 考试币
	 */
	public void setCoin(Integer coin) {
		this.coin = coin;
	}
}
