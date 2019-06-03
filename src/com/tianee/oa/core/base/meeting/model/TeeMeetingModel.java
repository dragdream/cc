package com.tianee.oa.core.base.meeting.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;

public class TeeMeetingModel {
	private int sid;//自增id

	private String userId;//申请人
	private String userName;//
	
	
	//private long createTime;//新建时间
	private String createTimeStr;//

	private String meetDateStr;
	private long startTime;//STarT_TIME
	private String startTimeStr;//

	
	private long endTime;//END_TIME
	private String endTimeStr;//
	private String meetName ;//MEET_NAME	VARCHAR(200)	会议名称
	private String subject;//SUBJECT	VARCHAR(200)	会议主题
	private String meetDesc;//MEET_DESC	CLOB	会议描述
	private String attendee;//ATTENDEE	CLOB	出席人员(内部)
	private String attendeeName;
	private int status;//STATUS	VARCHAR(20)	会议状态  0-待批 1-已批准  2-进行中 3-未批准 4-已结束

	private String meetRoomId;//会议室Id
	private String meetRoomName;//
	private String managerId;//MANAGER_ID	VARCHAR(200)	会议室管理员
	private String managerName;//

	private String attendeeOut;//ATTENDEE_OUT	CLOB	出席人员（外部）
	private String attendeeOutName;
	
	
	private String  attendLeaderIds;//出席领导
	private String attendLeaderNames;
	
	
	private String smsRemind;// SMS_REMIND	VARCHAR(200)	是否内部短信通知出席人员  使用内部短信提醒,选中为1,不选中为0

	private String sms2Remind;//SMS2_REMIND	VARCHAR(200)	手机短信提醒出席人员
	
	private int isWriteCalendar;//	是否写入日程安排
	
	private String submary;//SUMMARY	CLOB	会议纪要内容
	
	private String readPeopleIds;//READ_PEOPLE_ID	CLOB	会议纪要指定读者ID
	private String readPeopleNames;//

	private String attendeeNot;//ATTENDEE_NOT	CLOB	会议缺席人员
	
	private List<TeeAttachmentModel> attacheModels;

	private int resendHour;//RESEND_LONG	number	提醒设置：提前几小时提醒
	private int resendMinute;//RESEND_MINUTE	number	提醒设置：提前分钟小时提醒

	private int resendSeveral;//RESEND_SEVERAL	number	提醒设置：提醒几次(次数)
	
	private String equipmentIds;
	
	private String equipmentNames;
	
	private int recorderId;
	
	private String recorderName;
	
	private String attendeeNotIds;
	
	private String attendeeNotNames;
	
    private int isAttend;//当前登录人是不是参会人
    
    private  int isConfirm;//当前登陆人  是否确认参会了
    
    private String isUpLoadSummary="未上传";
    
    private String wbMeetRoomName;//外部会议名称或地点

    private int sbStatus;//设备是否异常
    
    
    
    
	public String getAttendLeaderIds() {
		return attendLeaderIds;
	}

	public void setAttendLeaderIds(String attendLeaderIds) {
		this.attendLeaderIds = attendLeaderIds;
	}

	public String getAttendLeaderNames() {
		return attendLeaderNames;
	}

	public void setAttendLeaderNames(String attendLeaderNames) {
		this.attendLeaderNames = attendLeaderNames;
	}

	public String getIsUpLoadSummary() {
		return isUpLoadSummary;
	}

	public void setIsUpLoadSummary(String isUpLoadSummary) {
		this.isUpLoadSummary = isUpLoadSummary;
	}

	public int getIsAttend() {
		return isAttend;
	}

	public void setIsAttend(int isAttend) {
		this.isAttend = isAttend;
	}

