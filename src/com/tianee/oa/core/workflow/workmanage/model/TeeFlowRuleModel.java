package com.tianee.oa.core.workflow.workmanage.model;

import java.util.Date;

import com.tianee.oa.webframe.httpModel.TeeBaseModel;

@SuppressWarnings("serial")
public class TeeFlowRuleModel extends TeeBaseModel{
	
	private int sid;
	private String flowIdStr;//流程id串
	private int userId;//委托人id
	private int toUser;//被委托人id
	private Date beginDate;//生效时间
	private Date endDate;//结束时间
	private int status;
	private int flowId;
	private String flowName;//流程名
	private String userName;//用户名
	private String validTime;
	private String flowStatus;//是否一直生效  1:一直有效   2：在固定时间段内有效   0：无效
	private String workName;//工作名称
	private String prcsName;//步骤名称
	private Date entrustTime;//委托时间
	private int runId;//流程ID
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getFlowIdStr() {
		return flowIdStr;
	}
	public void setFlowIdStr(String flowIdStr) {
		this.flowIdStr = flowIdStr;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getToUser() {
		return toUser;
	}
	public void setToUser(int toUser) {
		this.toUser = toUser;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getFlowId() {
		return flowId;
	}
	public void setFlowId(int flowId) {
		this.flowId = flowId;
	}
	public String getFlowName() {
		return flowName;
	}
	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getValidTime() {
		return validTime;
	}
	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}
	public String getFlowStatus() {
		return flowStatus;
	}
	public void setFlowStatus(String flowStatus) {
		this.flowStatus = flowStatus;
	}
	public String getWorkName() {
		return workName;
	}
	public void setWorkName(String workName) {
		this.workName = workName;
	}
	public String getPrcsName() {
		return prcsName;
	}
	public void setPrcsName(String prcsName) {
		this.prcsName = prcsName;
	}
	public Date getEntrustTime() {
		return entrustTime;
	}
	public void setEntrustTime(Date entrustTime) {
		this.entrustTime = entrustTime;
	}
	public int getRunId() {
		return runId;
	}
	public void setRunId(int runId) {
		this.runId = runId;
	}
	
	
	
	
	
	
	
	
}
