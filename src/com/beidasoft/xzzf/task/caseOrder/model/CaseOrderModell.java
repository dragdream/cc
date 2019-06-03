package com.beidasoft.xzzf.task.caseOrder.model;



public class CaseOrderModell {

	private int id; //主键
	private String caseOrderId;   //案件编号
	private String caseOrderName;  //案件名称
	private String name;
	private int caseOrderSource;//案件来源
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCaseOrderSource() {
		return caseOrderSource;
	}
	public void setCaseOrderSource(int caseOrderSource) {
		this.caseOrderSource = caseOrderSource;
	}
	public String getDateDeadlineStr() {
		return dateDeadlineStr;
	}
	public void setDateDeadlineStr(String dateDeadlineStr) {
		this.dateDeadlineStr = dateDeadlineStr;
	}
	
	
	
	

}
