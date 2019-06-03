package com.beidasoft.xzzf.task.caseOrder.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="CASEBORDER_BASE")
public class CaseOrder {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CASEBORDER_BASE_seq_gen")
	@SequenceGenerator(name="CASEBORDER_BASE_seq_gen", sequenceName="CASEBORDER_BASE_seq")
	@Column(name="ID")
	private int id; //主键
	
	@Column(name="CASEORDERID")
	private String caseOrderId;   //案件编号
	
	@Column(name="CASEORDERNAME")
	private String caseOrderName;  //案件名称
	
	@Column(name="CASEORDERSOURCE")
	private int caseOrderSource;//案件来源
	
	@Column(name="HANDLESTAGE")
	private int handleStage;  //办理阶段
	
	@Column(name="CURRENTLINK")
	private int currentLink;  //当前环节
	
	@Column(name="DATEDEADLINE")
	private Date dateDeadline;  //权限日期

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCaseOrderId() {
		return caseOrderId;
	}

	public void setCaseOrderId(String caseOrderId) {
		this.caseOrderId = caseOrderId;
	}

	public String getCaseOrderName() {
		return caseOrderName;
	}

	public void setCaseOrderName(String caseOrderName) {
		this.caseOrderName = caseOrderName;
	}

	public int getCaseOrderSource() {
		return caseOrderSource;
	}

	public void setCaseOrderSource(int caseOrderSource) {
		this.caseOrderSource = caseOrderSource;
	}

	public int getHandleStage() {
		return handleStage;
	}

	public void setHandleStage(int handleStage) {
		this.handleStage = handleStage;
	}

	public int getCurrentLink() {
		return currentLink;
	}

	public void setCurrentLink(int currentLink) {
		this.currentLink = currentLink;
	}

	public Date getDateDeadline() {
		return dateDeadline;
	}

	public void setDateDeadline(Date dateDeadline) {
		this.dateDeadline = dateDeadline;
	}
	
	
	
}
