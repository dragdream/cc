package com.tianee.oa.core.base.diary.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;

public class TeeDiaryModel implements Serializable{
	private int sid;
	private String title;
	private String content;
	private int type = 1;
	private String typeDesc;
	private Calendar createTime;
	private String createTimeDesc;
	private Calendar writeTime;
	private String writeTimeDesc;
	private List<TeeAttachmentModel> attacheModels;
	
	private long attachmentCount = 0;//附件数量
	private List<TeeDiaryReplyModel> replyModels;
	private int createUserId;
	private String createUserName;
	private String shareRangesNames;
	private String shareRangesIds;
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getTypeDesc() {
		return typeDesc;
	}
	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}
	public Calendar getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}
	public String getCreateTimeDesc() {
		return createTimeDesc;
	}
	public void setCreateTimeDesc(String createTimeDesc) {
		this.createTimeDesc = createTimeDesc;
	}
	public List<TeeAttachmentModel> getAttacheModels() {
		return attacheModels;
	}
	public void setAttacheModels(List<TeeAttachmentModel> attacheModels) {
		this.attacheModels = attacheModels;
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
	public String getShareRangesNames() {
		return shareRangesNames;
	}
	public void setShareRangesNames(String shareRangesNames) {
		this.shareRangesNames = shareRangesNames;
	}
	public String getShareRangesIds() {
		return shareRangesIds;
	}
	public void setShareRangesIds(String shareRangesIds) {
		this.shareRangesIds = shareRangesIds;
	}
	public void setWriteTime(Calendar writeTime) {
		this.writeTime = writeTime;
	}
	public Calendar getWriteTime() {
		return writeTime;
	}
	public void setWriteTimeDesc(String writeTimeDesc) {
		this.writeTimeDesc = writeTimeDesc;
	}
	public String getWriteTimeDesc() {
		return writeTimeDesc;
	}
	public List<TeeDiaryReplyModel> getReplyModels() {
		return replyModels;
	}
	public void setReplyModels(List<TeeDiaryReplyModel> replyModels) {
		this.replyModels = replyModels;
	}
	public long getAttachmentCount() {
		return attachmentCount;
	}
	public void setAttachmentCount(long attachmentCount) {
		this.attachmentCount = attachmentCount;
	}
	
	
}
