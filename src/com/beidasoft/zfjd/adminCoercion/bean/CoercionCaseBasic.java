package com.beidasoft.zfjd.adminCoercion.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 强制案件基础表实体类
 */
@Entity
@Table(name="TBL_COERCION_CASE_BASIC")
public class CoercionCaseBasic {
    // 唯一标识
    @Id
    @Column(name = "ID")
    private String id;

    // 案件触发来源标识(100平台内检查，200处罚，300直接强制）
    @Column(name = "CASE_SOURCE_TYPE")
    private Integer caseSourceType;

    // 案件来源处ID
    @Column(name = "CASE_SOURCE_ID")
    private String caseSourceId;

    // 创建日期
    @Column(name = "CREATE_DATE")
    private Date createDate;

    // 数据创建来源（1对接、2平台录入）
    @Column(name = "CREATE_TYPE")
    private Integer createType;

    // 数据更新日期
    @Column(name = "UPDATE_DATE")
    private Date updateDate;

    // 数据更新人员
    @Column(name = "UPDATE_PERSON_ID")
    private String updatePersonId;

    // 案件名称
    @Column(name = "NAME")
    private String name;

    // 删除标识
    @Column(name = "IS_DELETE")
    private Integer isDelete;

    // 是否已提交
    @Column(name = "IS_SUBMIT")
    private Integer isSubmit;

    // 是否已结案
    @Column(name = "IS_CLOSED")
    private Integer isClosed;

    // 申请立案日期
    @Column(name = "APPLY_FILING_DATE")
    private Date applyFilingDate;

    // 结案日期
    @Column(name = "CLOSED_DATE")
    private Date closedDate;

    // 立案号
    @Column(name = "FILING_NUMBER")
    private String filingNumber;

    // 所属部门ID
    @Column(name = "DEPARTMENT_ID")
    private String departmentId;

    // 所属部门名称
    @Column(name = "DEPARTMENT_NAME")
    private String departmentName;
    
    // 所属主体id
    @Column(name = "SUBJECT_ID")
    private String subjectId;

    // 案件编号
    @Column(name = "CASE_CODE")
    private String caseCode;

    // 登记人
    @Column(name = "REGISTRANT")
    private String registrant;

    // 案件来源
    @Column(name = "CASE_SOURCE")
    private Integer caseSource;

    // 案件来源-其他-详细描述
    @Column(name = "CASE_SOURCE_OTHER")
    private String caseSourceOther;

    // 案由
    @Column(name = "CASE_REASON")
    private String caseReason;

    // 批准立案日期
    @Column(name = "APPROVE_FILING_DATE")
    private Date approveFilingDate;

    // 立案批准人
    @Column(name = "APPROVE_FILING_PERSON")
    private String approveFilingPerson;

    // 当事人类型
    @Column(name = "PARTY_TYPE")
    private Integer partyType;

    // 公民姓名
    @Column(name = "CITIZEN_NAME")
    private String citizenName;

    // 公民性别
    @Column(name = "CITIZEN_SEX")
    private Integer citizenSex;

    // 公民有效证件类型
    @Column(name = "CITIZEN_CARD_TYPE")
    private String citizenCardType;

    // 公民有效证件号码
    @Column(name = "CITIZEN_CARD_CODE")
    private String citizenCardCode;

    // 公民单位
    @Column(name = "CITIZEN_COMPANY")
    private String citizenCompany;

    // 公民住址
    @Column(name = "CITIZEN_ADDRESS")
    private String citizenAddress;

    // 联系电话（公民）
    @Column(name = "CONTACT_PHONE_CITIZEN")
    private String contactPhoneCitizen;

    // 是否个体工商户
    @Column(name = "IS_SELFEMPLOYED")
    private Integer isSelfemployed;

    // 字号
    @Column(name = "SELFEMPLOYED_CODE")
    private String selfemployedCode;

    // 营业执照号
    @Column(name = "SELFEMPLOYED_CHARTERED_CODE")
    private String selfemployedCharteredCode;

    // 营业地址
    @Column(name = "SELFEMPLOYED_ADDRESS")
    private String selfemployedAddress;

    // 公民年龄
    @Column(name = "CITIZEN_AGE")
    private Integer citizenAge;

    // 法定代表人或负责人
    @Column(name = "PRINCIPAL")
    private String principal;

    // 单位名称（法人）
    @Column(name = "COMPANY_NAME")
    private String companyName;

    // 组织机构代码
    @Column(name = "ORGANIZATION_CODE")
    private String organizationCode;

    // 联系电话（法人或其他组织）
    @Column(name = "CONTACT_PHONE_LEGALPERSON")
    private String contactPhoneLegalperson;

    // 地址（法人或企业公司地址）
    @Column(name = "COMPANY_ADDRESS")
    private String companyAddress;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCaseSourceType() {
        return caseSourceType;
    }

