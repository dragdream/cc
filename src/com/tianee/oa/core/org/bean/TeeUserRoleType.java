package com.tianee.oa.core.org.bean;
import org.hibernate.annotations.Index;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="USER_ROLE_TYPE")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class TeeUserRoleType implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="USER_ROLE_TYPE_seq_gen")
	@SequenceGenerator(name="USER_ROLE_TYPE_seq_gen", sequenceName="USER_ROLE_TYPE_seq")
	@Column(name="SID")
	private int sid;
	
	@Column(name="TYPE_NAME",length=100)
	private String typeName;//角色分类名称
	
	@Column(name="TYPE_SORT",columnDefinition=("int default 0"))
	private int typeSort;// 排序


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

	public int getTypeSort() {
		return typeSort;
	}

	public void setTypeSort(int typeSort) {
		this.typeSort = typeSort;
	}
	
	

}
