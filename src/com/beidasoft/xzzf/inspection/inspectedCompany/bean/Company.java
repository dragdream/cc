package com.beidasoft.xzzf.inspection.inspectedCompany.bean;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 企业基础信息实体类
 */
@Entity
@Table(name="XY_COMPANY")
public class Company {
    // 企业唯一ID
    @Id
    @Column(name = "ID")
    private String id;

    // 组织机构代码
    @Column(name = "ORG_CODE")
    private String orgCode;

    // 企业名称
    @Column(name = "ORG_NAME")
    private String orgName;

    // 行政区划
    @Column(name = "REG_DISTRICT_DIC")
    private String regDistrictDic;

    // 企业类型
    @Column(name = "ORG_TYPE_DIC")
    private String orgTypeDic;

    // 组织机构地址
    @Column(name = "REG_ADDR")
    private String regAddr;

    // 注册号
    @Column(name = "REG_NO")
    private String regNo;

    // 法人代表
    @Column(name = "LEGAL_REPRE")
    private String legalRepre;

    // 联系电话
    @Column(name = "LEGAL_REPRE_TEL")
    private String legalRepreTel;

    // 经营期限至
    @Column(name = "BIZ_INDATE")
    private Date bizIndate;

    // 证书有效期止
    @Column(name = "CERT_INDATE")
    private Date certIndate;

    // 证书发证日期
    @Column(name = "CERT_ISSUE_DATE")
    private Date certIssueDate;

    // 发证机构
    @Column(name = "CERT_ISSUE_ORG_NAME")
    private String certIssueOrgName;

    // 税务登记证号
    @Column(name = "TAX_REGIST_NO")
    private String taxRegistNo;

    // 是否再用
    @Column(name = "ORG0199")
    private String org0199;

    // 监管单位
    @Column(name = "JGDW")
    private String jgdw;

    // 写入日趋
    @Column(name = "XRRQ")
    private Date xrrq;

    // 信用等级
    @Column(name = "CREDIT_LEVEL")
    private String creditLevel;

    // 抽取影响值
    @Column(name = "INFLUENCE_VALUE")
    private String influenceValue;

    // 企业信用代码
    @Column(name = "CREDIT_CODE")
    private String creditCode;

    // 统一社会信用代码
    @Column(name = "UNI_SCID")
    private String uniScid;

    // 注册地址邮政编码
    @Column(name = "REG_ZIP_CODE")
    private String regZipCode;

    // 经济类型代码
    @Column(name = "ECO_TYPE_DIC")
    private String ecoTypeDic;

    // 职工人数
    @Column(name = "WORKCOUNT")
    private String workcount;

    // 经办人或联络人证件号码
    @Column(name = "CONTACT_NO")
    private String contactNo;

    // 经办人或联络人移动电话
    @Column(name = "CONTACT_PHONE")
    private String contactPhone;

    // 网址
    @Column(name = "ORG_WEBSITE")
    private String orgWebsite;

    // 核算方式（核算形式代码（1 独立核算  2 非独立核算））
    @Column(name = "COMPUTE_TYPE")
    private String computeType;

    // 更新时间戳
    @Column(name = "UPDATEDATE")
    private Date updatedate;

    // 纳税人名称
    @Column(name = "TAX_NAME")
    private String taxName;

    // 税务登记类型
    @Column(name = "TAX_TYPE")
    private String taxType;

    // 经营地址
    @Column(name = "TAX_ADDR")
    private String taxAddr;

    // 经营地址联系电话
    @Column(name = "TAX_ADDR_PHONE")
    private String taxAddrPhone;

    // 纳税人状态
    @Column(name = "TAX_PERSON_STATE")
    private String taxPersonState;

    // 所处街乡代码
    @Column(name = "TAX_DISTRICT_DIC")
    private String taxDistrictDic;

    // 原机构类型
    @Column(name = "ORIGINAL_ORG_TYPE")
    private String originalOrgType;

    // 经办人或联络人证件类型
    @Column(name = "CONTACT_TYPE_DIC")
    private String contactTypeDic;

    // 代码状态
    @Column(name = "ORG_STATE")
    private String orgState;

    // 货币种类
    @Column(name = "CURRENCY_TYPE_DIC")
    private String currencyTypeDic;

