package com.tianee.oa.core.base.exam.model;


/**
 * 题库
 *
 */
public class TeeExamStoreModel {
	private int sid;
	
	private String storeCode;//题库编号
	
	private String storeName;//题库名称
	
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
