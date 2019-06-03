package com.tianee.oa.core.base.attend.model;


public class TeeAttendConfigRulesModel {
	private int sid;
	private String configName;//排班名称
	private String configModel;//排班模型
	private String configModelStr;//排班类型  复杂
	//private Set<TeeUserRole> roles;//角色范围
	//private Set<TeeDepartment> depts;//部门范围
	//private Set<TeePerson> users;//人员范围
	
	private String roleIds;
	private String roleNames;
	
	
	private String deptIds;
	private String deptNames;
	
	
	private String userIds;
	private String userNames;
	
	
	public int getSid() {
		return sid;
	}
	public String getConfigName() {
		return configName;
	}
	public String getConfigModel() {
		return configModel;
	}
	public String getRoleIds() {
		return roleIds;
	}
	public String getRoleNames() {
		return roleNames;
	}
	public String getDeptIds() {
		return deptIds;
	}
	public String getDeptNames() {
		return deptNames;
	}
	public String getUserIds() {
		return userIds;
	}
	public String getUserNames() {
		return userNames;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public void setConfigName(String configName) {
		this.configName = configName;
	}
	public void setConfigModel(String configModel) {
		this.configModel = configModel;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}
	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}
	public void setDeptNames(String deptNames) {
		this.deptNames = deptNames;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}
	public String getConfigModelStr() {
		return configModelStr;
	}
	public void setConfigModelStr(String configModelStr) {
		this.configModelStr = configModelStr;
	}
	
	
	
	
}
