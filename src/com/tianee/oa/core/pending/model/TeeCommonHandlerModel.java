package com.tianee.oa.core.pending.model;


public class TeeCommonHandlerModel {
	private String uuid; //主键
	private String pendingTitle;//待办名称
	private String pendingContent;//待办内容
	private String url;//办理地址
	private String model;//判断系统
	private int recUserIds; //办理人id
	private String recUserName;//办理人
	private int sendUserIds;//发布人id
	private String sendUserName;//发布人
	private int state;//更新状态
	private String createTime ;//创建时间
	private String yiUrl;//已办地址

	
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getYiUrl() {
		return yiUrl;
	}

	public void setYiUrl(String yiUrl) {
		this.yiUrl = yiUrl;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getPendingTitle() {
		return pendingTitle;
	}

	public void setPendingTitle(String pendingTitle) {
		this.pendingTitle = pendingTitle;
	}

	public String getPendingContent() {
		return pendingContent;
	}

	public void setPendingContent(String pendingContent) {
		this.pendingContent = pendingContent;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	

	public int getState() {
		return state;
	}

	public int getRecUserIds() {
		return recUserIds;
	}

	public void setRecUserIds(int recUserIds) {
		this.recUserIds = recUserIds;
	}

	public int getSendUserIds() {
		return sendUserIds;
	}

	public void setSendUserIds(int sendUserIds) {
		this.sendUserIds = sendUserIds;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getRecUserName() {
		return recUserName;
	}

	public void setRecUserName(String recUserName) {
		this.recUserName = recUserName;
	}

	public String getSendUserName() {
		return sendUserName;
	}

	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}

	
	
}
