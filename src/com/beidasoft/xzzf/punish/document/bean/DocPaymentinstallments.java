package com.beidasoft.xzzf.punish.document.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 延期、分期缴纳罚款审批书实体类
 */
@Entity
@Table(name="ZF_DOC_PAYMENTINSTALLMENTS")
public class DocPaymentinstallments {
    // 延期、分期缴纳罚款审批书唯一ID 
    @Id
    @Column(name = "ID")
    private String id;

    // 延期、分期
    @Column(name = "PAY_TYPE")
    private String payType;

    // 缴纳罚款批准书(区文)
    @Column(name = "PAY_ADDR")
    private String payAddr;

    // 缴纳罚款批准书(年份)
    @Column(name = "PAY_YEAR")
    private String payYear;

    // 缴纳罚款批准书(序列)
    @Column(name = "PAY_SQUENCE")
    private String paySquence;

    // 当事人名称（姓名)
    @Column(name = "PART_NAME")
    private String partName;

    // 取处罚决定书送达日期
    @Column(name = "SEND_DATA")
    private Date sendData;

    // 行政处罚决定(区文)
    @Column(name = "PUNISH_ADDR")
    private String punishAddr;

    // 行政处罚决定(年份)
    @Column(name = "PUNISH_YEAR")
    private String punishYear;

    // 行政处罚决定(序列)
    @Column(name = "PUNISH_SQUENCE")
    private String punishSquence;

    // 截止日期
    @Column(name = "LAST_DATA")
    private Date lastData;

    // 罚款
    @Column(name = "SUM_FINE")
    private String sumFine;

    // 缴款方式
    @Column(name = "PAY_WAY")
    private String payWay;

    // 延期截止日期
    @Column(name = "EXTENSION_DATE")
    private Date extensionDate;

    // 分期缴纳罚款第一期
    @Column(name = "FIRST_PHASE")
    private String firstPhase;

    // 分期缴纳罚款第一期结束时间
    @Column(name = "FIRST_PHASE_DATA")
    private Date firstPhaseData;

    // 分期缴纳罚款第一期金额
    @Column(name = "FIRST_PHASE_MONEY")
    private String firstPhaseMoney;

    // 分期缴纳罚款第二期
    @Column(name = "TWO_PHASE")
    private String twoPhase;

    // 分期缴纳罚款第二期结束时间
    @Column(name = "TWO_PHASE_DATA")
    private Date twoPhaseData;

    // 分期缴纳罚款第二期金额
    @Column(name = "TWO_PHASE_MONEY")
    private String twoPhaseMoney;

    // 分期缴纳罚款第三期
    @Column(name = "THREE_PHASE")
    private String threePhase;

    // 分期缴纳罚款第三期结束时间
    @Column(name = "THREE_PHASE_DATA")
    private Date threePhaseData;

    // 分期缴纳罚款第三期金额
    @Column(name = "THREE_PHASE_MONEY")
    private String threePhaseMoney;

    // 行政机关签名图片
    @Lob
    @Column(name = "ORGANIZATION_SIGNATURE_BASE64")
    private String organizationSignatureBase64;

    // 行政机关签名值
    @Lob
    @Column(name = "ORGANIZATION_SIGNATURE_VALUE")
    private String organizationSignatureValue;

    // 行政机关签名位置
    @Column(name = "ORGANIZATION_SIGNATURE_PLACE")
    private String organizationSignaturePlace;

    // 行政机关盖章图片
    @Lob
    @Column(name = "ORGANIZATION_STAMP_BASE64")
    private String organizationStampBase64;

    // 行政机关盖章值
    @Lob
    @Column(name = "ORGANIZATION_STAMP_VALUE")
    private String organizationStampValue;

    // 行政机关盖章位置
    @Column(name = "ORGANIZATION_STAMP_PLACE")
    private String organizationStampPlace;

