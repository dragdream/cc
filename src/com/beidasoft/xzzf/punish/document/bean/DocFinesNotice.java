package com.beidasoft.xzzf.punish.document.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 
行政处罚履行催告书实体类
 */
@Entity
@Table(name="ZF_DOC_FINES_NOTICE")
public class DocFinesNotice {
    // 催告书唯一ID
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

    // 执法机关名称
    @Column(name = "AGENCY_NAME")
    private String agencyName;

    // 处罚决定书选择状态
    @Column(name = "FINES_CHK_STATE")
    private String finesChkState;

    // 处罚决定书送达日期
    @Column(name = "PENALTY_DECISOIN_SENT_DATE")
    private Date penaltyDecisoinSentDate;

    // 处罚决定书文书区字
    @Column(name = "DOC_AREA_FINES")
    private String docAreaFines;

    // 处罚决定书文书年度
    @Column(name = "DOC_YEAR_FINES")
    private String docYearFines;

    // 处罚决定书文书序号
    @Column(name = "DOC_NUM_FINES")
    private String docNumFines;

    // 加处罚决定书选择状态
    @Column(name = "ADD_FINES_CHK_STATE")
    private String addFinesChkState;

    // 加处罚决定书送达日期
    @Column(name = "ADD_PENALTY_DECISOIN_SENT_DATE")
    private Date addPenaltyDecisoinSentDate;

    // 加处罚决定书文书区字
    @Column(name = "DOC_AREA_ADDFINES")
    private String docAreaAddfines;

    // 加处罚决定书文书年度
    @Column(name = "DOC_YEAR_ADDFINES")
    private String docYearAddfines;

    // 加处罚决定书文书序号
    @Column(name = "DOC_NUM_ADDFINES")
    private String docNumAddfines;

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

    //是否备注
    @Column(name = "IS_REMARKS")
    private String isRemarks;
    
    //备注
    @Column(name = "REMARKS")
    private String remarks;
    
    // 受送达人签名时间
    @Column(name = "RECEIVER_SIGNATURE_DATE")
    private Date receiverSignatureDate;

    // 送达方式（代码表）
    @Column(name = "SEND_TYPE")
    private String sendType;

    // 送达地点
    @Column(name = "SEND_ADDRESS")
    private String sendAddress;

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

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getFinesChkState() {
        return finesChkState;
    }

    public void setFinesChkState(String finesChkState) {
        this.finesChkState = finesChkState;
    }

    public Date getPenaltyDecisoinSentDate() {
        return penaltyDecisoinSentDate;
    }

    public void setPenaltyDecisoinSentDate(Date penaltyDecisoinSentDate) {
        this.penaltyDecisoinSentDate = penaltyDecisoinSentDate;
    }

    public String getDocAreaFines() {
        return docAreaFines;
    }

    public void setDocAreaFines(String docAreaFines) {
        this.docAreaFines = docAreaFines;
    }

    public String getDocYearFines() {
        return docYearFines;
    }

    public void setDocYearFines(String docYearFines) {
        this.docYearFines = docYearFines;
    }

    public String getDocNumFines() {
        return docNumFines;
    }

    public void setDocNumFines(String docNumFines) {
        this.docNumFines = docNumFines;
    }

    public String getAddFinesChkState() {
        return addFinesChkState;
    }

    public void setAddFinesChkState(String addFinesChkState) {
        this.addFinesChkState = addFinesChkState;
    }

    public Date getAddPenaltyDecisoinSentDate() {
		return addPenaltyDecisoinSentDate;
	}

	public void setAddPenaltyDecisoinSentDate(Date addPenaltyDecisoinSentDate) {
		this.addPenaltyDecisoinSentDate = addPenaltyDecisoinSentDate;
	}

	public String getDocAreaAddfines() {
        return docAreaAddfines;
    }

    public void setDocAreaAddfines(String docAreaAddfines) {
        this.docAreaAddfines = docAreaAddfines;
    }

    public String getDocYearAddfines() {
        return docYearAddfines;
    }

    public void setDocYearAddfines(String docYearAddfines) {
        this.docYearAddfines = docYearAddfines;
    }

    public String getDocNumAddfines() {
        return docNumAddfines;
    }

    public void setDocNumAddfines(String docNumAddfines) {
        this.docNumAddfines = docNumAddfines;
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
