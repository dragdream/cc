package com.tianee.oa.subsys.report.model;

import com.tianee.oa.subsys.report.bean.TeeReportCondition;

public class TeeConditionItemModel {
	private int sid;
	private int itemId;
	private String oper;
	private String val;
	private int conditionId;
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	public int getConditionId() {
		return conditionId;
	}
	public void setConditionId(int conditionId) {
		this.conditionId = conditionId;
	}
	
	
}
