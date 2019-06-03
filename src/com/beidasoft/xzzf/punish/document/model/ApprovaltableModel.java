package com.beidasoft.xzzf.punish.document.model;

import java.util.HashMap;
import java.util.Map;
import com.beidasoft.xzzf.punish.document.util.DocCommonUtils;

public class ApprovaltableModel {

    private String id;
    // 当事人姓名
    private String leadingName;
    // 当事人名称
    private String partyName;

    // 名称
    private String partyType;

    // 当事人电话
    private String partyPhone;

    // 当事人住址
    private String partyAddress;

    // 审批事项
    private String approvalMatter;

    // 案件来源
    private String caseSource;

    // 案由
    private String causeAction;

    // 案由名
    private String causeActionName;
    
    // 案情概要
    private String caseOverview;

    // 具体审批事项
    private String detailApprovalMatter;

    // 承办人员意见
    private String undertakePersonalIdea;

    // 承办人员主办人签名图片
    private String majorSignatureBase64;

    // 承办人员主办人签名值
    private String majorSignatureValue;

    // 承办人员主办人签名位置
    private String majorSignaturePlace;

    // 承办人员协办人签名图片
    private String minorSignatureBase64;

    // 承办人员协办人签名值
    private String minorSignatureValue;

    // 承办人员协办人签名位置
    private String minorSignaturePlace;

    // 承办人员签名时间
    private String undertakePersonalTimes;
  
    // 承办部门意见
    private String undertakeDepartmentIdea;
       
    // 承办部门负责人签名图片
    private String departmentSignatureBase64;

    // 承办部门负责人签名值
    private String departmentSignatureValue;

    // 承办部门负责人签名位置
    private String departmentSignaturePlace;

    // 承办部门签名时间
    private String undertakeDepartmentTimes;
    
    // 主管领导意见
    private String leaderIdea;

    // 主管领导签名图片
    private String leaderSignatureBase64;

    // 主管领导签名值
    private String leaderSignatureValue;

    // 主管领导签名位置
    private String leaderSignaturePlace;

    // 主管领导签名时间
    private String leaderTimes;

    // 执法办案唯一编号
    private String baseId;

    // 附件地址
    private String enclosureAddress;

    // 删除标记
    private String delFlg;

    // 执法环节ID
    private String lawLinkId;

    // 变更人ID
    private String updateUserId;

    // 变更人姓名
    private String updateUserNames;

