package com.tianee.oa.subsys.project.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;

//项目抄送
@Entity
@Table(name = "project_cost")
public class TeeProjectCost {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="project_cost_seq_gen")
	@SequenceGenerator(name="project_cost_seq_gen", sequenceName="project_cost_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	
	@Column(name="amount")
	private double amount ;//费用金额
	
	@Column(name="cr_time")
	private Date  createTime ;//创建时间
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="creater_id")
	private TeePerson creater;//创建人
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="approver_id")
	private TeePerson approver;//审批人员
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="costType_id")
	private TeeProjectCostType costType;//费用类型
	
	@Column(name="description")
	private String  description ;//费用说明
	
	@Column(name="status")
	private int  status ;//状态   0待审批   1通过  2拒绝
	
	@Column(name="project_id")
	private String  projectId ;//项目主键
	
	
	@Column(name="refused_reason")
	private String  refusedReason ;//拒绝原因
	

	public String getRefusedReason() {
		return refusedReason;
	}

	public void setRefusedReason(String refusedReason) {
		this.refusedReason = refusedReason;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public TeePerson getCreater() {
		return creater;
	}

	public void setCreater(TeePerson creater) {
		this.creater = creater;
	}

	public TeePerson getApprover() {
		return approver;
	}

	public void setApprover(TeePerson approver) {
		this.approver = approver;
	}

	public TeeProjectCostType getCostType() {
		return costType;
	}

	public void setCostType(TeeProjectCostType costType) {
		this.costType = costType;
	}

	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
}
