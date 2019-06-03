package com.tianee.oa.core.base.pm.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;


/**
 * 基本档案model
 *
 */
public class TeeHumanDocModel {
	private int sid;//主键
	private String personName;//人员姓名
	private String gender;//性别
	private String nativePlace;//籍贯
	private String ethnicity;//民族
	private String isOaUser;
	private int salaryLevel;//职级工资等级
	public String getIsOaUser() {
		return isOaUser;
	}
	public void setIsOaUser(String isOaUser) {
		this.isOaUser = isOaUser;
	}
	private int userId;//对应OA用户
	private int deptId;//所在部门
	private int roleId;//所属角色
	private String userName;
	private String deptIdName;
	private String roleName;
	private String salaryLevelModel;//职级工资模型
	private String statusType;//在职状态
	private String statusTypeDesc;//在职状态
	Map<String,String> map=new HashMap<String,String>();//自定义字段
	List<TeePmCustomerFieldModel> list=new ArrayList<TeePmCustomerFieldModel>();
	
	public List<TeePmCustomerFieldModel> getList() {
		return list;
	}
	public void setList(List<TeePmCustomerFieldModel> list) {
		this.list = list;
	}
	private List<TeeAttachmentModel> attachMentModel;
	public int getUserId() {
		return userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDeptIdName() {
		return deptIdName;
	}
	public void setDeptIdName(String deptIdName) {
		this.deptIdName = deptIdName;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getDeptId() {
		return deptId;
	}
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
	private String employeeType;//员工类型
	private String employeeTypeDesc;//员工类型
	private String codeNumber;//编号
	private String workNumber;//工号
	private String idCard;//身份证号
	private String englishName;//英文名
	private String birthdayDesc;
	private String marriage;//婚姻类型
	private String marriageDesc;//婚姻类型
	private String household;//户口类型
	private String householdDesc;//户口类型
	private String health;//健康状况
	private String householdPlace;//户口所在地
	private String politics;//政治面貌
	private String politicsDesc;//政治面貌
	private String joinPartyDateDesc;//入党时间
	private String major;//主修
	private String graduateDateDesc;
	private String educationDegree;//学历
	private String educationDegreeDesc;//学历
	private String degree;//学位
	private String degreeDesc;//学位
	private String graduateSchool;//毕业院校
	private String mobileNo;//手机号
	private String telNo;//电话
	private String email;//电子邮件
	private String qqNo;//qq号
	private String msn;//msn
	private String address;//住址
	private String otherAddress;//其他联系地址
	private String postState;//职务
	private String joinDateDesc;
	private int workYears;//工龄
	private int totalYears;//总工龄
	private String computerLevel;//计算机水平
	private String language1;//语种1
	private String language2;//语种2
	private String language3;//语种3
	private String skill;//特长
	private String postDesc;//职务情况
	private String shebaoDesc;//社保情况
	private String healthDesc;//体检情况
	private String remark;//备注
	
	private double defaultAnnualLeaveDays;//默认年假天数
	private int insuranceId;//保险ID
	private String insuranceName;//保险名称
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getNativePlace() {
		return nativePlace;
	}
	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}
	public String getEthnicity() {
		return ethnicity;
	}
	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}
	public String getStatusType() {
		return statusType;
	}
	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}
	public String getEmployeeType() {
		return employeeType;
	}
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}
	public String getCodeNumber() {
		return codeNumber;
	}
	public void setCodeNumber(String codeNumber) {
		this.codeNumber = codeNumber;
	}
	public String getWorkNumber() {
		return workNumber;
	}
	public void setWorkNumber(String workNumber) {
		this.workNumber = workNumber;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public String getBirthdayDesc() {
		return birthdayDesc;
	}
	public void setBirthdayDesc(String birthdayDesc) {
		this.birthdayDesc = birthdayDesc;
	}
	public String getMarriage() {
		return marriage;
	}
	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}
	public String getHousehold() {
		return household;
	}
	public void setHousehold(String household) {
		this.household = household;
	}
	public String getHealth() {
		return health;
	}
	public void setHealth(String health) {
		this.health = health;
	}
	public String getHouseholdPlace() {
		return householdPlace;
	}
	public void setHouseholdPlace(String householdPlace) {
		this.householdPlace = householdPlace;
	}
	public String getPolitics() {
		return politics;
	}
	public void setPolitics(String politics) {
		this.politics = politics;
	}
	public String getJoinPartyDateDesc() {
		return joinPartyDateDesc;
	}
	public void setJoinPartyDateDesc(String joinPartyDateDesc) {
		this.joinPartyDateDesc = joinPartyDateDesc;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getGraduateDateDesc() {
		return graduateDateDesc;
	}
	public void setGraduateDateDesc(String graduateDateDesc) {
		this.graduateDateDesc = graduateDateDesc;
	}
	public String getEducationDegree() {
		return educationDegree;
	}
	public void setEducationDegree(String educationDegree) {
		this.educationDegree = educationDegree;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getGraduateSchool() {
		return graduateSchool;
	}
	public void setGraduateSchool(String graduateSchool) {
		this.graduateSchool = graduateSchool;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getQqNo() {
		return qqNo;
	}
	public void setQqNo(String qqNo) {
		this.qqNo = qqNo;
	}
	public String getMsn() {
		return msn;
	}
	public void setMsn(String msn) {
		this.msn = msn;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getOtherAddress() {
		return otherAddress;
	}
	public void setOtherAddress(String otherAddress) {
		this.otherAddress = otherAddress;
	}
	public String getPostState() {
		return postState;
	}
	public void setPostState(String postState) {
		this.postState = postState;
	}
	public String getJoinDateDesc() {
		return joinDateDesc;
	}
	public void setJoinDateDesc(String joinDateDesc) {
		this.joinDateDesc = joinDateDesc;
	}
	public int getWorkYears() {
		return workYears;
	}
	public void setWorkYears(int workYears) {
		this.workYears = workYears;
	}
	public int getTotalYears() {
		return totalYears;
	}
	public void setTotalYears(int totalYears) {
		this.totalYears = totalYears;
	}
	public String getComputerLevel() {
		return computerLevel;
	}
	public void setComputerLevel(String computerLevel) {
		this.computerLevel = computerLevel;
	}
	public String getLanguage1() {
		return language1;
	}
	public void setLanguage1(String language1) {
		this.language1 = language1;
	}
	public String getLanguage2() {
		return language2;
	}
	public void setLanguage2(String language2) {
		this.language2 = language2;
	}
	public String getLanguage3() {
		return language3;
	}
	public void setLanguage3(String language3) {
		this.language3 = language3;
	}
	public String getSkill() {
		return skill;
	}
	public void setSkill(String skill) {
		this.skill = skill;
	}
	public String getPostDesc() {
		return postDesc;
	}
	public void setPostDesc(String postDesc) {
		this.postDesc = postDesc;
	}
	public String getShebaoDesc() {
		return shebaoDesc;
	}
	public void setShebaoDesc(String shebaoDesc) {
		this.shebaoDesc = shebaoDesc;
	}
	public String getHealthDesc() {
		return healthDesc;
	}
	public void setHealthDesc(String healthDesc) {
		this.healthDesc = healthDesc;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatusTypeDesc() {
		return statusTypeDesc;
	}
	public void setStatusTypeDesc(String statusTypeDesc) {
		this.statusTypeDesc = statusTypeDesc;
	}
	public String getEmployeeTypeDesc() {
		return employeeTypeDesc;
	}
	public void setEmployeeTypeDesc(String employeeTypeDesc) {
		this.employeeTypeDesc = employeeTypeDesc;
	}
	public String getMarriageDesc() {
		return marriageDesc;
	}
	public void setMarriageDesc(String marriageDesc) {
		this.marriageDesc = marriageDesc;
	}
	public String getHouseholdDesc() {
		return householdDesc;
	}
	public void setHouseholdDesc(String householdDesc) {
		this.householdDesc = householdDesc;
	}
	public String getPoliticsDesc() {
		return politicsDesc;
	}
	public void setPoliticsDesc(String politicsDesc) {
		this.politicsDesc = politicsDesc;
	}
	public String getEducationDegreeDesc() {
		return educationDegreeDesc;
	}
	public void setEducationDegreeDesc(String educationDegreeDesc) {
		this.educationDegreeDesc = educationDegreeDesc;
	}
	public String getDegreeDesc() {
		return degreeDesc;
	}
	public void setDegreeDesc(String degreeDesc) {
		this.degreeDesc = degreeDesc;
	}
	public double getDefaultAnnualLeaveDays() {
		return defaultAnnualLeaveDays;
	}
	public void setDefaultAnnualLeaveDays(double defaultAnnualLeaveDays) {
		this.defaultAnnualLeaveDays = defaultAnnualLeaveDays;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public int getInsuranceId() {
		return insuranceId;
	}
	public void setInsuranceId(int insuranceId) {
		this.insuranceId = insuranceId;
	}
	public String getInsuranceName() {
		return insuranceName;
	}
	public void setInsuranceName(String insuranceName) {
		this.insuranceName = insuranceName;
	}
	public int getSalaryLevel() {
		return salaryLevel;
	}
	public void setSalaryLevel(int salaryLevel) {
		this.salaryLevel = salaryLevel;
	}
	public String getSalaryLevelModel() {
		return salaryLevelModel;
	}
	public void setSalaryLevelModel(String salaryLevelModel) {
		this.salaryLevelModel = salaryLevelModel;
	}
	public List<TeeAttachmentModel> getAttachMentModel() {
		return attachMentModel;
	}
	public void setAttachMentModel(List<TeeAttachmentModel> attachMentModel) {
		this.attachMentModel = attachMentModel;
	}
	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	
}
