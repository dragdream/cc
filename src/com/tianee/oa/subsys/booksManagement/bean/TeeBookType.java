package com.tianee.oa.subsys.booksManagement.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 图书类别管理
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "BOOK_TYPE")
public class TeeBookType implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "BOOK_TYPE_seq_gen")
	@SequenceGenerator(name = "BOOK_TYPE_seq_gen", sequenceName = "BOOK_TYPE_seq")
	private int sid;// 自增id

	@Column(name = "TYPE_NAME", length = 300)
	private String typeName;// 类型名称

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
}
