package com.tianee.oa.subsys.project.model;


public class TeeProjectApprovalRuleModel {

	private int sid;//自增id
	private String approverIds ;//审批人员Id字符串
	private String approverNames;//审批人员姓名
	private String manageDeptIds ;//管辖部门Id字符串
	private String manageDeptNames;//管辖部门名称
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
	public String getManageDeptNames() {
		return manageDeptNames;
	}
	public void setManageDeptNames(String manageDeptNames) {
		this.manageDeptNames = manageDeptNames;
	}
	public String getApproverNames() {
		return approverNames;
	}
	public void setApproverNames(String approverNames) {
		this.approverNames = approverNames;
	}
}
