package com.tianee.oa.core.base.pm.model;


/**
 * 复职管理
 *
 */
public class TeeHumanRehabModel {
	private int sid;
	
	private String pos;//担任职务
	
	/**
	 * 调回
	 * 复原
	 */
	private String rehabType;//离职类型
	
	private String rehabTypeDesc;
	
	public String getRehabTypeDesc() {
		return rehabTypeDesc;
	}

	public void setRehabTypeDesc(String rehabTypeDesc) {
		this.rehabTypeDesc = rehabTypeDesc;
	}

	private String regTimeDesc;//申请日期
	
	private String planTimeDesc;//拟复职日期
	
	private String realTimeDesc;//实际复职日期
	
	private String payTimeDesc;//工资恢复日期
	
	private String rehabDept;//复职部门
	
	private String rehabDetail;//复职手续办理
	
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

	public String getRehabType() {
		return rehabType;
	}

	public void setRehabType(String rehabType) {
		this.rehabType = rehabType;
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

	public String getRehabDept() {
		return rehabDept;
	}

	public void setRehabDept(String rehabDept) {
		this.rehabDept = rehabDept;
	}

	public String getRehabDetail() {
		return rehabDetail;
	}

	public void setRehabDetail(String rehabDetail) {
		this.rehabDetail = rehabDetail;
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

	
	
	
}
