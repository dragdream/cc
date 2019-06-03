package com.beidasoft.xzzf.punish.document.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 案件移送单实体类
 */
@Entity
@Table(name="ZF_DOC_CASEMOVELIST")
public class DocCasemovelist {
    // 案件移送单id
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

    // 名字
    @Column(name = "PARTY_NAME")
    private String partyName;

    // 当事人类型
    @Column(name = "PARTY_TYPE")
    private String partyType;

    //负责人名称
    @Column(name = "PARTY_TYPE_NAME")
    private String partyTypeName;
    
    // 当事人联系电话
    @Column(name = "PARTY_PHONE")
    private String partyPhone;

    // 当事人住址
    @Column(name = "PARTY_ADDRESS")
    private String partyAddress;

    // 案由
    @Column(name = "CAUSE_ACTION")
    private String causeAction;
    
    // 案由名
    @Column(name = "CAUSE_ACTION_NAME")
    private String causeActionName;

    // 案件来源
    @Column(name = "CASE_SOURCE")
    private String caseSource;

    // 案情概要及移送理由
    @Column(name = "CASE_OVERVIEW")
    private String caseOverview;

    // 移送清单文书区字
    @Column(name = "MOVE_DOC_AREA")
    private String moveDocArea;

    // 移送清单文书年度
    @Column(name = "MOVE_DOC_YEAR")
    private String moveDocYear;

    // 移送清单文书序号
    @Column(name = "MOVE_DOC_NUM")
    private String moveDocNum;

    // 移送单位意见
    @Column(name = "MOVE_ADVICE")
    private String moveAdvice;

    // 移送人签名图片
    @Lob
    @Column(name = "MOVE_MAJOR_SIGNATURE_BASE64")
    private String moveMajorSignatureBase64;

    // 移送人签名值
    @Lob
    @Column(name = "MOVE_MAJOR_SIGNATURE_VALUE")
    private String moveMajorSignatureValue;

    // 移送人签名位置
    @Column(name = "MOVE_MAJOR_SIGNATURE_PLACE")
    private String moveMajorSignaturePlace;
    
    // 移送人2签名图片
    @Lob
    @Column(name = "MOVE_MINOR_SIGNATURE_BASE64")
    private String moveMinorSignatureBase64;

    // 移送人2签名值
    @Lob
    @Column(name = "MOVE_MINOR_SIGNATURE_VALUE")
    private String moveMinorSignatureValue;

    // 移送人2签名位置
    @Column(name = "MOVE_MINOR_SIGNATURE_PLACE")
    private String moveMinorSignaturePlace;

    // 移送人联系电话
    @Column(name = "MOVE_PHONE")
    private String movePhone;

    // 移送单位印章图片
    @Lob
    @Column(name = "MOVE_LAW_UNIT_SEAL")
    private String moveLawUnitSeal;

    // 移送单位印章值
    @Lob
    @Column(name = "MOVE_LAW_UNIT_VALUE")
    private String moveLawUnitValue;

    // 移送单位印章位置
    @Column(name = "MOVE_LAW_UNIT_PLACE")
    private String moveLawUnitPlace;

    // 移送单位印章时间
    @Column(name = "MOVE_LAW_UNIT_DATE")
    private Date moveLawUnitDate;

    // 接收单位意见
    @Column(name = "RECIVE_ADVICE")
    private String reciveAdvice;

    // 接收人签名图片
    @Lob
    @Column(name = "RECIVE_MAJOR_SIGNATURE_BASE64")
    private String reciveMajorSignatureBase64;

    // 接收人签名值
    @Lob
    @Column(name = "RECIVE_MAJOR_SIGNATURE_VALUE")
    private String reciveMajorSignatureValue;

    // 接收人签名位置
    @Column(name = "RECIVE_MAJOR_SIGNATURE_PLACE")
    private String reciveMajorSignaturePlace;

    // 接收人联系电话
    @Column(name = "RECIVE_PHONE")
    private String recivePhone;

    // 接收单位印章图片
    @Lob
    @Column(name = "RECIVE_LAW_UNIT_SEAL")
    private String reciveLawUnitSeal;

    // 接收单位印章值
    @Lob
    @Column(name = "RECIVE_LAW_UNIT_VALUE")
    private String reciveLawUnitValue;

