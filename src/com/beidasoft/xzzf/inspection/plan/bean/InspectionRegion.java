package com.beidasoft.xzzf.inspection.plan.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ZF_INSPECTION_REGION")
public class InspectionRegion {
	//主键
	@Id
	@Column(name = "ID")
	private String id;
	
	//检查计划主表ID
	@Column(name = "INSPECTION_ID")
	private String inspectionId;
	
	//筛选企业所在区域
	@Column(name = "REGION_CODE")
	private int regionCode;
	
	//该区域所占权重
	@Column(name = "REGION_WEIGHT")
	private int regionWeight;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInspectionId() {
		return inspectionId;
	}

	public void setInspectionId(String inspectionId) {
		this.inspectionId = inspectionId;
	}

	public int getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(int regionCode) {
		this.regionCode = regionCode;
	}

	public int getRegionWeight() {
		return regionWeight;
	}

	public void setRegionWeight(int regionWeight) {
		this.regionWeight = regionWeight;
	}
	
}
