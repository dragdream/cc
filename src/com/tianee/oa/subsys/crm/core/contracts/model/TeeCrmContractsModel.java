package com.tianee.oa.subsys.crm.core.contracts.model;


import com.tianee.webframe.controller.BaseController;

public class TeeCrmContractsModel extends BaseController {
	
	private int sid;
	
    private int customerId;//Customer_ID	Int	11	否	客户表外键
	
	private String customerName;//客户名称
	
	private int orderId;  //订单id
	
	private String orderNo;//订单编号
	
	private String contractsNo;//合同编号
	
	private String contractsTitle;//合同标题
	
	private int contractsStatus;// 合同状态
	
	private String contractsStatusDesc;// 合同状态
	
	private double contractsAmount;//合同金额
	
	private String contractsStartTimeDesc;//合同开始日期
	
	private String contractsEndTimeDesc;//合同结束日期
	
	private int addPersonId;//创建人Id
		
	private String addPersonName;// 创建人名称
		
	private String createTimeDesc;//创建时间
		
	private int managePersonId;//  负责人
		
	private String managePersonName;//  负责人
		
	private String lastEditTimeDesc;//最后一次修改日期

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
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

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getContractsNo() {
		return contractsNo;
	}

	public void setContractsNo(String contractsNo) {
		this.contractsNo = contractsNo;
	}

	public String getContractsTitle() {
		return contractsTitle;
	}

	public void setContractsTitle(String contractsTitle) {
		this.contractsTitle = contractsTitle;
	}

	public int getContractsStatus() {
		return contractsStatus;
	}

	public void setContractsStatus(int contractsStatus) {
		this.contractsStatus = contractsStatus;
	}

	public String getContractsStatusDesc() {
		return contractsStatusDesc;
	}

	public void setContractsStatusDesc(String contractsStatusDesc) {
		this.contractsStatusDesc = contractsStatusDesc;
	}

	public double getContractsAmount() {
		return contractsAmount;
	}

	public void setContractsAmount(double contractsAmount) {
		this.contractsAmount = contractsAmount;
	}

	public String getContractsStartTimeDesc() {
		return contractsStartTimeDesc;
	}

	public void setContractsStartTimeDesc(String contractsStartTimeDesc) {
		this.contractsStartTimeDesc = contractsStartTimeDesc;
	}

	public String getContractsEndTimeDesc() {
		return contractsEndTimeDesc;
	}

	public void setContractsEndTimeDesc(String contractsEndTimeDesc) {
		this.contractsEndTimeDesc = contractsEndTimeDesc;
	}

	public int getAddPersonId() {
		return addPersonId;
	}

	public void setAddPersonId(int addPersonId) {
		this.addPersonId = addPersonId;
	}

	public String getAddPersonName() {
		return addPersonName;
	}

	public void setAddPersonName(String addPersonName) {
		this.addPersonName = addPersonName;
	}

	public String getCreateTimeDesc() {
		return createTimeDesc;
	}

	public void setCreateTimeDesc(String createTimeDesc) {
		this.createTimeDesc = createTimeDesc;
	}

	public int getManagePersonId() {
		return managePersonId;
	}

	public void setManagePersonId(int managePersonId) {
		this.managePersonId = managePersonId;
	}

	public String getManagePersonName() {
		return managePersonName;
	}

	public void setManagePersonName(String managePersonName) {
		this.managePersonName = managePersonName;
	}

	public String getLastEditTimeDesc() {
		return lastEditTimeDesc;
	}

	public void setLastEditTimeDesc(String lastEditTimeDesc) {
		this.lastEditTimeDesc = lastEditTimeDesc;
	}
	

}
