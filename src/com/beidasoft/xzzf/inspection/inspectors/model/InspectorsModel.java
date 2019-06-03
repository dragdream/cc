package com.beidasoft.xzzf.inspection.inspectors.model;

import java.util.Date;

public class InspectorsModel {
	// 检查人员ID
    private int staffId;

    // 检查人员姓名
    private String staffName;
    
    // 检查人员ID
    //private String staffIdStr;

    // 检查人员姓名
    //private String staffNameStr;

    // 所属部门ID
    private int departmentId;

    // 所属部门名称
    private String departmentName;

    // 所属部门检查类型
    private String departmentInspectType;
    
    // 删除标记
    private String delFlg;

    // 更新时间
    private Date updateTimeStr;
	
	public int getStaffId() {
		return staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentInspectType() {
		return departmentInspectType;
	}

	public void setDepartmentInspectType(String departmentInspectType) {
		this.departmentInspectType = departmentInspectType;
	}

	public String getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}

	public Date getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(Date updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

//	public String getStaffIdStr() {
//		return staffIdStr;
//	}
//
//	public void setStaffIdStr(String staffIdStr) {
//		this.staffIdStr = staffIdStr;
//	}
//
//	public String getStaffNameStr() {
//		return staffNameStr;
//	}
//
//	public void setStaffNameStr(String staffNameStr) {
//		this.staffNameStr = staffNameStr;
//	}
	
	
}
