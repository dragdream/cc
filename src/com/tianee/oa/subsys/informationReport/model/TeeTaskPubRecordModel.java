package com.tianee.oa.subsys.informationReport.model;


public class TeeTaskPubRecordModel {
	
	private int sid;//自增id
	
	//private Date createTime;//创建时间
	private String createTimeStr;//创建时间
	
	//private TeeTaskTemplate taskTemplate;//所属任务模板
	private String taskTemplateName;//任务模板名称
	private  int taskTemplateId;//任务模板主键
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public String getTaskTemplateName() {
		return taskTemplateName;
	}
	public void setTaskTemplateName(String taskTemplateName) {
		this.taskTemplateName = taskTemplateName;
	}
	public int getTaskTemplateId() {
		return taskTemplateId;
	}
	public void setTaskTemplateId(int taskTemplateId) {
		this.taskTemplateId = taskTemplateId;
	}
	
	
	
	
}
