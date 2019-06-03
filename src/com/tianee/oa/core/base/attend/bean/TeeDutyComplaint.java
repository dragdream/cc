package com.tianee.oa.core.base.attend.bean;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 考勤申诉
 * @author xsy
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "duty_complaint")
public class TeeDutyComplaint {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="duty_complaint_seq_gen")
	@SequenceGenerator(name="duty_complaint_seq_gen", sequenceName="duty_complaint_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	
	@ManyToOne()
	@JoinColumn(name="USER_ID")
	private TeePerson user;//创建人
	
	
	@ManyToOne()
	@JoinColumn(name="APPROVE_USER_ID")
	private TeePerson approver;//审批人
	
	@Column(name="CREATE_TIME")
	private Calendar createTime;//创建时间 
	
	@Column(name="STATUS_")
	private int status;//审批状态   0待审批    1已批准   2未批准
	
	@Column(name="REGISTER_NUM")
	private int registerNum;//针对的是第几次登记   值为 1,2,3,4,5,6
	
	@Column(name="REASON")
	private String reason;//异常原因
	
	//针对哪一天
	@Column(name="REMARK_TIME_STR")
	private String remarkTimeStr;//记录时间

	
	
	
	public TeePerson getApprover() {
		return approver;
	}

	public void setApprover(TeePerson approver) {
		this.approver = approver;
	}

	public int getSid() {
		return sid;
	}

	public TeePerson getUser() {
		return user;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public int getStatus() {
		return status;
	}

	public int getRegisterNum() {
		return registerNum;
	}

	public String getReason() {
		return reason;
	}

	public String getRemarkTimeStr() {
		return remarkTimeStr;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public void setUser(TeePerson user) {
		this.user = user;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setRegisterNum(int registerNum) {
		this.registerNum = registerNum;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setRemarkTimeStr(String remarkTimeStr) {
		this.remarkTimeStr = remarkTimeStr;
	}
	
	
	
}
