package com.tianee.oa.subsys.bisengin.bean;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="BIS_CONFIG")
public class BisConfig {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="BIS_CONFIG_seq_gen")
	@SequenceGenerator(name="BIS_CONFIG_seq_gen", sequenceName="BIS_CONFIG_seq")
	private int sid;
	
	@Column(name="TYPE")
	private int type;
	
	@Column(name="TYPE_DESC")
	private String typeDesc;
	
	@Column(name="STATUS")
	private int status;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
