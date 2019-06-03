package com.beidasoft.zfjd.adminCoercion.model;

/**
 * 强制执行信息表MODEL类
 */
public class CoercionPerformModel {
    // 唯一标识
    private String id;

    // 强制案件主表ID
    private String coercionCaseId;

    // 创建时间
    private String createDateStr;

    // 数据更新时间
    private String updateDateStr;

    // 数据更新人员
    private String updatePersonId;

    // 强制执行类型
    private String performType;
    
    // 强制执行类型
    private String performTypeStr;

    // 原处罚决定书文号
    private String punishCodeBefore;

    // 原处罚决定书日期
    private String punishDateBeforeStr;

    // 催告书送达日期
    private String pressSendDateStr;

    // 催告书送达方式
    private String pressSendType;

    // 原处罚金额
    private Integer originalMoney;

    // 原处罚日期
    private String originalDateStr;

    // 加处罚款
    private Integer addFindMoney;

    // 二次催告日期
    private String secondPressDateStr;
    
    // 二次催告送达方式
    private String secondPressType;

    // 申请强制执行日期
    private String applyDateStr;

    // 批准日期
    private String approveDateStr;

    // 批准人
    private String approvePerson;

    // 其他强制执行描述
    private String otherPerformDescribe;

    // 强制执行决定书文号
    private String coercionPerformCode;

    // 强制执行日期
    private String performEnforceDateStr;

    // 决定书送达日期
    private String decideSendDateStr;

    // 决定书送达方式
    private String decideSendType;

    // 划拨金额
    private Integer transDeposit;

    // 划拨金融机构
    private String transOrganization;

    // 通知划拨日期
    private String transNoticeDateStr;

    // 拍卖地址
    private String auctionAddr;

    // 拍卖日期
    private String auctionDateStr;

    // 拍卖所得处理
    private String auctionUse;

    // 代履行人
    private String replaceObject;

    // 代履行费用
    private Integer replaceDeposit;

    // 代履行监督人
    private String replaceSupervise;

    // 代履行日期
    private String replaceEnforceDateStr;

    // 是否执行协议
    private Integer isAgreementEnforce;

    // 协议签订日期
    private String agreeSignDateStr;

    // 协议内容
    private String agreeContent;

    // 协议执行情况
    private String agreeEnforceCondition;

    // 是否执行回转
    private Integer isEnforceReturn;

    // 执行回转日期
    private String enforceReturnDateStr;

    // 执行回转原因
    private String enforceReturnReason;

    // 执行回转内容
    private String enforceReturnContent;

    // 是否终结执行
    private Integer isEndEnforce;

    // 终结通知书送达日期
    private String endEnforceSendDateStr;

    // 终结通知书送达方式
    private String endSendType;

    // 终结原因
    private String enforceEndReason;

    // 是否中止执行
    private Integer isBreakEnforce;

    // 中止执行日期
    private String breakEnforceDateStr;

    // 恢复执行日期
    private String relwaseEnforceDateStr;

    // 中止执行原因
    private String enforceBreakReason;

    // 流程状态
    private Integer enforceStep;
    
    //主表强制案件来源类型
    private Integer caseSourceType;
    
    //主表强制案件来源id
    private String caseSourceId;
    
    //执法主体id
    private String subjectId;
   
    //执法主体id
    private String departmentId;
    
    //职权信息json字符串
    private String powerJsonStr;
    
    //依据信息json字符串
    private String gistJsonStr;
    
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

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public String getUpdateDateStr() {
        return updateDateStr;
    }

    public void setUpdateDateStr(String updateDateStr) {
        this.updateDateStr = updateDateStr;
    }

    public String getUpdatePersonId() {
        return updatePersonId;
    }

    public void setUpdatePersonId(String updatePersonId) {
        this.updatePersonId = updatePersonId;
    }

    public String getPerformTypeStr() {
        return performTypeStr;
    }

    public void setPerformTypeStr(String performTypeStr) {
        this.performTypeStr = performTypeStr;
    }

    public String getPunishCodeBefore() {
        return punishCodeBefore;
    }

    public void setPunishCodeBefore(String punishCodeBefore) {
        this.punishCodeBefore = punishCodeBefore;
    }

    public String getPunishDateBeforeStr() {
        return punishDateBeforeStr;
    }

    public void setPunishDateBeforeStr(String punishDateBeforeStr) {
        this.punishDateBeforeStr = punishDateBeforeStr;
    }

    public String getPressSendDateStr() {
        return pressSendDateStr;
    }

