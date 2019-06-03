package com.tianee.oa.subsys.project.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name = "project_cost_type")
public class TeeProjectCostType {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="project_cost_type_seq_gen")
	@SequenceGenerator(name="project_cost_type_seq_gen", sequenceName="project_cost_type_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	
	@Column(name="type_name")
	private String typeName ;//类型名称
	
	@Column(name="order_num")
	private int orderNum ;//排序号
	
	
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

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	
}
