package com.tianee.oa.core.workflow.formmanage.bean;
import org.hibernate.annotations.Index;

public class TeeFlowFormData {
	private int itemId;//控件ID
	private int runId;//流程ID
	private String data;//数据
	
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getRunId() {
		return runId;
	}
	public void setRunId(int runId) {
		this.runId = runId;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}
