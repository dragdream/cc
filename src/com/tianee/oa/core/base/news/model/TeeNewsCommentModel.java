package com.tianee.oa.core.base.news.model;


public class TeeNewsCommentModel {

	private int sid; // 自增ID
	private int parentId = 0;//跟帖用的字段
	private int newsId = 0;//新闻ID
	private String userId = "";//发表评论/回复的用户
	private String nickName = "";
	private String content = null;// 评论/回复内容
	private String strReTime = null;//评论/回复时间
	private int sortId;
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public int getNewsId() {
		return newsId;
	}
	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStrReTime() {
		return strReTime;
	}
	public void setStrReTime(String strReTime) {
		this.strReTime = strReTime;
	}
	public int getSortId() {
		return sortId;
	}
	public void setSortId(int sortId) {
		this.sortId = sortId;
	}
	
}
