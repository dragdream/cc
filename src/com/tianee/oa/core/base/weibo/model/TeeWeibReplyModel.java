package com.tianee.oa.core.base.weibo.model;


public class TeeWeibReplyModel {
	
    private int sid;//回复主键
	
	private String content;//回复内容
	
	private int plId;//评论id
	
	private int userId;//回复人id
	
	private String userName;//回复人姓名
	
	private String creTime;//回复时间
	
	private boolean dianZanReply;//是否给回复点赞
	
	private int personId;//被回复人id
	
	private String personName;//被回复人姓名
	
	private int countReply;//点赞次数
	
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getPlId() {
		return plId;
	}

	public void setPlId(int plId) {
		this.plId = plId;
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

	public boolean isDianZanReply() {
		return dianZanReply;
	}

	public void setDianZanReply(boolean dianZanReply) {
		this.dianZanReply = dianZanReply;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public int getCountReply() {
		return countReply;
	}

	public void setCountReply(int countReply) {
		this.countReply = countReply;
	}
	
	
	
}
