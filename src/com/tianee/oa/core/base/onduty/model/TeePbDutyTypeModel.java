package com.tianee.oa.core.base.onduty.model;

import java.util.ArrayList;
import java.util.List;

public class TeePbDutyTypeModel {

    private int sid;
	
	private String typeName;
	
	private String sease;
	
	private int number;
	
	List<TeePbTypeChildModel> childModel=new ArrayList<TeePbTypeChildModel>();

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

	public String getSease() {
		return sease;
	}

	public void setSease(String sease) {
		this.sease = sease;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public List<TeePbTypeChildModel> getChildModel() {
		return childModel;
	}

	public void setChildModel(List<TeePbTypeChildModel> childModel) {
		this.childModel = childModel;
	}
	
}
