package com.tianee.oa.webframe.httpModel.core.workflow;

import javax.persistence.Column;


public class TeeFlowTypeModel {
	private int sid;
	private String flowName;//流程名称
	private int formId;//表单
	private String formName;//表单
	private int type;//流程类型
	private String typeDesc;//流程类型
	private int orderNo;//排序号
	private int flowSortId;//流程类型
	private String flowSortName;//流程类型名称
	private int locked;//流程锁
	private int hasDoc;//是否有正文
	private int freePreset;//是否预设步骤
	private String comment;//流程说明
	private int viewPriv;//是否允许传阅
	private String viewPersonIds;//传阅人Id字符串
	private String viewPersonNames;//传阅人姓名串
	private int delegate;//委托类型
	private String fileCodeExpression;//文号表达式
	private int numbering;//编号
	private int numberingLength;//编号位数
	private int editTitle;//编辑标题1-允许修改2-不允许修改3-修改前缀4-修改后缀-5修改前缀和后缀
	private int attachPriv;
	private int feedbackPriv;//会签权限1-允许 0-不允许
	private int totalWorkNum;//工作总数
	private int delWorkNum;//已删除的工作数
	private int normalWorkNum;//正常的工作数
	private String queryFieldModel;//查询字段模型
	private String flowRunVarsModel="[]";//流程变量模型
	private int runNamePriv;
	//外接模块页面
	private String outerPage;
	private int deptId;//部门主键
	private String deptName;//部门名称
	
	
	private String  archiveMapping;//归档映射
	
	private int autoArchive;//自动归档
	private int autoArchiveValue;//自动归档值
	
	
	
	
	public String getFlowSortName() {
		return flowSortName;
	}
	public void setFlowSortName(String flowSortName) {
		this.flowSortName = flowSortName;
	}
	public int getAutoArchive() {
		return autoArchive;
	}
	public int getAutoArchiveValue() {
		return autoArchiveValue;
	}
	public void setAutoArchive(int autoArchive) {
		this.autoArchive = autoArchive;
	}
	public void setAutoArchiveValue(int autoArchiveValue) {
		this.autoArchiveValue = autoArchiveValue;
	}
	public String getArchiveMapping() {
		return archiveMapping;
	}
	public void setArchiveMapping(String archiveMapping) {
		this.archiveMapping = archiveMapping;
	}
	public int getDeptId() {
		return deptId;
	}
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getFlowName() {
		return flowName;
	}
	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}
	
	public String getViewPersonIds() {
		return viewPersonIds;
	}
	public void setViewPersonIds(String viewPersonIds) {
		this.viewPersonIds = viewPersonIds;
	}
	public String getViewPersonNames() {
		return viewPersonNames;
	}
	public void setViewPersonNames(String viewPersonNames) {
		this.viewPersonNames = viewPersonNames;
	}
	public int getFormId() {
		return formId;
	}
	public void setFormId(int formId) {
		this.formId = formId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public int getFlowSortId() {
		return flowSortId;
	}
	public void setFlowSortId(int flowSortId) {
		this.flowSortId = flowSortId;
	}
	public int getLocked() {
		return locked;
	}
	public void setLocked(int locked) {
		this.locked = locked;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getViewPriv() {
		return viewPriv;
	}
	public void setViewPriv(int viewPriv) {
		this.viewPriv = viewPriv;
	}
	public int getDelegate() {
		return delegate;
	}
	public void setDelegate(int delegate) {
		this.delegate = delegate;
	}
	public String getFileCodeExpression() {
		return fileCodeExpression;
	}
	public void setFileCodeExpression(String fileCodeExpression) {
		this.fileCodeExpression = fileCodeExpression;
	}
	public int getNumbering() {
		return numbering;
	}
	public void setNumbering(int numbering) {
		this.numbering = numbering;
	}
	public int getNumberingLength() {
		return numberingLength;
	}
	public void setNumberingLength(int numberingLength) {
		this.numberingLength = numberingLength;
	}
	public int getEditTitle() {
		return editTitle;
	}
	public void setEditTitle(int editTitle) {
		this.editTitle = editTitle;
	}
	public void setAttachPriv(int attachPriv) {
		this.attachPriv = attachPriv;
	}
	public int getAttachPriv() {
		return attachPriv;
	}
	public int getTotalWorkNum() {
		return totalWorkNum;
	}
	public void setTotalWorkNum(int totalWorkNum) {
		this.totalWorkNum = totalWorkNum;
	}
	public int getDelWorkNum() {
		return delWorkNum;
	}
	public void setDelWorkNum(int delWorkNum) {
		this.delWorkNum = delWorkNum;
	}
	public int getNormalWorkNum() {
		return normalWorkNum;
	}
	public void setNormalWorkNum(int normalWorkNum) {
		this.normalWorkNum = normalWorkNum;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public String getTypeDesc() {
		return typeDesc;
	}
	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}
	public void setQueryFieldModel(String queryFieldModel) {
		this.queryFieldModel = queryFieldModel;
	}
	public String getQueryFieldModel() {
		return queryFieldModel;
	}
	public void setHasDoc(int hasDoc) {
		this.hasDoc = hasDoc;
	}
	public int getHasDoc() {
		return hasDoc;
	}
	public void setFlowRunVarsModel(String flowRunVarsModel) {
		this.flowRunVarsModel = flowRunVarsModel;
	}
	public String getFlowRunVarsModel() {
		return flowRunVarsModel;
	}
	public int getFreePreset() {
		return freePreset;
	}
	public void setFreePreset(int freePreset) {
		this.freePreset = freePreset;
	}
	public int getFeedbackPriv() {
		return feedbackPriv;
	}
	public void setFeedbackPriv(int feedbackPriv) {
		this.feedbackPriv = feedbackPriv;
	}
	public int getRunNamePriv() {
		return runNamePriv;
	}
	public void setRunNamePriv(int runNamePriv) {
		this.runNamePriv = runNamePriv;
	}
	public String getOuterPage() {
		return outerPage;
	}
	public void setOuterPage(String outerPage) {
		this.outerPage = outerPage;
	}
}
