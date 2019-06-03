package com.tianee.oa.subsys.project.model;

//消息提醒模型类
public class TeeNeiBuTiXingModel {
	private int sid;//自增id
	private String content;//提示内容
	private String userIds;//提示人员ID字符串
	private String userNames;//提示人员姓名字符串
	private String creTime;//提示时间
	private int createUserId;//创建人员ID
	private String createUserName;//创建人员姓名
	public int getSid() {
		return sid;
	}
	public String getContent() {
		return content;
	}
	public String getUserIds() {
		return userIds;
	}
	
	public String getCreTime() {
		return creTime;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	
	public void setCreTime(String creTime) {
		this.creTime = creTime;
	}
	public int getCreateUserId() {
		return createUserId;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getUserNames() {
		return userNames;
	}
	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}

	
}
