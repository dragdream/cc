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
@Table(name="eval_score_level")
public class TeeEvalScoreLevel{
	@Id
	@Column(name="SID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator="eval_score_level_seq_gen")
	@SequenceGenerator(name="eval_score_level_seq_gen", sequenceName="eval_score_level_seq")
	private int sid;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="RANGE1")
	private double range1;
	
	@Column(name="RANGE2")
	private double range2;
	
	@ManyToOne()
	@Index(name="EVAL_TEMPLATE_ID_INDEX")
	@JoinColumn(name="EVAL_TEMPLATE_ID")
	private TeeEvalTemplate evalTemplate;

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

	public TeeEvalTemplate getEvalTemplate() {
		return evalTemplate;
	}

	public void setEvalTemplate(TeeEvalTemplate evalTemplate) {
		this.evalTemplate = evalTemplate;
	}
	
}