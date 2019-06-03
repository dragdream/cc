package com.tianee.oa.core.phoneSms.model;

import java.util.Calendar;

public class TeeSmsRecvPhoneModel{
	private int sid;//SID	int(11)	自增ID	auto_increment
	
	private String phone;//PHONE	varchar(20)	短信发送人手机号	谁回复的，显示谁的手机号，比如：10086等
	
	private String content;//CONTENT	text	短信内容	
	
	private String sendTimeDesc;//SEND_TIME	datetime	发送时间	

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

	public String getSendTimeDesc() {
		return sendTimeDesc;
	}

	public void setSendTimeDesc(String sendTimeDesc) {
		this.sendTimeDesc = sendTimeDesc;
	}

	
}