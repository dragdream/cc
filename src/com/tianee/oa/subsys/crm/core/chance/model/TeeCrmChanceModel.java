package com.tianee.oa.subsys.crm.core.chance.model;


public class TeeCrmChanceModel {


	private int sid;//主键
	

	private String chanceName;//机会名称
    

	private String forcastTimeStr;//预计成交时间
	

	private String createTimeStr;//创建时间
	

	private Double forcastCost;//预计成交金额
	
	
	private int crUserId;//创建人id
	
	private String crUserName;//创建人姓名
	
    private int customerId;//关联客户id
    
    private String customerName;//关联客户姓名

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getChanceName() {
		return chanceName;
	}

	public void setChanceName(String chanceName) {
		this.chanceName = chanceName;
	}

	

	public Double getForcastCost() {
		return forcastCost;
	}

	public void setForcastCost(Double forcastCost) {
		this.forcastCost = forcastCost;
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

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getForcastTimeStr() {
		return forcastTimeStr;
	}

	public void setForcastTimeStr(String forcastTimeStr) {
		this.forcastTimeStr = forcastTimeStr;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	
}
