package com.examw.test.front.model.library;

import java.math.BigDecimal;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 
 * @author fengwei.
 * @since 2014年10月10日 下午2:39:08.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class FrontItemInfo extends StructureItemInfo{
	private static final long serialVersionUID = 1L;
	private Boolean isCollected;    // 是否被收藏
	private Integer totalNoteNum;   // 笔记总数
	private Integer userNoteNum;    // 用户笔记数
	private String userAnswer;	    // 用户答案
	private BigDecimal userScore;	// 用户得分
	private Integer answerStatus;	// 回答状态

	/**
	 * 获取 用户答案
	 * 
	 * @return userAnswer 用户答案
	 */
	public String getUserAnswer() {
		return userAnswer;
	}

	/**
	 * 设置 用户答案
	 * 
	 * @param userAnswer
	 *            用户答案
	 */
	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}

	/**
	 * 获取 用户得分
	 * 
	 * @return userScore 用户得分
	 */
	public BigDecimal getUserScore() {
		return userScore;
	}

	/**
	 * 设置 用户得分
	 * 
	 * @param userScore
	 *            用户得分
	 */
	public void setUserScore(BigDecimal userScore) {
		this.userScore = userScore;
	}

	/**
	 * 获取 答题状态
	 * 
	 * @return answerStatus
	 * 
	 */
	public Integer getAnswerStatus() {
		return answerStatus;
	}

	/**
	 * 设置 答题状态
	 * 
	 * @param answerStatus
	 * 
	 */
	public void setAnswerStatus(Integer answerStatus) {
		this.answerStatus = answerStatus;
	}

	/**
	 * 获取 是否被收藏
	 * 
	 * @return isCollected 是否被收藏
	 */
	public Boolean getIsCollected() {
		return isCollected;
	}

	/**
	 * 设置 是否被收藏
	 * 
	 * @param isCollected
	 *            是否被收藏
	 */
	public void setIsCollected(Boolean isCollected) {
		this.isCollected = isCollected;
	}

	/**
	 * 获取 笔记总数
	 * 
	 * @return totalNoteNum 笔记总数
	 */
	public Integer getTotalNoteNum() {
		return totalNoteNum;
	}

	/**
	 * 设置 笔记总数
	 * 
	 * @param totalNoteNum
	 *            笔记总数
	 */
	public void setTotalNoteNum(Integer totalNoteNum) {
		this.totalNoteNum = totalNoteNum;
	}

	/**
	 * 获取 用户笔记总数
	 * 
	 * @return userNoteNum 用户笔记总数
	 */
	public Integer getUserNoteNum() {
		return userNoteNum;
	}

	/**
	 * 设置 用户笔记总数
	 * 
	 * @param userNoteNum
	 *            用户笔记总数
	 */
	public void setUserNoteNum(Integer userNoteNum) {
		this.userNoteNum = userNoteNum;
	}
}
