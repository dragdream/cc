package com.tianee.oa.subsys.footprint.model;


public class TeeFootPrintModel {
	private int sid;//自增id
	//private TeePerson user;// 当前用户
	private String userName;//用户姓名
	private String userId;//用户登录名
	private int userUuid;//用户主键
	private String  crTimeStr;//创建时间
	private double coordinateX;//X坐标
	private double coordinateY;//Y坐标
	private String addressDescription;//地理位置描述
	
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getUserUuid() {
		return userUuid;
	}
	public void setUserUuid(int userUuid) {
		this.userUuid = userUuid;
	}
	public String getCrTimeStr() {
		return crTimeStr;
	}
	public void setCrTimeStr(String crTimeStr) {
		this.crTimeStr = crTimeStr;
	}
	public double getCoordinateX() {
		return coordinateX;
	}
	public void setCoordinateX(double coordinateX) {
		this.coordinateX = coordinateX;
	}
	public double getCoordinateY() {
		return coordinateY;
	}
	public void setCoordinateY(double coordinateY) {
		this.coordinateY = coordinateY;
	}
	public String getAddressDescription() {
		return addressDescription;
	}
	public void setAddressDescription(String addressDescription) {
		this.addressDescription = addressDescription;
	}
	
}
