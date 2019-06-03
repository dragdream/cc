package com.tianee.oa.subsys.informationReport.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.subsys.project.bean.TeeTask;

/**
 * 任务模板项
 * @author xsy
 *
 */
@Entity
@Table(name = "rep_task_template_item")
public class TeeTaskTemplateItem {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="rep_task_template_item_seq_gen")
	@SequenceGenerator(name="rep_task_template_item_seq_gen", sequenceName="rep_task_template_item_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	
	@Column(name = "FIELD_NAME")
	private String fieldName;//字段名称
	
	
	@Column(name = "FIELD_TYPE")
	private int fieldType;//字段类型   1=单行文本    2=多行文本   3=数字文本   4=日期时间   5=下拉菜单

    
	
	@Column(name = "SHOW_TYPE")
	private String  showType;//显示方式         当field_type=4时，该字段值为yyyy-MM-dd类似格式         当field_type=2时，该字段值可为 SIMPLE或RICH

	
	
	@Column(name = "SHOW_AT_LIST")
	private int showAtList;//表头列显示   0=不显示      1=显示
	
	@Column(name = "CAL")
	private String  cal;//是否为计算项   值为SUM，表示合计
	
	
	@ManyToOne
	@JoinColumn(name = "TASK_TEMPLATE_ID")
	private TeeTaskTemplate taskTemplate;//所属任务模板

	public TeeTaskTemplate getTaskTemplate() {
		return taskTemplate;
	}

	public void setTaskTemplate(TeeTaskTemplate taskTemplate) {
		this.taskTemplate = taskTemplate;
	}

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

	

	public int getFieldType() {
		return fieldType;
	}

	public void setFieldType(int fieldType) {
		this.fieldType = fieldType;
	}

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	public int getShowAtList() {
		return showAtList;
	}

	public void setShowAtList(int showAtList) {
		this.showAtList = showAtList;
	}

	public String getCal() {
		return cal;
	}

	public void setCal(String cal) {
		this.cal = cal;
	}
	
	
	
	
}
