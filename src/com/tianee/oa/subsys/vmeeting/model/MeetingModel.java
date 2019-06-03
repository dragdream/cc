package com.tianee.oa.subsys.vmeeting.model;

import java.util.Date;

public class MeetingModel {
	private String meetingId;//meetid;
	private String meetingName;//会议名称
	private String startT;//开始时间
	private String endT;//结束时间
    private String userId;//申请人Id
    private String userName;//用户姓名
	private String password;//用户密码
    private String chairmanPwd;//主持人密码
    private String confuserPwd;//会议密码
    private int maxvideo;//视频数目
    private int maxPersonspeak;//发言人数
    private String content;//会议内容
    private String meetingNumber;//会议编号
    private String personIds;//参会人员id字符串  1，2，3，4，5
    private String personNames;//参会人员的名字字符串   王晓，丽丽，
    private int attendNum;//会议数量
    private int timeState;//0是只读状态 、1是允许状态
	public int getTimeState() {
		return timeState;
	}
	public void setTimeState(int timeState) {
		this.timeState = timeState;
	}
	public int getAttendNum() {
		return attendNum;
	}
	public void setAttendNum(int attendNum) {
		this.attendNum = attendNum;
	}
	public String getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}
	public String getMeetingName() {
		return meetingName;
	}
	public void setMeetingName(String meetingName) {
		this.meetingName = meetingName;
	}

	public String getStartT() {
		return startT;
	}
	public void setStartT(String startT) {
		this.startT = startT;
	}
	public String getEndT() {
		return endT;
	}
	public void setEndT(String endT) {
		this.endT = endT;
	}
	public String getPersonIds() {
		return personIds;
	}
	public void setPersonIds(String personIds) {
		this.personIds = personIds;
	}
	public String getPersonNames() {
		return personNames;
	}
	public void setPersonNames(String personNames) {
		this.personNames = personNames;
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
	public String getChairmanPwd() {
		return chairmanPwd;
	}
	public void setChairmanPwd(String chairmanPwd) {
		this.chairmanPwd = chairmanPwd;
	}
	public String getConfuserPwd() {
		return confuserPwd;
	}
	public void setConfuserPwd(String confuserPwd) {
		this.confuserPwd = confuserPwd;
	}
	public int getMaxvideo() {
		return maxvideo;
	}
	public void setMaxvideo(int maxvideo) {
		this.maxvideo = maxvideo;
	}
	public int getMaxPersonspeak() {
		return maxPersonspeak;
	}
	public void setMaxPersonspeak(int maxPersonspeak) {
		this.maxPersonspeak = maxPersonspeak;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMeetingNumber() {
		return meetingNumber;
	}
	public void setMeetingNumber(String meetingNumber) {
		this.meetingNumber = meetingNumber;
	}
	public String getPassword() {
			return password;
    }
	public void setPassword(String password) {
			this.password = password;
	}
	
	
}
