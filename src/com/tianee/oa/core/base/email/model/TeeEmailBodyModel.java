package com.tianee.oa.core.base.email.model;


public class TeeEmailBodyModel {

	private int sid;
	private int fromUserId;//发件人id
	private String fromUserName;//发件人姓名
	private String subject;//邮件标题
	private String content;//邮件内容 。HTML文本（UEditor的内容）
	private String sendTimeStr; //发送时间
	
	private  int pubType;//收件人规则     0=指定人员   1=所有人员
	
	
	public int getPubType() {
		return pubType;
	}
	public void setPubType(int pubType) {
		this.pubType = pubType;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(int fromUserId) {
		this.fromUserId = fromUserId;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSendTimeStr() {
		return sendTimeStr;
	}
	public void setSendTimeStr(String sendTimeStr) {
		this.sendTimeStr = sendTimeStr;
	}
	
	
	
	
	
	

	
	

}
