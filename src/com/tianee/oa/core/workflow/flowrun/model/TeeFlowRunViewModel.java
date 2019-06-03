package com.tianee.oa.core.workflow.flowrun.model;

import java.io.Serializable;

public class TeeFlowRunViewModel implements Serializable{
	private int sid;
	private String runName;
	private int prcsId;//实际步骤
	private int flowPrcsId;//设计步骤
	private String viewUsername;//查阅人
	private String viewTimeDesc;//查看时间
	private int viewFlag=0;//查看标记
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getRunName() {
		return runName;
	}
	public void setRunName(String runName) {
		this.runName = runName;
	}
	public int getPrcsId() {
		return prcsId;
	}
	public void setPrcsId(int prcsId) {
		this.prcsId = prcsId;
	}
	public int getFlowPrcsId() {
		return flowPrcsId;
	}
	public void setFlowPrcsId(int flowPrcsId) {
		this.flowPrcsId = flowPrcsId;
	}
	public String getViewUsername() {
		return viewUsername;
	}
	public void setViewUsername(String viewUsername) {
		this.viewUsername = viewUsername;
	}
	public String getViewTimeDesc() {
		return viewTimeDesc;
	}
	public void setViewTimeDesc(String viewTimeDesc) {
		this.viewTimeDesc = viewTimeDesc;
	}
	public int getViewFlag() {
		return viewFlag;
	}
	public void setViewFlag(int viewFlag) {
		this.viewFlag = viewFlag;
	}
	
}
