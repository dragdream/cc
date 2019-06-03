package com.tianee.oa.core.phoneSms.bean;
import org.hibernate.annotations.Index;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "SMS_RECV_PHONE")
public class TeeSmsRecvPhone{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="SMS_RECV_PHONE_seq_gen")
	@SequenceGenerator(name="SMS_RECV_PHONE_seq_gen", sequenceName="SMS_RECV_PHONE_seq")
	@Column(name = "SID")
	private int sid;//SID	int(11)	自增ID	auto_increment
	
	@Column(name="PHONE")
	private String phone;//PHONE	varchar(20)	短信发送人手机号	谁回复的，显示谁的手机号，比如：10086等
	
	@Column(name="CONTENT")
	@Lob
	private String content;//CONTENT	text	短信内容	
	
	@Column(name="SEND_TIME")
	private Calendar sendTime;//SEND_TIME	datetime	发送时间	

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Calendar getSendTime() {
		return sendTime;
	}

	public void setSendTime(Calendar sendTime) {
		this.sendTime = sendTime;
	}
	
}