    // 经济行业代码
    @Column(name = "ECO_VOCAT_TYPE_DIC")
    private String ecoVocatTypeDic;

    // 法定代表人或负责人证件类型
    @Column(name = "LEGAL_TYPE_DIC")
    private String legalTypeDic;

    // 机构类型
    @Column(name = "ORG_TYPE")
    private String orgType;

    // 生产经营地址
    @Column(name = "ORIGIN_ADDR")
    private String originAddr;

    // 生产经营地址行政区划代码
    @Column(name = "ORIGIN_ZIP_CODE")
    private String originZipCode;

    // 外方投资国别
    @Column(name = "CONTRY")
    private String contry;

    // 变更日期（组织机构审核并通过日期）
    @Column(name = "CHANGEDATE")
    private Date changedate;

    // 税务登记日期
    @Column(name = "TAX_REGIST_DATE")
    private Date taxRegistDate;

    // 注（吊）销日期
    @Column(name = "DELETE_DATE")
    private Date deleteDate;

    // 核发终止日期
    @Column(name = "CONFIG_END_DATE")
    private Date configEndDate;

    // 核发起始日期
    @Column(name = "CONFIG_START_DATE")
    private Date configStartDate;

    // 最后变更日期（入库时间）
    @Column(name = "LAST_CHANGE_DATE")
    private Date lastChangeDate;

    // 成立日期
    @Column(name = "ORG_STARTDATE")
    private Date orgStartdate;

    // 法人移动电话
    @Column(name = "LEGAL_PHONE")
    private String legalPhone;

    // 发证机构
    @Column(name = "CERT_ORG")
    private String certOrg;

    // 经营地址邮编
    @Column(name = "TAX_ZIP_CODE")
    private String taxZipCode;

    // 批准机构代码
    @Column(name = "PARENTMANAGE_ORG_CODE")
    private String parentmanageOrgCode;

    // 主管部门代码
    @Column(name = "MANAGE_ORG_CODE")
    private String manageOrgCode;

    // 批准机构名称
    @Column(name = "PARENTMANAGE_ORG_NAME")
    private String parentmanageOrgName;

    // 经办人姓名
    @Column(name = "CONTACTNAME")
    private String contactname;

    // 机构邮箱
    @Column(name = "EMAIL")
    private String email;

    // 经营范围
    @Column(name = "BIZ_SCOPE")
    private String bizScope;

    // 法定代表人或负责人证件号码
    @Column(name = "LEGAL_NO")
    private String legalNo;

    // 注册资金
    @Column(name = "REGIST_FUND")
    private String registFund;

    // 主管部门名称
    @Column(name = "MANAGE_ORG_NAME")
    private String manageOrgName;

    // 机构电话
    @Column(name = "PHONE")
    private String phone;

    // 导入数据日期
    @Column(name = "CREATE_DATE")
    private Date createDate;

    // 组织机构ID
    @Column(name = "JGDM00")
    private String jgdm00;

    // 删除时间
    @Column(name = "ORG0193")
    private Date org0193;
    
    // 删除标记
    @Column(name = "DEL_FLG")
    private String delFlg;

    // 更新时间
    @Column(name = "UPDATE_TIME")
    private Date updateTime;
    
    // 权重
    @Column(name = "WEIGHT")
    private String weight;
    
    //领域类型
    @Column(name = "FIELD_TYPE")
    private String fieldType;
    

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getRegDistrictDic() {
		return regDistrictDic;
	}

	public void setRegDistrictDic(String regDistrictDic) {
		this.regDistrictDic = regDistrictDic;
	}

	public String getOrgTypeDic() {
		return orgTypeDic;
	}

	public void setOrgTypeDic(String orgTypeDic) {
		this.orgTypeDic = orgTypeDic;
	}

	public String getRegAddr() {
		return regAddr;
	}

