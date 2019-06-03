package com.tianee.oa.core.workflow.flowrun.bean;
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

import com.tianee.oa.subsys.schedule.bean.TeeSchedule;
@Entity
@Table(name="RUN_SCHEDULE_REL")
public class TeeRunScheduleRel {
	@Id
	@GeneratedValue(generator = "system-uuid")  
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(columnDefinition = "char(32)",name="UUID")
	private String uuid;//自增id
	
	@ManyToOne
	@Index(name="IDX365301919fc340bf9fe2da6007c")
	@JoinColumn(name="RUN_ID")
	private TeeFlowRun flowRun;
	
	@ManyToOne
	@Index(name="IDXb93c80fddb214a6ab9019526ce0")
	@JoinColumn(name="SCHEDULE_ID")
	private TeeSchedule schedule;
	
	@Column(name="TIME_")
	private Calendar time;

	@Column(name="USER_ID")
	private int userId;
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public TeeFlowRun getFlowRun() {
		return flowRun;
	}

	public void setFlowRun(TeeFlowRun flowRun) {
		this.flowRun = flowRun;
	}

	public TeeSchedule getSchedule() {
		return schedule;
	}

	public void setSchedule(TeeSchedule schedule) {
		this.schedule = schedule;
	}

	public Calendar getTime() {
		return time;
	}

	public void setTime(Calendar time) {
		this.time = time;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
}
