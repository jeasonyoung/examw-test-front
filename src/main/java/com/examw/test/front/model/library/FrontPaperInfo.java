package com.examw.test.front.model.library;

import java.math.BigDecimal;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 前端试卷摘要信息。
 * 
 * @author yangyong
 * @since 2014年9月26日
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class FrontPaperInfo extends BasePaperInfo {
	private static final long serialVersionUID = 1L;
	private Integer total;
	private BigDecimal maxScore;	//最高得分
	private Integer sumUser;		//总人数
	/**
	 * 获取试题总数。
	 * @return 试题总数。
	 */
	public Integer getTotal() {
		return total;
	}
	/**
	 * 设置试题总数。
	 * @param total 
	 *	  试题总数。
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}
	/**
	 * 获取 最高得分
	 * @return maxScore
	 * 最高得分
	 */
	public BigDecimal getMaxScore() {
		return maxScore;
	}
	/**
	 * 设置 最高得分
	 * @param maxScore
	 * 最高得分
	 */
	public void setMaxScore(BigDecimal maxScore) {
		this.maxScore = maxScore;
	}
	/**
	 * 获取 总参考人数
	 * @return sumUser
	 * 总参考人数
	 */
	public Integer getSumUser() {
		return sumUser;
	}
	/**
	 * 设置 总参考人数
	 * @param sumUser
	 * 总参考人数
	 */
	public void setSumUser(Integer sumUser) {
		this.sumUser = sumUser;
	}
	
	
}