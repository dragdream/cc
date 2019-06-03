package com.beidasoft.xzzf.punish.document.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 强制执行申请书实体类
 */
@Entity
@Table(name="ZF_DOC_ENGORCE_APPLICATION")
public class DocEngorceApplication {
    // 强制执行申请书唯一ID 
    @Id
    @Column(name = "ID")
    private String id;

    // 强制执行申请书(区字)
    @Column(name = "ENFORCE_ADDR")
    private String enforceAddr;

    // 强制执行申请书(年度)
    @Column(name = "ENFORCE_YEAR")
    private String enforceYear;

    // 强制执行申请书(序列)
    @Column(name = "ENFORCE_SEQUENCE")
    private String enforceSequence;

    // 人民法院名称 
    @Column(name = "COURT_NAME")
    private String courtName;
    
    // 执法机关名称
    @Column(name = "AGENCY_NAME")
    private String agencyName;

    // 执罚决定类型
    @Column(name = "PENALTY_DECISION")
    private String penaltyDecision;
    
    // 执罚决定类型
    @Column(name = "AD_PENALTY_DECISION")
    private String adPenaltyDecision;

    // 处罚决定书送达日期
    @Column(name = "ENFORCE_DATA")
    private Date enforceData;

    // 处罚决定书(地址)
    @Column(name = "PENALTY_ADDR")
    private String penaltyAddr;

    // 处罚决定书(年)
    @Column(name = "PENALTY_YEAR")
    private String penaltyYear;

    // 处罚决定书(序列)
    @Column(name = "PENALTY_SEQUENCE")
    private String penaltySequence;

    // 加处罚决定书送达日期
    @Column(name = "ADD_PENALTY_DECISION")
    private Date addPenaltyDecision;

    // 加处罚决定书(地址)
    @Column(name = "ADD_ADDR")
    private String addAddr;

    // 加处罚决定书(年)
    @Column(name = "ADD_YEAR")
    private String addYear;

    // 加处罚决定书(序列)
    @Column(name = "ADD_SEQUENCE")
    private String addSequence;

    // 催告书送达日期
    @Column(name = "EXIGENT_NOTICE_DATA")
    private Date exigentNoticeData;

    // 行政处罚履行催告书(地址)
    @Column(name = "EXIGENT_ADDR")
    private String exigentAddr;

    // 行政处罚履行催告书(年)
    @Column(name = "EXIGENT_YEAR")
    private String exigentYear;

    // 行政处罚履行催告书(序列)
    @Column(name = "EXIGENT_SEQUENCE")
    private String exigentSequence;

    // 罚款金额选择状态
    @Column(name = "FINES_SUM_CHK_STATE")
    private String finesSumChkState;

    // 罚款金额
    @Column(name = "FINES_SUM")
    private String finesSum;

    // 违法所得选择状态
    @Column(name = "ILLEGAL_INCOME_CHK_STATE")
    private String illegalIncomeChkState;

    // 违法所得金额
    @Column(name = "ILLEGAL_INCOME_SUM")
    private String illegalIncomeSum;

    // 加处罚款金额选择状态
    @Column(name = "ADDFINES_SUM_CHK_STATE")
    private String addfinesSumChkState;

    // 加处罚款金额
    @Column(name = "ADDFINES_SUM")
    private String addfinesSum;

    // 停业整顿选择状态
    @Column(name = "CLOSE_DOWN_CHK_STATE")
    private String closeDownChkState;

    // 停业整顿天数
    @Column(name = "CLOSE_DOWN_DAYS")
    private String closeDownDays;

    // 负责人签名图片
    @Lob
    @Column(name = "SITE_LEADER_SIGNATURE_BASE64")
    private String siteLeaderSignatureBase64;

    // 负责人签名值
    @Lob
    @Column(name = "SITE_LEADER_VALUE")
    private String siteLeaderValue;

    // 负责人签名位置
    @Column(name = "SITE_LEADER_PLACE")
    private String siteLeaderPlace;

    // 行政机关落款图片
    @Lob
    @Column(name = "LEADER_SIGNATURE_BASE64")
    private String exigentLeaderSignatureBase64;

    // 行政机关落款值
    @Lob
    @Column(name = "LEADER_SIGNATURE_VALUE")
    private String exigentLeaderSignatureValue;

    // 行政机关落款位置
    @Column(name = "LEADER_SIGNATURE_PLACE")
    private String exigentLeaderPlace;

    // 行政机关盖章图片
    @Lob
    @Column(name = "LAW_UNIT_SIGNATURE_BASE64")
    private String lawUnitSignatureBase64;

    // 行政机关盖章值
    @Lob
    @Column(name = "LAW_UNIT_SIGNATURE_VALUE")
    private String lawUnitSignatureValue;

    // 行政机关盖章位置
    @Column(name = "LAW_UNIT_PLACE")
    private String lawUnitPlace;

    // 行政机关盖章时间
    @Column(name = "LAW_UNIT_DATE")
    private Date lawUnitDate;

    // 组织机构编号
    @Column(name = "ORGANS_CODE")
    private String organsCode;

    // 组织机构名称
    @Column(name = "ORGANS_NAME")
    private String organsName;

    // 附件地址
    @Column(name = "ENCLOSURE_ADDRESS")
    private String enclosureAddress;

    // 删除标记
    @Column(name = "DEL_FLG")
    private String delFlg;

    // 执法办案唯一编号
    @Column(name = "BASE_ID")
    private String baseId;

    // 执法环节ID
    @Column(name = "LAW_LINK_ID")
    private String lawLinkId;

    // 创建人ID
    @Column(name = "CREATE_USER_ID")
    private String createUserId;

    // 创建人姓名
    @Column(name = "CREATE_USER_NAME")
    private String createUserName;

    // 创建时间
    @Column(name = "CREATE_TIME")
    private Date createTime;

    // 变更人ID
    @Column(name = "UPDATE_USER_ID")
    private String updateUserId;

    // 变更人姓名
    @Column(name = "UPDATE_USER_NAME")
    private String updateUserName;

    // 变更时间
    @Column(name = "UPDATE_TIME")
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

	public String getAdPenaltyDecision() {
		return adPenaltyDecision;
	}

	public void setAdPenaltyDecision(String adPenaltyDecision) {
		this.adPenaltyDecision = adPenaltyDecision;
	}

	public Date getEnforceData() {
		return enforceData;
	}

	public void setEnforceData(Date enforceData) {
		this.enforceData = enforceData;
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

	public Date getAddPenaltyDecision() {
		return addPenaltyDecision;
	}

	public void setAddPenaltyDecision(Date addPenaltyDecision) {
		this.addPenaltyDecision = addPenaltyDecision;
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

	public Date getExigentNoticeData() {
		return exigentNoticeData;
	}

	public void setExigentNoticeData(Date exigentNoticeData) {
		this.exigentNoticeData = exigentNoticeData;
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

	public Date getLawUnitDate() {
		return lawUnitDate;
	}

	public void setLawUnitDate(Date lawUnitDate) {
		this.lawUnitDate = lawUnitDate;
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

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

    
}
