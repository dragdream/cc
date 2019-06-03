package com.beidasoft.xzzf.punish.document.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 责令改正通知书实体类
 */
@Entity
@Table(name="ZF_DOC_NOTIFICATIONCORRECTION")
public class DocNotificationcorrection {
    // 责令改正主键ID
    @Id
    @Column(name = "ID")
    private String id;

    // 责令文改字
    @Column(name = "DOC_WORLD")
    private String docWorld;

    // 责令 第
    @Column(name = "DOC_NO")
    private String docNo;

    // 责令 号
    @Column(name = "DOC_NUMBER")
    private String docNumber;

    // 当事人姓名
    @Column(name = "PARTY_NAME")
    private String partyName;
    // 案由期间前
    @Column(name = "CASE_TIME")
    private Date caseTime;
    // 案由期间后
    @Column(name = "CASE_TIME_END")
    private Date caseTimeEnd;

    // 案件行为
    @Column(name = "CASE_CAUSE")
    private String caseCause;
    
    // 案件行为id
    @Column(name = "CAUSE_ACTION")
    private String causeAction;

    // 相关规定
    @Column(name = "LAW_CASE")
    private String lawCase;

    // 立即改正上述行为
    @Column(name = "ATONCE")
    private String atonce;

    // 整改日期之前
    @Column(name = "BEFORE")
    private Date before;


    // 整改内容
    @Column(name = "CORRECTION")
    private String correction;

    // 送达主办人签名图片
    @Lob
    @Column(name = "DELIVER_MAJOR_SIGNATURE_BASE64")
    private String deliverMajorSignatureBase64;

    // 送达主办人签名值
    @Lob
    @Column(name = "DELIVER_MAJOR_SIGNATURE_VALUE")
    private String deliverMajorSignatureValue;

    // 送达主办人签名位置
    @Column(name = "DELIVER_MAJOR_SIGNATURE_PLACE")
    private String deliverMajorSignaturePlace;

    // 送达协办人签名图片
    @Lob
    @Column(name = "DELIVER_MINOR_SIGNATURE_BASE64")
    private String deliverMinorSignatureBase64;

    // 送达协办人签名值
    @Lob
    @Column(name = "DELIVER_MINOR_SIGNATURE_VALUE")
    private String deliverMinorSignatureValue;

    // 送达协办人签名位置
    @Column(name = "DELIVER_MINOR_SIGNATURE_PLACE")
    private String deliverMinorSignaturePlace;

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
    
    //是否备注
    @Column(name = "IS_REMARKS")
    private String isRemarks;
    
    //备注
    @Column(name = "REMARKS")
    private String remarks;
    
    // 签收时间
    @Column(name = "SIGN_TIME")
    private Date signTime;

    // 组织机构编号
    @Column(name = "ORGANS_CODE")
    private String organsCode;

    // 组织机构名称
    @Column(name = "ORGANS_NAME")
    private String organsName;
    
    // 送达方式
    @Column(name = "DELIVERY_WAY")
    private String deliveryWay;

    // 送达地点
    @Column(name = "DELIVERY_PLACE")
    private String deliveryPlace;

    // 行政机关落款和印章图片
    @Lob
    @Column(name = "SEAL_SIGNATURE_BASE64")
    private String sealSignatureBase64;

    // 行政机关落款和印章位置
    @Column(name = "SEAL_PALCE")
    private String sealPalce;

    // 行政机关落款和印章时间
    @Column(name = "SEAL_TIME")
    private Date sealTime;

    // 行政机关落款和印章值
    @Lob
    @Column(name = "SEAL_VALUE")
    private String sealValue;

    // 附件地址
    @Column(name = "ENCLOSURE_ADDRESS")
    private String enclosureAddress;

    // 执法办案唯一编号
    @Column(name = "BASE_ID")
    private String baseId;

    // 删除标记
    @Column(name = "DEL_FLG")
    private String delFlg;

    // 执法环节ID
    @Column(name = "LAW_LINK_ID")
    private String lawLinkId;

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

    // 创建人ID
    @Column(name = "CREATE_USER_ID")
    private String createUserId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocWorld() {
        return docWorld;
    }

    public void setDocWorld(String docWorld) {
        this.docWorld = docWorld;
    }

