/**   
 * 功能描述：简易案件信息表实体类
 * @Package: com.beidasoft.zfjd.caseManager.simpleCase.bean 
 * @author: songff   
 * @date: 2018年12月26日 下午1:00:37 
 */
package com.beidasoft.zfjd.caseManager.simpleCase.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**   
* 2018 
* @ClassName: CaseSimpleBasic.java
* @Description: 简易案件信息表实体类
*
* @author: songff
* @date: 2018年12月26日 下午1:00:37 
*
*/

@Entity
@Table(name="TBL_CASE_SIMPLE_BASE")
public class CaseSimpleBase {
    // 主键
    @Id
    @Column(name = "ID")
    private String id;

    // 案件编号
    @Column(name = "CASE_CODE")
    private String caseCode;

    // 主体ID
    @Column(name = "SUBJECT_ID")
    private String subjectId;

    // 执法部门ID
    @Column(name = "DEPARTMENT_ID")
    private String departmentId;

    // 登记人
    @Column(name = "REGISTRANT")
    private String registrant;

    // 执法日期
    @Column(name = "ENFORCEMENT_DATE")
    private Date enforcementDate;

    // 登记日期
    @Column(name = "REGISTRANT_DATE")
    private Date registrantDate;

    // 案件名称
    @Column(name = "NAME")
    private String name;

    // 当事人类型(code: 1公民，2法人，3其他组织)
    @Column(name = "PARTY_TYPE")
    private String partyType;

    // 公民姓名
    @Column(name = "CITIZEN_NAME")
    private String citizenName;

    // 公民性别
    @Column(name = "CITIZEN_SEX")
    private Integer citizenSex;

    // 公民当时年龄
    @Column(name = "CITIZEN_AGE")
    private Integer citizenAge;

    // 公民有效身份证件类型（code: 01身份证，02驾驶证，03军官证，04护照，99其他）
    @Column(name = "CITIZEN_CARD_TYPE")
    private String citizenCardType;

    // 公民有效身份证件号码
    @Column(name = "CITIZEN_CARD_CODE")
    private String citizenCardCode;

    // 公民单位
    @Column(name = "CITIZEN_COMPANY")
    private String citizenCompany;

    // 联系电话（公民）
    @Column(name = "CONTACT_PHONE_CITIZEN")
    private String contactPhoneCitizen;

    // 公民住址
    @Column(name = "CITIZEN_ADDRESS")
    private String citizenAddress;

    // 法定代表人或负责人
    @Column(name = "PRINCIPAL")
    private String principal;

    // 单位名称（法人）
    @Column(name = "COMPANY_NAME")
    private String companyName;
    
    // 组织证件类型（法人）
    @Column(name = "ORGANIZATION_CODE_TYPE")
    private String organizationCodeType;

    // 组织机构代码（法人）
    @Column(name = "ORGANIZATION_CODE")
    private String organizationCode;

    // 统一社会信用代码（法人）
    @Column(name = "UNIFORM_CREDIT_CODE")
    private String uniformCreditCode;

    // 地址（法人）
    @Column(name = "ADDRESS")
    private String address;

    // 行政处罚决定书文号
    @Column(name = "PUNISHMENT_CODE")
    private String punishmentCode;

    // 行政处罚决定书日期
    @Column(name = "PUNISHMENT_DATE")
    private Date punishmentDate;

    // 是否警告（0不警告，1警告)
    @Column(name = "IS_WARN")
    private Integer isWarn;

    // 是否罚款（0不罚款，1罚款）
    @Column(name = "IS_FINE")
    private Integer isFine;

    // 罚款金额
    @Column(name = "FINE_SUM")
    private Double fineSum;

    // 是否行政复议（0不复议，1复议）
    @Column(name = "IS_RECONSIDERATION")
    private Integer isReconsideration;

    // 复议日期
    @Column(name = "RECONSIDERATION_DATE")
    private Date reconsiderationDate;

    // 复议结果
    @Column(name = "RECONSIDERATION_RESULT")
    private String reconsiderationResult;

    // 是否行政诉讼（0不诉讼，1诉讼）
    @Column(name = "IS_LAWSUIT")
    private Integer isLawsuit;

    // 诉讼日期
    @Column(name = "LAWSUIT_DATE")
    private Date lawsuitDate;

    // 诉讼结果
    @Column(name = "LAWSUIT_RESULT")
    private String lawsuitResult;

    // 是否结案(0未结案，1已结案)
    @Column(name = "IS_END_CASE")
    private Integer isEndCase;

    // 结案日期
    @Column(name = "CLOSED_DATE")
    private Date closedDate;

