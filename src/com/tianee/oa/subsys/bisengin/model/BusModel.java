package com.tianee.oa.subsys.bisengin.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class BusModel {
	private String bisKey;
	private String businessName;
	private String businessTitle;
	private String businessOperation;
	private String headerFields;
	private String queryFields;
	private String orderField;
	private String groupField;
	private String displayCondition;
	private int bisFormId;//业务表单主键
	private String bisFormName;//表单名称
	private int businessCatId;//业务类别主键
	private String businessCatName;//业务类别名称
	private int flowId;//绑定流程
	private String flowName;
	//private TeeFlowProcess flowProcess;
	private int flowStep;//触发步骤主键
	private String mapping;//字段映射
	
	private List<Map> mappingList=new ArrayList<Map>();//存放详细的字段映射关系
	
	
	
	

	public List<Map> getMappingList() {
		return mappingList;
	}

	public void setMappingList(List<Map> mappingList) {
		this.mappingList = mappingList;
	}

	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	public int getFlowStep() {
		return flowStep;
	}

	public void setFlowStep(int flowStep) {
		this.flowStep = flowStep;
	}

	public int getFlowId() {
		return flowId;
	}
	
	public String getMapping() {
		return mapping;
	}
	public void setFlowId(int flowId) {
		this.flowId = flowId;
	}
	
	public void setMapping(String mapping) {
		this.mapping = mapping;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getBusinessTitle() {
		return businessTitle;
	}
	public void setBusinessTitle(String businessTitle) {
		this.businessTitle = businessTitle;
	}
	public String getBusinessOperation() {
		return businessOperation;
	}
	public void setBusinessOperation(String businessOperation) {
		this.businessOperation = businessOperation;
	}
	public String getHeaderFields() {
		return headerFields;
	}
	public void setHeaderFields(String headerFields) {
		this.headerFields = headerFields;
	}
	public String getQueryFields() {
		return queryFields;
	}
	public void setQueryFields(String queryFields) {
		this.queryFields = queryFields;
	}
	public String getOrderField() {
		return orderField;
	}
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
	public String getGroupField() {
		return groupField;
	}
	public void setGroupField(String groupField) {
		this.groupField = groupField;
	}
	public String getDisplayCondition() {
		return displayCondition;
	}
	public void setDisplayCondition(String displayCondition) {
		this.displayCondition = displayCondition;
	}
	public int getBisFormId() {
		return bisFormId;
	}
	public void setBisFormId(int bisFormId) {
		this.bisFormId = bisFormId;
	}
	public String getBisFormName() {
		return bisFormName;
	}
	public void setBisFormName(String bisFormName) {
		this.bisFormName = bisFormName;
	}
	public int getBusinessCatId() {
		return businessCatId;
	}
	public void setBusinessCatId(int businessCatId) {
		this.businessCatId = businessCatId;
	}
	public String getBusinessCatName() {
		return businessCatName;
	}
	public void setBusinessCatName(String businessCatName) {
		this.businessCatName = businessCatName;
	}
	public String getBisKey() {
		return bisKey;
	}
	public void setBisKey(String bisKey) {
		this.bisKey = bisKey;
	}
	

}
