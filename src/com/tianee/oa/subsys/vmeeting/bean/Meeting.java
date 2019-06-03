package com.tianee.oa.subsys.vmeeting.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.tianee.oa.core.org.bean.TeePerson;



@Entity
@Table(name="meet")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Meeting implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -116676156460795271L;
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "meeting_id")//主键id
	private String meetingId;
	@Column(name="meeting_name")//会议名称
	private String meetingName;
	@Column(name="start_time")//开始时间
	private Date startTime;
	@Column(name="end_time")//结束时间
	private Date endTime;
	/*@Column(name="meeting_user")//申请人
	private int meetingUser;*/
	@ManyToOne
	@JoinColumn(name = "meeting_user")//申请人
	private TeePerson tPerson;
	@Column(name="attend_num")//会议数量
	private int attendNum;
	@Column(name="chairman_pwd")//主持人密码
	private String chairmanPwd;
	@Column(name="confuser_pwd")//会议密码
	private String confuserPwd;
	@Column(name="maxvideo")//视频数目
	private int maxvideo;
	@Column(name="maxpersonspeak")//发言人数
	private int maxPersonspeak;
	@Column(name="content")//会议内容
	private String content;
	@Column(name="meeting_number")//会议编号
	private String meetingNumber;
	
	@ManyToMany(targetEntity=TeePerson.class,fetch = FetchType.LAZY  ) //参会人员
	@JoinTable(name="vm_meet_persons",
			joinColumns={@JoinColumn(name="canHid")},       
        inverseJoinColumns={@JoinColumn(name="userId")}  ) 	
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	private List<TeePerson> persons = new ArrayList(0);
	
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
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

	public TeePerson gettPerson() {
		return tPerson;
	}
	public void settPerson(TeePerson tPerson) {
		this.tPerson = tPerson;
	}
	public List<TeePerson> getPersons() {
		return persons;
	}
	public void setPersons(List<TeePerson> persons) {
		this.persons = persons;
	}
	public int getAttendNum() {
		return attendNum;
	}
	public void setAttendNum(int attendNum) {
		this.attendNum = attendNum;
	}
	

	
}
