package com.tianee.oa.subsys.zhidao.bean;

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

/**
 * 知道分类
 * @author xsy
 *
 */
@Entity
@Table(name = "ZD_CAT")
public class TeeZhiDaoCat {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="zd_cat_seq_gen")
	@SequenceGenerator(name="zd_cat_seq_gen", sequenceName="zd_cat_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@Column(name="CAT_NAME")
	private String catName ;//分类名称
	
	@Column(name="MANAGER_IDS")
	private String managerIds ;//分类管理员
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PARENT_ID")
	private TeeZhiDaoCat parentCat;//父级分类

	public int getSid() {
		return sid;
	}

	public String getCatName() {
		return catName;
	}

	public String getManagerIds() {
		return managerIds;
	}

	public TeeZhiDaoCat getParentCat() {
		return parentCat;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public void setManagerIds(String managerIds) {
		this.managerIds = managerIds;
	}

	public void setParentCat(TeeZhiDaoCat parentCat) {
		this.parentCat = parentCat;
	}
}
