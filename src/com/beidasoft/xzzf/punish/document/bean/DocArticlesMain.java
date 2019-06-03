package com.beidasoft.xzzf.punish.document.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 物品清单主表实体类
 */
@Entity
@Table(name="ZF_DOC_ARTICLES_MAIN")
public class DocArticlesMain {
    // 物品清单主表主键ID
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

    // 合计计量单位
    @Column(name = "GOODS_UNITS")
    private String goodsUnits;

    // 合计数量
    @Column(name = "GOODS_SUMS")
    private int goodsSums;

    // 合计备注
    @Column(name = "GOODS_REMARKS")
    private String goodsRemarks;

    // 当事人现场负责人或受托人意见
    @Column(name = "SITE_LEADER_OPINION")
    private String siteLeaderOpinion;

    // 当事人现场负责人或受托人意见
    @Lob
    @Column(name = "SITE_LEADER_OPINION_BASE64")
    private String siteLeaderOpinionBase64;

    // 当事人现场负责人或受托人意见盖章值
    @Lob
    @Column(name = "SITE_LEADER_OPINION_VALUE")
    private String siteLeaderOpinionValue;

    // 当事人现场负责人或受托人意见盖章位置
    @Column(name = "SITE_LEADER_OPINION_PLACE")
    private String siteLeaderOpinionPlace;
    
    // 当事人签名或盖章图片
    @Lob
    @Column(name = "SITE_LEADER_SIGNATURE_BASE64")
    private String siteLeaderSignatureBase64;

    // 当事人签名或盖章值
    @Lob
    @Column(name = "SITE_LEADER_VALUE")
    private String siteLeaderValue;

    // 当事人签名或盖章位置
    @Column(name = "SITE_LEADER_PLACE")
    private String siteLeaderPlace;

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

    // 当事人签名或盖章时间
    @Column(name = "SITE_LEADER_DATE")
    private Date siteLeaderDate;

    //是否备注
    @Column(name = "IS_REMARKS")
    private String isRemarks;
    
    //备注
    @Column(name = "REMARKS")
    private String remarks;
    
    //被绑定的物品清单
    @Column(name = "BIND")
    private String bind;
    
    //被绑定的查封扣押决定书文号
    @Column(name = "SEALSEIZURE_ID")
    private String sealseizureId;
    
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

    public String getSiteLeaderOpinionValue() {
		return siteLeaderOpinionValue;
	}

	public void setSiteLeaderOpinionValue(String siteLeaderOpinionValue) {
		this.siteLeaderOpinionValue = siteLeaderOpinionValue;
	}

	public String getSiteLeaderOpinionPlace() {
		return siteLeaderOpinionPlace;
	}

	public void setSiteLeaderOpinionPlace(String siteLeaderOpinionPlace) {
		this.siteLeaderOpinionPlace = siteLeaderOpinionPlace;
	}

	public String getSiteLeaderOpinionBase64() {
		return siteLeaderOpinionBase64;
	}

	public void setSiteLeaderOpinionBase64(String siteLeaderOpinionBase64) {
		this.siteLeaderOpinionBase64 = siteLeaderOpinionBase64;
	}

	public void setDocNum(String docNum) {
        this.docNum = docNum;
    }

    public String getGoodsUnits() {
        return goodsUnits;
    }

    public void setGoodsUnits(String goodsUnits) {
        this.goodsUnits = goodsUnits;
    }

    public int getGoodsSums() {
        return goodsSums;
    }

    public void setGoodsSums(int goodsSums) {
        this.goodsSums = goodsSums;
    }

    public String getGoodsRemarks() {
        return goodsRemarks;
    }

    public void setGoodsRemarks(String goodsRemarks) {
        this.goodsRemarks = goodsRemarks;
    }

    public String getSiteLeaderOpinion() {
        return siteLeaderOpinion;
    }

    public void setSiteLeaderOpinion(String siteLeaderOpinion) {
        this.siteLeaderOpinion = siteLeaderOpinion;
    }


    public String getSiteLeaderSignatureBase64() {
		return siteLeaderSignatureBase64;
	}

	public void setSiteLeaderSignatureBase64(String siteLeaderSignatureBase64) {
		this.siteLeaderSignatureBase64 = siteLeaderSignatureBase64;
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

	public Date getSiteLeaderDate() {
		return siteLeaderDate;
	}

	public void setSiteLeaderDate(Date siteLeaderDate) {
		this.siteLeaderDate = siteLeaderDate;
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

	public String getBind() {
		return bind;
	}

	public void setBind(String bind) {
		this.bind = bind;
	}

	public String getSealseizureId() {
		return sealseizureId;
	}

	public void setSealseizureId(String sealseizureId) {
		this.sealseizureId = sealseizureId;
	}

}
