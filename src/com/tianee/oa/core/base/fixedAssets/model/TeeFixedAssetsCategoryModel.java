package com.tianee.oa.core.base.fixedAssets.model;


/**
 * 资产类别
 *
 */
public class TeeFixedAssetsCategoryModel {
	private int sid;
	
	/**
	 * 资产类别名称
	 */
	private String name;
	
	/**
	 * 排序号
	 */
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
