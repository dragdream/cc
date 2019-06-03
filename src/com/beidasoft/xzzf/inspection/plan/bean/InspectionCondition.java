package com.beidasoft.xzzf.inspection.plan.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ZF_INSPECTION_CONDITION")
public class InspectionCondition {
	//主键
	@Id
	@Column(name = "ID")
	private String id;
	
	//被检查企业类型,多选,   1,2,3的方式保存
	@Column(name = "CONDITION_TYPE")
	private String conditionType;
	
	//被检查类型企业所占比例
	@Column(name = "CONDITION_PROPORTION")
	private int conditionProportion;

	//被检查类型企业的信用等级
	@Column(name = "CONDITION_CREDIT")
	private int conditionCredit;
	
	//被检查企业所在城区
	@Column(name = "CONDITION_REGION")
	private int conditionRegion;
	
	//检查计划主表id
	@Column(name = "INSPECTION_ID")
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
	
}
