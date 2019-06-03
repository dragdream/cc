package com.beidasoft.xzfy.caseTrial.caseInvestigation.model;

public class CaseInvestigationModel {
	//ID
	private String id;
	
	//案件id
	private String caseId;
	
	//调查人姓名
	private String investName;
	
	//调查开始时间
	private String startTime;
	
	//取证方式
	private String investType;
	
	//被调查人
	private String respondent;
	
	//被调查人电话
	private String respondentPhoneNum;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getInvestName() {
		return investName;
	}

	public void setInvestName(String investName) {
		this.investName = investName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getInvestType() {
		return investType;
	}

	public void setInvestType(String investType) {
		this.investType = investType;
	}

	public String getRespondent() {
		return respondent;
	}

	public void setRespondent(String respondent) {
		this.respondent = respondent;
	}

	public String getRespondentPhoneNum() {
		return respondentPhoneNum;
	}

	public void setRespondentPhoneNum(String respondentPhoneNum) {
		this.respondentPhoneNum = respondentPhoneNum;
	}
	
	

	
	
	
}
