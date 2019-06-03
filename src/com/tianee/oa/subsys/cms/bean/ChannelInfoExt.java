package com.tianee.oa.subsys.cms.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 栏目扩展模型
 * @author liteng
 *
 */
@Entity
@Table(name="CMS_CHANNEL_INFO_EXT")
public class ChannelInfoExt {
	@Id
	@Column(name="SID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CMS_CHANNEL_INFO_EXT_seq_gen")
	@SequenceGenerator(name="CMS_CHANNEL_INFO_EXT_seq_gen", sequenceName="CMS_CHANNEL_INFO_EXT_seq")
	private int sid;
	
	@Column(name="FIELD_NAME")
	private String fieldName;
	
	@Column(name="FIELD_TITLE")
	private String fieldTitle;

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
	
}
