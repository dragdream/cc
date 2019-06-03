package com.beidasoft.xzfy.organ.model.request;

import com.beidasoft.xzfy.base.model.request.Request;

public class OrganListRequest implements Request{

	//treeID
	private String treeID;
	//机关层级
	private String organLevel;
	//机关名称
	private String organName;
	//联系人
	private String contacts;

	
	//当前页
	private int page;
	//每页记录数
	private int rows;
	
	//校验
	@Override
	public void validate() {
		
		//校验组织机构名称是否为空
		//ValidateUtils.validateEmpty(this.organName);
		//校验组织机构名称是否包含特殊字符
		//ValidateUtils.validateSpecialChar(this.organName);
	}
	
	
	public String getOrganLevel() {
		return organLevel;
	}
	public void setOrganLevel(String organLevel) {
		this.organLevel = organLevel;
	}
	public String getOrganName() {
		return organName;
	}
	public void setOrganName(String organName) {
		this.organName = organName;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public String getTreeID() {
		return treeID;
	}
	public void setTreeID(String treeID) {
		this.treeID = treeID;
	}
}
