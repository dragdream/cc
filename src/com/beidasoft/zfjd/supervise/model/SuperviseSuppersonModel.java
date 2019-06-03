package com.beidasoft.zfjd.supervise.model;

/**
 * 监督部门人员关系表MODEL类
 */
public class SuperviseSuppersonModel {
    // 主键
    private String id;

    // 监督部门id
    private String superviseId;

    // 监督人员id
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
