package com.beidasoft.xzzf.punish.document.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 行政处罚决定书实体类
 */
@Entity
@Table(name="ZF_DOC_SECURITY_ADMIN")
public class DocSecurityAdmin {
    // 行政处罚决定书主键ID
    @Id
    @Column(name = "P_ID")
    private String pId;

    // 文书区字
    @Column(name = "P_DOC_AREA")
    private String pDocArea;

    // 文书年度
    @Column(name = "P_DOC_YEAR")
    private String pDocYear;

    // 文书序号
    @Column(name = "P_DOC_NUM")
    private String pDocNum;

    // 当事人
    @Column(name = "P_PERSON")
    private String pPerson;

    // 号码类型(复选框单选)
    @Column(name = "P_NUMBER_TYPE")
    private String pNumberType;

    // 号码内容
    @Column(name = "P_NUMBER_CONTENT")
    private String pNumberContent;

    // 单位当事人类型（代码表）
    @Column(name = "P_ORGAN_TYPE")
    private String pOrganType;

    // 单位当事人名称
    @Column(name = "P_ORGAN_NAME")
    private String pOrganName;

    // 住所（住址）
    @Column(name = "P_ORGAN_ADDRESS")
    private String pOrganAddress;

    // 案件来源
    @Column(name = "P_CASE_SOURCE")
    private String pCaseSource;

    // 案件时间
    @Column(name = "P_CASE_DATE")
    private Date pCaseDate;

    // 执法单位ID（代码表）
    @Column(name = "P_LAW_UNIT_ID")
    private String pLawUnitId;

    // 检查地址
    @Column(name = "P_CHECK_ADDR")
    private String pCheckAddr;

    // 案由
    @Column(name = "P_CAUSE_ACTION")
    private String pCauseAction;

    // 违法时间
    @Column(name = "P_CASE_TIME")
    private Date pCaseTime;

    // 现场检查内容
    @Column(name = "P_CHECK_CONTENT")
    private String pCheckContent;

    // 证据内容
    @Column(name = "P_EVIDENCE_CONTENT")
    private String pEvidenceContent;

    // 案由法规
    @Column(name = "P_CAUSE_ACTION_REGULATION")
    private String pCauseActionRegulation;

    // 违法行为
    @Column(name = "P_CAUSE_BEHAVIOR")
    private String pCauseBehavior;

    // 行政处罚事先告知书送达时间
    @Column(name = "P_SEND_DATE_PENALITY")
    private Date pSendDatePenality;

    // 行政处罚事先告知书文书区字
    @Column(name = "P_DOC_AREA_PENALITY")
    private String pDocAreaPenality;

    // 行政处罚事先告知书文书年度
    @Column(name = "P_DOC_YEAR_PENALITY")
    private String pDocYearPenality;

    // 行政处罚事先告知书文书序号
    @Column(name = "P_DOC_NUM_PENALITY")
    private String pDocNumPenality;

    // 案由处罚条款
    @Column(name = "P_CAUSE_ACTION_PUBLISH")
    private String pCauseActionPublish;

    // 组织机构编号
    @Column(name = "P_ORGANS_CODE")
    private String pOrgansCode;

    // 组织机构名称
    @Column(name = "P_ORGANS_NAME")
    private String pOrgansName;

    // 附件地址
    @Column(name = "P_ENCLOSURE_ADDRESS")
    private String pEnclosureAddress;

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
    

    public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getpDocArea() {
		return pDocArea;
	}

	public void setpDocArea(String pDocArea) {
		this.pDocArea = pDocArea;
	}

	public String getpDocYear() {
		return pDocYear;
	}

	public void setpDocYear(String pDocYear) {
		this.pDocYear = pDocYear;
	}

	public String getpDocNum() {
		return pDocNum;
	}

	public void setpDocNum(String pDocNum) {
		this.pDocNum = pDocNum;
	}

	public String getpPerson() {
		return pPerson;
	}

	public void setpPerson(String pPerson) {
		this.pPerson = pPerson;
	}

	public String getpNumberType() {
		return pNumberType;
	}

	public void setpNumberType(String pNumberType) {
		this.pNumberType = pNumberType;
	}

