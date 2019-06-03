package com.tianee.oa.core.general.bean;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@SuppressWarnings("serial")
@Entity
@Table(name = "SMS")
public class TeeSms {
	
	@Id
	@GeneratedValue(generator = "system-uuid")  
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(columnDefinition = "char(32)",name="UUID")
	private String uuid;//自增id
	
	@Column(name="to_id")
	private int toId;//接收人员uuid，不用对象型
	
	/**
	 *  0-未阅读
 		1-已阅读
 		2-弹出未阅读
	 */
	@Column(name="remind_flag",length=10)
	private int remindFlag;//提醒状态
	
	/**
	 *  0-没删除
		1-收信人删除
		2-发信人删除
	 */
	@Column(name="delete_flag",length=10)
	private int deleteFlag;//删除状态
	
	@Column(name="remind_time")
	private Date remindTime;//最近一次提醒时间

	@ManyToOne
	@Index(name="IDX3663c169ad34461bb8eac0662c5")
	@JoinColumn(name="body_id")
	private TeeSmsBody smsBody;
	/**
	 * 1-消息已发送
	 * 0-消息没有发送
	 * 2-消息 已同步
	 * 3-消息 没有同步
	 */
	@Column(name="XKP_FLAG",length=10)
	private int xkpFlag ;
	

	public int getXkpFlag() {
		return xkpFlag;
	}

	public void setXkpFlag(int xkpFlag) {
		this.xkpFlag = xkpFlag;
	}

	public int getToId() {
		return toId;
	}

	public void setToId(int toId) {
		this.toId = toId;
	}

	

	

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getRemindFlag() {
		return remindFlag;
	}

	public void setRemindFlag(int remindFlag) {
		this.remindFlag = remindFlag;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Date getRemindTime() {
		return remindTime;
	}

	public void setRemindTime(Date remindTime) {
		this.remindTime = remindTime;
	}

	public void setSmsBody(TeeSmsBody smsBody) {
		this.smsBody = smsBody;
	}

	public TeeSmsBody getSmsBody() {
		
		return smsBody;
	}

}