    // 当前状态（code: 01未办理完成，02办理完成）
    @Column(name = "CURRENT_STATE")
    private String currentState;

    // 是否作出处罚(1作出处罚，2不予处罚)
    @Column(name = "IS_PUNISHMENT")
    private Integer isPunishment;

    // 立案日期
    @Column(name = "FILING_DATE")
    private Date filingDate;

    // 处罚决定执行日期
    @Column(name = "PUNISH_DECISION_EXECUT_DATE")
    private Date punishDecisionExecutDate;

    // 违法行为发生地
    @Column(name = "HAPPEN_PLACE")
    private String happenPlace;

    // 联系电话（法人或其他组织）
    @Column(name = "CONTACT_PHONE_LEGALPERSON")
    private String contactPhoneLegalperson;

    // 其他机构负责人
    @Column(name = "OTHER_ORGAN_NAME")
    private String otherOrganName;

    // 其他机构联系方式
    @Column(name = "OTHER_ORGAN_PHONE_NUM")
    private String otherOrganPhoneNum;

    // 其他机构名称
    @Column(name = "OTHER_ORGAN_COMPANY_NAME")
    private String otherOrganCompanyName;
    
    //组织证件类型（其他组织）
    @Column(name = "OTHER_ORGAN_CODE_TYPE")
    private String otherOrganCodeType;

    // 其他组织 组织机构代码
    @Column(name = "OTHER_ORGAN_CODE")
    private String otherOrganCode;

    // 其他组织 统一社会信用代码
    @Column(name = "OTHER_CREDIT_CODE")
    private String otherCreditCode;

    // 其他组织机构地址
    @Column(name = "OTHER_ORGAN_ADDRESS")
    private String otherOrganAddress;

    // 是否提交
    @Column(name = "IS_SUBMIT")
    private Integer isSubmit;

    // 是否个体户
    @Column(name = "IS_SELFEMPLOYED")
    private Integer isSelfemployed;

    // 字号(是个体户属性)
    @Column(name = "SELFEMPLOYED_CODE")
    private String selfemployedCode;

    // 营业执照号(是个体户属性)
    @Column(name = "SELFEMPLOYED_CHARTERED_CODE")
    private String selfemployedCharteredCode;

    // 代码类型(是个体户属性)
    @Column(name = "SELFEMPLOYED_CHARTERED_TYPE")
    private String selfemployedCharteredType;
    
    // 营业地址(是个体户属性)
    @Column(name = "SELFEMPLOYED_ADDRESS")
    private String selfemployedAddress;

    // 创建时间
    @Column(name = "CREATE_DATE")
    private Date createDate;

    // 数据来源（0系统录入，1接口对接）
    @Column(name = "DATA_SOURCE")
    private Integer dataSource;

    // 提交日期
    @Column(name = "SUBMIT_DATE")
    private Date submitDate;

    // 更新日期
    @Column(name = "UPDATE_DATE")
    private Date updateDate;

    // 删除标识（0未删除，1已删除）
    @Column(name = "IS_DELETE")
    private Integer isDelete;

    // 删除日期
    @Column(name = "DELETE_DATE")
    private Date deleteDate;

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

    public Date getEnforcementDate() {
        return enforcementDate;
    }

    public void setEnforcementDate(Date enforcementDate) {
        this.enforcementDate = enforcementDate;
    }

    public Date getRegistrantDate() {
        return registrantDate;
    }

    public void setRegistrantDate(Date registrantDate) {
        this.registrantDate = registrantDate;
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

    public Date getPunishmentDate() {
        return punishmentDate;
    }

    public void setPunishmentDate(Date punishmentDate) {
        this.punishmentDate = punishmentDate;
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

    public Date getReconsiderationDate() {
        return reconsiderationDate;
    }

    public void setReconsiderationDate(Date reconsiderationDate) {
        this.reconsiderationDate = reconsiderationDate;
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

    public Date getLawsuitDate() {
        return lawsuitDate;
    }

    public void setLawsuitDate(Date lawsuitDate) {
        this.lawsuitDate = lawsuitDate;
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

    public Date getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
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

    public Date getFilingDate() {
        return filingDate;
    }

    public void setFilingDate(Date filingDate) {
        this.filingDate = filingDate;
    }

    public Date getPunishDecisionExecutDate() {
        return punishDecisionExecutDate;
    }

    public void setPunishDecisionExecutDate(Date punishDecisionExecutDate) {
        this.punishDecisionExecutDate = punishDecisionExecutDate;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getDataSource() {
        return dataSource;
    }

    public void setDataSource(Integer dataSource) {
        this.dataSource = dataSource;
    }

	public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
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

}
