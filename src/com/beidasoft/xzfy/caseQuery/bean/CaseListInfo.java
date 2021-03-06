package com.beidasoft.xzfy.caseQuery.bean;

import java.math.BigDecimal;

public class CaseListInfo {

	private String caseId;// 案件ID
	private String caseNum; // 案件编号
	private String postTypeCode; // 案件来源编码
	private String postType; // 案件来源
	private String name; // 姓名/组织名称
	private String respondentName; // 被申请人名称
	private String caseStatusCode; // 案件状态
	private String caseStatus; // 案件状态
	private String caseChiledStatusCode;// 案件子状态代码
	private String caseChiledStatus;//案件子状态
	private String applicationDate; // 收到复议申请日期
//	private String organId; // 复议机关
//	private String organ; // 复议机关
	private String settleTypeCode; // 结案类型
	private String settleType; // 结案类型
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getCaseNum() {
		return caseNum;
	}
	public void setCaseNum(String caseNum) {
		this.caseNum = caseNum;
	}
	public String getPostTypeCode() {
		return postTypeCode;
	}
	public void setPostTypeCode(String postTypeCode) {
		this.postTypeCode = postTypeCode;
	}
	public String getPostType() {
		return postType;
	}
	public void setPostType(String postType) {
		this.postType = postType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRespondentName() {
		return respondentName;
	}
	public void setRespondentName(String respondentName) {
		this.respondentName = respondentName;
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
	public String getCaseChiledStatusCode() {
		return caseChiledStatusCode;
	}
	public void setCaseChiledStatusCode(String caseChiledStatusCode) {
		this.caseChiledStatusCode = caseChiledStatusCode;
	}
	public String getCaseChiledStatus() {
		return caseChiledStatus;
	}
	public void setCaseChiledStatus(String caseChiledStatus) {
		this.caseChiledStatus = caseChiledStatus;
	}
	public String getApplicationDate() {
		return applicationDate;
	}
	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}
//	public String getOrganId() {
//		return organId;
//	}
//	public void setOrganId(String organId) {
//		this.organId = organId;
//	}
//	public String getOrgan() {
//		return organ;
//	}
//	public void setOrgan(String organ) {
//		this.organ = organ;
//	}
	public String getSettleTypeCode() {
		return settleTypeCode;
	}
	public void setSettleTypeCode(String settleTypeCode) {
		this.settleTypeCode = settleTypeCode;
	}
	public String getSettleType() {
		return settleType;
	}
	public void setSettleType(String settleType) {
		this.settleType = settleType;
	}

}
