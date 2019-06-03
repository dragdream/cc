package com.tianee.oa.core.base.attend.bean;
import org.hibernate.annotations.Index;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;



@SuppressWarnings("serial")
@Entity
@Table(name = "ATTEND_HOLIDAY")
public class TeeAttendHoliday{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="ATTEND_HOLIDAY_seq_gen")
	@SequenceGenerator(name="ATTEND_HOLIDAY_seq_gen", sequenceName="ATTEND_HOLIDAY_seq")
	@Column(name="SID")
	private int sid;
	
	
	@Column(name="HOLIDAY_NAME")
	private String holidayName;
	
	@Column(name="START_TIME")
	private Calendar startTime;
	
	@Column(name="END_TIME")
	private Calendar endTime;
	
	// 父级id
	@Column(name = "PARENT_ID", columnDefinition = "int default 0")
	private int parentId;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getHolidayName() {
		return holidayName;
	}

	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}

	public Calendar getStartTime() {
		return startTime;
	}

	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	public Calendar getEndTime() {
		return endTime;
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	
	
}
