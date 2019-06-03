package com.tianee.oa.core.base.vote.model;


import java.util.List;

import javax.persistence.Column;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;


public class TeeVoteModel {
	private int sid;//自增id
	private int parentVoteId;//投标项目
	private String parentVoteName;//投标项目

	private int createUserId;
	private String createUserName;//

	private String postDeptIds ;
	private String postDeptNames;//申请权限部门
	
	private String postUserIds;
	private String postUserNames;//申请权限人员
	
	private String postUserRoleIds;
	private String postUserRoleNames;//申请权限角色

	private String  subject;//标题

	private String  content;//标题CONTENT	CLOB	投票描述
	
	private String  voteType;//	Varchar（10）	 投票类型0-单选  1-多选    2-文本输入 3-多行输入框
	
	private int maxNum;//MAX_NUM Int	多选 可选最多选项
	
	private int minNum;//MIN_NUM Int	多选 可选最少选项  0不控制
	
	private String  anonymity;//	ANONYMITY	Varchar（10）	是否允许匿名投票0：不允许  1：允许
	
	private String  viewPriv;//	VIEW_PRIV	CLOB	查看投票结果0：投票后允许查看   1：投票前允许查看  2：不允许查看
	
	private String  voteTop;//	VOTE_TOP	Varchar（10）	是否置顶      0-不置顶   1-置顶
	
	private String  publish;//	PUBLISH	CLOB	是否发布  0-未发布  1-已发布；2-终止（20150625添加）

	private String createDateStr;//CREATE_DATE	Date	新建时间

	private String beginDateStr;//BENGIN_DATE	Date	有效开始日期
	
	private String endDateStr;//END_DATE	Date	有效结束时间

	private int voteNo;//排序号
	
	private int itemNum;//所有子项数量，只记录在父投票项

	private String  textHtml;//TEXTHTML	CLOB	子投票项内容
	
	private String  htmlForPreview;//预览内容	子投票项
	
	private List<TeeAttachmentModel> attacheModels  ;//附件
	
	private List<TeeVotePersonModel> votePersonModels  ;//投票人员
	
	private List<TeeVoteItemModel> voteItemModels  ;//投票选项
	
	private int required;//是否必填
	
	private int ifContent;//是否有说明

	private int voteStatus;//投票状态：0 未开始 1 进行中 2 已结束
	
	
	
	public int getVoteStatus() {
		return voteStatus;
	}

	public void setVoteStatus(int voteStatus) {
		this.voteStatus = voteStatus;
	}

	public int getRequired() {
		return required;
	}

	public void setRequired(int required) {
		this.required = required;
	}

	public int getIfContent() {
		return ifContent;
	}

	public void setIfContent(int ifContent) {
		this.ifContent = ifContent;
	}

	public int getItemNum() {
		return itemNum;
	}

	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}

	public String getTextHtml() {
		return textHtml;
	}

	public void setTextHtml(String textHtml) {
		this.textHtml = textHtml;
	}

	public String getHtmlForPreview() {
		return htmlForPreview;
	}

	public void setHtmlForPreview(String htmlForPreview) {
		this.htmlForPreview = htmlForPreview;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getParentVoteId() {
		return parentVoteId;
	}

	public void setParentVoteId(int parentVoteId) {
		this.parentVoteId = parentVoteId;
	}

	public String getParentVoteName() {
		return parentVoteName;
	}

	public void setParentVoteName(String parentVoteName) {
		this.parentVoteName = parentVoteName;
	}

	public int getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
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

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getVoteType() {
		return voteType;
	}

	public void setVoteType(String voteType) {
		this.voteType = voteType;
	}

	public int getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

	public int getMinNum() {
		return minNum;
	}

	public void setMinNum(int minNum) {
		this.minNum = minNum;
	}

	public String getAnonymity() {
		return anonymity;
	}

	public void setAnonymity(String anonymity) {
		this.anonymity = anonymity;
	}

	public String getViewPriv() {
		return viewPriv;
	}

	public void setViewPriv(String viewPriv) {
		this.viewPriv = viewPriv;
	}

	public String getVoteTop() {
		return voteTop;
	}

	public void setVoteTop(String voteTop) {
		this.voteTop = voteTop;
	}

	public String getPublish() {
		return publish;
	}

	public void setPublish(String publish) {
		this.publish = publish;
	}

	public String getCreateDateStr() {
		return createDateStr;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}

	public String getBeginDateStr() {
		return beginDateStr;
	}

	public void setBeginDateStr(String beginDateStr) {
		this.beginDateStr = beginDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public int getVoteNo() {
		return voteNo;
	}

	public void setVoteNo(int voteNo) {
		this.voteNo = voteNo;
	}

	public List<TeeAttachmentModel> getAttacheModels() {
		return attacheModels;
	}

	public void setAttacheModels(List<TeeAttachmentModel> attacheModels) {
		this.attacheModels = attacheModels;
	}

	public List<TeeVotePersonModel> getVotePersonModels() {
		return votePersonModels;
	}

	public void setVotePersonModels(List<TeeVotePersonModel> votePersonModels) {
		this.votePersonModels = votePersonModels;
	}

	public List<TeeVoteItemModel> getVoteItemModels() {
		return voteItemModels;
	}

	public void setVoteItemModels(List<TeeVoteItemModel> voteItemModels) {
		this.voteItemModels = voteItemModels;
	}
	
	
	
}
