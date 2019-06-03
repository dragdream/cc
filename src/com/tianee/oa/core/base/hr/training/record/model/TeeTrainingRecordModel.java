package com.tianee.oa.core.base.hr.training.record.model;


public class TeeTrainingRecordModel {

	private int sid;// 自增id

	private String planId;//培训计划
	private String planName;
	
	private String recordUserId;//培训记录人
	private String recordUserName;//培训记录人
	
	private String recordInstitution;//INSTITUTION

	private double recordCost;

	
	private double examResults = 0.0;
	
	
	private int examLevel = 0;

	private String dutySituation;
	

	private String trainningSituation;

	private String recordComment;


	private String remark;

	private String createTimeDesc;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getRecordUserId() {
		return recordUserId;
	}

	public void setRecordUserId(String recordUserId) {
		this.recordUserId = recordUserId;
	}

	public String getRecordUserName() {
		return recordUserName;
	}

	public void setRecordUserName(String recordUserName) {
		this.recordUserName = recordUserName;
	}

	public String getRecordInstitution() {
		return recordInstitution;
	}

	public void setRecordInstitution(String recordInstitution) {
		this.recordInstitution = recordInstitution;
	}

	public double getRecordCost() {
		return recordCost;
	}

	public void setRecordCost(double recordCost) {
		this.recordCost = recordCost;
	}

	public double getExamResults() {
		return examResults;
	}

	public void setExamResults(double examResults) {
		this.examResults = examResults;
	}

	public int getExamLevel() {
		return examLevel;
	}

	public void setExamLevel(int examLevel) {
		this.examLevel = examLevel;
	}

	public String getDutySituation() {
		return dutySituation;
	}

	public void setDutySituation(String dutySituation) {
		this.dutySituation = dutySituation;
	}

	public String getTrainningSituation() {
		return trainningSituation;
	}

	public void setTrainningSituation(String trainningSituation) {
		this.trainningSituation = trainningSituation;
	}

	public String getRecordComment() {
		return recordComment;
	}

	public void setRecordComment(String recordComment) {
		this.recordComment = recordComment;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateTimeDesc() {
		return createTimeDesc;
	}

	public void setCreateTimeDesc(String createTimeDesc) {
		this.createTimeDesc = createTimeDesc;
	}

}
