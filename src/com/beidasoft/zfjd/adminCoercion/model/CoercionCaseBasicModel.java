package com.beidasoft.zfjd.adminCoercion.model;

/**
 * 强制案件基础表MODEL类
 */
public class CoercionCaseBasicModel {
    // 唯一标识
    private String id;

    // 案件触发来源标识(100平台内检查，200处罚，300直接强制）
    private Integer caseSourceType;
    
    // 案件触发来源标识(100平台内检查，200处罚，300直接强制）
    private String caseSourceTypeStr;
    
    // 案件来源处ID
    private String caseSourceId;

    // 创建日期
    private String createDateStr;

    // 数据创建来源（1对接、2平台录入）
    private Integer createType;

    // 数据更新日期
    private String updateDateStr;

    // 数据更新人员
    private String updatePersonId;

    // 案件名称
    private String name;

    // 删除标识
    private Integer isDelete;

    // 是否已提交
    private Integer isSubmit;

    // 是否已结案
    private Integer isClosed;

    // 申请立案日期
    private String applyFilingDateStr;

    // 结案日期
    private String closedDateStr;

    // 立案号
    private String filingNumber;

    // 所属部门ID
    private String departmentId;

    // 所属部门名称
    private String departmentName;
    
    // 所属主体ID
    private String subjectId;

    // 案件编号
    private String caseCode;

    // 登记人
    private String registrant;

    // 案件来源
    private Integer caseSource;

    // 案件来源-其他-详细描述
    private String caseSourceOther;

    // 案由
    private String caseReason;

    // 批准立案日期
    private String approveFilingDateStr;

    // 立案批准人
    private String approveFilingPerson;

    // 当事人类型
    private Integer partyType;

    // 公民姓名
    private String citizenName;

    // 公民性别
    private Integer citizenSex;

    // 公民有效证件类型
    private String citizenCardType;

    // 公民有效证件号码
    private String citizenCardCode;

    // 公民单位
    private String citizenCompany;

    // 公民住址
    private String citizenAddress;

    // 联系电话（公民）
    private String contactPhoneCitizen;

    // 是否个体工商户
    private Integer isSelfemployed;

    // 字号
    private String selfemployedCode;

    // 营业执照号
    private String selfemployedCharteredCode;

    // 营业地址
    private String selfemployedAddress;

    // 公民年龄
    private Integer citizenAge;

    // 法定代表人或负责人
    private String principal;

    // 单位名称（法人）
    private String companyName;

    // 组织机构代码
    private String organizationCode;

    // 联系电话（法人或其他组织）
    private String contactPhoneLegalperson;

    // 地址（法人或企业公司地址）
    private String companyAddress;
    
    //用于查看页面类型控制
    private Integer ctrlSeeType;

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

    public String getCaseSourceTypeStr() {
        return caseSourceTypeStr;
    }

    public void setCaseSourceTypeStr(String caseSourceTypeStr) {
        this.caseSourceTypeStr = caseSourceTypeStr;
    }

    public String getCaseSourceId() {
        return caseSourceId;
    }

    public void setCaseSourceId(String caseSourceId) {
        this.caseSourceId = caseSourceId;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public Integer getCreateType() {
        return createType;
    }

    public void setCreateType(Integer createType) {
        this.createType = createType;
    }

    public String getUpdateDateStr() {
        return updateDateStr;
    }

    public void setUpdateDateStr(String updateDateStr) {
        this.updateDateStr = updateDateStr;
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

    public String getApplyFilingDateStr() {
        return applyFilingDateStr;
    }

    public void setApplyFilingDateStr(String applyFilingDateStr) {
        this.applyFilingDateStr = applyFilingDateStr;
    }

    public String getClosedDateStr() {
        return closedDateStr;
    }

    public void setClosedDateStr(String closedDateStr) {
        this.closedDateStr = closedDateStr;
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

    public String getApproveFilingDateStr() {
        return approveFilingDateStr;
    }

    public void setApproveFilingDateStr(String approveFilingDateStr) {
        this.approveFilingDateStr = approveFilingDateStr;
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

	public Integer getCtrlSeeType() {
		return ctrlSeeType;
	}

	public void setCtrlSeeType(Integer ctrlSeeType) {
		this.ctrlSeeType = ctrlSeeType;
	}
}
