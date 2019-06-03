package com.tianee.oa.core.priv.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

public class TeeSysMenuPrivModel {
	
	private int sid;
	
	private String privUrl;//PRIV_URL 页面路径（jsp/action）
	
	private String privFlag;//PRIV_FLAG  权限是否校验0-不校验1-校验
	
	private String privName;//PRIV_NAME 权限名称（描述
	
	private String sysMenuIds;//菜单权限中间表

	private String sysMenuNames;
	
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getPrivUrl() {
		return privUrl;
	}

	public void setPrivUrl(String privUrl) {
		this.privUrl = privUrl;
	}

	public String getPrivFlag() {
		return privFlag;
	}

	public void setPrivFlag(String privFlag) {
		this.privFlag = privFlag;
	}

	public String getPrivName() {
		return privName;
	}

	public void setPrivName(String privName) {
		this.privName = privName;
	}

	public String getSysMenuIds() {
		return sysMenuIds;
	}

	public void setSysMenuIds(String sysMenuIds) {
		this.sysMenuIds = sysMenuIds;
	}

	public String getSysMenuNames() {
		return sysMenuNames;
	}

	public void setSysMenuNames(String sysMenuNames) {
		this.sysMenuNames = sysMenuNames;
	}

	

	
	
}
