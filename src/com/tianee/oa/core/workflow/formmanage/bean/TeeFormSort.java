package com.tianee.oa.core.workflow.formmanage.bean;
import org.hibernate.annotations.Index;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="FORM_SORT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TeeFormSort implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="FORM_SORT_seq_gen")
	@SequenceGenerator(name="FORM_SORT_seq_gen", sequenceName="FORM_SORT_seq")
	@Column(name="SID")
	private int sid;
	
	@Column(name="SORT_NAME")
	private String sortName;
	
	@Column(name="SORT_NO")
	private int orderNo;
	
	@OneToMany(mappedBy="formSort",fetch=FetchType.LAZY)
	private List<TeeForm> formList;

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public List<TeeForm> getFormList() {
		return formList;
	}

	public void setFormList(List<TeeForm> formList) {
		this.formList = formList;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getSid() {
		return sid;
	}
	
}
