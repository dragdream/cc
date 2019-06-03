package com.tianee.oa.core.base.attend.bean;


import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;

public class TeeAttendDutyRecordModel {

	private int sid;

	private int perfectCount;//全勤天数

	private String hours;//上班时长

	private int workOnNoRegisters;//上班未登記

	private int lateNums;//迟到

	private int workOutNoRegisters;//下班未登記
	
	private int leaveEarlyNums;//早退

	private double leaveDays;

	private double outDays;

	private double evectionDays;

	private double overHours;
	
	
	private String month;
	

	//private TeePerson user;//用戶
	private int userId;
	private String userName;
	
	
	//private TeeDepartment dept;//部門
	private int deptId;
	private String deptName;
	
	private int attendAssignDays;
	private int attendAssignNums;
	
	private  int complainNum;//申诉次数
	
	
	
	
	public int getComplainNum() {
		return complainNum;
	}
	public void setComplainNum(int complainNum) {
		this.complainNum = complainNum;
	}
	public int getAttendAssignDays() {
		return attendAssignDays;
	}
	public int getAttendAssignNums() {
		return attendAssignNums;
	}
	public void setAttendAssignDays(int attendAssignDays) {
		this.attendAssignDays = attendAssignDays;
	}
	public void setAttendAssignNums(int attendAssignNums) {
		this.attendAssignNums = attendAssignNums;
	}
	public int getSid() {
		return sid;
	}
	public int getPerfectCount() {
		return perfectCount;
	}
	public String getHours() {
		return hours;
	}
	public int getWorkOnNoRegisters() {
		return workOnNoRegisters;
	}
	public int getLateNums() {
		return lateNums;
	}
	public int getWorkOutNoRegisters() {
		return workOutNoRegisters;
	}
	public int getLeaveEarlyNums() {
		return leaveEarlyNums;
	}
	public double getLeaveDays() {
		return leaveDays;
	}
	public double getOutDays() {
		return outDays;
	}
	public double getEvectionDays() {
		return evectionDays;
	}
	public double getOverHours() {
		return overHours;
	}
	public String getMonth() {
		return month;
	}
	public int getUserId() {
		return userId;
	}
	public String getUserName() {
		return userName;
	}
	public int getDeptId() {
		return deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public void setPerfectCount(int perfectCount) {
		this.perfectCount = perfectCount;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	public void setWorkOnNoRegisters(int workOnNoRegisters) {
		this.workOnNoRegisters = workOnNoRegisters;
	}
	public void setLateNums(int lateNums) {
		this.lateNums = lateNums;
	}
	public void setWorkOutNoRegisters(int workOutNoRegisters) {
		this.workOutNoRegisters = workOutNoRegisters;
	}
	public void setLeaveEarlyNums(int leaveEarlyNums) {
		this.leaveEarlyNums = leaveEarlyNums;
	}
	public void setLeaveDays(double leaveDays) {
		this.leaveDays = leaveDays;
	}
	public void setOutDays(double outDays) {
		this.outDays = outDays;
	}
	public void setEvectionDays(double evectionDays) {
		this.evectionDays = evectionDays;
	}
	public void setOverHours(double overHours) {
		this.overHours = overHours;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	
	
}
