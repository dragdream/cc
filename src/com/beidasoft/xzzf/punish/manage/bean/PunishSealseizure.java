package com.beidasoft.xzzf.punish.manage.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 查封扣押管理实体类
 */
@Entity
@Table(name = "ZF_PUNISH_SEALSEIZURE")
public class PunishSealseizure {

	// 查封扣押表管理主键
    @Id
    @Column(name = "PUNISH_SEALSEIZURE_ID")
    private String id;
    
    // 案件唯一标识
    @Column(name = "BASE_ID")
    private String baseId;
    
    // 查封扣押决定书ID
    @Column(name = "DOC_SEALSEIZURE_ID")
    private String docSealseizureId;
    
    // 查封扣押决定书文号
    @Column(name = "DOC_SEALSEIZURE_CODE")
    private String docSealseizureCode;
    
    // 查封期限起
    @Column(name = "PROCESS_DECISION_DATE_START")
    private Date processDecisionDateStart;
    
    // 查封期限末
    @Column(name = "PROCESS_DECISION_DATE_END")
    private Date processDecisionDateEnd;
    
    // 主办人ID
    @Column(name = "USER_ID")
    private Integer userId;
    
    // 主办人姓名
    @Column(name = "USER_NAME")
    private String userName;
    
    // 是否送鉴定
    @Column(name = "IS_APPRAISAL")
    private Integer isAppraisal;
    
    // 鉴定时间（开始）
    @Column(name = "CHECKUP_DATE_START")
    private Date checkupDateStart;
    
    // 鉴定时间（结束）
    @Column(name = "CHECKUP_DATE_END")
    private Date checkupDateEnd;
    
    // 鉴定结果附件
    @Column(name = "ATTACH_ID")
    private Integer attachId;
    
    // 是否延期
    @Column(name = "IS_DELAY")
    private Integer isDelay;
    
    // 延期到（时间）
    @Column(name = "DELAY_DATE")
    private Date delayDate;
    
    // 物品清单ID
    @Column(name = "ARTICLES_MAIN_ID")
    private String articlesMainId;
    
    // 物品清单文号
    @Column(name = "ARTICLES_MAIN_CODE")
    private String articlesMainCode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}

	public String getDocSealseizureId() {
		return docSealseizureId;
	}

	public void setDocSealseizureId(String docSealseizureId) {
		this.docSealseizureId = docSealseizureId;
	}

	public String getDocSealseizureCode() {
		return docSealseizureCode;
	}

	public void setDocSealseizureCode(String docSealseizureCode) {
		this.docSealseizureCode = docSealseizureCode;
	}

	public Date getProcessDecisionDateStart() {
		return processDecisionDateStart;
	}

	public void setProcessDecisionDateStart(Date processDecisionDateStart) {
		this.processDecisionDateStart = processDecisionDateStart;
	}

	public Date getProcessDecisionDateEnd() {
		return processDecisionDateEnd;
	}

	public void setProcessDecisionDateEnd(Date processDecisionDateEnd) {
		this.processDecisionDateEnd = processDecisionDateEnd;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getIsAppraisal() {
		return isAppraisal;
	}

	public void setIsAppraisal(Integer isAppraisal) {
		this.isAppraisal = isAppraisal;
	}

	public Date getCheckupDateStart() {
		return checkupDateStart;
	}

	public void setCheckupDateStart(Date checkupDateStart) {
		this.checkupDateStart = checkupDateStart;
	}

	public Date getCheckupDateEnd() {
		return checkupDateEnd;
	}

	public void setCheckupDateEnd(Date checkupDateEnd) {
		this.checkupDateEnd = checkupDateEnd;
	}

	public Integer getAttachId() {
		return attachId;
	}

	public void setAttachId(Integer attachId) {
		this.attachId = attachId;
	}

	public Integer getIsDelay() {
		return isDelay;
	}

	public void setIsDelay(Integer isDelay) {
		this.isDelay = isDelay;
	}

	public Date getDelayDate() {
		return delayDate;
	}

	public void setDelayDate(Date delayDate) {
		this.delayDate = delayDate;
	}

	public String getArticlesMainId() {
		return articlesMainId;
	}

	public void setArticlesMainId(String articlesMainId) {
		this.articlesMainId = articlesMainId;
	}

	public String getArticlesMainCode() {
		return articlesMainCode;
	}

	public void setArticlesMainCode(String articlesMainCode) {
		this.articlesMainCode = articlesMainCode;
	}

	
    
}
