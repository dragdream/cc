package com.tianee.oa.subsys.project.model;



public class TeeTaskReportModel {
private int sid;//自增id
	
	//private TeePerson reporter;//任务汇报人
	private String reporterName;//汇报人姓名
	
	private  int  reporterId;//汇报人主键
	
	private String  content ;//汇报内容

	//private Date createTime ;//创建时间
	private String createTimeStr;//创建时间

	private int progress ;//进度

	//private TeeTask task;//任务
	private  String taskName;//任务名称
	 
	private int taskId;//任务主键
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getReporterName() {
		return reporterName;
	}

	public void setReporterName(String reporterName) {
		this.reporterName = reporterName;
	}

	public int getReporterId() {
		return reporterId;
	}

	public void setReporterId(int reporterId) {
		this.reporterId = reporterId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	
}
