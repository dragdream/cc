package com.beidasoft.xzzf.inspection.plan.model;


public class InspectionRegionModel {
	//主键
	private String id;
	
	//检查计划主表ID
	private String inspectionId;
	
	//筛选企业所在区域
	private int regionCode;
	
	//筛选企业所在区域名称
	private String regionName;
	
	//该区域所占权重
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

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public int getRegionWeight() {
		return regionWeight;
	}

	public void setRegionWeight(int regionWeight) {
		this.regionWeight = regionWeight;
	}
	
}
