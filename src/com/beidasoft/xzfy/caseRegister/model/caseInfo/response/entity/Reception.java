package com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity;

import java.util.List;

import com.beidasoft.xzfy.caseRegister.model.entity.Material;
import com.beidasoft.xzfy.caseRegister.model.entity.Visitor;

/**
 * 接待信息
 * 
 * @author cc
 * 
 */
public class Reception {

    private String operationType; // 操作类型 "01"为登记/"02"为填报
    private String applicationTypeCode; // 复议申请方式代码
    private String applicationType; // 复议申请方式
    private String caseId; // 案件Id
    private String receptionDate; // 接待日期
    private String place; // 接待地点
    private String dealMan1Id; // 第一接待人
    private String dealMan2Id; // 第二接待人
    private String recertionTypeCode; // 复议请求/接待类型代码（01 问卷 02 法律咨询 03 反应问题 04
                                      // 补充材料 05 催办案件 06 提交申请 99 其他）
    private String dealResultCode; // 处理结果代码

    private String dealResult; // 处理结果

    private List<Visitor> visitorVo; // 被接待人基本信息

    private String receptionDetail; // 接待情况描述

    private String reconsiderOrganCode; // 复议机构代码

    private String reconsiderOrganName; // 复议机构名称

    private String isReconsiderTogether; // 其他复议机关\法院受理同一复议申请
    private String isReceiveMaterial; // 是否接收材料
    private List<Material> reconsiderMaterialVo; // 复议材料JSON

    public String getReconsiderOrganCode() {
        return reconsiderOrganCode;
    }

    public void setReconsiderOrganCode(String reconsiderOrganCode) {
        this.reconsiderOrganCode = reconsiderOrganCode;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getApplicationTypeCode() {
        return applicationTypeCode;
    }

    public void setApplicationTypeCode(String applicationTypeCode) {
        this.applicationTypeCode = applicationTypeCode;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getReceptionDate() {
        return receptionDate;
    }

    public void setReceptionDate(String receptionDate) {
        this.receptionDate = receptionDate;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDealMan1Id() {
        return dealMan1Id;
    }

    public void setDealMan1Id(String dealMan1Id) {
        this.dealMan1Id = dealMan1Id;
    }

    public String getDealMan2Id() {
        return dealMan2Id;
    }

    public void setDealMan2Id(String dealMan2Id) {
        this.dealMan2Id = dealMan2Id;
    }

    public String getRecertionTypeCode() {
        return recertionTypeCode;
    }

    public void setRecertionTypeCode(String recertionTypeCode) {
        this.recertionTypeCode = recertionTypeCode;
    }

    public String getDealResultCode() {
        return dealResultCode;
    }

    public void setDealResultCode(String dealResultCode) {
        this.dealResultCode = dealResultCode;
    }

    public String getDealResult() {
        return dealResult;
    }

    public void setDealResult(String dealResult) {
        this.dealResult = dealResult;
    }

    public List<Visitor> getVisitorVo() {
        return visitorVo;
    }

    public void setVisitorVo(List<Visitor> visitorVo) {
        this.visitorVo = visitorVo;
    }

    public String getReceptionDetail() {
        return receptionDetail;
    }

    public void setReceptionDetail(String receptionDetail) {
        this.receptionDetail = receptionDetail;
    }

    public String getReconsiderOrganName() {
        return reconsiderOrganName;
    }

    public void setReconsiderOrganName(String reconsiderOrganName) {
        this.reconsiderOrganName = reconsiderOrganName;
    }

    public String getIsReconsiderTogether() {
        return isReconsiderTogether;
    }

    public void setIsReconsiderTogether(String isReconsiderTogether) {
        this.isReconsiderTogether = isReconsiderTogether;
    }

    public String getIsReceiveMaterial() {
        return isReceiveMaterial;
    }

    public void setIsReceiveMaterial(String isReceiveMaterial) {
        this.isReceiveMaterial = isReceiveMaterial;
    }

    public List<Material> getReconsiderMaterialVo() {
        return reconsiderMaterialVo;
    }

    public void setReconsiderMaterialVo(List<Material> reconsiderMaterialVo) {
        this.reconsiderMaterialVo = reconsiderMaterialVo;
    }

}
