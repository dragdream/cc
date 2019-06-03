package com.tianee.oa.subsys.crm.core.base.model;

import java.util.List;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;

public class TeeCrmAfterSaleServModel {
	private int sid;// Sid int 11 否 主键

	private String serviceCode;// 服务编号

	private int customerInfoId;// 客户名称id
	private String customerInfoName;// 客户名称

	private int contactUserId;// 客户联系人Id
	private String contactUserName;// 客户联系人姓名

	private String serviceType;// 售后服务类型id CRM代码 CUSTOMER_SERVICE_TYPE
	private String serviceTypeName;//售后服务类型名称

	private String emergencyDegree;// 紧急程度
									// CRM代码表（CUSTOMER_SERVICE_EMERGENCY）

	private int accteptUserId;// 受理人
	private String accteptUserName;//

	private String acceptDatetimeStr;// 受理时间

	private String questionDesc;// 问题描述

	private int handleUserId;// 处理人
	private String handleUserName;

	private String handleDatetimeStr;// 处理时间

	private String handleStatus;// 完成状态

	private String handleDesc;// 处理结果

	private String feedback;// 反馈结果

	private int createUserId;// 创建人id
	private String createUserName;// 创建人名称

	private String createTimeStr;// 创建时间
	
	private List<TeeAttachmentModel> attacheModels;//附件
	private String attacheIds;//附件Ids字符串  以逗号分隔 
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
	public int getCustomerInfoId() {
		return customerInfoId;
	}
	public void setCustomerInfoId(int customerInfoId) {
		this.customerInfoId = customerInfoId;
	}
	public String getCustomerInfoName() {
		return customerInfoName;
	}
	public void setCustomerInfoName(String customerInfoName) {
		this.customerInfoName = customerInfoName;
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
	public int getAccteptUserId() {
		return accteptUserId;
	}
	public void setAccteptUserId(int accteptUserId) {
		this.accteptUserId = accteptUserId;
	}
	public String getAccteptUserName() {
		return accteptUserName;
	}
	public void setAccteptUserName(String accteptUserName) {
		this.accteptUserName = accteptUserName;
	}
	public String getAcceptDatetimeStr() {
		return acceptDatetimeStr;
	}
	public void setAcceptDatetimeStr(String acceptDatetimeStr) {
		this.acceptDatetimeStr = acceptDatetimeStr;
	}
	public String getQuestionDesc() {
		return questionDesc;
	}
	public void setQuestionDesc(String questionDesc) {
		this.questionDesc = questionDesc;
	}
	public int getHandleUserId() {
		return handleUserId;
	}
	public void setHandleUserId(int handleUserId) {
		this.handleUserId = handleUserId;
	}
	public String getHandleUserName() {
		return handleUserName;
	}
	public void setHandleUserName(String handleUserName) {
		this.handleUserName = handleUserName;
	}
	public String getHandleDatetimeStr() {
		return handleDatetimeStr;
	}
	public void setHandleDatetimeStr(String handleDatetimeStr) {
		this.handleDatetimeStr = handleDatetimeStr;
	}
	public String getHandleStatus() {
		return handleStatus;
	}
	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
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
	public int getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public List<TeeAttachmentModel> getAttacheModels() {
		return attacheModels;
	}
	public void setAttacheModels(List<TeeAttachmentModel> attacheModels) {
		this.attacheModels = attacheModels;
	}
	public String getAttacheIds() {
		return attacheIds;
	}
	public void setAttacheIds(String attacheIds) {
		this.attacheIds = attacheIds;
	}
	public String getServiceTypeName() {
		return serviceTypeName;
	}
	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}
	
	
}
