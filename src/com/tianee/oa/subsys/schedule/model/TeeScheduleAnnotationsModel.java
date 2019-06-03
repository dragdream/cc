package com.tianee.oa.subsys.schedule.model;

public class TeeScheduleAnnotationsModel {
	private String uuid;//自增id
	private String scheduleName;
	private String scheduleId;
	private int userId;
	private String userName;
	private String content;//内容
	private String crTimeDesc;//批阅时间
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getScheduleName() {
		return scheduleName;
	}
	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}
	public String getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCrTimeDesc() {
		return crTimeDesc;
	}
	public void setCrTimeDesc(String crTimeDesc) {
		this.crTimeDesc = crTimeDesc;
	}
	
}
