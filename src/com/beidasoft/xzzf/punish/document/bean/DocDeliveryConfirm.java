package com.beidasoft.xzzf.punish.document.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 送达信息确认书实体类
 */
@Entity
@Table(name="ZF_DOC_DELIVERY_CONFIRM")
public class DocDeliveryConfirm {
    // 送达信息确认书唯一ID 
    @Id
    @Column(name = "ID")
    private String id;

    // 案由
    @Column(name = "CAUSE_ACTION")
    private String causeAction;

    // 当事人：（姓名/名称）
    @Column(name = "PARTIES_NAME")
    private String partiesName;

    // 送达地址
    @Column(name = "SEND_ADDR")
    private String sendAddr;

    // 邮政编码
    @Column(name = "POST_CODE")
    private String postCode;

    // 联系人
    @Column(name = "LINK_PERSON")
    private String linkPerson;

    // 联系人电话(固话)
    @Column(name = "TELEPHONE")
    private String telephone;

    // 联系人电话(手机)
    @Column(name = "MOBILE_PHONE")
    private String mobilePhone;

    // 其他联系方式
    @Column(name = "OTHER_PHONE")
    private String otherPhone;

    // 代收人：（姓名/名称）
    @Column(name = "AGENCY_PARTIES_NAME")
    private String agencyPartiesName;

    // 送达地址（代收人）
    @Column(name = "AGENCY_SEND_ADDR")
    private String agencySendAddr;

    // 邮政编码（代收人）
    @Column(name = "AGENCY_POST_CODE")
    private String agencyPostCode;

    // 联系人（代收人）
    @Column(name = "AGENCY_LINK_PERSON")
    private String agencyLinkPerson;

    // 代收人电话(固话)
    @Column(name = "AGENCY_TELEPHONE")
    private String agencyTelephone;

    // 代收人电话(手机)
    @Column(name = "AGENCY_MOBILE_PHONE")
    private String agencyMobilePhone;

    // 其他联系方式（代收人）
    @Column(name = "AGENCY_OTHER_PHONE")
    private String agencyOtherPhone;

    // 当事人签名图片
    @Lob
    @Column(name = "ENTURST_SIGNATURE_SEAL")
    private String enturstSignatureSeal;

    // 当事人签名值
    @Lob
    @Column(name = "ENTURST_SIGNATURE_VALUE")
    private String enturstSignatureValue;

    // 当事人签名位置
    @Column(name = "ENTURST_SIGNATURE_PLACE")
    private String enturstSignaturePlace;

    // 当事人盖章图片
    @Lob
    @Column(name = "ENTURST_STAMP_SEAL")
    private String enturstStampSeal;

    // 当事人盖章值
    @Lob
    @Column(name = "ENTURST_STAMP_VALUE")
    private String enturstStampValue;

    // 当事人盖章位置
    @Column(name = "ENTURST_STAMP_PLACE")
    private String enturstStampPlace;

    // 当事人盖章时间
    @Column(name = "STAMP_DATE")
    private Date stampDate;

    // 备注
    @Column(name = "NOTE_REMARK")
    private String noteRemark;

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

    public String getCauseAction() {
        return causeAction;
    }

    public void setCauseAction(String causeAction) {
        this.causeAction = causeAction;
    }

    public String getPartiesName() {
        return partiesName;
    }

    public void setPartiesName(String partiesName) {
        this.partiesName = partiesName;
    }

    public String getSendAddr() {
        return sendAddr;
    }

    public void setSendAddr(String sendAddr) {
        this.sendAddr = sendAddr;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getLinkPerson() {
        return linkPerson;
    }

    public void setLinkPerson(String linkPerson) {
        this.linkPerson = linkPerson;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getOtherPhone() {
        return otherPhone;
    }

    public void setOtherPhone(String otherPhone) {
        this.otherPhone = otherPhone;
    }

    public String getAgencyPartiesName() {
        return agencyPartiesName;
    }

    public void setAgencyPartiesName(String agencyPartiesName) {
        this.agencyPartiesName = agencyPartiesName;
    }

    public String getAgencySendAddr() {
        return agencySendAddr;
    }

    public void setAgencySendAddr(String agencySendAddr) {
        this.agencySendAddr = agencySendAddr;
    }

    public String getAgencyPostCode() {
        return agencyPostCode;
    }

    public void setAgencyPostCode(String agencyPostCode) {
        this.agencyPostCode = agencyPostCode;
    }

    public String getAgencyLinkPerson() {
        return agencyLinkPerson;
    }

    public void setAgencyLinkPerson(String agencyLinkPerson) {
        this.agencyLinkPerson = agencyLinkPerson;
    }

    public String getAgencyTelephone() {
        return agencyTelephone;
    }

    public void setAgencyTelephone(String agencyTelephone) {
        this.agencyTelephone = agencyTelephone;
    }

    public String getAgencyMobilePhone() {
        return agencyMobilePhone;
    }

    public void setAgencyMobilePhone(String agencyMobilePhone) {
        this.agencyMobilePhone = agencyMobilePhone;
    }

    public String getAgencyOtherPhone() {
        return agencyOtherPhone;
    }

    public void setAgencyOtherPhone(String agencyOtherPhone) {
        this.agencyOtherPhone = agencyOtherPhone;
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

    public Date getStampDate() {
        return stampDate;
    }

    public void setStampDate(Date stampDate) {
        this.stampDate = stampDate;
    }

    public String getNoteRemark() {
        return noteRemark;
    }

    public void setNoteRemark(String noteRemark) {
        this.noteRemark = noteRemark;
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
