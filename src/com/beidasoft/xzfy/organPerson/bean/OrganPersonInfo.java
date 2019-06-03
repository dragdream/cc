package com.beidasoft.xzfy.organPerson.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FY_PEOPLE")
public class OrganPersonInfo {
	
	//机关ID
	@Column(name="ORG_ID")
	private String orgId;
	
	//机关名称
	@Column(name="ORG_NAME")
	private String orgName;
	
	//人员ID
	@Id
	@Column(name="PEOPLE_ID")
	private String personId;
	
	//人员姓名
	@Column(name="PEOPLE_NAME")
	private String personName;
	
	//性别
	@Column(name="SEX")
	private String sex;
	
	//身份证
	@Column(name="IDCARD")
	private String idCard;
	
	//人员编制编码
	@Column(name="STAFFING")
	private String staffing;
	
	//人员编制
	@Column(name="STAFFING_NAME")
	private String staffingName;
	
	//职级
	@Column(name="LEVEL_CODE")
	private String levelCode;
	
	//职级
	@Column(name="LEVEL_NAME")
	private String levelName;
	
	//学历
	@Column(name="EDUCATION_CODE")
	private String educationCode;
	
	//学历
	@Column(name="EDUCATION_NAME")
	private String educationName;
	
	//是否获取法律证书
	@Column(name="IS_LAW")
	private String isLaw;
	
	//是否党员
	@Column(name="IS_PARTY")
	private String isParty;
	
	//电话
	@Column(name="PHONE")
	private String phone;
	
	//邮箱
	@Column(name="EMAIL")
	private String email;
	
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

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getStaffing() {
		return staffing;
	}

	public void setStaffing(String staffing) {
		this.staffing = staffing;
	}

	public String getStaffingName() {
		return staffingName;
	}

	public void setStaffingName(String staffingName) {
		this.staffingName = staffingName;
	}

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getEducationCode() {
		return educationCode;
	}

	public void setEducationCode(String educationCode) {
		this.educationCode = educationCode;
	}

	public String getEducationName() {
		return educationName;
	}

	public void setEducationName(String educationName) {
		this.educationName = educationName;
	}

	public String getIsLaw() {
		return isLaw;
	}

	public void setIsLaw(String isLaw) {
		this.isLaw = isLaw;
	}

	public String getIsParty() {
		return isParty;
	}

	public void setIsParty(String isParty) {
		this.isParty = isParty;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
