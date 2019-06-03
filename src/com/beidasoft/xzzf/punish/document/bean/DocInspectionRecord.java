package com.beidasoft.xzzf.punish.document.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 现场检查（勘验）笔录实体类
 */
@Entity
@Table(name="ZF_DOC_INSPECTION_RECORD")
public class DocInspectionRecord {
    // 现场检查唯一ID
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

    // 当事人类别
    @Column(name = "PARTY_TYPE")
    private String partyType;

    // 当事人名称
    @Column(name = "PARTY_NAME")
    private String partyName;

    // 单位当事人住所
    @Column(name = "ORGAN_ADDRESS")
    private String organAddress;

    // 单位当事人类型（代码表）
    @Column(name = "ORGAN_TYPE")
    private String organType;

    // 单位负责人姓名
    @Column(name = "ORGAN_LEADING_NAME")
    private String organLeadingName;

    // 单位负责人联系电话
    @Column(name = "ORGAN_LEADING_TEL")
    private String organLeadingTel;

    // 个人字号名称
    @Column(name = "PSN_SHOP_NAME")
    private String psnShopName;

    // 个人当事人性别
    @Column(name = "PSN_SEX")
    private String psnSex;

    // 个人当事人联系电话
    @Column(name = "PSN_TEL")
    private String psnTel;

    // 个人当事人住址
    @Column(name = "PSN_ADDRESS")
    private String psnAddress;

    // 个人当事人证件类型（代码表）
    @Column(name = "PSN_TYPE")
    private String psnType;

    // 个人当事人证件号码
    @Column(name = "PSN_ID_NO")
    private String psnIdNo;

    // 现场负责人
    @Column(name = "ORGAN_SITE_LEADER")
    private String organSiteLeader;

    // 现场负责人职务1
    @Column(name = "ORGAN_SITE_DUTIES")
    private String organSiteDuties;
    
    // 现场负责人职务2
    @Column(name = "ORGAN_SITE_DUTIES2")
    private String organSiteDuties2;
    
    // 检查地点
    @Column(name = "INSPECTION_ADDRESS")
    private String inspectionAddress;

    // 检查时间（开始）
    @Column(name = "INSPECTION_TIME_START")
    private Date inspectionTimeStart;

    // 检查时间（结束）
    @Column(name = "INSPECTION_TIME_END")
    private Date inspectionTimeEnd;

    // 检查情况
    @Column(name = "INSPECTION_CONTENT")
    private String inspectionContent;

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
    
    // 现场负责人意见图片
    @Lob
    @Column(name = "SITE_LEADER_OPINION_BASE64")
    private String siteLeaderOpinionBase64;
    
	// 现场负责人意见值
    @Lob
    @Column(name = "SITE_LEADER_OPINION_VALUE")
    private String siteLeaderOpinionValue;

    // 现场负责人意见
    @Column(name = "SITE_LEADER_OPINION")
    private String siteLeaderOpinion;

    // 现场负责人签名图片
    @Lob
    @Column(name = "SITE_LEADER_BASE64")
    private String siteLeaderBase64;

    // 现场负责人签名值
    @Lob
    @Column(name = "SITE_LEADER_VALUE")
    private String siteLeaderValue;

    // 现场负责人签名位置
    @Column(name = "SITE_LEADER_PLACE")
    private String siteLeaderPlace;

    // 现场负责人签名时间
    @Column(name = "SITE_LEADER_DATE")
    private Date siteLeaderDate;

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
    public String getOrganSiteDuties2() {
		return organSiteDuties2;
	}

