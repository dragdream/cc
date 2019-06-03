package com.beidasoft.xzfy.caseRegister.bean;

public class CaseExtractInfo {

	// 案件编号
	private String caseNum;
	// 案件来源
	private String applicationTypeCode; 
	 // 姓名/组织名称
	private String name;
	// 开始时间
	private String startTime;
	// 结束时间
	private String endTime;
	//当前页
	private int page;
	//每页记录数
	private int rows;
	
	public String getCaseNum() {
		return caseNum;
	}
	public void setCaseNum(String caseNum) {
		this.caseNum = caseNum;
	}
	public String getApplicationTypeCode() {
		return applicationTypeCode;
	}
	public void setApplicationTypeCode(String applicationTypeCode) {
		this.applicationTypeCode = applicationTypeCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	
}
