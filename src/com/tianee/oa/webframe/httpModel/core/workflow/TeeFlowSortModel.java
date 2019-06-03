package com.tianee.oa.webframe.httpModel.core.workflow;

import com.tianee.oa.webframe.httpModel.TeeBaseModel;

public class TeeFlowSortModel extends TeeBaseModel{
	private int sid;
	private int orderNo;
	private String sortName;
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getSortName() {
		return sortName;
	}
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public int getOrderNo() {
		return orderNo;
	}
	
	
}
