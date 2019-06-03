package com.beidasoft.zfjd.system.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 系统部门表实体类
 */
@Entity
@Table(name="department")
public class Department {
    // 主键
	@Id
    @Column(name = "uuid")
    private Integer uuid;

    // 
    @Column(name = "address")
    private String address;

    // 主体id
    @Column(name = "dept_full_id")
    private String deptFullId;

    // 部门全称
    @Column(name = "dept_full_name")
    private String deptFullName;

    // 
    @Column(name = "dept_func")
    private String deptFunc;

    // 部门名称
    @Column(name = "dept_name")
    private String deptName;

    // 部门编号
    @Column(name = "dept_no")
    private Integer deptNo;

    // 
    @Column(name = "dept_parent_level")
    private String deptParentLevel;

    // 
    @Column(name = "ding_dept_id")
    private Integer dingDeptId;

    // 
    @Column(name = "fax_no")
    private String faxNo;

    // 
    @Column(name = "guid")
    private String guid;

    // 
    @Column(name = "leader1")
    private String leader1;

    // 
    @Column(name = "leader2")
    private String leader2;

    // 
    @Column(name = "manager")
    private String manager;

    // 
    @Column(name = "manager2")
    private String manager2;

    public int getUuid() {
        return uuid;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDeptFullId() {
        return deptFullId;
    }

    public void setDeptFullId(String deptFullId) {
        this.deptFullId = deptFullId;
    }

    public String getDeptFullName() {
        return deptFullName;
    }

    public void setDeptFullName(String deptFullName) {
        this.deptFullName = deptFullName;
    }

    public String getDeptFunc() {
        return deptFunc;
    }

    public void setDeptFunc(String deptFunc) {
        this.deptFunc = deptFunc;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(Integer deptNo) {
        this.deptNo = deptNo;
    }

    public String getDeptParentLevel() {
        return deptParentLevel;
    }

    public void setDeptParentLevel(String deptParentLevel) {
        this.deptParentLevel = deptParentLevel;
    }

    public Integer getDingDeptId() {
        return dingDeptId;
    }

    public void setDingDeptId(Integer dingDeptId) {
        this.dingDeptId = dingDeptId;
    }

    public String getFaxNo() {
        return faxNo;
    }

    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getLeader1() {
        return leader1;
    }

    public void setLeader1(String leader1) {
        this.leader1 = leader1;
    }

    public String getLeader2() {
        return leader2;
    }

    public void setLeader2(String leader2) {
        this.leader2 = leader2;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getManager2() {
        return manager2;
    }

    public void setManager2(String manager2) {
        this.manager2 = manager2;
    }

}
