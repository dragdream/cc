package com.tianee.oa.core.base.attend.bean;
import java.math.BigDecimal;

import org.hibernate.annotations.Index;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;

@SuppressWarnings("serial")
@Entity
@Table(name = "attend_overtime")
public class TeeAttendOvertime {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="attend_overtime_seq_gen")
	@SequenceGenerator(name="attend_overtime_seq_gen", sequenceName="attend_overtime_seq")
	@Column(name="SID")
	private int sid;//自增idi
	
	@ManyToOne()
	@Index(name="IDX728ad9a8a87e493bb0ac90e5929")
	@JoinColumn(name="USER_ID")
	private TeePerson user;//申请人
	
	@ManyToOne()
	@Index(name="IDXd85b8a7f8e5d4906b8e47c6d2a2")
	@JoinColumn(name="LEADER_ID")
	private TeePerson leader;//审批人
	
	@Column(name="CREATE_TIME")
	private long createTime;//新建时间
	
	@Column(name="SUBMIT_TIME")
	private long submitTime;//登记时间
	
	@Column(name="START_TIME")
	private long startTime;//STRAT_TIME

	@Column(name="END_TIME")
	private long endTime;//END_TIME
	
	@Column(name="OVERTIME_DESC")
	@Lob()
	private String overtimeDesc;//加班内容
	
	@Column(name="ALLOW",columnDefinition="int  default 1")
	private int allow;//审批状态
	
	@Column(name="STATUS",columnDefinition="int  default 1")
	private int status;//办理状态
	
	@Column(name="REASON")
	private String reason;//不批准原因
	
	@Column(name="REGISTER_IP")
	private String registerId;//申请IP
	
	@Column(name="HOURS")
	private int hours;//加班小时数
	
	@Column(name="MINUTE")
	private int minute;//加班分钟数

	
	@Column(name="OVER_HOURS")
	private BigDecimal overHours=new BigDecimal(0);//加班小时数
	
	public BigDecimal getOverHours() {
		return overHours;
	}

	public void setOverHours(BigDecimal overHours) {
		this.overHours = overHours;
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

	public TeePerson getLeader() {
		return leader;
	}

	public void setLeader(TeePerson leader) {
		this.leader = leader;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(long submitTime) {
		this.submitTime = submitTime;
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

	public String getOvertimeDesc() {
		return overtimeDesc;
	}

	public void setOvertimeDesc(String overtimeDesc) {
		this.overtimeDesc = overtimeDesc;
	}

	public int getAllow() {
		return allow;
	}

	public void setAllow(int allow) {
		this.allow = allow;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

}
