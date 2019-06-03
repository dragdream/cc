package com.tianee.oa.core.workflow.flowrun.bean;
import org.hibernate.annotations.Index;

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

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name="FLOW_RUN_DOC")
public class TeeFlowRunDoc {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="FLOW_RUN_DOC_seq_gen")
	@SequenceGenerator(name="FLOW_RUN_DOC_seq_gen", sequenceName="FLOW_RUN_DOC_seq")
	private int sid;
	
	@ManyToOne
	@Index(name="IDX9574d3117cc644808ae09900480")
	@JoinColumn(name="RUN_ID")
	private TeeFlowRun flowRun;
	
	@ManyToOne
	@Index(name="IDX2684a803b6c44bfbbe5ec749882")
	@JoinColumn(name="DOC_ATTACH_ID")
	private TeeAttachment docAttach;
	
	@Column(name="CREATE_TIME")
	private Calendar createTime = Calendar.getInstance();
	
	@Column(name="LOCK0")
	private int lock;//锁
	
	@Column(name="VERSION_NO")
	private int versionNo;//版本号
	
	@Column(name="LAST_EDITOR")
	private int lastEditor;//最后一个编辑人
	
	@Column(name="LAST_EDIT_TIME")
	private Calendar lastEditTime;//最后一次编辑时间

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeeFlowRun getFlowRun() {
		return flowRun;
	}

	public void setFlowRun(TeeFlowRun flowRun) {
		this.flowRun = flowRun;
	}

	public TeeAttachment getDocAttach() {
		return docAttach;
	}

	public void setDocAttach(TeeAttachment docAttach) {
		this.docAttach = docAttach;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public int getLock() {
		return lock;
	}

	public void setLock(int lock) {
		this.lock = lock;
	}

	public void setVersionNo(int versionNo) {
		this.versionNo = versionNo;
	}

	public int getVersionNo() {
		return versionNo;
	}

	public void setLastEditor(int lastEditor) {
		this.lastEditor = lastEditor;
	}

	public int getLastEditor() {
		return lastEditor;
	}

	public void setLastEditTime(Calendar lastEditTime) {
		this.lastEditTime = lastEditTime;
	}

	public Calendar getLastEditTime() {
		return lastEditTime;
	}
	
	
}
