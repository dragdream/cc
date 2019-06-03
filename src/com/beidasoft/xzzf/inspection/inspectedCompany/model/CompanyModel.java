package com.beidasoft.xzzf.inspection.inspectedCompany.model;

import java.util.Date;

public class CompanyModel {
	// 企业唯一ID
    private String id;

    // 组织机构代码
    private String orgCode;

    // 企业名称
    private String orgName;

    // 行政区划
    private String regDistrictDic;

    // 企业类型
    private String orgTypeDic;

    // 组织机构地址
    private String regAddr;

    // 注册号
    private String regNo;

    // 法人代表
    private String legalRepre;

    // 联系电话
    private String legalRepreTel;

    // 经营期限至
    private String bizIndateStr;

    // 证书有效期止
    private String certIndateStr;

    // 证书发证日期
    private String certIssueDateStr;

    // 发证机构
    private String certIssueOrgName;

    // 税务登记证号
    private String taxRegistNo;

    // 是否再用
    private String org0199;

    // 监管单位
    private String jgdw;

    // 写入日期
    private String xrrqStr;

    // 信用等级
    private String creditLevel;

    // 抽取影响值
    private String influenceValue;

    // 企业信用代码
    private String creditCode;

    // 统一社会信用代码
    private String uniScid;

    // 注册地址邮政编码
    private String regZipCode;

    // 经济类型代码
    private String ecoTypeDic;

    // 职工人数
    private String workcount;

    // 经办人或联络人证件号码
    private String contactNo;

    // 经办人或联络人移动电话
    private String contactPhone;

    // 网址
    private String orgWebsite;

    // 核算方式（核算形式代码（1 独立核算  2 非独立核算））
    private String computeType;

    // 更新时间戳
    private String updatedateStr;

    // 纳税人名称
    private String taxName;

    // 税务登记类型
    private String taxType;

    // 经营地址
    private String taxAddr;

    // 经营地址联系电话
    private String taxAddrPhone;

    // 纳税人状态
    private String taxPersonState;

    // 所处街乡代码
    private String taxDistrictDic;

    // 原机构类型
    private String originalOrgType;

    // 经办人或联络人证件类型
    private String contactTypeDic;

    // 代码状态
    private String orgState;

    // 货币种类
    private String currencyTypeDic;

    // 经济行业代码
    private String ecoVocatTypeDic;

    // 法定代表人或负责人证件类型
    private String legalTypeDic;

    // 机构类型
    private String orgType;

    // 生产经营地址
    private String originAddr;

    // 生产经营地址行政区划代码
    private String originZipCode;

    // 外方投资国别
    private String contry;

    // 变更日期（组织机构审核并通过日期）
    private String changedateStr;

    // 税务登记日期
    private String taxRegistDateStr;

    // 注（吊）销日期
    private String deleteDateStr;

    // 核发终止日期
    private String configEndDateStr;

    // 核发起始日期
    private String configStartDateStr;

    // 最后变更日期（入库时间）
    private String lastChangeDateStr;

    // 成立日期
    private String orgStartdateStr;

    // 法人移动电话
    private String legalPhone;

    // 发证机构
    private String certOrg;

    // 经营地址邮编
    private String taxZipCode;

    // 批准机构代码
    private String parentmanageOrgCode;

    // 主管部门代码
    private String manageOrgCode;

    // 批准机构名称
    private String parentmanageOrgName;

    // 经办人姓名
    private String contactname;

    // 机构邮箱
    private String email;

    // 经营范围
    private String bizScope;

    // 法定代表人或负责人证件号码
    private String legalNo;

    // 注册资金
    private String registFund;

    // 主管部门名称
    private String manageOrgName;

    // 机构电话
    private String phone;

    // 组织机构ID
    private String jgdm00;

    // 删除时间
    private String org0193Str;
    
    // 权重
    private String weight;
    
    //领域类型
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

	public String getBizIndateStr() {
		return bizIndateStr;
	}

	public void setBizIndateStr(String bizIndateStr) {
		this.bizIndateStr = bizIndateStr;
	}

	public String getCertIndateStr() {
		return certIndateStr;
	}

	public void setCertIndateStr(String certIndateStr) {
		this.certIndateStr = certIndateStr;
	}

	public String getCertIssueDateStr() {
		return certIssueDateStr;
	}

	public void setCertIssueDateStr(String certIssueDateStr) {
		this.certIssueDateStr = certIssueDateStr;
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

	public String getXrrqStr() {
		return xrrqStr;
	}

	public void setXrrqStr(String xrrqStr) {
		this.xrrqStr = xrrqStr;
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

	public String getUpdatedateStr() {
		return updatedateStr;
	}

	public void setUpdatedateStr(String updatedateStr) {
		this.updatedateStr = updatedateStr;
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

	public String getChangedateStr() {
		return changedateStr;
	}

	public void setChangedateStr(String changedateStr) {
		this.changedateStr = changedateStr;
	}

	public String getTaxRegistDateStr() {
		return taxRegistDateStr;
	}

	public void setTaxRegistDateStr(String taxRegistDateStr) {
		this.taxRegistDateStr = taxRegistDateStr;
	}

	public String getDeleteDateStr() {
		return deleteDateStr;
	}

	public void setDeleteDateStr(String deleteDateStr) {
		this.deleteDateStr = deleteDateStr;
	}

	public String getConfigEndDateStr() {
		return configEndDateStr;
	}

	public void setConfigEndDateStr(String configEndDateStr) {
		this.configEndDateStr = configEndDateStr;
	}

	public String getConfigStartDateStr() {
		return configStartDateStr;
	}

	public void setConfigStartDateStr(String configStartDateStr) {
		this.configStartDateStr = configStartDateStr;
	}

	public String getLastChangeDateStr() {
		return lastChangeDateStr;
	}

	public void setLastChangeDateStr(String lastChangeDateStr) {
		this.lastChangeDateStr = lastChangeDateStr;
	}

	public String getOrgStartdateStr() {
		return orgStartdateStr;
	}

	public void setOrgStartdateStr(String orgStartdateStr) {
		this.orgStartdateStr = orgStartdateStr;
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

	public String getJgdm00() {
		return jgdm00;
	}

	public void setJgdm00(String jgdm00) {
		this.jgdm00 = jgdm00;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getOrg0193Str() {
		return org0193Str;
	}

	public void setOrg0193Str(String org0193Str) {
		this.org0193Str = org0193Str;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

}
