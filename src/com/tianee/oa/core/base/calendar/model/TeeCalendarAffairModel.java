package com.tianee.oa.core.base.calendar.model;

import java.util.Calendar;

import com.tianee.webframe.controller.BaseController;

/**
 * 
 * @author syl
 *
 */

public class TeeCalendarAffairModel extends BaseController{
	private int sid;//自增id
	private String userId;
	private String userName;
	private Calendar startDate;//STRAT_TIME
	
	private long startTime;
	private String startTimeStr;
	private String startTimeTempStr;//临时开始时间，为周期性事务考虑
	
	private long endTime;
	private Calendar endDate;//END_TIME
	private String endTimeStr;//	
	private String endTimeTempStr ;////临时结束时间，为周期性事务考虑
	private int calType;//
	private int calAffType;//日程或者--周期性事务  0-日程 2-周期性事务
	private String calLevel = "0";//CAL_LEVEL 级别
	private int overStatus = 0;//OVER_STATUS
	private String managerId;//MANAGER_ID
	private String managerName;
	
	private String actorIds;//参与者
	private String actorNames;
	private String status;//进行状态 ---日程专用
	private int allDayStatus;//判断是否跨天
	private int remindType;//
	private int isWeekend;//是否排除周末 1-排除  0 - 不排除
	private int preHours;
	private int preMinutes;
	
	public int getIsWeekend() {
		return isWeekend;
	}
	public void setIsWeekend(int isWeekend) {
		this.isWeekend = isWeekend;
	}
	private String content;//CONTENT
	private String remindDate;//提醒时间
	private String remindTimeStr;//提醒时间
	private long lastRemind;//最后一次内部提醒时间
	private String smsRemind;//是否使用内部短信提醒 0 否  1-是
	private int SMS2_REMIND;//是否使用手机短信提醒0-否 1-是
	private long lastSms2Remind;//最近一次手机短信提醒的时间
	
	public int getPreHours() {
		return preHours;
	}
	public void setPreHours(int preHours) {
		this.preHours = preHours;
	}
	public int getPreMinutes() {
		return preMinutes;
	}
	public void setPreMinutes(int preMinutes) {
		this.preMinutes = preMinutes;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}
	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public Calendar getStartDate() {
		return startDate;
	}
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}
	public Calendar getEndDate() {
		return endDate;
	}
	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}
	public String getEndTimeStr() {
		return endTimeStr;
	}
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	public int getCalType() {
		return calType;
	}
	public void setCalType(int calType) {
		this.calType = calType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRemindDate() {
		return remindDate;
	}
	public void setRemindDate(String remindDate) {
		this.remindDate = remindDate;
	}
	
	public String getRemindTimeStr() {
		return remindTimeStr;
	}
	public void setRemindTimeStr(String remindTimeStr) {
		this.remindTimeStr = remindTimeStr;
	}
	public long getLastRemind() {
		return lastRemind;
	}
	public void setLastRemind(long lastRemind) {
		this.lastRemind = lastRemind;
	}
	public int getSMS2_REMIND() {
		return SMS2_REMIND;
	}
	public void setSMS2_REMIND(int sMS2_REMIND) {
		SMS2_REMIND = sMS2_REMIND;
	}
	public long getLastSms2Remind() {
		return lastSms2Remind;
	}
	public void setLastSms2Remind(long lastSms2Remind) {
		this.lastSms2Remind = lastSms2Remind;
	}
	public String getManagerId() {
		return managerId;
	}
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getActorIds() {
		return actorIds;
	}
	public void setActorIds(String actorIds) {
		this.actorIds = actorIds;
	}
	public String getActorNames() {
		return actorNames;
	}
	public void setActorNames(String actorNames) {
		this.actorNames = actorNames;
	}
	public int getRemindType() {
		return remindType;
	}
	public void setRemindType(int remindType) {
		this.remindType = remindType;
	}
	public int getCalAffType() {
		return calAffType;
	}
	public void setCalAffType(int calAffType) {
		this.calAffType = calAffType;
	}
	public String getCalLevel() {
		return calLevel;
	}
	public void setCalLevel(String calLevel) {
		this.calLevel = calLevel;
	}
	public int getOverStatus() {
		return overStatus;
	}
	public void setOverStatus(int overStatus) {
		this.overStatus = overStatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getAllDayStatus() {
		return allDayStatus;
	}
	public void setAllDayStatus(int allDayStatus) {
		this.allDayStatus = allDayStatus;
	}
	public String getStartTimeTempStr() {
		return startTimeTempStr;
	}
	public void setStartTimeTempStr(String startTimeTempStr) {
		this.startTimeTempStr = startTimeTempStr;
	}
	public String getEndTimeTempStr() {
		return endTimeTempStr;
	}
	public void setEndTimeTempStr(String endTimeTempStr) {
		this.endTimeTempStr = endTimeTempStr;
	}
	public String getSmsRemind() {
		return smsRemind;
	}
	public void setSmsRemind(String smsRemind) {
		this.smsRemind = smsRemind;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	


}