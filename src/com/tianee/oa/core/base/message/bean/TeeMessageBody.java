package com.tianee.oa.core.base.message.bean;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;

@Entity
@Table(name = "MESSAGE_BODY")
public class TeeMessageBody {
	@Id
	@GeneratedValue(generator = "system-uuid")  
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(columnDefinition = "char(32)",name="UUID")
	private String uuid;//自增id
	
	@Column(name="FROM_ID")
	@Index(name="MSG_FROM_ID")
	private String fromId;//发信人
	
	@Lob
	@Column(name="CONTENT")
	private String content;//内容
	
	@Column(name="MESSAGE_TYPE")
	private int messageType;//消息类型   1：即时通讯 2：安卓 
	
	@Column(name="SEND_TIME")
	private Calendar sendTime;//发送时间
	
	@Column(name="EXT")
	private String ext;//扩展
	
	@Lob
	@Column(name="ATTACH_IDS")
	private String attachIds;//消息附件
	
	@Column(name="DELETE_FLAG")
	private int deleteFlag;


	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	public Calendar getSendTime() {
		return sendTime;
	}

	public void setSendTime(Calendar sendTime) {
		this.sendTime = sendTime;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getAttachIds() {
		return attachIds;
	}

	public void setAttachIds(String attachIds) {
		this.attachIds = attachIds;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	
}
