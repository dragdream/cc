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
@Entity
@Table(name="RUN_REL")
public class TeeRunRel {
	@Id
	@GeneratedValue(generator = "system-uuid")  
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(columnDefinition = "char(32)",name="UUID")
	private String uuid;//自增id
	
	@ManyToOne
	@Index(name="IDXb477cbe90b004abd8fef19a9694")
	@JoinColumn(name="RUN_ID")
	private TeeFlowRun flowRun;
	
	@ManyToOne
	@Index(name="IDXba8e9618a67a40f498e154b2a59")
	@JoinColumn(name="RUN_ID1")
	private TeeFlowRun flowRun1;
	
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

	public TeeFlowRun getFlowRun1() {
		return flowRun1;
	}

	public void setFlowRun1(TeeFlowRun flowRun1) {
		this.flowRun1 = flowRun1;
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
