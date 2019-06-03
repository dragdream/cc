package com.tianee.oa.subsys.crm.core.contracts.bean;

import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomer;
import com.tianee.oa.subsys.crm.core.order.bean.TeeCrmOrder;

/**
 * 合同实体
 * @author zhaocaigen
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "CRM_CONTRACTS")
public class TeeCrmContracts {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CRM_CONTRACTS_seq_gen")
	@SequenceGenerator(name="CRM_CONTRACTS_seq_gen", sequenceName="CRM_CONTRACTS_seq")
	@Column(name = "SID")
	private int sid;// 自增id
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	private TeeCrmCustomer customer;//关联客户
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ORDER_ID")
	private TeeCrmOrder order;  //关联订单
	
	@Column(name="CONTRACTS_NO")
	private String contractsNo;//合同编号
	
	@Column(name="CONTRACTS_TITLE")
	private String contractsTitle;//合同标题
	
	@Column(name = "CONTRACTS_STATUS", length = 100)
	private int contractsStatus;// 合同状态
	
	@Column(name = "CONTRACTS_AMOUNT")
	private double contractsAmount;//合同金额
	
	@Column(name = "CONTRACTS_START_DATE")
	private Calendar contractsStartDate;//合同开始日期
	
	@Column(name = "CONTRACTS_END_DATE")
	private Calendar contractsEndDate;//合同结束日期
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RESPONSIBLE_USER_ID")
	private TeePerson responsibleUser;//负责人（系统用户）
	
	@Column(name = "CREATE_TIME")
	private Calendar createTime;	// 创建时间
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATE_USER_ID")
	private TeePerson createUser;//创建者
	
	@Column(name="LAST_EDIT_TIME")
	private Calendar lastEditTime;//最后一次修改日期

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeeCrmCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(TeeCrmCustomer customer) {
		this.customer = customer;
	}

	public TeeCrmOrder getOrder() {
		return order;
	}

	public void setOrder(TeeCrmOrder order) {
		this.order = order;
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

	public double getContractsAmount() {
		return contractsAmount;
	}

	public void setContractsAmount(double contractsAmount) {
		this.contractsAmount = contractsAmount;
	}

	public Calendar getContractsStartDate() {
		return contractsStartDate;
	}

	public void setContractsStartDate(Calendar contractsStartDate) {
		this.contractsStartDate = contractsStartDate;
	}

	public Calendar getContractsEndDate() {
		return contractsEndDate;
	}

	public void setContractsEndDate(Calendar contractsEndDate) {
		this.contractsEndDate = contractsEndDate;
	}

	public TeePerson getResponsibleUser() {
		return responsibleUser;
	}

	public void setResponsibleUser(TeePerson responsibleUser) {
		this.responsibleUser = responsibleUser;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public TeePerson getCreateUser() {
		return createUser;
	}

	public void setCreateUser(TeePerson createUser) {
		this.createUser = createUser;
	}

	public Calendar getLastEditTime() {
		return lastEditTime;
	}

	public void setLastEditTime(Calendar lastEditTime) {
		this.lastEditTime = lastEditTime;
	}

}
