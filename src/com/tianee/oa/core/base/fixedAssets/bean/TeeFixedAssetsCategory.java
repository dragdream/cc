package com.tianee.oa.core.base.fixedAssets.bean;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 资产类别
 * @author kakalion
 *
 */
@Entity
@Table(name="FIXED_ASSETS_CAT")
public class TeeFixedAssetsCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="FIXED_ASSETS_CAT_seq_gen")
	@SequenceGenerator(name="FIXED_ASSETS_CAT_seq_gen", sequenceName="FIXED_ASSETS_CAT_seq")
	private int sid;
	
	/**
	 * 资产类别名称
	 */
	@Column(name="CAT_NAME")
	private String name;
	
	/**
	 * 排序号
	 */
	@Column(name="SORT_NO")
	private int sortNo;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSortNo() {
		return sortNo;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}
	
	
}
