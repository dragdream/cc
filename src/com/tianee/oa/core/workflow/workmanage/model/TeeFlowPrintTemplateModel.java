package com.tianee.oa.core.workflow.workmanage.model;

import com.tianee.oa.webframe.httpModel.TeeBaseModel;

public class TeeFlowPrintTemplateModel extends TeeBaseModel {
	private int sid;
	private int flowTypeId;
	private String flowTypeName;
	private int modulType;//1-打印模板   2-手写成批单
	private String modulName;//模版名称
	private String modulContent;
	private String modulField;
	private String flowPrcsIds;
	private String flowPrcsDescs;
	
	private int isUpdate;//是否更换模版
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getFlowTypeId() {
		return flowTypeId;
	}
	public void setFlowTypeId(int flowTypeId) {
		this.flowTypeId = flowTypeId;
	}
	public String getFlowTypeName() {
		return flowTypeName;
	}
	public void setFlowTypeName(String flowTypeName) {
		this.flowTypeName = flowTypeName;
	}
		
	public int getModulType() {
		return modulType;
	}
	public void setModulType(int modulType) {
		this.modulType = modulType;
	}
	public String getModulName() {
		return modulName;
	}
	public void setModulName(String modulName) {
		this.modulName = modulName;
	}
	public String getModulContent() {
		return modulContent;
	}
	public void setModulContent(String modulContent) {
		this.modulContent = modulContent;
	}
	public String getModulField() {
		return modulField;
	}
	public void setModulField(String modulField) {
		this.modulField = modulField;
	}
	public String getFlowPrcsIds() {
		return flowPrcsIds;
	}
	public void setFlowPrcsIds(String flowPrcsIds) {
		this.flowPrcsIds = flowPrcsIds;
	}
	public String getFlowPrcsDescs() {
		return flowPrcsDescs;
	}
	public void setFlowPrcsDescs(String flowPrcsDescs) {
		this.flowPrcsDescs = flowPrcsDescs;
	}
	public int getIsUpdate() {
		return isUpdate;
	}
	public void setIsUpdate(int isUpdate) {
		this.isUpdate = isUpdate;
	}

}