	public void setRegAddr(String regAddr) {
		this.regAddr = regAddr;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getLegalRepre() {
		return legalRepre;
	}

	public void setLegalRepre(String legalRepre) {
		this.legalRepre = legalRepre;
	}

	public String getLegalRepreTel() {
		return legalRepreTel;
	}

	public void setLegalRepreTel(String legalRepreTel) {
		this.legalRepreTel = legalRepreTel;
	}

	public Date getBizIndate() {
		return bizIndate;
	}

	public void setBizIndate(Date bizIndate) {
		this.bizIndate = bizIndate;
	}

	public Date getCertIndate() {
		return certIndate;
	}

	public void setCertIndate(Date certIndate) {
		this.certIndate = certIndate;
	}

	public Date getCertIssueDate() {
		return certIssueDate;
	}

	public void setCertIssueDate(Date certIssueDate) {
		this.certIssueDate = certIssueDate;
	}

	public String getCertIssueOrgName() {
		return certIssueOrgName;
	}

	public void setCertIssueOrgName(String certIssueOrgName) {
		this.certIssueOrgName = certIssueOrgName;
	}

	public String getTaxRegistNo() {
		return taxRegistNo;
	}

	public void setTaxRegistNo(String taxRegistNo) {
		this.taxRegistNo = taxRegistNo;
	}

	public String getOrg0199() {
		return org0199;
	}

	public void setOrg0199(String org0199) {
		this.org0199 = org0199;
	}

	public String getJgdw() {
		return jgdw;
	}

	public void setJgdw(String jgdw) {
		this.jgdw = jgdw;
	}

	public Date getXrrq() {
		return xrrq;
	}

	public void setXrrq(Date xrrq) {
		this.xrrq = xrrq;
	}

	public String getCreditLevel() {
		return creditLevel;
	}

	public void setCreditLevel(String creditLevel) {
		this.creditLevel = creditLevel;
	}

	public String getInfluenceValue() {
		return influenceValue;
	}

	public void setInfluenceValue(String influenceValue) {
		this.influenceValue = influenceValue;
	}

	public String getCreditCode() {
		return creditCode;
	}

	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}

	public String getUniScid() {
		return uniScid;
	}

	public void setUniScid(String uniScid) {
		this.uniScid = uniScid;
	}

	public String getRegZipCode() {
		return regZipCode;
	}

	public void setRegZipCode(String regZipCode) {
		this.regZipCode = regZipCode;
	}

	public String getEcoTypeDic() {
		return ecoTypeDic;
	}

	public void setEcoTypeDic(String ecoTypeDic) {
		this.ecoTypeDic = ecoTypeDic;
	}

	public String getWorkcount() {
		return workcount;
	}

