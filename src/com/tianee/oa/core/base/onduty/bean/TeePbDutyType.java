package com.tianee.oa.core.base.onduty.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="pb_type")
public class TeePbDutyType {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="pb_type_seq_gen")
	@SequenceGenerator(name="pb_type_seq_gen", sequenceName="pb_type_seq")
	@Column(name = "SID")
	private int sid;
	
	@Column(name="TYPE_NAME")
	private String typeName;
	
	@Column(name="SERSE")
	private String sease;
	
	@Column(name="NUMBER_")
	private int number;
	
	

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
	
	
}
