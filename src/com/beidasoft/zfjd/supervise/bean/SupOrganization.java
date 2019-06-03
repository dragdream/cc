package com.beidasoft.zfjd.supervise.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 执法部门监督部门关系表实体类
 */
@Entity
@Table(name="TBL_BASE_SUP_ORGANIZATION")
public class SupOrganization {
    // 主键
	@Id
    @Column(name = "id")
    private String id;

    // 执法部门id
    @Column(name = "organization_id")
    private String organizationId;

    // 监督部门id
    @Column(name = "supervise_id")
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
