package com.beidasoft.xzzf.punish.document.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 行政处罚事先告知书实体类
 */
@Entity
@Table(name="ZF_DOC_ADMINISTRATIVE_PENALITY")
public class DocAdministrativePenality {
    // 行政处罚事先告知书主键ID
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

    // 当事人
    @Column(name = "PERSON_NAME")
    private String personName;

    // 涉嫌（案由）
    @Column(name = "CAUSE")
    private String cause;

    // 案由名
    @Column(name = "CAUSE_ACTION_NAME")
    private String causeActionName;
    
    // 违法时间
    @Column(name = "CAUSE_TIME")
    private Date causeTime;

    //违法时间后
    @Column(name = "CAUSE_TIME_END")
    private Date causeTimeEnd;
    
    // 违法行为
    @Column(name = "CASE_NAME")
    private String caseName;

    // 涉案法律
    @Column(name = "LAW_CASE")
    private String lawCase;

    // 涉案法律
    @Column(name = "LAW_CAUSE")
    private String lawCause;

    // 警告（复选框）
    @Column(name = "WARN")
    private String warn;

    // 没收违法所得（复选框）
    @Column(name = "CONFISCATE_RECEIVE")
    private String confiscateReceive;

    // 没收非法财物（复选框）
    @Column(name = "CONFISCATE_ILLEGALITY")
    private String confiscateIllegality;

    // 处罚裁量基准（复选框）
    @Column(name = "IS_NOTICE")
    private String isNotice;

    // 罚金（根据裁量基准）
    @Column(name = "PENAL_SUM")
    private String penalSum;

    // 违法经营额（复选框）
    @Column(name = "CONFISCATE_MANAGE")
    private String confiscateManage;

    // 违法所得（复选框）
    @Column(name = "CONFISCATE_RECEIVED")
    private String confiscateReceived;

    // 罚款倍数（起）
    @Column(name = "MULRIPLE_START")
    private String mulripleStart;

    // 罚款倍数（末）
    @Column(name = "MULRIPLE_END")
    private String mulripleEnd;

    // 责令整顿（复选框）
    @Column(name = "IS_STOP_BUSINESS")
    private String isStopBusiness;

    // 吊销许可（复选框）
    @Column(name = "IS_REVOKE_LICENSE")
    private String isRevokeLicense;

    // 较大数额罚款处罚决定（复选框）
    @Column(name = "IS_PENALTY")
    private String isPenalty;

    // 责令整顿处罚决定（复选框）
    @Column(name = "IS_STOP")
    private String isStop;

    // 吊销许可处罚决定（复选框）
    @Column(name = "IS_REVOKE")
    private String isRevoke;

    // 要求陈述申辩（复选框）
    @Column(name = "IS_DEFEND")
    private String isDefend;

    // 要求听证（复选框）
    @Column(name = "IS_LISTEN")
    private String isListen;

    // 当事人签名或盖章图片
    @Lob
    @Column(name = "SITE_LEADER_SIGNATURE")
    private String siteLeaderSignature;

    // 当事人签名或盖章图片
    @Lob
    @Column(name = "SITE_LEADER_SIGNATURE_BASE64")
    private String siteLeaderSignatureBase64;
    
    //是否备注
    @Column(name = "IS_REMARKS")
    private String isRemarks;
    
    //备注
    @Column(name = "REMARKS")
    private String remarks;
    
	// 当事人签名或盖章值
    @Lob
    @Column(name = "SITE_LEADER_VALUE")
    private String siteLeaderValue;

    // 当事人签名或盖章位置
    @Column(name = "SITE_LEADER_PLACE")
    private String siteLeaderPlace;

    // 当事人签名或盖章时间
    @Column(name = "SITE_LEADER_DATE")
    private Date siteLeaderDate;

    // 行政机关落款印章图片
    @Lob
    @Column(name = "LAW_UNIT_SEAL_BASE64")
    private String lawUnitSealBase64;

    // 行政机关落款印章值
    @Lob
    @Column(name = "LAW_UNIT_VALUE")
    private String lawUnitValue;

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

	// 行政机关落款印章位置
    @Column(name = "LAW_UNIT_PLACE")
    private String lawUnitPlace;

    // 行政机关落款印章时间
    @Column(name = "LAW_UNIT_DATE")
    private Date lawUnitDate;

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

    public Date getCauseTimeEnd() {
		return causeTimeEnd;
	}

