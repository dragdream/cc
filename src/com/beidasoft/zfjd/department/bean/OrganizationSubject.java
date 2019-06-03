package com.beidasoft.zfjd.department.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 部门主体关联表表实体类
 */
@Entity
@Table(name="TBL_BASE_ORGANIZATION_SUBJECT")
public class OrganizationSubject {
    // 主键
	@Id
    @Column(name = "id")
    private String id;

    // 部门id
    @Column(name = "organization_id")
    private String organizationId;

    // 主体id
    @Column(name = "subject_id")
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
