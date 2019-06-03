package com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity;

/**
 * 案件受理信息
 * @author A
 *
 */
public class AcceptInfo {

	// 案件ID
	private String caseId;
	// 申请行政复议日期
	private String applicationTime;
	// 是否经过补正
	private String isCorrection;
	// 补正是否过期
	private String isCorrectionOut;
	// 通知补正时间
	private String noticeCorrectionTime;
	// 补正时间
	private String correctionTime;
	// 处理意见
	private String opinions;
	// 立案时间
	private String filingTime;
	// 案件号
	private String caseNum;
	// 审查人
	private String acceptPeople;
	// 是否延期审理
	private String isDelay;
	
	public String getApplicationTime() {
		return applicationTime;
	}
	public void setApplicationTime(String applicationTime) {
		this.applicationTime = applicationTime;
	}
	public String getIsCorrection() {
		return isCorrection;
	}
	public void setIsCorrection(String isCorrection) {
		this.isCorrection = isCorrection;
	}
	public String getIsCorrectionOut() {
		return isCorrectionOut;
	}
	public void setIsCorrectionOut(String isCorrectionOut) {
		this.isCorrectionOut = isCorrectionOut;
	}
	public String getNoticeCorrectionTime() {
		return noticeCorrectionTime;
	}
	public void setNoticeCorrectionTime(String noticeCorrectionTime) {
		this.noticeCorrectionTime = noticeCorrectionTime;
	}
	public String getCorrectionTime() {
		return correctionTime;
	}
	public void setCorrectionTime(String correctionTime) {
		this.correctionTime = correctionTime;
	}
	public String getOpinions() {
		return opinions;
	}
	public void setOpinions(String opinions) {
		this.opinions = opinions;
	}
	public String getFilingTime() {
		return filingTime;
	}
	public void setFilingTime(String filingTime) {
		this.filingTime = filingTime;
	}
	public String getCaseNum() {
		return caseNum;
	}
	public void setCaseNum(String caseNum) {
		this.caseNum = caseNum;
	}
	public String getAcceptPerole() {
		return acceptPeople;
	}
	public void setAcceptPeople(String acceptPeople) {
		this.acceptPeople = acceptPeople;
	}
	public String getIsDelay() {
		return isDelay;
	}
	public void setIsDelay(String isDelay) {
		this.isDelay = isDelay;
	}
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	
}
