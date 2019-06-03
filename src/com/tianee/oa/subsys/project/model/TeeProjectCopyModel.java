package com.tianee.oa.subsys.project.model;


public class TeeProjectCopyModel {
	
	private int sid;//自增id
	
    private String projectId ;//项目主键
    
    private String projectName;//项目名称
    
    private String projectNum;//项目编码
    
    private String  projectBeginTimeStr;//项目计划开始时间
    
    private String projectEndTimeStr;//项目计划结束时间
    
    public String getProjectBeginTimeStr() {
		return projectBeginTimeStr;
	}

	public void setProjectBeginTimeStr(String projectBeginTimeStr) {
		this.projectBeginTimeStr = projectBeginTimeStr;
	}

	public String getProjectEndTimeStr() {
		return projectEndTimeStr;
	}

	public void setProjectEndTimeStr(String projectEndTimeStr) {
		this.projectEndTimeStr = projectEndTimeStr;
	}

	private  String  projectManagerName;//项目负责人姓名
    
    private String  projectMemberNames;//项目成员姓名
    
    
    private String  createTimeStr ;//创建时间
	
	//private TeePerson user;//被抄送人
	private int userId;
	
	private String userName;
	
	
	//抄送人
	private int  fromUserId;
	
	private String fromUserName;
	
	public int getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(int fromUserId) {
		this.fromUserId = fromUserId;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	private int readFlag;//阅读状态
	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectNum() {
		return projectNum;
	}

	public void setProjectNum(String projectNum) {
		this.projectNum = projectNum;
	}

	public String getProjectManagerName() {
		return projectManagerName;
	}

	public void setProjectManagerName(String projectManagerName) {
		this.projectManagerName = projectManagerName;
	}

	public String getProjectMemberNames() {
		return projectMemberNames;
	}

	public void setProjectMemberNames(String projectMemberNames) {
		this.projectMemberNames = projectMemberNames;
	}

	
	
	
	
	public int getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(int readFlag) {
		this.readFlag = readFlag;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	
}
