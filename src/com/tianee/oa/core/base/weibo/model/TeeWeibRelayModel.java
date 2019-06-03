package com.tianee.oa.core.base.weibo.model;

public class TeeWeibRelayModel {

    private int sid;//转发主键
	
    private int infoId;//发布信息id
	
	private int userId;//转发人id
	
	private String creTime;//转发时间
	
	private int relayId;//转发信息id
	
	private String userName;//转发人姓名
	
	private int avatar;//头像

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getInfoId() {
		return infoId;
	}

	public void setInfoId(int infoId) {
		this.infoId = infoId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getCreTime() {
		return creTime;
	}

	public void setCreTime(String creTime) {
		this.creTime = creTime;
	}

	public int getRelayId() {
		return relayId;
	}

	public void setRelayId(int relayId) {
		this.relayId = relayId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getAvatar() {
		return avatar;
	}

	public void setAvatar(int avatar) {
		this.avatar = avatar;
	}
	
	
}
