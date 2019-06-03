package com.beidasoft.xzzf.punish.common.bean;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 案件参与人表实体类
 */
@Entity
@Table(name="ZF_AFFILIATED_PERSON")
public class AffiliatedPerson {
    // 参与人主键ID
    @Id
    @Column(name = "ID")
    private String id;

    // 执法办案唯一编号
    @Column(name = "BASE_ID")
    private String baseId;

    // 执法环节ID
    @Column(name = "LAW_LINK_ID")
    private String lawLinkId;

    // 人员ID
    @Column(name = "PERSON_ID")
    private int personId;

    // 人员名称
    @Column(name = "PERSON_NAME")
    private String personName;

    // 人员执法证号
    @Column(name = "PERSON_NO")
    private String personNo;

    // 部门ID
    @Column(name = "DEPARTMENT_ID")
    private int departmentId;

    // 部门名称
    @Column(name = "DEPARTMENT_NAME")
    private String departmentName;

    // 删除标记
    @Column(name = "DEL_FLG")
    private String delFlg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    public String getLawLinkId() {
        return lawLinkId;
    }

    public void setLawLinkId(String lawLinkId) {
        this.lawLinkId = lawLinkId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonNo() {
        return personNo;
    }

    public void setPersonNo(String personNo) {
        this.personNo = personNo;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

}
