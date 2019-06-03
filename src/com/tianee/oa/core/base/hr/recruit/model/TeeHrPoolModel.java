package com.tianee.oa.core.base.hr.recruit.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;

/**
 * 人才库
 * @author syl
 */

public class TeeHrPoolModel{

	private int sid;// 自增id
	
	private String planId;
	private String planName;//招聘计划名称
	private String planNo;//招聘计划编号
	
	private String employeeName;	// 应聘人姓名：
	private String employeeSex;	// 性别
	private String employeeSexDesc;	// 性别
	private int employeeStatus;	//	人员状态
	private String employeeStatusDesc;	//	人员状态
	private Date employeeBirth;	// 出生日期
	private String employeeBirthStr;
	
	private String employeeNationality;	// 民族
	private String residencePlace;	// 现居住城市
	private String employeePhone;	// 联系电话
	private String employeeEmail;	// Email
	private String employeeNativePlace;	// 籍贯
	private String employeeNativePlaceDesc;	// 籍贯
	private String employeeNativePlace2;	// 籍贯
	 
	private String employeeDomicilePlace;	// 户口所在地：
	private String employeeMaritalStatus;	// 婚姻状况：0 -未婚 1-已婚 2-离异 3-丧偶
	private String employeeMaritalStatusDesc;
	private String employeePoliticalStatus;	// 政治面貌 系统代码参数   ：0-无 1-群众 2-共青团员 3-中共党员  4-中共预备党员  5-民主党派 6-无党派人士
	private String employeePoliticalStatusDesc;	
	private String employeeHealth;	// 健康状况
	private Date jobBeginning;	// 参加工作时间：
	private String jobBeginningStr;//
	private String jobCategory;	// 期望工作性质  系统代码参数：0-无 -全职 1-全职 2-兼职< 3-临时 4-实习
	private String jobCategoryDesc;
/*	@Column(name = "ATTACHMENT" ,)
	private String ATTACHMENT;	// 照片上传：
*/	 
	private String jobIndustry;	// 期望从事行业：
	private String jobIntension;	// 期望从事职业：
	private String workCity;	// 期望工作城市：
	private double expectedSalary;	// 期望薪水(税前)：
	private String  position;	// 岗位： 系统代码参数
	private String  positionDesc;
	private String startWorking;	// 期望工作性质：0-周以内 1-1个月内< 2-1~3个月 3-临时 4-随时到岗
	private String startWorkingDesc;
	private Date graduationDate;	//毕业时间：
	private String graduationDateStr;	//毕业时间：
	private String graduationSchool;//毕业学校：
	private String employeeMajor;//所学专业：：Hr系统代码参数
	private String employeeMajorDesc;
	private String employeeHighestSchool;//学历 Hr系统代码参数
	private String employeeHighestSchoolDesc;
	private String employeeHighestDegree;//学位：
	private String employeeHighestDegreeDesc;
	private String foreignLanguage1;//外语语种1
	private String foreignLanguage2;//外语语种2
	private String foreignLanguage3;//外语语种3
	private String foreignLevel1;//外语水平1
	private String foreignLevel2;//外语水平2
	private String foreignLevel3;//外语水平3
	private String computerLevel;//计算机水平：
	private String employeeAge;//年龄：
	private String recruChannel;//招聘渠道： Hr系统代码参数
	private String recruChannelDesc;
	private String employeeSkills;//特长：
	 
	private String chreerSkills;//职业技能：
	  
	private String wordExperience;//工作经验：
	
	private String projectExperience;//项目经验：
	
	private String remark;//备注：
	
	private String resume;//简历
	private String createTimeStr;//创建时间

	private String attacheIds;
	private TeeAttachmentModel attachemntModel = new TeeAttachmentModel();//照片
	private List<TeeAttachmentModel> attachesModels = new ArrayList<TeeAttachmentModel>(0);//简历附件
	
	
	
	
	
