package com.tianee.oa.subsys.project.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.SequenceGenerator;
import javax.persistence.Table;



@Entity
@Table(name = "project_approval_rule")
public class TeeProjectApprovalRule {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="project_approval_rule_seq_gen")
	@SequenceGenerator(name="project_approval_rule_seq_gen", sequenceName="project_approval_rule_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	
	@Column(name="approver_ids")
	private String approverIds ;//审批人员Id字符串
	
	@Column(name="manage_dept_Ids")
	private String manageDeptIds ;//管辖部门Id字符串

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getApproverIds() {
		return approverIds;
	}

	public void setApproverIds(String approverIds) {
		this.approverIds = approverIds;
	}

	public String getManageDeptIds() {
		return manageDeptIds;
	}

	public void setManageDeptIds(String manageDeptIds) {
		this.manageDeptIds = manageDeptIds;
	}

	
}
