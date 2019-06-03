package com.tianee.oa.subsys.footprint.model;


public class TeeFootPrintRangeModel {

	private int sid;//自增id
	
	private String rangeName;//围栏名称
	
	private String rangeStr;//围栏范围
	
	//private List<TeePerson> users;//管理范围
	private String userIds;
	private String userNames;
	public int getSid() {
		return sid;
	}
	public String getRangeName() {
		return rangeName;
	}
	public String getRangeStr() {
		return rangeStr;
	}
	public String getUserIds() {
		return userIds;
	}
	public String getUserNames() {
		return userNames;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public void setRangeName(String rangeName) {
		this.rangeName = rangeName;
	}
	public void setRangeStr(String rangeStr) {
		this.rangeStr = rangeStr;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}
	
	
	
	
	
	
	
}
