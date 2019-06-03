package com.tianee.oa.core.base.exam.model;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.tianee.oa.core.base.exam.bean.TeeExamInfo;
import com.tianee.oa.core.base.exam.bean.TeeExamQuestion;
import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 考试数据
 *
 */
public class TeeExamDataModel {
	private int sid;
	
	/**
	 * 答题人
	 */
	private int userId;
	private String userName;
	
	/**
	 * 所属考试信息
	 */
	private int examInfoId;
	private String examInfoName;
	
	/**
	 * 问题ID
	 */
	private int questionId;
	
	
	/**
	 * 答案
	 */
	private String answer;
	
	/**
	 * 单题所获得分数
	 */
	private int score;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getExamInfoId() {
		return examInfoId;
	}

	public void setExamInfoId(int examInfoId) {
		this.examInfoId = examInfoId;
	}

	public String getExamInfoName() {
		return examInfoName;
	}

	public void setExamInfoName(String examInfoName) {
		this.examInfoName = examInfoName;
	}


	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
