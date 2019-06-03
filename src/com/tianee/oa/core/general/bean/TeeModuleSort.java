package com.tianee.oa.core.general.bean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="MODULE_SORT")
public class TeeModuleSort {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="MODULE_SORT_seq_gen")
	@SequenceGenerator(name="MODULE_SORT_seq_gen", sequenceName="MODULE_SORT_seq")
	private int sid;
	
	@Column(name="KEY_")
	private String key;
	
	@Column(name="VALUE_")
	private String value;
	
	@Column(name="COLOR_")
	private String color;
	
	@Column(name="WX_APP_ID")
	private String wxAppId;
	
	@Column(name="WX_SECRET")
	private String wxSecret;
	
	@Column(name="DD_APP_ID")
	private String ddAppId;
	
	@Column(name="URL_")
	private String url;

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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getWxAppId() {
		return wxAppId;
	}

	public void setWxAppId(String wxAppId) {
		this.wxAppId = wxAppId;
	}

	public String getDdAppId() {
		return ddAppId;
	}

	public void setDdAppId(String ddAppId) {
		this.ddAppId = ddAppId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getWxSecret() {
		return wxSecret;
	}

	public void setWxSecret(String wxSecret) {
		this.wxSecret = wxSecret;
	}
	
	
}
