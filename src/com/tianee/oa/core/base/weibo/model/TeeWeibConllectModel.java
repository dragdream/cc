package com.tianee.oa.core.base.weibo.model;

public class TeeWeibConllectModel {

    private int sid;//收藏主键
	
    private int infoId;//发布信息id
	
	private int userId;//收藏人id
	
	private String infoTime;//收藏时间
	
	private boolean conllect;//是否收藏

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

	public String getInfoTime() {
		return infoTime;
	}

	public void setInfoTime(String infoTime) {
		this.infoTime = infoTime;
	}

	public boolean isConllect() {
		return conllect;
	}

	public void setConllect(boolean conllect) {
		this.conllect = conllect;
	}
	
	
}
