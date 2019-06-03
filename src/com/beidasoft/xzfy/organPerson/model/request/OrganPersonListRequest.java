package com.beidasoft.xzfy.organPerson.model.request;

import com.beidasoft.xzfy.base.model.request.Request;

public class OrganPersonListRequest implements Request{

	//机关ID
	private String orgId;
	//姓名
	private String personName;
	//职级
	private String staffing;
	//是否显示全部
	private String isShowAll;
	
	//当前页
	private int page;
	//每页记录数
	private int rows;
	
	//校验
	@Override
	public void validate() {
		
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getStaffing() {
		return staffing;
	}

	public void setStaffing(String staffing) {
		this.staffing = staffing;
	}

	public String getIsShowAll() {
		return isShowAll;
	}

	public void setIsShowAll(String isShowAll) {
		this.isShowAll = isShowAll;
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

}
