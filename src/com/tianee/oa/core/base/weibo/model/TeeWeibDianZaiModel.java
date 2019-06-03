package com.tianee.oa.core.base.weibo.model;

import java.util.Date;

public class TeeWeibDianZaiModel {

    private int sid;//点赞主键
	
    private int infoId;//发布信息id
	
	private int userId;//点赞人id
	
	private String userName;//点赞人姓名
	
	private String creTime;//点赞时间
	
	private boolean dianzai;//是否已经点赞
	
	private int avatar;//头像
	
	private int replyId;//评论id

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

	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCreTime() {
		return creTime;
	}

	public void setCreTime(String creTime) {
		this.creTime = creTime;
	}

	public boolean isDianzai() {
		return dianzai;
	}

	public void setDianzai(boolean dianzai) {
		this.dianzai = dianzai;
	}

	public int getReplyId() {
		return replyId;
	}

	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}

	public int getAvatar() {
		return avatar;
	}

	public void setAvatar(int avatar) {
		this.avatar = avatar;
	}
	
	
}