    public void setPressSendDateStr(String pressSendDateStr) {
        this.pressSendDateStr = pressSendDateStr;
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

    public String getOriginalDateStr() {
        return originalDateStr;
    }

    public void setOriginalDateStr(String originalDateStr) {
        this.originalDateStr = originalDateStr;
    }

    public Integer getAddFindMoney() {
        return addFindMoney;
    }

    public void setAddFindMoney(Integer addFindMoney) {
        this.addFindMoney = addFindMoney;
    }

    public String getSecondPressDateStr() {
        return secondPressDateStr;
    }

    public void setSecondPressDateStr(String secondPressDateStr) {
        this.secondPressDateStr = secondPressDateStr;
    }

    public String getSecondPressType() {
        return secondPressType;
    }

    public void setSecondPressType(String secondPressType) {
        this.secondPressType = secondPressType;
    }

    public String getApplyDateStr() {
        return applyDateStr;
    }

    public void setApplyDateStr(String applyDateStr) {
        this.applyDateStr = applyDateStr;
    }

    public String getApproveDateStr() {
        return approveDateStr;
    }

    public void setApproveDateStr(String approveDateStr) {
        this.approveDateStr = approveDateStr;
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

    public String getPerformEnforceDateStr() {
        return performEnforceDateStr;
    }

    public void setPerformEnforceDateStr(String performEnforceDateStr) {
        this.performEnforceDateStr = performEnforceDateStr;
    }

    public String getDecideSendDateStr() {
        return decideSendDateStr;
    }

    public void setDecideSendDateStr(String decideSendDateStr) {
        this.decideSendDateStr = decideSendDateStr;
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

    public String getTransNoticeDateStr() {
        return transNoticeDateStr;
    }

    public void setTransNoticeDateStr(String transNoticeDateStr) {
        this.transNoticeDateStr = transNoticeDateStr;
    }

    public String getAuctionAddr() {
        return auctionAddr;
    }

    public void setAuctionAddr(String auctionAddr) {
        this.auctionAddr = auctionAddr;
    }

    public String getAuctionDateStr() {
        return auctionDateStr;
    }

    public void setAuctionDateStr(String auctionDateStr) {
        this.auctionDateStr = auctionDateStr;
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

    public String getReplaceEnforceDateStr() {
        return replaceEnforceDateStr;
    }

    public void setReplaceEnforceDateStr(String replaceEnforceDateStr) {
        this.replaceEnforceDateStr = replaceEnforceDateStr;
    }

    public Integer getIsAgreementEnforce() {
        return isAgreementEnforce;
    }

    public void setIsAgreementEnforce(Integer isAgreementEnforce) {
        this.isAgreementEnforce = isAgreementEnforce;
    }

    public String getAgreeSignDateStr() {
        return agreeSignDateStr;
    }

    public void setAgreeSignDateStr(String agreeSignDateStr) {
        this.agreeSignDateStr = agreeSignDateStr;
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

    public String getEnforceReturnDateStr() {
        return enforceReturnDateStr;
    }

    public void setEnforceReturnDateStr(String enforceReturnDateStr) {
        this.enforceReturnDateStr = enforceReturnDateStr;
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

    public String getEndEnforceSendDateStr() {
        return endEnforceSendDateStr;
    }

    public void setEndEnforceSendDateStr(String endEnforceSendDateStr) {
        this.endEnforceSendDateStr = endEnforceSendDateStr;
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

    public String getBreakEnforceDateStr() {
        return breakEnforceDateStr;
    }

    public void setBreakEnforceDateStr(String breakEnforceDateStr) {
        this.breakEnforceDateStr = breakEnforceDateStr;
    }

    public String getRelwaseEnforceDateStr() {
        return relwaseEnforceDateStr;
    }

    public void setRelwaseEnforceDateStr(String relwaseEnforceDateStr) {
        this.relwaseEnforceDateStr = relwaseEnforceDateStr;
    }

    public String getEnforceBreakReason() {
        return enforceBreakReason;
    }

    public void setEnforceBreakReason(String enforceBreakReason) {
        this.enforceBreakReason = enforceBreakReason;
    }

    public String getPerformType() {
        return performType;
    }

    public void setPerformType(String performType) {
        this.performType = performType;
    }

    public Integer getCaseSourceType() {
        return caseSourceType;
    }

    public void setCaseSourceType(Integer caseSourceType) {
        this.caseSourceType = caseSourceType;
    }

    public String getCaseSourceId() {
        return caseSourceId;
    }

    public void setCaseSourceId(String caseSourceId) {
        this.caseSourceId = caseSourceId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getPowerJsonStr() {
        return powerJsonStr;
    }

    public void setPowerJsonStr(String powerJsonStr) {
        this.powerJsonStr = powerJsonStr;
    }

    public String getGistJsonStr() {
        return gistJsonStr;
    }

    public void setGistJsonStr(String gistJsonStr) {
        this.gistJsonStr = gistJsonStr;
    }

    public Integer getEnforceStep() {
        return enforceStep;
    }

    public void setEnforceStep(Integer enforceStep) {
        this.enforceStep = enforceStep;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
}
