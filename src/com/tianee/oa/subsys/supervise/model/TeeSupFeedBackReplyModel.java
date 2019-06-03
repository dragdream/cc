package com.tianee.oa.subsys.supervise.model;


public class TeeSupFeedBackReplyModel {
	
	private int sid;//自增id
	
	private String content ;//内容
	
	//private Date createTime ;//回复时间
	private String createTimeStr;
	
	//private TeePerson creater;//创建人
	private int createrId;
	private String createrName;
	
	
	
	private int fbId;//反馈主键
	private String fbTitle;//反馈标题
	
	
	public int getFbId() {
		return fbId;
	}
	public void setFbId(int fbId) {
		this.fbId = fbId;
	}
	public String getFbTitle() {
		return fbTitle;
	}
	public void setFbTitle(String fbTitle) {
		this.fbTitle = fbTitle;
	}
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
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public int getCreaterId() {
		return createrId;
	}
	public void setCreaterId(int createrId) {
		this.createrId = createrId;
	}
	public String getCreaterName() {
		return createrName;
	}
	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}
	
	
}
