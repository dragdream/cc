package com.beidasoft.zfjd.subject.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 主体职权类别关系表实体类
 */
@Entity
@Table(name="jd_base_subject_subpower")
public class SubjectSubpower {
    // 主键
	@Id
    @Column(name = "id")
    private String id;

    // 主体id
    @Column(name = "subject_id")
    private String subjectId;

    // 职权类别id
    @Column(name = "subject_power_id")
    private String subjectPowerId;

    // 职权类别名称
    @Column(name = "subject_power_name")
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
