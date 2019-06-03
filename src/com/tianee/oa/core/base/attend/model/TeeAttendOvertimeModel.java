package com.tianee.oa.core.base.attend.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;

public class TeeAttendOvertimeModel {
	private int sid;//自增idi
	private String deptId;
	private String deptName;//申请 部门
	private int userId;//申请人
	private String userName;
	private int leaderId;//审批人
	private String leaderName;
	private Date createTimeDate;
	private String  createTimeDesc;//新建时间
	private Date submitTimeDate;
	private String submitTimeDesc;//登记时间
	private Date startTimeDate;
	private String startTimeDesc;//STRAT_TIME
	private Date endTimeeDate;
	private String endTimeDesc;//END_TIME
	private String overtimeDesc;//加班内容
	private int allow;//审批状态
	private String allowDesc;
	private int status;//办理状态
	private String statusDesc;
	private String reason;//不批准原因
	private String registerId;//申请IP
	private int hours;//加班小时数
	private int minute;//加班分钟数
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
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getLeaderId() {
		return leaderId;
	}
	public void setLeaderId(int leaderId) {
		this.leaderId = leaderId;
	}
	public String getLeaderName() {
		return leaderName;
	}
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}
	public String getCreateTimeDesc() {
		return createTimeDesc;
	}
	public void setCreateTimeDesc(String createTimeDesc) {
		this.createTimeDesc = createTimeDesc;
	}
	public String getSubmitTimeDesc() {
		return submitTimeDesc;
	}
	public void setSubmitTimeDesc(String submitTimeDesc) {
		this.submitTimeDesc = submitTimeDesc;
	}
	public String getStartTimeDesc() {
		return startTimeDesc;
	}
	public void setStartTimeDesc(String startTimeDesc) {
		this.startTimeDesc = startTimeDesc;
	}
	public String getEndTimeDesc() {
		return endTimeDesc;
	}
	public void setEndTimeDesc(String endTimeDesc) {
		this.endTimeDesc = endTimeDesc;
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
	public String getAllowDesc() {
		return allowDesc;
	}
	public void setAllowDesc(String allowDesc) {
		this.allowDesc = allowDesc;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
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
	public Date getCreateTimeDate() {
		return createTimeDate;
	}
	public void setCreateTimeDate(Date createTimeDate) {
		this.createTimeDate = createTimeDate;
	}
	public Date getSubmitTimeDate() {
		return submitTimeDate;
	}
	public void setSubmitTimeDate(Date submitTimeDate) {
		this.submitTimeDate = submitTimeDate;
	}
	public Date getStartTimeDate() {
		return startTimeDate;
	}
	public void setStartTimeDate(Date startTimeDate) {
		this.startTimeDate = startTimeDate;
	}
	public Date getEndTimeeDate() {
		return endTimeeDate;
	}
	public void setEndTimeeDate(Date endTimeeDate) {
		this.endTimeeDate = endTimeeDate;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	
}
