package com.beidasoft.zfjd.common.model;

import java.io.Serializable;

/**
 * 监督系统机构权限控制信息MODEL类
 */
public class OrgCtrlInfoModel implements Serializable{

    // @Fields serialVersionUID : TODO
    private static final long serialVersionUID = 1L;

    // 数据标识
    private String id;
    
    //用户登录的系统部门id
    private Integer sysDeptId;
    
    //机构类型  1.监督部门 2.执法部门3.执法主体
    private Integer orgType;
    
    //机构层级code
    private String levelCode;
    
    //行政区划code
    private String adminDivisionCode;
    
    //执法系统code（多个以,间隔）
    private String orgSysCode;
    
    //菜单权限组(多个权限，用英文 “,” 分隔)
    private String menuNames;
    
    //分级管理员（1是，2否）
    private boolean gradeAdministrator;
    
    //执法人员（1是，2否）
    private boolean isLawPerson;
    
    //监督部门ID
    private String supDeptId;
    
    //监督部门名称
    private String supDeptName;
    
    //执法部门ID
    private String departId;
    
    //执法部门名称
    private String departName;
    
    //执法主体ID
    private String subjectId;
    
    //执法主体名称
    private String subjectName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSysDeptId() {
        return sysDeptId;
    }

    public void setSysDeptId(Integer sysDeptId) {
        this.sysDeptId = sysDeptId;
    }

    public Integer getOrgType() {
        return orgType;
    }

    public void setOrgType(Integer orgType) {
        this.orgType = orgType;
    }

    public String getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

    public String getAdminDivisionCode() {
        return adminDivisionCode;
    }

    public void setAdminDivisionCode(String adminDivisionCode) {
        this.adminDivisionCode = adminDivisionCode;
    }

    public String getOrgSysCode() {
        return orgSysCode;
    }

    public void setOrgSysCode(String orgSysCode) {
        this.orgSysCode = orgSysCode;
    }

    public String getMenuNames() {
        return menuNames;
    }

    public void setMenuNames(String menuNames) {
        this.menuNames = menuNames;
    }

    public boolean getGradeAdministrator() {
        return gradeAdministrator;
    }

    public void setGradeAdministrator(boolean gradeAdministrator) {
        this.gradeAdministrator = gradeAdministrator;
    }

    public boolean getIsLawPerson() {
        return isLawPerson;
    }

    public void setIsLawPerson(boolean isLawPerson) {
        this.isLawPerson = isLawPerson;
    }

    public String getSupDeptId() {
        return supDeptId;
    }

    public void setSupDeptId(String supDeptId) {
        this.supDeptId = supDeptId;
    }

    public String getSupDeptName() {
        return supDeptName;
    }

    public void setSupDeptName(String supDeptName) {
        this.supDeptName = supDeptName;
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    
}
