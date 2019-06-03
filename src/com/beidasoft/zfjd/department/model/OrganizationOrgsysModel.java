package com.beidasoft.zfjd.department.model;

/**
 * 部门和所属领域关系表MODEL类
 */
public class OrganizationOrgsysModel {
    // 主键
    private String id;

    // 部门id
    private String deptId;

    // 所属领域id
    private String fieldId;

    // 所属领域名称
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