    public Date getCaseTimeEnd() {
		return caseTimeEnd;
	}

	public void setCaseTimeEnd(Date caseTimeEnd) {
		this.caseTimeEnd = caseTimeEnd;
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

	public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public Date getCaseTime() {
        return caseTime;
    }

    public void setCaseTime(Date date) {
        this.caseTime = date;
    }

    public String getCaseCause() {
        return caseCause;
    }

    public void setCaseCause(String caseCause) {
        this.caseCause = caseCause;
    }

    public String getLawCase() {
        return lawCase;
    }

    public void setLawCase(String lawCase) {
        this.lawCase = lawCase;
    }

    public String getAtonce() {
        return atonce;
    }

    public void setAtonce(String atonce) {
        this.atonce = atonce;
    }

    public Date getBefore() {
        return before;
    }

    public void setBefore(Date date) {
        this.before = date;
    }

    public String getCorrection() {
        return correction;
    }

    public void setCorrection(String correction) {
        this.correction = correction;
    }

    public String getDeliverMajorSignatureBase64() {
        return deliverMajorSignatureBase64;
    }

    public void setDeliverMajorSignatureBase64(String deliverMajorSignatureBase64) {
        this.deliverMajorSignatureBase64 = deliverMajorSignatureBase64;
    }

    public String getDeliverMajorSignatureValue() {
        return deliverMajorSignatureValue;
    }

    public void setDeliverMajorSignatureValue(String deliverMajorSignatureValue) {
        this.deliverMajorSignatureValue = deliverMajorSignatureValue;
    }

    public String getDeliverMajorSignaturePlace() {
        return deliverMajorSignaturePlace;
    }

    public void setDeliverMajorSignaturePlace(String deliverMajorSignaturePlace) {
        this.deliverMajorSignaturePlace = deliverMajorSignaturePlace;
    }

    public String getDeliverMinorSignatureBase64() {
        return deliverMinorSignatureBase64;
    }

    public void setDeliverMinorSignatureBase64(String deliverMinorSignatureBase64) {
        this.deliverMinorSignatureBase64 = deliverMinorSignatureBase64;
    }

    public String getDeliverMinorSignatureValue() {
        return deliverMinorSignatureValue;
    }

    public void setDeliverMinorSignatureValue(String deliverMinorSignatureValue) {
        this.deliverMinorSignatureValue = deliverMinorSignatureValue;
    }

    public String getDeliverMinorSignaturePlace() {
        return deliverMinorSignaturePlace;
    }

    public void setDeliverMinorSignaturePlace(String deliverMinorSignaturePlace) {
        this.deliverMinorSignaturePlace = deliverMinorSignaturePlace;
    }

    public Date getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(Date deliverDate) {
        this.deliverDate = deliverDate;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public String getDeliveryWay() {
        return deliveryWay;
    }

    public void setDeliveryWay(String deliveryWay) {
        this.deliveryWay = deliveryWay;
    }

    public String getDeliveryPlace() {
        return deliveryPlace;
    }

    public void setDeliveryPlace(String deliveryPlace) {
        this.deliveryPlace = deliveryPlace;
    }

	public String getSealSignatureBase64() {
		return sealSignatureBase64;
	}

	public void setSealSignatureBase64(String sealSignatureBase64) {
		this.sealSignatureBase64 = sealSignatureBase64;
	}

	public String getSealPalce() {
        return sealPalce;
    }

    public void setSealPalce(String sealPalce) {
        this.sealPalce = sealPalce;
    }

    public Date getSealTime() {
        return sealTime;
    }

    public void setSealTime(Date sealTime) {
        this.sealTime = sealTime;
    }

    public String getSealValue() {
        return sealValue;
    }

    public void setSealValue(String sealValue) {
        this.sealValue = sealValue;
    }

    public String getEnclosureAddress() {
        return enclosureAddress;
    }

    public void setEnclosureAddress(String enclosureAddress) {
        this.enclosureAddress = enclosureAddress;
    }

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    public String getLawLinkId() {
        return lawLinkId;
    }

    public void setLawLinkId(String lawLinkId) {
        this.lawLinkId = lawLinkId;
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

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

	public String getCauseAction() {
		return causeAction;
	}

	public void setCauseAction(String causeAction) {
		this.causeAction = causeAction;
	}

}
