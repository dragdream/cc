package com.tianee.oa.core.base.commonword.model;


public class CommonWordModel {

	private String sid;
	private String cyy;
	private int cis;//次数
	private String pensonName;
	private int  pensonId;
	
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getCyy() {
		return cyy;
	}
	public void setCyy(String cyy) {
		this.cyy = cyy;
	}
	public String getPensonName() {
		return pensonName;
	}
	public void setPensonName(String pensonName) {
		this.pensonName = pensonName;
	}
	public int getPensonId() {
		return pensonId;
	}
	public void setPensonId(int pensonId) {
		this.pensonId = pensonId;
	}
	public int getCis() {
		return cis;
	}
	public void setCis(int cis) {
		this.cis = cis;
	}
	
}