    // 变更时间
    private String updateTimes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getPartyType() {
		return partyType;
	}

	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}

	public String getLeadingName() {
		return leadingName;
	}

	public void setLeadingName(String leadingName) {
		this.leadingName = leadingName;
	}

	public String getPartyPhone() {
		return partyPhone;
	}

	public void setPartyPhone(String partyPhone) {
		this.partyPhone = partyPhone;
	}

	public String getPartyAddress() {
		return partyAddress;
	}

	public void setPartyAddress(String partyAddress) {
		this.partyAddress = partyAddress;
	}

	public String getApprovalMatter() {
		return approvalMatter;
	}

	public void setApprovalMatter(String approvalMatter) {
		this.approvalMatter = approvalMatter;
	}

	public String getCaseSource() {
		return caseSource;
	}

	public void setCaseSource(String caseSource) {
		this.caseSource = caseSource;
	}

	public String getCauseAction() {
		return causeAction;
	}

	public void setCauseAction(String causeAction) {
		this.causeAction = causeAction;
	}

	public String getCaseOverview() {
		return caseOverview;
	}

	public void setCaseOverview(String caseOverview) {
		this.caseOverview = caseOverview;
	}

	public String getDetailApprovalMatter() {
		return detailApprovalMatter;
	}

	public void setDetailApprovalMatter(String detailApprovalMatter) {
		this.detailApprovalMatter = detailApprovalMatter;
	}

	public String getUndertakePersonalIdea() {
		return undertakePersonalIdea;
	}

	public void setUndertakePersonalIdea(String undertakePersonalIdea) {
		this.undertakePersonalIdea = undertakePersonalIdea;
	}

	public String getLeaderIdea() {
		return leaderIdea;
	}

	public void setLeaderIdea(String leaderIdea) {
		this.leaderIdea = leaderIdea;
	}

	public String getMajorSignatureBase64() {
		return majorSignatureBase64;
	}

	public void setMajorSignatureBase64(String majorSignatureBase64) {
		this.majorSignatureBase64 = majorSignatureBase64;
	}

	public String getMajorSignatureValue() {
		return majorSignatureValue;
	}

	public void setMajorSignatureValue(String majorSignatureValue) {
		this.majorSignatureValue = majorSignatureValue;
	}

	public String getMajorSignaturePlace() {
		return majorSignaturePlace;
	}

	public void setMajorSignaturePlace(String majorSignaturePlace) {
		this.majorSignaturePlace = majorSignaturePlace;
	}

	public String getMinorSignatureBase64() {
		return minorSignatureBase64;
	}

	public void setMinorSignatureBase64(String minorSignatureBase64) {
		this.minorSignatureBase64 = minorSignatureBase64;
	}

	public String getMinorSignatureValue() {
		return minorSignatureValue;
	}

	public void setMinorSignatureValue(String minorSignatureValue) {
		this.minorSignatureValue = minorSignatureValue;
	}

	public String getMinorSignaturePlace() {
		return minorSignaturePlace;
	}

	public void setMinorSignaturePlace(String minorSignaturePlace) {
		this.minorSignaturePlace = minorSignaturePlace;
	}

	public String getUndertakeDepartmentIdea() {
		return undertakeDepartmentIdea;
	}

	public void setUndertakeDepartmentIdea(String undertakeDepartmentIdea) {
		this.undertakeDepartmentIdea = undertakeDepartmentIdea;
	}

	public String getDepartmentSignatureBase64() {
		return departmentSignatureBase64;
	}

	public void setDepartmentSignatureBase64(String departmentSignatureBase64) {
		this.departmentSignatureBase64 = departmentSignatureBase64;
	}

	public String getDepartmentSignatureValue() {
		return departmentSignatureValue;
	}

	public void setDepartmentSignatureValue(String departmentSignatureValue) {
		this.departmentSignatureValue = departmentSignatureValue;
	}

	public String getDepartmentSignaturePlace() {
		return departmentSignaturePlace;
	}

	public void setDepartmentSignaturePlace(String departmentSignaturePlace) {
		this.departmentSignaturePlace = departmentSignaturePlace;
	}

	public String getLeaderSignatureBase64() {
		return leaderSignatureBase64;
	}

	public void setLeaderSignatureBase64(String leaderSignatureBase64) {
		this.leaderSignatureBase64 = leaderSignatureBase64;
	}

	public String getLeaderSignatureValue() {
		return leaderSignatureValue;
	}

	public void setLeaderSignatureValue(String leaderSignatureValue) {
		this.leaderSignatureValue = leaderSignatureValue;
	}

	public String getLeaderSignaturePlace() {
		return leaderSignaturePlace;
	}

	public void setLeaderSignaturePlace(String leaderSignaturePlace) {
		this.leaderSignaturePlace = leaderSignaturePlace;
	}

	public String getUndertakePersonalTimes() {
		return undertakePersonalTimes;
	}

	public void setUndertakePersonalTimes(String undertakePersonalTimes) {
		this.undertakePersonalTimes = undertakePersonalTimes;
	}

	public String getLeaderTimes() {
		return leaderTimes;
	}

	public void setLeaderTimes(String leaderTimes) {
		this.leaderTimes = leaderTimes;
	}

	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
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

	public String getUpdateUserNames() {
		return updateUserNames;
	}

	public String getCauseActionName() {
		return causeActionName;
	}

	public void setCauseActionName(String causeActionName) {
		this.causeActionName = causeActionName;
	}

	public void setUpdateUserNames(String updateUserNames) {
		this.updateUserNames = updateUserNames;
	}

	public String getUndertakeDepartmentTimes() {
		return undertakeDepartmentTimes;
	}

	public void setUndertakeDepartmentTimes(String undertakeDepartmentTimes) {
		this.undertakeDepartmentTimes = undertakeDepartmentTimes;
	}

	public String getUpdateTimes() {
		return updateTimes;
	}

	public void setUpdateTimes(String updateTimes) {
		this.updateTimes = updateTimes;
	}

	public Map<String, String> getDocInfo(String caseCode) {
		// 文书内容
		Map<String, String> content = new HashMap<String, String>();
		//名称
		content.put("名称", partyName);
		// 当事人类型
		String partyTypeName = "";
		if ("1".equals(partyType)) {
			partyTypeName = "法定代表人";
		} else if ("2".equals(partyType)) {
			partyTypeName = "负责人";
		} else if ("3".equals(partyType)) {
			partyTypeName = "投资人";
		} else {
			partyTypeName = "其他";
		}
        content.put("当事人类型", partyTypeName);
		// 当事人姓名
		content.put("当事人姓名", leadingName);
		// 联系电话
		content.put("联系电话", partyPhone);
		// 住所
		content.put("住所", partyAddress);

		// 审批事项
		String approvalMatterName = "";
		if ("1".equals(approvalMatter)) {
			approvalMatterName = "抽样取证";
		} else if ("2".equals(approvalMatter)) {
			approvalMatterName = "抽样取证物品处理";
		} else if ("3".equals(approvalMatter)) {
			approvalMatterName = "证据先行登记保存";
		} else if ("4".equals(approvalMatter)) {
			approvalMatterName = "证据先行登记保存处理";
		} else if ("5".equals(approvalMatter)) {
			approvalMatterName = "查封/扣押";
		} else if ("6".equals(approvalMatter)) {
			approvalMatterName = "延长查封/扣押";
		} else if ("7".equals(approvalMatter)) {
			approvalMatterName = "解除查封/扣押";
		} else if ("8".equals(approvalMatter)) {
			approvalMatterName = "移送案件";
		} else if ("9".equals(approvalMatter)) {
			approvalMatterName = "案件延期办理";
		} else if ("10".equals(approvalMatter)) {
			approvalMatterName = "延期/分期缴纳罚款";
		} else if ("11".equals(approvalMatter)) {
			approvalMatterName = "出库";
		} else if ("12".equals(approvalMatter)) {
			approvalMatterName = "撤销案件";
		} else {
			approvalMatterName = "其他";
		}

		content.put("审批事项", approvalMatterName);

		// 案件来 源
		String caseSourceName = "";
		if ("1".equals(caseSource)) {
			caseSourceName = "日常检查";
		} else if ("2".equals(caseSource)) {
			caseSourceName = "群众举报";
		} else if ("3".equals(caseSource)) {
			caseSourceName = "上级交办";
		} else if ("4".equals(caseSource)) {
			caseSourceName = "其他部门转办";
		} else {
			caseSourceName = "其他";
		}
		content.put("案件来源", caseSourceName);

		// 案由
		content.put("案由", causeAction);
		// 案情概要
		content.put("案情概要", caseOverview);
		// 具体审批事项
		content.put("具体审批事项", detailApprovalMatter);
		// 承办人员意见
		content.put("承办人员意见", undertakePersonalIdea);
		// 主办人签名
		content.put("主办人签名", DocCommonUtils.cut(majorSignatureBase64));
		// 协办人签名
		content.put("协办人签名", DocCommonUtils.cut(minorSignatureBase64));
		// 承办人员签名时间
		content.put("承办人员签名时间", undertakePersonalTimes);
		// 承办部门负责人意见
		content.put("承办部门负责人意见", undertakeDepartmentIdea);
		// 承办部门负责人签名
		content.put("承办部门负责人签名", DocCommonUtils.cut(departmentSignatureBase64));
		// 承办部门签名时间
		content.put("承办部门负责人签名时间", undertakeDepartmentTimes);
		// 主管领导意见
		content.put("主管领导意见", leaderIdea);
		// 主管领导签名
		content.put("主管领导签名", DocCommonUtils.cut(leaderSignatureBase64));
		// 主管领导意见
		content.put("主管领导签名时间", leaderTimes);
		// 页眉
		content.put("页眉", caseCode);

		return content;
	}

}
