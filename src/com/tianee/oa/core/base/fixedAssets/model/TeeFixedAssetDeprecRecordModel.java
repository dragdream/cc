package com.tianee.oa.core.base.fixedAssets.model;

import java.util.Calendar;

public class TeeFixedAssetDeprecRecordModel {
	private int sid;
	
	private Calendar startTime;//折旧开始时间
	
	private double original;//资产原值
	
	private Calendar deprecTime;//折旧年月
	
	private double deprecValue;//本月折旧值
	
	private double deprecRemainValue;//本月残值
	
	private Calendar crTime;//操作日期
	
	private int flag;//操作类型  1:自动折旧   2：手动折旧
	private String userName;
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public Calendar getStartTime() {
		return startTime;
	}
	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}
	public double getOriginal() {
		return original;
	}
	public void setOriginal(double original) {
		this.original = original;
	}
	public Calendar getDeprecTime() {
		return deprecTime;
	}
	public void setDeprecTime(Calendar deprecTime) {
		this.deprecTime = deprecTime;
	}
	public double getDeprecValue() {
		return deprecValue;
	}
	public void setDeprecValue(double deprecValue) {
		this.deprecValue = deprecValue;
	}
	public double getDeprecRemainValue() {
		return deprecRemainValue;
	}
	public void setDeprecRemainValue(double deprecRemainValue) {
		this.deprecRemainValue = deprecRemainValue;
	}
	public Calendar getCrTime() {
		return crTime;
	}
	public void setCrTime(Calendar crTime) {
		this.crTime = crTime;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
