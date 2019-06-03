package com.beidasoft.xzzf.punish.document.model;

import java.util.Date;
import java.util.Map;

/**
 *	强制执行申请书
 */
public class EngorceApplicationModel {

	// 强制执行申请书唯一ID 
    private String id;

    // 强制执行申请书(区字)
    private String enforceAddr;

    // 强制执行申请书(年度)
    private String enforceYear;

    // 强制执行申请书(序列)
    private String enforceSequence;
    
    // 执法机关名称
    private String agencyName;

    // 人民法院名称 
    private String courtName;

    // 执罚决定类型
    private String penaltyDecision;
    
    // 执罚决定类型
    private String adPenaltyDecision;

    // 处罚决定书送达日期
    private String enforceDataStr;

    // 处罚决定书(地址)
    private String penaltyAddr;

    // 处罚决定书(年)
    private String penaltyYear;

    // 处罚决定书(序列)
    private String penaltySequence;

    // 加处罚决定书送达日期
    private String addPenaltyDecisionStr;

    // 加处罚决定书(地址)
    private String addAddr;

    // 加处罚决定书(年)
    private String addYear;

    // 加处罚决定书(序列)
    private String addSequence;

    // 催告书送达日期
    private String exigentNoticeDataStr;

    // 行政处罚履行催告书(地址)
    private String exigentAddr;

    // 行政处罚履行催告书(年)
    private String exigentYear;

    // 行政处罚履行催告书(序列)
    private String exigentSequence;

  // 罚款金额选择状态
    private String finesSumChkState;

    // 罚款金额
    private String finesSum;

    // 违法所得选择状态
    private String illegalIncomeChkState;

    // 违法所得金额
    private String illegalIncomeSum;

    // 加处罚款金额选择状态
    private String addfinesSumChkState;

    // 加处罚款金额
    private String addfinesSum;

    // 停业整顿选择状态
    private String closeDownChkState;

    // 停业整顿天数
    private String closeDownDays;

    // 负责人签名图片
    private String siteLeaderSignatureBase64;

    // 负责人签名值
    private String siteLeaderValue;

    // 负责人签名位置
    private String siteLeaderPlace;

    // 行政机关落款图片
    private String exigentLeaderSignatureBase64;

    // 行政机关落款值
    private String exigentLeaderSignatureValue;

    // 行政机关落款位置
    private String exigentLeaderPlace;

    // 行政机关盖章图片
    private String lawUnitSignatureBase64;

    // 行政机关盖章值
    private String lawUnitSignatureValue;

    // 行政机关盖章位置
    private String lawUnitPlace;

    // 行政机关盖章时间
    private String lawUnitDateStr;

    // 组织机构编号
    private String organsCode;

    // 组织机构名称
    private String organsName;

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
    private Date updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEnforceAddr() {
		return enforceAddr;
	}

	public void setEnforceAddr(String enforceAddr) {
		this.enforceAddr = enforceAddr;
	}

	public String getEnforceYear() {
		return enforceYear;
	}

	public void setEnforceYear(String enforceYear) {
		this.enforceYear = enforceYear;
	}

	public String getEnforceSequence() {
		return enforceSequence;
	}

	public void setEnforceSequence(String enforceSequence) {
		this.enforceSequence = enforceSequence;
	}

	public String getCourtName() {
		return courtName;
	}

	public void setCourtName(String courtName) {
		this.courtName = courtName;
	}

	

	public String getPenaltyDecision() {
		return penaltyDecision;
	}

	public void setPenaltyDecision(String penaltyDecision) {
		this.penaltyDecision = penaltyDecision;
	}

	public void setAdPenaltyDecision(String adPenaltyDecision) {
		this.adPenaltyDecision = adPenaltyDecision;
	}

	public String getEnforceDataStr() {
		return enforceDataStr;
	}

	public void setEnforceDataStr(String enforceDataStr) {
		this.enforceDataStr = enforceDataStr;
	}

	public String getPenaltyAddr() {
		return penaltyAddr;
	}

	public void setPenaltyAddr(String penaltyAddr) {
		this.penaltyAddr = penaltyAddr;
	}

	public String getPenaltyYear() {
		return penaltyYear;
	}

	public void setPenaltyYear(String penaltyYear) {
		this.penaltyYear = penaltyYear;
	}

	public String getPenaltySequence() {
		return penaltySequence;
	}

	public void setPenaltySequence(String penaltySequence) {
		this.penaltySequence = penaltySequence;
	}

	public String getAddPenaltyDecisionStr() {
		return addPenaltyDecisionStr;
	}

	public void setAddPenaltyDecisionStr(String addPenaltyDecisionStr) {
		this.addPenaltyDecisionStr = addPenaltyDecisionStr;
	}

	public String getAddAddr() {
		return addAddr;
	}

	public void setAddAddr(String addAddr) {
		this.addAddr = addAddr;
	}

	public String getAddYear() {
		return addYear;
	}

	public void setAddYear(String addYear) {
		this.addYear = addYear;
	}

