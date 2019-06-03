package com.tianee.oa.core.workflow.flowmanage.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "html_print_template")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TeeHtmlPrintTemplate implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "html_print_template_seq_gen")
	@SequenceGenerator(name = "html_print_template_seq_gen", sequenceName = "html_print_template_seq")
	@Column(name = "SID")
	private int sid;


	@Column(name = "TEMPLATE_NAME")
	private String templateName = "";// 模板名称

	@Column(name = "TPL_CONTENT")
	private String tplContent= "";// 模板内容
	
	
	@Column(name = "FLOW_TYPE_ID")
	private int flowTypeId;// 所属流程类型


	public int getSid() {
		return sid;
	}


	public String getTemplateName() {
		return templateName;
	}


	public String getTplContent() {
		return tplContent;
	}


	public int getFlowTypeId() {
		return flowTypeId;
	}


	public void setSid(int sid) {
		this.sid = sid;
	}


	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}


	public void setTplContent(String tplContent) {
		this.tplContent = tplContent;
	}


	public void setFlowTypeId(int flowTypeId) {
		this.flowTypeId = flowTypeId;
	}
	
	
	
	
}
