package com.beidasoft.zfjd.supervise.model;

/**
 * 执法部门监督部门关系表MODEL类
 */
public class SupOrganizationModel {
    // 主键
    private String id;

    // 执法部门id
    private String organizationId;

    // 监督部门id
    private String superviseId;

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

    public String getSuperviseId() {
        return superviseId;
    }

    public void setSuperviseId(String superviseId) {
        this.superviseId = superviseId;
    }

}