    // 接收单位印章位置
    @Column(name = "RECIVE_LAW_UNIT_PLACE")
    private String reciveLawUnitPlace;

    // 接收单位印章时间
    @Column(name = "RECIVE_LAW_UNIT_DATE")
    private Date reciveLawUnitDate;

    // 移送组织机构编号
    @Column(name = "ORGANS_CODE")
    private String organsCode;

    // 移送组织机构名称
    @Column(name = "ORGANS_NAME")
    private String organsName;

    // 接收组织机构编号
    @Column(name = "ORGANS_CODE_TO")
    private String organsCodeTo;

    // 接收组织机构名称
    @Column(name = "ORGANS_NAME_TO")
    private String organsNameTo;

    // 附件地址
    @Column(name = "ENCLOSURE_ADDRESS")
    private String enclosureAddress;

    // 删除标记
    @Column(name = "DEL_FLG")
    private String delFlg;

    // 执法环节id
    @Column(name = "LAW_LINK_ID")
    private String lawLinkId;

    // 创建人id
    @Column(name = "CREATE_USER_ID")
    private String createUserId;

    // 创建人姓名
    @Column(name = "CREATE_USER_NAME")
    private String createUserName;

    // 创建时间
    @Column(name = "CREATE_TIME")
    private Date createTime;

    // 变更人id
    @Column(name = "UPDATE_USER_ID")
    private String updateUserId;

    // 变更人姓名
    @Column(name = "UPDATE_USER_NAME")
    private String updateUserName;

    // 变更时间
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    // 执法办案唯一id
    @Column(name = "BASE_ID")
    private String baseId;

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

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getPartyType() {
        return partyType;
    }

    public void setPartyType(String partyType) {
        this.partyType = partyType;
    }

    public String getPartyPhone() {
        return partyPhone;
    }

    public void setPartyPhone(String partyPhone) {
        this.partyPhone = partyPhone;
    }

    public String getPartyAddress() {
        return partyAddress;
    }

    public void setPartyAddress(String partyAddress) {
        this.partyAddress = partyAddress;
    }

    public String getCauseAction() {
        return causeAction;
    }

    public void setCauseAction(String causeAction) {
        this.causeAction = causeAction;
    }

    public String getCaseSource() {
        return caseSource;
    }

    public void setCaseSource(String caseSource) {
        this.caseSource = caseSource;
    }

    public String getCaseOverview() {
        return caseOverview;
    }

    public void setCaseOverview(String caseOverview) {
        this.caseOverview = caseOverview;
    }

    public String getMoveDocArea() {
        return moveDocArea;
    }

    public void setMoveDocArea(String moveDocArea) {
        this.moveDocArea = moveDocArea;
    }

    public String getMoveDocYear() {
        return moveDocYear;
    }

    public void setMoveDocYear(String moveDocYear) {
        this.moveDocYear = moveDocYear;
    }

    public String getMoveDocNum() {
        return moveDocNum;
    }

    public void setMoveDocNum(String moveDocNum) {
        this.moveDocNum = moveDocNum;
    }

    public String getMoveAdvice() {
        return moveAdvice;
    }

    public void setMoveAdvice(String moveAdvice) {
        this.moveAdvice = moveAdvice;
    }

    public String getMoveMajorSignatureBase64() {
        return moveMajorSignatureBase64;
    }

    public void setMoveMajorSignatureBase64(String moveMajorSignatureBase64) {
        this.moveMajorSignatureBase64 = moveMajorSignatureBase64;
    }

    public String getMoveMajorSignatureValue() {
        return moveMajorSignatureValue;
    }

    public void setMoveMajorSignatureValue(String moveMajorSignatureValue) {
        this.moveMajorSignatureValue = moveMajorSignatureValue;
    }

    public String getMoveMajorSignaturePlace() {
        return moveMajorSignaturePlace;
    }

    public void setMoveMajorSignaturePlace(String moveMajorSignaturePlace) {
        this.moveMajorSignaturePlace = moveMajorSignaturePlace;
    }

    public String getMovePhone() {
        return movePhone;
    }

    public void setMovePhone(String movePhone) {
        this.movePhone = movePhone;
    }

    public String getMoveLawUnitSeal() {
        return moveLawUnitSeal;
    }

    public void setMoveLawUnitSeal(String moveLawUnitSeal) {
        this.moveLawUnitSeal = moveLawUnitSeal;
    }