	public String getAddSequence() {
		return addSequence;
	}

	public void setAddSequence(String addSequence) {
		this.addSequence = addSequence;
	}

	public String getExigentNoticeDataStr() {
		return exigentNoticeDataStr;
	}

	public void setExigentNoticeDataStr(String exigentNoticeDataStr) {
		this.exigentNoticeDataStr = exigentNoticeDataStr;
	}

	public String getExigentAddr() {
		return exigentAddr;
	}

	public void setExigentAddr(String exigentAddr) {
		this.exigentAddr = exigentAddr;
	}

	public String getExigentYear() {
		return exigentYear;
	}

	public void setExigentYear(String exigentYear) {
		this.exigentYear = exigentYear;
	}

	public String getExigentSequence() {
		return exigentSequence;
	}

	public void setExigentSequence(String exigentSequence) {
		this.exigentSequence = exigentSequence;
	}

	public String getFinesSumChkState() {
		return finesSumChkState;
	}

	public void setFinesSumChkState(String finesSumChkState) {
		this.finesSumChkState = finesSumChkState;
	}

	public String getFinesSum() {
		return finesSum;
	}

	public void setFinesSum(String finesSum) {
		this.finesSum = finesSum;
	}

	public String getIllegalIncomeChkState() {
		return illegalIncomeChkState;
	}

	public void setIllegalIncomeChkState(String illegalIncomeChkState) {
		this.illegalIncomeChkState = illegalIncomeChkState;
	}

	public String getIllegalIncomeSum() {
		return illegalIncomeSum;
	}

	public void setIllegalIncomeSum(String illegalIncomeSum) {
		this.illegalIncomeSum = illegalIncomeSum;
	}

	public String getAddfinesSumChkState() {
		return addfinesSumChkState;
	}

	public void setAddfinesSumChkState(String addfinesSumChkState) {
		this.addfinesSumChkState = addfinesSumChkState;
	}

	public String getAddfinesSum() {
		return addfinesSum;
	}

	public void setAddfinesSum(String addfinesSum) {
		this.addfinesSum = addfinesSum;
	}

	public String getCloseDownChkState() {
		return closeDownChkState;
	}

	public void setCloseDownChkState(String closeDownChkState) {
		this.closeDownChkState = closeDownChkState;
	}

	public String getCloseDownDays() {
		return closeDownDays;
	}

	public void setCloseDownDays(String closeDownDays) {
		this.closeDownDays = closeDownDays;
	}

	public String getSiteLeaderSignatureBase64() {
		return siteLeaderSignatureBase64;
	}

	public void setSiteLeaderSignatureBase64(String siteLeaderSignatureBase64) {
		this.siteLeaderSignatureBase64 = siteLeaderSignatureBase64;
	}

	public String getSiteLeaderValue() {
		return siteLeaderValue;
	}

	public void setSiteLeaderValue(String siteLeaderValue) {
		this.siteLeaderValue = siteLeaderValue;
	}

	public String getSiteLeaderPlace() {
		return siteLeaderPlace;
	}

	public void setSiteLeaderPlace(String siteLeaderPlace) {
		this.siteLeaderPlace = siteLeaderPlace;
	}

	public String getExigentLeaderSignatureBase64() {
		return exigentLeaderSignatureBase64;
	}

	public void setExigentLeaderSignatureBase64(String exigentLeaderSignatureBase64) {
		this.exigentLeaderSignatureBase64 = exigentLeaderSignatureBase64;
	}

	public String getExigentLeaderSignatureValue() {
		return exigentLeaderSignatureValue;
	}

	public void setExigentLeaderSignatureValue(String exigentLeaderSignatureValue) {
		this.exigentLeaderSignatureValue = exigentLeaderSignatureValue;
	}

	public String getExigentLeaderPlace() {
		return exigentLeaderPlace;
	}

	public void setExigentLeaderPlace(String exigentLeaderPlace) {
		this.exigentLeaderPlace = exigentLeaderPlace;
	}

	public String getLawUnitSignatureBase64() {
		return lawUnitSignatureBase64;
	}

	public void setLawUnitSignatureBase64(String lawUnitSignatureBase64) {
		this.lawUnitSignatureBase64 = lawUnitSignatureBase64;
	}

	public String getLawUnitSignatureValue() {
		return lawUnitSignatureValue;
	}

	public void setLawUnitSignatureValue(String lawUnitSignatureValue) {
		this.lawUnitSignatureValue = lawUnitSignatureValue;
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

	public String getOrgansCode() {
		return organsCode;
	}

	public void setOrgansCode(String organsCode) {
		this.organsCode = organsCode;
	}

	public String getOrgansName() {
		return organsName;
	}

	public void setOrgansName(String organsName) {
		this.organsName = organsName;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getAdPenaltyDecision() {
		return adPenaltyDecision;
	}
	
	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public Map<String, String> getDocInfo(String caseCode) {
		// TODO Auto-generated method stub
		return null;
	}

	
    
}
