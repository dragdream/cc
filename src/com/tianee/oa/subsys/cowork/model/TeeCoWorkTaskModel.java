package com.tianee.oa.subsys.cowork.model;

public class TeeCoWorkTaskModel {
	private int sid;
	private String taskTitle;
	private int taskType;//任务来源   0：默认  1：部门计划   2：上级任务
	private String rangeTimes;//评估工时
	private String relTimes;//实际工时
	private String createTimeDesc;
	private String startTimeDesc;
	private String endTimeDesc;
	private String relStartTimeDesc;
	private String relEndTimeDesc;
	private int createUserId;//布置人
	private String createUserName;//布置人姓名
	private int auditorId;//审批人
	private String auditorName;//布置人姓名
	private int chargerId;//负责人
	private String chargerName;//负责人
	private String joinerIds;
	private String joinerNames;
	private String content;//会议内容
	private String standard;//奖罚标准
	private String leaderRemark;//领导批示
	private int status;//状态，0：等待接收  1：等待审批  2：审批不通过  3：拒绝接收  4：进行中  5：提交审核  6：审核通过 7：任务撤销  8:已完成
	private int progress;//进度百分比
	private int score;//任务评分
	private int parentTaskId;//父任务id
	private String parentTaskName;//父任务名称
	private String scheduleId;//计划ID
	private Long quanbuCount;
	private Long weikaishiCount;
	private Long jinxingzhongCount;
	private Long yichaoqiCount;
	private Long yiwanchengCount;
	private Long yiquxiaoCount;
	private Long wofuzedeCount;
	private Long wocanyudeCount;
	private Long wochangjiandeCount;
	public Long getQuanbuCount() {
		return quanbuCount;
	}
	public void setQuanbuCount(Long quanbuCount) {
		this.quanbuCount = quanbuCount;
	}
	public Long getWeikaishiCount() {
		return weikaishiCount;
	}
	public void setWeikaishiCount(Long weikaishiCount) {
		this.weikaishiCount = weikaishiCount;
	}
	public Long getJinxingzhongCount() {
		return jinxingzhongCount;
	}
	public void setJinxingzhongCount(Long jinxingzhongCount) {
		this.jinxingzhongCount = jinxingzhongCount;
	}
	public Long getYichaoqiCount() {
		return yichaoqiCount;
	}
	public void setYichaoqiCount(Long yichaoqiCount) {
		this.yichaoqiCount = yichaoqiCount;
	}
	public Long getYiwanchengCount() {
		return yiwanchengCount;
	}
	public void setYiwanchengCount(Long yiwanchengCount) {
		this.yiwanchengCount = yiwanchengCount;
	}
	public Long getYiquxiaoCount() {
		return yiquxiaoCount;
	}
	public void setYiquxiaoCount(Long yiquxiaoCount) {
		this.yiquxiaoCount = yiquxiaoCount;
	}
	public Long getWofuzedeCount() {
		return wofuzedeCount;
	}
	public void setWofuzedeCount(Long wofuzedeCount) {
		this.wofuzedeCount = wofuzedeCount;
	}
	public Long getWocanyudeCount() {
		return wocanyudeCount;
	}
	public void setWocanyudeCount(Long wocanyudeCount) {
		this.wocanyudeCount = wocanyudeCount;
	}
	public Long getWochangjiandeCount() {
		return wochangjiandeCount;
	}
	public void setWochangjiandeCount(Long wochangjiandeCount) {
		this.wochangjiandeCount = wochangjiandeCount;
	}
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
	public int getTaskType() {
		return taskType;
	}
	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}
	public String getRangeTimes() {
		return rangeTimes;
	}
	public void setRangeTimes(String rangeTimes) {
		this.rangeTimes = rangeTimes;
	}
	public String getCreateTimeDesc() {
		return createTimeDesc;
	}
	public void setCreateTimeDesc(String createTimeDesc) {
		this.createTimeDesc = createTimeDesc;
	}
	public String getStartTimeDesc() {
		return startTimeDesc;
	}
	public void setStartTimeDesc(String startTimeDesc) {
		this.startTimeDesc = startTimeDesc;
	}
	public String getEndTimeDesc() {
		return endTimeDesc;
	}
	public void setEndTimeDesc(String endTimeDesc) {
		this.endTimeDesc = endTimeDesc;
	}
	public String getRelStartTimeDesc() {
		return relStartTimeDesc;
	}
	public void setRelStartTimeDesc(String relStartTimeDesc) {
		this.relStartTimeDesc = relStartTimeDesc;
	}
	public String getRelEndTimeDesc() {
		return relEndTimeDesc;
	}
	public void setRelEndTimeDesc(String relEndTimeDesc) {
		this.relEndTimeDesc = relEndTimeDesc;
	}
	public int getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public int getAuditorId() {
		return auditorId;
	}
	public void setAuditorId(int auditorId) {
		this.auditorId = auditorId;
	}
	public String getAuditorName() {
		return auditorName;
	}
	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}
	public int getChargerId() {
		return chargerId;
	}
	public void setChargerId(int chargerId) {
		this.chargerId = chargerId;
	}
	public String getChargerName() {
		return chargerName;
	}
	public void setChargerName(String chargerName) {
		this.chargerName = chargerName;
	}
	public String getJoinerIds() {
		return joinerIds;
	}
	public void setJoinerIds(String joinerIds) {
		this.joinerIds = joinerIds;
	}
	public String getJoinerNames() {
		return joinerNames;
	}
	public void setJoinerNames(String joinerNames) {
		this.joinerNames = joinerNames;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public String getLeaderRemark() {
		return leaderRemark;
	}
	public void setLeaderRemark(String leaderRemark) {
		this.leaderRemark = leaderRemark;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
	public int getParentTaskId() {
		return parentTaskId;
	}
	public void setParentTaskId(int parentTaskId) {
		this.parentTaskId = parentTaskId;
	}
	public String getParentTaskName() {
		return parentTaskName;
	}
	public void setParentTaskName(String parentTaskName) {
		this.parentTaskName = parentTaskName;
	}
	public String getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}
	public String getRelTimes() {
		return relTimes;
	}
	public void setRelTimes(String relTimes) {
		this.relTimes = relTimes;
	}
}
