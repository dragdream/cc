package com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity;

/**
 * 案件审理信息
 * @author A
 *
 */
public class TrialInfo {
	
	// 案件ID
	private String caseId;
	// 是否中止审理
	private String isSuspension;
	// 中止日期
	private String suspensionTime;
	// 恢复审理日期
	private String recoveryTime;
	// 审理结果
	private String trialResult;
	// 结案日期
	private String closeTime;
	// 是否国家赔偿
	private String isPay;
	// 赔偿金额
	private String pay;
	// 承办人
	private String trialPeople;
	// 结案文书
	private String closeWord;
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getIsSuspension() {
		return isSuspension;
	}
	public void setIsSuspension(String isSuspension) {
		this.isSuspension = isSuspension;
	}
	public String getSuspensionTime() {
		return suspensionTime;
	}
	public void setSuspensionTime(String suspensionTime) {
		this.suspensionTime = suspensionTime;
	}
	public String getRecoveryTime() {
		return recoveryTime;
	}
	public void setRecoveryTime(String recoveryTime) {
		this.recoveryTime = recoveryTime;
	}
	public String getTrialResult() {
		return trialResult;
	}
	public void setTrialResult(String trialResult) {
		this.trialResult = trialResult;
	}
	public String getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}
	public String getIsPay() {
		return isPay;
	}
	public void setIsPay(String isPay) {
		this.isPay = isPay;
	}
	public String getPay() {
		return pay;
	}
	public void setPay(String pay) {
		this.pay = pay;
	}
	public String getTrialPeople() {
		return trialPeople;
	}
	public void setTrialPeople(String trialPeople) {
		this.trialPeople = trialPeople;
	}
	public String getCloseWord() {
		return closeWord;
	}
	public void setCloseWord(String closeWord) {
		this.closeWord = closeWord;
	}
	
}
