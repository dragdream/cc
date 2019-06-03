package com.tianee.oa.core.base.message.bean;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@SuppressWarnings("serial")
@Entity
@Table(name = "MESSAGE_GROUP")
public class TeeMessageGroup {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="MESSAGE_GROUP_seq_gen")
	@SequenceGenerator(name="MESSAGE_GROUP_seq_gen", sequenceName="MESSAGE_GROUP_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@Column(name="TO_ID" , length=1000)
	private String toId;//群组人员
	
	@Column(name="TO_USER_ID" , length=1000)
	private String toUserId;//群组人员，用户账号
	
	@Column(name="ORDER_NO" )
	private int orderNo;//排序号
	
	@Column(name="GROUP_NAME" )
	private String groupName;//名称
	
	@Column(name="GROUP_SUBJECT" )
	private String groupSubject;//团组主题
	
	@Column(name="GROUP_INTRODUCTION" )
	private String groupIntroduction;//团组简介
	
	@Column(name="GROUP_NOTIFY" )
	private String groupNotify;//团组公告
	
	@Column(name="SMS_REMIND" )
	private int smsRemind;//使用微讯提醒
	
	@Column(name=" SMS2_REMIND" )
	private int sms2Remind;//使用手机短信提醒
	
	@Column(name=" GROUP_CREATOR" )
	private int groupCreator;//新建人
	
	@Column(name=" GROUP_FALG" )
	private int groupFlag;//群组状态

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getToId() {
		return toId;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupSubject() {
		return groupSubject;
	}

	public void setGroupSubject(String groupSubject) {
		this.groupSubject = groupSubject;
	}

	public String getGroupIntroduction() {
		return groupIntroduction;
	}

	public void setGroupIntroduction(String groupIntroduction) {
		this.groupIntroduction = groupIntroduction;
	}

	public int getSmsRemind() {
		return smsRemind;
	}

	public void setSmsRemind(int smsRemind) {
		this.smsRemind = smsRemind;
	}

	public int getSms2Remind() {
		return sms2Remind;
	}

	public void setSms2Remind(int sms2Remind) {
		this.sms2Remind = sms2Remind;
	}

	public int getGroupCreator() {
		return groupCreator;
	}

	public void setGroupCreator(int groupCreator) {
		this.groupCreator = groupCreator;
	}

	public int getGroupFlag() {
		return groupFlag;
	}

	public void setGroupFlag(int groupFlag) {
		this.groupFlag = groupFlag;
	}

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	public String getGroupNotify() {
		return groupNotify;
	}

	public void setGroupNotify(String groupNotify) {
		this.groupNotify = groupNotify;
	}
	
	
}

	
