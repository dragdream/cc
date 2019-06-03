package com.tianee.oa.core.base.hr.settings.model;



public class TeeAnnualLeaveModel {

	private String uuid;//自增id
	private int yearCount;// 在职年数
	private int vacationDays;// 假期天数
	private String defaultFlag;// 预定义标识   1-预定义; 0-否
	private int createUserId;//创建者id
	private String createUserName;//创建者名称
	private String createTimeStr;//创建时间
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public int getYearCount() {
		return yearCount;
	}
	public void setYearCount(int yearCount) {
		this.yearCount = yearCount;
	}
	public int getVacationDays() {
		return vacationDays;
	}
	public void setVacationDays(int vacationDays) {
		this.vacationDays = vacationDays;
	}
	public String getDefaultFlag() {
		return defaultFlag;
	}
	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag = defaultFlag;
	}
	public int getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	
	
}
