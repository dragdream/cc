package com.beidasoft.xzzf.punish.document.model;

import java.util.Map;

import javax.persistence.Lob;


/**
 * 行政处罚听证报告
 */
public class HearingReportModel {
	// 听证报告唯一ID
    private String id;

    // 案件名称
    private String caseName;

    // 听证时间
    private String hearingTimeStr;

    // 听证地点
    private String hearingAddress;

    // 听证主持人
    private String hearingCompere;

    // 记录人
    private String recorderName;

    // 主办人ID
    private String majorPersonId;

    // 主办人姓名
    private String majorPersonName;

    // 主办人执法证号
    private String majorPersonCode;

    // 协办人ID
    private String minorPersonId;

    // 协办人姓名
    private String minorPersonName;

    // 协办人执法证号
    private String minorPersonCode;

    // 当事人名称
    private String partyName;

    // 当事人类型（代码表）
    private String partyType;

    // 负责人姓名
    private String leadingName;

    // 负责人联系电话
    private String leadingTel;

    // 委托代理人姓名
    private String agentName;

    // 证人
    private String witnessName;

    // 其他人员
    private String othersName;

    // 案件基本情况
    private String caseBaseContent;

    // 办案人员意见
    private String casePersonnelOpinion;

    // 当事人陈述、申辩理由和要求
    private String partyJustification;

    // 行政机关处理意见
    private String departmentHandingOpinions;

    // 听证主持人签名图片
    @Lob
    private String compereSignatureBase64;

    // 听证主持人签名值
    @Lob
    private String compereSignatureValue;

    // 听证主持人签名位置
    private String compereSignaturePlace;

    // 报告日期
    private String reportDateStr;

    // 附件地址
    private String enclosureAddress;

    // 删除标记
    private String delFlg;

    // 执法办案唯一编号
    private String baseId;

    // 执法环节ID
    private String lawLinkId;

    // 变更人ID
    private String updateUserId;

    // 变更人姓名
    private String updateUserName;

    // 变更时间
    private String updateTimeStr;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}



	public String getHearingAddress() {
		return hearingAddress;
	}

	public void setHearingAddress(String hearingAddress) {
		this.hearingAddress = hearingAddress;
	}

	public String getHearingCompere() {
		return hearingCompere;
	}

	public void setHearingCompere(String hearingCompere) {
		this.hearingCompere = hearingCompere;
	}

	public String getRecorderName() {
		return recorderName;
	}

	public void setRecorderName(String recorderName) {
		this.recorderName = recorderName;
	}

	public String getMajorPersonId() {
		return majorPersonId;
	}

	public void setMajorPersonId(String majorPersonId) {
		this.majorPersonId = majorPersonId;
	}

	public String getMajorPersonName() {
		return majorPersonName;
	}

	public void setMajorPersonName(String majorPersonName) {
		this.majorPersonName = majorPersonName;
	}

	public String getMajorPersonCode() {
		return majorPersonCode;
	}

	public void setMajorPersonCode(String majorPersonCode) {
		this.majorPersonCode = majorPersonCode;
	}

	public String getMinorPersonId() {
		return minorPersonId;
	}

	public void setMinorPersonId(String minorPersonId) {
		this.minorPersonId = minorPersonId;
	}

	public String getMinorPersonName() {
		return minorPersonName;
	}

	public void setMinorPersonName(String minorPersonName) {
		this.minorPersonName = minorPersonName;
	}

	public String getMinorPersonCode() {
		return minorPersonCode;
	}

	public void setMinorPersonCode(String minorPersonCode) {
		this.minorPersonCode = minorPersonCode;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getPartyType() {
		return partyType;
	}

	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}

	public String getLeadingName() {
		return leadingName;
	}

	public void setLeadingName(String leadingName) {
		this.leadingName = leadingName;
	}

	public String getLeadingTel() {
		return leadingTel;
	}

	public void setLeadingTel(String leadingTel) {
		this.leadingTel = leadingTel;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getWitnessName() {
		return witnessName;
	}

	public void setWitnessName(String witnessName) {
		this.witnessName = witnessName;
	}

	public String getOthersName() {
		return othersName;
	}

	public void setOthersName(String othersName) {
		this.othersName = othersName;
	}

	public String getCaseBaseContent() {
		return caseBaseContent;
	}

	public void setCaseBaseContent(String caseBaseContent) {
		this.caseBaseContent = caseBaseContent;
	}

	public String getCasePersonnelOpinion() {
		return casePersonnelOpinion;
	}

	public void setCasePersonnelOpinion(String casePersonnelOpinion) {
		this.casePersonnelOpinion = casePersonnelOpinion;
	}

	public String getPartyJustification() {
		return partyJustification;
	}

	public void setPartyJustification(String partyJustification) {
		this.partyJustification = partyJustification;
	}

	public String getDepartmentHandingOpinions() {
		return departmentHandingOpinions;
	}

	public void setDepartmentHandingOpinions(String departmentHandingOpinions) {
		this.departmentHandingOpinions = departmentHandingOpinions;
	}

	public String getCompereSignatureBase64() {
		return compereSignatureBase64;
	}

	public void setCompereSignatureBase64(String compereSignatureBase64) {
		this.compereSignatureBase64 = compereSignatureBase64;
	}

	public String getCompereSignatureValue() {
		return compereSignatureValue;
	}

	public void setCompereSignatureValue(String compereSignatureValue) {
		this.compereSignatureValue = compereSignatureValue;
	}

	public String getCompereSignaturePlace() {
		return compereSignaturePlace;
	}

	public void setCompereSignaturePlace(String compereSignaturePlace) {
		this.compereSignaturePlace = compereSignaturePlace;
	}



	public String getHearingTimeStr() {
		return hearingTimeStr;
	}

	public void setHearingTimeStr(String hearingTimeStr) {
		this.hearingTimeStr = hearingTimeStr;
	}

	public String getReportDateStr() {
		return reportDateStr;
	}

	public void setReportDateStr(String reportDateStr) {
		this.reportDateStr = reportDateStr;
	}

	public String getEnclosureAddress() {
		return enclosureAddress;
	}

	public void setEnclosureAddress(String enclosureAddress) {
		this.enclosureAddress = enclosureAddress;
	}

	public String getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}

	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}

	public String getLawLinkId() {
		return lawLinkId;
	}

	public void setLawLinkId(String lawLinkId) {
		this.lawLinkId = lawLinkId;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

	public Map<String, String> getDocInfo(String caseCode) {
		// TODO Auto-generated method stub
		return null;
	}
    
    
    
}
