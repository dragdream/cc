package com.tianee.oa.webframe.httpModel.core.workflow;

import java.util.HashMap;
import java.util.Map;

import com.tianee.oa.webframe.httpModel.TeeBaseModel;


public class TeeWorkFlowInfoModel extends TeeBaseModel{
	private int frpSid;//步骤实例唯一标识
	private int runId;//所属流水流程
	private int prcsId;//流程序号(累加)
	private int flowId;//所属设计步骤
	private int typeFlag;//1.固定流程  2.自由流程
	private String flowName;//流程类型名称
	private String prcsUserId;//流程处理人
	private String prcsUserName;//流程处理人名字
	private String beginTime;//开始接收时间
	private String endTime;//办理完成时间
	private String createTime;//创建时间
	private int prcsFlag;//状态标识  0-未接收  1-接收  2-办理完毕  3-已委托办理
	private int opFlag;//主办标识  0-经办  1-主办
	private int topFlag;//办理类型 1：指定主办人 2：无主办会签 3：先接受为主办人
	private int delFlag;//删除标识   0-未删除  1-已删除
	private int timeoutFlag;//超时标记
	private double timeout;//超时时限
	private String fromUserId;//从谁委托过来的
	private String freeModel;//自由流程数据模型，主要是用于扩展自由流程属性用的
	
	private String runName;//文号
	private String beginUserName;//发起人姓名
	private String prcsName;//步骤名字 第几步 如果是固定流则显示 步骤以及步骤名字 如果是自由流 则显示第几步
	private String prcsTimeDesc;//办理时间
	
	private int flowMonitorPrivType;//流程监控权限类型1-管理 2-监控 3-查询 4-编辑 5-查询
	private int level;//流程级别  1:普通  2：紧急  3：加急
	private Map params = new HashMap();//其余数据
	
	
	public int getTypeFlag() {
		return typeFlag;
	}
	public void setTypeFlag(int typeFlag) {
		this.typeFlag = typeFlag;
	}
	public int getRunId() {
		return runId;
	}
	public void setRunId(int runId) {
		this.runId = runId;
	}
	public int getPrcsId() {
		return prcsId;
	}
	public void setPrcsId(int prcsId) {
		this.prcsId = prcsId;
	}
	public int getFlowId() {
		return flowId;
	}
	public void setFlowId(int flowId) {
		this.flowId = flowId;
	}
	public String getPrcsUserId() {
		return prcsUserId;
	}
	public void setPrcsUserId(String prcsUserId) {
		this.prcsUserId = prcsUserId;
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public int getPrcsFlag() {
		return prcsFlag;
	}
	public void setPrcsFlag(int prcsFlag) {
		this.prcsFlag = prcsFlag;
	}
	public int getOpFlag() {
		return opFlag;
	}
	public void setOpFlag(int opFlag) {
		this.opFlag = opFlag;
	}
	public int getTopFlag() {
		return topFlag;
	}
	public void setTopFlag(int topFlag) {
		this.topFlag = topFlag;
	}
	public int getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}
	public int getTimeoutFlag() {
		return timeoutFlag;
	}
	public void setTimeoutFlag(int timeoutFlag) {
		this.timeoutFlag = timeoutFlag;
	}
	public double getTimeout() {
		return timeout;
	}
	public void setTimeout(double timeout) {
		this.timeout = timeout;
	}
	public String getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}
	public String getFreeModel() {
		return freeModel;
	}
	public void setFreeModel(String freeModel) {
		this.freeModel = freeModel;
	}
	public String getRunName() {
		return runName;
	}
	public void setRunName(String runName) {
		this.runName = runName;
	}
	public String getBeginUserName() {
		return beginUserName;
	}
	public void setBeginUserName(String beginUserName) {
		this.beginUserName = beginUserName;
	}
	public String getPrcsName() {
		return prcsName;
	}
	public void setPrcsName(String prcsName) {
		this.prcsName = prcsName;
	}
	public String getPrcsUserName() {
		return prcsUserName;
	}
	public void setPrcsUserName(String prcsUserName) {
		this.prcsUserName = prcsUserName;
	}
	public String getFlowName() {
		return flowName;
	}
	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}
	public int getFlowMonitorPrivType() {
		return flowMonitorPrivType;
	}
	public void setFlowMonitorPrivType(int flowMonitorPrivType) {
		this.flowMonitorPrivType = flowMonitorPrivType;
	}
	public void setFrpSid(int frpSid) {
		this.frpSid = frpSid;
	}
	public int getFrpSid() {
		return frpSid;
	}
	public void setPrcsTimeDesc(String prcsTimeDesc) {
		this.prcsTimeDesc = prcsTimeDesc;
	}
	public String getPrcsTimeDesc() {
		return prcsTimeDesc;
	}
	public void setParams(Map params) {
		this.params = params;
	}
	public Map getParams() {
		return params;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
}
