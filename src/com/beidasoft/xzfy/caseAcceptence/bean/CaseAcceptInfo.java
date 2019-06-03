package com.beidasoft.xzfy.caseAcceptence.bean;

public class CaseAcceptInfo {

	//案件ID
	private String caseId;
	
	//案件状态码
	private String caseStatusCode;
	
	//案件状态
	private String caseStatus;
	
	//案件子状态码
	private String caseChildeStatusCode;
	
	//案件子状态
	private String caseChildeStatus;
	
	//受理时间
	private String acceptTime;
	
	//受理原因
	private String reason;
	
	//备注
	private String remark;
	
	//第一承办人
	private String dealManFirstId;
	
	//第二承办人
	private String dealManSecondId;
	
	
	//更新人
	private String modifiedUser;
	
	//更新人Id 
	private String modifiedUserId;
	
	//更新时间
	private String modifiedTime;

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(String acceptTime) {
		this.acceptTime = acceptTime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getDealManFirstId() {
		return dealManFirstId;
	}

	public void setDealManFirstId(String dealManFirstId) {
		this.dealManFirstId = dealManFirstId;
	}

	public String getDealManSecondId() {
		return dealManSecondId;
	}

	public void setDealManSecondId(String dealManSecondId) {
		this.dealManSecondId = dealManSecondId;
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
