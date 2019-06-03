package com.tianee.oa.subsys.crm.core.product.model;


public class TeeCrmProductsTypeModel {
	private int sid;// 自增id
	private String typeName;//分类名称
	private int typeOrder ;//排序	
	private int typeFlag ;//是否删除	
	private int parentId;//上级分类Id
	private String parentName;
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
	public int getTypeOrder() {
		return typeOrder;
	}
	public void setTypeOrder(int typeOrder) {
		this.typeOrder = typeOrder;
	}
	public int getTypeFlag() {
		return typeFlag;
	}
	public void setTypeFlag(int typeFlag) {
		this.typeFlag = typeFlag;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
	
}
