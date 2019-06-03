package com.tianee.oa.subsys.crm.core.base.bean;
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

import org.hibernate.annotations.Index;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomerInfo;

/**
 * 售后服务
 * 
 * @author think
 * 
 */
@Entity
@Table(name = "CRM_AFTER_SALE_SERVICE")
public class TeeCrmAfterSaleServ {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CRM_AF_SA_SER_seq_gen")
	@SequenceGenerator(name="CRM_AF_SA_SER_seq_gen", sequenceName="CRM_AF_SA_SER_seq")
	@Column(name = "SID")
	private int sid;// Sid 主键

	@Column(name = "SERVICE_CODE")
	private String serviceCode;// 服务编号

	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name="IDX691f5ab0d949496c9d8b77be20b")
	@JoinColumn(name = "CUSTOMER_ID")
	private TeeCrmCustomerInfo customer;// Customer_ID  否 客户表外键（客户名称）

	@Column(name = "CONTACT_USER_ID")
	private int contactUserId;// 客户联系人Id

	@Column(name = "CONTACT_USER_NAME")
	private String contactUserName;// 客户联系人姓名

	@Column(name = "SERVICE_TYPE", length = 50)
	private String serviceType;// 售后服务类型 CRM代码 CUSTOMER_SERVICE_TYPE

	@Column(name = "EMERGENCY_DEGREE", length = 50)
	private String emergencyDegree;// 紧急程度
									// CRM代码表（CUSTOMER_SERVICE_EMERGENCY）

	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name="IDXafa11ce1375e451f89e7e833562")
	@JoinColumn(name = "ACCEPT_USER_ID")
	private TeePerson accteptUser;// 受理人

	@Column(name = "ACCEPT_DATETIME")
	private Date acceptDatetime;// 受理时间

	@Lob
	@Column(name = "QUESTION_DESC")
	private String questionDesc;// 问题描述

	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name="IDXc15d9fc81e2f4ed3aef228465ad")
	@JoinColumn(name = "HANDLE_USER_ID")
	private TeePerson handleUser;// 处理人

	@Column(name = "HANDLE_DATETIME")
	private Date handleDatetime;// 处理时间

	@Lob
	@Column(name = "HANDLE_DESC")
	private String handleDesc;// 处理结果

	@Lob
	@Column(name = "FEEDBACK")
	private String feedback;// 反馈结果

	@Column(name = "HANDLE_STATUS", columnDefinition = "char(1) default 0")
	private String handleStatus;// 完成状态 0-未完成；1-已完成

	// 创建者id
	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name="IDX82a86e8f4b964d5c8191a031fa9")
	@JoinColumn(name = "CREATE_USER")
	private TeePerson createUser;

	// 创建时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME")
	private Date createTime;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public TeeCrmCustomerInfo getCustomer() {
		return customer;
	}

	public void setCustomer(TeeCrmCustomerInfo customer) {
		this.customer = customer;
	}

	public int getContactUserId() {
		return contactUserId;
	}

	public void setContactUserId(int contactUserId) {
		this.contactUserId = contactUserId;
	}

	public String getContactUserName() {
		return contactUserName;
	}

	public void setContactUserName(String contactUserName) {
		this.contactUserName = contactUserName;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getEmergencyDegree() {
		return emergencyDegree;
	}

	public void setEmergencyDegree(String emergencyDegree) {
		this.emergencyDegree = emergencyDegree;
	}

	public TeePerson getAccteptUser() {
		return accteptUser;
	}

	public void setAccteptUser(TeePerson accteptUser) {
		this.accteptUser = accteptUser;
	}

	public Date getAcceptDatetime() {
		return acceptDatetime;
	}

	public void setAcceptDatetime(Date acceptDatetime) {
		this.acceptDatetime = acceptDatetime;
	}

	public String getQuestionDesc() {
		return questionDesc;
	}

	public void setQuestionDesc(String questionDesc) {
		this.questionDesc = questionDesc;
	}

	public TeePerson getHandleUser() {
		return handleUser;
	}

	public void setHandleUser(TeePerson handleUser) {
		this.handleUser = handleUser;
	}

	public Date getHandleDatetime() {
		return handleDatetime;
	}

	public void setHandleDatetime(Date handleDatetime) {
		this.handleDatetime = handleDatetime;
	}

	public String getHandleDesc() {
		return handleDesc;
	}

	public void setHandleDesc(String handleDesc) {
		this.handleDesc = handleDesc;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public String getHandleStatus() {
		return handleStatus;
	}

	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
	}

	public TeePerson getCreateUser() {
		return createUser;
	}

	public void setCreateUser(TeePerson createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	

}
