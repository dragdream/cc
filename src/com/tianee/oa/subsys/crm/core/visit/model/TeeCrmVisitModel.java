package com.tianee.oa.subsys.crm.core.visit.model;

import com.tianee.oa.webframe.httpModel.TeeBaseModel;

public class TeeCrmVisitModel extends TeeBaseModel{
	private int sid; //主键
	
	private String visitName;//拜访名称
	
	private int customerId; //客户
	
	private String customerName; //客户名称
	
	private String visitTopic;//拜访主题
	
	private String visitTopicDesc;//拜访主题
	
    private int managePersonId;  //负责人
	
	private String managePersonName;
	
	private int visitStatus;  //拜访状态
	
	private String visitStatusDesc;
	
	private int createUserId;//创建人
	
	private String createUserName;//
	
	private String createTimeDesc;//创建时间
	
	private String visitTimeDesc;//计划日期
	
	private String visitEndTimeDesc;//拜访完成时间
	
	private String lastEditTimeDesc;//最后变化时间

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}
	
	public String getVisitName() {
		return visitName;
	}

	public void setVisitName(String visitName) {
		this.visitName = visitName;
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

	public String getVisitTopic() {
		return visitTopic;
	}

	public void setVisitTopic(String visitTopic) {
		this.visitTopic = visitTopic;
	}

	public String getVisitTopicDesc() {
		return visitTopicDesc;
	}

	public void setVisitTopicDesc(String visitTopicDesc) {
		this.visitTopicDesc = visitTopicDesc;
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

	public int getVisitStatus() {
		return visitStatus;
	}

	public void setVisitStatus(int visitStatus) {
		this.visitStatus = visitStatus;
	}

	public String getVisitStatusDesc() {
		return visitStatusDesc;
	}

	public void setVisitStatusDesc(String visitStatusDesc) {
		this.visitStatusDesc = visitStatusDesc;
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

	public String getCreateTimeDesc() {
		return createTimeDesc;
	}

	public void setCreateTimeDesc(String createTimeDesc) {
		this.createTimeDesc = createTimeDesc;
	}

	public String getVisitTimeDesc() {
		return visitTimeDesc;
	}

	public void setVisitTimeDesc(String visitTimeDesc) {
		this.visitTimeDesc = visitTimeDesc;
	}

	public String getVisitEndTimeDesc() {
		return visitEndTimeDesc;
	}

	public void setVisitEndTimeDesc(String visitEndTimeDesc) {
		this.visitEndTimeDesc = visitEndTimeDesc;
	}

	public String getLastEditTimeDesc() {
		return lastEditTimeDesc;
	}

	public void setLastEditTimeDesc(String lastEditTimeDesc) {
		this.lastEditTimeDesc = lastEditTimeDesc;
	}
	
}
