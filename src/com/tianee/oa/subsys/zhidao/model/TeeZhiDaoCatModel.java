package com.tianee.oa.subsys.zhidao.model;

public class TeeZhiDaoCatModel {
	private int sid;//自增id
	private String catName ;//分类名称
	private String managerIds ;//分类管理员id
	private String managerNames;//分类管理员names
	//private TeeZhiDaoCat parentCat;//父级分类
	private String parentCatName;//父级分类名称
	private int parentCatId;//父级分类主键 
	public int getSid() {
		return sid;
	}
	public String getCatName() {
		return catName;
	}
	public String getManagerIds() {
		return managerIds;
	}
	public String getManagerNames() {
		return managerNames;
	}
	public String getParentCatName() {
		return parentCatName;
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
	public void setManagerNames(String managerNames) {
		this.managerNames = managerNames;
	}
	public void setParentCatName(String parentCatName) {
		this.parentCatName = parentCatName;
	}
	public int getParentCatId() {
		return parentCatId;
	}
	public void setParentCatId(int parentCatId) {
		this.parentCatId = parentCatId;
	}
	
	
	
	
}
