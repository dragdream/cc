package com.beidasoft.zfjd.department.model;

/**
 * 执法部门人员关系表MODEL类
 */
public class OrganizationPersonModel {
    // 唯一标识
    private String id;

    // 关联执法部门id
    private String deptId;

    // 关联监督部门id
    private String supDeptId;

    // 关联执法主体id
    private String subjectId;

    // 人员id
    private String personId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getSupDeptId() {
        return supDeptId;
    }

    public void setSupDeptId(String supDeptId) {
        this.supDeptId = supDeptId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

}