	public void setCauseTimeEnd(Date causeTimeEnd) {
		this.causeTimeEnd = causeTimeEnd;
	}

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public Date getCauseTime() {
        return causeTime;
    }

    public void setCauseTime(Date causeTime) {
        this.causeTime = causeTime;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getLawCase() {
        return lawCase;
    }

    public void setLawCase(String lawCase) {
        this.lawCase = lawCase;
    }

    public String getLawCause() {
        return lawCause;
    }

    public void setLawCause(String lawCause) {
        this.lawCause = lawCause;
    }

    public String getWarn() {
        return warn;
    }

    public void setWarn(String warn) {
        this.warn = warn;
    }

    public String getConfiscateReceive() {
        return confiscateReceive;
    }

    public void setConfiscateReceive(String confiscateReceive) {
        this.confiscateReceive = confiscateReceive;
    }

    public String getConfiscateIllegality() {
        return confiscateIllegality;
    }

    public void setConfiscateIllegality(String confiscateIllegality) {
        this.confiscateIllegality = confiscateIllegality;
    }

    public String getIsNotice() {
        return isNotice;
    }

    public void setIsNotice(String isNotice) {
        this.isNotice = isNotice;
    }

    public String getPenalSum() {
        return penalSum;
    }

    public void setPenalSum(String penalSum) {
        this.penalSum = penalSum;
    }

    public String getConfiscateManage() {
        return confiscateManage;
    }
    public String getSiteLeaderSignatureBase64() {
		return siteLeaderSignatureBase64;
	}

	public void setSiteLeaderSignatureBase64(String siteLeaderSignatureBase64) {
		this.siteLeaderSignatureBase64 = siteLeaderSignatureBase64;
	}

    public void setConfiscateManage(String confiscateManage) {
        this.confiscateManage = confiscateManage;
    }

    public String getConfiscateReceived() {
        return confiscateReceived;
    }

    public void setConfiscateReceived(String confiscateReceived) {
        this.confiscateReceived = confiscateReceived;
    }

    

    public String getMulripleStart() {
		return mulripleStart;
	}

	public void setMulripleStart(String mulripleStart) {
		this.mulripleStart = mulripleStart;
	}

	public String getMulripleEnd() {
		return mulripleEnd;
	}

	public void setMulripleEnd(String mulripleEnd) {
		this.mulripleEnd = mulripleEnd;
	}

	public String getIsStopBusiness() {
        return isStopBusiness;
    }

    public void setIsStopBusiness(String isStopBusiness) {
        this.isStopBusiness = isStopBusiness;
    }

    public String getIsRevokeLicense() {
        return isRevokeLicense;
    }

    public void setIsRevokeLicense(String isRevokeLicense) {
        this.isRevokeLicense = isRevokeLicense;
    }

    public String getIsPenalty() {
        return isPenalty;
    }

    public void setIsPenalty(String isPenalty) {
        this.isPenalty = isPenalty;
    }

    public String getIsStop() {
        return isStop;
    }

    public void setIsStop(String isStop) {
        this.isStop = isStop;
    }

    public String getIsRevoke() {
        return isRevoke;
    }

    public void setIsRevoke(String isRevoke) {
        this.isRevoke = isRevoke;
    }

    public String getIsDefend() {
        return isDefend;
    }

    public void setIsDefend(String isDefend) {
        this.isDefend = isDefend;
    }

    public String getIsListen() {
        return isListen;
    }

    public void setIsListen(String isListen) {
        this.isListen = isListen;
    }

    public String getSiteLeaderSignature() {
        return siteLeaderSignature;
    }

    public void setSiteLeaderSignature(String siteLeaderSignature) {
        this.siteLeaderSignature = siteLeaderSignature;
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

    public Date getSiteLeaderDate() {
        return siteLeaderDate;
    }

    public void setSiteLeaderDate(Date siteLeaderDate) {
        this.siteLeaderDate = siteLeaderDate;
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

	public String getLawUnitSealBase64() {
		return lawUnitSealBase64;
	}

	public void setLawUnitSealBase64(String lawUnitSealBase64) {
		this.lawUnitSealBase64 = lawUnitSealBase64;
	}

	public String getLawUnitValue() {
        return lawUnitValue;
    }

    public void setLawUnitValue(String lawUnitValue) {
        this.lawUnitValue = lawUnitValue;
    }

    public String getCauseActionName() {
		return causeActionName;
	}

	public void setCauseActionName(String causeActionName) {
		this.causeActionName = causeActionName;
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
