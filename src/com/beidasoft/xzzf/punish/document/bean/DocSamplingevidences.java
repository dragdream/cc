package com.beidasoft.xzzf.punish.document.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 抽样取证凭证实体类
 */
@Entity
@Table(name="ZF_DOC_SAMPLINGEVIDENCES")
public class DocSamplingevidences {
    // 送达信息确认书唯一ID 
    @Id
    @Column(name = "ID")
    private String id;

    // 抽样取证（区字）
    @Column(name = "SAMPLE_ADDR")
    private String sampleAddr;

    // 抽样取证（年度）
    @Column(name = "SAMPLE_YEAR")
    private String sampleYear;

    // 抽样取证（序列）
    @Column(name = "SAMPLE_SQUENCE")
    private String sampleSquence;

    // 当事人名称（姓名）
    @Column(name = "CLIENT_NAME")
    private String clientName;

    // 你（单位）（立案表中的案由）
    @Column(name = "UNIT_NAME")
    private String unitName;
    
    // 案件行为id
    @Column(name = "CAUSE_ACTION")
    private String causeAction;

    // 案由名
    @Column(name = "CAUSE_ACTION_NAME")
    private String causeActionName;
    
    // 违法行为(法律法规)
    @Column(name = "INITIATE_CAUSE")
    private String initiateCause;

    // 存放地址 （勘验笔录的检查地址）
    @Column(name = "CHECK_ADDR")
    private String checkAddr;

    // 抽样取证方式
    @Column(name = "EXTRACT_WAY")
    private String extractWay;

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

    // 物品清单(区文)
    @Column(name = "ITEM_ADDR")
    private String itemAddr;

    // 物品清单（年度）
    @Column(name = "ITEM_YEAR")
    private String itemYear;

    // 物品清单(序列)
    @Column(name = "ITEM_SQUENCE")
    private String itemSquence;

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

    // 送达人主办人签名图片
    @Lob
    @Column(name = "SENTER_MAJOR_SIGNATURE_BASE64")
    private String senterMajorSignatureBase64;
    
    // 送达人主办人签名值
    @Lob
    @Column(name = "SENTER_MAJOR_SIGNATURE_VALUE")
    private String senterMajorSignatureValue;
    
    // 送达人主办人签名位置
    @Column(name = "SENTER_MAJOR_SIGNATURE_PLACE")
    private String senterMajorSignaturePlace;

    // 送达人协办人签名图片
    @Lob
    @Column(name = "SENTER_MINOR_SIGNATURE_BASE64")
    private String senterMinorSignatureBase64;
    
    // 送达人协办人签名值
    @Lob
    @Column(name = "SENTER_MINOR_SIGNATURE_VALUE")
    private String senterMinorSignatureValue;
    
    // 送达人协办人签名位置
    @Column(name = "SENTER_MINOR_SIGNATURE_PLACE")
    private String senterMinorSignaturePlace;

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

    //是否备注
    @Column(name = "IS_REMARKS")
    private String isRemarks;
    
    //备注
    @Column(name = "REMARKS")
    private String remarks;
    
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
    
    //比例
    @Column(name = "PROPORTION")
    private String proportion;
    
   // 物品清单内容
    @Column(name = "ITEM_CONTAIN")
    private String itemContain;
    
    

	public String getCauseActionName() {
		return causeActionName;
	}

	public void setCauseActionName(String causeActionName) {
		this.causeActionName = causeActionName;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSampleAddr() {
        return sampleAddr;
    }

    public void setSampleAddr(String sampleAddr) {
        this.sampleAddr = sampleAddr;
    }

    public String getSampleYear() {
        return sampleYear;
    }

    public void setSampleYear(String sampleYear) {
        this.sampleYear = sampleYear;
    }

    public String getSampleSquence() {
        return sampleSquence;
    }

    public void setSampleSquence(String sampleSquence) {
        this.sampleSquence = sampleSquence;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getInitiateCause() {
        return initiateCause;
    }

    public void setInitiateCause(String initiateCause) {
        this.initiateCause = initiateCause;
    }

    public String getCheckAddr() {
        return checkAddr;
    }

    public void setCheckAddr(String checkAddr) {
        this.checkAddr = checkAddr;
    }

    public String getExtractWay() {
        return extractWay;
    }

    public void setExtractWay(String extractWay) {
        this.extractWay = extractWay;
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

    public String getProportion() {
		return proportion;
	}

	public void setProportion(String proportion) {
		this.proportion = proportion;
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

    public String getItemAddr() {
        return itemAddr;
    }

    public void setItemAddr(String itemAddr) {
        this.itemAddr = itemAddr;
    }

    public String getItemYear() {
        return itemYear;
    }

    public void setItemYear(String itemYear) {
        this.itemYear = itemYear;
    }

    public String getItemSquence() {
        return itemSquence;
    }

    public void setItemSquence(String itemSquence) {
        this.itemSquence = itemSquence;
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

    public String getItemContain() {
		return itemContain;
	}

	public void setItemContain(String itemContain) {
		this.itemContain = itemContain;
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

	public String getSenterMajorSignatureBase64() {
		return senterMajorSignatureBase64;
	}

	public void setSenterMajorSignatureBase64(String senterMajorSignatureBase64) {
		this.senterMajorSignatureBase64 = senterMajorSignatureBase64;
	}

	public String getSenterMajorSignatureValue() {
		return senterMajorSignatureValue;
	}

	public void setSenterMajorSignatureValue(String senterMajorSignatureValue) {
		this.senterMajorSignatureValue = senterMajorSignatureValue;
	}

	public String getSenterMajorSignaturePlace() {
		return senterMajorSignaturePlace;
	}

	public void setSenterMajorSignaturePlace(String senterMajorSignaturePlace) {
		this.senterMajorSignaturePlace = senterMajorSignaturePlace;
	}

	public String getSenterMinorSignatureBase64() {
		return senterMinorSignatureBase64;
	}

	public void setSenterMinorSignatureBase64(String senterMinorSignatureBase64) {
		this.senterMinorSignatureBase64 = senterMinorSignatureBase64;
	}

	public String getSenterMinorSignatureValue() {
		return senterMinorSignatureValue;
	}

	public void setSenterMinorSignatureValue(String senterMinorSignatureValue) {
		this.senterMinorSignatureValue = senterMinorSignatureValue;
	}

	public String getSenterMinorSignaturePlace() {
		return senterMinorSignaturePlace;
	}

	public void setSenterMinorSignaturePlace(String senterMinorSignaturePlace) {
		this.senterMinorSignaturePlace = senterMinorSignaturePlace;
	}

	public String getCauseAction() {
		return causeAction;
	}

	public void setCauseAction(String causeAction) {
		this.causeAction = causeAction;
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

}
