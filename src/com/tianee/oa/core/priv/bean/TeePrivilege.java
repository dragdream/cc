package com.tianee.oa.core.priv.bean;
import org.hibernate.annotations.Index;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class TeePrivilege {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="PRIVILEGE_seq_gen")
	@SequenceGenerator(name="PRIVILEGE_seq_gen", sequenceName="PRIVILEGE_seq")
	private int privId;
	
	private String privCode;
	
	private String privDesc;
	
	public int getPrivId() {
		return privId;
	}
	public void setPrivId(int privId) {
		this.privId = privId;
	}
	public String getPrivDesc() {
		return privDesc;
	}
	public void setPrivDesc(String privDesc) {
		this.privDesc = privDesc;
	}
	public void setPrivCode(String privCode) {
		this.privCode = privCode;
	}
	public String getPrivCode() {
		return privCode;
	}
	
	
}