    public void setCaseSourceType(Integer caseSourceType) {
        this.caseSourceType = caseSourceType;
    }

    public String getCaseSourceId() {
        return caseSourceId;
    }

    public void setCaseSourceId(String caseSourceId) {
        this.caseSourceId = caseSourceId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getCreateType() {
        return createType;
    }

    public void setCreateType(Integer createType) {
        this.createType = createType;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdatePersonId() {
        return updatePersonId;
    }

    public void setUpdatePersonId(String updatePersonId) {
        this.updatePersonId = updatePersonId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getIsSubmit() {
        return isSubmit;
    }

    public void setIsSubmit(Integer isSubmit) {
        this.isSubmit = isSubmit;
    }

    public Integer getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Integer isClosed) {
        this.isClosed = isClosed;
    }

    public Date getApplyFilingDate() {
        return applyFilingDate;
    }

    public void setApplyFilingDate(Date applyFilingDate) {
        this.applyFilingDate = applyFilingDate;
    }

    public Date getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
    }

    public String getFilingNumber() {
        return filingNumber;
    }

    public void setFilingNumber(String filingNumber) {
        this.filingNumber = filingNumber;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode;
    }

    public String getRegistrant() {
        return registrant;
    }

    public void setRegistrant(String registrant) {
        this.registrant = registrant;
    }

    public Integer getCaseSource() {
        return caseSource;
    }

    public void setCaseSource(Integer caseSource) {
        this.caseSource = caseSource;
    }

    public String getCaseSourceOther() {
        return caseSourceOther;
    }

    public void setCaseSourceOther(String caseSourceOther) {
        this.caseSourceOther = caseSourceOther;
    }

    public String getCaseReason() {
        return caseReason;
    }

    public void setCaseReason(String caseReason) {
        this.caseReason = caseReason;
    }

    public Date getApproveFilingDate() {
        return approveFilingDate;
    }

    public void setApproveFilingDate(Date approveFilingDate) {
        this.approveFilingDate = approveFilingDate;
    }

    public String getApproveFilingPerson() {
        return approveFilingPerson;
    }

    public void setApproveFilingPerson(String approveFilingPerson) {
        this.approveFilingPerson = approveFilingPerson;
    }

    public Integer getPartyType() {
        return partyType;
    }

    public void setPartyType(Integer partyType) {
        this.partyType = partyType;
    }

    public String getCitizenName() {
        return citizenName;
    }

    public void setCitizenName(String citizenName) {
        this.citizenName = citizenName;
    }

    public Integer getCitizenSex() {
        return citizenSex;
    }

    public void setCitizenSex(Integer citizenSex) {
        this.citizenSex = citizenSex;
    }

    public String getCitizenCardType() {
        return citizenCardType;
    }

    public void setCitizenCardType(String citizenCardType) {
        this.citizenCardType = citizenCardType;
    }

    public String getCitizenCardCode() {
        return citizenCardCode;
    }

    public void setCitizenCardCode(String citizenCardCode) {
        this.citizenCardCode = citizenCardCode;
    }

    public String getCitizenCompany() {
        return citizenCompany;
    }

    public void setCitizenCompany(String citizenCompany) {
        this.citizenCompany = citizenCompany;
    }

    public String getCitizenAddress() {
        return citizenAddress;
    }

    public void setCitizenAddress(String citizenAddress) {
        this.citizenAddress = citizenAddress;
    }

    public String getContactPhoneCitizen() {
        return contactPhoneCitizen;
    }

    public void setContactPhoneCitizen(String contactPhoneCitizen) {
        this.contactPhoneCitizen = contactPhoneCitizen;
    }

    public Integer getIsSelfemployed() {
        return isSelfemployed;
    }

    public void setIsSelfemployed(Integer isSelfemployed) {
        this.isSelfemployed = isSelfemployed;
    }

    public String getSelfemployedCode() {
        return selfemployedCode;
    }

    public void setSelfemployedCode(String selfemployedCode) {
        this.selfemployedCode = selfemployedCode;
    }

    public String getSelfemployedCharteredCode() {
        return selfemployedCharteredCode;
    }

    public void setSelfemployedCharteredCode(String selfemployedCharteredCode) {
        this.selfemployedCharteredCode = selfemployedCharteredCode;
    }

    public String getSelfemployedAddress() {
        return selfemployedAddress;
    }

    public void setSelfemployedAddress(String selfemployedAddress) {
        this.selfemployedAddress = selfemployedAddress;
    }

    public Integer getCitizenAge() {
        return citizenAge;
    }

    public void setCitizenAge(Integer citizenAge) {
        this.citizenAge = citizenAge;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getContactPhoneLegalperson() {
        return contactPhoneLegalperson;
    }

    public void setContactPhoneLegalperson(String contactPhoneLegalperson) {
        this.contactPhoneLegalperson = contactPhoneLegalperson;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

}
