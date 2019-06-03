package com.tianee.oa.core.base.meeting.model;

public class TeeMeetingRoomModel {
	private int sid;//自增id
	private String mrName;//MR_NAME	VARCHAR(200)	会议室名称
	private String mrCapacity;//MR_CAPACITY	VARCHAR(200)	会议容量人数
	private String mrDevice;//MR_DEVICE	CLOB	设备情况
	private String mrdesc;//MR_DESC	CLOB	会议室描述
	private String mrPlace;//MR_PLACE	VARCHAR(200)	地址
	private String managerIds;//	OPERATOR	CLOB	会议管理员
	private String managerNames;//
	private String postDeptIds;//
	private String postDeptNames;//
	private String postUserIds;//
	private String postUserNames;//
	
	private String equipmentIds;
	
	private String equipmentNames;
	
	private int type;//会议室类型  1=会议室  2=询问室
	
	private String typeDesc;//类型描述
	
	private int sbStatus;//设备是否异常 0：正常 1：异常
	
	private String sbStatusName;
	
	public String getTypeDesc() {
		return typeDesc;
	}
	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getMrName() {
		return mrName;
	}
	public void setMrName(String mrName) {
		this.mrName = mrName;
	}
	public String getMrCapacity() {
		return mrCapacity;
	}
	public void setMrCapacity(String mrCapacity) {
		this.mrCapacity = mrCapacity;
	}
	public String getMrDevice() {
		return mrDevice;
	}
	public void setMrDevice(String mrDevice) {
		this.mrDevice = mrDevice;
	}
	public String getMrdesc() {
		return mrdesc;
	}
	public void setMrdesc(String mrdesc) {
		this.mrdesc = mrdesc;
	}
	public String getMrPlace() {
		return mrPlace;
	}
	public void setMrPlace(String mrPlace) {
		this.mrPlace = mrPlace;
	}
	public String getManagerIds() {
		return managerIds;
	}
	public void setManagerIds(String managerIds) {
		this.managerIds = managerIds;
	}
	public String getManagerNames() {
		return managerNames;
	}
	public void setManagerNames(String managerNames) {
		this.managerNames = managerNames;
	}
	public String getPostDeptIds() {
		return postDeptIds;
	}
	public void setPostDeptIds(String postDeptIds) {
		this.postDeptIds = postDeptIds;
	}
	public String getPostDeptNames() {
		return postDeptNames;
	}
	public void setPostDeptNames(String postDeptNames) {
		this.postDeptNames = postDeptNames;
	}
	public String getPostUserIds() {
		return postUserIds;
	}
	public void setPostUserIds(String postUserIds) {
		this.postUserIds = postUserIds;
	}
	public String getPostUserNames() {
		return postUserNames;
	}
	public void setPostUserNames(String postUserNames) {
		this.postUserNames = postUserNames;
	}
	public String getEquipmentIds() {
		return equipmentIds;
	}
	public void setEquipmentIds(String equipmentIds) {
		this.equipmentIds = equipmentIds;
	}
	public String getEquipmentNames() {
		return equipmentNames;
	}
	public void setEquipmentNames(String equipmentNames) {
		this.equipmentNames = equipmentNames;
	}
	public int getSbStatus() {
		return sbStatus;
	}
	public void setSbStatus(int sbStatus) {
		this.sbStatus = sbStatus;
	}
	public String getSbStatusName() {
		return sbStatusName;
	}
	public void setSbStatusName(String sbStatusName) {
		this.sbStatusName = sbStatusName;
	}
	
	
}
