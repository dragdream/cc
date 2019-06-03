package com.tianee.oa.subsys.project.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.tianee.oa.core.org.bean.TeeUserRole;

//项目自定义字段
@Entity
@Table(name = "project_custom_field")
public class TeeProjectCustomField {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="project_custom_field_seq_gen")
	@SequenceGenerator(name="project_custom_field_seq_gen", sequenceName="project_custom_field_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@Column(name="field_name")
	private String fieldName ;//字段名称
	
	@Column(name="order_num")
	private int orderNum ;//排序号
	
	@Column(name="field_type")
	private String fieldType ;//字段类型
	
	@Column(name="field_ctrl_model")
	private String fieldCtrModel ;//字段控制模型  例如：{type:"系统编码",value:"xxxx"}   {type:"自定义选项",value:["选项1，选项2，选项3","1,2,3"]}
	
	@Column(name="is_query")
	private int isQuery ;//是否作为查询字段    0--否  1--是
	
	@Column(name="is_show")
	private int isShow ;//是否显示在列表   0--否  1--是
	
	@ManyToOne
	@JoinColumn(name="project_type_id")
	private TeeProjectType projectType;//所属项目类型
	
	
	@ManyToOne
	@JoinColumn(name="project_task_id")
	private TeeProjectType projectTask;//所属项目类型
	
	public String getFieldCtrModel() {
		return fieldCtrModel;
	}

	public void setFieldCtrModel(String fieldCtrModel) {
		this.fieldCtrModel = fieldCtrModel;
	}


	
	

	public TeeProjectType getProjectType() {
		return projectType;
	}

	public void setProjectType(TeeProjectType projectType) {
		this.projectType = projectType;
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

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public int getIsQuery() {
		return isQuery;
	}

	public void setIsQuery(int isQuery) {
		this.isQuery = isQuery;
	}

	public int getIsShow() {
		return isShow;
	}

	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}

	public TeeProjectType getProjectTask() {
		return projectTask;
	}

	public void setProjectTask(TeeProjectType projectTask) {
		this.projectTask = projectTask;
	}
	
}
