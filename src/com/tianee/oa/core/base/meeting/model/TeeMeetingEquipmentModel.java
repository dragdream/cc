package com.tianee.oa.core.base.meeting.model;


public class TeeMeetingEquipmentModel {
	private int sid;//自增id
	
//	private String equipmentNo ;//MEET_NAME	VARCHAR(200)	设备编号

	private String equipmentName;//SUBJECT	VARCHAR(200)	设备名称

//	private String groupNo;//GROUP_NO	CLOB	同类设备名称


	private String remark;//REMARK	CLOB	设备描述


//	private int statue;//STATUS	VARCHAR(20)	设备状态  0-正常 1-不可用
	
//	private int meetRoomId;
	
//	private String meetRoomName;//

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
