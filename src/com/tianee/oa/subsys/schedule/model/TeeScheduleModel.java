package com.tianee.oa.subsys.schedule.model;

import java.util.List;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;

public class TeeScheduleModel {
	private String uuid;//自增id
	private String title;//标题
	private int type;//类型 1：个人计划  2：部门计划 3：公司计划
	private int rangeType;//时间类型  1：日计划 2：周计划  3：月计划  4：季度计划  5：半年计划  6：年计划
	private String userName;//人员
	private int userId;//人员
	private String deptName;//部门
	private int deptId;//部门
	private String rangeDesc;//时间描述
	private String time1Desc;//时间段1
	private String time2Desc;//时间段2
	private String content;//内容
	private String summarize;//总结
	private int flag;//0:进行中 1：已完成  2:超时完成
	private String finishTimeDesc;//完成时间
	private String reportedRangesIds;
	private String reportedRangesNames;
	private String sharedRangesIds;
	private String sharedRangesNames;
	private String crTimeDesc;
	private List<TeeAttachmentModel> attachMentModel;
	
	private int managerUserId;
	
	private String managerUserName;
	
	public int getManagerUserId() {
		return managerUserId;
	}
	public void setManagerUserId(int managerUserId) {
		this.managerUserId = managerUserId;
	}
	public String getManagerUserName() {
		return managerUserName;
	}
	public void setManagerUserName(String managerUserName) {
		this.managerUserName = managerUserName;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getRangeType() {
		return rangeType;
	}
	public void setRangeType(int rangeType) {
		this.rangeType = rangeType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public int getDeptId() {
		return deptId;
	}
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
	public String getRangeDesc() {
		return rangeDesc;
	}
	public void setRangeDesc(String rangeDesc) {
		this.rangeDesc = rangeDesc;
	}
	public String getTime1Desc() {
		return time1Desc;
	}
	public void setTime1Desc(String time1Desc) {
		this.time1Desc = time1Desc;
	}
	public String getTime2Desc() {
		return time2Desc;
	}
	public void setTime2Desc(String time2Desc) {
		this.time2Desc = time2Desc;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSummarize() {
		return summarize;
	}
	public void setSummarize(String summarize) {
		this.summarize = summarize;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getFinishTimeDesc() {
		return finishTimeDesc;
	}
	public void setFinishTimeDesc(String finishTimeDesc) {
		this.finishTimeDesc = finishTimeDesc;
	}
	public String getReportedRangesIds() {
		return reportedRangesIds;
	}
	public void setReportedRangesIds(String reportedRangesIds) {
		this.reportedRangesIds = reportedRangesIds;
	}
	public String getReportedRangesNames() {
		return reportedRangesNames;
	}
	public void setReportedRangesNames(String reportedRangesNames) {
		this.reportedRangesNames = reportedRangesNames;
	}
	public String getSharedRangesIds() {
		return sharedRangesIds;
	}
	public void setSharedRangesIds(String sharedRangesIds) {
		this.sharedRangesIds = sharedRangesIds;
	}
	public String getSharedRangesNames() {
		return sharedRangesNames;
	}
	public void setSharedRangesNames(String sharedRangesNames) {
		this.sharedRangesNames = sharedRangesNames;
	}
	public String getCrTimeDesc() {
		return crTimeDesc;
	}
	public void setCrTimeDesc(String crTimeDesc) {
		this.crTimeDesc = crTimeDesc;
	}
	public List<TeeAttachmentModel> getAttachMentModel() {
		return attachMentModel;
	}
	public void setAttachMentModel(List<TeeAttachmentModel> attachMentModel) {
		this.attachMentModel = attachMentModel;
	}
}
