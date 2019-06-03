package com.tianee.oa.core.workflow.flowrun.model;

public class TeeFlowRelatedResourceModel {

	private int sid;//主键

	//private TeePerson createUser;//创建人
	private int createUserId;//创建人主键
	private String createUserName;//创建人姓名

	//private Calendar createTime;//创建时间
	private String createTimeStr;//创建时间
	

	private int type ;//1=相关流程   2=相关任务  3=相关客户   4=相关项目
	
	private String  relatedId ;//关联主键
	
	private String relatedName;//关联名称
	
	//private TeeFlowRun flowRun;//关联流程
	private int runId;//流程主键
	private String runName;//流程名称
	public int getSid() {
		return sid;
	}
	public int getCreateUserId() {
		return createUserId;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public int getType() {
		return type;
	}
	public String getRelatedId() {
		return relatedId;
	}
	public String getRelatedName() {
		return relatedName;
	}
	public int getRunId() {
		return runId;
	}
	public String getRunName() {
		return runName;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public void setType(int type) {
		this.type = type;
	}
	public void setRelatedId(String relatedId) {
		this.relatedId = relatedId;
	}
	public void setRelatedName(String relatedName) {
		this.relatedName = relatedName;
	}
	public void setRunId(int runId) {
		this.runId = runId;
	}
	public void setRunName(String runName) {
		this.runName = runName;
	}
	
	
	
	
}
