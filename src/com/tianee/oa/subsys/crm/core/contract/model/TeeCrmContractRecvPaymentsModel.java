package com.tianee.oa.subsys.crm.core.contract.model;

import java.util.Date;

public class TeeCrmContractRecvPaymentsModel {
	private int sid;// 自增id
	private int recvPaymentSid;//临时Id
	private int managerUserId;//负责人Id
	private String managerUserName;//
	private String contractId;// 合同ID
	private String contractNo;//合同编号
	private String contractName;//合同名称
	private Date planRecvDate;// 预计回款日期
	private String planRecvDateStr;// 预计回款日期
	private double recvPayAmount;// 回款金额
	private double recvPayParcent;//回款百分比
	private Date recvDate;// 实际回款日期
	private String recvDateStr;// 实际回款日期
	private String recvPayPerson;//收款人
	private String recvPaymentFlag;//状态  0-未回款  1-已汇款
	private String remark;// 备注
	private String makeInvice;// 是否开发票
	private String inviceNumber;// 	发票号
	private Date inviceDate;// 发票日期
	private Date inviceSendDate;// 寄出发票日期
	private Date createTime;// 创建时间
	private String createTimeStr;// 创建时间
	private String contractCreateUser;// 合同创建人
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public Date getPlanRecvDate() {
		return planRecvDate;
	}
	public void setPlanRecvDate(Date planRecvDate) {
		this.planRecvDate = planRecvDate;
	}
	public double getRecvPayAmount() {
		return recvPayAmount;
	}
	public void setRecvPayAmount(double recvPayAmount) {
		this.recvPayAmount = recvPayAmount;
	}
	public double getRecvPayParcent() {
		return recvPayParcent;
	}
	public void setRecvPayParcent(double recvPayParcent) {
		this.recvPayParcent = recvPayParcent;
	}
	public Date getRecvDate() {
		return recvDate;
	}
	public void setRecvDate(Date recvDate) {
		this.recvDate = recvDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getMakeInvice() {
		return makeInvice;
	}
	public void setMakeInvice(String makeInvice) {
		this.makeInvice = makeInvice;
	}
	public String getInviceNumber() {
		return inviceNumber;
	}
	public void setInviceNumber(String inviceNumber) {
		this.inviceNumber = inviceNumber;
	}
	public Date getInviceDate() {
		return inviceDate;
	}
	public void setInviceDate(Date inviceDate) {
		this.inviceDate = inviceDate;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getRecvPayPerson() {
		return recvPayPerson;
	}
	public void setRecvPayPerson(String recvPayPerson) {
		this.recvPayPerson = recvPayPerson;
	}
	public int getRecvPaymentSid() {
		return recvPaymentSid;
	}
	public void setRecvPaymentSid(int recvPaymentSid) {
		this.recvPaymentSid = recvPaymentSid;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public Date getInviceSendDate() {
		return inviceSendDate;
	}
	public void setInviceSendDate(Date inviceSendDate) {
		this.inviceSendDate = inviceSendDate;
	}
	public String getRecvPaymentFlag() {
		return recvPaymentFlag;
	}
	public void setRecvPaymentFlag(String recvPaymentFlag) {
		this.recvPaymentFlag = recvPaymentFlag;
	}
	public int getManagerUserId() {
		return managerUserId;
	}
	public void setManagerUserId(int managerUserId) {
		this.managerUserId = managerUserId;
	}
	public String getManagerUserName() {
		return managerUserName;
	}
	public void setManagerUserName(String managerUserName) {
		this.managerUserName = managerUserName;
	}
	public String getContractCreateUser() {
		return contractCreateUser;
	}
	public void setContractCreateUser(String contractCreateUser) {
		this.contractCreateUser = contractCreateUser;
	}
	public String getPlanRecvDateStr() {
		return planRecvDateStr;
	}
	public void setPlanRecvDateStr(String planRecvDateStr) {
		this.planRecvDateStr = planRecvDateStr;
	}
	public String getRecvDateStr() {
		return recvDateStr;
	}
	public void setRecvDateStr(String recvDateStr) {
		this.recvDateStr = recvDateStr;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	
	
	
	
}
