package com.beidasoft.xzfy.caseRegister.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 申请人信息
 * 
 * @author chumc
 * 
 */
@Entity
@Table(name = "FY_AGENT")
public class FyAgent {

    /** 主键:主键 */
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "id")
    @GenericGenerator(name = "id", strategy = "uuid")
    private String id;

    /** 案件ID */
    @Column(name = "CASE_ID")
    private String caseId;

    /*代理人类型编码 */
    @Column(name = "AGENT_TYPE_CODE")
    private String agentTypeCode;

    /** 代理人类型 */
    @Column(name = "AGENT_TYPE")
    private String agentType;

    /** 代理人名称 */
    @Column(name = "AGENT_NAME")
    private String agentName;

    /** 证件类型编码 */
    @Column(name = "CARD_TYPE_CODE")
    private String cardTypeCode;

    /** 证件类型 */
    @Column(name = "CARD_TYPE")
    private String cardType;

    /** 证件号码 */
    @Column(name = "ID_CARD")
    private String idCard;

    /** 联系电话 */
    @Column(name = "PHONE")
    private String phone;

    /** 是否有授权书 */
    @Column(name = "IS_AUTHORIZATION")
    private String isAuthorization;

    /** 创建人 */
    @Column(name = "CREATED_USER")
    private String createdUser;

    /** 创建时间 */
    @Column(name = "CREATED_TIME")
    private String createdTime;

    /** 创建人Id */
    @Column(name = "CREATED_USER_ID")
    private String createdUserId;

    /** 修改人 */
    @Column(name = "MODIFIED_USER")
    private String modifiedUser;

    /** 修改时间 */
    @Column(name = "MODIFIED_TIME")
    private String modifiedTime;

    /** 修改人ID */
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;

    /** 类型:1申请人代理，2被申请人代理，3第三人代理 */
    @Column(name = "TYPE")
    private String type;

    /** 是否删除 */
    @Column(name = "IS_DELETE")
    private Integer isDelete;

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

    public String getAgentTypeCode() {
        return agentTypeCode;
    }

    public void setAgentTypeCode(String agentTypeCode) {
        this.agentTypeCode = agentTypeCode;
    }

    public String getAgentType() {
        return agentType;
    }

    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getCardTypeCode() {
        return cardTypeCode;
    }

    public void setCardTypeCode(String cardTypeCode) {
        this.cardTypeCode = cardTypeCode;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIsAuthorization() {
        return isAuthorization;
    }

    public void setIsAuthorization(String isAuthorization) {
        this.isAuthorization = isAuthorization;
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

    public String getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(String createdUserId) {
        this.createdUserId = createdUserId;
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

    public String getModifiedUserId() {
        return modifiedUserId;
    }

    public void setModifiedUserId(String modifiedUserId) {
        this.modifiedUserId = modifiedUserId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}
