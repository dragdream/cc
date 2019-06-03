package com.tianee.oa.core.base.examine.model;

import javax.persistence.Column;


public class TeeExamineDataModel {

	private int sid;//SID	int(11)	自增字段	是	自增	
	private String taskId ;//任务Id
	private String taskName;//
	private String rankmanId;
	private String rankmanName;//
	private String participantId;//
	private String participantName;//		//被考核人
	private String examineData;//SELF_DATA	考核内容 [{item_id:2,score:20.4,desc:”测试”},{item_id:3,score:2.4,desc:”测试2”}]	考核内容：item_id为表EXAMINE_item.sidscore ：考核分数desc：考核描述
	private String examineDateStr;//SELF_DATE	DATETIME	考核时间
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

	public String getRankmanId() {
		return rankmanId;
	}

	public void setRankmanId(String rankmanId) {
		this.rankmanId = rankmanId;
	}

	public String getRankmanName() {
		return rankmanName;
	}

	public void setRankmanName(String rankmanName) {
		this.rankmanName = rankmanName;
	}



	public String getExamineData() {
		return examineData;
	}

	public void setExamineData(String examineData) {
		this.examineData = examineData;
	}

	public String getExamineDateStr() {
		return examineDateStr;
	}

	public void setExamineDateStr(String examineDateStr) {
		this.examineDateStr = examineDateStr;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}



	
}
