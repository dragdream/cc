package com.tianee.oa.core.base.email.bean;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;

import org.hibernate.annotations.GenericGenerator;

/**
 * 外部邮件外发任务表
 * @author kakalion
 *
 */
@Entity
@Table(name="WEB_MAIL_SEND_TASK")
public class TeeWebMailSendTask {
	@Id
	@GeneratedValue(generator = "system-uuid")  
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(columnDefinition = "char(32)",name="UUID")
	private String uuid;//自增id
	
	@Column(name="MAIL_BODY_ID")
	private int mailBodyId;//mailBodyId
	
	@Column(name="FROM_")
	private int from;//从某内部邮箱发送
	
	@Column(name="TO_")
	private String to;//目标邮箱
	
	@Column(name="STATUS_")
	private int status;//发送状态  -1：发送失败  0：未发送  1：发送成功  2：正在发送
	
	@Column(name="MESSAGE_")
	private String message;//回馈信息

	@Column(name="SEND_NUMBER")
	private int sendNumber=0;//发送次数
	
	public int getSendNumber() {
		return sendNumber;
	}

	public void setSendNumber(int sendNumber) {
		this.sendNumber = sendNumber;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getMailBodyId() {
		return mailBodyId;
	}

	public void setMailBodyId(int mailBodyId) {
		this.mailBodyId = mailBodyId;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
