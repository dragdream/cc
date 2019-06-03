package com.tianee.oa.core.base.vote.model;


public class TeeVoteItemPersonModel {
	private int sid;//自增id
	private int voteId;
	private int voteItemId;
	private String voteData;//
	private int userId;
	private String userName;//
	private String createDateStr;//创建时间
	
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getVoteItemId() {
		return voteItemId;
	}
	public void setVoteItemId(int voteItemId) {
		this.voteItemId = voteItemId;
	}
	public String getVoteData() {
		return voteData;
	}
	public void setVoteData(String voteData) {
		this.voteData = voteData;
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
	public String getCreateDateStr() {
		return createDateStr;
	}
	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}
	public int getVoteId() {
		return voteId;
	}
	public void setVoteId(int voteId) {
		this.voteId = voteId;
	}

	
	
}
