package com.beidasoft.xzzf.inspection.plan.model;

public class InspectionConditionModel {
	//主键
	private String id;
	
	//被检查企业类型   ","分隔
	private String conditionType;
	
	//被检查企业类型名称  ","分隔
	private String conditionTypeName;
	
	//被检查类型企业所占比例
	private int conditionProportion;

	//被检查类型企业的信用等级
	private int conditionCredit;
	
	//被检查企业所在城区
	private int conditionRegion;
	
	//被检查企业所在城区名称
	private String conditionRegionName;
	
	//检查计划主表id
	private String inspectionId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	public String getConditionType() {
		return conditionType;
	}

	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}

	public String getConditionTypeName() {
		return conditionTypeName;
	}

	public void setConditionTypeName(String conditionTypeName) {
		this.conditionTypeName = conditionTypeName;
	}

	public int getConditionProportion() {
		return conditionProportion;
	}

	public void setConditionProportion(int conditionProportion) {
		this.conditionProportion = conditionProportion;
	}

	public int getConditionCredit() {
		return conditionCredit;
	}

	public void setConditionCredit(int conditionCredit) {
		this.conditionCredit = conditionCredit;
	}

	public String getInspectionId() {
		return inspectionId;
	}

	public void setInspectionId(String inspectionId) {
		this.inspectionId = inspectionId;
	}

	public int getConditionRegion() {
		return conditionRegion;
	}

	public void setConditionRegion(int conditionRegion) {
		this.conditionRegion = conditionRegion;
	}

	public String getConditionRegionName() {
		return conditionRegionName;
	}

	public void setConditionRegionName(String conditionRegionName) {
		this.conditionRegionName = conditionRegionName;
	}
	
}
