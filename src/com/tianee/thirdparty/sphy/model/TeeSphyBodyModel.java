package com.tianee.thirdparty.sphy.model;

/**
 * 视频会议
 * */
public class TeeSphyBodyModel {
	
	private int sid;//自增id
	
	private int sphyId;//会议id
	
	private int status;//参加人的类型 1主持人2普通用户3助理
	
	private String cjTime;//进入会议时间
	
	private int cjStatus;//是否参加会议
	
	//参加人
	private int cjUser;
	
	private String cjUserName;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getSphyId() {
		return sphyId;
	}

	public void setSphyId(int sphyId) {
		this.sphyId = sphyId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCjTime() {
		return cjTime;
	}

	public void setCjTime(String cjTime) {
		this.cjTime = cjTime;
	}

	public int getCjStatus() {
		return cjStatus;
	}

	public void setCjStatus(int cjStatus) {
		this.cjStatus = cjStatus;
	}

	public int getCjUser() {
		return cjUser;
	}

	public void setCjUser(int cjUser) {
		this.cjUser = cjUser;
	}

	public String getCjUserName() {
		return cjUserName;
	}

	public void setCjUserName(String cjUserName) {
		this.cjUserName = cjUserName;
	}

}
