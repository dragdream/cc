package com.tianee.oa.core.base.exam.bean;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 题库
 * @author kakalion
 *
 */
@Entity
@Table(name="EXAM_STORE")
public class TeeExamStore {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="EXAM_STORE_seq_gen")
	@SequenceGenerator(name="EXAM_STORE_seq_gen", sequenceName="EXAM_STORE_seq")
	private int sid;
	
	@Column(name="STORE_CODE")
	private String storeCode;//题库编号
	
	@Column(name="STORE_NAME")
	private String storeName;//题库名称
	
	@Column(name="STORE_DESC")
	@Lob()
	private String storeDesc;//题库描述

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getSid() {
		return sid;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreDesc() {
		return storeDesc;
	}

	public void setStoreDesc(String storeDesc) {
		this.storeDesc = storeDesc;
	}
	
}
