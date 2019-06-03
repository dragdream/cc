package com.tianee.oa.core.workflow.flowrun.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.raq.dm.Calendar;
import com.tianee.oa.core.org.bean.TeePerson;

public class TeeFlowRunDocVersionModel {

	private int sid;
	private int versionNo;//版本号
	private int attachId;//附件
	private int runId;//流程主键
	//private Calendar createTime;//创建时间
	private String createTimeStr;
	//private TeePerson createUser;
	private int createUserId;
	private String createUserName;
	
	private int prcsId;//序号
	private String prcsName;//步骤名称  描述
	public int getSid() {
		return sid;
	}
	public int getVersionNo() {
		return versionNo;
	}
	public int getAttachId() {
		return attachId;
	}
	public int getRunId() {
		return runId;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public int getCreateUserId() {
		return createUserId;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public int getPrcsId() {
		return prcsId;
	}
	public String getPrcsName() {
		return prcsName;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public void setVersionNo(int versionNo) {
		this.versionNo = versionNo;
	}
	public void setAttachId(int attachId) {
		this.attachId = attachId;
	}
	public void setRunId(int runId) {
		this.runId = runId;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public void setPrcsId(int prcsId) {
		this.prcsId = prcsId;
	}
	public void setPrcsName(String prcsName) {
		this.prcsName = prcsName;
	}
	
	
	
	
	
}
