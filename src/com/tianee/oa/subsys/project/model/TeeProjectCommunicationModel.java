package com.tianee.oa.subsys.project.model;


public class TeeProjectCommunicationModel {
	
	private int sid;//自增id
	
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public int getCreateUserUuid() {
		return createUserUuid;
	}

	public void setCreateUserUuid(int createUserUuid) {
		this.createUserUuid = createUserUuid;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	private String content ;//内容
	
	private String projectId ;//项目主键
	
	private String  createTimeStr ;//创建时间
	
	//private TeePerson createUser;//创建人
	
	private int createUserUuid;//创建人主键
	
	private String createUserName;//创建人姓名
}
