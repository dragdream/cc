package com.tianee.oa.subsys.bisengin.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="Business_Cat")
public class BusinessCat {
    /**
     * 主键id
     */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="Business_Cat_seq_gen")
	@SequenceGenerator(name="Business_Cat_seq_gen", sequenceName="Business_Cat_seq")
	private int sid;
	
	/**
	 * 业务名称
	 */
	@Column(name="Cat_Name")
	private String catName;
	
	/**
	 * 排序号
	 */
	@Column(name="sort_no")
	private String sortNo;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getSortNo() {
		return sortNo;
	}

	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}
}
