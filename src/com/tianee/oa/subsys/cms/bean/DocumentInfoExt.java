package com.tianee.oa.subsys.cms.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 文档信息扩展字段模型
 * @author liteng
 *
 */
@Entity
@Table(name="CMS_DOCUMENT_INFO_EXT")
public class DocumentInfoExt {
	@Id
	@Column(name="SID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CMS_DOCUMENT_INFO_EXT_seq_gen")
	@SequenceGenerator(name="CMS_DOCUMENT_INFO_EXT_seq_gen", sequenceName="CMS_DOCUMENT_INFO_EXT_seq")
	private int sid;
	
	@Column(name="FIELD_NAME")
	private String fieldName;
	
	@Column(name="FIELD_TITLE")
	private String fieldTitle;
	
	@Column(name="FIELD_TYPE")
	private String fieldType;
	
	@Column(name="FIELD_FORMAT")
	private String fieldFormat;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldTitle() {
		return fieldTitle;
	}

	public void setFieldTitle(String fieldTitle) {
		this.fieldTitle = fieldTitle;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldFormat() {
		return fieldFormat;
	}

	public void setFieldFormat(String fieldFormat) {
		this.fieldFormat = fieldFormat;
	}
	
	
}
