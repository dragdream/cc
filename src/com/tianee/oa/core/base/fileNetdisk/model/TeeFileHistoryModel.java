package com.tianee.oa.core.base.fileNetdisk.model;

public class TeeFileHistoryModel {

    private int sid;//自增id
	
    private int userId;
    
    private String userName;
    
	private String createTime;
	
	private int fileId;
	
	private int banben;
	
	private int attachIdOld;
	
	private int attachIdNew;
	
	private String sease;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public int getBanben() {
		return banben;
	}

	public void setBanben(int banben) {
		this.banben = banben;
	}

	public int getAttachIdOld() {
		return attachIdOld;
	}

	public void setAttachIdOld(int attachIdOld) {
		this.attachIdOld = attachIdOld;
	}

	public int getAttachIdNew() {
		return attachIdNew;
	}

	public void setAttachIdNew(int attachIdNew) {
		this.attachIdNew = attachIdNew;
	}

	public String getSease() {
		return sease;
	}

	public void setSease(String sease) {
		this.sease = sease;
	}

	
	
	
}
