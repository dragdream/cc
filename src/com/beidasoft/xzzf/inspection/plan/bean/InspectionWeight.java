package com.beidasoft.xzzf.inspection.plan.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ZF_INSPECTION_WEIGHT")
public class InspectionWeight {
	//主键
	@Id
	@Column(name = "ID")
	private String id;
	
	//红色权重
	@Column(name = "WEIGHT_RED")
	private int weightRed;
	
	//黄色权重
	@Column(name = "WEIGHT_YELLOW")
	private int weightYellow;

	//蓝色权重
	@Column(name = "WEIGHT_BLUE")
	private int weightBlue;

	//检查计划主表id
	@Column(name = "INSPECTION_ID")
	private String inspectionId;

	//检查企业总数
	@Column(name = "INSPECTION_TOTAL")
	private int inspectionTotal;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getWeightRed() {
		return weightRed;
	}

	public void setWeightRed(int weightRed) {
		this.weightRed = weightRed;
	}

	public int getWeightYellow() {
		return weightYellow;
	}

	public void setWeightYellow(int weightYellow) {
		this.weightYellow = weightYellow;
	}

	public int getWeightBlue() {
		return weightBlue;
	}

	public void setWeightBlue(int weightBlue) {
		this.weightBlue = weightBlue;
	}

	public String getInspectionId() {
		return inspectionId;
	}

	public void setInspectionId(String inspectionId) {
		this.inspectionId = inspectionId;
	}

	public int getInspectionTotal() {
		return inspectionTotal;
	}

	public void setInspectionTotal(int inspectionTotal) {
		this.inspectionTotal = inspectionTotal;
	}
	
	
}
