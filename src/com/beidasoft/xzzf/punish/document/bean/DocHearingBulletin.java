package com.beidasoft.xzzf.punish.document.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 听证公告实体类
 */
@Entity
@Table(name="ZF_DOC_HEARING_BULLETIN")
public class DocHearingBulletin {
    // 听证公告唯一ID
    @Id
    @Column(name = "ID")
    private String id;

    // 文书区字
    @Column(name = "DOC_AREA")
    private String docArea;

    // 文书年度
    @Column(name = "DOC_YEAR")
    private String docYear;

    // 文书序号
    @Column(name = "DOC_NUM")
    private String docNum;

    // 当事人名称
    @Column(name = "PARTY_NAME")
    private String partyName;

    // 事项一当事人名称
    @Column(name = "HEARING_PARTY_NAME")
    private String hearingPartyName;

    // 事项二听证事由
    @Column(name = "HEARING_CAUSE")
    private String hearingCause;

    // 事项三听证时间
    @Column(name = "HEARING_TIME")
    private Date hearingTime;

    // 事项四听证地点
    @Column(name = "HEARING_ADDRESS")
    private String hearingAddress;

    // 执法单位ID（代码表）
    @Column(name = "LAW_UNIT_ID")
    private String lawUnitId;

    // 执法单位名称
    @Column(name = "LAW_UNIT_NAME")
    private String lawUnitName;

    // 执法单位印章图片
    @Lob
    @Column(name = "LAW_UNIT_SEAL")
    private String lawUnitSeal;

    // 执法单位印章值
    @Lob
    @Column(name = "LAW_UNIT_VALUE")
    private String lawUnitValue;

    // 执法单位印章位置
    @Column(name = "LAW_UNIT_PLACE")
    private String lawUnitPlace;

    // 执法单位印章时间
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

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getHearingPartyName() {
        return hearingPartyName;
    }

    public void setHearingPartyName(String hearingPartyName) {
        this.hearingPartyName = hearingPartyName;
    }

    public String getHearingCause() {
        return hearingCause;
    }

    public void setHearingCause(String hearingCause) {
        this.hearingCause = hearingCause;
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

    public String getLawUnitId() {
        return lawUnitId;
    }

    public void setLawUnitId(String lawUnitId) {
        this.lawUnitId = lawUnitId;
    }

    public String getLawUnitName() {
        return lawUnitName;
    }

    public void setLawUnitName(String lawUnitName) {
        this.lawUnitName = lawUnitName;
    }

    public String getLawUnitSeal() {
        return lawUnitSeal;
    }

    public void setLawUnitSeal(String lawUnitSeal) {
        this.lawUnitSeal = lawUnitSeal;
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

}
