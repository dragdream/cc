package com.tianee.oa.core.base.attend.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;

public class TeeAttendOutModel {
	private int sid;//自增id
	private String deptId;
	private String deptName;//申请 部门
	private String userId;//申请人
	private String userName;//申请人姓名
	private String leaderId;//审批人
	private String leaderName;//审批人姓名
	private Date createDate;//新建时间
	private String createTimeStr;//新建时间字符串
	private Date submitDate;//外出 时间
	private String submitTimeStr;//外出时间字符串
	private String startTimeStr;//STRAT_TIME
	private String endTimeStr;//END_TIME
	private String outDesc;//外出原因
	private int allow;//审批状态
	private int status;//办理状态
	private String reason;//不批准原因
	private String registerId;//申请IP
	private BigDecimal days=new BigDecimal(0);//外出天数
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLeaderId() {
		return leaderId;
	}
	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}
	public String getLeaderName() {
		return leaderName;
	}
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public Date getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}
	public String getSubmitTimeStr() {
		return submitTimeStr;
	}
	public void setSubmitTimeStr(String submitTimeStr) {
		this.submitTimeStr = submitTimeStr;
	}
	public String getStartTimeStr() {
		return startTimeStr;
	}
	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}
	public String getEndTimeStr() {
		return endTimeStr;
	}
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
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
