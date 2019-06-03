package com.tianee.oa.subsys.cms.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="CMS_DOCUMENT_CATEGORY")
public class DocumentCategory {
	@Id
	@Column(name="SID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CMS_DOCUMENT_CATEGORY_seq_gen")
	@SequenceGenerator(name="CMS_DOCUMENT_CATEGORY_seq_gen", sequenceName="CMS_DOCUMENT_CATEGORY_seq")
	private int sid;
	
	@Column(name="PRIV_")
	private int priv;//权限值   1，2，4，8，16，……
	
	@Column(name="CAT_NAME_")
	private String name;//分类名称

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getPriv() {
		return priv;
	}

	public void setPriv(int priv) {
		this.priv = priv;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
