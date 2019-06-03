package com.beidasoft.zfjd.department.model;

/**
 * 部门主体关联表MODEL类
 */
public class OrganizationSubjectModel {
    // 主键
    private String id;

    // 部门id
    private String organizationId;

    // 主体id
    private String subjectId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

}
