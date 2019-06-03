package com.tianee.oa.core.base.vehicle.model;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;

public class TeeVehicleModel {
	private int sid;//自增id

	private String buyDateStr;//购买日期

	private int status;//STATUS	VARCHAR(20)	0:可用（只有可用状态才可以申请使用）1：损坏2：维修中3：报废
	
	private String vModel;// V_MODEL	VARCHAR(200)	厂牌型号			
	
	private String vNum;//V_NUM	VARCHAR(200)	车牌号		

	private String vDriver;//V_DRIVER	varchar(500)	司机	

	private String vType;//V_TYPE	varchar(20)	车辆类型			系统代码的标识ID“CEHICLE_TYPE”

	private String vRemark;//V_REMARK	CLOB	备注			

	private String vEngineNum;//V_ENG	发动机号			
	
	private double vPrice;//V_PRICE	varchar(20)	车辆价格	
	
	private TeeAttachmentModel attacheModel ;//ATTACHMENT_ID	CLOB	车辆图片ID			TeeAttement
	
	private String postDeptIds ;
	private String postDeptNames;//申请权限部门
	
	private String postUserIds;
	private String postUserNames;//申请权限人员

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}


	public String getBuyDateStr() {
		return buyDateStr;
	}

	public void setBuyDateStr(String buyDateStr) {
		this.buyDateStr = buyDateStr;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getvModel() {
		return vModel;
	}

	public void setvModel(String vModel) {
		this.vModel = vModel;
	}

	public String getvNum() {
		return vNum;
	}

	public void setvNum(String vNum) {
		this.vNum = vNum;
	}

	public String getvDriver() {
		return vDriver;
	}

	public void setvDriver(String vDriver) {
		this.vDriver = vDriver;
	}

	public String getvType() {
		return vType;
	}

	public void setvType(String vType) {
		this.vType = vType;
	}

	public String getvRemark() {
		return vRemark;
	}

	public void setvRemark(String vRemark) {
		this.vRemark = vRemark;
	}

	public String getvEngineNum() {
		return vEngineNum;
	}

	public void setvEngineNum(String vEngineNum) {
		this.vEngineNum = vEngineNum;
	}

	public double getvPrice() {
		return vPrice;
	}

	public void setvPrice(double vPrice) {
		this.vPrice = vPrice;
	}

	public TeeAttachmentModel getAttacheModel() {
		return attacheModel;
	}

	public void setAttacheModel(TeeAttachmentModel attacheModel) {
		this.attacheModel = attacheModel;
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
	
	
}
