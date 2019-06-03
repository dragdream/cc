package com.beidasoft.xzfy.caseRegister.model.caseInfo.response;

import java.util.List;

import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity.FlowInfo;

/**
 * 案件办案进度详情
 * @author A
 *
 */
public class CaseFlowResponse {
	// 案件ID
	private String caseId;
	// 案件流程列表
	private List<FlowInfo> flowList;

	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public List<FlowInfo> getFlowList() {
		return flowList;
	}
	public void setFlowList(List<FlowInfo> flowList) {
		this.flowList = flowList;
	}
	
}
