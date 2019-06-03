package com.tianee.oa.subsys.report.bean;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="REPORT_TPL_ITEM")
public class TeeTemplateItem {
	@Id
	@Column(name="SID")
	@GeneratedValue(strategy=GenerationType.AUTO,generator="REPORT_TPL_ITEM_seq_gen")
	@SequenceGenerator(name="REPORT_TPL_ITEM_seq_gen", sequenceName="REPORT_TPL_ITEM_seq")
	private int sid;
	
	@Column(name="ITEM")
	private String item;//表单项，RUN_xxx=》固定项      DATA_xxx=》表单项
	
	@Column(name="DEF_NAME")
	private String defName;//自定义名称
	
	@Column(name="WIDTH")
	private int width;//宽度
	
	@Lob
	@Column(name="COL_MODEL")
	private String colModel;//列模型
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDXb99bb66bbdaf4f15bb436383336")
	@JoinColumn(name="TEMPLATE_ID")
	private TeeReportTemplate template;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}
	
	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getDefName() {
		return defName;
	}

	public void setDefName(String defName) {
		this.defName = defName;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getColModel() {
		return colModel;
	}

	public void setColModel(String colModel) {
		this.colModel = colModel;
	}

	public TeeReportTemplate getTemplate() {
		return template;
	}

	public void setTemplate(TeeReportTemplate template) {
		this.template = template;
	}
	
	
}
