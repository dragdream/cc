package com.beidasoft.zfjd.system.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 业务部门管理员帐号记录表实体类
 */
@Entity
@Table(name="TBL_SYS_MANAGER_USER")
public class SysManagerUser {
    // 数据唯一标识
    @Id
    @Column(name = "ID")
    private String id;

    // 机构类型（1执法部门 2执法主体  3监督部门）
    @Column(name = "ORG_TYPE")
    private int orgType;

    // 执法部门id
    @Column(name = "DEPT_ID")
    private String deptId;

    // 执法主体id
    @Column(name = "SUBJECT_ID")
    private String subjectId;

    // 监督部门id
    @Column(name = "SUP_DEPT_ID")
    private String supDeptId;

    // 是否人民政府机构
    @Column(name = "IS_GOVERNMENT")
    private int isGovernment;

    // 行政区划代码
    @Column(name = "ADMIN_DIVISION_CODE")
    private String adminDivisionCode;

    // 用户类型（1分级管理员  2执法部门管理员 3监督部门管理员）
    @Column(name = "ACCOUNT_TYPE")
    private int accountType;

    // 用户id
    @Column(name = "USER_UUID")
    private int userUuid;

    // 用户账号
    @Column(name = "USER_ACCOUNT")
    private String userAccount;

    // 是否删除标识（0 未删除  1已删除）
    @Column(name = "IS_DELETE")
    private int isDelete;

    // 删除日期
    @Column(name = "DELETE_DATE")
    private Date deleteDate;

    // 创建日期
    @Column(name = "CREATE_DATE")
    private Date createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOrgType() {
        return orgType;
    }

    public void setOrgType(int orgType) {
        this.orgType = orgType;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSupDeptId() {
        return supDeptId;
    }

    public void setSupDeptId(String supDeptId) {
        this.supDeptId = supDeptId;
    }

    public int getIsGovernment() {
        return isGovernment;
    }

    public void setIsGovernment(int isGovernment) {
        this.isGovernment = isGovernment;
    }

    public String getAdminDivisionCode() {
        return adminDivisionCode;
    }

    public void setAdminDivisionCode(String adminDivisionCode) {
        this.adminDivisionCode = adminDivisionCode;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public int getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(int userUuid) {
        this.userUuid = userUuid;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
