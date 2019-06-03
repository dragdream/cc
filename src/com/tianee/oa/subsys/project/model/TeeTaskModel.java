package com.tianee.oa.subsys.project.model;

import java.util.Date;

import javax.persistence.Column;

public class TeeTaskModel {
	private int sid;//自增id
	private String taskNo ;//任务序号
	private String taskName ;//任务名称
	private String taskLevel ;//任务级别
	//private TeePerson manager;//任务负责人
	private String managerName;//负责人姓名 
	private int  managerId;//负责人主键
	private String flowTypeIds ;//相关流程
	private String flowTypeNames;//相关流程名称
	//private TeeTask higherTask;//上级任务
	private String higherTaskName;//上级任务名称
	private int  higherTaskId;//上级任务主键
	
	//private TeeTask preTask;//前置任务
	private String preTaskName;//前置任务名称
	private int preTaskId;//前置任务主键
	
	private String beginTimeStr ;//开始时间
	
	private String endTimeStr ;//结束时间
	
	private String realBeginTimeStr ;//实际开始时间
	
	private String realEndTimeStr ;//实际结束时间
	
    private int days ;//项目周期
	
	private String createTimeStr ;//创建时间
	
	//private TeePerson creater;//任务创建人
	private int createrId;//创建人主键
	private String createrName;//创建人姓名
	
	private int progress ;//任务进度
	
	private int status ;//0 进行中   1已完成
	
	private String description ;//任务描述
	
	private String remark ;//任务备注
	
	private String projectId ;//项目主键
	private String projectName ;//项目名称
	private String  projectNo;//项目编码
	
	private boolean flag;

	public String getRealBeginTimeStr() {
		return realBeginTimeStr;
	}

	public void setRealBeginTimeStr(String realBeginTimeStr) {
		this.realBeginTimeStr = realBeginTimeStr;
	}

	public String getRealEndTimeStr() {
		return realEndTimeStr;
	}

	public void setRealEndTimeStr(String realEndTimeStr) {
		this.realEndTimeStr = realEndTimeStr;
	}

	

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskLevel() {
		return taskLevel;
	}

	public void setTaskLevel(String taskLevel) {
		this.taskLevel = taskLevel;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public String getFlowTypeIds() {
		return flowTypeIds;
	}

	public void setFlowTypeIds(String flowTypeIds) {
		this.flowTypeIds = flowTypeIds;
	}

	public String getFlowTypeNames() {
		return flowTypeNames;
	}

	public void setFlowTypeNames(String flowTypeNames) {
		this.flowTypeNames = flowTypeNames;
	}

	public String getHigherTaskName() {
		return higherTaskName;
	}

	public void setHigherTaskName(String higherTaskName) {
		this.higherTaskName = higherTaskName;
	}

	public int getHigherTaskId() {
		return higherTaskId;
	}

	public void setHigherTaskId(int higherTaskId) {
		this.higherTaskId = higherTaskId;
	}

	public String getPreTaskName() {
		return preTaskName;
	}

	public void setPreTaskName(String preTaskName) {
		this.preTaskName = preTaskName;
	}

	public int getPreTaskId() {
		return preTaskId;
	}

	public void setPreTaskId(int preTaskId) {
		this.preTaskId = preTaskId;
	}

	public String getBeginTimeStr() {
		return beginTimeStr;
	}

	public void setBeginTimeStr(String beginTimeStr) {
		this.beginTimeStr = beginTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public int getCreaterId() {
		return createrId;
	}

	public void setCreaterId(int createrId) {
		this.createrId = createrId;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	
	
}
