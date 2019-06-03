package com.beidasoft.xzzf.punish.document.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 行政处罚听证报告实体类
 */
@Entity
@Table(name="ZF_DOC_HEARING_REPORT")
public class DocHearingReport {
    // 听证报告唯一ID
    @Id
    @Column(name = "ID")
    private String id;

    // 案件名称
    @Column(name = "CASE_NAME")
    private String caseName;

    // 听证时间
    @Column(name = "HEARING_TIME")
    private Date hearingTime;

    // 听证地点
    @Column(name = "HEARING_ADDRESS")
    private String hearingAddress;

    // 听证主持人
    @Column(name = "HEARING_COMPERE")
    private String hearingCompere;

    // 记录人
    @Column(name = "RECORDER_NAME")
    private String recorderName;

    // 主办人ID
    @Column(name = "MAJOR_PERSON_ID")
    private String majorPersonId;

    // 主办人姓名
    @Column(name = "MAJOR_PERSON_NAME")
    private String majorPersonName;

    // 主办人执法证号
    @Column(name = "MAJOR_PERSON_CODE")
    private String majorPersonCode;

    // 协办人ID
    @Column(name = "MINOR_PERSON_ID")
    private String minorPersonId;

    // 协办人姓名
    @Column(name = "MINOR_PERSON_NAME")
    private String minorPersonName;

    // 协办人执法证号
    @Column(name = "MINOR_PERSON_CODE")
    private String minorPersonCode;

    // 当事人名称
    @Column(name = "PARTY_NAME")
    private String partyName;

    // 当事人类型（代码表）
    @Column(name = "PARTY_TYPE")
    private String partyType;

    // 负责人姓名
    @Column(name = "LEADING_NAME")
    private String leadingName;

    // 负责人联系电话
    @Column(name = "LEADING_TEL")
    private String leadingTel;

    // 委托代理人姓名
    @Column(name = "AGENT_NAME")
    private String agentName;

    // 证人
    @Column(name = "WITNESS_NAME")
    private String witnessName;

    // 其他人员
    @Column(name = "OTHERS_NAME")
    private String othersName;

    // 案件基本情况
    @Column(name = "CASE_BASE_CONTENT")
    private String caseBaseContent;

    // 办案人员意见
    @Column(name = "CASE_PERSONNEL_OPINION")
    private String casePersonnelOpinion;

    // 当事人陈述、申辩理由和要求
    @Column(name = "PARTY_JUSTIFICATION")
    private String partyJustification;

    // 行政机关处理意见
    @Column(name = "DEPARTMENT_HANDING_OPINIONS")
    private String departmentHandingOpinions;

    // 听证主持人签名图片
    @Lob
    @Column(name = "COMPERE_SIGNATURE_BASE64")
    private String compereSignatureBase64;

    // 听证主持人签名值
    @Lob
    @Column(name = "COMPERE_SIGNATURE_VALUE")
    private String compereSignatureValue;

    // 听证主持人签名位置
    @Column(name = "COMPERE_SIGNATURE_PLACE")
    private String compereSignaturePlace;

    // 报告日期
    @Column(name = "REPORT_DATE")
    private Date reportDate;

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

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public Date getHearingTime() {
        return hearingTime;
    }

    public void setHearingTime(Date hearingTime) {
        this.hearingTime = hearingTime;
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

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
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

}
