package com.beidasoft.xzfy.caseAcceptence.model.request;

import com.beidasoft.xzfy.base.model.request.Request;

public class CaseMergeListRequest implements Request{

	//主案件ID
 	private String mainCaseId;
 	// 案件编号
 	private String caseNum;
 	// 案件来源
 	private String postType; 
 	// 姓名/组织名称
 	private String name;
 	// 案件状态
 	private String caseStatus;
 	// 开始时间
 	private String start;
 	// 结束时间
 	private String end;
 	//当前页
 	private int page;
 	//每页记录数
 	private int rows;
 	
	@Override
	public void validate() {
		
	}
	
	public String getMainCaseId() {
		return mainCaseId;
	}
	public void setMainCaseId(String mainCaseId) {
		this.mainCaseId = mainCaseId;
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

	public String getCaseStatus() {
		return caseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}
	
}
