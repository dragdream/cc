package com.beidasoft.xzfy.caseRegister.model.caseExtract.request;

import com.beidasoft.xzfy.base.model.request.Request;

/**
 * 案件提取
 * 
 * @author cc
 * 
 */
public class GetCaseExtractReq implements Request {

    private String caseId; // 案件ID
    private String caseNum; // 案件编号
    private String name; // 申请人名称 （申请人信息表）
    private String respondentName; // 被申请人名称
    private String dealMan1Id; // 第一接待人 （接待信息表）
    private String startTime;
    private String endTime;
    private int page; // 当前页
    private int rows; // 每页记录数

    @Override
    public void validate() {
        // TODO Auto-generated method stub

    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCaseNum() {
        return caseNum;
    }

    public void setCaseNum(String caseNum) {
        this.caseNum = caseNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRespondentName() {
        return respondentName;
    }

    public void setRespondentName(String respondentName) {
        this.respondentName = respondentName;
    }

    public String getDealMan1Id() {
        return dealMan1Id;
    }

    public void setDealMan1Id(String dealMan1Id) {
        this.dealMan1Id = dealMan1Id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

}
