package com.tianee.oa.core.base.news.model;

import java.util.Date;

import com.tianee.webframe.util.str.TeeUtility;

public class TeeNewsInfoModel {

	private int sid ; // 
	private String userName;
	private String deptName;
	private String roleName;
	private String stateDesc;
	private String readTime1;
	private int isRead = 0;
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public int getIsRead() {
		return isRead;
	}
	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}
	public String getStateDesc() {
		return stateDesc;
	}
	public void setStateDesc(String stateDesc) {
		this.stateDesc = stateDesc;
	}
	public String getReadTime1() {
		if(TeeUtility.isNullorEmpty(readTime1)){
			readTime1 = "";
		}
		return readTime1;
	}
	public void setReadTime1(String readTime1) {
		this.readTime1 = readTime1;
	}
	
	
}
