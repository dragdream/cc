package com.tianee.oa.core.attachment.model;


public class TeeOfficeSwitchModel {
	private int sid; //主键
	private int flag;//0：未转换  1：转换中 2：已转换  -1：转换失败
	
	//private TeeAttachment  attachment;//原附件
	private int attachmentId;
	private String attachmentName;
	//private TeeAttachment  htmlAtt;//html附件
	
	private int htmlAttId;
	private String htmlAttName;
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public int getAttachmentId() {
		return attachmentId;
	}
	public void setAttachmentId(int attachmentId) {
		this.attachmentId = attachmentId;
	}
	public String getAttachmentName() {
		return attachmentName;
	}
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}
	public int getHtmlAttId() {
		return htmlAttId;
	}
	public void setHtmlAttId(int htmlAttId) {
		this.htmlAttId = htmlAttId;
	}
	public String getHtmlAttName() {
		return htmlAttName;
	}
	public void setHtmlAttName(String htmlAttName) {
		this.htmlAttName = htmlAttName;
	}
	
	
	
	
}
