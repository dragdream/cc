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
 * 强制措施管理实体类
 */
@Entity
@Table(name="TBL_COERCION_CASE_MEASURE")
public class CoercionMeasure {
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

    // 强制措施类型
    @Column(name = "MEASURE_TYPE")
    private String measureType;

    // 申请日期
    @Column(name = "APPLY_DATE")
    private Date applyDate;

    // 批准日期
    @Column(name = "APPROVE_DATE")
    private Date approveDate;

    // 批准人
    @Column(name = "APPROVE_PERSON")
    private String approvePerson;

    // 强制措施决定书文号
    @Column(name = "COERCION_CODE")
    private String coercionCode;

    // 强制措施决定书送达日期
    @Column(name = "CD_SEND_DATE")
    private Date cdSendDate;

    // 强制措施决定书送达方式
    @Column(name = "CD_SEND_TYPE")
    private String cdSendType;

    // 强制措施期限（起）
    @Column(name = "MEASURE_DATE_LIMIT_START")
    private Date measureDateLimitStart;

    // 强制措施期限（止）
    @Column(name = "MEASURE_DATE_LIMIT_END")
    private Date measureDateLimitEnd;

    // 实施强制措施日期
    @Column(name = "MEASURE_ENFORCE_DATE")
    private Date measureEnforceDate;

    // 是否延期
    @Column(name = "IS_DELAY_LIMIT")
    private Integer isDelayLimit;

    // 强制措施延长期限（止）
    @Column(name = "MEASURE_DELAY_DATE_END")
    private Date measureDelayDateEnd;

    // 当事人是否在场（1是，2否）
    @Column(name = "IS_PARTY_PRESENT")
    private Integer isPartyPresent;

    // 强制对象（查封）
    @Column(name = "MEASURE_TARGET_TYPE")
    private String measureTargetType;

    // 查封设施、场所名称
    @Column(name = "CLOSE_DOWN_PLACE_NAME")
    private String closeDownPlaceName;

    // 查封设施、场所地址
    @Column(name = "CLOSE_DOWN_PLACE_ADDR")
    private String closeDownPlaceAddr;

    // 见证人名称
    @Column(name = "WITNESS_NAME")
    private String witnessName;

    // 见证人联系方式
    @Column(name = "WITNESS_CONTACT_WAY")
    private String witnessContactWay;

    // 查封、扣押财物名称、数量、金额详情
    @Column(name = "CLOSE_DOWN_PROPERTY_INFO")
    private String closeDownPropertyInfo;

    // (扣押)当事人是否在场（1是，2否）
    @Column(name = "IS_DETENTION_PARTY_PRESENT")
    private Integer isDetentionPartyPresent;
    
    // 扣押原因
    @Column(name = "DETENTION_REASON")
    private String detentionReason;
    
    // 强制措施处理决定
    @Column(name = "MEASURE_DEAL_WAY")
    private String measureDealWay;

    // (扣押)见证人名称
    @Column(name = "DETENTION_WITNESS_NAME")
    private String detentionWitnessName;

    // (扣押)见证人联系方式
    @Column(name = "DETENTION_WITNESS_CONTACT_WAY")
    private String detentionWitnessContactWay;

    // 扣押财物名称、数量、金额详情
    @Column(name = "DETENTION_PROPERTY_INFO")
    private String detentionPropertyInfo;

    // 查封、扣押原因
    @Column(name = "CLOSE_DOWN_REASON")
    private String closeDownReason;
    // 冻结金额
    @Column(name = "FREEZE_NUMBER")
    private Integer freezeNumber;

    // 金融机构名称
    @Column(name = "FREEZE_ORGANIZATION")
    private String freezeOrganization;

    // 通知冻结日期
    @Column(name = "FREEZE_NOTICE_DATE")
    private Date freezeNoticeDate;
    
    //环节状态
    @Column(name = "ENFORCE_STEP")
    private Integer enforceStep;
    
    //是否通知家属及所在单位
    @Column(name = "IS_NOTICE_FAMILY")
    private Integer isNoticeFamily;
    
    //未通知原因
    @Column(name = "NOT_NOTICE_REASON")
    private String notNoticeReason;
    
    @OneToMany(mappedBy = "coercionMeasureGist", fetch = FetchType.LAZY) // 双向,  目标写@JoinColumn 
    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    @OrderBy("lawName ASC")
    private List<CoercionCaseGist> gists;
    
    @OneToMany(mappedBy = "coercionMeasurePower", fetch = FetchType.LAZY) // 双向,  目标写@JoinColumn 
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
 
    public String getMeasureType() {
        return measureType;
    }

    public void setMeasureType(String measureType) {
        this.measureType = measureType;
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

    public String getCoercionCode() {
        return coercionCode;
    }

    public void setCoercionCode(String coercionCode) {
        this.coercionCode = coercionCode;
    }

    public Date getCdSendDate() {
        return cdSendDate;
    }

    public void setCdSendDate(Date cdSendDate) {
        this.cdSendDate = cdSendDate;
    }

    public String getCdSendType() {
        return cdSendType;
    }

    public void setCdSendType(String cdSendType) {
        this.cdSendType = cdSendType;
    }

    public Date getMeasureDateLimitStart() {
        return measureDateLimitStart;
    }

    public void setMeasureDateLimitStart(Date measureDateLimitStart) {
        this.measureDateLimitStart = measureDateLimitStart;
    }

    public Date getMeasureDateLimitEnd() {
        return measureDateLimitEnd;
    }

    public void setMeasureDateLimitEnd(Date measureDateLimitEnd) {
        this.measureDateLimitEnd = measureDateLimitEnd;
    }

    public Date getMeasureEnforceDate() {
        return measureEnforceDate;
    }

    public void setMeasureEnforceDate(Date measureEnforceDate) {
        this.measureEnforceDate = measureEnforceDate;
    }

    public Integer getIsDelayLimit() {
        return isDelayLimit;
    }

    public void setIsDelayLimit(Integer isDelayLimit) {
        this.isDelayLimit = isDelayLimit;
    }

    public Date getMeasureDelayDateEnd() {
        return measureDelayDateEnd;
    }

    public void setMeasureDelayDateEnd(Date measureDelayDateEnd) {
        this.measureDelayDateEnd = measureDelayDateEnd;
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

    public Date getFreezeNoticeDate() {
        return freezeNoticeDate;
    }

    public void setFreezeNoticeDate(Date freezeNoticeDate) {
        this.freezeNoticeDate = freezeNoticeDate;
    }

    public Integer getEnforceStep() {
        return enforceStep;
    }

    public void setEnforceStep(Integer enforceStep) {
        this.enforceStep = enforceStep;
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

    public String getMeasureDealWay() {
        return measureDealWay;
    }

    public void setMeasureDealWay(String measureDealWay) {
        this.measureDealWay = measureDealWay;
    }
    
}
