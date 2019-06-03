package com.tianee.oa.subsys.crm.core.contract.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;

public class TeeCrmContractModel {
	private int sid;// 自增id

	private String customerInfoId;// 客户Id
	private String customerInfoName;//客户名称
	private String contractName;// 合同名称
	private String contractNo;// 合同编号
	
	private String contractCode;// 合同类型 (CRM代码 ,   CRM_CONTRACT_CODE)
	private String contractCodeDesc;
	
	private String contractStatus;// 合同状态(CRM代码,CRM_ CURRENCY_STATU)
	private String contractStatusDesc;

	private String currencyType;// 货币类别(CRM代码, CRM_ CURRENCY_TYPE)
	private String currencyTypeDesc;
	
	private String accountsMethod;// 结算方式(CRM代码, CONTRACT_ACCOUNTS_METHOD)
	private String accountsMethodDesc;
	
	private double contractAmount;//成交金额

	private Date contractSignDate;//合同签订日期
	private String contractSignDateStr;//合同签订日期
	
	private Date contractStartDate;//合同开始日期
	private String contractStartDateStr;//合同开始日期
	
	private Date contractEndDate;//合同结束日期
	private String contractEndDateStr;//合同结束日期

	private String bueBank;//收款银行
	private String paymentMethod;// 付款方式 0-一次付清  1-多次付款
	private String paymentMethodDesc;

	private String remark;// 产品说明（备注）

	private String createUserId;
	private String createUserName;


	private String responsibleUserId;//责任人（系统用户）
	private String responsibleUserName;

	private Date createTime;	// 创建时间
	private String createTimeDesc;//
	
	List<TeeCrmContractProductItemModel> productItemModel = new ArrayList<TeeCrmContractProductItemModel>();
	List<TeeCrmContractRecvPaymentsModel> recvPaymentModel = new ArrayList<TeeCrmContractRecvPaymentsModel>();
	List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>();
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getCustomerInfoId() {
		return customerInfoId;
	}
	public void setCustomerInfoId(String customerInfoId) {
		this.customerInfoId = customerInfoId;
	}
	public String getCustomerInfoName() {
		return customerInfoName;
	}
	public void setCustomerInfoName(String customerInfoName) {
		this.customerInfoName = customerInfoName;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getContractCodeDesc() {
		return contractCodeDesc;
	}
	public void setContractCodeDesc(String contractCodeDesc) {
		this.contractCodeDesc = contractCodeDesc;
	}
	public String getContractStatus() {
		return contractStatus;
	}
	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}
	public String getContractStatusDesc() {
		return contractStatusDesc;
	}
	public void setContractStatusDesc(String contractStatusDesc) {
		this.contractStatusDesc = contractStatusDesc;
	}
	public String getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}
	public String getCurrencyTypeDesc() {
		return currencyTypeDesc;
	}
	public void setCurrencyTypeDesc(String currencyTypeDesc) {
		this.currencyTypeDesc = currencyTypeDesc;
	}
	public String getAccountsMethod() {
		return accountsMethod;
	}
	public void setAccountsMethod(String accountsMethod) {
		this.accountsMethod = accountsMethod;
	}
	public String getAccountsMethodDesc() {
		return accountsMethodDesc;
	}
	public void setAccountsMethodDesc(String accountsMethodDesc) {
		this.accountsMethodDesc = accountsMethodDesc;
	}
	public double getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(double contractAmount) {
		this.contractAmount = contractAmount;
	}
	public Date getContractSignDate() {
		return contractSignDate;
	}
	public void setContractSignDate(Date contractSignDate) {
		this.contractSignDate = contractSignDate;
	}
	public Date getContractStartDate() {
		return contractStartDate;
	}
	public void setContractStartDate(Date contractStartDate) {
		this.contractStartDate = contractStartDate;
	}
	public Date getContractEndDate() {
		return contractEndDate;
	}
	public void setContractEndDate(Date contractEndDate) {
		this.contractEndDate = contractEndDate;
	}
	public String getBueBank() {
		return bueBank;
	}
	public void setBueBank(String bueBank) {
		this.bueBank = bueBank;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getPaymentMethodDesc() {
		return paymentMethodDesc;
	}
	public void setPaymentMethodDesc(String paymentMethodDesc) {
		this.paymentMethodDesc = paymentMethodDesc;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getResponsibleUserId() {
		return responsibleUserId;
	}
	public void setResponsibleUserId(String responsibleUserId) {
		this.responsibleUserId = responsibleUserId;
	}
	public String getResponsibleUserName() {
		return responsibleUserName;
	}
	public void setResponsibleUserName(String responsibleUserName) {
		this.responsibleUserName = responsibleUserName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateTimeDesc() {
		return createTimeDesc;
	}
	public void setCreateTimeDesc(String createTimeDesc) {
		this.createTimeDesc = createTimeDesc;
	}
	public List<TeeCrmContractProductItemModel> getProductItemModel() {
		return productItemModel;
	}
	public void setProductItemModel(
			List<TeeCrmContractProductItemModel> productItemModel) {
		this.productItemModel = productItemModel;
	}
	public List<TeeCrmContractRecvPaymentsModel> getRecvPaymentModel() {
		return recvPaymentModel;
	}
	public void setRecvPaymentModel(
			List<TeeCrmContractRecvPaymentsModel> recvPaymentModel) {
		this.recvPaymentModel = recvPaymentModel;
	}
	public List<TeeAttachmentModel> getAttachmodels() {
		return attachmodels;
	}
	public void setAttachmodels(List<TeeAttachmentModel> attachmodels) {
		this.attachmodels = attachmodels;
	}
	public String getContractSignDateStr() {
		return contractSignDateStr;
	}
	public void setContractSignDateStr(String contractSignDateStr) {
		this.contractSignDateStr = contractSignDateStr;
	}
	public String getContractStartDateStr() {
		return contractStartDateStr;
	}
	public void setContractStartDateStr(String contractStartDateStr) {
		this.contractStartDateStr = contractStartDateStr;
	}
	public String getContractEndDateStr() {
		return contractEndDateStr;
	}
	public void setContractEndDateStr(String contractEndDateStr) {
		this.contractEndDateStr = contractEndDateStr;
	}
	
	
	
}
