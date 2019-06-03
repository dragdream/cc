package com.tianee.oa.core.base.vehicle.model;

/**
 * 
 * @author syl
 *
 */
public class TeeVehicleMaintenanceModel {
	private int sid;//自增id

	private int vehicleId;//维护车辆Id
	private String vehicleName;//车辆名称/车牌号
	
	private int vmPersonId;//经办人
	private String vmPersonName;//经办人

	private String vmRequestDateStr;//维护日期

	private int vmType;//本状态与车辆使用申请无关	0:维  1：加油 2:洗车  3：年检 4：其他
	
	private String vmReason;//CLOB	维护原因	
	
	private String vmRemark;//CLOB	备注	

	private Double vmFee;//维护费用	
	private String createTimeStr;
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}
	public String getVehicleName() {
		return vehicleName;
	}
	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}
	public int getVmPersonId() {
		return vmPersonId;
	}
	public void setVmPersonId(int vmPersonId) {
		this.vmPersonId = vmPersonId;
	}
	public String getVmPersonName() {
		return vmPersonName;
	}
	public void setVmPersonName(String vmPersonName) {
		this.vmPersonName = vmPersonName;
	}
	public String getVmRequestDateStr() {
		return vmRequestDateStr;
	}
	public void setVmRequestDateStr(String vmRequestDateStr) {
		this.vmRequestDateStr = vmRequestDateStr;
	}
	public int getVmType() {
		return vmType;
	}
	public void setVmType(int vmType) {
		this.vmType = vmType;
	}
	public String getVmReason() {
		return vmReason;
	}
	public void setVmReason(String vmReason) {
		this.vmReason = vmReason;
	}
	public String getVmRemark() {
		return vmRemark;
	}
	public void setVmRemark(String vmRemark) {
		this.vmRemark = vmRemark;
	}
	public Double getVmFee() {
		return vmFee;
	}
	public void setVmFee(Double vmFee) {
		this.vmFee = vmFee;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}



}

