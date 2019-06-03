package com.tianee.oa.core.base.attend.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.tianee.oa.core.org.bean.TeePerson;

public class TeeAttendAssignModel {
	
	private int sid;//自增id
	
	//private TeePerson user;//申请人
	private int userId;
	private String userName;
	
	//private Calendar createTime;//上报时间
	private String createTimeStr;
	
	private String remark;//备注
	
	private String address;//上报地点
	
	private String addrPoint;//上报点

	
	private String attachIds;//附件id字符串
	
	
	public String getAttachIds() {
		return attachIds;
	}

	public void setAttachIds(String attachIds) {
		this.attachIds = attachIds;
	}

	public int getSid() {
		return sid;
	}

	public int getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public String getRemark() {
		return remark;
	}

	public String getAddress() {
		return address;
	}

	public String getAddrPoint() {
		return addrPoint;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setAddrPoint(String addrPoint) {
		this.addrPoint = addrPoint;
	}
	
	
	
}