	public int getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(int isConfirm) {
		this.isConfirm = isConfirm;
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


	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public String getMeetName() {
		return meetName;
	}

	public void setMeetName(String meetName) {
		this.meetName = meetName;
	}



	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMeetRoomId() {
		return meetRoomId;
	}

	public void setMeetRoomId(String meetRoomId) {
		this.meetRoomId = meetRoomId;
	}

	public String getMeetRoomName() {
		return meetRoomName;
	}

	public void setMeetRoomName(String meetRoomName) {
		this.meetRoomName = meetRoomName;
	}

	public String getMeetDesc() {
		return meetDesc;
	}

	public void setMeetDesc(String meetDesc) {
		this.meetDesc = meetDesc;
	}

	public String getAttendee() {
		return attendee;
	}

	public void setAttendee(String attendee) {
		this.attendee = attendee;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public String getAttendeeOut() {
		return attendeeOut;
	}

	public void setAttendeeOut(String attendeeOut) {
		this.attendeeOut = attendeeOut;
	}

	public String getAttendeeOutName() {
		return attendeeOutName;
	}

	public void setAttendeeOutName(String attendeeOutName) {
		this.attendeeOutName = attendeeOutName;
	}

	public String getSmsRemind() {
		return smsRemind;
	}

	public void setSmsRemind(String smsRemind) {
		this.smsRemind = smsRemind;
	}

	public String getSms2Remind() {
		return sms2Remind;
	}

	public void setSms2Remind(String sms2Remind) {
		this.sms2Remind = sms2Remind;
	}

	public String getSubmary() {
		return submary;
	}

	public void setSubmary(String submary) {
		this.submary = submary;
	}

	public String getReadPeopleIds() {
		return readPeopleIds;
	}

	public void setReadPeopleIds(String readPeopleIds) {
		this.readPeopleIds = readPeopleIds;
	}

	public String getReadPeopleNames() {
		return readPeopleNames;
	}

	public void setReadPeopleNames(String readPeopleNames) {
		this.readPeopleNames = readPeopleNames;
	}

	public String getAttendeeNot() {
		return attendeeNot;
	}

	public void setAttendeeNot(String attendeeNot) {
		this.attendeeNot = attendeeNot;
	}

	public List<TeeAttachmentModel> getAttacheModels() {
		return attacheModels;
	}

	public void setAttacheModels(List<TeeAttachmentModel> attacheModels) {
		this.attacheModels = attacheModels;
	}



	public int getResendHour() {
		return resendHour;
	}

	public void setResendHour(int resendHour) {
		this.resendHour = resendHour;
	}

	public int getResendMinute() {
		return resendMinute;
	}

	public void setResendMinute(int resendMinute) {
		this.resendMinute = resendMinute;
	}

	public int getResendSeveral() {
		return resendSeveral;
	}

	public void setResendSeveral(int resendSeveral) {
		this.resendSeveral = resendSeveral;
	}

	public String getMeetDateStr() {
		return meetDateStr;
	}

	public void setMeetDateStr(String meetDateStr) {
		this.meetDateStr = meetDateStr;
	}

	public String getAttendeeName() {
		return attendeeName;
	}

	public void setAttendeeName(String attendeeName) {
		this.attendeeName = attendeeName;
	}

	public int getIsWriteCalendar() {
		return isWriteCalendar;
	}

	public void setIsWriteCalendar(int isWriteCalendar) {
		this.isWriteCalendar = isWriteCalendar;
	}

	public String getEquipmentIds() {
		return equipmentIds;
	}

	public void setEquipmentIds(String equipmentIds) {
		this.equipmentIds = equipmentIds;
	}

	public String getEquipmentNames() {
		return equipmentNames;
	}

	public void setEquipmentNames(String equipmentNames) {
		this.equipmentNames = equipmentNames;
	}

	public int getRecorderId() {
		return recorderId;
	}

	public void setRecorderId(int recorderId) {
		this.recorderId = recorderId;
	}

	public String getRecorderName() {
		return recorderName;
	}

	public void setRecorderName(String recorderName) {
		this.recorderName = recorderName;
	}

	public String getAttendeeNotIds() {
		return attendeeNotIds;
	}

	public void setAttendeeNotIds(String attendeeNotIds) {
		this.attendeeNotIds = attendeeNotIds;
	}

	public String getAttendeeNotNames() {
		return attendeeNotNames;
	}

	public void setAttendeeNotNames(String attendeeNotNames) {
		this.attendeeNotNames = attendeeNotNames;
	}

	public String getWbMeetRoomName() {
		return wbMeetRoomName;
	}

	public void setWbMeetRoomName(String wbMeetRoomName) {
		this.wbMeetRoomName = wbMeetRoomName;
	}

	public int getSbStatus() {
		return sbStatus;
	}

	public void setSbStatus(int sbStatus) {
		this.sbStatus = sbStatus;
	}
	
	
}