	public String getPlanNo() {
		return planNo;
	}
	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public int getEmployeeStatus() {
		return employeeStatus;
	}
	public void setEmployeeStatus(int employeeStatus) {
		this.employeeStatus = employeeStatus;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeeSex() {
		return employeeSex;
	}
	public void setEmployeeSex(String employeeSex) {
		this.employeeSex = employeeSex;
	}
	public Date getEmployeeBirth() {
		return employeeBirth;
	}
	public void setEmployeeBirth(Date employeeBirth) {
		this.employeeBirth = employeeBirth;
	}
	public String getEmployeeNationality() {
		return employeeNationality;
	}
	public void setEmployeeNationality(String employeeNationality) {
		this.employeeNationality = employeeNationality;
	}
	public String getResidencePlace() {
		return residencePlace;
	}
	public void setResidencePlace(String residencePlace) {
		this.residencePlace = residencePlace;
	}
	public String getEmployeePhone() {
		return employeePhone;
	}
	public void setEmployeePhone(String employeePhone) {
		this.employeePhone = employeePhone;
	}
	public String getEmployeeEmail() {
		return employeeEmail;
	}
	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}
	public String getEmployeeNativePlace() {
		return employeeNativePlace;
	}
	public void setEmployeeNativePlace(String employeeNativePlace) {
		this.employeeNativePlace = employeeNativePlace;
	}
	public String getEmployeeNativePlace2() {
		return employeeNativePlace2;
	}
	public void setEmployeeNativePlace2(String employeeNativePlace2) {
		this.employeeNativePlace2 = employeeNativePlace2;
	}
	public String getEmployeeDomicilePlace() {
		return employeeDomicilePlace;
	}
	public void setEmployeeDomicilePlace(String employeeDomicilePlace) {
		this.employeeDomicilePlace = employeeDomicilePlace;
	}
	public String getEmployeeMaritalStatus() {
		return employeeMaritalStatus;
	}
	public void setEmployeeMaritalStatus(String employeeMaritalStatus) {
		this.employeeMaritalStatus = employeeMaritalStatus;
	}
	public String getEmployeePoliticalStatus() {
		return employeePoliticalStatus;
	}
	public void setEmployeePoliticalStatus(String employeePoliticalStatus) {
		this.employeePoliticalStatus = employeePoliticalStatus;
	}
	public String getEmployeeHealth() {
		return employeeHealth;
	}
	public void setEmployeeHealth(String employeeHealth) {
		this.employeeHealth = employeeHealth;
	}
	public Date getJobBeginning() {
		return jobBeginning;
	}
	public void setJobBeginning(Date jobBeginning) {
		this.jobBeginning = jobBeginning;
	}
	public String getJobCategory() {
		return jobCategory;
	}
	public void setJobCategory(String jobCategory) {
		this.jobCategory = jobCategory;
	}
	public String getJobIndustry() {
		return jobIndustry;
	}
	public void setJobIndustry(String jobIndustry) {
		this.jobIndustry = jobIndustry;
	}
	public String getJobIntension() {
		return jobIntension;
	}
	public void setJobIntension(String jobIntension) {
		this.jobIntension = jobIntension;
	}
	public String getWorkCity() {
		return workCity;
	}
	public void setWorkCity(String workCity) {
		this.workCity = workCity;
	}
	public double getExpectedSalary() {
		return expectedSalary;
	}
	public void setExpectedSalary(double expectedSalary) {
		this.expectedSalary = expectedSalary;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getStartWorking() {
		return startWorking;
	}
	public void setStartWorking(String startWorking) {
		this.startWorking = startWorking;
	}
	public Date getGraduationDate() {
		return graduationDate;
	}
	public void setGraduationDate(Date graduationDate) {
		this.graduationDate = graduationDate;
	}
	public String getGraduationSchool() {
		return graduationSchool;
	}
	public void setGraduationSchool(String graduationSchool) {
		this.graduationSchool = graduationSchool;
	}
	public String getEmployeeMajor() {
		return employeeMajor;
	}
	public void setEmployeeMajor(String employeeMajor) {
		this.employeeMajor = employeeMajor;
	}
	public String getEmployeeHighestSchool() {
		return employeeHighestSchool;
	}
	public void setEmployeeHighestSchool(String employeeHighestSchool) {
		this.employeeHighestSchool = employeeHighestSchool;
	}
	public String getEmployeeHighestDegree() {
		return employeeHighestDegree;
	}
	public void setEmployeeHighestDegree(String employeeHighestDegree) {
		this.employeeHighestDegree = employeeHighestDegree;
	}
	public String getForeignLanguage1() {
		return foreignLanguage1;
	}
	public void setForeignLanguage1(String foreignLanguage1) {
		this.foreignLanguage1 = foreignLanguage1;
	}
	public String getForeignLanguage2() {
		return foreignLanguage2;
	}
	public void setForeignLanguage2(String foreignLanguage2) {
		this.foreignLanguage2 = foreignLanguage2;
	}
	public String getForeignLanguage3() {
		return foreignLanguage3;
	}
	public void setForeignLanguage3(String foreignLanguage3) {
		this.foreignLanguage3 = foreignLanguage3;
	}
	public String getForeignLevel1() {
		return foreignLevel1;
	}
	public void setForeignLevel1(String foreignLevel1) {
		this.foreignLevel1 = foreignLevel1;
	}
	public String getForeignLevel2() {
		return foreignLevel2;
	}
	public void setForeignLevel2(String foreignLevel2) {
		this.foreignLevel2 = foreignLevel2;
	}
	public String getForeignLevel3() {
		return foreignLevel3;
	}
	public void setForeignLevel3(String foreignLevel3) {
		this.foreignLevel3 = foreignLevel3;
	}
	public String getComputerLevel() {
		return computerLevel;
	}
	public void setComputerLevel(String computerLevel) {
		this.computerLevel = computerLevel;
	}
	public String getEmployeeAge() {
		return employeeAge;
	}
	public void setEmployeeAge(String employeeAge) {
		this.employeeAge = employeeAge;
	}
	public String getRecruChannel() {
		return recruChannel;
	}
	public void setRecruChannel(String recruChannel) {
		this.recruChannel = recruChannel;
	}
	public String getEmployeeSkills() {
		return employeeSkills;
	}
	public void setEmployeeSkills(String employeeSkills) {
		this.employeeSkills = employeeSkills;
	}
	public String getChreerSkills() {
		return chreerSkills;
	}
	public void setChreerSkills(String chreerSkills) {
		this.chreerSkills = chreerSkills;
	}
	public String getWordExperience() {
		return wordExperience;
	}
	public void setWordExperience(String wordExperience) {
		this.wordExperience = wordExperience;
	}
	public String getProjectExperience() {
		return projectExperience;
	}
	public void setProjectExperience(String projectExperience) {
		this.projectExperience = projectExperience;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getResume() {
		return resume;
	}
	public void setResume(String resume) {
		this.resume = resume;
	}
	public TeeAttachmentModel getAttachemntModel() {
		return attachemntModel;
	}
	public void setAttachemntModel(TeeAttachmentModel attachemntModel) {
		this.attachemntModel = attachemntModel;
	}
	public List<TeeAttachmentModel> getAttachesModels() {
		return attachesModels;
	}
	public void setAttachesModels(List<TeeAttachmentModel> attachesModels) {
		this.attachesModels = attachesModels;
	}
	public String getAttacheIds() {
		return attacheIds;
	}
	public void setAttacheIds(String attacheIds) {
		this.attacheIds = attacheIds;
	}
	public String getEmployeeBirthStr() {
		return employeeBirthStr;
	}
	public void setEmployeeBirthStr(String employeeBirthStr) {
		this.employeeBirthStr = employeeBirthStr;
	}
	public String getJobBeginningStr() {
		return jobBeginningStr;
	}
	public void setJobBeginningStr(String jobBeginningStr) {
		this.jobBeginningStr = jobBeginningStr;
	}
	public String getGraduationDateStr() {
		return graduationDateStr;
	}
	public void setGraduationDateStr(String graduationDateStr) {
		this.graduationDateStr = graduationDateStr;
	}
	public String getEmployeeMaritalStatusDesc() {
		return employeeMaritalStatusDesc;
	}
	public void setEmployeeMaritalStatusDesc(String employeeMaritalStatusDesc) {
		this.employeeMaritalStatusDesc = employeeMaritalStatusDesc;
	}
	public String getEmployeePoliticalStatusDesc() {
		return employeePoliticalStatusDesc;
	}
	public void setEmployeePoliticalStatusDesc(String employeePoliticalStatusDesc) {
		this.employeePoliticalStatusDesc = employeePoliticalStatusDesc;
	}
	public String getJobCategoryDesc() {
		return jobCategoryDesc;
	}
	public void setJobCategoryDesc(String jobCategoryDesc) {
		this.jobCategoryDesc = jobCategoryDesc;
	}
	public String getPositionDesc() {
		return positionDesc;
	}
	public void setPositionDesc(String positionDesc) {
		this.positionDesc = positionDesc;
	}
	public String getEmployeeMajorDesc() {
		return employeeMajorDesc;
	}
	public void setEmployeeMajorDesc(String employeeMajorDesc) {
		this.employeeMajorDesc = employeeMajorDesc;
	}
	public String getEmployeeHighestSchoolDesc() {
		return employeeHighestSchoolDesc;
	}
	public void setEmployeeHighestSchoolDesc(String employeeHighestSchoolDesc) {
		this.employeeHighestSchoolDesc = employeeHighestSchoolDesc;
	}
	public String getEmployeeHighestDegreeDesc() {
		return employeeHighestDegreeDesc;
	}
	public void setEmployeeHighestDegreeDesc(String employeeHighestDegreeDesc) {
		this.employeeHighestDegreeDesc = employeeHighestDegreeDesc;
	}
	public String getRecruChannelDesc() {
		return recruChannelDesc;
	}
	public void setRecruChannelDesc(String recruChannelDesc) {
		this.recruChannelDesc = recruChannelDesc;
	}
	public String getEmployeeNativePlaceDesc() {
		return employeeNativePlaceDesc;
	}
	public void setEmployeeNativePlaceDesc(String employeeNativePlaceDesc) {
		this.employeeNativePlaceDesc = employeeNativePlaceDesc;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public String getEmployeeSexDesc() {
		return employeeSexDesc;
	}
	public void setEmployeeSexDesc(String employeeSexDesc) {
		this.employeeSexDesc = employeeSexDesc;
	}
	public String getStartWorkingDesc() {
		return startWorkingDesc;
	}
	public void setStartWorkingDesc(String startWorkingDesc) {
		this.startWorkingDesc = startWorkingDesc;
	}
	public String getEmployeeStatusDesc() {
		return employeeStatusDesc;
	}
	public void setEmployeeStatusDesc(String employeeStatusDesc) {
		this.employeeStatusDesc = employeeStatusDesc;
	}
}
