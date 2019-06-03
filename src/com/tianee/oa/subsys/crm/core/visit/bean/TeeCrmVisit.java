package com.tianee.oa.subsys.crm.core.visit.bean;

import java.util.Calendar;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomer;

/**
 * 拜访实体
 * @author zhaocaigen
 *
 */
@Entity
@Table(name="CRM_VISIT")
public class TeeCrmVisit {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CRM_VISIT_seq_gen")
	@SequenceGenerator(name="CRM_VISIT_seq_gen", sequenceName="CRM_VISIT_seq")
	@Column(name = "SID")
	private int sid;// 自增id
	
	@Column(name="VISIT_NAME")
	private String visitName;//拜访名称
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	private TeeCrmCustomer customer;//关联客户
	
	@Column(name="VISIT_TOPIC")
	private String visitTopic;//拜访主题
	
	@Column(name="VISIT_TIME")
	private Calendar visitTime;//计划日期
	
	@Column(name="VISIT_END_TIME")
	private Calendar visitEndTime;//拜访完成时间
	
	@Column(name="VISIT_STATUS")
	private int visitStatus;//拜访状态（未完成、已完成）
	
	@Column(name = "CREATE_TIME")
	private Calendar createTime;	// 创建时间
	
	@Column(name = "LAST_EDIT_TIME")
	private Calendar lastEditTime;	// 最后变化日期
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATE_USER_ID")
	private TeePerson createUser;//创建者
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MANAGER_USER_ID")
	private TeePerson managePerson;//  负责人

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

	public String getVisitTopic() {
		return visitTopic;
	}

	public void setVisitTopic(String visitTopic) {
		this.visitTopic = visitTopic;
	}

	public Calendar getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Calendar visitTime) {
		this.visitTime = visitTime;
	}

	public int getVisitStatus() {
		return visitStatus;
	}

	public void setVisitStatus(int visitStatus) {
		this.visitStatus = visitStatus;
	}

	public Calendar getVisitEndTime() {
		return visitEndTime;
	}

	public void setVisitEndTime(Calendar visitEndTime) {
		this.visitEndTime = visitEndTime;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public Calendar getLastEditTime() {
		return lastEditTime;
	}

	public void setLastEditTime(Calendar lastEditTime) {
		this.lastEditTime = lastEditTime;
	}

	public TeePerson getCreateUser() {
		return createUser;
	}

	public void setCreateUser(TeePerson createUser) {
		this.createUser = createUser;
	}

	public TeePerson getManagePerson() {
		return managePerson;
	}

	public void setManagePerson(TeePerson managePerson) {
		this.managePerson = managePerson;
	}

	public String getVisitName() {
		return visitName;
	}

	public void setVisitName(String visitName) {
		this.visitName = visitName;
	}
	
}
