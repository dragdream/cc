package com.tianee.oa.core.workflow.flowrun.model;

import java.util.List;

/**
 * 
 * @author zhp
 * @createTime 2013-10-1
 * @desc
 */
public class TeeFlowInfoCharModel {

	private int prcsId;//第几步骤
	
	private String prcsName;//步骤名称
	
	private String userName;//办理人名称
	
	private int runId;//runId 

	private List prcsList;
	
	private String runName;
	
	private int flowFlag;//当前步骤所处状态  0 办理中 1 已经办结
	
	public int getPrcsId() {
		return prcsId;
	}

	public void setPrcsId(int prcsId) {
		this.prcsId = prcsId;
	}

	public String getPrcsName() {
		return prcsName;
	}

	public void setPrcsName(String prcsName) {
		this.prcsName = prcsName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getRunId() {
		return runId;
	}

	public void setRunId(int runId) {
		this.runId = runId;
	}

	public List getPrcsList() {
		return prcsList;
	}

	public void setPrcsList(List prcsList) {
		this.prcsList = prcsList;
	}

	public int getFlowFlag() {
		return flowFlag;
	}

	public void setFlowFlag(int flowFlag) {
		this.flowFlag = flowFlag;
	}

	public String getRunName() {
		return runName;
	}

	public void setRunName(String runName) {
		this.runName = runName;
	}
	
	
}
