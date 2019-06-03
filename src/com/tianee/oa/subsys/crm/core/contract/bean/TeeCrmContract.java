package com.tianee.oa.subsys.crm.core.contract.bean;
import org.hibernate.annotations.Index;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomerInfo;

/**
 * 合同表
 * 
 * @author think
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "CRM_CONTRACT")
public class TeeCrmContract {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CRM_CONTRACT_seq_gen")
	@SequenceGenerator(name="CRM_CONTRACT_seq_gen", sequenceName="CRM_CONTRACT_seq")
	@Column(name = "SID")
	private int sid;// 自增id

	@ManyToOne()
	@Index(name="IDX4c3fdd6d9c824c85851b6bbe1bd")
	@JoinColumn(name = "CUSTOMER_INFO_ID")
	private TeeCrmCustomerInfo customerInfo;// 客户名称

	@Column(name = "CONTRACT_NAME")
	private String contractName;// 合同名称
	
	@Column(name = "CONTRACT_NO" , length = 100)
	private String contractNo;// 合同编号
	

	@Column(name = "CONTRACT_CODE" , length = 100)
	private String contractCode;// 合同类型 (CRM代码 ,   CONTRACT_CODE)

	@Column(name = "CONTRACT_STATUS", length = 100)
	private String contractStatus;// 合同状态(CRM代码,CURRENCY_STATUS)

	@Column(name = "CURRENCY_TYPE", length = 100)
	private String currencyType;// 货币类别(CRM代码, CURRENCY_TYPE)
	
	@Column(name = "ACCOUNTS_METHOD", length = 100)
	private String accountsMethod;// 结算方式(CRM代码, CONTRACT_ACCOUNTS_METHOD)
	
	@Column(name = "CONTRACT_AMOUNT")
	private double contractAmount;//成交金额

	@Column(name = "CONTRACT_SIGN_DATE")
	private Date contractSignDate;//合同签订日期
	
	@Column(name = "CONTRACT_START_DATE")
	private Date contractStartDate;//合同开始日期
	
	@Column(name = "CONTRACT_END_DATE")
	private Date contractEndDate;//合同结束日期
	
	@Column(name = "DUE_BANK")
	private String bueBank;//收款银行

	@Column(name = "PAYMENT_METHOD", columnDefinition="char(1) default 0")
	private String paymentMethod;// 付款方式 0-一次付清  1-多次付款
	
	@Lob
	@Column(name = "REMARK")
	private String remark;// 产品说明（备注）

	// 创建者id
	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name="IDX79535c4bd44346109d309aacbdb")
	@JoinColumn(name = "CREATE_USER")
	private TeePerson createUser;

	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name="IDX6021309f153247b6b8762d65e0a")
	@JoinColumn(name = "RESPONSIBLE_USER")
	private TeePerson responsibleUser;//责任人（系统用户）

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME")
	private Date createTime;	// 创建时间

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeeCrmCustomerInfo getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(TeeCrmCustomerInfo customerInfo) {
		this.customerInfo = customerInfo;
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

	public String getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public String getAccountsMethod() {
		return accountsMethod;
	}

	public void setAccountsMethod(String accountsMethod) {
		this.accountsMethod = accountsMethod;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public TeePerson getCreateUser() {
		return createUser;
	}

	public void setCreateUser(TeePerson createUser) {
		this.createUser = createUser;
	}

	public TeePerson getResponsibleUser() {
		return responsibleUser;
	}

	public void setResponsibleUser(TeePerson responsibleUser) {
		this.responsibleUser = responsibleUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


}
