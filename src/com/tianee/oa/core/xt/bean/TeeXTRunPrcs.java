package com.tianee.oa.core.xt.bean;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 协同子类
 * @author xsy
 *
 */
@Entity
@Table(name="XT_RUN_PRCS")
public class TeeXTRunPrcs {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="XT_RUN_PRCS_seq_gen")
	@SequenceGenerator(name="XT_RUN_PRCS_seq_gen", sequenceName="XT_RUN_PRCS_seq")
	private int sid;//主键
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="XT_RUN_ID")
	private TeeXTRun xtRun;//所属事项
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PRCS_USER_ID")
	private TeePerson prcsUser;//办理人
	
	
	@Column(name="RECEIVE_TIME")
	private Calendar receiveTime;//接收时间
	
	@Column(name="HANDLE_TIME")
	private Calendar handleTime;//处理时间
	
	@Column(name="STATUS")
	private int status;//状态  0=未接收  1=已接收  2=已办结 
	
	@Column(name="OPINION_TYPE")
	private int opinionType;//意见类型  1=已阅  2=同意   3=不同意
	
	
	@Column(name="OPINION_CONTENT")
	private String  opinionContent;//意见内容


	
	@Column(name="DELETE_STATUS")
	private int  deleteStatus;//删除状态  0=未删除  1=已删除
	
	
	@Column(name="SMS_REMIND")
	private int  smsRemind;//是否短信提醒
	
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PRE_ID")
	private TeeXTRunPrcs prePrcs;//前置任务
	
	
	
	
	
	
	public TeeXTRunPrcs getPrePrcs() {
		return prePrcs;
	}


	public void setPrePrcs(TeeXTRunPrcs prePrcs) {
		this.prePrcs = prePrcs;
	}


	public int getSmsRemind() {
		return smsRemind;
	}


	public void setSmsRemind(int smsRemind) {
		this.smsRemind = smsRemind;
	}


	public int getDeleteStatus() {
		return deleteStatus;
	}


	public void setDeleteStatus(int deleteStatus) {
		this.deleteStatus = deleteStatus;
	}


	public int getSid() {
		return sid;
	}


	public void setSid(int sid) {
		this.sid = sid;
	}


	public TeeXTRun getXtRun() {
		return xtRun;
	}


	public void setXtRun(TeeXTRun xtRun) {
		this.xtRun = xtRun;
	}


	public TeePerson getPrcsUser() {
		return prcsUser;
	}


	public void setPrcsUser(TeePerson prcsUser) {
		this.prcsUser = prcsUser;
	}


	public Calendar getReceiveTime() {
		return receiveTime;
	}


	public void setReceiveTime(Calendar receiveTime) {
		this.receiveTime = receiveTime;
	}


	public Calendar getHandleTime() {
		return handleTime;
	}


	public void setHandleTime(Calendar handleTime) {
		this.handleTime = handleTime;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public int getOpinionType() {
		return opinionType;
	}


	public void setOpinionType(int opinionType) {
		this.opinionType = opinionType;
	}


	public String getOpinionContent() {
		return opinionContent;
	}


	public void setOpinionContent(String opinionContent) {
		this.opinionContent = opinionContent;
	}
	
	
	
}
