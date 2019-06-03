package com.beidasoft.xzfy.organ.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "FY_ORGAN")
public class OrganInfo {

	//机关ID
	@Id
	@Column(name="org_id")
	private String orgId;
	
	//机关名称
	@Column(name="org_name")
	private String orgName;
	
	//机关编码
	@Column(name="org_code")
	private String orgCode;
	
	//机关层级编码
	@Column(name="org_level_code")
	private String orgLevelCode;
	
	//机关层级名称
	@Column(name="org_level_name")
	private String orgLevelName;
	
	//法人
	@Column(name="org_legal_representative")
	private String legalRepresentative;
	
	//编制人数
	@Column(name="compilers_num")
	private String compilersNum;
	
	//联系人
	@Column(name="contacts")
	private String contacts;
	
	//联系人电话
	@Column(name="contacts_phone")
	private String contactsPhone;
	
	//传真
	@Column(name="fax")
	private String fax;
	
	//邮政编码
	@Column(name="area_code")
	private String areaCode;
	
	//地址
	@Column(name="address")
	private String address;
	
	//备注
	@Column(name="remark")
	private String remark;
	
	//部门ID
	@Column(name="dept_id")
	private String deptId;
	
	//创建人
	@Column(name="CREATED_USER")
	private String createdUser;
	
	//创建人ID
	@Column(name="CREATED_USER_Id")
	private String createdUserId;
	
	//创建时间
	@Column(name="CREATED_TIME")
	private String createdTime;
	
	//更新人
	@Column(name="MODIFIED_USER")
	private String modifiedUser;
	
	//更新人Id 
	@Column(name="MODIFIED_USER_ID")
	private String modifiedUserId;
	
	//更新时间
	@Column(name="MODIFIED_TIME")
	private String modifiedTime;

	
	
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
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
	public String getCreatedUserId() {
		return createdUserId;
	}
	public void setCreatedUserId(String createdUserId) {
		this.createdUserId = createdUserId;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public String getModifiedUser() {
		return modifiedUser;
	}
	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}
	public String getModifiedUserId() {
		return modifiedUserId;
	}
	public void setModifiedUserId(String modifiedUserId) {
		this.modifiedUserId = modifiedUserId;
	}
	public String getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	
}
