package com.tianee.oa.core.general.bean;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "SYS_PARA")
public class TeeSysPara {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="SYS_PARA_seq_gen")
	@SequenceGenerator(name="SYS_PARA_seq_gen", sequenceName="SYS_PARA_seq")
	private int uuid;
	
	@Column(name="PARA_NAME",length=200)
	private String paraName;
	
	@Lob
	@Column(name="PARA_VALUE")
	private String paraValue;
	
	public int getUuid() {
		return uuid;
	}
	public void setUuid(int uuid) {
		this.uuid = uuid;
	}
	public String getParaName() {
		return paraName;
	}
	public void setParaName(String paraName) {
		this.paraName = paraName;
	}
	public String getParaValue() {
		if(paraValue == null){
			paraValue = "";
		}
		return paraValue;
	}
	public void setParaValue(String paraValue) {
		this.paraValue = paraValue;
	}

}
