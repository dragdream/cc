package com.beidasoft.zfjd.supervise.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 监督部门人员关系表实体类
 */
@Entity
@Table(name="TBL_BASE_SUPERVISE_SUPPERSON")
public class SuperviseSupperson {
    // 主键
	@Id
    @Column(name = "id")
    private String id;

    // 监督部门id
    @Column(name = "supervise_id")
    private String superviseId;

    // 监督人员id
    @Column(name = "supperson_id")
    private String suppersonId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSuperviseId() {
        return superviseId;
    }

    public void setSuperviseId(String superviseId) {
        this.superviseId = superviseId;
    }

    public String getSuppersonId() {
        return suppersonId;
    }

    public void setSuppersonId(String suppersonId) {
        this.suppersonId = suppersonId;
    }

}
