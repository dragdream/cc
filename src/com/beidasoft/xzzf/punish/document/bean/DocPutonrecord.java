package com.beidasoft.xzzf.punish.document.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 立案审批表实体类
 */
@Entity
@Table(name="ZF_DOC_PUTONRECORD")
public class DocPutonrecord {
    // 立案审批表id
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

    // 当事人证件名称及编号
    @Column(name = "ID_NAME")
    private String idName;
    
    // 当事人证件名称及编号
    @Column(name = "ID_NAME_CODE")
    private String idNameCode;

    // 当事人类型
    @Column(name = "PARTY_TYPE")
    private String partyType;

    // 当事人姓名
    @Column(name = "PARTY_PERSON_NAME")
    private String partyPersonName;
    
    // 当事人联系电话
    @Column(name = "PARTY_PHONE")
    private String partyPhone;

    // 当事人住址
    @Column(name = "PARTY_ADDRESS")
    private String partyAddress;

    // 案件来源
    @Column(name = "CASE_SOURCE")
    private String caseSource;

    // 案由
    @Column(name = "CAUSE_ACTION")
    private String causeAction;
    
    // 案由名
    @Column(name = "CAUSE_ACTION_NAME")
    private String causeActionName;

    // 案情概要
    @Column(name = "CASE_OVERVIEW")
    private String caseOverview;

    // 承办人员意见
    @Column(name = "UNDERTAKE_PERSONAL_IDEA")
    private String undertakePersonalIdea;

    // 承办人员主办人签名图片
    @Lob
    @Column(name = "MAJOR_SIGNATURE_BASE64")
    private String majorSignatureBase64;

    // 承办人员主办人签名值
    @Lob
    @Column(name = "MAJOR_SIGNATURE_VALUE")
    private String majorSignatureValue;

    // 承办人员主办人签名位置
    @Column(name = "MAJOR_SIGNATURE_PLACE")
    private String majorSignaturePlace;

    // 承办人员协办人签名图片
    @Lob
    @Column(name = "MINOR_SIGNATURE_BASE64")
    private String minorSignatureBase64;

    // 承办人员协办人签名值
    @Lob
    @Column(name = "MINOR_SIGNATURE_VALUE")
    private String minorSignatureValue;

    // 承办人员协办人签名位置
    @Column(name = "MINOR_SIGNATURE_PLACE")
    private String minorSignaturePlace;

    // 承办人员签名时间
    @Column(name = "UNDERTAKE_PERSONAL_TIME")
    private Date undertakePersonalTime;

    // 承办部门意见
    @Column(name = "UNDERTAKE_DEPARTMENT_IDEA")
    private String undertakeDepartmentIdea;

    // 承办部门负责人签名图片
    @Lob
    @Column(name = "DEPT_PERSON_SIGNATURE_BASE64")
    private String departmentPersonSignatureBase64;

    // 承办部门负责人签名值
    @Lob
    @Column(name = "DEPT_PERSON_SIGNATURE_VALUE")
    private String departmentPersonSignatureValue;

    // 承办部门负责人签名位置
    @Column(name = "DEPT_PERSON_SIGNATURE_PLACE")
    private String departmentPersonSignaturePlace;

    // 承办部门签名时间
    @Column(name = "UNDERTAKE_DEPARTMENT_TIME")
    private Date undertakeDepartmentTime;

    // 备注
    @Column(name = "NOTE")
    private String note;

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

    public String getPartyPersonName() {
		return partyPersonName;
	}

	public void setPartyPersonName(String partyPersonName) {
		this.partyPersonName = partyPersonName;
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

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
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

    public String getCaseSource() {
        return caseSource;
    }

    public void setCaseSource(String caseSource) {
        this.caseSource = caseSource;
    }

    public String getCauseAction() {
        return causeAction;
    }

    public void setCauseAction(String causeAction) {
        this.causeAction = causeAction;
    }

    public String getCaseOverview() {
        return caseOverview;
    }

    public void setCaseOverview(String caseOverview) {
        this.caseOverview = caseOverview;
    }

    public String getUndertakePersonalIdea() {
        return undertakePersonalIdea;
    }

    public void setUndertakePersonalIdea(String undertakePersonalIdea) {
        this.undertakePersonalIdea = undertakePersonalIdea;
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

    public Date getUndertakePersonalTime() {
        return undertakePersonalTime;
    }

    public void setUndertakePersonalTime(Date undertakePersonalTime) {
        this.undertakePersonalTime = undertakePersonalTime;
    }

    public String getUndertakeDepartmentIdea() {
        return undertakeDepartmentIdea;
    }

    public void setUndertakeDepartmentIdea(String undertakeDepartmentIdea) {
        this.undertakeDepartmentIdea = undertakeDepartmentIdea;
    }

    public String getDepartmentPersonSignatureBase64() {
        return departmentPersonSignatureBase64;
    }

    public void setDepartmentPersonSignatureBase64(String departmentPersonSignatureBase64) {
        this.departmentPersonSignatureBase64 = departmentPersonSignatureBase64;
    }

    public String getDepartmentPersonSignatureValue() {
        return departmentPersonSignatureValue;
    }

    public void setDepartmentPersonSignatureValue(String departmentPersonSignatureValue) {
        this.departmentPersonSignatureValue = departmentPersonSignatureValue;
    }

    public String getDepartmentPersonSignaturePlace() {
        return departmentPersonSignaturePlace;
    }

    public void setDepartmentPersonSignaturePlace(String departmentPersonSignaturePlace) {
        this.departmentPersonSignaturePlace = departmentPersonSignaturePlace;
    }

    public Date getUndertakeDepartmentTime() {
        return undertakeDepartmentTime;
    }

    public void setUndertakeDepartmentTime(Date undertakeDepartmentTime) {
        this.undertakeDepartmentTime = undertakeDepartmentTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

	public String getIdNameCode() {
		return idNameCode;
	}

	public void setIdNameCode(String idNameCode) {
		this.idNameCode = idNameCode;
	}

	public String getCauseActionName() {
		return causeActionName;
	}

	public void setCauseActionName(String causeActionName) {
		this.causeActionName = causeActionName;
	}
	
	

}
