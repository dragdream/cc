package com.beidasoft.xzzf.punish.document.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 案件集体讨论记录实体类
 */
@Entity
@Table(name="ZF_DOC_GROUP_DISCUSSION")
public class DocGroupDiscussion {
    // 案件集体讨论记录主键ID
    @Id
    @Column(name = "ID")
    private String id;

    // 案件名称
    @Column(name = "CASE_NAME")
    private String caseName;

    // 讨论开始时间
    @Column(name = "DISCUSSION_TIME_START")
    private Date discussionTimeStart;

    // 讨论结束时间
    @Column(name = "DISCUSSION_TIME_END")
    private Date discussionTimeEnd;

    // 讨论地点
    @Column(name = "DISCUSSION_PLACE")
    private String discussionPlace;

    // 主持人名
    @Column(name = "COMPERE_NAME")
    private String compereName;

    // 主持人职务
    @Column(name = "COMPERE_DUTY")
    private String compereDuty;

    // 汇报人名
    @Column(name = "REPORTER_NAME")
    private String reporterName;

    // 记录人名
    @Column(name = "RECORDER_NAME")
    private String recorderName;

    // 参与人员名集合
    @Column(name = "PARTICIPATION_NAMES")
    private String participationNames;

    // 参与人员职务集合
    @Column(name = "PARTICIPATION_DUTIES")
    private String participationDuties;
    
    // 参与人员对象字符串
    @Lob
    @Column(name = "PARTICIPATION_STR")
    private String participationStr;

    // 案由
    @Column(name = "CASUSE_REASON")
    private String casuseReason;

    // 承办人员介绍的基本案情和处罚建议
    @Column(name = "ORGAN_CASE_OR_ADVICE")
    private String organCaseOrAdvice;

    // 法制部门介绍审核意见
    @Column(name = "LAW_AUDIT_OPINION")
    private String lawAuditOpinion;

    // 讨论记录
    @Column(name = "RECORD_DISCUSSION")
    private String recordDiscussion;

    // 集体讨论结论性意见
    @Column(name = "ADVICE_CONCLUSION")
    private String adviceConclusion;

    // 附件地址
    @Column(name = "ENCLOSURE_ADDRESS")
    private String enclosureAddress;

    // 删除标记
    @Column(name = "DEL_FLG")
    private String delFlg;

    // 执法办案唯一编号
    @Column(name = "BASE_ID")
    private String baseId;

    // 执法环节ID
    @Column(name = "LAW_LINK_ID")
    private String lawLinkId;

    // 创建人ID
    @Column(name = "CREATE_USER_ID")
    private String createUserId;

    // 创建人姓名
    @Column(name = "CREATE_USER_NAME")
    private String createUserName;

    // 创建时间
    @Column(name = "CREATE_TIME")
    private Date createTime;

    // 变更人ID
    @Column(name = "UPDATE_USER_ID")
    private String updateUserId;

    // 变更人姓名
    @Column(name = "UPDATE_USER_NAME")
    private String updateUserName;

    // 变更时间
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public Date getDiscussionTimeStart() {
        return discussionTimeStart;
    }

    public void setDiscussionTimeStart(Date discussionTimeStart) {
        this.discussionTimeStart = discussionTimeStart;
    }

    public Date getDiscussionTimeEnd() {
        return discussionTimeEnd;
    }

    public void setDiscussionTimeEnd(Date discussionTimeEnd) {
        this.discussionTimeEnd = discussionTimeEnd;
    }

    public String getDiscussionPlace() {
        return discussionPlace;
    }

    public void setDiscussionPlace(String discussionPlace) {
        this.discussionPlace = discussionPlace;
    }

    public String getCompereName() {
        return compereName;
    }

    public void setCompereName(String compereName) {
        this.compereName = compereName;
    }

    public String getCompereDuty() {
        return compereDuty;
    }

    public void setCompereDuty(String compereDuty) {
        this.compereDuty = compereDuty;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public String getRecorderName() {
        return recorderName;
    }

    public void setRecorderName(String recorderName) {
        this.recorderName = recorderName;
    }

    public String getParticipationNames() {
        return participationNames;
    }

    public void setParticipationNames(String participationNames) {
        this.participationNames = participationNames;
    }

    public String getParticipationDuties() {
        return participationDuties;
    }

    public void setParticipationDuties(String participationDuties) {
        this.participationDuties = participationDuties;
    }

    public String getParticipationStr() {
		return participationStr;
	}

	public void setParticipationStr(String participationStr) {
		this.participationStr = participationStr;
	}

	public String getCasuseReason() {
        return casuseReason;
    }

    public void setCasuseReason(String casuseReason) {
        this.casuseReason = casuseReason;
    }

    public String getOrganCaseOrAdvice() {
        return organCaseOrAdvice;
    }

    public void setOrganCaseOrAdvice(String organCaseOrAdvice) {
        this.organCaseOrAdvice = organCaseOrAdvice;
    }

    public String getLawAuditOpinion() {
        return lawAuditOpinion;
    }

    public void setLawAuditOpinion(String lawAuditOpinion) {
        this.lawAuditOpinion = lawAuditOpinion;
    }

    public String getRecordDiscussion() {
        return recordDiscussion;
    }

    public void setRecordDiscussion(String recordDiscussion) {
        this.recordDiscussion = recordDiscussion;
    }

    public String getAdviceConclusion() {
        return adviceConclusion;
    }

    public void setAdviceConclusion(String adviceConclusion) {
        this.adviceConclusion = adviceConclusion;
    }

    public String getEnclosureAddress() {
        return enclosureAddress;
    }

    public void setEnclosureAddress(String enclosureAddress) {
        this.enclosureAddress = enclosureAddress;
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    public String getLawLinkId() {
        return lawLinkId;
    }

    public void setLawLinkId(String lawLinkId) {
        this.lawLinkId = lawLinkId;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
