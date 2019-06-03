package com.tianee.oa.subsys.project.model;


public class TeeProjectNotationModel {
	
	private int sid;//自增id
	
	private String projectId ;//项目主键
	
	//private Date createTime ;//创建时间
	private String createTimeStr;
	
	private String content ;//批注内容
	
	//private TeePerson creater;//批注创建人
	private int createrId;//创建人主键
	private String createrName;//创建人姓名
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
}
