package com.tianee.oa.core.general.model;

import java.util.Calendar;

import com.tianee.oa.webframe.httpModel.TeeBaseModel;

public class TeeSealModel extends TeeBaseModel {

	private int sid;//自增id
	
	private String sealId;
	
	private int deptId;//所属部门Id
	
	private String deptName;//所属部门Id
	
	private String sealName;//印章名称

	private String sealData;

	private Calendar createTime;
	
	private String createTimeDesc;
	
	private String userStr;
	
	private String userStrDesc;
	
	private int isFlag;

	private String certStr;//印章相关

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getSealId() {
		return sealId;
	}

	public void setSealId(String sealId) {
		this.sealId = sealId;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getSealName() {
		return sealName;
	}

	public void setSealName(String sealName) {
		this.sealName = sealName;
	}

	public String getSealData() {
		return sealData;
	}

	public void setSealData(String sealData) {
		this.sealData = sealData;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public int getIsFlag() {
		return isFlag;
	}

	public void setIsFlag(int isFlag) {
		this.isFlag = isFlag;
	}

	public String getCertStr() {
		return certStr;
	}

	public void setCertStr(String certStr) {
		this.certStr = certStr;
	}

	public String getCreateTimeDesc() {
		return createTimeDesc;
	}

	public void setCreateTimeDesc(String createTimeDesc) {
		this.createTimeDesc = createTimeDesc;
	}

	public String getUserStr() {
		return userStr;
	}

	public void setUserStr(String userStr) {
		this.userStr = userStr;
	}

	public String getUserStrDesc() {
		return userStrDesc;
	}

	public void setUserStrDesc(String userStrDesc) {
		this.userStrDesc = userStrDesc;
	}
	
	
}
