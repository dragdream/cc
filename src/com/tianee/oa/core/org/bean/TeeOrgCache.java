package com.tianee.oa.core.org.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="org_cache")
public class TeeOrgCache {
	@Id
	@Column(name="KEY_")
	private String key;
	
	@Column(name="VALUE_")
	private byte[] value;
	
	@Column(name="VERSION_")
	private int version;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public byte[] getValue() {
		return value;
	}

	public void setValue(byte[] value) {
		this.value = value;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
}
