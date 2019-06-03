package com.tianee.oa.core.base.hr.recruit.requirements.bean;
import java.util.Date;
import java.util.List;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Index;

import com.tianee.oa.core.base.hr.recruit.bean.TeeHrPool;
import com.tianee.oa.core.base.hr.recruit.plan.bean.TeeRecruitPlan;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 招聘需求
 * 
 * @author wyw
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "HR_RECRUIT_REQUIREMENTS")
public class TeeRecruitRequirements {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="HR_RECRUIT_REQUIR_seq_gen")
	@SequenceGenerator(name="HR_RECRUIT_REQUIR_seq_gen", sequenceName="HR_RECRUIT_REQUIR_seq")
	@Column(name = "SID")
	private int sid;// 自增id

	// 需求编号
	@Column(name = "REQU_NO", nullable = true, length = 200)
	private String requNo;

	// 创建者id
	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name="IDX7879424453894c99a7ee912d1bd")
	@JoinColumn(name = "CREATE_USER_ID")
	private TeePerson createUser;

	// 创建者部门id
	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name="IDX12c55aac73844601ab32957aea6")
	@JoinColumn(name = "CREATE_DEPT_ID")
	private TeeDepartment createDept;

	// 登记时间(创建时间)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME")
	private Date createTime;

	// 需求部门,sid串，多个用逗号隔开
	@Lob
	@Column(name = "REQU_DEPT")
	private String requDept;

	// 需求岗位
	@Column(name = "REQU_JOB", nullable = true, length = 254)
	private String requJob;

	// 需求人数
	@Column(name = "REQU_NUM", nullable = true, length = 20, columnDefinition = "INT default 0")
	private int requNum = 0;

	// 岗位要求
	@Lob
	@Column(name = "REQU_REQUIRES")
	private String requRequires;

	// 用工日期
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REQU_TIME")
	private Date requTime;

	// 需求状态 0-已提交 1-已受理 2-已确认 3-已结束
	@Column(name = "REQU_STATUS", nullable = true, columnDefinition = "INT default 0")
	private int requStatus = 0;
	
	//人才库
	@ManyToMany(targetEntity=TeeHrPool.class,   //多对多     
			fetch = FetchType.LAZY  ) 
			@JoinTable( name="RECRUIRENTS_POOLS",        
			joinColumns={@JoinColumn(name="RECRUIRENTS_ID")},       
			inverseJoinColumns={@JoinColumn(name="POOLS_ID")}  ) 	
	private List<TeeHrPool> hrPools;
	
	
	//关联计划
	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name="HR_RECRUIT_REQUIREMENTS_PLAN")
	@JoinColumn(name = "PLAN_ID")
	private TeeRecruitPlan plan;
	
	// 备注
	@Lob
	@Column(name = "REMARK")
	private String remark;
	
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getRequNo() {
		return requNo;
	}

	public void setRequNo(String requNo) {
		this.requNo = requNo;
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

	public String getRequDept() {
		return requDept;
	}

	public void setRequDept(String requDept) {
		this.requDept = requDept;
	}

	public String getRequJob() {
		return requJob;
	}

	public void setRequJob(String requJob) {
		this.requJob = requJob;
	}

	public int getRequNum() {
		return requNum;
	}

	public void setRequNum(int requNum) {
		this.requNum = requNum;
	}

	public String getRequRequires() {
		return requRequires;
	}

	public void setRequRequires(String requRequires) {
		this.requRequires = requRequires;
	}

	public Date getRequTime() {
		return requTime;
	}

	public void setRequTime(Date requTime) {
		this.requTime = requTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getRequStatus() {
		return requStatus;
	}

	public void setRequStatus(int requStatus) {
		this.requStatus = requStatus;
	}

	public List<TeeHrPool> getHrPools() {
		return hrPools;
	}

	public void setHrPools(List<TeeHrPool> hrPools) {
		this.hrPools = hrPools;
	}

	public TeeRecruitPlan getPlan() {
		return plan;
	}

	public void setPlan(TeeRecruitPlan plan) {
		this.plan = plan;
	}

}
