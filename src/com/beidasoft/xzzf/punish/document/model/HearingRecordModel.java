package com.beidasoft.xzzf.punish.document.model;


public class HearingRecordModel {
	
	private String id;

    // 案由
    private String caseReason;

    // 听证时间（开始）
    private String hearingTimeStartStr;

    // 听证时间（结束）
    private String hearingTimeEndStr;

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

    // 负责人性别（代码表）
    private String leadingSex;

    // 负责人联系电话
    private String leadingTel;

    // 当事人住址
    private String partyAddress;

    // 委托代理人姓名
    private String agentName;

    // 委托代理人性别（代码表）
    private String agentSex;

    // 委托代理人联系电话
    private String agentTel;

    // 委托代理人姓名2
    private String agentName2;

    // 委托代理人性别2（代码表）
    private String agentSex2;

    // 委托代理人联系电话2
    private String agentTel2;

    // 证人
    private String witnessName;

    // 其他人员
    private String othersName;

    // 听证笔录
    private String hearingRecord;

    // 当事人签名图片
    private String partySignatureBase64;

    // 当事人签名值
    private String partySignatureValue;

    // 当事人签名位置
    private String partySignaturePlace;

    //是否备注
    private String isRemarks;
    
    //备注
    private String remarks;
    
    // 委托代理人签名图片
    private String ahentSignatureBase64;

    // 委托代理人签名值
    private String ahentSignatureValue;

    // 委托代理人签名位置
    private String ahentSignaturePlace;

    // 委托代理人签名图片2
    private String ahentSignatureBase642;

    // 委托代理人签名值2
    private String ahentSignatureValue2;

    // 委托代理人签名位置2
    private String ahentSignaturePlace2;

    // 主办人签名图片
    private String majorSignatureBase64;

    // 主办人签名值
    private String majorSignatureValue;

    // 主办人签名位置
    private String majorSignaturePlace;

    // 协办人签名图片
    private String minorSignatureBase64;

    // 协办人签名值
    private String minorSignatureValue;

    // 协办人签名位置
    private String minorSignaturePlace;

    // 听证主持人签名图片
    private String compereSignatureBase64;

    // 听证主持人签名值
    private String compereSignatureValue;

    // 听证主持人签名位置
    private String compereSignaturePlace;

    // 记录人签名图片
    private String recorderSignatureBase64;

    // 记录人签名值
    private String recorderSignatureValue;

    // 记录人签名位置
    private String recorderSignaturePlace;

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

	public String getCaseReason() {
		return caseReason;
	}

	public void setCaseReason(String caseReason) {
		this.caseReason = caseReason;
	}

	public String getHearingTimeStartStr() {
		return hearingTimeStartStr;
	}

	public void setHearingTimeStartStr(String hearingTimeStartStr) {
		this.hearingTimeStartStr = hearingTimeStartStr;
	}

	public String getHearingTimeEndStr() {
		return hearingTimeEndStr;
	}

