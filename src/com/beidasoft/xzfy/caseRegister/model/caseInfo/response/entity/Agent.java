package com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity;

public class Agent {

    /** 主键id */
    private String id;

    /** 案件id */
    private String caseId;

    /** 代理人类型编码 */
    private String agentTypeCode;

    /** 代理人类型 */
    private String agentType;

    /** 代理人名称 */
    private String agentName;

    /** 证件类型编码 */
    private String cardTypeCode;

    /** 证件类型 */
    private String cardType;

    /** 证件号码 */
    private String idCard;

    /** 联系电话 */
    private String phone;

    /** 是否有授权书 */
    private String isAuthorization;

    /** 类型:1申请人代理，2被申请人代理，3第三人代理 */
    private String type;

    /*被代理人*/
    private String agentParent;

    /*被代理人Id*/
    private String agentParentId;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getAgentParentId() {
        return agentParentId;
    }

    public void setAgentParentId(String agentParentId) {
        this.agentParentId = agentParentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAgentParent() {
        return agentParent;
    }

    public void setAgentParent(String agentParent) {
        this.agentParent = agentParent;
    }

}
