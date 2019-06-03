package com.tianee.oa.core.base.meeting.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 参会及查阅情况
 * 
 * @author wyw
 * 
 */
@Entity
@Table(name = "MEETING_ATTEND_CONFIRM")
public class TeeMeetingAttendConfirm {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "MEETING_CONFIRM_seq_gen")
	@SequenceGenerator(name = "MEETING_CONFIRM_seq_gen", sequenceName = "MEETING_CONFIRM_seq")
	@Column(name = "SID")
	private int sid;// 自增id

	@Column(name = "MEETING_ID", columnDefinition = "INT default 0")
	private int meetingId; // 会议ID
	
	//参会情况
	@Column(name = "ATTEND_FLAG", columnDefinition = "INT default 0")
	private int attendFlag; // 参会状态；0-待确认；1-参会；2-不参会
	
	@Column(name = "REMARK")
	private String remark;// 备注说明
	
	
	@Column(name = "CONFIRM_TIME")
	private Date confirmTime;// 确认参会时间
	
	//签阅情况
	@Column(name = "READ_FLAG", columnDefinition = "INT default 0")
	private int readFlag; // 签阅状态；0-未阅读；1-已阅
	
	@Column(name = "READING_TIME")
	private Date readingTime;// 签阅时间

	
	

	// 创建人
	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name = "IDX_MEETING_CONFIRM_CR_USER")
	@JoinColumn(name = "CREATE_USER")
	private TeePerson createUser;

	// 创建时间
	@Column(name = "CREATE_TIME")
	private Date createTime;// 录入时间

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

	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public int getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(int readFlag) {
		this.readFlag = readFlag;
	}

	public Date getReadingTime() {
		return readingTime;
	}

	public void setReadingTime(Date readingTime) {
		this.readingTime = readingTime;
	}

	public TeePerson getCreateUser() {
		return createUser;
	}

	public void setCreateUser(TeePerson createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
	

}
