package com.tianee.oa.subsys.topic.model;

import javax.persistence.Column;


public class TeeTopicSectionModel {
	private String uuid;// 自增id
	private String sectionName;// 版块名称
	
	private String manager;// 版主id
	private String managerName;// 版主名称
	private boolean managerPrivFlag;// 版主 true-有权限；false-无权限
	
	private String crPriv;// 发帖权限 ALL：全体人员 非ALL（即1，32，42，23）逗号分隔
	private String crPrivName;// 发帖权限 ALL：全体人员 非ALL（即1，32，42，23）逗号分隔
	private int crPrivAllFlag;// 发帖权限 标记  1-全体人员；0-指定人员
	private boolean crPrivFlag;// 发帖权限 true-有权限；false-无权限
	
	private String viewPrivId;// 版块可见人员id
	private String viewPrivName;// 版块可见人员名称
	private int viewPrivAllFlag;// 版块可见人员标记 1：全部 0：非全部
	private boolean viewPrivFlag;// 版块可见 true-有权限；false-无权限
	
	private int newTopicSmsPriv = 0;// 新发贴时给可见人员发送系统消息
	private int replySmsPriv = 0;// 有跟贴时给楼主发送系统消息
	private int orderNo = 0;// 版块排序
	private String remark;// 备注
	private String createTimeStr;// 创建时间
	private int createUserId;// 申请人
	private String createUserName;// 申请人名称
	private int topicCount = 0;//话题总数
	private long topicReplyCount = 0;//话题文章 总数
	public int getAnonymous() {
		return anonymous;
	}
	public void setAnonymous(int anonymous) {
		this.anonymous = anonymous;
	}
	private int anonymous;//是否匿名发布1为匿名0为不匿名
	
	private String lastTopicSubject;// 最后回复主题
	private int lastReplyUserId;// 最后回复的用户Id;
	private String lastReplyUserName;// 最后回复的用户名称
	private String lastReplyTimeStr;// 最后回复时间
	private String lastTopicId;// 最后回复主题
	
	
	
	
	
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getCrPriv() {
		return crPriv;
	}
	public void setCrPriv(String crPriv) {
		this.crPriv = crPriv;
	}
	public String getCrPrivName() {
		return crPrivName;
	}
	public void setCrPrivName(String crPrivName) {
		this.crPrivName = crPrivName;
	}


	public String getViewPrivId() {
		return viewPrivId;
	}
	public void setViewPrivId(String viewPrivId) {
		this.viewPrivId = viewPrivId;
	}
	public String getViewPrivName() {
		return viewPrivName;
	}
	public void setViewPrivName(String viewPrivName) {
		this.viewPrivName = viewPrivName;
	}
	public int getNewTopicSmsPriv() {
		return newTopicSmsPriv;
	}
	public void setNewTopicSmsPriv(int newTopicSmsPriv) {
		this.newTopicSmsPriv = newTopicSmsPriv;
	}
	public int getReplySmsPriv() {
		return replySmsPriv;
	}
	public void setReplySmsPriv(int replySmsPriv) {
		this.replySmsPriv = replySmsPriv;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public int getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public int getViewPrivAllFlag() {
		return viewPrivAllFlag;
	}
	public void setViewPrivAllFlag(int viewPrivAllFlag) {
		this.viewPrivAllFlag = viewPrivAllFlag;
	}
	public int getCrPrivAllFlag() {
		return crPrivAllFlag;
	}
	public void setCrPrivAllFlag(int crPrivAllFlag) {
		this.crPrivAllFlag = crPrivAllFlag;
	}
	public int getTopicCount() {
		return topicCount;
	}
	public void setTopicCount(int topicCount) {
		this.topicCount = topicCount;
	}
	public long getTopicReplyCount() {
		return topicReplyCount;
	}
	public void setTopicReplyCount(long topicReplyCount) {
		this.topicReplyCount = topicReplyCount;
	}
	public int getLastReplyUserId() {
		return lastReplyUserId;
	}
	public void setLastReplyUserId(int lastReplyUserId) {
		this.lastReplyUserId = lastReplyUserId;
	}
	public String getLastReplyUserName() {
		return lastReplyUserName;
	}
	public void setLastReplyUserName(String lastReplyUserName) {
		this.lastReplyUserName = lastReplyUserName;
	}
	public String getLastReplyTimeStr() {
		return lastReplyTimeStr;
	}
	public void setLastReplyTimeStr(String lastReplyTimeStr) {
		this.lastReplyTimeStr = lastReplyTimeStr;
	}
	public String getLastTopicSubject() {
		return lastTopicSubject;
	}
	public void setLastTopicSubject(String lastTopicSubject) {
		this.lastTopicSubject = lastTopicSubject;
	}
	public String getLastTopicId() {
		return lastTopicId;
	}
	public void setLastTopicId(String lastTopicId) {
		this.lastTopicId = lastTopicId;
	}
	public boolean isManagerPrivFlag() {
		return managerPrivFlag;
	}
	public void setManagerPrivFlag(boolean managerPrivFlag) {
		this.managerPrivFlag = managerPrivFlag;
	}
	public boolean isCrPrivFlag() {
		return crPrivFlag;
	}
	public void setCrPrivFlag(boolean crPrivFlag) {
		this.crPrivFlag = crPrivFlag;
	}
	public boolean isViewPrivFlag() {
		return viewPrivFlag;
	}
	public void setViewPrivFlag(boolean viewPrivFlag) {
		this.viewPrivFlag = viewPrivFlag;
	}

	

}
