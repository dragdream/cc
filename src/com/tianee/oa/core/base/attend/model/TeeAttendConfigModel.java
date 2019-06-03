package com.tianee.oa.core.base.attend.model;

import javax.persistence.Column;

public class TeeAttendConfigModel{
	
	private int sid;
	
	private String dutyName;
	
	private String general;
	
	private String dutyTimeDesc1;
	
	private String dutyTimeDesc2;
	
	private String dutyTimeDesc3;
	
	private String dutyTimeDesc4;
	
	private String dutyTimeDesc5;
	
	private String dutyTimeDesc6;
	
	private char dutyType1;
	
	private char dutyType2;
	
	private char dutyType3;
	
	private char dutyType4;
	
	private char dutyType5;
	
	private char dutyType6;
	
	private String centerPosition;//移動端签到中心点坐标  格式：xxx,yyy
	
	private String centerPositionDesc;//移動端签到中心点坐标 
	
	private double radius;//移动端签到区域半径大小
	
	
	
	@Column(name="addr_json")
	private String addrJson;//签到区域json字符串
	
	
	
	
	
	public String getAddrJson() {
		return addrJson;
	}

	public void setAddrJson(String addrJson) {
		this.addrJson = addrJson;
	}

	public String getCenterPosition() {
		return centerPosition;
	}

	public void setCenterPosition(String centerPosition) {
		this.centerPosition = centerPosition;
	}

	public String getCenterPositionDesc() {
		return centerPositionDesc;
	}

	public void setCenterPositionDesc(String centerPositionDesc) {
		this.centerPositionDesc = centerPositionDesc;
	}

	

	

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getDutyName() {
		return dutyName;
	}

	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}

	public String getGeneral() {
		return general;
	}

	public void setGeneral(String general) {
		this.general = general;
	}

	public String getDutyTimeDesc1() {
		return dutyTimeDesc1;
	}

	public void setDutyTimeDesc1(String dutyTimeDesc1) {
		this.dutyTimeDesc1 = dutyTimeDesc1;
	}

	public String getDutyTimeDesc2() {
		return dutyTimeDesc2;
	}

	public void setDutyTimeDesc2(String dutyTimeDesc2) {
		this.dutyTimeDesc2 = dutyTimeDesc2;
	}

	public String getDutyTimeDesc3() {
		return dutyTimeDesc3;
	}

	public void setDutyTimeDesc3(String dutyTimeDesc3) {
		this.dutyTimeDesc3 = dutyTimeDesc3;
	}

	public String getDutyTimeDesc4() {
		return dutyTimeDesc4;
	}

	public void setDutyTimeDesc4(String dutyTimeDesc4) {
		this.dutyTimeDesc4 = dutyTimeDesc4;
	}

	public String getDutyTimeDesc5() {
		return dutyTimeDesc5;
	}

	public void setDutyTimeDesc5(String dutyTimeDesc5) {
		this.dutyTimeDesc5 = dutyTimeDesc5;
	}

	public String getDutyTimeDesc6() {
		return dutyTimeDesc6;
	}

	public void setDutyTimeDesc6(String dutyTimeDesc6) {
		this.dutyTimeDesc6 = dutyTimeDesc6;
	}

	public char getDutyType1() {
		return dutyType1;
	}

	public void setDutyType1(char dutyType1) {
		this.dutyType1 = dutyType1;
	}

	public char getDutyType2() {
		return dutyType2;
	}

	public void setDutyType2(char dutyType2) {
		this.dutyType2 = dutyType2;
	}

	public char getDutyType3() {
		return dutyType3;
	}

	public void setDutyType3(char dutyType3) {
		this.dutyType3 = dutyType3;
	}

	public char getDutyType4() {
		return dutyType4;
	}

	public void setDutyType4(char dutyType4) {
		this.dutyType4 = dutyType4;
	}

	public char getDutyType5() {
		return dutyType5;
	}

	public void setDutyType5(char dutyType5) {
		this.dutyType5 = dutyType5;
	}

	public char getDutyType6() {
		return dutyType6;
	}

	public void setDutyType6(char dutyType6) {
		this.dutyType6 = dutyType6;
	}

	
	
}