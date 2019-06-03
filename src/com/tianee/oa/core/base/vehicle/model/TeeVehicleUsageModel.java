package com.tianee.oa.core.base.vehicle.model;

public class TeeVehicleUsageModel {
	private int sid;//自增id
	private int vehicleId;//维护车辆
	private String  vehicleName = "";
	private int vuProposerId;//申请人Id
	private String vuProposerName = "";//申请人
	private int vuUserId;//	用车人		
	private String vuUserName = "";//	用车人		
	private String VuReqestDateStr = "";//使用日期
	private int status;//STATUS	VARCHAR(20)0-	申请待批1-	申请已批准          2-	使用中      3-	审批未通过           4-	结束
	private String vuReason = "";// 事由		
	private String vuStartStr = "";//开始时间
	private String vuEndStr = "";//结束时间
	private double vuMileage;//
	private String vuRemark = "";// 事由	备注			
	private String vuDestination = "";//varchar (200)	目的地		
	private int vuOperatorId;//	TeePerson	调度员		
	private String vuOperatorName = "";//
	private String vuDriver = "";//VU_DRIVER	varchar(20)	司机		
	private String smsRemind;//SMS_REMIND	varchar(20)	内部提醒申请人			0否、1是
	private String sms2Remind;//SMS2_REMIND	varchar(20)	手机短信申请人			0否、1是
	private int deptManagerId;//	DEPT_MANAGER	TeePerson	部门审批人			部门审批人ID
	private String deptManagerName = "";//
	private int dmerStatus ;//DMER_STATUS	int	部门审批状态			0-部门申请待批 1-部门审批通过  3-未批准
	private String deptReason = "";//DEPT_REASON	CLOB	部门审批人意见			申请没通过时审批人的意见
	private String operatorReason = "";//OPERATOR_REASON	CLOB	调度员意见			申请没通过时调度员的意见
	private String createTimeStr  = "";//申请时间

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

	public int getVuProposerId() {
		return vuProposerId;
	}

	public void setVuProposerId(int vuProposerId) {
		this.vuProposerId = vuProposerId;
	}

	public String getVuProposerName() {
		return vuProposerName;
	}

	public void setVuProposerName(String vuProposerName) {
		this.vuProposerName = vuProposerName;
	}

	public int getVuUserId() {
		return vuUserId;
	}

	public void setVuUserId(int vuUserId) {
		this.vuUserId = vuUserId;
	}

	public String getVuUserName() {
		return vuUserName;
	}

	public void setVuUserName(String vuUserName) {
		this.vuUserName = vuUserName;
	}

	public String getVuReqestDateStr() {
		return VuReqestDateStr;
	}

	public void setVuReqestDateStr(String vuReqestDateStr) {
		VuReqestDateStr = vuReqestDateStr;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getVuReason() {
		return vuReason;
	}

	public void setVuReason(String vuReason) {
		this.vuReason = vuReason;
	}

	public String getVuStartStr() {
		return vuStartStr;
	}

	public void setVuStartStr(String vuStartStr) {
		this.vuStartStr = vuStartStr;
	}

	public String getVuEndStr() {
		return vuEndStr;
	}

	public void setVuEndStr(String vuEndStr) {
		this.vuEndStr = vuEndStr;
	}

	public double getVuMileage() {
		return vuMileage;
	}

	public void setVuMileage(double vuMileage) {
		this.vuMileage = vuMileage;
	}

	public String getVuRemark() {
		return vuRemark;
	}

	public void setVuRemark(String vuRemark) {
		this.vuRemark = vuRemark;
	}

	public String getVuDestination() {
		return vuDestination;
	}

	public void setVuDestination(String vuDestination) {
		this.vuDestination = vuDestination;
	}

	public int getVuOperatorId() {
		return vuOperatorId;
	}

	public void setVuOperatorId(int vuOperatorId) {
		this.vuOperatorId = vuOperatorId;
	}

	public String getVuOperatorName() {
		return vuOperatorName;
	}

	public void setVuOperatorName(String vuOperatorName) {
		this.vuOperatorName = vuOperatorName;
	}

	public String getVuDriver() {
		return vuDriver;
	}

	public void setVuDriver(String vuDriver) {
		this.vuDriver = vuDriver;
	}

	public String getSmsRemind() {
		return smsRemind;
	}

	public void setSmsRemind(String smsRemind) {
		this.smsRemind = smsRemind;
	}

	public String getSms2Remind() {
		return sms2Remind;
	}

	public void setSms2Remind(String sms2Remind) {
		this.sms2Remind = sms2Remind;
	}

	public int getDeptManagerId() {
		return deptManagerId;
	}

	public void setDeptManagerId(int deptManagerId) {
		this.deptManagerId = deptManagerId;
	}

	public String getDeptManagerName() {
		return deptManagerName;
	}

	public void setDeptManagerName(String deptManagerName) {
		this.deptManagerName = deptManagerName;
	}

	public int getDmerStatus() {
		return dmerStatus;
	}

	public void setDmerStatus(int dmerStatus) {
		this.dmerStatus = dmerStatus;
	}

	public String getDeptReason() {
		return deptReason;
	}

	public void setDeptReason(String deptReason) {
		this.deptReason = deptReason;
	}

	public String getOperatorReason() {
		return operatorReason;
	}

	public void setOperatorReason(String operatorReason) {
		this.operatorReason = operatorReason;
	}

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }
	
	
	
}