	public String getpNumberContent() {
		return pNumberContent;
	}

	public void setpNumberContent(String pNumberContent) {
		this.pNumberContent = pNumberContent;
	}

	public String getpOrganType() {
		return pOrganType;
	}

	public void setpOrganType(String pOrganType) {
		this.pOrganType = pOrganType;
	}

	public String getpOrganName() {
		return pOrganName;
	}

	public void setpOrganName(String pOrganName) {
		this.pOrganName = pOrganName;
	}

	public String getpOrganAddress() {
		return pOrganAddress;
	}

	public void setpOrganAddress(String pOrganAddress) {
		this.pOrganAddress = pOrganAddress;
	}

	public String getpCaseSource() {
		return pCaseSource;
	}

	public void setpCaseSource(String pCaseSource) {
		this.pCaseSource = pCaseSource;
	}

	public Date getpCaseDate() {
		return pCaseDate;
	}

	public void setpCaseDate(Date pCaseDate) {
		this.pCaseDate = pCaseDate;
	}

	public String getpLawUnitId() {
		return pLawUnitId;
	}

	public void setpLawUnitId(String pLawUnitId) {
		this.pLawUnitId = pLawUnitId;
	}

	public String getpCheckAddr() {
		return pCheckAddr;
	}

	public void setpCheckAddr(String pCheckAddr) {
		this.pCheckAddr = pCheckAddr;
	}

	public String getpCauseAction() {
		return pCauseAction;
	}

	public void setpCauseAction(String pCauseAction) {
		this.pCauseAction = pCauseAction;
	}

	public Date getpCaseTime() {
		return pCaseTime;
	}

	public void setpCaseTime(Date pCaseTime) {
		this.pCaseTime = pCaseTime;
	}

	public String getpCheckContent() {
		return pCheckContent;
	}

	public void setpCheckContent(String pCheckContent) {
		this.pCheckContent = pCheckContent;
	}

	public String getpEvidenceContent() {
		return pEvidenceContent;
	}

	public void setpEvidenceContent(String pEvidenceContent) {
		this.pEvidenceContent = pEvidenceContent;
	}

	public String getpCauseActionRegulation() {
		return pCauseActionRegulation;
	}

	public void setpCauseActionRegulation(String pCauseActionRegulation) {
		this.pCauseActionRegulation = pCauseActionRegulation;
	}

	public String getpCauseBehavior() {
		return pCauseBehavior;
	}

	public void setpCauseBehavior(String pCauseBehavior) {
		this.pCauseBehavior = pCauseBehavior;
	}

	public Date getpSendDatePenality() {
		return pSendDatePenality;
	}

	public void setpSendDatePenality(Date pSendDatePenality) {
		this.pSendDatePenality = pSendDatePenality;
	}

	public String getpDocAreaPenality() {
		return pDocAreaPenality;
	}

	public void setpDocAreaPenality(String pDocAreaPenality) {
		this.pDocAreaPenality = pDocAreaPenality;
	}

	public String getpDocYearPenality() {
		return pDocYearPenality;
	}

	public void setpDocYearPenality(String pDocYearPenality) {
		this.pDocYearPenality = pDocYearPenality;
	}

	public String getpDocNumPenality() {
		return pDocNumPenality;
	}

	public void setpDocNumPenality(String pDocNumPenality) {
		this.pDocNumPenality = pDocNumPenality;
	}

	public String getpCauseActionPublish() {
		return pCauseActionPublish;
	}

	public void setpCauseActionPublish(String pCauseActionPublish) {
		this.pCauseActionPublish = pCauseActionPublish;
	}

	public String getpOrgansCode() {
		return pOrgansCode;
	}

	public void setpOrgansCode(String pOrgansCode) {
		this.pOrgansCode = pOrgansCode;
	}

	public String getpOrgansName() {
		return pOrgansName;
	}

	public void setpOrgansName(String pOrgansName) {
		this.pOrgansName = pOrgansName;
	}

	public String getpEnclosureAddress() {
		return pEnclosureAddress;
	}

	public void setpEnclosureAddress(String pEnclosureAddress) {
		this.pEnclosureAddress = pEnclosureAddress;
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

}
