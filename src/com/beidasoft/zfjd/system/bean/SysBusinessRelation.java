package com.beidasoft.zfjd.system.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeeDepartment;

@Entity
@Table(name = "TBL_SYS_BUSSINESS_RELATION")
public class SysBusinessRelation {
    // 主键
    @Id
    @Column(name = "ID")
    private String id;
    
    // 系统部门uuid
    @ManyToOne
    @JoinColumn(name = "SYS_ORG_ID")
    private TeeDepartment deptRelation;
    
    // 系统部门名称
    @Column(name = "SYS_ORG_NAME")
    private String sysOrgName;
    
    // 业务执法部门ID
    @Column(name = "BUSINESS_DEPT_ID")
    private String businessDeptId;
    
    // 业务执法部门名称
    @Column(name = "BUSINESS_DEPT_NAME")
    private String businessDeptName;
    
    // 业务监督部门ID
    @Column(name = "BUSINESS_SUP_DEPT_ID")
    private String businessSupDeptId;
    
    // 业务监督部门名称
    @Column(name = "BUSINESS_SUP_DEPT_NAME")
    private String businessSupDeptName;
    
    // 业务执法主体ID
    @Column(name = "BUSINESS_SUBJECT_ID")
    private String businessSubjectId;
    
    // 业务主体名称
    @Column(name = "BUSINESS_SUBJECT_NAME")
    private String businessSubjectName;
    
    // 业务部门类型
    @Column(name = "ORG_TYPE")
    private String orgType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TeeDepartment getDeptRelation() {
        return deptRelation;
    }

    public void setDeptRelation(TeeDepartment deptRelation) {
        this.deptRelation = deptRelation;
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
