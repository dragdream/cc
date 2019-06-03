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

import org.hibernate.annotations.Index;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name="FLOW_RUN_UPLOAD_CTRL")
public class TeeFlowRunUploadCtrl {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="FLOW_RUN_UPLOAD_CTRL_seq_gen")
	@SequenceGenerator(name="FLOW_RUN_UPLOAD_CTRL_seq_gen", sequenceName="FLOW_RUN_UPLOAD_CTRL_seq")
	private int sid;
	
	@ManyToOne()
	@Index(name="IDXdeeea2eb23b44fae8d3cf28b732")
	@JoinColumn(name="USER_ID")
	private TeePerson user;
	
	@ManyToOne()
	@Index(name="IDX90ec877e51d641adb5c7e12c36e")
	@JoinColumn(name="RUN_ID")
	private TeeFlowRun flowRun;
	
	@ManyToOne()
	@Index(name="IDXc817b50359da4ca7b257cc15472")
	@JoinColumn(name="ATTACH_ID")
	private TeeAttachment attach;
	
	@Column(name="CREATE_TIME")
	private Calendar createTime;
	
	@Column(name="CTRL_ID")
	@Index(name="CTRL_ID")
	private String ctrlId;//控件ID

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeePerson getUser() {
		return user;
	}

	public void setUser(TeePerson user) {
		this.user = user;
	}

	public TeeFlowRun getFlowRun() {
		return flowRun;
	}

	public void setFlowRun(TeeFlowRun flowRun) {
		this.flowRun = flowRun;
	}

	public TeeAttachment getAttach() {
		return attach;
	}

	public void setAttach(TeeAttachment attach) {
		this.attach = attach;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public void setCtrlId(String ctrlId) {
		this.ctrlId = ctrlId;
	}

	public String getCtrlId() {
		return ctrlId;
	}
	
}
