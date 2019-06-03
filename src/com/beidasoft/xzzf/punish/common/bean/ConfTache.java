package com.beidasoft.xzzf.punish.common.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ZF_CONF_TACHE")
public class ConfTache {
	//系统配置环节CODE
	@Id
	@Column(name = "CONF_TACHE_CODE")
	private String confTacheCode;				
	
	//系统配置环节url
	@Column(name = "CONF_TACHE_URL")
	private String confTacheUrl;				

	//系统配置环节名称
	@Column(name = "CONF_TACHE_NAME")
	private String confTacheName;				
	
	//排序
	@Column(name = "CONF_TACHE_INDEX")
	private int confTacheIndex;					

	public String getConfTacheCode() {
		return confTacheCode;
	}

	public void setConfTacheCode(String confTacheCode) {
		this.confTacheCode = confTacheCode;
	}

	public String getConfTacheUrl() {
		return confTacheUrl;
	}

	public void setConfTacheUrl(String confTacheUrl) {
		this.confTacheUrl = confTacheUrl;
	}

	public String getConfTacheName() {
		return confTacheName;
	}

	public void setConfTacheName(String confTacheName) {
		this.confTacheName = confTacheName;
	}

	public int getConfTacheIndex() {
		return confTacheIndex;
	}

	public void setConfTacheIndex(int confTacheIndex) {
		this.confTacheIndex = confTacheIndex;
	}
	
	
	
}