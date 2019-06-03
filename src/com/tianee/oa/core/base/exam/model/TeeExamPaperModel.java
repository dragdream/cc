package com.tianee.oa.core.base.exam.model;

import java.io.Serializable;



/**
 * 试卷
 *
 */
public class TeeExamPaperModel implements Serializable  {
	private int sid;
	
	/**
	 * 试卷标题
	 */
	private String title;
	
	/**
	 * 分数类型
	 * 1、根据试题分值以百分比计算
	 * 2、按试题分数计分
	 */
	private int scoreType;
	private String scoreTypeDesc;
	
	/**
	 * 试卷总分
	 * 如果scoreType=2，则此项可以为空，
	 */
	private int scoreAll;
	
	/**
	 * 试题数量
	 */
	private int qCount;
	
	private int examTimes;
	public int getExamTimes() {
		return examTimes;
	}

	public void setExamTimes(int examTimes) {
		this.examTimes = examTimes;
	}

	private int storeId;//所属题库
	
	private String paperDesc;//试卷说明

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getScoreType() {
		return scoreType;
	}

	public void setScoreType(int scoreType) {
		this.scoreType = scoreType;
	}

	public String getScoreTypeDesc() {
		return scoreTypeDesc;
	}

	public void setScoreTypeDesc(String scoreTypeDesc) {
		this.scoreTypeDesc = scoreTypeDesc;
	}

	public int getScoreAll() {
		return scoreAll;
	}

	public void setScoreAll(int scoreAll) {
		this.scoreAll = scoreAll;
	}

	public int getqCount() {
		return qCount;
	}

	public void setqCount(int qCount) {
		this.qCount = qCount;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getPaperDesc() {
		return paperDesc;
	}

	public void setPaperDesc(String paperDesc) {
		this.paperDesc = paperDesc;
	}

	
}
