package com.tianee.oa.core.base.meeting.bean;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name = "MEETING_TOPIC")
public class TeeMeetingTopic {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="MEETING_TOPIC_seq_gen")
	@SequenceGenerator(name="MEETING_TOPIC_seq_gen", sequenceName="MEETING_TOPIC_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@Lob
	@Column(name="CONTENT_")
	private String content;//内容
	
	@Column(name="CR_TIME")
	private Calendar crTime;//创建时间
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID")
	@Index(name="MEETING_TOPIC_USER_IDX")
	private TeePerson crUser;//创建人
	
	@ManyToOne()
	@JoinColumn(name="MEETING_ID")
	@Index(name="MEETING_TOPIC_MEETING_IDX")
	private TeeMeeting meeting;//关联会议

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Calendar getCrTime() {
		return crTime;
	}

	public void setCrTime(Calendar crTime) {
		this.crTime = crTime;
	}

	public TeePerson getCrUser() {
		return crUser;
	}

	public void setCrUser(TeePerson crUser) {
		this.crUser = crUser;
	}

	public TeeMeeting getMeeting() {
		return meeting;
	}

	public void setMeeting(TeeMeeting meeting) {
		this.meeting = meeting;
	}
	
	
}
