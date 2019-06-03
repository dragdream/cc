package com.beidasoft.xzzf.informationEntry.model;

/**
 * 子表执法人员MODEL类
 */
public class InforEntryStaffModel {
    // 主键ID
    private String id;

    // 案件ID
    private String caseId;

    // 执法人员ID
    private String identistyId;

    // 执法人员姓名
    private String officeName;

    // 执法证号
    private String cardCode;

    // 所属主体ID
    private String subjectId;

    // 入库日期
    private String createDateStr;

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

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

}
