package com.tianee.oa.subsys.evaluation.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="eval_scroing_item_type")
public class TeeEvalScoringItemType{
	@Id
	@Column(name="SID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator="eval_scroing_item_type_seq_gen")
	@SequenceGenerator(name="eval_scroing_item_type_seq_gen", sequenceName="eval_scroing_item_type_seq")
	private int sid;
	
	@Column(name="NAME")
	private String name;

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
	
	
	
}