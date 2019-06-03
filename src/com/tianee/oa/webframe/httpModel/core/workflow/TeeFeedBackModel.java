package com.tianee.oa.webframe.httpModel.core.workflow;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;

public class TeeFeedBackModel {

	private int sid;
	private int runId;
	private int prcsId;
	private int userId;
	private String content;
	private Calendar editTime;
	private String editTimeDesc;
	private int feedFlag;
	private int flowPrcsId;
	//private String signData; 这个不要有 太大了 已经改成异步获取了
	private int replayId;
	//private String userName;
	private String attachmentIds;
	private List attachList;
	private String prcsName;//步骤名称
	private boolean isHavaSignData;
	private String voiceId;
	private int backFlag;//回退标记
	private String userName;
	private String deptName;
	private String deptFullPath;
	private String roleName;
	
	
	
	public String getDeptName() {
		return deptName;
	}
	public String getDeptFullPath() {
		return deptFullPath;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public void setDeptFullPath(String deptFullPath) {
		this.deptFullPath = deptFullPath;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getRunId() {
		return runId;
	}
	public void setRunId(int runId) {
		this.runId = runId;
	}
	public int getPrcsId() {
		return prcsId;
	}
	public void setPrcsId(int prcsId) {
		this.prcsId = prcsId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Calendar getEditTime() {
		return editTime;
	}
	public void setEditTime(Calendar editTime) {
		this.editTime = editTime;
	}
	
	public int getFeedFlag() {
		return feedFlag;
	}
	public void setFeedFlag(int feedFlag) {
		this.feedFlag = feedFlag;
	}
	public int getReplayId() {
		return replayId;
	}
	public void setReplayId(int replayId) {
		this.replayId = replayId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAttachmentIds() {
		return attachmentIds;
	}
	public void setAttachmentIds(String attachmentIds) {
		this.attachmentIds = attachmentIds;
	}
	public List getAttachList() {
		return attachList;
	}
	public void setAttachList(List attachList) {
		this.attachList = attachList;
	}
	public boolean isHavaSignData() {
		return isHavaSignData;
	}
	public void setHavaSignData(boolean isHavaSignData) {
		this.isHavaSignData = isHavaSignData;
	}
	public void setEditTimeDesc(String editTimeDesc) {
		this.editTimeDesc = editTimeDesc;
	}
	public String getEditTimeDesc() {
		return editTimeDesc;
	}
	public void setFlowPrcsId(int flowPrcsId) {
		this.flowPrcsId = flowPrcsId;
	}
	public int getFlowPrcsId() {
		return flowPrcsId;
	}
	public void setPrcsName(String prcsName) {
		this.prcsName = prcsName;
	}
	public String getPrcsName() {
		return prcsName;
	}
	public String getVoiceId() {
		return voiceId;
	}
	public void setVoiceId(String voiceId) {
		this.voiceId = voiceId;
	}
	public int getBackFlag() {
		return backFlag;
	}
	public void setBackFlag(int backFlag) {
		this.backFlag = backFlag;
	}
	
	
}
