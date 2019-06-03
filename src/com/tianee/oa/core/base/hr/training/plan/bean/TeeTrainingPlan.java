package com.tianee.oa.core.base.hr.training.plan.bean;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 招聘计划
 * 
 * @author wyw
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "HR_TRAINING_PLAN")
public class TeeTrainingPlan {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="HR_TRAINING_PLAN_seq_gen")
	@SequenceGenerator(name="HR_TRAINING_PLAN_seq_gen", sequenceName="HR_TRAINING_PLAN_seq")
	@Column(name = "SID")
	private int sid;// 自增id

	// 培训计划编号
	@Column(name = "PLAN_NO", nullable = true, length = 254)
	private String planNo;

	// 培训计划名称
	@Column(name = "PLAN_NAME", nullable = true, length = 254)
	private String planName;

	// 培训渠道;0-内部培训,1-渠道培训
	@Column(name = "PLAN_CHANNEL", nullable = true, length = 254)
	private String planChannel;

	// 培训预算
	@Column(name = "PLAN_COST")
	private double planCost = 0.0;

	// 开课时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "COURSE_START_TIME")
	private Date courseStartTime;

	// 开课结束时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "COURSE_END_TIME")
	private Date courseEndTime;

	// 审批人
	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name="IDX656dd498b7f849989d08ce152c7")
	@JoinColumn(name = "APPROVE_PERSON")
	private TeePerson approvePerson;

	// 审批时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "APPROVE_TIME")
	private Date approveTime;

	// 审批意见
	@Lob
	@Column(name = "APPROVE_COMMENT")
	private String approveComment;

	// 计划状态;0-新建（待审批）; 1-批准; 2-不批准
	@Column(name = "PLAN_STATUS", length = 1, columnDefinition = "INT default 0")
	private int planStatus = 0;

	// 计划培训人数
	@Column(name = "JOIN_NUM", columnDefinition = "INT default 0")
	private int joinNum = 0;

	// 参与培训部门,sid串，多个用逗号隔开
	@Lob
	@Column(name = "JOIN_DEPT")
	private String joinDept;

	// 参与培训人员,sid串，多个用逗号隔开
	@Lob
	@Column(name = "JOIN_PERSON")
	private String joinPerson;

	// 培训要求
	@Lob
	@Column(name = "PLAN_REQUIRES")
	private String planRequires;

	// 培训机构名称
	@Column(name = "INSTITUTION_NAME", nullable = true, length = 254)
	private String institutionName;

	// 培训机构相关信息
	@Lob
	@Column(name = "INSTITUTION_INFO")
	private String institutionInfo;

	// 培训机构联系人
	@Column(name = "INSTITUTION_CONTACT", nullable = true, length = 254)
	private String institutionContact;

	// 培训机构联系人相关信息
	@Lob
	@Column(name = "INSTITU_CONTACT_INFO")
	private String instituContactInfo;

	// 培训课程名称
	@Column(name = "COURSE_NAME", nullable = true, length = 254)
	private String courseName;

	// 主办部门
	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name="IDXeb9bdfaa5a604744982a4341a2c")
	@JoinColumn(name = "HOST_DEPARTMENTS")
	private TeeDepartment hostDepartments;

	// 负责人
	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name="IDX682f6f5fea3a41898ee5b1e18f8")
	@JoinColumn(name = "CHARGE_PERSON")
	private TeePerson chargePerson;

	// 总课时
	@Column(name = "COURSE_HOURS")
	private double courseHours=0;

	// 实际费用
	@Column(name = "COURSE_PAY")
	private double coursePay = 0.0;

	// 培训形式
	@Column(name = "COURSE_TYPES", nullable = true, length = 254)
	private String courseTypes;

	// 培训说明
	@Lob
	@Column(name = "DESCRIPTION")
	private String description;

	// 培训备注
	@Lob
	@Column(name = "REMARK")
	private String remark;

	// 培训地点
	@Column(name = "ADDRESS", nullable = true, length = 254)
	private String address;

	// 培训内容
	@Lob
	@Column(name = "CONTENT")
	private String content;

	// 创建者id
	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name="IDXd21dfb23546543ffad54a70cfeb")
	@JoinColumn(name = "CREATE_USER")
	private TeePerson createUser;

	// 创建时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME")
	private Date createTime;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getPlanNo() {
		return planNo;
	}

	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getPlanChannel() {
		return planChannel;
	}

	public void setPlanChannel(String planChannel) {
		this.planChannel = planChannel;
	}

	public double getPlanCost() {
		return planCost;
	}

	public void setPlanCost(double planCost) {
		this.planCost = planCost;
	}

	public Date getCourseStartTime() {
		return courseStartTime;
	}

	public void setCourseStartTime(Date courseStartTime) {
		this.courseStartTime = courseStartTime;
	}

	public Date getCourseEndTime() {
		return courseEndTime;
	}

	public void setCourseEndTime(Date courseEndTime) {
		this.courseEndTime = courseEndTime;
	}

	public TeePerson getApprovePerson() {
		return approvePerson;
	}

	public void setApprovePerson(TeePerson approvePerson) {
		this.approvePerson = approvePerson;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public String getApproveComment() {
		return approveComment;
	}

	public void setApproveComment(String approveComment) {
		this.approveComment = approveComment;
	}

	public int getPlanStatus() {
		return planStatus;
	}

	public void setPlanStatus(int planStatus) {
		this.planStatus = planStatus;
	}

	public int getJoinNum() {
		return joinNum;
	}

	public void setJoinNum(int joinNum) {
		this.joinNum = joinNum;
	}

	public String getJoinDept() {
		return joinDept;
	}

	public void setJoinDept(String joinDept) {
		this.joinDept = joinDept;
	}

	public String getJoinPerson() {
		return joinPerson;
	}

	public void setJoinPerson(String joinPerson) {
		this.joinPerson = joinPerson;
	}

	public String getPlanRequires() {
		return planRequires;
	}

	public void setPlanRequires(String planRequires) {
		this.planRequires = planRequires;
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	public String getInstitutionInfo() {
		return institutionInfo;
	}

	public void setInstitutionInfo(String institutionInfo) {
		this.institutionInfo = institutionInfo;
	}

	public String getInstitutionContact() {
		return institutionContact;
	}

	public void setInstitutionContact(String institutionContact) {
		this.institutionContact = institutionContact;
	}

	public String getInstituContactInfo() {
		return instituContactInfo;
	}

	public void setInstituContactInfo(String instituContactInfo) {
		this.instituContactInfo = instituContactInfo;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public TeeDepartment getHostDepartments() {
		return hostDepartments;
	}

	public void setHostDepartments(TeeDepartment hostDepartments) {
		this.hostDepartments = hostDepartments;
	}

	public TeePerson getChargePerson() {
		return chargePerson;
	}

	public void setChargePerson(TeePerson chargePerson) {
		this.chargePerson = chargePerson;
	}


	public double getCourseHours() {
		return courseHours;
	}

	public void setCourseHours(double courseHours) {
		this.courseHours = courseHours;
	}

	public double getCoursePay() {
		return coursePay;
	}

	public void setCoursePay(double coursePay) {
		this.coursePay = coursePay;
	}

	public String getCourseTypes() {
		return courseTypes;
	}

	public void setCourseTypes(String courseTypes) {
		this.courseTypes = courseTypes;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public TeePerson getCreateUser() {
		return createUser;
	}

	public void setCreateUser(TeePerson createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
