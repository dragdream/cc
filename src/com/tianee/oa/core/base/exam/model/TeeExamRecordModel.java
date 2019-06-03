package com.tianee.oa.core.base.exam.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class TeeExamRecordModel{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int sid;
	
	/**
	 * 答题人id
	 */
	private int participantId;
	
	/**
	 * 考试信息id
	 */
    private int examInfoId;
	
	/**
	 * 交卷时间
	 */
	private String subExamTimeDesc;
	
	
	/**
	 * 是否已阅卷 判断当前考试信息对应的试卷是否存在主观题，
	 * 不存在默认为true,如果存在阅后为true ,否则为false
	 */
	private boolean isChecked;
	private int score;//分数

	public int getSid() {
		return sid;
	}


	public void setSid(int sid) {
		this.sid = sid;
	}


	public int getParticipantId() {
		return participantId;
	}


	public void setParticipantId(int participantId) {
		this.participantId = participantId;
	}


	public int getExamInfoId() {
		return examInfoId;
	}


	public void setExamInfoId(int examInfoId) {
		this.examInfoId = examInfoId;
	}


	public String getSubExamTimeDesc() {
		return subExamTimeDesc;
	}


	public void setSubExamTimeDesc(String subExamTimeDesc) {
		this.subExamTimeDesc = subExamTimeDesc;
	}


	public boolean isChecked() {
		return isChecked;
	}


	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}


	public int getScore() {
		return score;
	}


	public void setScore(int score) {
		this.score = score;
	}


}
