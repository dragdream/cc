package com.tianee.oa.subsys.budget.model;


public class TeeUserExcessBudgetModel {
	private String uuid;//主键
	private int userId;//用户id
	private String userName;//用户名称
	private String year;//年度   例如  2014
	private String month;//月份  例如 01 02 03 11 12 等
	private double excessAmount; //超金额
	private int crUserId;//创建人id
	private String crUserName;//创建人名称
	
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}


	public double getExcessAmount() {
		return excessAmount;
	}
	public void setExcessAmount(double excessAmount) {
		this.excessAmount = excessAmount;
	}
	public int getCrUserId() {
		return crUserId;
	}
	public void setCrUserId(int crUserId) {
		this.crUserId = crUserId;
	}
	public String getCrUserName() {
		return crUserName;
	}
	public void setCrUserName(String crUserName) {
		this.crUserName = crUserName;
	}
	
	
	
}
