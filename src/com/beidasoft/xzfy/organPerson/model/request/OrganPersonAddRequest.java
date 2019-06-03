package com.beidasoft.xzfy.organPerson.model.request;


import com.beidasoft.xzfy.base.model.request.Request;

public class OrganPersonAddRequest implements Request{

	//机关ID
	private String orgId;
	
	//机关名称
	private String orgName;
		
	//人员姓名
	private String personName;
	
	//性别
	private String sex;
	
	//身份证
	private String idCard;
	
	//人员编制编码 
	private String staffing;
	
	// 人员编制
	private String staffingName;
	
	//职级编码
	private String levelCode;
	
	//职级
	private String levelName;
	
	//学历
	private String educationCode;
	
	//学历
	private String educationName;
	
	
	//是否获取法律证书
	private String isLaw;
	
	//是否党员
	private String isParty;
	
	//电话
	private String phone;
	
	//邮箱
	private String email;
	
	@Override
	public void validate() {
		// TODO Auto-generated method stub
		
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

	
}
