package com.tianee.oa.subsys.evaluation.model;

import com.tianee.oa.core.org.bean.TeePerson;

public class TeeEvalTemplateModel{
	private int sid;
	
	private String subject;
	
	private int evalTypeId;
	
	private String evalTypeName;
	
	private TeePerson createUser;
	
	private String createTimeDesc;
	
	private int validDays;
	
	private int autoType;
	
	private int startType;
	
	private int delta;
	
	private int startDate;
	
	private String remark;
	
	private String targetsUsers;
	
	private String targetsUserNames;
	
	private String targetsDepts;
	
	private String targetsDeptNames;
	
	private String targetsRoles;
	
	private String targetsRoleNames;
	
	private String viewPrivsUserIds;
	
	private String viewPrivsUserNames;
	
	private String managePrivsUserIds;
	
	private String managePrivsUserNames;
	
	private int rangeType;//1 月度考核  2 季度考核 3 年度考核

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}


	public TeePerson getCreateUser() {
		return createUser;
	}

	public void setCreateUser(TeePerson createUser) {
		this.createUser = createUser;
	}


	public String getCreateTimeDesc() {
		return createTimeDesc;
	}

	public void setCreateTimeDesc(String createTimeDesc) {
		this.createTimeDesc = createTimeDesc;
	}

	public int getValidDays() {
		return validDays;
	}

	public void setValidDays(int validDays) {
		this.validDays = validDays;
	}

	public int getAutoType() {
		return autoType;
	}

	public void setAutoType(int autoType) {
		this.autoType = autoType;
	}

	public int getStartType() {
		return startType;
	}

	public void setStartType(int startType) {
		this.startType = startType;
	}

	public int getDelta() {
		return delta;
	}

	public void setDelta(int delta) {
		this.delta = delta;
	}

	public int getStartDate() {
		return startDate;
	}

	public void setStartDate(int startDate) {
		this.startDate = startDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTargetsUsers() {
		return targetsUsers;
	}

	public void setTargetsUsers(String targetsUsers) {
		this.targetsUsers = targetsUsers;
	}

	public String getTargetsDepts() {
		return targetsDepts;
	}

	public void setTargetsDepts(String targetsDepts) {
		this.targetsDepts = targetsDepts;
	}

	public String getTargetsRoles() {
		return targetsRoles;
	}

	public void setTargetsRoles(String targetsRoles) {
		this.targetsRoles = targetsRoles;
	}


	public int getRangeType() {
		return rangeType;
	}

	public void setRangeType(int rangeType) {
		this.rangeType = rangeType;
	}

	public int getEvalTypeId() {
		return evalTypeId;
	}

	public void setEvalTypeId(int evalTypeId) {
		this.evalTypeId = evalTypeId;
	}

	public String getEvalTypeName() {
		return evalTypeName;
	}

	public void setEvalTypeName(String evalTypeName) {
		this.evalTypeName = evalTypeName;
	}

	public String getViewPrivsUserIds() {
		return viewPrivsUserIds;
	}

	public void setViewPrivsUserIds(String viewPrivsUserIds) {
		this.viewPrivsUserIds = viewPrivsUserIds;
	}

	public String getViewPrivsUserNames() {
		return viewPrivsUserNames;
	}

	public void setViewPrivsUserNames(String viewPrivsUserNames) {
		this.viewPrivsUserNames = viewPrivsUserNames;
	}

	public String getManagePrivsUserIds() {
		return managePrivsUserIds;
	}

	public void setManagePrivsUserIds(String managePrivsUserIds) {
		this.managePrivsUserIds = managePrivsUserIds;
	}

	public String getManagePrivsUserNames() {
		return managePrivsUserNames;
	}

	public void setManagePrivsUserNames(String managePrivsUserNames) {
		this.managePrivsUserNames = managePrivsUserNames;
	}

	public String getTargetsUserNames() {
		return targetsUserNames;
	}

	public void setTargetsUserNames(String targetsUserNames) {
		this.targetsUserNames = targetsUserNames;
	}

	public String getTargetsDeptNames() {
		return targetsDeptNames;
	}

	public void setTargetsDeptNames(String targetsDeptNames) {
		this.targetsDeptNames = targetsDeptNames;
	}

	public String getTargetsRoleNames() {
		return targetsRoleNames;
	}

	public void setTargetsRoleNames(String targetsRoleNames) {
		this.targetsRoleNames = targetsRoleNames;
	}
	
	
}