    // 行政机关落款和印章时间
    @Column(name = "STAMP_DATE")
    private Date stampDate;

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
    @Column(name = "SEND_DATE")
    private Date sendDate;

    // 受送达人签名图片
    @Lob
    @Column(name = "GRANT_SIGNATURE_BASE64")
    private String grantSignatureBase64;

    // 受送达人签名值
    @Lob
    @Column(name = "GRANT_SIGNATURE_VALUE")
    private String grantSignatureValue;

    // 受送达人签名位置
    @Column(name = "GRANT_SIGNATURE_PLACE")
    private String grantSignaturePlace;

    // 受送达人盖章图片
    @Lob
    @Column(name = "GRANT_STAMP_BASE64")
    private String grantStampBase64;

    // 受送达人盖章值
    @Lob
    @Column(name = "GRANT_STAMP_VALUE")
    private String grantStampValue;

    // 受送达人盖章位置
    @Column(name = "GRANT_STAMP_PLACE")
    private String grantStampPlace;

    //是否备注
    @Column(name = "IS_REMARKS")
    private String isRemarks;
    
    //备注
    @Column(name = "REMARKS")
    private String remarks;
    
    // 签收时间
    @Column(name = "RECEIPT_DATE")
    private Date receiptDate;

    // 送达方式
    @Column(name = "GRANTED_WAY")
    private String grantedWay;

    // 送达地点
    @Column(name = "GRANTED_ADDR")
    private String grantedAddr;

    // 组织机构编号
    @Column(name = "ORGANS_CODE")
    private String organsCode;

    // 组织机构名称
    @Column(name = "ORGANS_NAME")
    private String organsName;

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

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayAddr() {
        return payAddr;
    }

    public void setPayAddr(String payAddr) {
        this.payAddr = payAddr;
    }

    public String getPayYear() {
        return payYear;
    }

    public void setPayYear(String payYear) {
        this.payYear = payYear;
    }

    public String getPaySquence() {
        return paySquence;
    }

