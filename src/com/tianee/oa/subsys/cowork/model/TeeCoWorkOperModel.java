package com.tianee.oa.subsys.cowork.model;

public class TeeCoWorkOperModel {
	private int taskId;
	private String remark;//备注
	private String delayTime;
	private int progress;
	private int score;
	private String relTimes;//确认工时
	private String attachIds;//附件
	private String docId;//文档id
	private int rangeTimes;
	private String startTimes;
	private String endTimes;
	
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDelayTime() {
		return delayTime;
	}
	public void setDelayTime(String delayTime) {
		this.delayTime = delayTime;
	}
	public int getProgress() {
		return progress;
	}
	public void setProgress(int progress) {
		this.progress = progress;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getRelTimes() {
		return relTimes;
	}
	public int getRangeTimes() {
		return rangeTimes;
	}
	public void setRangeTimes(int rangeTimes) {
		this.rangeTimes = rangeTimes;
	}
	public String getStartTimes() {
		return startTimes;
	}
	public void setStartTimes(String startTimes) {
		this.startTimes = startTimes;
	}
	public String getEndTimes() {
		return endTimes;
	}
	public void setEndTimes(String endTimes) {
		this.endTimes = endTimes;
	}
	public void setRelTimes(String relTimes) {
		this.relTimes = relTimes;
	}
	
	
}
