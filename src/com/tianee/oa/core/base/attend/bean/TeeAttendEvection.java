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
@Table(name = "ATTEND_EVECTION")
public class TeeAttendEvection {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="ATTEND_EVECTION_seq_gen")
	@SequenceGenerator(name="ATTEND_EVECTION_seq_gen", sequenceName="ATTEND_EVECTION_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@ManyToOne()
	@Index(name="IDX8ea9dd7bfa3f42cc8d245139a5b")
	@JoinColumn(name="USER_ID")
	private TeePerson user;//申请人
	
	@ManyToOne()
	@Index(name="IDX79c964ad7c034d7f8ff46c82e9b")
	@JoinColumn(name="LEADER_ID")
	private TeePerson leader;//审批人
	
	@Column(name="CREATE_TIME")
	private long createTime;//新建时间
	
	
	@Column(name="START_TIME")
	private long startTime;//STRAT_TIME

	@Column(name="END_TIME")
	private long endTime;//END_TIME
	
	@Column(name="days")
	private BigDecimal days=new BigDecimal(0);//出差天数
	
	
	@Column(name="EVECTION_ADDRESS")
	private String evectionAddress;//出差地址
	
	@Column(name="EVECTION_DESC")
	@Lob()
	private String evectionDesc;//出差原因
	
	@Column(name="ALLOW",columnDefinition="int default 1")
	private int allow;//审批状态
	
	@Column(name="STATUS",columnDefinition="int default 1")
	private int status;//办理状态
	
	@Column(name="REASON")
	private String reason;//不批准原因
	
	@Column(name="REGISTER_IP")
	private String registerId;//申请IP

	
	
	public BigDecimal getDays() {
		return days;
	}

	public void setDays(BigDecimal days) {
		this.days = days;
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

	public String getEvectionDesc() {
		return evectionDesc;
	}

	public void setEvectionDesc(String evectionDesc) {
		this.evectionDesc = evectionDesc;
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

	public String getEvectionAddress() {
		return evectionAddress;
	}

	public void setEvectionAddress(String evectionAddress) {
		this.evectionAddress = evectionAddress;
	}

	
	
}
