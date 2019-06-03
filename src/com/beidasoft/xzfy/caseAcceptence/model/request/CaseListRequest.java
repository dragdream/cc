package com.beidasoft.xzfy.caseAcceptence.model.request;

import com.beidasoft.xzfy.base.model.request.Request;

public class CaseListRequest implements Request{

	//类型 2为受理,3为审理,4为结案,5为归档
	private String type;
	// 案件编号
	private String caseNum;
	// 案件来源
	private String postType; 
	// 姓名/组织名称
	private String name;
	// 案件受理状态
	private String caseStatus;
	// 开始时间
	private String start;
	// 结束时间
	private String end;
	//当前页
	private int page;
	//每页记录数
	private int rows;
	
	//是否待办列表
	private String isNeedDeal;
	
	//被申请人
	private String respName;
	
	//审批状态
	private String approveType;
	
	@Override
	public void validate() {
		// TODO Auto-generated method stub
		
	}
	public String getCaseNum() {
		return caseNum;
	}
	public void setCaseNum(String caseNum) {
		this.caseNum = caseNum;
	}
	public String getPostType() {
		return postType;
	}
	public void setPostType(String postType) {
		this.postType = postType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCaseStatus() {
		return caseStatus;
	}
	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}
	public String getIsNeedDeal() {
		return isNeedDeal;
	}
	public void setIsNeedDeal(String isNeedDeal) {
		this.isNeedDeal = isNeedDeal;
	}
	public String getRespName() {
		return respName;
	}
	public void setRespName(String respName) {
		this.respName = respName;
	}
	public String getApproveType() {
		return approveType;
	}
	public void setApproveType(String approveType) {
		this.approveType = approveType;
	}
	
}
