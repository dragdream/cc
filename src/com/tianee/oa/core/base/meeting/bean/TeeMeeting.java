package com.tianee.oa.core.base.meeting.bean;
import org.hibernate.annotations.Index;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 
 * @author syl
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "MEETING")
public class TeeMeeting {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="MEETING_seq_gen")
	@SequenceGenerator(name="MEETING_seq_gen", sequenceName="MEETING_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@ManyToOne()
	@Index(name="IDX30f3297a40ec47d2a8f814c880e")
	@JoinColumn(name="USER_ID")
	private TeePerson user;//申请人
	
	@Column(name="CREATE_TIME")
	private long createTime;//新建时间
	
	@Column(name="START_TIME")
	private long startTime;//STRAT_TIME

	@Column(name="END_TIME")
	private long endTime;//END_TIME
	
	
	@Column(name="MEET_NAME" , length = 200)
	private String meetName ;//MEET_NAME	VARCHAR(200)	会议名称
	@Column(name="SUBJECT" , length = 200)
	private String subject;//SUBJECT	VARCHAR(200)	会议主题
	
	@Column(name="MEET_DESC" , length = 200)
	private String meetDesc;//MEET_DESC	CLOB	会议描述

	@Lob
	@Column(name="ATTENDEE" )
	private String attendee;//ATTENDEE	CLOB	出席人员(内部)
	
	
	@Lob
	@Column(name="ATTENDLEADERIDS" )
	private String attendLeaderIds;//ATTENDEE	CLOB	出席领导
	
	@ManyToMany(cascade={CascadeType.MERGE,CascadeType.PERSIST},fetch=FetchType.LAZY)
	@JoinTable(name="MEETING_ATTENDEE",
			joinColumns={@JoinColumn(name="MEETING_ID")},
			inverseJoinColumns={@JoinColumn(name="ATTENDEE_ID")})
	private Set<TeePerson> attendeeList = new HashSet<TeePerson>(0);//出席人员(内部)，用于exists查询使用

	
	@ManyToMany(cascade={CascadeType.MERGE,CascadeType.PERSIST},fetch=FetchType.LAZY)
	@JoinTable(name="MEETING_ATTENDLEADERIDS",
			joinColumns={@JoinColumn(name="MEETING_ID")},
			inverseJoinColumns={@JoinColumn(name="LEADER_ID")})
	private Set<TeePerson> attendLeader = new HashSet<TeePerson>(0);//出席领导(内部)，用于exists查询使用
	
	
	@ManyToOne()
	@Index(name="IDXd2b2ee25d8c34823bb4f451615c")
	@JoinColumn(name="MEET_ROOM_ID")
	private TeeMeetingRoom meetRoom;// /*ROOM_ID	Int	所属会议室的MR_ID*/
	
	
	@Column(name="STATUS" ,columnDefinition="INT default 0" ,nullable=false)
	private int status;//STATUS	VARCHAR(20)	会议状态  0-待批 1-已批准  2-进行中 3-未批准 4-已结束

	@ManyToOne()
	@Index(name="IDX36350ed0d1734c939ba6d689cd7")
	@JoinColumn(name="MANAGER_ID")
	private TeePerson manager;//MANAGER_ID	VARCHAR(200)	会议室管理员
	@Lob
	@Column(name="ATTENDEE_OUT" )
	private String attendeeOut;//ATTENDEE_OUT	CLOB	出席人员（外部）
	
	@Column(name="SMS_REMIND" ,columnDefinition="char(1)" )
	private String smsRemind;// SMS_REMIND	VARCHAR(200)	是否内部短信通知出席人员  使用内部短信提醒,选中为1,不选中为0
	
	@Column(name="SMS2_REMIND" ,columnDefinition="char(1)" )
	private String sms2Remind;//SMS2_REMIND	VARCHAR(200)	手机短信提醒出席人员
	
	@Column(name="IS_WRITE_CALEDNAR" ,columnDefinition="INT default 0" )
	private int isWriteCalendar;//	是否写入日程安排
	
	@ManyToOne()
	@Index(name="IDX36350ed0d1734c939ba6d6wocao")
	@JoinColumn(name="RECORDER_ID")
	private TeePerson recorder;//会议纪要员
	
	@Column(name="WB_MEETBOOT_NAME")
	private String wbMeetRoomName;
	
	@Lob
	@Column(name="SUMMARY" )
	private String submary;//SUMMARY	CLOB	会议纪要内容
	
	@ManyToMany(cascade={CascadeType.MERGE,CascadeType.PERSIST},fetch=FetchType.LAZY)
	@JoinTable(name="MEETING_SUM_READER",
			joinColumns={@JoinColumn(name="MEETING_ID")},
			inverseJoinColumns={@JoinColumn(name="READ_PEOPLE_ID")})
	private Set<TeePerson> readPeoples = new HashSet<TeePerson>(0);//会议纪要读者
	
	@Column(name="ISUPLOADSUMMARY" )
	private String isUpLoadSummary;
	
	@Lob
	@Column(name="ATTENDEE_NOT_IDS" )
	private String attendeeNotIds;
	
	@Lob
	@Column(name="ATTENDEE_NOT_NAMES" )
	private String attendeeNotNames;
	
	@Lob
	@Column(name="EQUIPMENT_IDS" )
	private String equipmentIds;
	
	@Lob
	@Column(name="EQUIPMENT_NAMES" )
	private String equipmentNames;
	
	/*ATTACHMENT_ID	CLOB	附件ID
	ATTACHMENT_NAME	CLOB	附件名称*/
	/*SECRET_TO_ID	CLOB	查看范围（人员）
	PRIV_ID	CLOB	查看范围（角色）
	TO_ID	CLOB	查看范围（部门）*/
	@Column(name="RESEND_HOUR" ,columnDefinition="INT default 0" ,nullable=false)
	private int resendHour;//RESEND_LONG	number	提醒设置：提前几小时提醒
	
	@Column(name="RESEND_MINUTE" ,columnDefinition="INT default 0" ,nullable=false)
	private int resendMinute;//RESEND_MINUTE	number	提醒设置：提前分钟小时提醒
	
	@Column(name="RESEND_SEVERAL" ,columnDefinition="INT default 0" ,nullable=false)
	private int resendSeveral;//RESEND_SEVERAL	number	提醒设置：提醒几次(次数)
	
	@OneToMany(mappedBy="meeting",fetch=FetchType.LAZY,orphanRemoval=true,cascade=CascadeType.ALL)
	private List<TeeMeetingTopic> meetingTopics = new ArrayList(0);

	@Column(name="SB_STATUS")
	private int sbStatus;//设备是否异常 0：正常 1：异常
	
	
	
	
	public String getAttendLeaderIds() {
		return attendLeaderIds;
	}

	public void setAttendLeaderIds(String attendLeaderIds) {
		this.attendLeaderIds = attendLeaderIds;
	}

	public Set<TeePerson> getAttendLeader() {
		return attendLeader;
	}

	public void setAttendLeader(Set<TeePerson> attendLeader) {
		this.attendLeader = attendLeader;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeePerson getUser() {
		return user;
	}

	public void setUser(TeePerson user) {
		this.user = user;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
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

	public TeeMeetingRoom getMeetRoom() {
		return meetRoom;
	}

	public void setMeetRoom(TeeMeetingRoom meetRoom) {
		this.meetRoom = meetRoom;
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

	public TeePerson getManager() {
		return manager;
	}

	public void setManager(TeePerson manager) {
		this.manager = manager;
	}

	public String getAttendeeOut() {
		return attendeeOut;
	}

	public void setAttendeeOut(String attendeeOut) {
		this.attendeeOut = attendeeOut;
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

	public int getResendSeveral() {
		return resendSeveral;
	}

	public void setResendSeveral(int resendSeveral) {
		this.resendSeveral = resendSeveral;
	}

	public int getIsWriteCalendar() {
		return isWriteCalendar;
	}

	public void setIsWriteCalendar(int isWriteCalendar) {
		this.isWriteCalendar = isWriteCalendar;
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

	public TeePerson getRecorder() {
		return recorder;
	}

	public void setRecorder(TeePerson recorder) {
		this.recorder = recorder;
	}

	public Set<TeePerson> getReadPeoples() {
		return readPeoples;
	}

	public void setReadPeoples(Set<TeePerson> readPeoples) {
		this.readPeoples = readPeoples;
	}

	public Set<TeePerson> getAttendeeList() {
		return attendeeList;
	}

	public void setAttendeeList(Set<TeePerson> attendeeList) {
		this.attendeeList = attendeeList;
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

	public List<TeeMeetingTopic> getMeetingTopics() {
		return meetingTopics;
	}

	public void setMeetingTopics(List<TeeMeetingTopic> meetingTopics) {
		this.meetingTopics = meetingTopics;
	}

	public String getIsUpLoadSummary() {
		return isUpLoadSummary;
	}

	public void setIsUpLoadSummary(String isUpLoadSummary) {
		this.isUpLoadSummary = isUpLoadSummary;
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


	

	
