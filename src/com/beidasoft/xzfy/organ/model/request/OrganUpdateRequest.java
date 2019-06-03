package com.beidasoft.xzfy.organ.model.request;

import com.beidasoft.xzfy.base.model.request.Request;
import com.beidasoft.xzfy.utils.ValidateUtils;


public class OrganUpdateRequest implements Request{

	private String orgId;
	
	//组织机构名称
	private String orgName;
	
	//组织机构编码
	private String orgCode;
	
	//组织机构层级编码
	private String orgLevelCode;
	
	//组织机构层级名称
	private String orgLevelName;
	
	//法人
	private String legalRepresentative;
	
	//编制人数
	private String compilersNum;
	
	//联系人
	private String contacts;
	
	//联系人电话
	private String contactsPhone;
	
	//传真
	private String fax;
	
	//邮政编码
	private String areaCode;
	
	//地址
	private String address;
	
	//备注
	private String remark;
	
	//上级机关ID
	private String parentId;
	
	//上级机关名称
	private String parentName;
	
	//部门ID
	private String deptId;

	@Override
	public void validate() {
		
		//校验非空
		ValidateUtils.validateEmpty(orgId);
		
		//校验特殊字符
		ValidateUtils.validateSpecialChar(orgId);
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgLevelCode() {
		return orgLevelCode;
	}

	public void setOrgLevelCode(String orgLevelCode) {
		this.orgLevelCode = orgLevelCode;
	}

	public String getOrgLevelName() {
		return orgLevelName;
	}

	public void setOrgLevelName(String orgLevelName) {
		this.orgLevelName = orgLevelName;
	}

	public String getLegalRepresentative() {
		return legalRepresentative;
	}

	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}

	public String getCompilersNum() {
		return compilersNum;
	}

	public void setCompilersNum(String compilersNum) {
		this.compilersNum = compilersNum;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getContactsPhone() {
		return contactsPhone;
	}

	public void setContactsPhone(String contactsPhone) {
		this.contactsPhone = contactsPhone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

}
