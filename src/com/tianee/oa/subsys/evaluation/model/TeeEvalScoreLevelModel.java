package com.tianee.oa.subsys.evaluation.model;



public class TeeEvalScoreLevelModel{
	private int sid;
	
	private String name;
	
	private double range1;
	
	private double range2;
	
	private int evalTemplateId;
	
	private String evalTemplateSubject;

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

	
}