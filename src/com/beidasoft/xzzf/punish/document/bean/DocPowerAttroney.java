package com.beidasoft.xzzf.punish.document.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 授权委托书实体类
 */
@Entity
@Table(name="ZF_DOC_POWER_ATTRONEY")
public class DocPowerAttroney {
    // 强制执行申请书唯一ID 
    @Id
    @Column(name = "ID")
    private String id;

    // 委托人
    @Column(name = "ENTURST_NAME")
    private String enturstName;

    // 法定代表人
    @Column(name = "PRINCIPAL_NAME")
    private String principalName;

    // 身份证件号码
    @Column(name = "PRINCIPAL_CARD_ID")
    private String principalCardId;

    //  受委托人1
    @Column(name = "TRUSTEE_NAME_ONE")
    private String trusteeNameOne;

    // 工作单位1
    @Column(name = "TRUSTEE_UNIT_ONE")
    private String trusteeUnitOne;

    // 职务1
    @Column(name = "TRUSTEE_JOB_ONE")
    private String trusteeJobOne;

    // 身份证件号码1
    @Column(name = "TRUSTEE_CARD_ONE")
    private String trusteeCardOne;

    //  受委托人2
    @Column(name = "TRUSTEE_NAME_TWO")
    private String trusteeNameTwo;

    // 工作单位2
    @Column(name = "TRUSTEE_UNIT_TWO")
    private String trusteeUnitTwo;

    // 职务2
    @Column(name = "TRUSTEE_JOB_TWO")
    private String trusteeJobTwo;

    // 身份证件号码2
    @Column(name = "TRUSTEE_CARD_TWO")
    private String trusteeCardTwo;

    // 代理人
    @Column(name = "AGENCY_NAME")
    private String agencyName;

    // 执法单位
    @Column(name = "LAW_UNIT")
    private String lawUnit;

    // 调查事项
    @Column(name = "CHECK_EVENT")
    private String checkEvent;

    // 委托人签名图片
    @Lob
    @Column(name = "ENTURST_SIGNATURE_SEAL")
    private String enturstSignatureSeal;

    // 委托人签名值
    @Lob
    @Column(name = "ENTURST_SIGNATURE_VALUE")
    private String enturstSignatureValue;

    // 委托人签名位置
    @Column(name = "ENTURST_SIGNATURE_PLACE")
    private String enturstSignaturePlace;

    // 委托人盖章图片
    @Lob
    @Column(name = "ENTURST_STAMP_SEAL")
    private String enturstStampSeal;

    // 委托人盖章值
    @Lob
    @Column(name = "ENTURST_STAMP_VALUE")
    private String enturstStampValue;

    // 委托人盖章位置
    @Column(name = "ENTURST_STAMP_PLACE")
    private String enturstStampPlace;

    // 法定代表人签名图片
    @Lob
    @Column(name = "AGENCY_SIGNATURE_SEAL")
    private String agencySignatureSeal;

    // 法定代表人签名值
    @Lob
    @Column(name = "AGENCY_SIGNATURE_VALUE")
    private String agencySignatureValue;

    // 法定代表人签名位置
    @Column(name = "AGENCY_SIGNATURE_PLACE")
    private String agencySignaturePlace;

    // 法定代表人盖章图片
    @Lob
    @Column(name = "AGENCY_STAMP_SEAL")
    private String agencyStampSeal;

    // 法定代表人盖章值
    @Lob
    @Column(name = "AGENCY_STAMP_VALUE")
    private String agencyStampValue;

    // 法定代表人盖章位置
    @Column(name = "AGENCY_STAMP_PLACE")
    private String agencyStampPlace;

    // 签发日期
    @Column(name = "LAW_UNIT_DATE")
    private Date lawUnitDate;

    // 附件地址
    @Column(name = "PROSESS_UNIT")
    private String prosessUnit;

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

    public String getEnturstName() {
        return enturstName;
    }

