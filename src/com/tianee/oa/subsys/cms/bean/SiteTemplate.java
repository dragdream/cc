package com.tianee.oa.subsys.cms.bean;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="SITE_TEMPLATE")
public class SiteTemplate {
	@Id
	@Column(name="SID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator="SITE_TEMPLATE_seq_gen")
	@SequenceGenerator(name="SITE_TEMPLATE_seq_gen", sequenceName="SITE_TEMPLATE_seq")
	private int sid;
	
	@Column(name="SITE_ID")
	private int siteId;
	
	@Column(name="TPL_NAME")
	private String tplName;//模板名称
	
	@Column(name="TPL_FILE_NAME")
	private String tplFileName;//模板文件名称
	
	@Column(name="TPL_DESC")
	private String tplDesc;//模板描述

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public String getTplName() {
		return tplName;
	}

	public void setTplName(String tplName) {
		this.tplName = tplName;
	}

	public String getTplFileName() {
		return tplFileName;
	}

	public void setTplFileName(String tplFileName) {
		this.tplFileName = tplFileName;
	}

	public String getTplDesc() {
		return tplDesc;
	}

	public void setTplDesc(String tplDesc) {
		this.tplDesc = tplDesc;
	}
	
}
