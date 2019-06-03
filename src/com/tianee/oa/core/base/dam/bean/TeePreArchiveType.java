package com.tianee.oa.core.base.dam.bean;

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

import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 预归档分类表 
 * @author xsy
 *
 */
@Entity
@Table(name="pre_archive_type")
public class TeePreArchiveType {
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO, generator="pre_archive_type_seq_gen")
	@SequenceGenerator(name="pre_archive_type_seq_gen", sequenceName="pre_archive_type_seq")
	private int sid;
	
	@Column(name="type_name")
	private String typeName;//分类名称
	
	
	@Column(name="sort_no")
	private int sortNo;//排序号

	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="manager_id")
	private TeePerson manager;//档案管理员
	
	
	
	

	public TeePerson getManager() {
		return manager;
	}


	public void setManager(TeePerson manager) {
		this.manager = manager;
	}


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


	public int getSortNo() {
		return sortNo;
	}


	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}
	
	
	
	
	
}
