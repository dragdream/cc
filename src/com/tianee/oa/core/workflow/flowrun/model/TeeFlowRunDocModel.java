package com.tianee.oa.core.workflow.flowrun.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;

public class TeeFlowRunDocModel {
	private int sid;
	private int runId;
	private TeeAttachmentModel docAttach;
	private TeeAttachmentModel docAipAttach;
	private Calendar createTime;
	private String createTimeDesc;
	private int lock;//锁
	private int versionNo;//版本号
	private int lastEditor;//最后一个编辑人
	private String lastEditorUsername;
	private Calendar lastEditTime;//最后一次编辑时间
	private String lastEditTimeDesc;//
	
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
	public TeeAttachmentModel getDocAttach() {
		return docAttach;
	}
	public void setDocAttach(TeeAttachmentModel docAttach) {
		this.docAttach = docAttach;
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
	public int getLock() {
		return lock;
	}
	public void setLock(int lock) {
		this.lock = lock;
	}
	public int getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(int versionNo) {
		this.versionNo = versionNo;
	}
	public int getLastEditor() {
		return lastEditor;
	}
	public void setLastEditor(int lastEditor) {
		this.lastEditor = lastEditor;
	}
	public String getLastEditorUsername() {
		return lastEditorUsername;
	}
	public void setLastEditorUsername(String lastEditorUsername) {
		this.lastEditorUsername = lastEditorUsername;
	}
	public Calendar getLastEditTime() {
		return lastEditTime;
	}
	public void setLastEditTime(Calendar lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
	public String getLastEditTimeDesc() {
		return lastEditTimeDesc;
	}
	public void setLastEditTimeDesc(String lastEditTimeDesc) {
		this.lastEditTimeDesc = lastEditTimeDesc;
	}
	public TeeAttachmentModel getDocAipAttach() {
		return docAipAttach;
	}
	public void setDocAipAttach(TeeAttachmentModel docAipAttach) {
		this.docAipAttach = docAipAttach;
	}
	
}
