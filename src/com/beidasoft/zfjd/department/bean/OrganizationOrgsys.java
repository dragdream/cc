package com.beidasoft.zfjd.department.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 部门和所属领域关系表实体类
 */
@Entity
@Table(name="tbl_base_organization_orgsys")
public class OrganizationOrgsys {
    // 主键
	@Id
    @Column(name = "id")
    private String id;

    // 部门id
    @Column(name = "dept_id")
    private String deptId;

    // 所属领域id
    @Column(name = "field_id")
    private String fieldId;

    // 所属领域名称
    @Column(name = "field_name")
    private String fieldName;

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

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

}
