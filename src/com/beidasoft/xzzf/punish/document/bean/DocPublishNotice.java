package com.beidasoft.xzzf.punish.document.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 行政处罚听证通知书实体类
 */
@Entity
@Table(name="ZF_DOC_PUBLISH_NOTICE")
public class DocPublishNotice {
    // 行政处罚听证通知书主键ID
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

    // 单位当事人名称
    @Column(name = "ORGAN_NAME")
    private String organName;

    // 行政处罚听证会时间
    @Column(name = "DOC_DATE")
    private Date docDate;

    // 行政处罚听证会地址
    @Column(name = "DOC_ADDRESS")
    private String docAddress;

    // 行政处罚听证会内容
    @Column(name = "DOC_CONTENT")
    private String docContent;

    // 行政处罚听证会主持人
    @Column(name = "COMPERE_NAME")
    private String compereName;

    // 行政处罚听证会记录人
    @Column(name = "RECORDER_NAME")
    private String recorderName;

    // 联系人
    @Column(name = "CONTACT_NAME")
    private String contactName;

    // 联系人联系方式
    @Column(name = "CONTACT_TEL")
    private String contactTel;

    // 行政机关落款印章图片
    @Lob
    @Column(name = "LAW_UNIT_SEAL")
    private String lawUnitSeal;

    // 行政机关落款印章值
    @Lob
    @Column(name = "LAW_UNIT_VALUE")
    private String lawUnitValue;

    // 行政机关落款印章位置
    @Column(name = "LAW_UNIT_PLACE")
    private String lawUnitPlace;

    // 行政机关落款印章时间
    @Column(name = "LAW_UNIT_DATE")
    private Date lawUnitDate;

    // 主办人签名图片
    @Lob
    @Column(name = "MAJOR_SIGNATURE_BASE64")
    private String majorSignatureBase64;

    // 主办人签名值
    @Lob
    @Column(name = "MAJOR_SIGNATURE_VALUE")
    private String majorSignatureValue;

    // 主办人签名位置
    @Column(name = "MAJOR_SIGNATURE_PLACE")
    private String majorSignaturePlace;

    // 协办人签名图片
    @Lob
    @Column(name = "MINOR_SIGNATURE_BASE64")
    private String minorSignatureBase64;

    // 协办人签名值
    @Lob
    @Column(name = "MINOR_SIGNATURE_VALUE")
    private String minorSignatureValue;

    // 协办人签名位置
    @Column(name = "MINOR_SIGNATURE_PLACE")
    private String minorSignaturePlace;

    // 送达时间
    @Column(name = "DELIVER_DATE")
    private Date deliverDate;

    // 受送达人签名或盖章图片
    @Lob
    @Column(name = "RECEIVER_BASE64")
    private String receiverBase64;

    // 受送达人签名或盖章值
    @Lob
    @Column(name = "RECEIVER_VALUE")
    private String receiverValue;

    // 受送达人签名或盖章位置
    @Column(name = "RECEIVER_PLACE")
    private String receiverPlace;

    // 受送达人签名或盖章时间
    @Column(name = "RECEIVER_DATE")
    private Date receiverDate;

    //是否备注
    @Column(name = "IS_REMARKS")
    private String isRemarks;
    
    //备注
    @Column(name = "REMARKS")
    private String remarks;
    
    // 送达方式
    @Column(name = "DELIVER_TYPE")
    private String deliverType;

    // 送达地点
    @Column(name = "DELIVER_ADDRESS")
    private String deliverAddress;

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

	public void setDocNum(String docNum) {
        this.docNum = docNum;
    }

    public String getOrganName () {
        return organName ;
    }

    public void setOrganName (String organName ) {
        this.organName  = organName ;
    }

    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    public String getDocAddress() {
        return docAddress;
    }

    public void setDocAddress(String docAddress) {
        this.docAddress = docAddress;
    }

    public String getDocContent() {
        return docContent;
    }

    public void setDocContent(String docContent) {
        this.docContent = docContent;
    }

    public String getCompereName() {
        return compereName;
    }

    public void setCompereName(String compereName) {
        this.compereName = compereName;
    }

    public String getRecorderName() {
        return recorderName;
    }

    public void setRecorderName(String recorderName) {
        this.recorderName = recorderName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
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

    public Date getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(Date deliverDate) {
        this.deliverDate = deliverDate;
    }

    public String getReceiverBase64() {
        return receiverBase64;
    }

    public void setReceiverBase64(String receiverBase64) {
        this.receiverBase64 = receiverBase64;
    }

    public String getReceiverValue() {
        return receiverValue;
    }

    public void setReceiverValue(String receiverValue) {
        this.receiverValue = receiverValue;
    }

    public String getReceiverPlace() {
        return receiverPlace;
    }

    public void setReceiverPlace(String receiverPlace) {
        this.receiverPlace = receiverPlace;
    }

    public Date getReceiverDate() {
        return receiverDate;
    }

    public void setReceiverDate(Date receiverDate) {
        this.receiverDate = receiverDate;
    }

    public String getDeliverType() {
        return deliverType;
    }

    public void setDeliverType(String deliverType) {
        this.deliverType = deliverType;
    }

    public String getDeliverAddress() {
        return deliverAddress;
    }

    public void setDeliverAddress(String deliverAddress) {
        this.deliverAddress = deliverAddress;
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
