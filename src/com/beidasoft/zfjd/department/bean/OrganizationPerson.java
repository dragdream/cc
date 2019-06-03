package com.beidasoft.zfjd.department.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 执法部门人员关系表实体类
 */
@Entity
@Table(name="TBL_BASE_ORGANIZATION_PERSON")
public class OrganizationPerson {
    // 唯一标识
	@Id
    @Column(name = "id")
    private String id;

    // 关联执法部门id
    @Column(name = "dept_id")
    private String deptId;

    // 关联监督部门id
    @Column(name = "sup_dept_id")
    private String supDeptId;

    // 关联执法主体id
    @Column(name = "subject_id")
    private String subjectId;

    // 人员id
    @Column(name = "person_id")
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
