package com.beidasoft.xzzf.punish.document.model;


public class FinalReportModel {
	// 结案报告表主键ID
    private String id;

    // 文书区字
    private String docArea;

    // 文书年度
    private String docYear;

    // 文书序号
    private String docNum;

    // 当 事 人
    private String clientName;

    // 案 由
    private String summary;

    // 立案时间
    private String fileTimeStr;

    // 处罚决定书编号
    private String publishCode;

    // 处罚时间
    private String publishDateStr;

    // 处罚内容
    private String publishContent;

    // 案情概要
    private String summaryCase;

    // 执行情况
    private String executiveCondition;

    // 承办人员意见
    private String majorPersonOpinion;

    // 承办人员签名图片
    private String majorPersonBase64;

    // 承办人员签名值
    private String majorPersonValue;

    // 承办人员签名位置
    private String majorPersonPlace;

    // 承办人员签名时间
    private String majorPersonDateStr;
    
    // 承办人员1签名图片
    private String minorPersonBase64;

    // 承办人员1签名值
    private String minorPersonValue;

    // 承办人员1签名位置
    private String minorPersonPlace;

    // 承办人员1签名时间
    private String minorPersonDateStr;

    // 承办部门负责人签名图片
    private String majorEmpLeaderBase64;

    // 承办部门负责人签名值
    private String majorEmpLeaderValue;

    // 承办部门负责人签名位置
    private String majorEmpLeaderPlace;

    // 承办部门负责人签名时间
    private String majorEmpLeaderDateStr;

    // 承办部门负责人意见
    private String majorEmpLeaderOpinion;

    // 法制部门意见
    private String lawUnitOpinion;

    // 法制部门签名图片
    private String lawUnitBase64;

    // 法制部门签名值
    private String lawUnitValue;

    // 法制部门签名位置
    private String lawUnitPlace;

    // 法制部门签名时间
    private String lawUnitDateStr;

    // 主管领导意见
    private String majorLeaderOpinion;

    // 主管领导签名图片
    private String majorLeaderBase64;

    // 主管领导签名值
    private String majorLeaderValue;

    // 主管领导签名位置
    private String majorLeaderPlace;

    // 主管领导签名时间
    private String majorLeaderDateStr;

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

	public String getDocArea() {
		return docArea;
	}

	public void setDocArea(String docArea) {
		this.docArea = docArea;
	}

	public String getDocYear() {
		return docYear;
	}

	public void setDocYear(String docYear) {
		this.docYear = docYear;
	}

	public String getDocNum() {
		return docNum;
	}

	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getFileTimeStr() {
		return fileTimeStr;
	}

	public void setFileTimeStr(String fileTimeStr) {
		this.fileTimeStr = fileTimeStr;
	}

	public String getPublishCode() {
		return publishCode;
	}

	public void setPublishCode(String publishCode) {
		this.publishCode = publishCode;
	}

	public String getPublishDateStr() {
		return publishDateStr;
	}

	public void setPublishDateStr(String publishDateStr) {
		this.publishDateStr = publishDateStr;
	}

	public String getPublishContent() {
		return publishContent;
	}

	public void setPublishContent(String publishContent) {
		this.publishContent = publishContent;
	}

	public String getSummaryCase() {
		return summaryCase;
	}

	public void setSummaryCase(String summaryCase) {
		this.summaryCase = summaryCase;
	}

	public String getExecutiveCondition() {
		return executiveCondition;
	}

	public void setExecutiveCondition(String executiveCondition) {
		this.executiveCondition = executiveCondition;
	}

	public String getMajorPersonOpinion() {
		return majorPersonOpinion;
	}

	public void setMajorPersonOpinion(String majorPersonOpinion) {
		this.majorPersonOpinion = majorPersonOpinion;
	}

	public String getMajorPersonBase64() {
		return majorPersonBase64;
	}

	public void setMajorPersonBase64(String majorPersonBase64) {
		this.majorPersonBase64 = majorPersonBase64;
	}

	public String getMajorPersonValue() {
		return majorPersonValue;
	}

	public void setMajorPersonValue(String majorPersonValue) {
		this.majorPersonValue = majorPersonValue;
	}

	public String getMajorPersonPlace() {
		return majorPersonPlace;
	}

	public void setMajorPersonPlace(String majorPersonPlace) {
		this.majorPersonPlace = majorPersonPlace;
	}

	public String getMajorPersonDateStr() {
		return majorPersonDateStr;
	}

