package com.beidasoft.xzzf.task.caseOrder.model;



public class CaseOrderModel {

	private int id; //主键
	private String caseOrderId;   //案件编号
	private String caseOrderName;  //案件名称
	private int caseOrderSource;//案件来源
	private int handleStage;  //办理阶段
	private int currentLink;  //当前环节
	private String dateDeadlineStr;  //权限日期
	
	
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
	public String getDateDeadlineStr() {
		return dateDeadlineStr;
	}
	public void setDateDeadlineStr(String dateDeadlineStr) {
		this.dateDeadlineStr = dateDeadlineStr;
	}
	
	
	

}
