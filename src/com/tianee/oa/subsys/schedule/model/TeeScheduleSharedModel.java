package com.tianee.oa.subsys.schedule.model;

public class TeeScheduleSharedModel {
	private String uuid;//自增id
	private String scheduleId;
	private String scheduleName;
	private String scheduleTime;
	private int scheduleRangeType;
	private int scheduleType;
	private int scheduleFlag;
	private String scheduleUser;
	private int userId;
	private String userName;
	private int readFlag;//阅读标记
	private String readTimeDesc;//阅读时间
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}
	public String getScheduleName() {
		return scheduleName;
	}
	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
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
	public int getReadFlag() {
		return readFlag;
	}
	public void setReadFlag(int readFlag) {
		this.readFlag = readFlag;
	}
	public String getReadTimeDesc() {
		return readTimeDesc;
	}
	public void setReadTimeDesc(String readTimeDesc) {
		this.readTimeDesc = readTimeDesc;
	}
	public String getScheduleTime() {
		return scheduleTime;
	}
	public void setScheduleTime(String scheduleTime) {
		this.scheduleTime = scheduleTime;
	}
	public int getScheduleFlag() {
		return scheduleFlag;
	}
	public void setScheduleFlag(int scheduleFlag) {
		this.scheduleFlag = scheduleFlag;
	}
	public String getScheduleUser() {
		return scheduleUser;
	}
	public void setScheduleUser(String scheduleUser) {
		this.scheduleUser = scheduleUser;
	}
	public int getScheduleRangeType() {
		return scheduleRangeType;
	}
	public void setScheduleRangeType(int scheduleRangeType) {
		this.scheduleRangeType = scheduleRangeType;
	}
	public int getScheduleType() {
		return scheduleType;
	}
	public void setScheduleType(int scheduleType) {
		this.scheduleType = scheduleType;
	}
	
}
