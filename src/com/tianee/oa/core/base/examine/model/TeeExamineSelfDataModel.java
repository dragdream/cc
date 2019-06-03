package com.tianee.oa.core.base.examine.model;


public class TeeExamineSelfDataModel {

	private int sid;//SID	int(11)	自增字段	是	自增	
	private String taskId ;//任务Id
	private String taskName;//
	private String  participantId;//
	private String participantName;//		//被考核人
	private String selfData;//SELF_DATA	自评内容 [{item_id:2,score:20.4,desc:”测试”},{item_id:3,score:2.4,desc:”测试2”}]	考核内容：item_id为表EXAMINE_item.sidscore ：考核分数desc：考核描述
	private String selfDateStr;//SELF_DATE	DATETIME	自评时间
	private double score;//总分
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getParticipantId() {
		return participantId;
	}

	public void setParticipantId(String participantId) {
		this.participantId = participantId;
	}

	public String getParticipantName() {
		return participantName;
	}

	public void setParticipantName(String participantName) {
		this.participantName = participantName;
	}

	public String getSelfData() {
		return selfData;
	}

	public void setSelfData(String selfData) {
		this.selfData = selfData;
	}

	public String getSelfDateStr() {
		return selfDateStr;
	}

	public void setSelfDateStr(String selfDateStr) {
		this.selfDateStr = selfDateStr;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	
}
