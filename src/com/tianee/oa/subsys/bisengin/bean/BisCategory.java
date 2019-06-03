package com.tianee.oa.subsys.bisengin.bean;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="BIS_CAT")
public class BisCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="BIS_CAT_seq_gen")
	@SequenceGenerator(name="BIS_CAT_seq_gen", sequenceName="BIS_CAT_seq")
	private int sid;
	
	/**
	 * 
	 * 分类名称
	 */
	@Column(name="CAT_NAME")
	private String catName;
	
	/**
	 * 分类排序
	 */
	@Column(name="SORT_NO")
	private int sortNo;

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

	public int getSortNo() {
		return sortNo;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}
	
}
