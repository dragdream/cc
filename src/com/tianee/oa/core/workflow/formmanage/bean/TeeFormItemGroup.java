package com.tianee.oa.core.workflow.formmanage.bean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;


@Entity
@Table(name="FORM_ITEM_GROUP")
public class TeeFormItemGroup implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="FORM_ITEM_GROUP_seq_gen")
	@SequenceGenerator(name="FORM_ITEM_GROUP_seq_gen", sequenceName="FORM_ITEM_GROUP_seq")
	@Column(name="SID")
	private int sid;
	
	@Column(name="GROUP_NAME")
	private String groupName;//组名称
	
	
	@ManyToOne()
	@JoinColumn(name="FORM_ID")
	private TeeForm form;//表单 
	
	@Column(name="ITEM_IDS")
	private String itemIds;//
	
	@Column(name="ORDER_")
	private int order;//排序号

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public TeeForm getForm() {
		return form;
	}

	public void setForm(TeeForm form) {
		this.form = form;
	}

	public String getItemIds() {
		return itemIds;
	}

	public void setItemIds(String itemIds) {
		this.itemIds = itemIds;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	
	
}
