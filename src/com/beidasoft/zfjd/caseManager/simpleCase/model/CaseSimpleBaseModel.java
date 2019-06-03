/**   
 * 功能描述：
 * @Package: com.beidasoft.zfjd.caseManager.simpleCase.model 
 * @author: songff   
 * @date: 2018年12月26日 下午2:35:44 
 */
package com.beidasoft.zfjd.caseManager.simpleCase.model;

/**
 * 2018
 * 
 * @ClassName: CaseSimpleBasicModel.java
 * @Description: 简易案件信息表MODEL类
 *
 * @author: songff
 * @date: 2018年12月26日 下午2:35:44
 *
 */

public class CaseSimpleBaseModel {
    // 执法人员id
    private String staffIds;
    // 违法依据id
    private String gistIds;
    // 职权表id
    private String powerIds;
    // 处罚依据id
    private String punishIds;

    // 主键
    private String id;

    // 案件编号
    private String caseCode;

    // 主体ID
    private String subjectId;

    // 主体ID
    private String subjectIds;

    // 主体ID
    private String subjectName;

    // 执法部门ID
    private String departmentId;

    // 执法部门ID
    private String departmentName;

    // 登记人
    private String registrant;

    // 执法日期
    private String enforcementDateStr;

    // 登记日期
    private String registrantDateStr;

    private String registrantStartDateStr;

    private String registrantEndDateStr;

    // 案件名称
    private String name;

    // 当事人类型(code: 1公民，2法人，3其他组织)
    private String partyType;

    // 公民姓名
    private String citizenName;

    // 公民性别
    private Integer citizenSex;

    // 公民当时年龄
    private Integer citizenAge;

    // 公民有效身份证件类型（code: 01身份证，02驾驶证，03军官证，04护照，99其他）
    private String citizenCardType;

    // 公民有效身份证件号码
    private String citizenCardCode;

    // 公民单位
    private String citizenCompany;

    // 联系电话（公民）
    private String contactPhoneCitizen;

    // 公民住址
    private String citizenAddress;

    // 法定代表人或负责人
    private String principal;

    // 单位名称（法人）
    private String companyName;

    // 组织证件类型（法人）
    private String organizationCodeType;

    // 组织机构代码（法人）
    private String organizationCode;

    // 统一社会信用代码（法人）
    private String uniformCreditCode;

    // 地址（法人）
    private String address;

    // 行政处罚决定书文号
    private String punishmentCode;

    // 行政处罚决定书日期（开始）
    private String punishmentStartDateStr;

    // 行政处罚决定书日期
    private String punishmentDateStr;

    // 行政处罚决定书日期（结束）
    private String punishmentEndDateStr;

    // 是否警告（0不警告，1警告)
    private Integer isWarn;

    // 是否罚款（0不罚款，1罚款）
    private Integer isFine;

    // 罚款金额
    private Double fineSum;

    // 是否行政复议（0不复议，1复议）
    private Integer isReconsideration;

    // 复议日期
    private String reconsiderationDateStr;

    // 复议结果
    private String reconsiderationResult;

    // 是否行政诉讼（0不诉讼，1诉讼）
    private Integer isLawsuit;

    // 诉讼日期
    private String lawsuitDateStr;

    // 诉讼结果
    private String lawsuitResult;

    // 是否结案(0未结案，1已结案)
    private Integer isEndCase;

    // 结案日期
    private String closedDateStr;

    private String closedStartDateStr;

    private String closedEndDateStr;

    // 当前状态（code: 01未办理完成，02办理完成）
    private String currentState;

    // 是否作出处罚(1作出处罚，2不予处罚)
    private Integer isPunishment;

    // 立案日期
    private String filingDateStr;

    private String filingStartDateStr;

    private String filingEndDateStr;

    // 处罚决定执行日期
    private String punishDecisionExecutDateStr;

    // 违法行为发生地
    private String happenPlace;

