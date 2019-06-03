package com.beidasoft.zfjd.adminCoercion.model;

/**
 * 强制措施管理MODEL类
 */
public class CoercionMeasureModel {
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

    // 强制措施类型
    private String measureType;

    // 强制措施类型文本描述
    private String measureTypeStr;

    // 申请日期
    private String applyDateStr;

    // 批准日期
    private String approveDateStr;

    // 批准人
    private String approvePerson;

    // 强制措施决定书文号
    private String coercionCode;

    // 强制措施决定书送达日期
    private String cdSendDateStr;

    // 强制措施决定书送达方式
    private String cdSendType;

    // 强制措施决定书送达方式
    private String cdSendTypeStr;
    
    // 强制措施期限（起）
    private String measureDateLimitStartStr;

    // 强制措施期限（止）
    private String measureDateLimitEndStr;

    // 实施强制措施日期
    private String measureEnforceDateStr;

    // 是否延期
    private Integer isDelayLimit;

    // 强制措施延长期限（止）
    private String measureDelayDateEndStr;

    // 当事人是否在场（1是，2否）
    private Integer isPartyPresent;

    // 强制对象（查封）
    private String measureTargetType;

    // 查封设施、场所名称
    private String closeDownPlaceName;

    // 查封设施、场所地址
    private String closeDownPlaceAddr;

    // 见证人名称
    private String witnessName;

    // 见证人联系方式
    private String witnessContactWay;

    // 查封财物名称、数量、金额详情
    private String closeDownPropertyInfo;

    // 查封原因
    private String closeDownReason;

    // (扣押)当事人是否在场（1是，2否）
    private Integer isDetentionPartyPresent;

    // 扣押原因
    private String detentionReason;

    // (扣押)见证人名称
    private String detentionWitnessName;

    // (扣押)见证人联系方式
    private String detentionWitnessContactWay;

    // 扣押财物名称、数量、金额详情
    private String detentionPropertyInfo;

    // 冻结金额
    private Integer freezeNumber;

    // 金融机构名称
    private String freezeOrganization;

    // 通知冻结日期
    private String freezeNoticeDateStr;

    // 环节状态
    private Integer enforceStep;

    // 是否通知家属及所在单位
    private Integer isNoticeFamily;

    // 未通知原因
    private String notNoticeReason;

    // 强制措施处理决定
    private String measureDealWay;

    // 主表强制案件来源类型
    private Integer caseSourceType;

    // 主表强制案件来源id
    private String caseSourceId;

    private String subjectId;
    
    private String departmentId;

    // 职权信息json字符串
    private String powerJsonStr;

    // 依据信息json字符串
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

    public String getCoercionCode() {
        return coercionCode;
    }

    public void setCoercionCode(String coercionCode) {
        this.coercionCode = coercionCode;
    }

    public String getCdSendDateStr() {
        return cdSendDateStr;
    }

    public void setCdSendDateStr(String cdSendDateStr) {
        this.cdSendDateStr = cdSendDateStr;
    }

    public String getCdSendType() {
        return cdSendType;
    }

    public void setCdSendType(String cdSendType) {
        this.cdSendType = cdSendType;
    }

    public String getMeasureDateLimitStartStr() {
        return measureDateLimitStartStr;
    }

    public void setMeasureDateLimitStartStr(String measureDateLimitStartStr) {
        this.measureDateLimitStartStr = measureDateLimitStartStr;
    }

    public String getMeasureDateLimitEndStr() {
        return measureDateLimitEndStr;
    }

    public void setMeasureDateLimitEndStr(String measureDateLimitEndStr) {
        this.measureDateLimitEndStr = measureDateLimitEndStr;
    }

    public String getMeasureEnforceDateStr() {
        return measureEnforceDateStr;
    }

    public void setMeasureEnforceDateStr(String measureEnforceDateStr) {
        this.measureEnforceDateStr = measureEnforceDateStr;
    }

    public Integer getIsDelayLimit() {
        return isDelayLimit;
    }

    public void setIsDelayLimit(Integer isDelayLimit) {
        this.isDelayLimit = isDelayLimit;
    }

    public String getMeasureDelayDateEndStr() {
        return measureDelayDateEndStr;
    }

    public void setMeasureDelayDateEndStr(String measureDelayDateEndStr) {
        this.measureDelayDateEndStr = measureDelayDateEndStr;
    }

    public Integer getIsPartyPresent() {
        return isPartyPresent;
    }

    public void setIsPartyPresent(Integer isPartyPresent) {
        this.isPartyPresent = isPartyPresent;
    }

