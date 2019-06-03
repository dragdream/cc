package com.beidasoft.xzzf.inspection.plan.model;

public class InspectionOrgModel {
	//主键
	private String id;
	//检查计划ID
	private String inspectionId;
	//监管企业ID
	private String orgId;
	//监管企业名称
	private String orgName;
	//机构代码
	private String orgCode;
	//企业类型代码
	private String orgType;
	//企业类型名称
	private String orgTypeName;
	//企业信用等级
	private String creditLevel;
	//企业所在城区
	private String regDistrict;
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
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	public String getOrgTypeName() {
		return orgTypeName;
	}
	public void setOrgTypeName(String orgTypeName) {
		this.orgTypeName = orgTypeName;
	}
	public String getCreditLevel() {
		return creditLevel;
	}
	public void setCreditLevel(String creditLevel) {
		this.creditLevel = creditLevel;
	}
	public String getRegDistrict() {
		return regDistrict;
	}
	public void setRegDistrict(String regDistrict) {
		this.regDistrict = regDistrict;
	}
}
