package com.beidasoft.xzzf.punish.document.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 证据先行登记保存处理决定书实体类
 */
@Entity
@Table(name="ZF_DOC_EVIDENCE_DECISION")
public class DocEvidenceDecision {
    // 证据决定书唯一ID
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

    // 证据通知书文书区字
    @Column(name = "DOC_AREA_NOTE")
    private String docAreaNote;

    // 证据通知书文书年度
    @Column(name = "DOC_YEAR_NOTE")
    private String docYearNote;

    // 证据通知书文书序号
    @Column(name = "DOC_NUM_NOTE")
    private String docNumNote;

    // 物品清单文书区字
    @Column(name = "DOC_AREA_GOODS")
    private String docAreaGoods;

    // 物品清单文书年度
    @Column(name = "DOC_YEAR_GOODS")
    private String docYearGoods;

    // 物品清单文书序号
    @Column(name = "DOC_NUM_GOODS")
    private String docNumGoods;

    @Column(name = "DOC_AREA_GOODS2")
    private String docAreaGoods2;

    // 物品清单文书年度
    @Column(name = "DOC_YEAR_GOODS2")
    private String docYearGoods2;

    // 物品清单文书序号
    @Column(name = "DOC_NUM_GOODS2")
    private String docNumGoods2;
    // 物品处理范围（代码表）
    @Column(name = "GOODS_PROCESS_RANGE")
    private String goodsProcessRange;

    // 处理决定类别
    @Column(name = "PROCESS_DECISION_TYPE")
    private String processDecisionType;

    // 鉴定时间（开始）
    @Column(name = "CHECKUP_DATE_START")
    private Date checkupDateStart;

    // 鉴定时间（结束）
    @Column(name = "CHECKUP_DATE_END")
    private Date checkupDateEnd;

    // 移送机构名称
    @Column(name = "TRANSFER_ORGAN_NAME")
    private String transferOrganName;

    // 其他处理
    @Column(name = "OTHER_PROCESS")
    private String otherProcess;

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

    // 主办人执法证号
    @Column(name = "MAJOR_CODE")
    private String majorCode;

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

    // 协办人执法证号
    @Column(name = "MINOR_CODE")
    private String minorCode;

    // 执法单位ID（代码表）
    @Column(name = "LAW_UNIT_ID")
    private String lawUnitId;

    // 执法单位名称
    @Column(name = "LAW_UNIT_NAME")
    private String lawUnitName;
    
    // 组织机构编号
    @Column(name = "ORGANS_CODE")
    private String organsCode;

    // 组织机构名称
    @Column(name = "ORGANS_NAME")
    private String organsName;
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

    // 送达人（主）签名图片
    @Lob
    @Column(name = "SENDER_MJR_SIGNATURE_BASE64")
    private String senderMjrSignatureBase64;

    // 送达人（主）签名值
    @Lob
    @Column(name = "SENDER_MJR_SIGNATURE_VALUE")
    private String senderMjrSignatureValue;

    // 送达人（主）签名位置
    @Column(name = "SENDER_MJR_SIGNATURE_PLACE")
    private String senderMjrSignaturePlace;

    // 送达人（协）签名图片
    @Lob
    @Column(name = "SENDER_MNR_SIGNATURE_BASE64")
    private String senderMnrSignatureBase64;

    // 送达人（协）签名值
    @Lob
    @Column(name = "SENDER_MNR_SIGNATURE_VALUE")
    private String senderMnrSignatureValue;

    // 送达人（协）签名位置
    @Column(name = "SENDER_MNR_SIGNATURE_PLACE")
    private String senderMnrSignaturePlace;

    // 送达时间
    @Column(name = "SEND_TIME")
    private Date sendTime;

    // 受送达人签名图片
    @Lob
    @Column(name = "RECEIVER_SIGNATURE_BASE64")
    private String receiverSignatureBase64;

    // 受送达人签名值
    @Lob
    @Column(name = "RECEIVER_SIGNATURE_VALUE")
    private String receiverSignatureValue;

    // 受送达人签名位置
    @Column(name = "RECEIVER_SIGNATURE_PLACE")
    private String receiverSignaturePlace;

    // 受送达人签名时间
    @Column(name = "RECEIVER_SIGNATURE_DATE")
    private Date receiverSignatureDate;

    // 送达方式（代码表）
    @Column(name = "SEND_TYPE")
    private String sendType;

    // 送达地点
    @Column(name = "SEND_ADDRESS")
    private String sendAddress;
    
    //是否备注
    @Column(name = "IS_REMARKS")
    private String isRemarks;
    
    //备注
    @Column(name = "REMARKS")
    private String remarks;
    
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

    // 物品清单(内容)
    @Column(name = "ITEM_CONTAIN")
    private String itemContain;
    
    // 物品清单(内容1)
    @Column(name = "ITEM_CONTAIN_ONE")
    private String itemContainOne;
    
    // 物品清单(内容2)
    @Column(name = "ITEM_CONTAIN_TWO")
    private String itemContainTwo;
    
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

	public String getDocAreaGoods2() {
		return docAreaGoods2;
	}

	public void setDocAreaGoods2(String docAreaGoods2) {
		this.docAreaGoods2 = docAreaGoods2;
	}

	public String getDocYearGoods2() {
		return docYearGoods2;
	}

	public void setDocYearGoods2(String docYearGoods2) {
		this.docYearGoods2 = docYearGoods2;
	}

	public String getDocNumGoods2() {
		return docNumGoods2;
	}

