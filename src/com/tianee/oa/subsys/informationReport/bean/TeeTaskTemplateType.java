package com.tianee.oa.subsys.informationReport.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/*
 * 上报信息类型表
 */

@Entity
@Table(name = "rep_task_template_type")
public class TeeTaskTemplateType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="rep_task_template_seq_gen")
	@SequenceGenerator(name="rep_task_template_seq_gen", sequenceName="rep_task_template_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	
	@Column(name = "TYPE_NAME")
	private String typeName;//类型名称
	
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
