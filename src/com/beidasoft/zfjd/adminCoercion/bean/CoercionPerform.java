package com.beidasoft.zfjd.adminCoercion.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 强制执行信息表实体类
 */
@Entity
@Table(name="TBL_COERCION_CASE_PERFORM")
public class CoercionPerform {
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

    // 强制执行类型
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
    private Integer originalMoney;

    // 原处罚日期
    @Column(name = "ORIGINAL_DATE")
    private Date originalDate;

    // 加处罚款
    @Column(name = "ADD_FIND_MONEY")
    private Integer addFindMoney;

    // 二次催告日期
    @Column(name = "SECOND_PRESS_DATE")
    private Date secondPressDate;
    
    // 二次催告送达方式
    @Column(name = "SECOND_PRESS_TYPE")
    private String secondPressType;

    // 申请强制执行日期
    @Column(name = "APPLY_DATE")
    private Date applyDate;

    // 批准日期
    @Column(name = "APPROVE_DATE")
    private Date approveDate;

    // 批准人
    @Column(name = "APPROVE_PERSON")
    private String approvePerson;

    // 其他强制执行描述
    @Column(name = "OTHER_PERFORM_DESCRIBE")
    private String otherPerformDescribe;

    // 强制执行决定书文号
    @Column(name = "COERCION_PERFORM_CODE")
    private String coercionPerformCode;

    // 强制执行日期
    @Column(name = "PERFORM_ENFORCE_DATE")
    private Date performEnforceDate;

    // 决定书送达日期
    @Column(name = "DECIDE_SEND_DATE")
    private Date decideSendDate;

    // 决定书送达方式
    @Column(name = "DECIDE_SEND_TYPE")
    private String decideSendType;

    // 划拨金额
    @Column(name = "TRANS_DEPOSIT")
    private Integer transDeposit;

    // 划拨金融机构
    @Column(name = "TRANS_ORGANIZATION")
    private String transOrganization;

    // 通知划拨日期
    @Column(name = "TRANS_NOTICE_DATE")
    private Date transNoticeDate;

    // 拍卖地址
    @Column(name = "AUCTION_ADDR")
    private String auctionAddr;

    // 拍卖日期
    @Column(name = "AUCTION_DATE")
    private Date auctionDate;

    // 拍卖所得处理
    @Column(name = "AUCTION_USE")
    private String auctionUse;

    // 代履行人
    @Column(name = "REPLACE_OBJECT")
    private String replaceObject;

    // 代履行费用
    @Column(name = "REPLACE_DEPOSIT")
    private Integer replaceDeposit;

    // 代履行监督人
    @Column(name = "REPLACE_SUPERVISE")
    private String replaceSupervise;

    // 代履行日期
    @Column(name = "REPLACE_ENFORCE_DATE")
    private Date replaceEnforceDate;

    // 是否执行协议
    @Column(name = "IS_AGREEMENT_ENFORCE")
    private Integer isAgreementEnforce;

    // 协议签订日期
    @Column(name = "AGREE_SIGN_DATE")
    private Date agreeSignDate;

    // 协议内容
    @Column(name = "AGREE_CONTENT")
    private String agreeContent;

    // 协议执行情况
    @Column(name = "AGREE_ENFORCE_CONDITION")
    private String agreeEnforceCondition;

    // 是否执行回转
    @Column(name = "IS_ENFORCE_RETURN")
    private Integer isEnforceReturn;

    // 执行回转日期
    @Column(name = "ENFORCE_RETURN_DATE")
    private Date enforceReturnDate;

    // 执行回转原因
    @Column(name = "ENFORCE_RETURN_REASON")
    private String enforceReturnReason;

    // 执行回转内容
    @Column(name = "ENFORCE_RETURN_CONTENT")
    private String enforceReturnContent;

    // 是否终结执行
    @Column(name = "IS_END_ENFORCE")
    private Integer isEndEnforce;

    // 终结通知书送达日期
    @Column(name = "END_ENFORCE_SEND_DATE")
    private Date endEnforceSendDate;

    // 终结通知书送达方式
    @Column(name = "END_SEND_TYPE")
    private String endSendType;

    // 终结原因
    @Column(name = "ENFORCE_END_REASON")
    private String enforceEndReason;

    // 是否中止执行
    @Column(name = "IS_BREAK_ENFORCE")
    private Integer isBreakEnforce;

