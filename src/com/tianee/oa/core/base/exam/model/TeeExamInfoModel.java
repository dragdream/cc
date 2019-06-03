package com.tianee.oa.core.base.exam.model;

import java.io.Serializable;


/**
 * 考试信息
 *
 */
public class TeeExamInfoModel implements Serializable{
	private int sid;
	
	private String examName;//考试名称
	
	/**
	 * 考试参加人
	 */
	private String attendUserIds;
	private String attendUserNames;
	
	private int ownId;
	private String ownName;
	
	private String ownDeptName;
	private  int ownDeptId;
	
	/**
	 * 所用试卷
	 */
	private int paperId;
	private String paperName;
	
	/**
	 * 主观题阅卷人
	 */
	private String subExaminerIds;
	private String subExaminerNames;
	
	private String startTimeDesc;
	
	private String endTimeDesc;//结束时间
	
	private int checkDays;//查卷时间，为0则立刻查卷
	
	private int scoreAll;
	
	
	private int realScore;//分数
	
	private int opt;//操作   1=查卷  2=考试
	
	
	
	public int getOpt() {
		return opt;
	}

	public void setOpt(int opt) {
		this.opt = opt;
	}

	public int getOwnDeptId() {
		return ownDeptId;
	}

	public void setOwnDeptId(int ownDeptId) {
		this.ownDeptId = ownDeptId;
	}

	public String getOwnDeptName() {
		return ownDeptName;
	}

	public void setOwnDeptName(String ownDeptName) {
		this.ownDeptName = ownDeptName;
	}

	public int getRealScore() {
		return realScore;
	}

	public void setRealScore(int realScore) {
		this.realScore = realScore;
	}

	public int getScoreAll() {
		return scoreAll;
	}

	public void setScoreAll(int scoreAll) {
		this.scoreAll = scoreAll;
	}

	private String infoDesc;//信息描述

	public int getOwnId() {
		return ownId;
	}

	public void setOwnId(int ownId) {
		this.ownId = ownId;
	}

	public String getOwnName() {
		return ownName;
	}

	public void setOwnName(String ownName) {
		this.ownName = ownName;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getAttendUserIds() {
		return attendUserIds;
	}

	public void setAttendUserIds(String attendUserIds) {
		this.attendUserIds = attendUserIds;
	}

	public String getAttendUserNames() {
		return attendUserNames;
	}

	public void setAttendUserNames(String attendUserNames) {
		this.attendUserNames = attendUserNames;
	}

	public int getPaperId() {
		return paperId;
	}

	public void setPaperId(int paperId) {
		this.paperId = paperId;
	}

	public String getSubExaminerIds() {
		return subExaminerIds;
	}

	public void setSubExaminerIds(String subExaminerIds) {
		this.subExaminerIds = subExaminerIds;
	}

	public String getSubExaminerNames() {
		return subExaminerNames;
	}

	public void setSubExaminerNames(String subExaminerNames) {
		this.subExaminerNames = subExaminerNames;
	}

	public String getStartTimeDesc() {
		return startTimeDesc;
	}

	public void setStartTimeDesc(String startTimeDesc) {
		this.startTimeDesc = startTimeDesc;
	}

	public String getEndTimeDesc() {
		return endTimeDesc;
	}

	public void setEndTimeDesc(String endTimeDesc) {
		this.endTimeDesc = endTimeDesc;
	}

	public int getCheckDays() {
		return checkDays;
	}

	public void setCheckDays(int checkDays) {
		this.checkDays = checkDays;
	}

	public String getInfoDesc() {
		return infoDesc;
	}

	public void setInfoDesc(String infoDesc) {
		this.infoDesc = infoDesc;
	}

	public String getPaperName() {
		return paperName;
	}

	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}

	
}
