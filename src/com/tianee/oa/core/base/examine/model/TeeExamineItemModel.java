package com.tianee.oa.core.base.examine.model;

public class TeeExamineItemModel {

	private int sid;//SID	int(11)	自增字段	是	自增
	private String itemName;//ITEM_NAME	varchar(254)	项目名称		
	private double itemMin;//ITEM_MIN	Number	最小值	
	private double itemMax;//ITEM_MAX	Number	最大值
	private String groupId;//指标集Id
	
	private String itemDesc;//ITEM_NAME	varchar(254)	项目描述
	private String groupName;//

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public double getItemMin() {
		return itemMin;
	}

	public void setItemMin(double itemMin) {
		this.itemMin = itemMin;
	}

	public double getItemMax() {
		return itemMax;
	}

	public void setItemMax(double itemMax) {
		this.itemMax = itemMax;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	
	
}
