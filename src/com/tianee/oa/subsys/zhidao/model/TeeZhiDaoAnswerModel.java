package com.tianee.oa.subsys.zhidao.model;

import java.util.List;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;

public class TeeZhiDaoAnswerModel {
	
	private int sid;//自增id
	private String content ;//回答内容
	//private Calendar createTime ;//创建时间
	private String createTimeStr;
	private int isBest ;//是否是最佳答案
	//private TeeZhiDaoQuestion question;//所属问题	
	private String questionName;
	private int questionId;
	
	//private TeePerson createUser;//创建人
	private int createUserId;
	private String createUserName;
	private String createUserInfo;//系统管理员[系统管理部]
	
	private String avatar;//人员头像id
	private List<TeeAttachmentModel> attachMentModel;//附件组模型
	
	
	public List<TeeAttachmentModel> getAttachMentModel() {
		return attachMentModel;
	}
	public void setAttachMentModel(List<TeeAttachmentModel> attachMentModel) {
		this.attachMentModel = attachMentModel;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getCreateUserInfo() {
		return createUserInfo;
	}
	public void setCreateUserInfo(String createUserInfo) {
		this.createUserInfo = createUserInfo;
	}
	public int getSid() {
		return sid;
	}
	public String getContent() {
		return content;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public int getIsBest() {
		return isBest;
	}
	public String getQuestionName() {
		return questionName;
	}
	public int getQuestionId() {
		return questionId;
	}
	public int getCreateUserId() {
		return createUserId;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public void setIsBest(int isBest) {
		this.isBest = isBest;
	}
	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
	
	
	
}
