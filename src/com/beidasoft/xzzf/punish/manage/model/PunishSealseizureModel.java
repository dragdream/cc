package com.beidasoft.xzzf.punish.manage.model;


/**
 * 查封扣押管理model类
 */
public class PunishSealseizureModel {

	// 查封扣押表管理主键
    private String id;
    
    // 案件唯一标识
    private String baseId;
    
    // 查封扣押决定书ID
    private String DocSealseizureId;
    
    // 查封扣押决定书文号
    private String docSealseizureCode;
    
    // 查封期限起
    private String processDecisionDateStartStr; //YYYY-MM-DD
    
    // 查封期限末
    private String processDecisionDateEndStr;//YYYY-MM-DD
    
    // 主办人ID
    private Integer userId;
    
    // 主办人姓名
    private String userName;
    
    // 是否送鉴定
    private Integer isAppraisal;//默认0，0:否，1：是
    
    // 鉴定时间（开始）
    private String checkupDateStartStr;//YYYY-MM-DD
    
    // 鉴定时间（结束）
    private String checkupDateEndStr;//YYYY-MM-DD
    
    // 鉴定结果附件
    private Integer attachId;//只有本文件上传后，才可以结束鉴定。并从鉴定结束日期开始重新计算扣押时间
    
    // 是否延期
    private Integer isDelay;
    
    // 延期到（时间）
    private String delayDateStr;
    
    // 物品清单ID
    private String articlesMainId;
    
    // 物品清单文号
    private String ArticlesMainCode;

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
		return DocSealseizureId;
	}

	public void setDocSealseizureId(String docSealseizureId) {
		DocSealseizureId = docSealseizureId;
	}

	public String getDocSealseizureCode() {
		return docSealseizureCode;
	}

	public void setDocSealseizureCode(String docSealseizureCode) {
		this.docSealseizureCode = docSealseizureCode;
	}

	public String getProcessDecisionDateStartStr() {
		return processDecisionDateStartStr;
	}

	public void setProcessDecisionDateStartStr(String processDecisionDateStartStr) {
		this.processDecisionDateStartStr = processDecisionDateStartStr;
	}

	public String getProcessDecisionDateEndStr() {
		return processDecisionDateEndStr;
	}

	public void setProcessDecisionDateEndStr(String processDecisionDateEndStr) {
		this.processDecisionDateEndStr = processDecisionDateEndStr;
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

	public String getCheckupDateStartStr() {
		return checkupDateStartStr;
	}

	public void setCheckupDateStartStr(String checkupDateStartStr) {
		this.checkupDateStartStr = checkupDateStartStr;
	}

	public String getCheckupDateEndStr() {
		return checkupDateEndStr;
	}

	public void setCheckupDateEndStr(String checkupDateEndStr) {
		this.checkupDateEndStr = checkupDateEndStr;
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

	public String getDelayDateStr() {
		return delayDateStr;
	}

	public void setDelayDateStr(String delayDateStr) {
		this.delayDateStr = delayDateStr;
	}

	public String getArticlesMainId() {
		return articlesMainId;
	}

	public void setArticlesMainId(String articlesMainId) {
		this.articlesMainId = articlesMainId;
	}

	public String getArticlesMainCode() {
		return ArticlesMainCode;
	}

	public void setArticlesMainCode(String articlesMainCode) {
		ArticlesMainCode = articlesMainCode;
	}

	
}
