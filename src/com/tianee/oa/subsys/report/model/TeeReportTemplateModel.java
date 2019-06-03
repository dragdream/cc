package com.tianee.oa.subsys.report.model;

public class TeeReportTemplateModel {
	private int sid;
	private String tplName;
	private int conditionId;
	private String conditionName;
	private String groupBy;
	private String groupByOrder;
	private String sortBy;
	private String sortByOrder;
	private int pageSize;
	private int operPriv;
	private String userPrivIds;
	private String userPrivNames;
	private String deptPrivIds;
	private String deptPrivNames;
	private int flowId;
	private int mergeGroup;//分组合并
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getTplName() {
		return tplName;
	}
	public void setTplName(String tplName) {
		this.tplName = tplName;
	}
	public int getConditionId() {
		return conditionId;
	}
	public void setConditionId(int conditionId) {
		this.conditionId = conditionId;
	}
	public String getGroupBy() {
		return groupBy;
	}
	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}
	public String getGroupByOrder() {
		return groupByOrder;
	}
	public void setGroupByOrder(String groupByOrder) {
		this.groupByOrder = groupByOrder;
	}
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	public String getSortByOrder() {
		return sortByOrder;
	}
	public void setSortByOrder(String sortByOrder) {
		this.sortByOrder = sortByOrder;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getOperPriv() {
		return operPriv;
	}
	public void setOperPriv(int operPriv) {
		this.operPriv = operPriv;
	}
	public String getUserPrivIds() {
		return userPrivIds;
	}
	public void setUserPrivIds(String userPrivIds) {
		this.userPrivIds = userPrivIds;
	}
	public String getUserPrivNames() {
		return userPrivNames;
	}
	public void setUserPrivNames(String userPrivNames) {
		this.userPrivNames = userPrivNames;
	}
	public String getDeptPrivIds() {
		return deptPrivIds;
	}
	public void setDeptPrivIds(String deptPrivIds) {
		this.deptPrivIds = deptPrivIds;
	}
	public String getDeptPrivNames() {
		return deptPrivNames;
	}
	public void setDeptPrivNames(String deptPrivNames) {
		this.deptPrivNames = deptPrivNames;
	}
	public int getFlowId() {
		return flowId;
	}
	public void setFlowId(int flowId) {
		this.flowId = flowId;
	}
	public String getConditionName() {
		return conditionName;
	}
	public void setConditionName(String conditionName) {
		this.conditionName = conditionName;
	}
	public int getMergeGroup() {
		return mergeGroup;
	}
	public void setMergeGroup(int mergeGroup) {
		this.mergeGroup = mergeGroup;
	}
	
	
}