    // 联系电话（法人或其他组织）
    private String contactPhoneLegalperson;

    // 其他机构负责人
    private String otherOrganName;

    // 其他机构联系方式
    private String otherOrganPhoneNum;

    // 其他机构名称
    private String otherOrganCompanyName;

    // 组织证件类型（其他组织）
    private String otherOrganCodeType;

    // 其他组织 组织机构代码
    private String otherOrganCode;

    // 其他组织 统一社会信用代码
    private String otherCreditCode;

    // 其他组织机构地址
    private String otherOrganAddress;

    // 是否提交
    private Integer isSubmit;

    // 是否个体户
    private Integer isSelfemployed;

    // 字号(是个体户属性)
    private String selfemployedCode;

    // 营业执照号(是个体户属性)
    private String selfemployedCharteredCode;

    // 代码类型(是个体户属性)
    private String selfemployedCharteredType;

    // 营业地址(是个体户属性)
    private String selfemployedAddress;

    // 创建时间（起）
    private String createStartDateStr;

    // 创建时间
    private String createDateStr;

    // 创建时间（止）
    private String createEndDateStr;

    // 数据来源（0系统录入，1接口对接）
    private Integer dataSource;

    // 提交日期
    private String submitDateStr;

    // 更新日期
    private String updateDateStr;

    // 删除标识（0未删除，1已删除）
    private Integer isDelete;

    // 删除日期
    private String deleteDateStr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectIds() {
        return subjectIds;
    }

