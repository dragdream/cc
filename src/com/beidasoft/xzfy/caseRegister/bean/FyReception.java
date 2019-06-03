package com.beidasoft.xzfy.caseRegister.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 接待信息
 * 
 * @author cc
 * 
 */
@Entity
@Table(name = "FY_RECEPTION")
public class FyReception {

    @Id
    @Column(name = "CASE_ID")
    private String caseId; // 案件ID

    @Column(name = "RECEPTION_DATE")
    private String receptionDate; // 接待日期

    @Column(name = "PLACE")
    private String place; // 接待地点

    @Column(name = "DEAL_MAN1_ID")
    private String dealMan1Id; // 第一接待人

    @Column(name = "DEAL_MAN2_ID")
    private String dealMan2Id; // 第二接待人

    @Column(name = "VISITOR_NUM")
    private int visitorNum; // 被接待人数

    @Column(name = "RECEPTION_TYPE_CODE")
    private String recertionTypeCode; // 接待类型代码（01 问卷 02 法律咨询 03 反应问题 04 补充材料
                                      // 05催办案件 06 提交申请 99 其他）

    @Column(name = "RECEPTION_TYPE")
    private String recertionType; // 接待类型

    @Column(name = "DEAL_RESULT_CODE")
    private String dealResultCode; // 处理结果代码

    @Column(name = "DEAL_RESULT")
    private String dealResult; // 处理结果

    @Column(name = "RECEPTION_DETAIL")
    private String receptionDetail; // 接待情况描述

    @Column(name = "VISITOR")
    private String visitor; // 被接待人信息

    @Column(name = "RECONSIDER_ORGAN_CODE")
    private String reconsiderOrganCode; // 复议机构代码

    @Column(name = "RECONSIDER_ORGAN_NAME")
    private String reconsiderOrganName; // 复议机构名称

    @Column(name = "IS_RECONSIDER_TOGETHER")
    private String isReconsiderTogether; // 其他复议机关\法院受理同一复议申请

    @Column(name = "IS_RECEIVE_MATERIAL")
    private String isReceiveMaterial; // 是否接收材料

    @Column(name = "RECONSIDER_MATERIAL")
    private String reconsiderMaterial; // 复议材料JSON

    @Column(name = "CREATED_USER_ID")
    private String createdUserId; // 创建人Id

    @Column(name = "CREATED_USER")
    private String createdUser; // 创建人

    @Column(name = "CREATED_TIME")
    private String createTime; // 创建时间

    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId; // 修改人ID

    @Column(name = "MODIFIED_USER")
    private String modifiedUser; // 修改人

    @Column(name = "MODIFIED_TIME")
    private String modifiedTime; // 修改时间

    @Column(name = "IS_DELETE")
    private int isDelete; // 是否删除

    public String getReconsiderOrganCode() {
        return reconsiderOrganCode;
    }

    public void setReconsiderOrganCode(String reconsiderOrganCode) {
        this.reconsiderOrganCode = reconsiderOrganCode;
    }

    public String getRecertionType() {
        return recertionType;
    }

    public void setRecertionType(String recertionType) {
        this.recertionType = recertionType;
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

    public String getReconsiderMaterial() {
        return reconsiderMaterial;
    }

    public void setReconsiderMaterial(String reconsiderMaterial) {
        this.reconsiderMaterial = reconsiderMaterial;
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

    public String getDealResult() {
        return dealResult;
    }

    public void setDealResult(String dealResult) {
        this.dealResult = dealResult;
    }

    public String getDealMan2Id() {
        return dealMan2Id;
    }

    public void setDealMan2Id(String dealMan2Id) {
        this.dealMan2Id = dealMan2Id;
    }

    public int getVisitorNum() {
        return visitorNum;
    }

    public void setVisitorNum(int visitorNum) {
        this.visitorNum = visitorNum;
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

    public String getReceptionDetail() {
        return receptionDetail;
    }

    public void setReceptionDetail(String receptionDetail) {
        this.receptionDetail = receptionDetail;
    }

    public String getVisitor() {
        return visitor;
    }

    public void setVisitor(String visitor) {
        this.visitor = visitor;
    }

    public String getReconsiderOrganName() {
        return reconsiderOrganName;
    }

    public void setReconsiderOrganName(String reconsiderOrganName) {
        this.reconsiderOrganName = reconsiderOrganName;
    }

    public String getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(String createdUserId) {
        this.createdUserId = createdUserId;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifiedUserId() {
        return modifiedUserId;
    }

    public void setModifiedUserId(String modifiedUserId) {
        this.modifiedUserId = modifiedUserId;
    }

    public String getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

}
