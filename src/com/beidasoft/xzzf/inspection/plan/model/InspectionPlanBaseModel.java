package com.beidasoft.xzzf.inspection.plan.model;

import java.util.List;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;

public class InspectionPlanBaseModel {
	
	private String id;				//主键
	
	private String title;			//检查计划标题
	
	private String planStartDateStr;		//计划开始日期
	
	private String planClosedDateStr;	//计划结束日期
	
	private int inspectionType;		//检查类型（日常、双随机）
	
	private int inspectionMode;		//检查方式
	
	private String createTimeStr;	//创建日期
	
	private String planNum;	//检查计划编号   yyyyMMddhhmmss
	
	private String updateTimeStr;	//更新日期
	
	private int status;				//当前状态
	
	private int inspectionLevel;	//检查层级（市级下发、市级创建、区级创建）
	
	private String executeDepartment;	//执行部门
	
	private String executeDepartmentName;	//执行部门名称
	
	private String creatPerson;	//创建者
	
	private String inspectionExplain;	//计划说明
	
	private String inspectionAttachment;	//附件ids
	
	private List<TeeAttachmentModel> attachModels;//附件
	
	private int isDeleted;	//是否删除，0未删除，1已删除，默认为0

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPlanStartDateStr() {
		return planStartDateStr;
	}

	public void setPlanStartDateStr(String planStartDateStr) {
		this.planStartDateStr = planStartDateStr;
	}

	public String getPlanClosedDateStr() {
		return planClosedDateStr;
	}

	public void setPlanClosedDateStr(String planClosedDateStr) {
		this.planClosedDateStr = planClosedDateStr;
	}

	public int getInspectionType() {
		return inspectionType;
	}

	public void setInspectionType(int inspectionType) {
		this.inspectionType = inspectionType;
	}

	public int getInspectionMode() {
		return inspectionMode;
	}

	public void setInspectionMode(int inspectionMode) {
		this.inspectionMode = inspectionMode;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getInspectionLevel() {
		return inspectionLevel;
	}

	public void setInspectionLevel(int inspectionLevel) {
		this.inspectionLevel = inspectionLevel;
	}

	public String getExecuteDepartment() {
		return executeDepartment;
	}

	public void setExecuteDepartment(String executeDepartment) {
		this.executeDepartment = executeDepartment;
	}

	public String getCreatPerson() {
		return creatPerson;
	}

	public void setCreatPerson(String creatPerson) {
		this.creatPerson = creatPerson;
	}

	public String getInspectionExplain() {
		return inspectionExplain;
	}

	public void setInspectionExplain(String inspectionExplain) {
		this.inspectionExplain = inspectionExplain;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getInspectionAttachment() {
		return inspectionAttachment;
	}

	public void setInspectionAttachment(String inspectionAttachment) {
		this.inspectionAttachment = inspectionAttachment;
	}

	public String getExecuteDepartmentName() {
		return executeDepartmentName;
	}

	public void setExecuteDepartmentName(String executeDepartmentName) {
		this.executeDepartmentName = executeDepartmentName;
	}

	public List<TeeAttachmentModel> getAttachModels() {
		return attachModels;
	}

	public void setAttachModels(List<TeeAttachmentModel> attachModels) {
		this.attachModels = attachModels;
	}

	public String getPlanNum() {
		return planNum;
	}

	public void setPlanNum(String planNum) {
		this.planNum = planNum;
	}


	
}
