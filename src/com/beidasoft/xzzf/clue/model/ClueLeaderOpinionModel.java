package com.beidasoft.xzzf.clue.model;

public class ClueLeaderOpinionModel {
	
	private String id; // 线索管理领导意见表主键ID

	private String clueId;//对应的线索主键ID

	private String leadersOpinion;//领导意见

	private String leadersId;//领导ID

	private String leadersName;//领导名

	private String createTimeStr;//创建时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClueId() {
		return clueId;
	}

	public void setClueId(String clueId) {
		this.clueId = clueId;
	}

	public String getLeadersOpinion() {
		return leadersOpinion;
	}

	public void setLeadersOpinion(String leadersOpinion) {
		this.leadersOpinion = leadersOpinion;
	}

	public String getLeadersId() {
		return leadersId;
	}

	public void setLeadersId(String leadersId) {
		this.leadersId = leadersId;
	}

	public String getLeadersName() {
		return leadersName;
	}

	public void setLeadersName(String leadersName) {
		this.leadersName = leadersName;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	
}
