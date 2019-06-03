package com.tianee.oa.core.base.weekActive.model;

import javax.persistence.Column;


public class TeeWeekActiveModel {

	private int sid;//自增id
	private String activeUserId;//活动人员，以逗号分隔
	private String activeUserName;//活动人员，以逗号分隔
	private String activeStart;//ACTIVE_TIME 活动开始时间
	private String activeEnd;//ACTIVE_TIME 活动结束时间
	private String activeContent;//活动内容
	private String overStatus;//活动状态
	private int createUserId;//安排人
	private String createUserName;//安排人姓名
	private String createTimeStr;//安排时间
	
	private String deptAndUser;
	
	private String adderss;
	
	private String zhou;
	
	private int swOrxw;
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getActiveUserId() {
		return activeUserId;
	}

	public void setActiveUserId(String activeUserId) {
		this.activeUserId = activeUserId;
	}

	public String getActiveUserName() {
		return activeUserName;
	}

	public void setActiveUserName(String activeUserName) {
		this.activeUserName = activeUserName;
	}

	public String getActiveStart() {
		return activeStart;
	}

	public void setActiveStart(String activeStart) {
		this.activeStart = activeStart;
	}

	public String getActiveEnd() {
		return activeEnd;
	}

	public void setActiveEnd(String activeEnd) {
		this.activeEnd = activeEnd;
	}

	public String getActiveContent() {
		return activeContent;
	}

	public void setActiveContent(String activeContent) {
		this.activeContent = activeContent;
	}

	public String getOverStatus() {
		return overStatus;
	}

	public void setOverStatus(String overStatus) {
		this.overStatus = overStatus;
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

	public String getDeptAndUser() {
		return deptAndUser;
	}

	public void setDeptAndUser(String deptAndUser) {
		this.deptAndUser = deptAndUser;
	}

	public String getAdderss() {
		return adderss;
	}

	public void setAdderss(String adderss) {
		this.adderss = adderss;
	}

	public int getSwOrxw() {
		return swOrxw;
	}

	public void setSwOrxw(int swOrxw) {
		this.swOrxw = swOrxw;
	}

	public String getZhou() {
		return zhou;
	}

	public void setZhou(String zhou) {
		this.zhou = zhou;
	}
	

}
