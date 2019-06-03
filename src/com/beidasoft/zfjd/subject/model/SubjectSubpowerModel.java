package com.beidasoft.zfjd.subject.model;

/**
 * 主体职权类别关系表MODEL类
 */
public class SubjectSubpowerModel {
    // 主键
    private String id;

    // 主体id
    private String subjectId;

    // 职权类别id
    private String subjectPowerId;

    // 职权类别名称
    private String subjectPowerName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectPowerId() {
        return subjectPowerId;
    }

    public void setSubjectPowerId(String subjectPowerId) {
        this.subjectPowerId = subjectPowerId;
    }

    public String getSubjectPowerName() {
        return subjectPowerName;
    }

    public void setSubjectPowerName(String subjectPowerName) {
        this.subjectPowerName = subjectPowerName;
    }

}
