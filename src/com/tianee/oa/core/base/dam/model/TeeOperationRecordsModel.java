package com.tianee.oa.core.base.dam.model;


public class TeeOperationRecordsModel {
	private int sid;
	//private TeeFiles file;//关联的档案
	private String fileName;//档案名称
	private int fileId;//档案主键
	
	//private TeePerson operUser;//操作人
	private int operUserId;//操作人主键
	private String operUserName;//操作人姓名
	
	//private Calendar operTime;//操作时间
	private  String operTimeStr;//操作时间
	
	private String content;//内容

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public int getOperUserId() {
		return operUserId;
	}

	public void setOperUserId(int operUserId) {
		this.operUserId = operUserId;
	}

	public String getOperUserName() {
		return operUserName;
	}

	public void setOperUserName(String operUserName) {
		this.operUserName = operUserName;
	}

	public String getOperTimeStr() {
		return operTimeStr;
	}

	public void setOperTimeStr(String operTimeStr) {
		this.operTimeStr = operTimeStr;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}
