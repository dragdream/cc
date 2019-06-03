package com.beidasoft.xzzf.punish.document.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 行政处罚决定书（当场处罚）实体类
 */
@Entity
@Table(name="ZF_DOC_SITE_SANCTION")
public class DocSiteSanction {
    // 当场处罚唯一ID
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

    // 单位名称
    @Column(name = "ORGAN_NAME")
    private String organName;

    // 单位代码类型（代码表）
    @Column(name = "ORGAN_CODE_TYPE")
    private String organCodeType;

    // 单位代码
    @Column(name = "ORGAN_CODE")
    private String organCode;

    // 单位住所
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

    // 个人名称
    @Column(name = "PEN_NAME")
    private String penName;

    // 个人字号名称
    @Column(name = "PSN_SHOP_NAME")
    private String psnShopName;

    // 个人性别
    @Column(name = "PSN_SEX")
    private String psnSex;

    // 个人联系电话
    @Column(name = "PSN_TEL")
    private String psnTel;

    // 个人住址
    @Column(name = "PSN_ADDRESS")
    private String psnAddress;

    // 个人证件类型（代码表）
    @Column(name = "PSN_TYPE")
    private String psnType;

    // 个人证件号码
    @Column(name = "PSN_ID_NO")
    private String psnIdNo;

    // 违法事实和证据
    @Column(name = "ILLEGAL_FACTS_EVIDENCE")
    private String illegalFactsEvidence;

    // 处罚理由和依据
    @Column(name = "PUNISHMENT_BASIS")
    private String punishmentBasis;

    // 处罚内容
    @Column(name = "PUNISHMENT_CONTENT")
    private String punishmentContent;

    // 人民政府ID
    @Column(name = "GOV_ID")
    private String govId;

    // 人民政府名称
    @Column(name = "GOV_NAME")
    private String govName;

    // 部委ID
    @Column(name = "MINISTRIES_ID")
    private String ministriesId;

    // 部委名称
    @Column(name = "MINISTRIES_NAME")
    private String ministriesName;

    // 市区ID
    @Column(name = "AREA_ID")
    private String areaId;

    // 市区名称
    @Column(name = "AREA_NAME")
    private String areaName;

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

    // 被处罚人签名图片
    @Lob
    @Column(name = "SITE_LEADER_SIGNATURE_BASE64")
    private String siteLeaderSignatureBase64;

    // 被处罚人签名值
    @Lob
    @Column(name = "SITE_LEADER_SIGNATURE_VALUE")
    private String siteLeaderSignatureValue;

    // 被处罚人签名位置
    @Column(name = "SITE_LEADER_SIGNATURE_PLACE")
    private String siteLeaderSignaturePlace;

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

    // 组织编号
    @Column(name = "ORGANS_CODE")
    private String organsCode;

    // 组织名称
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

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public String getOrganCodeType() {
        return organCodeType;
    }

    public void setOrganCodeType(String organCodeType) {
        this.organCodeType = organCodeType;
    }

    public String getOrganCode() {
        return organCode;
    }

    public void setOrganCode(String organCode) {
        this.organCode = organCode;
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

    public String getPenName() {
        return penName;
    }

    public void setPenName(String penName) {
        this.penName = penName;
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

    public String getIllegalFactsEvidence() {
        return illegalFactsEvidence;
    }

    public void setIllegalFactsEvidence(String illegalFactsEvidence) {
        this.illegalFactsEvidence = illegalFactsEvidence;
    }

    public String getPunishmentBasis() {
        return punishmentBasis;
    }

    public void setPunishmentBasis(String punishmentBasis) {
        this.punishmentBasis = punishmentBasis;
    }

    public String getPunishmentContent() {
        return punishmentContent;
    }

    public void setPunishmentContent(String punishmentContent) {
        this.punishmentContent = punishmentContent;
    }

    public String getGovId() {
        return govId;
    }

    public void setGovId(String govId) {
        this.govId = govId;
    }

    public String getGovName() {
        return govName;
    }

    public void setGovName(String govName) {
        this.govName = govName;
    }

    public String getMinistriesId() {
        return ministriesId;
    }

    public void setMinistriesId(String ministriesId) {
        this.ministriesId = ministriesId;
    }

    public String getMinistriesName() {
        return ministriesName;
    }

    public void setMinistriesName(String ministriesName) {
        this.ministriesName = ministriesName;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
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

    
    public String getSiteLeaderSignatureBase64() {
		return siteLeaderSignatureBase64;
	}

	public void setSiteLeaderSignatureBase64(String siteLeaderSignatureBase64) {
		this.siteLeaderSignatureBase64 = siteLeaderSignatureBase64;
	}

	public String getSiteLeaderSignatureValue() {
		return siteLeaderSignatureValue;
	}

	public void setSiteLeaderSignatureValue(String siteLeaderSignatureValue) {
		this.siteLeaderSignatureValue = siteLeaderSignatureValue;
	}

	public String getSiteLeaderSignaturePlace() {
		return siteLeaderSignaturePlace;
	}

	public void setSiteLeaderSignaturePlace(String siteLeaderSignaturePlace) {
		this.siteLeaderSignaturePlace = siteLeaderSignaturePlace;
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
