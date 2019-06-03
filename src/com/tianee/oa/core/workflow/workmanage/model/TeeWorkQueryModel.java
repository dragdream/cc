package com.tianee.oa.core.workflow.workmanage.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class TeeWorkQueryModel {
	private int runId;
	private int flowId;
	private int prcsId;
	
	private String runName;//工作名称
	private String flowName;//流程名称
	private String beginTime;//开始时间
	private String endTime;//结束时间
	private String beginTimeDesc;//开始时间描述
	private String endTimeDesc;//结束时间描述
	private List<Map> attachments = new ArrayList<Map>();//公共附件
	private String beginUserName;//发起人
	
	/**
	 * 1：流程图
	 * 2：关注
	 * 3：结束
	 * 4：删除
	 */
	private List<Integer> operations = new ArrayList<Integer>();//操作渲染

	public int getRunId() {
		return runId;
	}

	public void setRunId(int runId) {
		this.runId = runId;
	}

	public int getFlowId() {
		return flowId;
	}

	public void setFlowId(int flowId) {
		this.flowId = flowId;
	}

	public int getPrcsId() {
		return prcsId;
	}

	public void setPrcsId(int prcsId) {
		this.prcsId = prcsId;
	}

	public String getRunName() {
		return runName;
	}

	public void setRunName(String runName) {
		this.runName = runName;
	}

	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}


	public String getBeginTimeDesc() {
		return beginTimeDesc;
	}

	public void setBeginTimeDesc(String beginTimeDesc) {
		this.beginTimeDesc = beginTimeDesc;
	}

	public String getEndTimeDesc() {
		return endTimeDesc;
	}

	public void setEndTimeDesc(String endTimeDesc) {
		this.endTimeDesc = endTimeDesc;
	}

	public List<Map> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Map> attachments) {
		this.attachments = attachments;
	}

	public void setOperations(List<Integer> operations) {
		this.operations = operations;
	}

	public List<Integer> getOperations() {
		return operations;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getBeginUserName() {
		return beginUserName;
	}

	public void setBeginUserName(String beginUserName) {
		this.beginUserName = beginUserName;
	}
}
