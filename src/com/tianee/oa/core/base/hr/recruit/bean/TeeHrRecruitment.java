package com.tianee.oa.core.base.hr.recruit.bean;
import org.hibernate.annotations.Index;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tianee.oa.core.base.hr.recruit.plan.bean.TeeRecruitPlan;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 招聘录用
 * 
 * @author wyw
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "HR_RECRUITMENT")
public class TeeHrRecruitment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="HR_RECRUITMENT_seq_gen")
	@SequenceGenerator(name="HR_RECRUITMENT_seq_gen", sequenceName="HR_RECRUITMENT_seq")
	@Column(name = "SID")
	private int sid;// 自增id

	// 计划招聘
	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name="IDX0879370ad5734696bb14e34f7f8")
	@JoinColumn(name = "PLAN_ID")
	private TeeRecruitPlan planObj;

	// 人才库 （应聘者）
	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name="IDX1aadc8b9ee224ec3bc3f2ac8da2")
	@JoinColumn(name = "POOL_ID")
	private TeeHrPool poolObj;

	// 招聘岗位
	@Column(name = "Position")
	private String position;

	// OA中用户名
	@Column(name = "OA_NAME")
	private String oaName;

	// 录用负责人
	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name="IDXe41d79ca5e6d444d852f727c57d")
	@JoinColumn(name = "RECORD_PERSON")
	private TeePerson recordPerson;

	// 录入日期
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECORD_TIME")
	private Date recordTime;

	// 招聘部门
	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name="IDX7049ca9f16b44f9aad402b49351")
	@JoinColumn(name = "REQU_DEPT")
	private TeeDepartment requDept;

	// 员工类型
	@Column(name = "EMPLOYEE_TYPE")
	private String employeeType;// 如：01-合同工；02-正式员工

	// 行政等级
	@Column(name = "ADMINISTRATION_LEVEL")
	private String administrationLevel;

	// 职务
	@Column(name = "JOB_POSITION")
	private String jobPosition;

	// 职称
	@Column(name = "PRESENT_POSITION")
	private String presentPosition;

	// 正式入职时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ON_BOARDING_TIME")
	private Date onBoardingTime;

	// 正式起薪时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "STARTING_SALARY_TIME")
	private Date startingSalaryTime;

	// 备注
	@Lob
	@Column(name = "REMARK")
	private String remark;

	// 创建时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME")
	private Date createTime;// 创建时间

	// 创建者id
	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name="IDXbacb6f9fc93841108bc4ab67656")
	@JoinColumn(name = "CREATE_USER")
	private TeePerson createUser;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	

	public TeeRecruitPlan getPlanObj() {
		return planObj;
	}

	public void setPlanObj(TeeRecruitPlan planObj) {
		this.planObj = planObj;
	}

	public TeeHrPool getPoolObj() {
		return poolObj;
	}

	public void setPoolObj(TeeHrPool poolObj) {
		this.poolObj = poolObj;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getOaName() {
		return oaName;
	}

	public void setOaName(String oaName) {
		this.oaName = oaName;
	}

	public TeePerson getRecordPerson() {
		return recordPerson;
	}

	public void setRecordPerson(TeePerson recordPerson) {
		this.recordPerson = recordPerson;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public TeeDepartment getRequDept() {
		return requDept;
	}

	public void setRequDept(TeeDepartment requDept) {
		this.requDept = requDept;
	}

	public String getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	public String getAdministrationLevel() {
		return administrationLevel;
	}

	public void setAdministrationLevel(String administrationLevel) {
		this.administrationLevel = administrationLevel;
	}

	public String getJobPosition() {
		return jobPosition;
	}

	public void setJobPosition(String jobPosition) {
		this.jobPosition = jobPosition;
	}

	public String getPresentPosition() {
		return presentPosition;
	}

	public void setPresentPosition(String presentPosition) {
		this.presentPosition = presentPosition;
	}

	public Date getOnBoardingTime() {
		return onBoardingTime;
	}

	public void setOnBoardingTime(Date onBoardingTime) {
		this.onBoardingTime = onBoardingTime;
	}

	public Date getStartingSalaryTime() {
		return startingSalaryTime;
	}

	public void setStartingSalaryTime(Date startingSalaryTime) {
		this.startingSalaryTime = startingSalaryTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public TeePerson getCreateUser() {
		return createUser;
	}

	public void setCreateUser(TeePerson createUser) {
		this.createUser = createUser;
	}
	
	
	
	
	

}
