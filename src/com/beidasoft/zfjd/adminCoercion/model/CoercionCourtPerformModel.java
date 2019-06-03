package com.beidasoft.zfjd.adminCoercion.model;

/**
 * 法院强制执行信息MODEL类
 */
public class CoercionCourtPerformModel {
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

    // 法院强制执行方式
    private String performType;

    // 原处罚决定书文号
    private String punishCodeBefore;

    // 原处罚决定书日期
    private String punishDateBeforeStr;

    // 催告书送达日期
    private String pressSendDateStr;

    // 催告书送达方式
    private String pressSendType;

    // 原处罚金额
    private int originalMoney;

    // 原处罚日期
    private String originalDateStr;

    // 加处罚款
    private int addFindMoney;

    // 二次催告日期
    private String secondPressDateStr;

    // 申请法院强制执行日期
    private String applyDateStr;

    // 批准日期
    private String approveDateStr;

    // 二次催告送达方式
    private String secondPressType;

    // 执行标的情况
    private String enforceElementCondition;

    // 是否进行二次催告
    private Integer isSecondPress;
    
    // 主表强制案件来源类型
    private Integer caseSourceType;

    // 主表强制案件来源id
    private String caseSourceId;

    // 主体id
    private String subjectId;
    
    // 主体id
    private String departmentId;
    
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

    public int getOriginalMoney() {
        return originalMoney;
    }

    public void setOriginalMoney(int originalMoney) {
        this.originalMoney = originalMoney;
    }

    public String getOriginalDateStr() {
        return originalDateStr;
    }

    public void setOriginalDateStr(String originalDateStr) {
        this.originalDateStr = originalDateStr;
    }

    public int getAddFindMoney() {
        return addFindMoney;
    }

    public void setAddFindMoney(int addFindMoney) {
        this.addFindMoney = addFindMoney;
    }

    public String getSecondPressDateStr() {
        return secondPressDateStr;
    }

    public void setSecondPressDateStr(String secondPressDateStr) {
        this.secondPressDateStr = secondPressDateStr;
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

    public Integer getIsSecondPress() {
        return isSecondPress;
    }

    public void setIsSecondPress(Integer isSecondPress) {
        this.isSecondPress = isSecondPress;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
}
