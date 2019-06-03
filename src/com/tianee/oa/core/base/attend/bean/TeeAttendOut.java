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

/**
 * 
 * @author syl
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "ATTEND_OUT")
public class TeeAttendOut {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="ATTEND_OUT_seq_gen")
	@SequenceGenerator(name="ATTEND_OUT_seq_gen", sequenceName="ATTEND_OUT_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@ManyToOne()
	@Index(name="IDXb0136e94ff6a40d7bce6ae8c42f")
	@JoinColumn(name="USER_ID")
	private TeePerson user;//申请人
	
	@ManyToOne()
	@Index(name="IDXff71977f69a94ae1bfb22a4f6af")
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
	
	@Column(name="days")
	private BigDecimal days=new BigDecimal(0);//外出天数
	
	@Column(name="OUT_DESC")
	@Lob()
	private String outDesc;//外出原因
	
	@Column(name="ALLOW",columnDefinition="int  default 1")
	private int allow;//审批状态   0待审批    1已批准   2未批准
	
	@Column(name="STATUS",columnDefinition="int  default 1")
	private int status;//办理状态     1外出归来
	
	@Column(name="REASON")
	private String reason;//不批准原因
	
	@Column(name="REGISTER_IP")
	private String registerId;//申请IP

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public BigDecimal getDays() {
		return days;
	}

	public void setDays(BigDecimal days) {
		this.days = days;
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

	public String getOutDesc() {
		return outDesc;
	}

	public void setOutDesc(String outDesc) {
		this.outDesc = outDesc;
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
	
	
}
