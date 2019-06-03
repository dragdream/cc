package com.tianee.oa.core.workflow.workmanage.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.tianee.oa.webframe.httpModel.TeeBaseModel;

@SuppressWarnings("serial")
public class TeeFlowTimerTaskModel extends TeeBaseModel{
	
	private int sid;//流水号
	
	private int flowId;//流程ID

	private String usersIds;//发起人ID串
	
	private String userNames;//发起人名称串

	private int type;//提醒类型提醒类型：1-仅此一次；2-按日；3-按周；4-按月；5-按年；
	
	private String timerType;//提醒类型提醒类型：1-仅此一次；2-按日；3-按周；4-按月；5-按年；

	private String remindModel;
	
	private String remindModelDesc;
	
	private long remindStamp;//提醒戳，long型
	
	private Date lastTime;//最近一次提醒时间

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getFlowId() {
		return flowId;
	}

	public void setFlowId(int flowId) {
		this.flowId = flowId;
	}


	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTimerType() {
		return timerType;
	}

	public void setTimerType(String timerType) {
		this.timerType = timerType;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public String getUserNames() {
		return userNames;
	}

	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}

	public String getRemindModel() {
		return remindModel;
	}

	public void setRemindModel(String remindModel) {
		this.remindModel = remindModel;
	}

	public long getRemindStamp() {
		return remindStamp;
	}

	public void setRemindStamp(long remindStamp) {
		this.remindStamp = remindStamp;
	}

	public void setRemindModelDesc(String remindModelDesc) {
		this.remindModelDesc = remindModelDesc;
	}

	public String getRemindModelDesc() {
		return remindModelDesc;
	}

	public String getUsersIds() {
		return usersIds;
	}

	public void setUsersIds(String usersIds) {
		this.usersIds = usersIds;
	}
	
}
