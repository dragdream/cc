package com.tianee.oa.subsys.topic.model;

import java.util.List;

import javax.persistence.Column;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;

public class TeeTopicReplyModel {
	private String uuid;// 自增id
	private String content;// 回复内容
	private String topicId;// 所属话题Id;
	private String sectionId;// 所属版块Id;
	private long clickAccount;// 点击数量
	private int flag;//回复状态   0：已屏蔽  1：有效
	public int getAnonymous() {
		return anonymous;
	}
	public void setAnonymous(int anonymous) {
		this.anonymous = anonymous;
	}
	private int anonymous;//是否匿名发布1为匿名0为不匿名

	private String crTimeStr;// 创建时间
	private int crUserId;// 创建人
	private String crUserName;// 创建人名称
	private String avatar;// 自定义头像
	private List<TeeAttachmentModel> attacheModels;//附件
	private String attacheIds;//附件Ids字符串  以逗号分隔 
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTopicId() {
		return topicId;
	}
	public void setTopicId(String topicId) {
		this.topicId = topicId;
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
	public String getSectionId() {
		return sectionId;
	}
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	
	
	
	
}
