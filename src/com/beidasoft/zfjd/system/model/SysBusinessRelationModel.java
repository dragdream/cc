package com.beidasoft.zfjd.system.model;

/**
 * 监督业务管理表MODEL类
 */
public class SysBusinessRelationModel {
    // 唯一标识
    private String id;

    // 系统管理机构id
    private int sysOrgId;

    // 系统管理机构名称
    private String sysOrgName;

    // 关联执法部门id
    private String businessDeptId;

    // 关联执法部门名称
    private String businessDeptName;

    // 关联监督部门id
    private String businessSupDeptId;

    // 关联监督部门名称
    private String businessSupDeptName;

    // 关联执法主体id
    private String businessSubjectId;

    // 关联执法主体名称
    private String businessSubjectName;

    // 部门类型（10：执法、20：监督）
    private String orgType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSysOrgId() {
        return sysOrgId;
    }

    public void setSysOrgId(int sysOrgId) {
        this.sysOrgId = sysOrgId;
    }

    public String getSysOrgName() {
        return sysOrgName;
    }

    public void setSysOrgName(String sysOrgName) {
        this.sysOrgName = sysOrgName;
    }

    public String getBusinessDeptId() {
        return businessDeptId;
    }

    public void setBusinessDeptId(String businessDeptId) {
        this.businessDeptId = businessDeptId;
    }

    public String getBusinessDeptName() {
        return businessDeptName;
    }

    public void setBusinessDeptName(String businessDeptName) {
        this.businessDeptName = businessDeptName;
    }

    public String getBusinessSupDeptId() {
        return businessSupDeptId;
    }

    public void setBusinessSupDeptId(String businessSupDeptId) {
        this.businessSupDeptId = businessSupDeptId;
    }

    public String getBusinessSupDeptName() {
        return businessSupDeptName;
    }

    public void setBusinessSupDeptName(String businessSupDeptName) {
        this.businessSupDeptName = businessSupDeptName;
    }

    public String getBusinessSubjectId() {
        return businessSubjectId;
    }

    public void setBusinessSubjectId(String businessSubjectId) {
        this.businessSubjectId = businessSubjectId;
    }

    public String getBusinessSubjectName() {
        return businessSubjectName;
    }

    public void setBusinessSubjectName(String businessSubjectName) {
        this.businessSubjectName = businessSubjectName;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

}
