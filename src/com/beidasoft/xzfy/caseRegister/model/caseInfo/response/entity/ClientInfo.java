package com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity;

import java.util.List;

/**
 * 当事人信息封装
 * 
 * @author cc
 * @date 2019年5月22日
 */
public class ClientInfo {

    private String applicantTypeCode; // 申请人类别代码

    private String applicantType; // 申请人类别

    private String isAgent; // 是否有申请人代理人

    private String isRespondentAgent; // 是否有被申请人代理人

    private String isThirdPartyAgent; // 是否有第三人代理人

    private String isThirdParty; // 是否有第三人

    private List<Applicant> applicant; // 申请人信息

    private List<Applicant> OtherApplicant; // 其他申请人

    private List<Agent> applicantAgent; // 申请人代理人信息

    private List<Respondent> respondent; // 被申请人信息

    private List<Agent> respondentAgent; // 被申请人代理人信息

    private List<ThirdParty> thirdParty; // 第三人信息

    private List<Agent> thirdPartyAgent; // 第三人代理人信息

    public String getApplicantType() {
        return applicantType;
    }

    public void setApplicantType(String applicantType) {
        this.applicantType = applicantType;
    }

    public String getApplicantTypeCode() {
        return applicantTypeCode;
    }

    public void setApplicantTypeCode(String applicantTypeCode) {
        this.applicantTypeCode = applicantTypeCode;
    }

    public String getIsAgent() {
        return isAgent;
    }

    public void setIsAgent(String isAgent) {
        this.isAgent = isAgent;
    }

    public String getIsRespondentAgent() {
        return isRespondentAgent;
    }

    public void setIsRespondentAgent(String isRespondentAgent) {
        this.isRespondentAgent = isRespondentAgent;
    }

    public String getIsThirdPartyAgent() {
        return isThirdPartyAgent;
    }

    public void setIsThirdPartyAgent(String isThirdPartyAgent) {
        this.isThirdPartyAgent = isThirdPartyAgent;
    }

    public String getIsThirdParty() {
        return isThirdParty;
    }

    public void setIsThirdParty(String isThirdParty) {
        this.isThirdParty = isThirdParty;
    }

    public List<Applicant> getApplicant() {
        return applicant;
    }

    public void setApplicant(List<Applicant> applicant) {
        this.applicant = applicant;
    }

    public List<Applicant> getOtherApplicant() {
        return OtherApplicant;
    }

    public void setOtherApplicant(List<Applicant> otherApplicant) {
        OtherApplicant = otherApplicant;
    }

    public List<Agent> getApplicantAgent() {
        return applicantAgent;
    }

    public void setApplicantAgent(List<Agent> applicantAgent) {
        this.applicantAgent = applicantAgent;
    }

    public List<Respondent> getRespondent() {
        return respondent;
    }

    public void setRespondent(List<Respondent> respondent) {
        this.respondent = respondent;
    }

    public List<Agent> getRespondentAgent() {
        return respondentAgent;
    }

    public void setRespondentAgent(List<Agent> respondentAgent) {
        this.respondentAgent = respondentAgent;
    }

    public List<ThirdParty> getThirdParty() {
        return thirdParty;
    }

    public void setThirdParty(List<ThirdParty> thirdParty) {
        this.thirdParty = thirdParty;
    }

    public List<Agent> getThirdPartyAgent() {
        return thirdPartyAgent;
    }

    public void setThirdPartyAgent(List<Agent> thirdPartyAgent) {
        this.thirdPartyAgent = thirdPartyAgent;
    }

}
