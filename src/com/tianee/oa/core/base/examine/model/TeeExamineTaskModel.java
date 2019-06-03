package com.tianee.oa.core.base.examine.model;

import java.util.List;
import java.util.Map;

public class TeeExamineTaskModel {

	private int sid;//SID	int(11)	自增字段	是	自增
	
	private String taskTitle;//TASK_TITLE	varchar(254)	任务名称	

	private String rankmanIds;//
	private String rankmanNames;//

	private String participantIds;
	private String participantNames;//
	
	private List<Map> participantList ;

	private String isSelfAssessment;//IS_SELF_ASSESSMENT	Char（1）	是否需要自评		0：不需要自评1：需要
	
	private String taskFlag;//TASK_FLAG	Char（1）	按照管理范围		0：不按1：按

	private String anonymity;//ANONYMITY	Char（1）	是否匿名		0：实名1：匿名
	private String anonymityDesc;//匿名描述

	private String taskBeginStr;//TASK_BEGIN	DATETIME	开始时间	
	private String taskBeginStr2;//TASK_BEGIN	DATETIME	开始时间	
	
	private String taskEndStr;//TASK_TIME	DATETIME	结束时间	
	private String taskEndStr2;//TASK_TIME	DATETIME	结束时间		
	
	private String sendTimeStr;///SEND_TIME	DATETIME	发布时间
	
	private String taskDesc;//TASK_DESC	TEXT	任务描述	

	private String groupId;//指标集Id

	private String groupName;//指标集名称
	
	private String sms;//是否发送事务消息
	
	private  int state ;
	
	
	private String createId;
	private String createName;
	
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getTaskTitle() {
		return taskTitle;
	}

	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}

	public String getRankmanIds() {
		return rankmanIds;
	}

	public void setRankmanIds(String rankmanIds) {
		this.rankmanIds = rankmanIds;
	}

	public String getRankmanNames() {
		return rankmanNames;
	}

	public void setRankmanNames(String rankmanNames) {
		this.rankmanNames = rankmanNames;
	}

	public String getParticipantIds() {
		return participantIds;
	}

	public void setParticipantIds(String participantIds) {
		this.participantIds = participantIds;
	}

	public String getParticipantNames() {
		return participantNames;
	}

	public void setParticipantNames(String participantNames) {
		this.participantNames = participantNames;
	}

	public String getIsSelfAssessment() {
		return isSelfAssessment;
	}

	public void setIsSelfAssessment(String isSelfAssessment) {
		this.isSelfAssessment = isSelfAssessment;
	}

	public String getTaskFlag() {
		return taskFlag;
	}

	public void setTaskFlag(String taskFlag) {
		this.taskFlag = taskFlag;
	}

	public String getAnonymity() {
		return anonymity;
	}

	public void setAnonymity(String anonymity) {
		this.anonymity = anonymity;
	}

	public String getTaskBeginStr() {
		return taskBeginStr;
	}

	public void setTaskBeginStr(String taskBeginStr) {
		this.taskBeginStr = taskBeginStr;
	}

	public String getTaskEndStr() {
		return taskEndStr;
	}

	public void setTaskEndStr(String taskEndStr) {
		this.taskEndStr = taskEndStr;
	}

	public String getSendTimeStr() {
		return sendTimeStr;
	}

	public void setSendTimeStr(String sendTimeStr) {
		this.sendTimeStr = sendTimeStr;
	}

	public String getTaskDesc() {
		return taskDesc;
	}

	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getAnonymityDesc() {
		return anonymityDesc;
	}

	public void setAnonymityDesc(String anonymityDesc) {
		this.anonymityDesc = anonymityDesc;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getTaskBeginStr2() {
		return taskBeginStr2;
	}

	public void setTaskBeginStr2(String taskBeginStr2) {
		this.taskBeginStr2 = taskBeginStr2;
	}

	public String getTaskEndStr2() {
		return taskEndStr2;
	}

	public void setTaskEndStr2(String taskEndStr2) {
		this.taskEndStr2 = taskEndStr2;
	}

	public String getSms() {
		return sms;
	}

	public void setSms(String sms) {
		this.sms = sms;
	}

	public List<Map> getParticipantList() {
		return participantList;
	}

	public void setParticipantList(List<Map> participantList) {
		this.participantList = participantList;
	}
	
	
}
