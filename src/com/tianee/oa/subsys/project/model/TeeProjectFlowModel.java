package com.tianee.oa.subsys.project.model;


public class TeeProjectFlowModel {
	private int sid;//自增id
	//private TeePerson creater;//发起人
		private String createrName;
		private int createrId;
		
		//private TeeTask task;//任务负责人
		private int taskId;
		private String taskName;
		
		private String projectId ;//项目主键
		
		//private TeeFlowRun flowRun;
		private String runName;
		private int runId;
	    private String flowTypeName;
	    private String currStep;//当前步骤
	    
	    
	    private String createTimeStr;//创建时间字符串
	
	
	    public String getCreateTimeStr() {
			return createTimeStr;
		}
		public void setCreateTimeStr(String createTimeStr) {
			this.createTimeStr = createTimeStr;
		}
		public String getCurrStep() {
			return currStep;
		}
		public void setCurrStep(String currStep) {
			this.currStep = currStep;
		}
		public String getFlowTypeName() {
			return flowTypeName;
		}
		public void setFlowTypeName(String flowTypeName) {
			this.flowTypeName = flowTypeName;
		}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getCreaterName() {
		return createrName;
	}
	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}
	public int getCreaterId() {
		return createrId;
	}
	public void setCreaterId(int createrId) {
		this.createrId = createrId;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getRunName() {
		return runName;
	}
	public void setRunName(String runName) {
		this.runName = runName;
	}
	public int getRunId() {
		return runId;
	}
	public void setRunId(int runId) {
		this.runId = runId;
	}
	
}
