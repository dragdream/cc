package com.tianee.oa.core.base.attend.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;



import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name = "ATTEND_DUTY_RECORD")
public class TeeAttendDutyRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="ATTEND_DUTY_RECORD_seq_gen")
	@SequenceGenerator(name="ATTEND_DUTY_RECORD_seq_gen", sequenceName="ATTEND_DUTY_RECORD_seq")
	@Column(name="SID")
	private int sid;
	
	@Column(name="complain_num")
	private int complainNum;//申诉次数
	
	@Column(name="perfect_Count")
	private int perfectCount;//全勤天数
	
	@Column(name="hours")
	private String hours;//上班时长
	
	
	@Column(name="work_On_No_Registers")
	private int workOnNoRegisters;//上班未登記
	
	
	@Column(name="late_Nums")
	private int lateNums;//迟到
	
	
	@Column(name="work_Out_No_Registers")
	private int workOutNoRegisters;//下班未登記
	
	
	@Column(name="leave_Early_Nums")
	private int leaveEarlyNums;//早退
	
	
	
	//请假天数、
	@Column(name="leave_Days")
	private double leaveDays;
	//外出天數
	@Column(name="out_Days")
	private double outDays;
	//出差天數
	@Column(name="evection_Days")
	private double evectionDays;
	//加班時長
	@Column(name="over_Hours")
	private double overHours;
	
	
	
	//统计月份
	@Column(name="month")
	private String month;
	
	@ManyToOne()
	@JoinColumn(name="USER_ID")
	private TeePerson user;//用戶
	
	
	
	@ManyToOne()
	@JoinColumn(name="DEPT_ID")
	private TeeDepartment dept;//部門


	
	@Column(name="assign_days")
	private int attendAssignDays=0;//外勤天数
	
	@Column(name="assign_nums")
	private int attendAssignNums=0;//外勤次数
	
	
	

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



	public TeePerson getUser() {
		return user;
	}



	public TeeDepartment getDept() {
		return dept;
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



	public void setUser(TeePerson user) {
		this.user = user;
	}



	public void setDept(TeeDepartment dept) {
		this.dept = dept;
	}



	public int getComplainNum() {
		return complainNum;
	}



	public void setComplainNum(int complainNum) {
		this.complainNum = complainNum;
	}
	
	
	
	
}