	public void setWorkcount(String workcount) {
		this.workcount = workcount;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getOrgWebsite() {
		return orgWebsite;
	}

	public void setOrgWebsite(String orgWebsite) {
		this.orgWebsite = orgWebsite;
	}

	public String getComputeType() {
		return computeType;
	}

	public void setComputeType(String computeType) {
		this.computeType = computeType;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public String getTaxName() {
		return taxName;
	}

	public void setTaxName(String taxName) {
		this.taxName = taxName;
	}

	public String getTaxType() {
		return taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

	public String getTaxAddr() {
		return taxAddr;
	}

	public void setTaxAddr(String taxAddr) {
		this.taxAddr = taxAddr;
	}

	public String getTaxAddrPhone() {
		return taxAddrPhone;
	}

	public void setTaxAddrPhone(String taxAddrPhone) {
		this.taxAddrPhone = taxAddrPhone;
	}

	public String getTaxPersonState() {
		return taxPersonState;
	}

	public void setTaxPersonState(String taxPersonState) {
		this.taxPersonState = taxPersonState;
	}

	public String getTaxDistrictDic() {
		return taxDistrictDic;
	}

	public void setTaxDistrictDic(String taxDistrictDic) {
		this.taxDistrictDic = taxDistrictDic;
	}

	public String getOriginalOrgType() {
		return originalOrgType;
	}

	public void setOriginalOrgType(String originalOrgType) {
		this.originalOrgType = originalOrgType;
	}

	public String getContactTypeDic() {
		return contactTypeDic;
	}

	public void setContactTypeDic(String contactTypeDic) {
		this.contactTypeDic = contactTypeDic;
	}

	public String getOrgState() {
		return orgState;
	}

	public void setOrgState(String orgState) {
		this.orgState = orgState;
	}

	public String getCurrencyTypeDic() {
		return currencyTypeDic;
	}

	public void setCurrencyTypeDic(String currencyTypeDic) {
		this.currencyTypeDic = currencyTypeDic;
	}

	public String getEcoVocatTypeDic() {
		return ecoVocatTypeDic;
	}

	public void setEcoVocatTypeDic(String ecoVocatTypeDic) {
		this.ecoVocatTypeDic = ecoVocatTypeDic;
	}

	public String getLegalTypeDic() {
		return legalTypeDic;
	}

	public void setLegalTypeDic(String legalTypeDic) {
		this.legalTypeDic = legalTypeDic;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getOriginAddr() {
		return originAddr;
	}

	public void setOriginAddr(String originAddr) {
		this.originAddr = originAddr;
	}

	public String getOriginZipCode() {
		return originZipCode;
	}

	public void setOriginZipCode(String originZipCode) {
		this.originZipCode = originZipCode;
	}

	public String getContry() {
		return contry;
	}

	public void setContry(String contry) {
		this.contry = contry;
	}

	public Date getChangedate() {
		return changedate;
	}

	public void setChangedate(Date changedate) {
		this.changedate = changedate;
	}

	public Date getTaxRegistDate() {
		return taxRegistDate;
	}

	public void setTaxRegistDate(Date taxRegistDate) {
		this.taxRegistDate = taxRegistDate;
	}

	public Date getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	public Date getConfigEndDate() {
		return configEndDate;
	}

	public void setConfigEndDate(Date configEndDate) {
		this.configEndDate = configEndDate;
	}

	public Date getConfigStartDate() {
		return configStartDate;
	}

	public void setConfigStartDate(Date configStartDate) {
		this.configStartDate = configStartDate;
	}

	public Date getLastChangeDate() {
		return lastChangeDate;
	}

	public void setLastChangeDate(Date lastChangeDate) {
		this.lastChangeDate = lastChangeDate;
	}

	public Date getOrgStartdate() {
		return orgStartdate;
	}

	public void setOrgStartdate(Date orgStartdate) {
		this.orgStartdate = orgStartdate;
	}

	public String getLegalPhone() {
		return legalPhone;
	}

	public void setLegalPhone(String legalPhone) {
		this.legalPhone = legalPhone;
	}

	public String getCertOrg() {
		return certOrg;
	}

	public void setCertOrg(String certOrg) {
		this.certOrg = certOrg;
	}

	public String getTaxZipCode() {
		return taxZipCode;
	}

	public void setTaxZipCode(String taxZipCode) {
		this.taxZipCode = taxZipCode;
	}

	public String getParentmanageOrgCode() {
		return parentmanageOrgCode;
	}

	public void setParentmanageOrgCode(String parentmanageOrgCode) {
		this.parentmanageOrgCode = parentmanageOrgCode;
	}

	public String getManageOrgCode() {
		return manageOrgCode;
	}

	public void setManageOrgCode(String manageOrgCode) {
		this.manageOrgCode = manageOrgCode;
	}

	public String getParentmanageOrgName() {
		return parentmanageOrgName;
	}

	public void setParentmanageOrgName(String parentmanageOrgName) {
		this.parentmanageOrgName = parentmanageOrgName;
	}

	public String getContactname() {
		return contactname;
	}

	public void setContactname(String contactname) {
		this.contactname = contactname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBizScope() {
		return bizScope;
	}

	public void setBizScope(String bizScope) {
		this.bizScope = bizScope;
	}

	public String getLegalNo() {
		return legalNo;
	}

	public void setLegalNo(String legalNo) {
		this.legalNo = legalNo;
	}

	public String getRegistFund() {
		return registFund;
	}

	public void setRegistFund(String registFund) {
		this.registFund = registFund;
	}

	public String getManageOrgName() {
		return manageOrgName;
	}

	public void setManageOrgName(String manageOrgName) {
		this.manageOrgName = manageOrgName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getJgdm00() {
		return jgdm00;
	}

	public void setJgdm00(String jgdm00) {
		this.jgdm00 = jgdm00;
	}

	public Date getOrg0193() {
		return org0193;
	}

	public void setOrg0193(Date org0193) {
		this.org0193 = org0193;
	}

	public String getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
}
