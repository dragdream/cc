package com.tianee.oa.core.workflow.flowmanage.bean;
import org.hibernate.annotations.Index;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="SELECT_AUTO_USER_RULE")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class TeeSelectAutoUserRule implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="SELECT_AUTO_USER_RULE_seq_gen")
	@SequenceGenerator(name="SELECT_AUTO_USER_RULE_seq_gen", sequenceName="SELECT_AUTO_USER_RULE_seq")
	@Column(name="SID")
	private int sid;
	@Column(name="AUTO_TYPE",nullable=false)
	private int autoType;//自动选人类型
	
	@Column(name="OP_USER_ID",nullable=false)
	private int autoOpUser;//主办人
	
	@Column(name="PRCS_USER_ID") 	
	private String autoPrcsUser;//经办人
	
	@Column(name="AUTO_FIELD_ID")
	private String autoFieldId;//AUTO_FIELD_ID
	
	@ManyToOne()
	@Index(name="IDXb0fc3342019c47b98e08a3a55f4")
	@JoinColumn(name="FLOW_PRCS_ID")
	private TeeFlowProcess process;//所属步骤

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getAutoType() {
		return autoType;
	}

	public void setAutoType(int autoType) {
		this.autoType = autoType;
	}

	public String getAutoFieldId() {
		return autoFieldId;
	}

	public void setAutoFieldId(String autoFieldId) {
		this.autoFieldId = autoFieldId;
	}

	public TeeFlowProcess getProcess() {
		return process;
	}

	public void setProcess(TeeFlowProcess process) {
		this.process = process;
	}

	public int getAutoOpUser() {
		return autoOpUser;
	}

	public void setAutoOpUser(int autoOpUser) {
		this.autoOpUser = autoOpUser;
	}

	public String getAutoPrcsUser() {
		return autoPrcsUser;
	}

	public void setAutoPrcsUser(String autoPrcsUser) {
		this.autoPrcsUser = autoPrcsUser;
	}
	
}
