package com.tianee.oa.subsys.evaluation.model;


public class TeeEvalScoringDesignModel{
	private int sid;
	
	private double weight;
	
	private int scoreItemTypeId;
	
	private String scoreItemTypeName;
	
	private String items;
	
	private String persons;
	
	private String personNames;
	
	private int extras;
	
	private int evalTemplateId;
	
	private String evalTemplateSubject;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}


	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public String getPersons() {
		return persons;
	}

	public void setPersons(String persons) {
		this.persons = persons;
	}

	public int getExtras() {
		return extras;
	}

	public void setExtras(int extras) {
		this.extras = extras;
	}

	public int getScoreItemTypeId() {
		return scoreItemTypeId;
	}

	public void setScoreItemTypeId(int scoreItemTypeId) {
		this.scoreItemTypeId = scoreItemTypeId;
	}

	public String getScoreItemTypeName() {
		return scoreItemTypeName;
	}

	public void setScoreItemTypeName(String scoreItemTypeName) {
		this.scoreItemTypeName = scoreItemTypeName;
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

	public String getPersonNames() {
		return personNames;
	}

	public void setPersonNames(String personNames) {
		this.personNames = personNames;
	}

	
}