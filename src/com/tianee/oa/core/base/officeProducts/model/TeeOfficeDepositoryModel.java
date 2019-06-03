package com.tianee.oa.core.base.officeProducts.model;

public class TeeOfficeDepositoryModel {
	private int sid;
	private String deposName;//库名称
	private String deptsIds;
	private String deptsNames;
	private String adminsIds;
	private String adminsNames;
	private String operatorsIds;
	private String operatorsNames;
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getDeposName() {
		return deposName;
	}
	public void setDeposName(String deposName) {
		this.deposName = deposName;
	}
	public String getAdminsIds() {
		return adminsIds;
	}
	public void setAdminsIds(String adminsIds) {
		this.adminsIds = adminsIds;
	}
	public String getAdminsNames() {
		return adminsNames;
	}
	public void setAdminsNames(String adminsNames) {
		this.adminsNames = adminsNames;
	}
	public String getOperatorsIds() {
		return operatorsIds;
	}
	public void setOperatorsIds(String operatorsIds) {
		this.operatorsIds = operatorsIds;
	}
	public String getOperatorsNames() {
		return operatorsNames;
	}
	public void setOperatorsNames(String operatorsNames) {
		this.operatorsNames = operatorsNames;
	}
	public String getDeptsIds() {
		return deptsIds;
	}
	public void setDeptsIds(String deptsIds) {
		this.deptsIds = deptsIds;
	}
	public String getDeptsNames() {
		return deptsNames;
	}
	public void setDeptsNames(String deptsNames) {
		this.deptsNames = deptsNames;
	}
	
}