    public void setEnturstName(String enturstName) {
        this.enturstName = enturstName;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public String getPrincipalCardId() {
        return principalCardId;
    }

    public void setPrincipalCardId(String principalCardId) {
        this.principalCardId = principalCardId;
    }

    public String getTrusteeNameOne() {
        return trusteeNameOne;
    }

    public void setTrusteeNameOne(String trusteeNameOne) {
        this.trusteeNameOne = trusteeNameOne;
    }

    public String getTrusteeUnitOne() {
        return trusteeUnitOne;
    }

    public void setTrusteeUnitOne(String trusteeUnitOne) {
        this.trusteeUnitOne = trusteeUnitOne;
    }

    public String getTrusteeJobOne() {
        return trusteeJobOne;
    }

    public void setTrusteeJobOne(String trusteeJobOne) {
        this.trusteeJobOne = trusteeJobOne;
    }

    public String getTrusteeCardOne() {
        return trusteeCardOne;
    }

    public void setTrusteeCardOne(String trusteeCardOne) {
        this.trusteeCardOne = trusteeCardOne;
    }

    public String getTrusteeNameTwo() {
        return trusteeNameTwo;
    }

    public void setTrusteeNameTwo(String trusteeNameTwo) {
        this.trusteeNameTwo = trusteeNameTwo;
    }

    public String getTrusteeUnitTwo() {
        return trusteeUnitTwo;
    }

    public void setTrusteeUnitTwo(String trusteeUnitTwo) {
        this.trusteeUnitTwo = trusteeUnitTwo;
    }

    public String getTrusteeJobTwo() {
        return trusteeJobTwo;
    }

    public void setTrusteeJobTwo(String trusteeJobTwo) {
        this.trusteeJobTwo = trusteeJobTwo;
    }

    public String getTrusteeCardTwo() {
        return trusteeCardTwo;
    }

    public void setTrusteeCardTwo(String trusteeCardTwo) {
        this.trusteeCardTwo = trusteeCardTwo;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getLawUnit() {
        return lawUnit;
    }

    public void setLawUnit(String lawUnit) {
        this.lawUnit = lawUnit;
    }

    public String getCheckEvent() {
        return checkEvent;
    }

    public void setCheckEvent(String checkEvent) {
        this.checkEvent = checkEvent;
    }

    public String getEnturstSignatureSeal() {
        return enturstSignatureSeal;
    }

    public void setEnturstSignatureSeal(String enturstSignatureSeal) {
        this.enturstSignatureSeal = enturstSignatureSeal;
    }

    public String getEnturstSignatureValue() {
        return enturstSignatureValue;
    }

    public void setEnturstSignatureValue(String enturstSignatureValue) {
        this.enturstSignatureValue = enturstSignatureValue;
    }

    public String getEnturstSignaturePlace() {
        return enturstSignaturePlace;
    }

    public void setEnturstSignaturePlace(String enturstSignaturePlace) {
        this.enturstSignaturePlace = enturstSignaturePlace;
    }

    public String getEnturstStampSeal() {
        return enturstStampSeal;
    }

    public void setEnturstStampSeal(String enturstStampSeal) {
        this.enturstStampSeal = enturstStampSeal;
    }

    public String getEnturstStampValue() {
        return enturstStampValue;
    }

    public void setEnturstStampValue(String enturstStampValue) {
        this.enturstStampValue = enturstStampValue;
    }

    public String getEnturstStampPlace() {
        return enturstStampPlace;
    }

    public void setEnturstStampPlace(String enturstStampPlace) {
        this.enturstStampPlace = enturstStampPlace;
    }

    public String getAgencySignatureSeal() {
        return agencySignatureSeal;
    }

    public void setAgencySignatureSeal(String agencySignatureSeal) {
        this.agencySignatureSeal = agencySignatureSeal;
    }

    public String getAgencySignatureValue() {
        return agencySignatureValue;
    }

    public void setAgencySignatureValue(String agencySignatureValue) {
        this.agencySignatureValue = agencySignatureValue;
    }

    public String getAgencySignaturePlace() {
        return agencySignaturePlace;
    }

    public void setAgencySignaturePlace(String agencySignaturePlace) {
        this.agencySignaturePlace = agencySignaturePlace;
    }

    public String getAgencyStampSeal() {
        return agencyStampSeal;
    }

    public void setAgencyStampSeal(String agencyStampSeal) {
        this.agencyStampSeal = agencyStampSeal;
    }

    public String getAgencyStampValue() {
        return agencyStampValue;
    }

    public void setAgencyStampValue(String agencyStampValue) {
        this.agencyStampValue = agencyStampValue;
    }

    public String getAgencyStampPlace() {
        return agencyStampPlace;
    }

    public void setAgencyStampPlace(String agencyStampPlace) {
        this.agencyStampPlace = agencyStampPlace;
    }

    public Date getLawUnitDate() {
        return lawUnitDate;
    }

    public void setLawUnitDate(Date lawUnitDate) {
        this.lawUnitDate = lawUnitDate;
    }

    public String getProsessUnit() {
        return prosessUnit;
    }

    public void setProsessUnit(String prosessUnit) {
        this.prosessUnit = prosessUnit;
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