	public void setMajorPersonDateStr(String majorPersonDateStr) {
		this.majorPersonDateStr = majorPersonDateStr;
	}

	public String getMinorPersonBase64() {
		return minorPersonBase64;
	}

	public void setMinorPersonBase64(String minorPersonBase64) {
		this.minorPersonBase64 = minorPersonBase64;
	}

	public String getMinorPersonValue() {
		return minorPersonValue;
	}

	public void setMinorPersonValue(String minorPersonValue) {
		this.minorPersonValue = minorPersonValue;
	}

	public String getMinorPersonPlace() {
		return minorPersonPlace;
	}

	public void setMinorPersonPlace(String minorPersonPlace) {
		this.minorPersonPlace = minorPersonPlace;
	}

	public String getMinorPersonDateStr() {
		return minorPersonDateStr;
	}

	public void setMinorPersonDateStr(String minorPersonDateStr) {
		this.minorPersonDateStr = minorPersonDateStr;
	}

	public String getMajorEmpLeaderBase64() {
		return majorEmpLeaderBase64;
	}

	public void setMajorEmpLeaderBase64(String majorEmpLeaderBase64) {
		this.majorEmpLeaderBase64 = majorEmpLeaderBase64;
	}

	public String getMajorEmpLeaderValue() {
		return majorEmpLeaderValue;
	}

	public void setMajorEmpLeaderValue(String majorEmpLeaderValue) {
		this.majorEmpLeaderValue = majorEmpLeaderValue;
	}

	public String getMajorEmpLeaderPlace() {
		return majorEmpLeaderPlace;
	}

	public void setMajorEmpLeaderPlace(String majorEmpLeaderPlace) {
		this.majorEmpLeaderPlace = majorEmpLeaderPlace;
	}

	public String getMajorEmpLeaderDateStr() {
		return majorEmpLeaderDateStr;
	}

	public void setMajorEmpLeaderDateStr(String majorEmpLeaderDateStr) {
		this.majorEmpLeaderDateStr = majorEmpLeaderDateStr;
	}

	public String getMajorEmpLeaderOpinion() {
		return majorEmpLeaderOpinion;
	}

	public void setMajorEmpLeaderOpinion(String majorEmpLeaderOpinion) {
		this.majorEmpLeaderOpinion = majorEmpLeaderOpinion;
	}

	public String getLawUnitOpinion() {
		return lawUnitOpinion;
	}

	public void setLawUnitOpinion(String lawUnitOpinion) {
		this.lawUnitOpinion = lawUnitOpinion;
	}

	public String getLawUnitBase64() {
		return lawUnitBase64;
	}

	public void setLawUnitBase64(String lawUnitBase64) {
		this.lawUnitBase64 = lawUnitBase64;
	}

	public String getLawUnitValue() {
		return lawUnitValue;
	}

	public void setLawUnitValue(String lawUnitValue) {
		this.lawUnitValue = lawUnitValue;
	}

	public String getLawUnitPlace() {
		return lawUnitPlace;
	}

	public void setLawUnitPlace(String lawUnitPlace) {
		this.lawUnitPlace = lawUnitPlace;
	}

	public String getLawUnitDateStr() {
		return lawUnitDateStr;
	}

	public void setLawUnitDateStr(String lawUnitDateStr) {
		this.lawUnitDateStr = lawUnitDateStr;
	}

	public String getMajorLeaderOpinion() {
		return majorLeaderOpinion;
	}

	public void setMajorLeaderOpinion(String majorLeaderOpinion) {
		this.majorLeaderOpinion = majorLeaderOpinion;
	}

	public String getMajorLeaderBase64() {
		return majorLeaderBase64;
	}

	public void setMajorLeaderBase64(String majorLeaderBase64) {
		this.majorLeaderBase64 = majorLeaderBase64;
	}

	public String getMajorLeaderValue() {
		return majorLeaderValue;
	}

	public void setMajorLeaderValue(String majorLeaderValue) {
		this.majorLeaderValue = majorLeaderValue;
	}

	public String getMajorLeaderPlace() {
		return majorLeaderPlace;
	}

	public void setMajorLeaderPlace(String majorLeaderPlace) {
		this.majorLeaderPlace = majorLeaderPlace;
	}

	public String getMajorLeaderDateStr() {
		return majorLeaderDateStr;
	}

	public void setMajorLeaderDateStr(String majorLeaderDateStr) {
		this.majorLeaderDateStr = majorLeaderDateStr;
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