    public void setPaySquence(String paySquence) {
        this.paySquence = paySquence;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public Date getSendData() {
        return sendData;
    }

    public void setSendData(Date sendData) {
        this.sendData = sendData;
    }

    public String getPunishAddr() {
        return punishAddr;
    }

    public void setPunishAddr(String punishAddr) {
        this.punishAddr = punishAddr;
    }

    public String getPunishYear() {
        return punishYear;
    }

    public void setPunishYear(String punishYear) {
        this.punishYear = punishYear;
    }

    public String getPunishSquence() {
        return punishSquence;
    }

    public void setPunishSquence(String punishSquence) {
        this.punishSquence = punishSquence;
    }

    public Date getLastData() {
        return lastData;
    }

    public void setLastData(Date lastData) {
        this.lastData = lastData;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public Date getExtensionDate() {
        return extensionDate;
    }

    public void setExtensionDate(Date extensionDate) {
        this.extensionDate = extensionDate;
    }

    public Date getFirstPhaseData() {
        return firstPhaseData;
    }

    public void setFirstPhaseData(Date firstPhaseData) {
        this.firstPhaseData = firstPhaseData;
    }

    public Date getTwoPhaseData() {
        return twoPhaseData;
    }

    public void setTwoPhaseData(Date twoPhaseData) {
        this.twoPhaseData = twoPhaseData;
    }

    public Date getThreePhaseData() {
        return threePhaseData;
    }

    public void setThreePhaseData(Date threePhaseData) {
        this.threePhaseData = threePhaseData;
    }

    public String getOrganizationSignatureBase64() {
        return organizationSignatureBase64;
    }

    public void setOrganizationSignatureBase64(String organizationSignatureBase64) {
        this.organizationSignatureBase64 = organizationSignatureBase64;
    }

    public String getOrganizationSignatureValue() {
        return organizationSignatureValue;
    }

    public String getSumFine() {
		return sumFine;
	}

	public void setSumFine(String sumFine) {
		this.sumFine = sumFine;
	}

	public String getFirstPhase() {
		return firstPhase;
	}

	public void setFirstPhase(String firstPhase) {
		this.firstPhase = firstPhase;
	}

	public String getFirstPhaseMoney() {
		return firstPhaseMoney;
	}

	public void setFirstPhaseMoney(String firstPhaseMoney) {
		this.firstPhaseMoney = firstPhaseMoney;
	}

	public String getTwoPhase() {
		return twoPhase;
	}

	public void setTwoPhase(String twoPhase) {
		this.twoPhase = twoPhase;
	}

	public String getTwoPhaseMoney() {
		return twoPhaseMoney;
	}

	public void setTwoPhaseMoney(String twoPhaseMoney) {
		this.twoPhaseMoney = twoPhaseMoney;
	}

	public String getThreePhase() {
		return threePhase;
	}

	public void setThreePhase(String threePhase) {
		this.threePhase = threePhase;
	}

	public String getThreePhaseMoney() {
		return threePhaseMoney;
	}

	public void setThreePhaseMoney(String threePhaseMoney) {
		this.threePhaseMoney = threePhaseMoney;
	}

	public void setOrganizationSignatureValue(String organizationSignatureValue) {
        this.organizationSignatureValue = organizationSignatureValue;
    }

    public String getOrganizationSignaturePlace() {
        return organizationSignaturePlace;
    }

    public void setOrganizationSignaturePlace(String organizationSignaturePlace) {
        this.organizationSignaturePlace = organizationSignaturePlace;
    }

    public String getOrganizationStampBase64() {
        return organizationStampBase64;
    }

    public void setOrganizationStampBase64(String organizationStampBase64) {
        this.organizationStampBase64 = organizationStampBase64;
    }

    public String getOrganizationStampValue() {
        return organizationStampValue;
    }

    public void setOrganizationStampValue(String organizationStampValue) {
        this.organizationStampValue = organizationStampValue;
    }

    public String getOrganizationStampPlace() {
        return organizationStampPlace;
    }

    public void setOrganizationStampPlace(String organizationStampPlace) {
        this.organizationStampPlace = organizationStampPlace;
    }

    public Date getStampDate() {
        return stampDate;
    }

    public void setStampDate(Date stampDate) {
        this.stampDate = stampDate;
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

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getGrantSignatureBase64() {
        return grantSignatureBase64;
    }

    public void setGrantSignatureBase64(String grantSignatureBase64) {
        this.grantSignatureBase64 = grantSignatureBase64;
    }

    public String getGrantSignatureValue() {
        return grantSignatureValue;
    }

    public void setGrantSignatureValue(String grantSignatureValue) {
        this.grantSignatureValue = grantSignatureValue;
    }

    public String getGrantSignaturePlace() {
        return grantSignaturePlace;
    }

    public void setGrantSignaturePlace(String grantSignaturePlace) {
        this.grantSignaturePlace = grantSignaturePlace;
    }

    public String getGrantStampBase64() {
        return grantStampBase64;
    }

    public void setGrantStampBase64(String grantStampBase64) {
        this.grantStampBase64 = grantStampBase64;
    }

    public String getGrantStampValue() {
        return grantStampValue;
    }

    public void setGrantStampValue(String grantStampValue) {
        this.grantStampValue = grantStampValue;
    }

    public String getGrantStampPlace() {
        return grantStampPlace;
    }

    public void setGrantStampPlace(String grantStampPlace) {
        this.grantStampPlace = grantStampPlace;
    }

    public Date getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(Date receiptDate) {
        this.receiptDate = receiptDate;
    }

    public String getGrantedWay() {
        return grantedWay;
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

	public void setGrantedWay(String grantedWay) {
        this.grantedWay = grantedWay;
    }

    public String getGrantedAddr() {
        return grantedAddr;
    }

    public void setGrantedAddr(String grantedAddr) {
        this.grantedAddr = grantedAddr;
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
