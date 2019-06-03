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
@Table(name = "ATTEND_LEAVE")
public class TeeAttendLeave {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="ATTEND_LEAVE_seq_gen")
	@SequenceGenerator(name="ATTEND_LEAVE_seq_gen", sequenceName="ATTEND_LEAVE_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@ManyToOne()
	@Index(name="IDXbc726b4eb5d04dc5b9fd03c5426")
	@JoinColumn(name="USER_ID")
	private TeePerson user;//申请人
	
	@ManyToOne()
	@Index(name="IDX4515962e0f7a46afabf64bba14f")
	@JoinColumn(name="LEADER_ID")
	private TeePerson leader;//审批人
	
	@Column(name="CREATE_TIME")
	private long createTime;//新建时间
	
	@Column(name="START_TIME")
	private long startTime;//STRAT_TIME

	@Column(name="END_TIME")
	private long endTime;//END_TIME
	
	@Column(name="DESTROY_TIME")
	private long destroyTime;//申请销毁时间
	
	@Column(name="ANNUAL_LEAVE")
	private BigDecimal annualLeave = new BigDecimal(0);//请假天数
	
	@Column(name="ALLOW",columnDefinition="int  default 1")
	private int allow;//审批状态
	
	@Column(name="STATUS",columnDefinition="int  default 1")
	private int status;//办理状态
	
	@Column(name="REASON")
	private String reason;//不批准原因
	
	@Column(name="REGISTER_IP")
	private String registerId;//申请IP
	
	@Column(name="LEAVE_DESC", length = 500)
	@Lob()
	private String leaveDesc;//请假原因
	
	/**
	 * <option value="1">事假</option>
				<option value="2">病假</option>
				<option value="3">年假</option>
				<option value="5">工伤假</option>
				<option value="6">婚假</option>
				<option value="7">丧假</option>
				<option value="8">产假</option>
				<option value="9">探亲假</option>
				<option value="10">公假</option>
				<option value="4">其他</option>
	 */
	@Column(name="LEAVE_TYPE")
	private int leaveType;//请假类型

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

	

	public BigDecimal getAnnualLeave() {
		return annualLeave;
	}

	public void setAnnualLeave(BigDecimal annualLeave) {
		this.annualLeave = annualLeave;
	}

	public long getDestroyTime() {
		return destroyTime;
	}

	public void setDestroyTime(long destroyTime) {
		this.destroyTime = destroyTime;
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

	public String getLeaveDesc() {
		return leaveDesc;
	}

	public void setLeaveDesc(String leaveDesc) {
		this.leaveDesc = leaveDesc;
	}

	public int getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(int leaveType) {
		this.leaveType = leaveType;
	}
	
	
	
}


	
	
