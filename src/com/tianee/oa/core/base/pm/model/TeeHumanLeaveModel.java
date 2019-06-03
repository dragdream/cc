package com.tianee.oa.core.base.pm.model;


/**
 * 离职管理
 *
 */
public class TeeHumanLeaveModel {
	private int sid;
	
	private String pos;//担任职务
	
	/**
	 * 辞职
	 * 离休
	 * 退休
	 * 借调
	 */
	private String leaveType;//离职类型
	
	private String leaveTypeDesc;
	
	private String regTimeDesc;//申请日期
	
	private String planTimeDesc;//拟离职日期
	
	private String realTimeDesc;//实际离职日期
	
	private String payTimeDesc;//工资截止日期
	
	private String leaveDept;//离职部门
	
	private String forward;//去向
	
	private String leaveDetail;//离职手续办理
	
	private String remark;//备注
	
	private int humanDocSid;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}


	public String getRegTimeDesc() {
		return regTimeDesc;
	}

	public void setRegTimeDesc(String regTimeDesc) {
		this.regTimeDesc = regTimeDesc;
	}

	public String getPlanTimeDesc() {
		return planTimeDesc;
	}

	public void setPlanTimeDesc(String planTimeDesc) {
		this.planTimeDesc = planTimeDesc;
	}

	public String getRealTimeDesc() {
		return realTimeDesc;
	}

	public void setRealTimeDesc(String realTimeDesc) {
		this.realTimeDesc = realTimeDesc;
	}

	public String getPayTimeDesc() {
		return payTimeDesc;
	}

	public void setPayTimeDesc(String payTimeDesc) {
		this.payTimeDesc = payTimeDesc;
	}

	public String getLeaveDept() {
		return leaveDept;
	}

	public void setLeaveDept(String leaveDept) {
		this.leaveDept = leaveDept;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public String getLeaveDetail() {
		return leaveDetail;
	}

	public void setLeaveDetail(String leaveDetail) {
		this.leaveDetail = leaveDetail;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getHumanDocSid() {
		return humanDocSid;
	}

	public void setHumanDocSid(int humanDocSid) {
		this.humanDocSid = humanDocSid;
	}

	public String getLeaveTypeDesc() {
		return leaveTypeDesc;
	}

	public void setLeaveTypeDesc(String leaveTypeDesc) {
		this.leaveTypeDesc = leaveTypeDesc;
	}

	
	
}
