package com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity;

public class FlowInfo {

	// 案件ID
	private String caseId;
	//处理环节
	private String processItem;
	// 处理开始时间
	private String startTime;
	// 处理结束时间
	private String endTime;
	// 处理结果
	private String dealResult;
	// 原因
	private String reason;
	// 备注
	private String remark;
	// 结案类型
	private String caseCloseType;
	// 结案时间
	private String caseCloseTime;
	// 归档目录
	private String archiveDir;
	// 归档类型:01 正卷 02 副卷 03 公示
	private String archiveType;
	// 第一承办人
	private String dealManFirstId;
	// 第二承办人
	private String dealManSecondId;
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getProcessItem() {
		return processItem;
	}
	public void setProcessItem(String processItem) {
		this.processItem = processItem;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getDealResult() {
		return dealResult;
	}
	public void setDealResult(String dealResult) {
		this.dealResult = dealResult;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCaseCloseType() {
		return caseCloseType;
	}
	public void setCaseCloseType(String caseCloseType) {
		this.caseCloseType = caseCloseType;
	}
	public String getCaseCloseTime() {
		return caseCloseTime;
	}
	public void setCaseCloseTime(String caseCloseTime) {
		this.caseCloseTime = caseCloseTime;
	}
	public String getArchiveDir() {
		return archiveDir;
	}
	public void setArchiveDir(String archiveDir) {
		this.archiveDir = archiveDir;
	}
	public String getArchiveType() {
		return archiveType;
	}
	public void setArchiveType(String archiveType) {
		this.archiveType = archiveType;
	}
	public String getDealManFirstId() {
		return dealManFirstId;
	}
	public void setDealManFirstId(String dealManFirstId) {
		this.dealManFirstId = dealManFirstId;
	}
	public String getDealManSecondId() {
		return dealManSecondId;
	}
	public void setDealManSecondId(String dealManSecondId) {
		this.dealManSecondId = dealManSecondId;
	}
	
}
