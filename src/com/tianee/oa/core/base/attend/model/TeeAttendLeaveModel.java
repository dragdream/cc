package com.tianee.oa.core.base.attend.model;

import java.math.BigDecimal;
import java.util.Date;

public class TeeAttendLeaveModel {
	private int sid;//自增id
	private String deptId;
	private String deptName;//申请 部门
	private String userId;//申请人
	private String userName;//申请人姓名
	private String leaderId;//审批人
	private String leaderName;//审批人姓名
	private Date createDate;//新建时间
	private String createTimeStr;//新建时间字符串
	private Date startDate;
	private String startTimeStr;//STRAT_TIME
	private Date endDate;
	private String endTimeStr;//END_TIME
	private String destroyTimeStr;
	private Date destroyDate;//申请销毁时间
	private String leaveDesc;//原因
	private int allow;//审批状态
	private int status;//办理状态
	private String reason;//不批准原因
	private String registerId;//申请IP
	private BigDecimal annualLeave=new BigDecimal(0);//请假天数
	private int leaveType;//请假类型
	private String leaveTypeDesc;//请假类型名称
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
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
	public String getLeaveDesc() {
		return leaveDesc;
	}
	public void setLeaveDesc(String leaveDesc) {
		this.leaveDesc = leaveDesc;
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
	
	public BigDecimal getAnnualLeave() {
		return annualLeave;
	}
	public void setAnnualLeave(BigDecimal annualLeave) {
		this.annualLeave = annualLeave;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getDestroyTimeStr() {
		return destroyTimeStr;
	}
	public void setDestroyTimeStr(String destroyTimeStr) {
		this.destroyTimeStr = destroyTimeStr;
	}
	public Date getDestroyDate() {
		return destroyDate;
	}
	public void setDestroyDate(Date destroyDate) {
		this.destroyDate = destroyDate;
	}
	public int getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(int leaveType) {
		this.leaveType = leaveType;
	}
	public String getLeaveTypeDesc() {
		return leaveTypeDesc;
	}
	public void setLeaveTypeDesc(String leaveTypeDesc) {
		this.leaveTypeDesc = leaveTypeDesc;
	}
	
	
}