    // 中止执行日期
    @Column(name = "BREAK_ENFORCE_DATE")
    private Date breakEnforceDate;

    // 恢复执行日期
    @Column(name = "RELWASE_ENFORCE_DATE")
    private Date relwaseEnforceDate;

    // 中止执行原因
    @Column(name = "ENFORCE_BREAK_REASON")
    private String enforceBreakReason;

    // 中止执行原因
    @Column(name = "ENFORCE_STEP")
    private Integer enforceStep;
    
    @OneToMany(mappedBy = "coercionPerformGist", fetch = FetchType.LAZY) // 双向,  目标写@JoinColumn 
    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    @OrderBy("lawName ASC")
    private List<CoercionCaseGist> gists;
    
    @OneToMany(mappedBy = "coercionPerformPower", fetch = FetchType.LAZY) // 双向,  目标写@JoinColumn 
    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private List<CoercionCasePower> powers;
    
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

    public Integer getOriginalMoney() {
        return originalMoney;
    }

    public void setOriginalMoney(Integer originalMoney) {
        this.originalMoney = originalMoney;
    }

    public Date getOriginalDate() {
        return originalDate;
    }

    public void setOriginalDate(Date originalDate) {
        this.originalDate = originalDate;
    }

    public Integer getAddFindMoney() {
        return addFindMoney;
    }

    public void setAddFindMoney(Integer addFindMoney) {
        this.addFindMoney = addFindMoney;
    }

    public Date getSecondPressDate() {
        return secondPressDate;
    }

    public void setSecondPressDate(Date secondPressDate) {
        this.secondPressDate = secondPressDate;
    }

    public String getSecondPressType() {
        return secondPressType;
    }

