package com.tianee.oa.core.base.examine.model;


public class TeeExamineGroupModel {

	private int sid;//SID	int(11)	自增字段	是	自增
	private String examineName;//EXAMINE_NAME	varchar(254)		
	private String examineDesc;//EXAMINE_DESC	TEST	考核指标描述	
	private String examineRefer;//EXAMINE_REFER	va

	private String postDeptIds;
	private String postDeptNames;
	
	private String postUserIds;
	private String postUserNames;
	
	private String postUserRoleIds;
	private String postUserRoleNames;
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getExamineName() {
		return examineName;
	}
	public void setExamineName(String examineName) {
		this.examineName = examineName;
	}
	public String getExamineDesc() {
		return examineDesc;
	}
	public void setExamineDesc(String examineDesc) {
		this.examineDesc = examineDesc;
	}
	public String getExamineRefer() {
		return examineRefer;
	}
	public void setExamineRefer(String examineRefer) {
		this.examineRefer = examineRefer;
	}
	public String getPostDeptIds() {
		return postDeptIds;
	}
	public void setPostDeptIds(String postDeptIds) {
		this.postDeptIds = postDeptIds;
	}
	public String getPostDeptNames() {
		return postDeptNames;
	}
	public void setPostDeptNames(String postDeptNames) {
		this.postDeptNames = postDeptNames;
	}
	
	public String getPostUserIds() {
		return postUserIds;
	}
	public void setPostUserIds(String postUserIds) {
		this.postUserIds = postUserIds;
	}
	public String getPostUserNames() {
		return postUserNames;
	}
	public void setPostUserNames(String postUserNames) {
		this.postUserNames = postUserNames;
	}
	public String getPostUserRoleIds() {
		return postUserRoleIds;
	}
	public void setPostUserRoleIds(String postUserRoleIds) {
		this.postUserRoleIds = postUserRoleIds;
	}
	public String getPostUserRoleNames() {
		return postUserRoleNames;
	}
	public void setPostUserRoleNames(String postUserRoleNames) {
		this.postUserRoleNames = postUserRoleNames;
	}
	
	
	
}
