package com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity;

public class Respondent {

    private String id; // 主键ID

    private String caseId; // 案件ID

    private String respondentTypeCode; // 被申请人类型代码

    private String respondentType; // 被申请人类型

    private String respondentName; // 被申请人名称

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

    public String getRespondentTypeCode() {
        return respondentTypeCode;
    }

    public void setRespondentTypeCode(String respondentTypeCode) {
        this.respondentTypeCode = respondentTypeCode;
    }

    public String getRespondentType() {
        return respondentType;
    }

    public void setRespondentType(String respondentType) {
        this.respondentType = respondentType;
    }

    public String getRespondentName() {
        return respondentName;
    }

    public void setRespondentName(String respondentName) {
        this.respondentName = respondentName;
    }

}
