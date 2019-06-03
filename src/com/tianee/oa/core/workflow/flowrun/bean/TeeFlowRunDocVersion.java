package com.tianee.oa.core.workflow.flowrun.bean;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name="FLOW_RUN_DOC_VERSION")
public class TeeFlowRunDocVersion {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="FLOW_RUN_DOC_VERSION_seq_gen")
	@SequenceGenerator(name="FLOW_RUN_DOC_VERSION_seq_gen", sequenceName="FLOW_RUN_DOC_VERSION_seq")
	@Column(name="SID")
	private int sid;
	
	@Column(name="VERSION_NO")
	private int versionNo;//版本号
	
	@Column(name="ATTACH_ID")
	private int attachId;//附件
	
	@Column(name="RUN_ID")
	private int runId;//流程主键
	
	
	@Column(name="CREATE_TIME")
	private Calendar createTime;//创建时间
	
	
	@ManyToOne
	@JoinColumn(name="CREATE_USER")
	private TeePerson createUser;
	
	@Column(name="PRCS_ID")
	private int prcsId;//序号
	
	
	@Column(name="PRCS_NAME")
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


	public Calendar getCreateTime() {
		return createTime;
	}


	public TeePerson getCreateUser() {
		return createUser;
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


	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}


	public void setCreateUser(TeePerson createUser) {
		this.createUser = createUser;
	}


	public void setPrcsId(int prcsId) {
		this.prcsId = prcsId;
	}


	public void setPrcsName(String prcsName) {
		this.prcsName = prcsName;
	}
	
	
	
}
