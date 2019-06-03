package com.tianee.oa.subsys.topic.model;

import java.util.List;

import javax.persistence.Column;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;

public class TeeTopicModel {
	private String uuid;// 自增id
	private String subject;// 主题
	private String content;// 内容
	private String topicSectionId;// 所属版块Id;
	private String topicSectionName;// 所属版块名称;
	private String topicSectionManager;// 所属版块版主
    private String topicSectionManagerId;//所属版主id
	public String getTopicSectionManagerId() {
		return topicSectionManagerId;
	}
	public void setTopicSectionManagerId(String topicSectionManagerId) {
		this.topicSectionManagerId = topicSectionManagerId;
	}
	private int lastReplyUserId;// 最后回复的用户Id;
	private String lastReplyUserName;// 最后回复的用户名称
	private String lastReplyTimeStr;// 最后回复时间
	private long replyAmount;// 回复数量
	private int isTop;// 是否置顶 0-否；1-是
	private long clickAccount;// 点击数量
	private int flag;// 帖子状态 0：已删除至回收站 1：已发布（有效）
	public int getAnonymous() {
		return anonymous;
	}
	public void setAnonymous(int anonymous) {
		this.anonymous = anonymous;
	}
	private int anonymous;//是否匿名发布1为匿名0为不匿名

	private String crTimeStr;// 创建时间
	private String avatar;// 用户头像
	private int crUserId;// 创建人
	private String crUserName;// 创建名称
	private List<TeeAttachmentModel> attacheModels;//附件
	private String attacheIds;//附件Ids字符串  以逗号分隔 
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTopicSectionId() {
		return topicSectionId;
	}
	public void setTopicSectionId(String topicSectionId) {
		this.topicSectionId = topicSectionId;
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
	public long getReplyAmount() {
		return replyAmount;
	}
	public void setReplyAmount(long replyAmount) {
		this.replyAmount = replyAmount;
	}
	public int getIsTop() {
		return isTop;
	}
	public void setIsTop(int isTop) {
		this.isTop = isTop;
	}
	public long getClickAccount() {
		return clickAccount;
	}
	public void setClickAccount(long clickAccount) {
		this.clickAccount = clickAccount;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getCrTimeStr() {
		return crTimeStr;
	}
	public void setCrTimeStr(String crTimeStr) {
		this.crTimeStr = crTimeStr;
	}
	public int getCrUserId() {
		return crUserId;
	}
	public void setCrUserId(int crUserId) {
		this.crUserId = crUserId;
	}
	public String getCrUserName() {
		return crUserName;
	}
	public void setCrUserName(String crUserName) {
		this.crUserName = crUserName;
	}
	public List<TeeAttachmentModel> getAttacheModels() {
		return attacheModels;
	}
	public void setAttacheModels(List<TeeAttachmentModel> attacheModels) {
		this.attacheModels = attacheModels;
	}
	public String getAttacheIds() {
		return attacheIds;
	}
	public void setAttacheIds(String attacheIds) {
		this.attacheIds = attacheIds;
	}
	public String getTopicSectionManager() {
		return topicSectionManager;
	}
	public void setTopicSectionManager(String topicSectionManager) {
		this.topicSectionManager = topicSectionManager;
	}
	public String getTopicSectionName() {
		return topicSectionName;
	}
	public void setTopicSectionName(String topicSectionName) {
		this.topicSectionName = topicSectionName;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	
	
}
