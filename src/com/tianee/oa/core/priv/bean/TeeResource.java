package com.tianee.oa.core.priv.bean;
import org.hibernate.annotations.Index;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class TeeResource {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="RESOURCE_seq_gen")
	@SequenceGenerator(name="RESOURCE_seq_gen", sequenceName="RESOURCE_seq")
	private int resId;
	
	private String resCode;
	
	private String resDesc;
	
	public int getResId() {
		return resId;
	}
	public void setResId(int resId) {
		this.resId = resId;
	}
	public String getResDesc() {
		return resDesc;
	}
	public void setResDesc(String resDesc) {
		this.resDesc = resDesc;
	}
	public void setResCode(String resCode) {
		this.resCode = resCode;
	}
	public String getResCode() {
		return resCode;
	}
	
}
