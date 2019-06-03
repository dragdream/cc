package com.tianee.oa.webframe.httpModel.core.workflow;

import java.util.ArrayList;
import java.util.List;

import com.tianee.oa.webframe.httpModel.TeeBaseModel;

public class TeeFormSortModel extends TeeBaseModel{
	private int sid;
	private int orderNo;
	private String sortName;
	
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
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	
}
