package com.beidasoft.xzfy.caseRegister.bean;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 被申请人信息
 * 
 * @author cc
 * 
 */
@Entity
@Table(name = "FY_RESPONDENT")
public class FyRespondent {

    /** 主键 */
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "id")
    @GenericGenerator(name = "id", strategy = "uuid")
    private String id;

    /** 案件ID FY_CASE_HANDLING.CASE_ID */
    @Column(name = "CASE_ID")
    private String caseId;

    /**
     * 被申请人类型代码 01 国务院部门 02 省级政府 03 省级政府部门 04 市级政府 05 市级政府部门 06 县政府 07 县政府部门 08 乡镇政府99 其他
     */
    @Column(name = "RESPONDENT_TYPE_CODE")
    private String respondentTypeCode;

    /** 被申请人类型 */
    @Column(name = "RESPONDENT_TYPE")
    private String respondentType;

    /** 被申请人行政区划 省市区县 */
    @Column(name = "AREA_CODE")
    private String areaCode;

    /** 被申请人名称 */
    @Column(name = "RESPONDENT_NAME")
    private String respondentName;

    /** 创建人 */
    @Column(name = "CREATED_USER")
    private String createdUser;

    /** 创建时间 */
    @Column(name = "CREATED_TIME")
    private String createdTime;

    /** 修改人 */
    @Column(name = "MODIFIED_USER")
    private String modifiedUser;

    /** 修改时间 */
    @Column(name = "MODIFIED_TIME")
    private String modifiedTime;

    /** 是否删除 */
    @Column(name = "IS_DELETE")
    private Integer isDelete;

    /** 是否有授权书 */
    @Column(name = "IS_AUTHORIZATION")
    private BigDecimal isAuthorization;

    /** 修改人ID */
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;

    /** 创建人Id */
    @Column(name = "CREATED_USER_ID")
    private String createdUserId;

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

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getRespondentName() {
        return respondentName;
    }

    public void setRespondentName(String respondentName) {
        this.respondentName = respondentName;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
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

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public BigDecimal getIsAuthorization() {
        return isAuthorization;
    }

    public void setIsAuthorization(BigDecimal isAuthorization) {
        this.isAuthorization = isAuthorization;
    }

    public String getModifiedUserId() {
        return modifiedUserId;
    }

    public void setModifiedUserId(String modifiedUserId) {
        this.modifiedUserId = modifiedUserId;
    }

    public String getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(String createdUserId) {
        this.createdUserId = createdUserId;
    }

}
