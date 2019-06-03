package com.beidasoft.xzzf.inspection.plan.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ZF_INSPECTION_ORG")
public class InspectionOrg {
	//主键
	@Id
	@Column(name = "ID")
	private String id;
	
	//检查计划ID
	@Column(name = "INSPECTION_ID")
	private String inspectionId;
	
	//监管企业ID
	@Column(name = "ORG_ID")
	private String orgId;
	
	//监管企业名称
	@Column(name = "ORG_NAME")
	private String orgName;
	
	//机构代码
	@Column(name = "ORG_CODE")
	private String orgCode;
	
	//企业类型代码
	@Column(name = "ORG_TYPE")
	private String orgType;
	
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
	
}