    public String getMoveLawUnitValue() {
        return moveLawUnitValue;
    }

    public void setMoveLawUnitValue(String moveLawUnitValue) {
        this.moveLawUnitValue = moveLawUnitValue;
    }

    public String getMoveLawUnitPlace() {
        return moveLawUnitPlace;
    }

    public void setMoveLawUnitPlace(String moveLawUnitPlace) {
        this.moveLawUnitPlace = moveLawUnitPlace;
    }

    public Date getMoveLawUnitDate() {
        return moveLawUnitDate;
    }

    public void setMoveLawUnitDate(Date moveLawUnitDate) {
        this.moveLawUnitDate = moveLawUnitDate;
    }

    public String getReciveAdvice() {
        return reciveAdvice;
    }

    public void setReciveAdvice(String reciveAdvice) {
        this.reciveAdvice = reciveAdvice;
    }

    public String getReciveMajorSignatureBase64() {
        return reciveMajorSignatureBase64;
    }

    public void setReciveMajorSignatureBase64(String reciveMajorSignatureBase64) {
        this.reciveMajorSignatureBase64 = reciveMajorSignatureBase64;
    }

    public String getReciveMajorSignatureValue() {
        return reciveMajorSignatureValue;
    }

    public void setReciveMajorSignatureValue(String reciveMajorSignatureValue) {
        this.reciveMajorSignatureValue = reciveMajorSignatureValue;
    }

    public String getReciveMajorSignaturePlace() {
        return reciveMajorSignaturePlace;
    }

    public void setReciveMajorSignaturePlace(String reciveMajorSignaturePlace) {
        this.reciveMajorSignaturePlace = reciveMajorSignaturePlace;
    }

    public String getRecivePhone() {
        return recivePhone;
    }

    public void setRecivePhone(String recivePhone) {
        this.recivePhone = recivePhone;
    }

    public String getReciveLawUnitSeal() {
        return reciveLawUnitSeal;
    }

    public void setReciveLawUnitSeal(String reciveLawUnitSeal) {
        this.reciveLawUnitSeal = reciveLawUnitSeal;
    }

    public String getReciveLawUnitValue() {
        return reciveLawUnitValue;
    }

    public void setReciveLawUnitValue(String reciveLawUnitValue) {
        this.reciveLawUnitValue = reciveLawUnitValue;
    }

    public String getReciveLawUnitPlace() {
        return reciveLawUnitPlace;
    }

    public void setReciveLawUnitPlace(String reciveLawUnitPlace) {
        this.reciveLawUnitPlace = reciveLawUnitPlace;
    }

    public Date getReciveLawUnitDate() {
        return reciveLawUnitDate;
    }

    public void setReciveLawUnitDate(Date reciveLawUnitDate) {
        this.reciveLawUnitDate = reciveLawUnitDate;
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

	public String getOrgansCodeTo() {
        return organsCodeTo;
    }

    public void setOrgansCodeTo(String organsCodeTo) {
        this.organsCodeTo = organsCodeTo;
    }

    public String getOrgansNameTo() {
        return organsNameTo;
    }

    public void setOrgansNameTo(String organsNameTo) {
        this.organsNameTo = organsNameTo;
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

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

	public String getPartyTypeName() {
		return partyTypeName;
	}

	public void setPartyTypeName(String partyTypeName) {
		this.partyTypeName = partyTypeName;
	}

	public String getMoveMinorSignatureBase64() {
		return moveMinorSignatureBase64;
	}

	public void setMoveMinorSignatureBase64(String moveMinorSignatureBase64) {
		this.moveMinorSignatureBase64 = moveMinorSignatureBase64;
	}

	public String getMoveMinorSignatureValue() {
		return moveMinorSignatureValue;
	}

	public void setMoveMinorSignatureValue(String moveMinorSignatureValue) {
		this.moveMinorSignatureValue = moveMinorSignatureValue;
	}

	public String getMoveMinorSignaturePlace() {
		return moveMinorSignaturePlace;
	}

	public void setMoveMinorSignaturePlace(String moveMinorSignaturePlace) {
		this.moveMinorSignaturePlace = moveMinorSignaturePlace;
	}

	public String getCauseActionName() {
		return causeActionName;
	}

	public void setCauseActionName(String causeActionName) {
		this.causeActionName = causeActionName;
	}
    
    
}
