package com.tianee.oa.webframe.httpModel.core.workflow;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.tianee.oa.core.org.bean.TeePerson;

public class TeeFlowRunPrcsModel {

	private int sid;
	private int prcsId;//流程序号(累加)
	private Calendar beginTime;//开始接收时间
	private String beginTimeDesc;
	private int prcsType;//步骤节点类型
	private Calendar endTime;//办理完成时间
	private String endTimeDesc;
	private Calendar createTime;//创建时间
	private String createTimeDesc;
	private int flag;//状态标识  0-未接收  1-接收  2-办理完毕  3-已委托办理
	private int opFlag;//主办标识  0-经办  1-主办
	private int topFlag;//办理类型 1：指定主办人 2：无主办会签 3：先接受为主办人
	private int delFlag;//删除标识   0-未删除  1-已删除
	private int timeoutFlag;//超时标记
	private double timeout;//超时时限
	private String freeTurnModel;//自由流程数据转交模型
	private String freeCtrlModel;//自由流程表单控制模型
	private int flowPrcsId;
	private int runId;
	private String runName;
	private String prcsName;
	private String parent;
	private String passedTime;//用时
	private int prcsUserId;//办理人员ID
	private String prcsUserName;//办理人员姓名
	private int otherUserId;//自动委托用户ID
	private String otherUserName;//自动委托用户姓名
	private int fromUserId;
	private String fromUserName;
	private int suspend;//挂起标记
	private int sendFlag;//发送标记，用于公文
	private int backFlag;//回退标记
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getPrcsId() {
		return prcsId;
	}
	public void setPrcsId(int prcsId) {
		this.prcsId = prcsId;
	}
	public Calendar getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Calendar beginTime) {
		this.beginTime = beginTime;
	}
	public Calendar getEndTime() {
		return endTime;
	}
	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}
	public Calendar getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
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
	public String getFreeTurnModel() {
		return freeTurnModel;
	}
	public void setFreeTurnModel(String freeTurnModel) {
		this.freeTurnModel = freeTurnModel;
	}
	public String getFreeCtrlModel() {
		return freeCtrlModel;
	}
	public void setFreeCtrlModel(String freeCtrlModel) {
		this.freeCtrlModel = freeCtrlModel;
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
	public String getCreateTimeDesc() {
		return createTimeDesc;
	}
	public void setCreateTimeDesc(String createTimeDesc) {
		this.createTimeDesc = createTimeDesc;
	}
	public void setRunId(int runId) {
		this.runId = runId;
	}
	public int getRunId() {
		return runId;
	}
	public void setFlowPrcsId(int flowPrcsId) {
		this.flowPrcsId = flowPrcsId;
	}
	public int getFlowPrcsId() {
		return flowPrcsId;
	}
	public void setPrcsName(String prcsName) {
		this.prcsName = prcsName;
	}
	public String getPrcsName() {
		return prcsName;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getParent() {
		return parent;
	}
	public void setPassedTime(String passedTime) {
		this.passedTime = passedTime;
	}
	public String getPassedTime() {
		return passedTime;
	}
	public int getPrcsUserId() {
		return prcsUserId;
	}
	public void setPrcsUserId(int prcsUserId) {
		this.prcsUserId = prcsUserId;
	}
	public String getPrcsUserName() {
		return prcsUserName;
	}
	public void setPrcsUserName(String prcsUserName) {
		this.prcsUserName = prcsUserName;
	}
	public int getOtherUserId() {
		return otherUserId;
	}
	public void setOtherUserId(int otherUserId) {
		this.otherUserId = otherUserId;
	}
	public String getOtherUserName() {
		return otherUserName;
	}
	public void setOtherUserName(String otherUserName) {
		this.otherUserName = otherUserName;
	}
	public int getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(int fromUserId) {
		this.fromUserId = fromUserId;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public void setPrcsType(int prcsType) {
		this.prcsType = prcsType;
	}
	public int getPrcsType() {
		return prcsType;
	}
	public int getSuspend() {
		return suspend;
	}
	public void setSuspend(int suspend) {
		this.suspend = suspend;
	}
	public int getSendFlag() {
		return sendFlag;
	}
	public void setSendFlag(int sendFlag) {
		this.sendFlag = sendFlag;
	}
	public String getRunName() {
		return runName;
	}
	public void setRunName(String runName) {
		this.runName = runName;
	}
	public int getBackFlag() {
		return backFlag;
	}
	public void setBackFlag(int backFlag) {
		this.backFlag = backFlag;
	}
	
}
