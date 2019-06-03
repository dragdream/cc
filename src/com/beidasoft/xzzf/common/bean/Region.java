package com.beidasoft.xzzf.common.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ZF_REGION")
public class Region {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "ZF_REGION_seq_gen")
	@SequenceGenerator(name = "ZF_REGION_seq_gen", sequenceName = "ZF_REGION_seq")
	@Column(name = "ID")
	private int id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "PARENT_ID")
	private String parentId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}
