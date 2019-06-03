package com.tianee.oa.core.general.model;


public class TeeMysqlBackupModel {
	
	private int sid;
	
	private String timeDesc;//操作时间
	

	private String path;//路径


	public int getSid() {
		return sid;
	}


	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getTimeDesc() {
		return timeDesc;
	}


	public void setTimeDesc(String timeDesc) {
		this.timeDesc = timeDesc;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}
	
}
