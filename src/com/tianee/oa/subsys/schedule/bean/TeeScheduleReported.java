package com.tianee.oa.subsys.schedule.bean;
import org.hibernate.annotations.Index;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name="SCHEDULE_REPORTED")
public class TeeScheduleReported {
	@Id
	@GeneratedValue(generator = "system-uuid")  
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(columnDefinition = "char(32)",name="UUID")
	private String uuid;//自增id
	
	@ManyToOne()
	@Index(name="IDXf623b51aedf34edd842352ed81c")
	@JoinColumn(name="SCHEDULE_ID")
	private TeeSchedule schedule;
	
	@ManyToOne()
	@Index(name="IDXbdb15479841a4dfab2819ddb188")
	@JoinColumn(name="USER_ID")
	private TeePerson user;
	
	@Column(name="READ_FLAG")
	private int readFlag;//阅读标记
	
	@Column(name="READ_TIME")
	private Calendar readTime;//阅读时间

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public TeeSchedule getSchedule() {
		return schedule;
	}

	public void setSchedule(TeeSchedule schedule) {
		this.schedule = schedule;
	}

	public TeePerson getUser() {
		return user;
	}

	public void setUser(TeePerson user) {
		this.user = user;
	}

	public int getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(int readFlag) {
		this.readFlag = readFlag;
	}

	public Calendar getReadTime() {
		return readTime;
	}

	public void setReadTime(Calendar readTime) {
		this.readTime = readTime;
	}
	
	
}
