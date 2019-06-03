package com.beidasoft.xzzf.punish.document.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.beidasoft.xzzf.punish.document.bean.DocDiscussionPersonSignature;


public class GroupDiscussionModel {

	  // 案件集体讨论记录主键ID
    private String id;

    // 案件名称
    private String caseName;

    // 讨论开始时间
    private String discussionTimeStartStr;

    // 讨论结束时间
    private String discussionTimeEndStr;

    // 讨论地点
    private String discussionPlace;

    // 主持人名
    private String compereName;

    // 主持人职务
    private String compereDuty;

    // 汇报人名
    private String reporterName;

    // 记录人名
    private String recorderName;

    // 参与人员名集合
    private String participationNames;

    // 参与人员职务集合
    private String participationDuties;
    
    // 参与人员字符串
    private String participationStr;

    // 案由
    private String casuseReason;

    // 承办人员介绍的基本案情和处罚建议
    private String organCaseOrAdvice;

    // 法制部门介绍审核意见
    private String lawAuditOpinion;

    // 讨论记录
    private String recordDiscussion;

    // 集体讨论结论性意见
    private String adviceConclusion;

    // 附件地址
    private String enclosureAddress;

    // 删除标记
    private String delFlg;

    // 执法办案唯一编号
    private String baseId;

    // 执法环节ID
    private String lawLinkId;

    // 变更人ID
    private String updateUserId;

    // 变更人姓名
    private String updateUserName;

    // 变更时间
    private String updateTimeStr;

    //签名数据字符串
    private String signObjStr;
    
	private List<DocDiscussionPersonSignature> listDiscussionPersonSignature;
    
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

	
	public String getDiscussionTimeStartStr() {
		return discussionTimeStartStr;
	}

	public void setDiscussionTimeStartStr(String discussionTimeStartStr) {
		this.discussionTimeStartStr = discussionTimeStartStr;
	}

	public String getDiscussionTimeEndStr() {
		return discussionTimeEndStr;
	}

	public void setDiscussionTimeEndStr(String discussionTimeEndStr) {
		this.discussionTimeEndStr = discussionTimeEndStr;
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

	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

	public String getSignObjStr() {
		return signObjStr;
	}

	public void setSignObjStr(String signObjStr) {
		this.signObjStr = signObjStr;
	}

	public List<DocDiscussionPersonSignature> getListDiscussionPersonSignature() {
		return listDiscussionPersonSignature;
	}

	public void setListDiscussionPersonSignature(
			List<DocDiscussionPersonSignature> listDiscussionPersonSignature) {
		this.listDiscussionPersonSignature = listDiscussionPersonSignature;
	}

			//预览
			public Map<String, String> getDocInfo(String caseCode) {
				
				// 文书内容
				Map<String, String> content = new HashMap<String, String>();
				//案件名称
				content.put("案件名称", caseName);
				
				//讨论开始时间
				content.put("讨论开始时间", discussionTimeStartStr);
						
				//讨论结束时间
				content.put("讨论结束时间", discussionTimeEndStr);
				
				//讨论地点
				content.put("讨论地点", discussionPlace);
				
				//主持人
				content.put("主持人", compereName);
				
				//汇报人
				content.put("汇报人", reporterName);
//				
//				//记录人
				content.put("记录人", recorderName);
				
				//案由
				content.put("案由", casuseReason);
				
				//案情及处罚
				content.put("案情及处罚", organCaseOrAdvice);
				
				//审核意见
				content.put("审核意见", lawAuditOpinion);

				// 讨论记录
				content.put("讨论记录", recordDiscussion);
				
				// 讨论结论性解决
				content.put("讨论结论性意见", adviceConclusion);
				
				// 参加讨论人员签名
				content.put("参加讨论人员签名", signObjStr);
				
				// 页眉
				content.put("页眉", caseCode);
				
				return content;
			}
}