    public String getMeasureTargetType() {
        return measureTargetType;
    }

    public void setMeasureTargetType(String measureTargetType) {
        this.measureTargetType = measureTargetType;
    }

    public String getCloseDownPlaceName() {
        return closeDownPlaceName;
    }

    public void setCloseDownPlaceName(String closeDownPlaceName) {
        this.closeDownPlaceName = closeDownPlaceName;
    }

    public String getCloseDownPlaceAddr() {
        return closeDownPlaceAddr;
    }

    public void setCloseDownPlaceAddr(String closeDownPlaceAddr) {
        this.closeDownPlaceAddr = closeDownPlaceAddr;
    }

    public String getWitnessName() {
        return witnessName;
    }

    public void setWitnessName(String witnessName) {
        this.witnessName = witnessName;
    }

    public String getWitnessContactWay() {
        return witnessContactWay;
    }

    public void setWitnessContactWay(String witnessContactWay) {
        this.witnessContactWay = witnessContactWay;
    }

    public String getCloseDownPropertyInfo() {
        return closeDownPropertyInfo;
    }

    public void setCloseDownPropertyInfo(String closeDownPropertyInfo) {
        this.closeDownPropertyInfo = closeDownPropertyInfo;
    }

    public String getCloseDownReason() {
        return closeDownReason;
    }

    public void setCloseDownReason(String closeDownReason) {
        this.closeDownReason = closeDownReason;
    }

    public Integer getFreezeNumber() {
        return freezeNumber;
    }

    public void setFreezeNumber(Integer freezeNumber) {
        this.freezeNumber = freezeNumber;
    }

    public String getFreezeOrganization() {
        return freezeOrganization;
    }

    public void setFreezeOrganization(String freezeOrganization) {
        this.freezeOrganization = freezeOrganization;
    }

    public String getFreezeNoticeDateStr() {
        return freezeNoticeDateStr;
    }

    public void setFreezeNoticeDateStr(String freezeNoticeDateStr) {
        this.freezeNoticeDateStr = freezeNoticeDateStr;
    }

    public Integer getEnforceStep() {
        return enforceStep;
    }

    public void setEnforceStep(Integer enforceStep) {
        this.enforceStep = enforceStep;
    }

    public String getMeasureType() {
        return measureType;
    }

    public void setMeasureType(String measureType) {
        this.measureType = measureType;
    }

    public String getMeasureTypeStr() {
        return measureTypeStr;
    }

    public void setMeasureTypeStr(String measureTypeStr) {
        this.measureTypeStr = measureTypeStr;
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

    public Integer getIsNoticeFamily() {
        return isNoticeFamily;
    }

    public void setIsNoticeFamily(Integer isNoticeFamily) {
        this.isNoticeFamily = isNoticeFamily;
    }

    public String getNotNoticeReason() {
        return notNoticeReason;
    }

    public void setNotNoticeReason(String notNoticeReason) {
        this.notNoticeReason = notNoticeReason;
    }

    public Integer getIsDetentionPartyPresent() {
        return isDetentionPartyPresent;
    }

    public void setIsDetentionPartyPresent(Integer isDetentionPartyPresent) {
        this.isDetentionPartyPresent = isDetentionPartyPresent;
    }

    public String getDetentionReason() {
        return detentionReason;
    }

    public void setDetentionReason(String detentionReason) {
        this.detentionReason = detentionReason;
    }

    public String getDetentionWitnessName() {
        return detentionWitnessName;
    }

    public void setDetentionWitnessName(String detentionWitnessName) {
        this.detentionWitnessName = detentionWitnessName;
    }

    public String getDetentionWitnessContactWay() {
        return detentionWitnessContactWay;
    }

    public void setDetentionWitnessContactWay(String detentionWitnessContactWay) {
        this.detentionWitnessContactWay = detentionWitnessContactWay;
    }

    public String getDetentionPropertyInfo() {
        return detentionPropertyInfo;
    }

    public void setDetentionPropertyInfo(String detentionPropertyInfo) {
        this.detentionPropertyInfo = detentionPropertyInfo;
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

    public String getMeasureDealWay() {
        return measureDealWay;
    }

    public void setMeasureDealWay(String measureDealWay) {
        this.measureDealWay = measureDealWay;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

	public String getCdSendTypeStr() {
		return cdSendTypeStr;
	}

	public void setCdSendTypeStr(String cdSendTypeStr) {
		this.cdSendTypeStr = cdSendTypeStr;
	}

}
