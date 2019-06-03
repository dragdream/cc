package com.beidasoft.zfjd.adminCoercion.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 法院强制执行信息实体类
 */
@Entity
@Table(name="TBL_COERCION_COURT_PERFORM")
public class CoercionCourtPerform {
    // 唯一标识
    @Id
    @Column(name = "ID")
    private String id;

    // 强制案件主表ID
    @Column(name = "COERCION_CASE_ID")
    private String coercionCaseId;

    // 创建时间
    @Column(name = "CREATE_DATE")
    private Date createDate;

    // 数据更新时间
    @Column(name = "UPDATE_DATE")
    private Date updateDate;

    // 数据更新人员
    @Column(name = "UPDATE_PERSON_ID")
    private String updatePersonId;

    // 法院强制执行方式
    @Column(name = "PERFORM_TYPE")
    private String performType;

    // 原处罚决定书文号
    @Column(name = "PUNISH_CODE_BEFORE")
    private String punishCodeBefore;

    // 原处罚决定书日期
    @Column(name = "PUNISH_DATE_BEFORE")
    private Date punishDateBefore;

    // 催告书送达日期
    @Column(name = "PRESS_SEND_DATE")
    private Date pressSendDate;

    // 催告书送达方式
    @Column(name = "PRESS_SEND_TYPE")
    private String pressSendType;

    // 原处罚金额
    @Column(name = "ORIGINAL_MONEY")
    private int originalMoney;

    // 原处罚日期
    @Column(name = "ORIGINAL_DATE")
    private Date originalDate;

    // 加处罚款
    @Column(name = "ADD_FIND_MONEY")
    private int addFindMoney;

    // 二次催告日期
    @Column(name = "SECOND_PRESS_DATE")
    private Date secondPressDate;
    
    // 是否进行二次催告
    @Column(name = "IS_SECOND_PRESS")
    private Integer isSecondPress;

    // 申请法院强制执行日期
    @Column(name = "APPLY_DATE")
    private Date applyDate;

    // 批准日期
    @Column(name = "APPROVE_DATE")
    private Date approveDate;

    // 二次催告送达方式
    @Column(name = "SECOND_PRESS_TYPE")
    private String secondPressType;

    // 执行标的情况
    @Column(name = "ENFORCE_ELEMENT_CONDITION")
    private String enforceElementCondition;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoercionCaseId() {
        return coercionCaseId;
    }

    public void setCoercionCaseId(String coercionCaseId) {
        this.coercionCaseId = coercionCaseId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdatePersonId() {
        return updatePersonId;
    }

    public void setUpdatePersonId(String updatePersonId) {
        this.updatePersonId = updatePersonId;
    }

    public String getPerformType() {
        return performType;
    }

    public void setPerformType(String performType) {
        this.performType = performType;
    }

    public String getPunishCodeBefore() {
        return punishCodeBefore;
    }

    public void setPunishCodeBefore(String punishCodeBefore) {
        this.punishCodeBefore = punishCodeBefore;
    }

    public Date getPunishDateBefore() {
        return punishDateBefore;
    }

    public void setPunishDateBefore(Date punishDateBefore) {
        this.punishDateBefore = punishDateBefore;
    }

    public Date getPressSendDate() {
        return pressSendDate;
    }

    public void setPressSendDate(Date pressSendDate) {
        this.pressSendDate = pressSendDate;
    }

    public String getPressSendType() {
        return pressSendType;
    }

    public void setPressSendType(String pressSendType) {
        this.pressSendType = pressSendType;
    }

    public int getOriginalMoney() {
        return originalMoney;
    }

    public void setOriginalMoney(int originalMoney) {
        this.originalMoney = originalMoney;
    }

    public Date getOriginalDate() {
        return originalDate;
    }

    public void setOriginalDate(Date originalDate) {
        this.originalDate = originalDate;
    }

    public int getAddFindMoney() {
        return addFindMoney;
    }

    public void setAddFindMoney(int addFindMoney) {
        this.addFindMoney = addFindMoney;
    }

    public Date getSecondPressDate() {
        return secondPressDate;
    }

    public void setSecondPressDate(Date secondPressDate) {
        this.secondPressDate = secondPressDate;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public String getSecondPressType() {
        return secondPressType;
    }

    public void setSecondPressType(String secondPressType) {
        this.secondPressType = secondPressType;
    }

    public String getEnforceElementCondition() {
        return enforceElementCondition;
    }

    public void setEnforceElementCondition(String enforceElementCondition) {
        this.enforceElementCondition = enforceElementCondition;
    }

    public Integer getIsSecondPress() {
        return isSecondPress;
    }

    public void setIsSecondPress(Integer isSecondPress) {
        this.isSecondPress = isSecondPress;
    }

}
