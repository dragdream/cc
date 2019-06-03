package com.tianee.oa.core.base.message.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tianee.oa.webframe.httpModel.TeeBaseModel;

public class TeeMessageGroupModel extends TeeBaseModel {
	
	private int sid;//自增id
	private String toId;//群组人员
	private String toName;
	private int orderNo;//排序号

	private String groupName;//名称
	
	private String groupSubject;//团组主题
	
	private String groupIntroduction;//团组简介

	private int smsRemind;//使用微讯提醒

	private int sms2Remind;//使用手机短信提醒

	private int groupCreator;//新建人
	private String groupCreatorName;
	
	private int groupFlag;//群组状态
	
	private String toUserId;//群组人员，用户账号
	private String groupNotify;//团组公告

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

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public String getGroupCreatorName() {
		return groupCreatorName;
	}

	public void setGroupCreatorName(String groupCreatorName) {
		this.groupCreatorName = groupCreatorName;
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

	