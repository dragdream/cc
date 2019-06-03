package com.tianee.oa.core.priv.model;

import com.tianee.oa.webframe.httpModel.TeeBaseModel;

public class TeeModulePrivModel extends TeeBaseModel{
	
	private int sid;
	
	private String personId;
	private Integer moduleId;
	
	private String deptPriv;
	
	private String rolePriv;
	
	private String deptIdStr;
	private String deptIdsName;
	
	private String roleIdStr;
	private String roleIdsName;
	
	private String userIdStr;
	private String userIdsName;
	private String userIds1;
	private String userNames;
	
	public String getUserNames() {
		return userNames;
	}
	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}
	public String getUserIds1() {
		return userIds1;
	}
	public void setUserIds1(String userIds1) {
		this.userIds1 = userIds1;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public Integer getModuleId() {
		return moduleId;
	}
	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}
	public String getDeptPriv() {
		return deptPriv;
	}
	public void setDeptPriv(String deptPriv) {
		this.deptPriv = deptPriv;
	}
	public String getRolePriv() {
		return rolePriv;
	}
	public void setRolePriv(String rolePriv) {
		this.rolePriv = rolePriv;
	}
	public String getDeptIdStr() {
		return deptIdStr;
	}
	public void setDeptIdStr(String deptIdStr) {
		this.deptIdStr = deptIdStr;
	}
	public String getDeptIdsName() {
		return deptIdsName;
	}
	public void setDeptIdsName(String deptIdsName) {
		this.deptIdsName = deptIdsName;
	}
	public String getRoleIdStr() {
		return roleIdStr;
	}
	public void setRoleIdStr(String roleIdStr) {
		this.roleIdStr = roleIdStr;
	}
	public String getRoleIdsName() {
		return roleIdsName;
	}
	public void setRoleIdsName(String roleIdsName) {
		this.roleIdsName = roleIdsName;
	}
	public String getUserIdStr() {
		return userIdStr;
	}
	public void setUserIdStr(String userIdStr) {
		this.userIdStr = userIdStr;
	}
	public String getUserIdsName() {
		return userIdsName;
	}
	public void setUserIdsName(String userIdsName) {
		this.userIdsName = userIdsName;
	}
	

}
