package com.tianee.oa.subsys.informationReport.model;

import java.util.Calendar;



public class TeeTaskTemplateModel {
	private int sid;//自增id
	private String taskName;//任务名称
	private String taskDesc;//任务描述
	private int pubType;//发布类型        1=按人员 2=按部门
	private String pubUserIds;//发布人员（id字符串）
	private String pubUserNames;//发布人员姓名
	
	private int pubStatus;//发布状态        0=未发布   1=已发布
	
	private String pubDeptIds;//发布部门（id字符串）
	private String pubDeptNames;//发布部门名称
	

	private int taskType;//任务类型        1=日报   2=周报    3=月报     4=季报     5=年报    6=一次性
	private String taskTypeDesc;//任务类型描述
	
	
	private String model;//频次的频次的JSON模型
	private String modelDesc;//频次描述
	
	
	
	//private TeePerson crUser;//创建人
	private  int crUserId;//创建人主键
	private  String crUserName;//创建人姓名
	
	//private Date crTime;//创建时间
	private String crTimeStr;//创建时间
	
	//上报人员
	private String reportUserIds;
	private String reportUserNames;
	//上报部门
	private String reportDeptIds;
	private String reportDeptNames;
	//上报角色
	private String reportRoleIds;
	private String reportRoleNames;
	
	//预估时间
	//private Calendar preTime;//预估时间
	private String preTimeStr;
	private int repType;//上报类别
	
	
	
	public String getPreTimeStr() {
		return preTimeStr;
	}

	public void setPreTimeStr(String preTimeStr) {
		this.preTimeStr = preTimeStr;
	}

	public String getTaskTypeDesc() {
		return taskTypeDesc;
	}

	public void setTaskTypeDesc(String taskTypeDesc) {
		this.taskTypeDesc = taskTypeDesc;
	}

	public int getPubStatus() {
		return pubStatus;
	}

	public void setPubStatus(int pubStatus) {
		this.pubStatus = pubStatus;
	}

	public String getModelDesc() {
		return modelDesc;
	}

	public void setModelDesc(String modelDesc) {
		this.modelDesc = modelDesc;
	}

	public String getReportUserIds() {
		return reportUserIds;
	}

	public void setReportUserIds(String reportUserIds) {
		this.reportUserIds = reportUserIds;
	}

	public String getReportUserNames() {
		return reportUserNames;
	}

	public void setReportUserNames(String reportUserNames) {
		this.reportUserNames = reportUserNames;
	}

	public String getReportDeptIds() {
		return reportDeptIds;
	}

	public void setReportDeptIds(String reportDeptIds) {
		this.reportDeptIds = reportDeptIds;
	}

	public String getReportDeptNames() {
		return reportDeptNames;
	}

	public void setReportDeptNames(String reportDeptNames) {
		this.reportDeptNames = reportDeptNames;
	}

	public String getReportRoleIds() {
		return reportRoleIds;
	}

	public void setReportRoleIds(String reportRoleIds) {
		this.reportRoleIds = reportRoleIds;
	}

	public String getReportRoleNames() {
		return reportRoleNames;
	}

	public void setReportRoleNames(String reportRoleNames) {
		this.reportRoleNames = reportRoleNames;
	}

	public String getPubUserNames() {
		return pubUserNames;
	}

	public void setPubUserNames(String pubUserNames) {
		this.pubUserNames = pubUserNames;
	}

	public String getPubDeptNames() {
		return pubDeptNames;
	}

	public void setPubDeptNames(String pubDeptNames) {
		this.pubDeptNames = pubDeptNames;
	}

	

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskDesc() {
		return taskDesc;
	}

	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}

	public int getPubType() {
		return pubType;
	}

	public void setPubType(int pubType) {
		this.pubType = pubType;
	}

	public String getPubUserIds() {
		return pubUserIds;
	}

	public void setPubUserIds(String pubUserIds) {
		this.pubUserIds = pubUserIds;
	}

	public String getPubDeptIds() {
		return pubDeptIds;
	}

	public void setPubDeptIds(String pubDeptIds) {
		this.pubDeptIds = pubDeptIds;
	}

	public int getTaskType() {
		return taskType;
	}

	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getCrUserId() {
		return crUserId;
	}

	public void setCrUserId(int crUserId) {
		this.crUserId = crUserId;
	}

	public String getCrUserName() {
		return crUserName;
	}

	public void setCrUserName(String crUserName) {
		this.crUserName = crUserName;
	}

	public String getCrTimeStr() {
		return crTimeStr;
	}

	public void setCrTimeStr(String crTimeStr) {
		this.crTimeStr = crTimeStr;
	}

	public int getRepType() {
		return repType;
	}

	public void setRepType(int repType) {
		this.repType = repType;
	}
	
}
