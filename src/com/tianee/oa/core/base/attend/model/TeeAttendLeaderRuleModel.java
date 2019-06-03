package com.tianee.oa.core.base.attend.model;


public class TeeAttendLeaderRuleModel {

	private int sid;//自增id

	private String deptIds;//管辖范围部门Id字符串
	private String deptNames;//
	private String userIds;//管辖范围人员Id字符串
	private String userNames;//
	private String leaderIds;//审批人员Id字符串
	private String leaderNames;//
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getLeaderIds() {
		return leaderIds;
	}

	public void setLeaderIds(String leaderIds) {
		this.leaderIds = leaderIds;
	}

	public String getDeptNames() {
		return deptNames;
	}

	public void setDeptNames(String deptNames) {
		this.deptNames = deptNames;
	}

	public String getUserNames() {
		return userNames;
	}

	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}

	public String getLeaderNames() {
		return leaderNames;
	}

	public void setLeaderNames(String leaderNames) {
		this.leaderNames = leaderNames;
	}
	
	
}

