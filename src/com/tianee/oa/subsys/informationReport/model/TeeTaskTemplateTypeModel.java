package com.tianee.oa.subsys.informationReport.model;

import javax.persistence.Column;

public class TeeTaskTemplateTypeModel {
	private int sid;//自增id
	
	private String typeName;//类型名称

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
}
