package com.tianee.oa.subsys.informationReport.model;


public class TeeTaskPubRecordItemModel {
	
	private int sid;//自增id
	
	//private Date createTime;//创建时间
	private  String createTimeStr;//创建时间
	
	//private TeeTaskTemplate taskTemplate;//所属任务模板
	private int taskTemplateId;
	private String taskTemplateName;
	private int pubType;//发布类型        1=按人员 2=按部门
	private int taskType;//任务类型        1=日报   2=周报    3=月报     4=季报     5=年报    6=一次性
	private String pc;//上报频次
	private String pubTypeDesc;//发布类型描述
	private String taskTypeDesc;//任务类型描述
	
	
		
	//private TeeTaskPubRecord taskPubRecord;//所关联的任务发布记录
	private int taskPubRecordId;
	
	//private TeePerson user;//关联人员id
	private int userId;
	private String userName;
	
	//private TeeDepartment dept;//关联部门id
	private int deptId;
	private String deptName;
	
	private int flag;//上报状态  0=未上报   1=已上报

	private int repType;
	
	
	public String getTaskTypeDesc() {
		return taskTypeDesc;
	}

	public void setTaskTypeDesc(String taskTypeDesc) {
		this.taskTypeDesc = taskTypeDesc;
	}

	public String getPubTypeDesc() {
		return pubTypeDesc;
	}

	public void setPubTypeDesc(String pubTypeDesc) {
		this.pubTypeDesc = pubTypeDesc;
	}

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

	public int getTaskTemplateId() {
		return taskTemplateId;
	}

	public void setTaskTemplateId(int taskTemplateId) {
		this.taskTemplateId = taskTemplateId;
	}

	public String getTaskTemplateName() {
		return taskTemplateName;
	}

	public void setTaskTemplateName(String taskTemplateName) {
		this.taskTemplateName = taskTemplateName;
	}

	public int getTaskPubRecordId() {
		return taskPubRecordId;
	}

	public void setTaskPubRecordId(int taskPubRecordId) {
		this.taskPubRecordId = taskPubRecordId;
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

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getPubType() {
		return pubType;
	}

	public void setPubType(int pubType) {
		this.pubType = pubType;
	}

	public int getTaskType() {
		return taskType;
	}

	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}

	public String getPc() {
		return pc;
	}

	public void setPc(String pc) {
		this.pc = pc;
	}

	public int getRepType() {
		return repType;
	}

	public void setRepType(int repType) {
		this.repType = repType;
	}
	
	
	
}