	public void setOrganSiteDuties2(String organSiteDuties2) {
		this.organSiteDuties2 = organSiteDuties2;
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

    public String getPartyType() {
        return partyType;
    }

    public void setPartyType(String partyType) {
        this.partyType = partyType;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getOrganAddress() {
        return organAddress;
    }

    public void setOrganAddress(String organAddress) {
        this.organAddress = organAddress;
    }

    public String getOrganType() {
        return organType;
    }

    public void setOrganType(String organType) {
        this.organType = organType;
    }

    public String getOrganLeadingName() {
        return organLeadingName;
    }

    public void setOrganLeadingName(String organLeadingName) {
        this.organLeadingName = organLeadingName;
    }

    public String getOrganLeadingTel() {
        return organLeadingTel;
    }

    public void setOrganLeadingTel(String organLeadingTel) {
        this.organLeadingTel = organLeadingTel;
    }

    public String getPsnShopName() {
        return psnShopName;
    }

    public void setPsnShopName(String psnShopName) {
        this.psnShopName = psnShopName;
    }

    public String getPsnSex() {
        return psnSex;
    }

    public void setPsnSex(String psnSex) {
        this.psnSex = psnSex;
    }

    public String getPsnTel() {
        return psnTel;
    }

    public void setPsnTel(String psnTel) {
        this.psnTel = psnTel;
    }

    public String getPsnAddress() {
        return psnAddress;
    }

    public void setPsnAddress(String psnAddress) {
        this.psnAddress = psnAddress;
    }

    public String getPsnType() {
        return psnType;
    }

    public void setPsnType(String psnType) {
        this.psnType = psnType;
    }

    public String getPsnIdNo() {
        return psnIdNo;
    }

    public void setPsnIdNo(String psnIdNo) {
        this.psnIdNo = psnIdNo;
    }

    public String getOrganSiteLeader() {
        return organSiteLeader;
    }

    public void setOrganSiteLeader(String organSiteLeader) {
        this.organSiteLeader = organSiteLeader;
    }

    public String getOrganSiteDuties() {
        return organSiteDuties;
    }

    public void setOrganSiteDuties(String organSiteDuties) {
        this.organSiteDuties = organSiteDuties;
    }

    public String getInspectionAddress() {
        return inspectionAddress;
    }

    public void setInspectionAddress(String inspectionAddress) {
        this.inspectionAddress = inspectionAddress;
    }

    public Date getInspectionTimeStart() {
        return inspectionTimeStart;
    }

    public void setInspectionTimeStart(Date inspectionTimeStart) {
        this.inspectionTimeStart = inspectionTimeStart;
    }

    public Date getInspectionTimeEnd() {
        return inspectionTimeEnd;
    }

    public void setInspectionTimeEnd(Date inspectionTimeEnd) {
        this.inspectionTimeEnd = inspectionTimeEnd;
    }

    public String getInspectionContent() {
        return inspectionContent;
    }

    public void setInspectionContent(String inspectionContent) {
        this.inspectionContent = inspectionContent;
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

    public String getSiteLeaderOpinionBase64() {
		return siteLeaderOpinionBase64;
	}

	public void setSiteLeaderOpinionBase64(String siteLeaderOpinionBase64) {
		this.siteLeaderOpinionBase64 = siteLeaderOpinionBase64;
	}

	public String getSiteLeaderOpinionValue() {
		return siteLeaderOpinionValue;
	}

	public void setSiteLeaderOpinionValue(String siteLeaderOpinionValue) {
		this.siteLeaderOpinionValue = siteLeaderOpinionValue;
	}

	public String getSiteLeaderOpinion() {
        return siteLeaderOpinion;
    }

    public void setSiteLeaderOpinion(String siteLeaderOpinion) {
        this.siteLeaderOpinion = siteLeaderOpinion;
    }

    public String getSiteLeaderBase64() {
		return siteLeaderBase64;
	}

	public void setSiteLeaderBase64(String siteLeaderBase64) {
		this.siteLeaderBase64 = siteLeaderBase64;
	}

	public String getSiteLeaderValue() {
        return siteLeaderValue;
    }

    public void setSiteLeaderValue(String siteLeaderValue) {
        this.siteLeaderValue = siteLeaderValue;
    }

    public String getSiteLeaderPlace() {
        return siteLeaderPlace;
    }

    public void setSiteLeaderPlace(String siteLeaderPlace) {
        this.siteLeaderPlace = siteLeaderPlace;
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

	public Date getSiteLeaderDate() {
        return siteLeaderDate;
    }

    public void setSiteLeaderDate(Date siteLeaderDate) {
        this.siteLeaderDate = siteLeaderDate;
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
