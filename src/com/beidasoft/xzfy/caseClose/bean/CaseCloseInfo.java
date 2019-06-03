package com.beidasoft.xzfy.caseClose.bean;

public class CaseCloseInfo {

	//案件ID集合
	private String caseIds;
	
	//案件状态码
	private String caseStatusCode;
	
	//案件状态
	private String caseStatus;
	
	//案件子状态码
	private String caseChildeStatusCode;
	
	//案件子状态
	private String caseChildeStatus;

	//更新人
	private String modifiedUser;
	
	//更新人Id 
	private String modifiedUserId;
	
	//更新时间
	private String modifiedTime;
	
	public String getCaseIds() {
		return caseIds;
	}

	public void setCaseIds(String caseIds) {
		this.caseIds = caseIds;
	}

	public String getCaseStatusCode() {
		return caseStatusCode;
	}

	public void setCaseStatusCode(String caseStatusCode) {
		this.caseStatusCode = caseStatusCode;
	}

	public String getCaseStatus() {
		return caseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}

	public String getCaseChildeStatusCode() {
		return caseChildeStatusCode;
	}

	public void setCaseChildeStatusCode(String caseChildeStatusCode) {
		this.caseChildeStatusCode = caseChildeStatusCode;
	}

	public String getCaseChildeStatus() {
		return caseChildeStatus;
	}

	public void setCaseChildeStatus(String caseChildeStatus) {
		this.caseChildeStatus = caseChildeStatus;
	}

	public String getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public String getModifiedUserId() {
		return modifiedUserId;
	}

	public void setModifiedUserId(String modifiedUserId) {
		this.modifiedUserId = modifiedUserId;
	}

	public String getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

}
