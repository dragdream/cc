package com.tianee.oa.subsys.report.model;

public class TeeReportConditionModel {
	private int sid;
	private int flowId;
	private String conditionName;//条件名称
	private int flowFlag;//流程状态
	private String beginUser;//发起人
	private String beginUserNames;//发起人
	private String beginDept;//发起人部门
	private String beginDeptNames;//发起人
	private String beginRole;//发起人角色
	private String beginRoleNames;//发起人
	private int timeRange;//时间范围
	private String time1Desc;
	private String time2Desc;
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getFlowId() {
		return flowId;
	}
	public void setFlowId(int flowId) {
		this.flowId = flowId;
	}
	public String getConditionName() {
		return conditionName;
	}
	public void setConditionName(String conditionName) {
		this.conditionName = conditionName;
	}
	public int getFlowFlag() {
		return flowFlag;
	}
	public void setFlowFlag(int flowFlag) {
		this.flowFlag = flowFlag;
	}
	public String getBeginUser() {
		return beginUser;
	}
	public void setBeginUser(String beginUser) {
		this.beginUser = beginUser;
	}
	public String getBeginDept() {
		return beginDept;
	}
	public void setBeginDept(String beginDept) {
		this.beginDept = beginDept;
	}
	public String getBeginRole() {
		return beginRole;
	}
	public void setBeginRole(String beginRole) {
		this.beginRole = beginRole;
	}
	public int getTimeRange() {
		return timeRange;
	}
	public void setTimeRange(int timeRange) {
		this.timeRange = timeRange;
	}
	public String getTime1Desc() {
		return time1Desc;
	}
	public void setTime1Desc(String time1Desc) {
		this.time1Desc = time1Desc;
	}
	public String getTime2Desc() {
		return time2Desc;
	}
	public void setTime2Desc(String time2Desc) {
		this.time2Desc = time2Desc;
	}
	public String getBeginUserNames() {
		return beginUserNames;
	}
	public void setBeginUserNames(String beginUserNames) {
		this.beginUserNames = beginUserNames;
	}
	public String getBeginDeptNames() {
		return beginDeptNames;
	}
	public void setBeginDeptNames(String beginDeptNames) {
		this.beginDeptNames = beginDeptNames;
	}
	public String getBeginRoleNames() {
		return beginRoleNames;
	}
	public void setBeginRoleNames(String beginRoleNames) {
		this.beginRoleNames = beginRoleNames;
	}
	
	
}