	public void setDocNumGoods2(String docNumGoods2) {
		this.docNumGoods2 = docNumGoods2;
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

    public String getDocAreaNote() {
        return docAreaNote;
    }

    public void setDocAreaNote(String docAreaNote) {
        this.docAreaNote = docAreaNote;
    }

    public String getDocYearNote() {
        return docYearNote;
    }

    public void setDocYearNote(String docYearNote) {
        this.docYearNote = docYearNote;
    }

    public String getDocNumNote() {
        return docNumNote;
    }

    public void setDocNumNote(String docNumNote) {
        this.docNumNote = docNumNote;
    }

    public String getDocAreaGoods() {
        return docAreaGoods;
    }

    public void setDocAreaGoods(String docAreaGoods) {
        this.docAreaGoods = docAreaGoods;
    }

    public String getDocYearGoods() {
        return docYearGoods;
    }

    public void setDocYearGoods(String docYearGoods) {
        this.docYearGoods = docYearGoods;
    }

    public String getDocNumGoods() {
        return docNumGoods;
    }

    public void setDocNumGoods(String docNumGoods) {
        this.docNumGoods = docNumGoods;
    }

    public String getGoodsProcessRange() {
        return goodsProcessRange;
    }

    public void setGoodsProcessRange(String goodsProcessRange) {
        this.goodsProcessRange = goodsProcessRange;
    }

    public String getProcessDecisionType() {
        return processDecisionType;
    }

    public void setProcessDecisionType(String processDecisionType) {
        this.processDecisionType = processDecisionType;
    }

    public Date getCheckupDateStart() {
        return checkupDateStart;
    }

    public void setCheckupDateStart(Date checkupDateStart) {
        this.checkupDateStart = checkupDateStart;
    }

    public Date getCheckupDateEnd() {
        return checkupDateEnd;
    }

    public void setCheckupDateEnd(Date checkupDateEnd) {
        this.checkupDateEnd = checkupDateEnd;
    }

    public String getTransferOrganName() {
        return transferOrganName;
    }

    public void setTransferOrganName(String transferOrganName) {
        this.transferOrganName = transferOrganName;
    }

    public String getOtherProcess() {
        return otherProcess;
    }
    
    public void setOtherProcess(String otherProcess) {
        this.otherProcess = otherProcess;
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

    public String getMajorCode() {
        return majorCode;
    }

    public void setMajorCode(String majorCode) {
        this.majorCode = majorCode;
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

    public String getMinorCode() {
        return minorCode;
    }

    public void setMinorCode(String minorCode) {
        this.minorCode = minorCode;
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

    public String getSenderMjrSignatureBase64() {
        return senderMjrSignatureBase64;
    }

    public void setSenderMjrSignatureBase64(String senderMjrSignatureBase64) {
        this.senderMjrSignatureBase64 = senderMjrSignatureBase64;
    }

    public String getSenderMjrSignatureValue() {
        return senderMjrSignatureValue;
    }

    public void setSenderMjrSignatureValue(String senderMjrSignatureValue) {
        this.senderMjrSignatureValue = senderMjrSignatureValue;
    }

    public String getSenderMjrSignaturePlace() {
        return senderMjrSignaturePlace;
    }

    public void setSenderMjrSignaturePlace(String senderMjrSignaturePlace) {
        this.senderMjrSignaturePlace = senderMjrSignaturePlace;
    }

    public String getSenderMnrSignatureBase64() {
        return senderMnrSignatureBase64;
    }

    public void setSenderMnrSignatureBase64(String senderMnrSignatureBase64) {
        this.senderMnrSignatureBase64 = senderMnrSignatureBase64;
    }

    public String getSenderMnrSignatureValue() {
        return senderMnrSignatureValue;
    }

    public void setSenderMnrSignatureValue(String senderMnrSignatureValue) {
        this.senderMnrSignatureValue = senderMnrSignatureValue;
    }

    public String getSenderMnrSignaturePlace() {
        return senderMnrSignaturePlace;
    }

    public void setSenderMnrSignaturePlace(String senderMnrSignaturePlace) {
        this.senderMnrSignaturePlace = senderMnrSignaturePlace;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getReceiverSignatureBase64() {
        return receiverSignatureBase64;
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

	public void setReceiverSignatureBase64(String receiverSignatureBase64) {
        this.receiverSignatureBase64 = receiverSignatureBase64;
    }

    public String getReceiverSignatureValue() {
        return receiverSignatureValue;
    }

    public void setReceiverSignatureValue(String receiverSignatureValue) {
        this.receiverSignatureValue = receiverSignatureValue;
    }

    public String getReceiverSignaturePlace() {
        return receiverSignaturePlace;
    }

    public void setReceiverSignaturePlace(String receiverSignaturePlace) {
        this.receiverSignaturePlace = receiverSignaturePlace;
    }

    public Date getReceiverSignatureDate() {
        return receiverSignatureDate;
    }

    public void setReceiverSignatureDate(Date receiverSignatureDate) {
        this.receiverSignatureDate = receiverSignatureDate;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public String getItemContain() {
		return itemContain;
	}

	public void setItemContain(String itemContain) {
		this.itemContain = itemContain;
	}

	public String getItemContainOne() {
		return itemContainOne;
	}

	public void setItemContainOne(String itemContainOne) {
		this.itemContainOne = itemContainOne;
	}

	public String getItemContainTwo() {
		return itemContainTwo;
	}

	public void setItemContainTwo(String itemContainTwo) {
		this.itemContainTwo = itemContainTwo;
	}

	public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
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
