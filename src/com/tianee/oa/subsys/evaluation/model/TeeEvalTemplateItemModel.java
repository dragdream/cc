package com.tianee.oa.subsys.evaluation.model;


public class TeeEvalTemplateItemModel{
	private int sid;
	
	private String name;
	
	private String remark;
	
	private int evalTemplateId;
	
	private String evalTemplateSubject;
	
	private String level;
	 
	private String number;
	
	private double standard;
	
	private double range1;
	
	private double range2;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public int getEvalTemplateId() {
		return evalTemplateId;
	}

	public void setEvalTemplateId(int evalTemplateId) {
		this.evalTemplateId = evalTemplateId;
	}

	public String getEvalTemplateSubject() {
		return evalTemplateSubject;
	}

	public void setEvalTemplateSubject(String evalTemplateSubject) {
		this.evalTemplateSubject = evalTemplateSubject;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public double getStandard() {
		return standard;
	}

	public void setStandard(double standard) {
		this.standard = standard;
	}

	public double getRange1() {
		return range1;
	}

	public void setRange1(double range1) {
		this.range1 = range1;
	}

	public double getRange2() {
		return range2;
	}

	public void setRange2(double range2) {
		this.range2 = range2;
	}

}