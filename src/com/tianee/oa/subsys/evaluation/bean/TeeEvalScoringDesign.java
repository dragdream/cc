package com.tianee.oa.subsys.evaluation.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

@Entity
@Table(name="eval_scoring_design")
public class TeeEvalScoringDesign{
	@Id
	@Column(name="SID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator="eval_scoring_design_seq_gen")
	@SequenceGenerator(name="eval_scoring_design_seq_gen", sequenceName="eval_scoring_design_seq")
	private int sid;
	
	@Column(name="WEIGHT")
	private double weight;
	
	@ManyToOne()
	@Index(name="SCORE_ITEM_TYPE_ID_INDEX")
	@JoinColumn(name="SCORE_ITEM_TYPE_ID")
	private TeeEvalScoringItemType scoreItemType;
	
	@Column(name="ITEMS")
	private String items;
	
	@Column(name="PERSONS")
	private String persons;
	
	@Column(name="EXTRAS")
	private int extras;
	
	@ManyToOne()
	@Index(name="EVAL_TEMPLATE_ID_DESIGN_INDEX")
	@JoinColumn(name="EVAL_TEMPLATE_ID")
	private TeeEvalTemplate evalTemplate;

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

	public TeeEvalScoringItemType getScoreItemType() {
		return scoreItemType;
	}

	public void setScoreItemType(TeeEvalScoringItemType scoreItemType) {
		this.scoreItemType = scoreItemType;
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

	public TeeEvalTemplate getEvalTemplate() {
		return evalTemplate;
	}

	public void setEvalTemplate(TeeEvalTemplate evalTemplate) {
		this.evalTemplate = evalTemplate;
	}
	
	
	
}