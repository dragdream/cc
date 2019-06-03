package com.tianee.oa.core.base.weibo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TeeWeibCommentModel {

    private int sid;//评论主键
	
    private int infoId;//发布信息id
	
	private int userId;//评论人id
	
	private String userName;//评论人姓名
	
	private String creTime;//评论时间
	
	private String content;//评论内容
	
	private int plNum;//评论点赞次数
	
	private String hfName;//第一个回复人的姓名
	
	private int hfNum;//回复次数
	
	private int avatar;//头像id
	
	private boolean pDianzan=false;
	
	private List<TeeWeibReplyModel> replyList=new ArrayList<TeeWeibReplyModel>();//微博信息的评论集合


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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getPlNum() {
		return plNum;
	}

	public void setPlNum(int plNum) {
		this.plNum = plNum;
	}

	public String getHfName() {
		return hfName;
	}

	public void setHfName(String hfName) {
		this.hfName = hfName;
	}

	public int getHfNum() {
		return hfNum;
	}

	public void setHfNum(int hfNum) {
		this.hfNum = hfNum;
	}

	public boolean ispDianzan() {
		return pDianzan;
	}

	public void setpDianzan(boolean pDianzan) {
		this.pDianzan = pDianzan;
	}

	public int getAvatar() {
		return avatar;
	}

	public void setAvatar(int avatar) {
		this.avatar = avatar;
	}

	public List<TeeWeibReplyModel> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<TeeWeibReplyModel> replyList) {
		this.replyList = replyList;
	}
	
	

}
