package com.tianee.oa.core.general.bean;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="MODULE_SORT_DETAIL")
public class TeeModuleSortDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="MODULE_SORT_DETAIL_seq_gen")
	@SequenceGenerator(name="MODULE_SORT_DETAIL_seq_gen", sequenceName="MODULE_SORT_DETAIL_seq")
	private int sid;
	
	@Column(name="KEY_")
	private String key;
	
	@Column(name="VALUE_")
	private String value;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