	public void setHearingTimeEndStr(String hearingTimeEndStr) {
		this.hearingTimeEndStr = hearingTimeEndStr;
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

	public String getLeadingSex() {
		return leadingSex;
	}

	public void setLeadingSex(String leadingSex) {
		this.leadingSex = leadingSex;
	}

	public String getLeadingTel() {
		return leadingTel;
	}

	public void setLeadingTel(String leadingTel) {
		this.leadingTel = leadingTel;
	}

	public String getPartyAddress() {
		return partyAddress;
	}

	public void setPartyAddress(String partyAddress) {
		this.partyAddress = partyAddress;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAgentSex() {
		return agentSex;
	}

	public void setAgentSex(String agentSex) {
		this.agentSex = agentSex;
	}

	public String getIsRemarks() {
		return isRemarks;
	}

	public void setIsRemarks(String isRemarks) {
		this.isRemarks = isRemarks;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getAgentTel() {
		return agentTel;
	}

	public void setAgentTel(String agentTel) {
		this.agentTel = agentTel;
	}

	public String getAgentName2() {
		return agentName2;
	}

	public void setAgentName2(String agentName2) {
		this.agentName2 = agentName2;
	}

	public String getAgentSex2() {
		return agentSex2;
	}

	public void setAgentSex2(String agentSex2) {
		this.agentSex2 = agentSex2;
	}

	public String getAgentTel2() {
		return agentTel2;
	}

	public void setAgentTel2(String agentTel2) {
		this.agentTel2 = agentTel2;
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

	public String getHearingRecord() {
		return hearingRecord;
	}

	public void setHearingRecord(String hearingRecord) {
		this.hearingRecord = hearingRecord;
	}

	public String getPartySignatureBase64() {
		return partySignatureBase64;
	}

	public void setPartySignatureBase64(String partySignatureBase64) {
		this.partySignatureBase64 = partySignatureBase64;
	}

	public String getPartySignatureValue() {
		return partySignatureValue;
	}

	public void setPartySignatureValue(String partySignatureValue) {
		this.partySignatureValue = partySignatureValue;
	}

	public String getPartySignaturePlace() {
		return partySignaturePlace;
	}

	public void setPartySignaturePlace(String partySignaturePlace) {
		this.partySignaturePlace = partySignaturePlace;
	}

	public String getAhentSignatureBase64() {
		return ahentSignatureBase64;
	}

	public void setAhentSignatureBase64(String ahentSignatureBase64) {
		this.ahentSignatureBase64 = ahentSignatureBase64;
	}

	public String getAhentSignatureValue() {
		return ahentSignatureValue;
	}

	public void setAhentSignatureValue(String ahentSignatureValue) {
		this.ahentSignatureValue = ahentSignatureValue;
	}

	public String getAhentSignaturePlace() {
		return ahentSignaturePlace;
	}

	public void setAhentSignaturePlace(String ahentSignaturePlace) {
		this.ahentSignaturePlace = ahentSignaturePlace;
	}

	public String getAhentSignatureBase642() {
		return ahentSignatureBase642;
	}

	public void setAhentSignatureBase642(String ahentSignatureBase642) {
		this.ahentSignatureBase642 = ahentSignatureBase642;
	}

	public String getAhentSignatureValue2() {
		return ahentSignatureValue2;
	}

	public void setAhentSignatureValue2(String ahentSignatureValue2) {
		this.ahentSignatureValue2 = ahentSignatureValue2;
	}

	public String getAhentSignaturePlace2() {
		return ahentSignaturePlace2;
	}

	public void setAhentSignaturePlace2(String ahentSignaturePlace2) {
		this.ahentSignaturePlace2 = ahentSignaturePlace2;
	}

	public String getMajorSignatureBase64() {
		return majorSignatureBase64;
	}

	public void setMajorSignatureBase64(String majorSignatureBase64) {
		this.majorSignatureBase64 = majorSignatureBase64;
	}

	public String getMajorSignatureValue() {
		return majorSignatureValue;
	}

	public void setMajorSignatureValue(String majorSignatureValue) {
		this.majorSignatureValue = majorSignatureValue;
	}

	public String getMajorSignaturePlace() {
		return majorSignaturePlace;
	}

	public void setMajorSignaturePlace(String majorSignaturePlace) {
		this.majorSignaturePlace = majorSignaturePlace;
	}

	public String getMinorSignatureBase64() {
		return minorSignatureBase64;
	}

	public void setMinorSignatureBase64(String minorSignatureBase64) {
		this.minorSignatureBase64 = minorSignatureBase64;
	}

	public String getMinorSignatureValue() {
		return minorSignatureValue;
	}

	public void setMinorSignatureValue(String minorSignatureValue) {
		this.minorSignatureValue = minorSignatureValue;
	}

	public String getMinorSignaturePlace() {
		return minorSignaturePlace;
	}

	public void setMinorSignaturePlace(String minorSignaturePlace) {
		this.minorSignaturePlace = minorSignaturePlace;
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

	public String getRecorderSignatureBase64() {
		return recorderSignatureBase64;
	}

	public void setRecorderSignatureBase64(String recorderSignatureBase64) {
		this.recorderSignatureBase64 = recorderSignatureBase64;
	}

	public String getRecorderSignatureValue() {
		return recorderSignatureValue;
	}

	public void setRecorderSignatureValue(String recorderSignatureValue) {
		this.recorderSignatureValue = recorderSignatureValue;
	}

	public String getRecorderSignaturePlace() {
		return recorderSignaturePlace;
	}

	public void setRecorderSignaturePlace(String recorderSignaturePlace) {
		this.recorderSignaturePlace = recorderSignaturePlace;
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
    
    
}
