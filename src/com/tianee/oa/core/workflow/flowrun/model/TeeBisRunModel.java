package com.tianee.oa.core.workflow.flowrun.model;

import java.util.Map;

public class TeeBisRunModel {
	private String bisKey;
	private String bisTable;
	private int runId;
	private String preName;
	private String runName;
	private String sufName;
	private Map<String,String> runDatas;
	private Map<String,String> runVars;
	private int docId;
	private String attachIds;
	private int docAipId;//版式正文主键
	
	private String  businessKey;//用来区分是不是从业务建模那跳转到流程的
	
	
	public String getBusinessKey() {
		return businessKey;
	}
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	public int getDocAipId() {
		return docAipId;
	}
	public void setDocAipId(int docAipId) {
		this.docAipId = docAipId;
	}
	public String getBisKey() {
		return bisKey;
	}
	public void setBisKey(String bisKey) {
		this.bisKey = bisKey;
	}
	public String getBisTable() {
		return bisTable;
	}
	public void setBisTable(String bisTable) {
		this.bisTable = bisTable;
	}
	public int getRunId() {
		return runId;
	}
	public void setRunId(int runId) {
		this.runId = runId;
	}
	public Map<String, String> getRunDatas() {
		return runDatas;
	}
	public void setRunDatas(Map<String, String> runDatas) {
		this.runDatas = runDatas;
	}
	public Map<String, String> getRunVars() {
		return runVars;
	}
	public void setRunVars(Map<String, String> runVars) {
		this.runVars = runVars;
	}
	public int getDocId() {
		return docId;
	}
	public void setDocId(int docId) {
		this.docId = docId;
	}
	public String getAttachIds() {
		return attachIds;
	}
	public void setAttachIds(String attachIds) {
		this.attachIds = attachIds;
	}
	public String getRunName() {
		return runName;
	}
	public void setRunName(String runName) {
		this.runName = runName;
	}
	public String getPreName() {
		return preName;
	}
	public void setPreName(String preName) {
		this.preName = preName;
	}
	public String getSufName() {
		return sufName;
	}
	public void setSufName(String sufName) {
		this.sufName = sufName;
	}
	
}
