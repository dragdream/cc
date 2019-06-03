package com.tianee.oa.subsys.budget.model;

public class TeeBudgetRegModel {
	private String uuid;//单据号，主键,自动生成，注意：非int型
	private int opUserId;//申请人
	private String opUserName;//申请人名称
	private int opDeptId;//申请人部门,包括可选择他的辅助部门
	private String opDeptName;//部门名称
	private int type;//记录类型    1：预算申请    2：报销
	private double amount;//申请金额
	private String reason;//申请原由id  （=》系统代码表 BUDGET_REG_REASON）
	private String reasonDesc;//申请原由  名称
	private String remark;//申请原由描述
	private String crTimeDesc;//申请时间
	private int regType;//申请类型，1：个人预算   2：部门预算
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public int getOpUserId() {
		return opUserId;
	}
	public void setOpUserId(int opUserId) {
		this.opUserId = opUserId;
	}
	public String getOpUserName() {
		return opUserName;
	}
	public void setOpUserName(String opUserName) {
		this.opUserName = opUserName;
	}
	public int getOpDeptId() {
		return opDeptId;
	}
	public void setOpDeptId(int opDeptId) {
		this.opDeptId = opDeptId;
	}
	public String getOpDeptName() {
		return opDeptName;
	}
	public void setOpDeptName(String opDeptName) {
		this.opDeptName = opDeptName;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCrTimeDesc() {
		return crTimeDesc;
	}
	public void setCrTimeDesc(String crTimeDesc) {
		this.crTimeDesc = crTimeDesc;
	}
	public int getRegType() {
		return regType;
	}
	public void setRegType(int regType) {
		this.regType = regType;
	}
	public String getReasonDesc() {
		return reasonDesc;
	}
	public void setReasonDesc(String reasonDesc) {
		this.reasonDesc = reasonDesc;
	}
	
	
}
