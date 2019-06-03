package com.tianee.oa.core.partthree.model;


public class TeePartThreeRuleModel {

	private int sid;//自增id
	private String ruleCode;//规则代码
	private String ruleDesc;//规则描述
	private int operPriv;//操作权限
	private String operPrivDesc;//操作权限描述
	private int isOpen;//开启状态
	public int getSid() {
		return sid;
	}
	public String getRuleCode() {
		return ruleCode;
	}
	public String getRuleDesc() {
		return ruleDesc;
	}
	public int getOperPriv() {
		return operPriv;
	}
	public String getOperPrivDesc() {
		return operPrivDesc;
	}
	public int getIsOpen() {
		return isOpen;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}
	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}
	public void setOperPriv(int operPriv) {
		this.operPriv = operPriv;
	}
	public void setOperPrivDesc(String operPrivDesc) {
		this.operPrivDesc = operPrivDesc;
	}
	public void setIsOpen(int isOpen) {
		this.isOpen = isOpen;
	}
	
	
}
