package com.beidasoft.xzzf.inspection.inspectors.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 检查人员信息实体类
 */
@Entity
@Table(name="ZF_INSPECTION_INSPECTORS")
public class Inspectors {
    // 检查人员ID
    @Id
    @Column(name = "STAFF_ID")
    private int staffId;

    // 检查人员姓名
    @Column(name = "STAFF_NAME")
    private String staffName;

    // 所属部门ID
    @Column(name = "DEPARTMENT_ID")
    private int departmentId;

    // 所属部门名称
    @Column(name = "DEPARTMENT_NAME")
    private String departmentName;

    // 所属部门检查类型
    @Column(name = "DEPARTMENT_INSPECT_TYPE")
    private String departmentInspectType;
    
    // 删除标记
    @Column(name = "DEL_FLG")
    private String delFlg;

    // 更新时间
    @Column(name = "UPDATE_TIME")
    private Date updateTime;
    
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
