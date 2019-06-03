package com.beidasoft.xzzf.informationEntry.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 子表执法人员实体类
 */
@Entity
@Table(name="ZF_INFOR_ENTRY_STAFF")
public class InforEntryStaff {
    // 主键ID
    @Id
    @Column(name = "ID")
    private String id;

    // 案件ID
    @Column(name = "CASE_ID")
    private String caseId;

    // 执法人员ID
    @Column(name = "IDENTISTY_ID")
    private String identistyId;

    // 执法人员姓名
    @Column(name = "OFFICE_NAME")
    private String officeName;

    // 执法证号
    @Column(name = "CARD_CODE")
    private String cardCode;

    // 所属主体ID
    @Column(name = "SUBJECT_ID")
    private String subjectId;

    // 入库日期
    @Column(name = "CREATE_DATE")
    private Date createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getIdentistyId() {
        return identistyId;
    }

    public void setIdentistyId(String identistyId) {
        this.identistyId = identistyId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
