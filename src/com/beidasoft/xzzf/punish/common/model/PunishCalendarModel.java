package com.beidasoft.xzzf.punish.common.model;



public class PunishCalendarModel {
	//主键
	private String id; 

	//案件唯一ID
	private String baseId;

	//跳转功能路径
	private String remindUrl;
	
	//开始日期
	private long startTime;

	//结束日期
	private long closedTime;

	//倒计时天数（每天上午8点更新）
	private int countDown;

	//提示开始日期（大于等于倒计时天数开始提示）
	private int remindDaily;

	//提示形式（半角逗号“,”分隔）
	private String remindType;

	//提示模板
	private String remindTemplet;
	
	//提示模板
	private String remindTitle;

	//当前状态（100：正常， 200：暂停提示， 300：完成）
	private String status;
	
	//参与者
	private String primaryId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}

	public String getRemindUrl() {
		return remindUrl;
	}

	public void setRemindUrl(String remindUrl) {
		this.remindUrl = remindUrl;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getClosedTime() {
		return closedTime;
	}

	public void setClosedTime(long closedTime) {
		this.closedTime = closedTime;
	}

	public int getCountDown() {
		return countDown;
	}

	public void setCountDown(int countDown) {
		this.countDown = countDown;
	}

	public int getRemindDaily() {
		return remindDaily;
	}

	public void setRemindDaily(int remindDaily) {
		this.remindDaily = remindDaily;
	}

	public String getRemindType() {
		return remindType;
	}

	public void setRemindType(String remindType) {
		this.remindType = remindType;
	}

	public String getRemindTemplet() {
		return remindTemplet;
	}

	public void setRemindTemplet(String remindTemplet) {
		this.remindTemplet = remindTemplet;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPrimaryId() {
		return primaryId;
	}

	public void setPrimaryId(String primaryId) {
		this.primaryId = primaryId;
	}

	public String getRemindTitle() {
		return remindTitle;
	}

	public void setRemindTitle(String remindTitle) {
		this.remindTitle = remindTitle;
	}
	
	
}