    public void setSecondPressType(String secondPressType) {
        this.secondPressType = secondPressType;
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

    public String getApprovePerson() {
        return approvePerson;
    }

    public void setApprovePerson(String approvePerson) {
        this.approvePerson = approvePerson;
    }

    public String getOtherPerformDescribe() {
        return otherPerformDescribe;
    }

    public void setOtherPerformDescribe(String otherPerformDescribe) {
        this.otherPerformDescribe = otherPerformDescribe;
    }

    public String getCoercionPerformCode() {
        return coercionPerformCode;
    }

    public void setCoercionPerformCode(String coercionPerformCode) {
        this.coercionPerformCode = coercionPerformCode;
    }

    public Date getPerformEnforceDate() {
        return performEnforceDate;
    }

    public void setPerformEnforceDate(Date performEnforceDate) {
        this.performEnforceDate = performEnforceDate;
    }

    public Date getDecideSendDate() {
        return decideSendDate;
    }

    public void setDecideSendDate(Date decideSendDate) {
        this.decideSendDate = decideSendDate;
    }

    public String getDecideSendType() {
        return decideSendType;
    }

    public void setDecideSendType(String decideSendType) {
        this.decideSendType = decideSendType;
    }

    public Integer getTransDeposit() {
        return transDeposit;
    }

    public void setTransDeposit(Integer transDeposit) {
        this.transDeposit = transDeposit;
    }

    public String getTransOrganization() {
        return transOrganization;
    }

    public void setTransOrganization(String transOrganization) {
        this.transOrganization = transOrganization;
    }

    public Date getTransNoticeDate() {
        return transNoticeDate;
    }

    public void setTransNoticeDate(Date transNoticeDate) {
        this.transNoticeDate = transNoticeDate;
    }

    public String getAuctionAddr() {
        return auctionAddr;
    }

    public void setAuctionAddr(String auctionAddr) {
        this.auctionAddr = auctionAddr;
    }

    public Date getAuctionDate() {
        return auctionDate;
    }

    public void setAuctionDate(Date auctionDate) {
        this.auctionDate = auctionDate;
    }

    public String getAuctionUse() {
        return auctionUse;
    }

    public void setAuctionUse(String auctionUse) {
        this.auctionUse = auctionUse;
    }

    public String getReplaceObject() {
        return replaceObject;
    }

    public void setReplaceObject(String replaceObject) {
        this.replaceObject = replaceObject;
    }

    public Integer getReplaceDeposit() {
        return replaceDeposit;
    }

    public void setReplaceDeposit(Integer replaceDeposit) {
        this.replaceDeposit = replaceDeposit;
    }

    public String getReplaceSupervise() {
        return replaceSupervise;
    }

    public void setReplaceSupervise(String replaceSupervise) {
        this.replaceSupervise = replaceSupervise;
    }

    public Date getReplaceEnforceDate() {
        return replaceEnforceDate;
    }

    public void setReplaceEnforceDate(Date replaceEnforceDate) {
        this.replaceEnforceDate = replaceEnforceDate;
    }

    public Integer getIsAgreementEnforce() {
        return isAgreementEnforce;
    }

    public void setIsAgreementEnforce(Integer isAgreementEnforce) {
        this.isAgreementEnforce = isAgreementEnforce;
    }

    public Date getAgreeSignDate() {
        return agreeSignDate;
    }

    public void setAgreeSignDate(Date agreeSignDate) {
        this.agreeSignDate = agreeSignDate;
    }

    public String getAgreeContent() {
        return agreeContent;
    }

    public void setAgreeContent(String agreeContent) {
        this.agreeContent = agreeContent;
    }

    public String getAgreeEnforceCondition() {
        return agreeEnforceCondition;
    }

    public void setAgreeEnforceCondition(String agreeEnforceCondition) {
        this.agreeEnforceCondition = agreeEnforceCondition;
    }

    public Integer getIsEnforceReturn() {
        return isEnforceReturn;
    }

    public void setIsEnforceReturn(Integer isEnforceReturn) {
        this.isEnforceReturn = isEnforceReturn;
    }

    public Date getEnforceReturnDate() {
        return enforceReturnDate;
    }

    public void setEnforceReturnDate(Date enforceReturnDate) {
        this.enforceReturnDate = enforceReturnDate;
    }

    public String getEnforceReturnReason() {
        return enforceReturnReason;
    }

    public void setEnforceReturnReason(String enforceReturnReason) {
        this.enforceReturnReason = enforceReturnReason;
    }

    public String getEnforceReturnContent() {
        return enforceReturnContent;
    }

    public void setEnforceReturnContent(String enforceReturnContent) {
        this.enforceReturnContent = enforceReturnContent;
    }

    public Integer getIsEndEnforce() {
        return isEndEnforce;
    }

    public void setIsEndEnforce(Integer isEndEnforce) {
        this.isEndEnforce = isEndEnforce;
    }

    public Date getEndEnforceSendDate() {
        return endEnforceSendDate;
    }

    public void setEndEnforceSendDate(Date endEnforceSendDate) {
        this.endEnforceSendDate = endEnforceSendDate;
    }

    public String getEndSendType() {
        return endSendType;
    }

    public void setEndSendType(String endSendType) {
        this.endSendType = endSendType;
    }

    public String getEnforceEndReason() {
        return enforceEndReason;
    }

    public void setEnforceEndReason(String enforceEndReason) {
        this.enforceEndReason = enforceEndReason;
    }

    public Integer getIsBreakEnforce() {
        return isBreakEnforce;
    }

    public void setIsBreakEnforce(Integer isBreakEnforce) {
        this.isBreakEnforce = isBreakEnforce;
    }

    public Date getBreakEnforceDate() {
        return breakEnforceDate;
    }

    public void setBreakEnforceDate(Date breakEnforceDate) {
        this.breakEnforceDate = breakEnforceDate;
    }

    public Date getRelwaseEnforceDate() {
        return relwaseEnforceDate;
    }

    public void setRelwaseEnforceDate(Date relwaseEnforceDate) {
        this.relwaseEnforceDate = relwaseEnforceDate;
    }

    public String getEnforceBreakReason() {
        return enforceBreakReason;
    }

    public void setEnforceBreakReason(String enforceBreakReason) {
        this.enforceBreakReason = enforceBreakReason;
    }

    public List<CoercionCaseGist> getGists() {
        return gists;
    }

    public void setGists(List<CoercionCaseGist> gists) {
        this.gists = gists;
    }

    public List<CoercionCasePower> getPowers() {
        return powers;
    }

    public void setPowers(List<CoercionCasePower> powers) {
        this.powers = powers;
    }

    public Integer getEnforceStep() {
        return enforceStep;
    }

    public void setEnforceStep(Integer enforceStep) {
        this.enforceStep = enforceStep;
    }

}
