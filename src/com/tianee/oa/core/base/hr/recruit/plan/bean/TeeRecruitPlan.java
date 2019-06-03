package com.tianee.oa.core.base.hr.recruit.plan.bean;
import org.hibernate.annotations.Index;

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
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tianee.oa.core.base.hr.recruit.bean.TeeHrPool;
import com.tianee.oa.core.base.hr.recruit.bean.TeeHrRecruitment;
import com.tianee.oa.core.base.hr.recruit.requirements.bean.TeeRecruitRequirements;
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
@Table(name = "HR_RECRUIT_PLAN")
public class TeeRecruitPlan {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="HR_RECRUIT_PLAN_seq_gen")
	@SequenceGenerator(name="HR_RECRUIT_PLAN_seq_gen", sequenceName="HR_RECRUIT_PLAN_seq")
	@Column(name = "SID")
	private int sid;// 自增id

	// 计划编号
	@Column(name = "PLAN_NO", nullable = true, length = 254)
	private String planNo;

	// 计划名称
	@Column(name = "PLAN_NAME", nullable = true, length = 254)
	private String planName;

	// 招聘渠道,01-	网络招聘	;02-招聘会招聘;	03-人才猎头推荐
	@Column(name = "PLAN_DITCH", nullable = true, length = 254)
	private String planDitch;

	// 预算费用
	@Column(name = "PLAN_COST")
	private double planCost;

	// 招聘人数
	@Column(name = "PLAN_RECR_NUM", nullable = true, length = 11, columnDefinition = "INT default 0")
	private int planRecrNum = 0;

	// 开始日期
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_DATE")
	private Date startDate;

	// 结束日期
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_DATE")
	private Date endDate;

	// 招聘说明
	@Lob
	@Column(name = "RECRUIT_DESCRIPTION")
	private String recruitDescription;

	// 招聘备注
	@Lob
	@Column(name = "RECRUIT_REMARK")
	private String recruitRemark;

	// 审批人id
	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name="IDX77da9f2d1a5248cdafc1a98cb7e")
	@JoinColumn(name = "APPROVE_PERSON")
	private TeePerson approvePerson;

	// 审批日期
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "APPROVE_DATE")
	private Date approveDate;

	// 审批意见
	@Column(name = "APPROVE_COMMENT", nullable = true, length = 254)
	private String approveComment;

	// 计划状态;0-新建（待审批）;	1-批准;	2-不批准   3-已结束
	@Column(name = "PLAN_STATUS", nullable = true, length = 1, columnDefinition = "INT default 0")
	private int planStatus = 0;
	
	@OneToMany(mappedBy="plan",fetch=FetchType.LAZY,cascade=CascadeType.ALL)//因为这里是双向的 一对多 所以要写mappedBy
	private List<TeeRecruitRequirements> recruitRequirementsList;//招聘需求

	// 创建者id
	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name="IDXcb727d23f2904955a32d8cf455f")
	@JoinColumn(name = "CREATE_USER_ID")
	private TeePerson createUser;

	// 创建者部门id
	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name="IDX5bb96f146ad6431d885aeb88677")
	@JoinColumn(name = "CREATE_DEPT_ID")
	private TeeDepartment createDept;

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

	public String getPlanDitch() {
		return planDitch;
	}

	public void setPlanDitch(String planDitch) {
		this.planDitch = planDitch;
	}

	public double getPlanCost() {
		return planCost;
	}

	public void setPlanCost(double planCost) {
		this.planCost = planCost;
	}

	public int getPlanRecrNum() {
		return planRecrNum;
	}

	public void setPlanRecrNum(int planRecrNum) {
		this.planRecrNum = planRecrNum;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getRecruitDescription() {
		return recruitDescription;
	}

	public void setRecruitDescription(String recruitDescription) {
		this.recruitDescription = recruitDescription;
	}

	public String getRecruitRemark() {
		return recruitRemark;
	}

	public void setRecruitRemark(String recruitRemark) {
		this.recruitRemark = recruitRemark;
	}

	public TeePerson getApprovePerson() {
		return approvePerson;
	}

	public void setApprovePerson(TeePerson approvePerson) {
		this.approvePerson = approvePerson;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
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

	public TeePerson getCreateUser() {
		return createUser;
	}

	public void setCreateUser(TeePerson createUser) {
		this.createUser = createUser;
	}

	public TeeDepartment getCreateDept() {
		return createDept;
	}

	public void setCreateDept(TeeDepartment createDept) {
		this.createDept = createDept;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<TeeRecruitRequirements> getRecruitRequirementsList() {
		return recruitRequirementsList;
	}
	
	public void setRecruitRequirementsList(
			List<TeeRecruitRequirements> recruitRequirementsList) {
		this.recruitRequirementsList = recruitRequirementsList;
	}
}
