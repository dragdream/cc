package com.tianee.oa.core.base.message.model;

import java.util.Calendar;

import javax.persistence.Column;



import com.tianee.oa.webframe.httpModel.TeeBaseModel;


public class TeeMessageModel extends TeeBaseModel {

	private String uuid;//uuid主键
	
	private String toId;//收信人Id
	private String toIdName;
	
	private int remindFlag;//接收标记0-已阅读  1-未阅读  2-未阅读已弹出
	
	private String remindFlagDesc;
	
	private int fromDeleteFlag;//发件人删除标记 0-没删除1-已删除
	
	private int toDeleteFlag;//收件人 删除标记 0-没删除1-已删除
	
	private int messageType;//消息类型 默认0：即时通讯
	private String messageTypeDesc;
	
	
	private String sendTimeDesc;//发送时间
	
	private String fromId;//发信人
	private String fromIdName ;
	

	private String content;//内容
	private String ext;

	

	public int getRemindFlag() {
		return remindFlag;
	}

	public void setRemindFlag(int remindFlag) {
		this.remindFlag = remindFlag;
	}



	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	

	public String getToIdName() {
		return toIdName;
	}

	public void setToIdName(String toIdName) {
		this.toIdName = toIdName;
	}

	public String getRemindFlagDesc() {
		return remindFlagDesc;
	}

	public void setRemindFlagDesc(String remindFlagDesc) {
		this.remindFlagDesc = remindFlagDesc;
	}


	public String getMessageTypeDesc() {
		return messageTypeDesc;
	}

	public void setMessageTypeDesc(String messageTypeDesc) {
		this.messageTypeDesc = messageTypeDesc;
	}

	public String getSendTimeDesc() {
		return sendTimeDesc;
	}

	public void setSendTimeDesc(String sendTimeDesc) {
		this.sendTimeDesc = sendTimeDesc;
	}


	public String getFromIdName() {
		return fromIdName;
	}

	public void setFromIdName(String fromIdName) {
		this.fromIdName = fromIdName;
	}

	public int getFromDeleteFlag() {
		return fromDeleteFlag;
	}

	public void setFromDeleteFlag(int fromDeleteFlag) {
		this.fromDeleteFlag = fromDeleteFlag;
	}

	public int getToDeleteFlag() {
		return toDeleteFlag;
	}

	public void setToDeleteFlag(int toDeleteFlag) {
		this.toDeleteFlag = toDeleteFlag;
	}

	public String getToId() {
		return toId;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}

	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


}