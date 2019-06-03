package com.tianee.oa.core.base.onduty.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="pb_typechild")
public class TeePbTypeChild {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="pb_typechild_seq_gen")
	@SequenceGenerator(name="pb_typechild_seq_gen", sequenceName="pb_typechild_seq")
	@Column(name = "SID")
	private int sid;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="SERSE")
	private String sease;
	
	@Column(name="TYPE_ID")
	private int typeId;

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

	public String getSease() {
		return sease;
	}

	public void setSease(String sease) {
		this.sease = sease;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}


	
}
