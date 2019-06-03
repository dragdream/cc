package com.tianee.oa.core.base.hr.recruit.bean;
import org.hibernate.annotations.Index;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.base.hr.recruit.plan.bean.TeeRecruitPlan;

/**
 * 人才库
 * @author syl
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "HR_POOL")
public class TeeHrPool {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="HR_POOL_seq_gen")
	@SequenceGenerator(name="HR_POOL_seq_gen", sequenceName="HR_POOL_seq")
	@Column(name = "SID")
	private int sid;// 自增id
	
	// 计划招聘
	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name="IDX7f349a9dfd1b4258a894787f0d0")
	@JoinColumn(name = "PLAN_ID")
	private TeeRecruitPlan plan;
	

	@Column(name = "EMPLOYEE_NAME")
	private String employeeName;	// 应聘人姓名：

	@Column(name = "EMPLOYEE_SEX" , columnDefinition="char(1) default 0")
	private String employeeSex;	// 姓名
	

	@Column(name = "EMPLOYEE_STATUS" , columnDefinition="INT default 1")
	private int employeeStatus;	// 当前状态 1-未入职  -可以关联需求 2-已通过   //3-未通过  可以关联需  4-已发offer 5-已入职

	@Temporal(TemporalType.DATE)
	@Column(name = "EMPLOYEE_BIRTH" )
	private Date employeeBirth;	// 出生日期

	@Column(name = "EMPLOYEE_NATIONALITY" )
	private String employeeNationality;	// 民族
	
	@Column(name = "RESIDENCE_PLACE" )
	private String residencePlace;	// 现居住城市
	
	@Column(name = "EMPLOYEE_PHONE" )
	private String employeePhone;	// 联系电话
	  
	@Column(name = "EMPLOYEE_EMAIL" )
	private String employeeEmail;	// Email

	@Column(name = "EMPLOYEE_NATIVE_PLACE" )
	private String employeeNativePlace;	// 籍贯
	 
	@Column(name = "EMPLOYEE_NATIVE_PLACE2" )
	private String employeeNativePlace2;	// 籍贯
	 

	@Column(name = "EMPLOYEE_DOMICILE_PLACE" )
	private String employeeDomicilePlace;	// 户口所在地：

	@Column(name = "EMPLOYEE_MARITAL_STATUS" , columnDefinition="char(1) default 0")
	private String employeeMaritalStatus;	// 婚姻状况：0 -未婚 1-已婚 2-离异 3-丧偶

	@Column(name = "EMPLOYEE_POLITICAL_STATUS" , columnDefinition="char(1) default 0")
	private String employeePoliticalStatus;	// 政治面貌：0-无 1-群众 2-共青团员 3-中共党员  4-中共预备党员  5-民主党派 6-无党派人士
	
	@Column(name = "EMPLOYEE_HEALTH" )
	private String employeeHealth;	// 健康状况
	
	@Temporal(TemporalType.DATE)
	@Column(name = "JOB_BEGINNING" )
	private Date jobBeginning;	// 参加工作时间：

	@Column(name = "JOB_CATEGORY" , columnDefinition="char(1) default 0")
	private String jobCategory;	// 期望工作性质：0-无 -全职 1-全职 2-兼职< 3-临时 4-实习
	 
	
	@Column(name = "JOB_INDUSTRY" )
	private String jobIndustry;	// 期望从事行业：
	
	@Column(name = "JOB_INTENSION" )
	private String jobIntension;	// 期望从事职业：

	@Column(name = "WORK_CITY" )
	private String workCity;	// 期望工作城市：
	

	@Column(name = "EXPECTED_SALARY" )
	private double expectedSalary;	// 期望薪水(税前)：
	
	@Column(name = "POSITION" )
	private String  position;	// 岗位：
	
	@Column(name = "START_WORKING" , columnDefinition="char(1) default 0")
	private String startWorking;	// 期望工作性质：0-周以内 1-1个月内< 2-1~3个月 3-临时 4-随时到岗

	
	@Temporal(TemporalType.DATE)
	@Column(name = "GRADUATION_DATE" )
	private Date graduationDate;	//毕业时间：
	
	@Column(name = "GRADUATION_SCHOOL" )
	private String graduationSchool;//毕业学校：

	@Column(name = "EMPLOYEE_MAJOR" )
	private String employeeMajor;//所学专业：：

	
	@Column(name = "EMPLOYEE_HIGHEST_SCHOOL" )
	private String employeeHighestSchool;//学历
	@Column(name = "EMPLOYEE_HIGHEST_DEGREE" )
	private String employeeHighestDegree;//学位：
	
	
	@Column(name = "FOREIGN_LANGUAGE1" )
	private String foreignLanguage1;//外语语种1
	
	@Column(name = "FOREIGN_LANGUAGE2" )
	private String foreignLanguage2;//外语语种2
	
	@Column(name = "FOREIGN_LANGUAGE3" )
	private String foreignLanguage3;//外语语种3
	 
	@Column(name = "FOREIGN_LEVEL1" )
	private String foreignLevel1;//外语水平1
	
	@Column(name = "FOREIGN_LEVEL2" )
	private String foreignLevel2;//外语水平2
	@Column(name = "FOREIGN_LEVEL3" )
	private String foreignLevel3;//外语水平3


	@Column(name = "COMPUTER_LEVEL" )
	private String computerLevel;//计算机水平：
	
	
	@Column(name = "EMPLOYEE_AGE" )
	private String employeeAge;//年龄：
	

	@Column(name = "RECRU_CHANNEL" )
	private String recruChannel;//招聘渠道：
	
	
	@Column(name = "EMPLOYEE_SKILLS" )
	private String employeeSkills;//特长：
	 
	
	@Column(name = "CAREER_SKILLS" )
	private String chreerSkills;//职业技能：
	  
	@Lob
	@Column(name = "WORK_EXPERIENCE" )
	private String wordExperience;//工作经验：
	
	@Lob
	@Column(name = "PROJECT_EXPERIENCE" )
	private String projectExperience;//项目经验：
	
	@Lob
	@Column(name = "REMARK" )
	private String remark;//备注：
	
	
	@Lob
	@Column(name = "RESUME" )
	private String resume;//备注：
	
	@OneToOne
	private TeeAttachment attachemnt;// 照片上传
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME" )
	private Date createTime;	//创建时间

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeeRecruitPlan getPlan() {
		return plan;
	}

	public void setPlan(TeeRecruitPlan plan) {
		this.plan = plan;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public int getEmployeeStatus() {
		return employeeStatus;
	}

	public void setEmployeeStatus(int employeeStatus) {
		this.employeeStatus = employeeStatus;
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

	public TeeAttachment getAttachemnt() {
		return attachemnt;
	}

	public void setAttachemnt(TeeAttachment attachemnt) {
		this.attachemnt = attachemnt;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
	
}
