package com.tianee.oa.core.base.attend.model;


public class TeeAttendDutyModel{
	
	private int sid;
	
	private int userId;
	
	private String registerType;
	
	private String registerTimeDesc;
	
	private String registerIp;
	
	private String remark;

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

	public String getRegisterType() {
		return registerType;
	}

	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}

	public String getRegisterTimeDesc() {
		return registerTimeDesc;
	}

	public void setRegisterTimeDesc(String registerTimeDesc) {
		this.registerTimeDesc = registerTimeDesc;
	}

	public String getRegisterIp() {
		return registerIp;
	}

	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	
}