package com.tianee.oa.core.base.email.bean;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WEB_MAIL_REC")
public class TeeWebMailRec {
	
	@Id
	@Column(name="UUID")
	private String uuid;//主键
	
	@Column(name="REC_USER")
	private int recUser;//接收人ID
	
	@Column(name="REC_TIME")
	private Calendar recTime;
	
	@Column(name="SEND_TIME")
	private Calendar sendTime;
	
	@Column(name="SUCCESS_")
	private int success;//0：成功  1：失败

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getRecUser() {
		return recUser;
	}

	public void setRecUser(int recUser) {
		this.recUser = recUser;
	}

	public Calendar getRecTime() {
		return recTime;
	}

	public void setRecTime(Calendar recTime) {
		this.recTime = recTime;
	}

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public Calendar getSendTime() {
		return sendTime;
	}

	public void setSendTime(Calendar sendTime) {
		this.sendTime = sendTime;
	}
	
	
}
