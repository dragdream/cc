package com.tianee.oa.sync.config.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 系统管理-组织机构管理-外部系统配置
 * @author ZATP18070502
 *
 */
@Entity
@Table(name="out_system_config")
public class TeeOutSystemConfig {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="system_config_seq_gen")
	@SequenceGenerator(name="system_config_seq_gen", sequenceName="system_config_seq")
	@Column(name="SID")
	private int sid;//主键
	
	@Column(name="SYSTEM_NAME")
	private String systemName;//外部系统名称
	
	@Column(name="SYSTEM_URL")
	private String systemUrl;//外部系统url地址
	
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getSystemUrl() {
		return systemUrl;
	}

	public void setSystemUrl(String systemUrl) {
		this.systemUrl = systemUrl;
	}
	
}
