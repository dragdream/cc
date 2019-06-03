package com.tianee.oa.core.base.meeting.model;


/**
 * 参会及查阅情况
 * 
 * @author wyw
 * 
 */
public class TeeMeetingAttendConfirmModel {

	private int sid;// 自增id

	private int meetingId; // 会议ID
	
	private int attendFlag; // 参会状态；0-待确认；1-参会；2-不参会
	
	private String remark;// 备注说明
	
	
	private String confirmTimeStr;// 确认参会时间
	
	private int readFlag; // 签阅状态；0-未阅读；1-已阅
	
	private String readingTimeStr;// 签阅时间


	// 创建人
	private String createUserName;
	private int createUserId;

	// 创建时间
	private String createTimeStr;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(int meetingId) {
		this.meetingId = meetingId;
	}

	public int getAttendFlag() {
		return attendFlag;
	}

	public void setAttendFlag(int attendFlag) {
		this.attendFlag = attendFlag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getConfirmTimeStr() {
		return confirmTimeStr;
	}

	public void setConfirmTimeStr(String confirmTimeStr) {
		this.confirmTimeStr = confirmTimeStr;
	}

	public int getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(int readFlag) {
		this.readFlag = readFlag;
	}

	public String getReadingTimeStr() {
		return readingTimeStr;
	}

	public void setReadingTimeStr(String readingTimeStr) {
		this.readingTimeStr = readingTimeStr;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public int getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	
	
	
	

}
