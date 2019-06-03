package com.tianee.oa.core.base.meeting.model;

import javax.persistence.Entity;

@Entity
public class TeeMeetingTopicModel {
	private int sid;//自增id
	
	private String content;//内容
	
	private String crTimeDesc;//创建时间
	
	private int crUserId;//创建人
	private String crUserName;
	
	private int meetingSid;//关联会议

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

	public String getCrTimeDesc() {
		return crTimeDesc;
	}

	public void setCrTimeDesc(String crTimeDesc) {
		this.crTimeDesc = crTimeDesc;
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

	public int getMeetingSid() {
		return meetingSid;
	}

	public void setMeetingSid(int meetingSid) {
		this.meetingSid = meetingSid;
	}

}