    public void setSubjectIds(String subjectIds) {
        this.subjectIds = subjectIds;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getRegistrant() {
        return registrant;
    }

    public void setRegistrant(String registrant) {
        this.registrant = registrant;
    }

    public String getEnforcementDateStr() {
        return enforcementDateStr;
    }

    public void setEnforcementDateStr(String enforcementDateStr) {
        this.enforcementDateStr = enforcementDateStr;
    }

    public String getRegistrantDateStr() {
        return registrantDateStr;
    }

    public void setRegistrantDateStr(String registrantDateStr) {
        this.registrantDateStr = registrantDateStr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPartyType() {
        return partyType;
    }

    public void setPartyType(String partyType) {
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

    public Integer getCitizenAge() {
        return citizenAge;
    }

    public void setCitizenAge(Integer citizenAge) {
        this.citizenAge = citizenAge;
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

    public String getContactPhoneCitizen() {
        return contactPhoneCitizen;
    }

    public void setContactPhoneCitizen(String contactPhoneCitizen) {
        this.contactPhoneCitizen = contactPhoneCitizen;
    }

    public String getCitizenAddress() {
        return citizenAddress;
    }

    public void setCitizenAddress(String citizenAddress) {
        this.citizenAddress = citizenAddress;
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

    public String getUniformCreditCode() {
        return uniformCreditCode;
    }

    public void setUniformCreditCode(String uniformCreditCode) {
        this.uniformCreditCode = uniformCreditCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPunishmentCode() {
        return punishmentCode;
    }

    public void setPunishmentCode(String punishmentCode) {
        this.punishmentCode = punishmentCode;
    }

    public String getPunishmentStartDateStr() {
        return punishmentStartDateStr;
    }

    public void setPunishmentStartDateStr(String punishmentStartDateStr) {
        this.punishmentStartDateStr = punishmentStartDateStr;
    }

    public String getPunishmentEndDateStr() {
        return punishmentEndDateStr;
    }

    public void setPunishmentEndDateStr(String punishmentEndDateStr) {
        this.punishmentEndDateStr = punishmentEndDateStr;
    }

    public Integer getIsWarn() {
        return isWarn;
    }

    public void setIsWarn(Integer isWarn) {
        this.isWarn = isWarn;
    }

    public Integer getIsFine() {
        return isFine;
    }

    public void setIsFine(Integer isFine) {
        this.isFine = isFine;
    }

    public Double getFineSum() {
        return fineSum;
    }

    public void setFineSum(Double fineSum) {
        this.fineSum = fineSum;
    }

    public Integer getIsReconsideration() {
        return isReconsideration;
    }

    public void setIsReconsideration(Integer isReconsideration) {
        this.isReconsideration = isReconsideration;
    }

    public String getReconsiderationDateStr() {
        return reconsiderationDateStr;
    }

    public void setReconsiderationDateStr(String reconsiderationDateStr) {
        this.reconsiderationDateStr = reconsiderationDateStr;
    }

    public String getReconsiderationResult() {
        return reconsiderationResult;
    }

    public void setReconsiderationResult(String reconsiderationResult) {
        this.reconsiderationResult = reconsiderationResult;
    }

    public Integer getIsLawsuit() {
        return isLawsuit;
    }

    public void setIsLawsuit(Integer isLawsuit) {
        this.isLawsuit = isLawsuit;
    }

    public String getLawsuitDateStr() {
        return lawsuitDateStr;
    }

    public void setLawsuitDateStr(String lawsuitDateStr) {
        this.lawsuitDateStr = lawsuitDateStr;
    }

    public String getLawsuitResult() {
        return lawsuitResult;
    }

    public void setLawsuitResult(String lawsuitResult) {
        this.lawsuitResult = lawsuitResult;
    }

    public Integer getIsEndCase() {
        return isEndCase;
    }

    public void setIsEndCase(Integer isEndCase) {
        this.isEndCase = isEndCase;
    }

    public String getClosedDateStr() {
        return closedDateStr;
    }

    public void setClosedDateStr(String closedDateStr) {
        this.closedDateStr = closedDateStr;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public Integer getIsPunishment() {
        return isPunishment;
    }

    public void setIsPunishment(Integer isPunishment) {
        this.isPunishment = isPunishment;
    }

    public String getFilingDateStr() {
        return filingDateStr;
    }

    public void setFilingDateStr(String filingDateStr) {
        this.filingDateStr = filingDateStr;
    }

    public String getPunishDecisionExecutDateStr() {
        return punishDecisionExecutDateStr;
    }

    public void setPunishDecisionExecutDateStr(String punishDecisionExecutDateStr) {
        this.punishDecisionExecutDateStr = punishDecisionExecutDateStr;
    }

    public String getHappenPlace() {
        return happenPlace;
    }

    public void setHappenPlace(String happenPlace) {
        this.happenPlace = happenPlace;
    }

    public String getContactPhoneLegalperson() {
        return contactPhoneLegalperson;
    }

    public void setContactPhoneLegalperson(String contactPhoneLegalperson) {
        this.contactPhoneLegalperson = contactPhoneLegalperson;
    }

    public String getOtherOrganName() {
        return otherOrganName;
    }

    public void setOtherOrganName(String otherOrganName) {
        this.otherOrganName = otherOrganName;
    }

    public String getOtherOrganPhoneNum() {
        return otherOrganPhoneNum;
    }

    public void setOtherOrganPhoneNum(String otherOrganPhoneNum) {
        this.otherOrganPhoneNum = otherOrganPhoneNum;
    }

    public String getOtherOrganCompanyName() {
        return otherOrganCompanyName;
    }

    public void setOtherOrganCompanyName(String otherOrganCompanyName) {
        this.otherOrganCompanyName = otherOrganCompanyName;
    }

    public String getOtherOrganCode() {
        return otherOrganCode;
    }

    public void setOtherOrganCode(String otherOrganCode) {
        this.otherOrganCode = otherOrganCode;
    }

    public String getOtherCreditCode() {
        return otherCreditCode;
    }

    public void setOtherCreditCode(String otherCreditCode) {
        this.otherCreditCode = otherCreditCode;
    }

    public String getOtherOrganAddress() {
        return otherOrganAddress;
    }

    public void setOtherOrganAddress(String otherOrganAddress) {
        this.otherOrganAddress = otherOrganAddress;
    }

    public Integer getIsSubmit() {
        return isSubmit;
    }

    public void setIsSubmit(Integer isSubmit) {
        this.isSubmit = isSubmit;
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

    public String getSelfemployedCharteredType() {
        return selfemployedCharteredType;
    }

    public void setSelfemployedCharteredType(String selfemployedCharteredType) {
        this.selfemployedCharteredType = selfemployedCharteredType;
    }

    public String getCreateStartDateStr() {
        return createStartDateStr;
    }

    public void setCreateStartDateStr(String createStartDateStr) {
        this.createStartDateStr = createStartDateStr;
    }

    public String getCreateEndDateStr() {
        return createEndDateStr;
    }

    public void setCreateEndDateStr(String createEndDateStr) {
        this.createEndDateStr = createEndDateStr;
    }

    public Integer getDataSource() {
        return dataSource;
    }

    public void setDataSource(Integer dataSource) {
        this.dataSource = dataSource;
    }

    public String getSubmitDateStr() {
        return submitDateStr;
    }

    public void setSubmitDateStr(String submitDateStr) {
        this.submitDateStr = submitDateStr;
    }

    public String getUpdateDateStr() {
        return updateDateStr;
    }

    public void setUpdateDateStr(String updateDateStr) {
        this.updateDateStr = updateDateStr;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getDeleteDateStr() {
        return deleteDateStr;
    }

    public void setDeleteDateStr(String deleteDateStr) {
        this.deleteDateStr = deleteDateStr;
    }

    public String getPunishmentDateStr() {
        return punishmentDateStr;
    }

    public void setPunishmentDateStr(String punishmentDateStr) {
        this.punishmentDateStr = punishmentDateStr;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public String getStaffIds() {
        return staffIds;
    }

    public void setStaffIds(String staffIds) {
        this.staffIds = staffIds;
    }

    public String getGistIds() {
        return gistIds;
    }

    public void setGistIds(String gistIds) {
        this.gistIds = gistIds;
    }

    public String getPowerIds() {
        return powerIds;
    }

    public void setPowerIds(String powerIds) {
        this.powerIds = powerIds;
    }

    public String getPunishIds() {
        return punishIds;
    }

    public void setPunishIds(String punishIds) {
        this.punishIds = punishIds;
    }

    public String getOrganizationCodeType() {
        return organizationCodeType;
    }

    public void setOrganizationCodeType(String organizationCodeType) {
        this.organizationCodeType = organizationCodeType;
    }

    public String getOtherOrganCodeType() {
        return otherOrganCodeType;
    }

    public void setOtherOrganCodeType(String otherOrganCodeType) {
        this.otherOrganCodeType = otherOrganCodeType;
    }

    public String getRegistrantStartDateStr() {
        return registrantStartDateStr;
    }

    public void setRegistrantStartDateStr(String registrantStartDateStr) {
        this.registrantStartDateStr = registrantStartDateStr;
    }

    public String getRegistrantEndDateStr() {
        return registrantEndDateStr;
    }

    public void setRegistrantEndDateStr(String registrantEndDateStr) {
        this.registrantEndDateStr = registrantEndDateStr;
    }

    public String getClosedStartDateStr() {
        return closedStartDateStr;
    }

    public void setClosedStartDateStr(String closedStartDateStr) {
        this.closedStartDateStr = closedStartDateStr;
    }

    public String getClosedEndDateStr() {
        return closedEndDateStr;
    }

    public void setClosedEndDateStr(String closedEndDateStr) {
        this.closedEndDateStr = closedEndDateStr;
    }

    public String getFilingStartDateStr() {
        return filingStartDateStr;
    }

    public void setFilingStartDateStr(String filingStartDateStr) {
        this.filingStartDateStr = filingStartDateStr;
    }

    public String getFilingEndDateStr() {
        return filingEndDateStr;
    }

    public void setFilingEndDateStr(String filingEndDateStr) {
        this.filingEndDateStr = filingEndDateStr;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

}
