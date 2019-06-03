package com.tianee.oa.subsys.report.bean;
import org.hibernate.annotations.Index;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;

@Entity
@Table(name="REPORT_CONDITION")
public class TeeReportCondition {
	@Id
	@Column(name="SID")
	@GeneratedValue(strategy=GenerationType.AUTO,generator="REPORT_CONDITION_seq_gen")
	@SequenceGenerator(name="REPORT_CONDITION_seq_gen", sequenceName="REPORT_CONDITION_seq")
	private int sid;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDX623b3336ef2f4398a1ce0178e63")
	@JoinColumn(name="FLOW_ID")
	private TeeFlowType flowType;
	
	@Column(name="CONDITION_NAME")
	private String conditionName;//条件名称
	
	@Column(name="FLOW_FLAG")
	private int flowFlag;//流程状态
	
	@Column(name="BEGIN_USER")
	private String beginUser;//发起人
	
	@Column(name="BEGIN_DEPT")
	private String beginDept;//发起人部门
	
	@Column(name="BEGIN_ROLE")
	private String beginRole;//发起人角色
	
	@Column(name="TIME_RANGE")
	private int timeRange;//时间范围
	
	private Calendar time1;
	
	private Calendar time2;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeeFlowType getFlowType() {
		return flowType;
	}

	public void setFlowType(TeeFlowType flowType) {
		this.flowType = flowType;
	}

	public String getConditionName() {
		return conditionName;
	}

	public void setConditionName(String conditionName) {
		this.conditionName = conditionName;
	}

	public int getFlowFlag() {
		return flowFlag;
	}

	public void setFlowFlag(int flowFlag) {
		this.flowFlag = flowFlag;
	}

	public String getBeginUser() {
		return beginUser;
	}

	public void setBeginUser(String beginUser) {
		this.beginUser = beginUser;
	}

	public String getBeginDept() {
		return beginDept;
	}

	public void setBeginDept(String beginDept) {
		this.beginDept = beginDept;
	}

	public String getBeginRole() {
		return beginRole;
	}

	public void setBeginRole(String beginRole) {
		this.beginRole = beginRole;
	}

	public int getTimeRange() {
		return timeRange;
	}

	public void setTimeRange(int timeRange) {
		this.timeRange = timeRange;
	}

	public Calendar getTime1() {
		return time1;
	}

	public void setTime1(Calendar time1) {
		this.time1 = time1;
	}

	public Calendar getTime2() {
		return time2;
	}

	public void setTime2(Calendar time2) {
		this.time2 = time2;
	}
	
}
