package com.tianee.oa.subsys.zhidao.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;

public class TeeZhiDaoQuestionModel {
	private int sid;//自增id
	private String title ;//标题
	private String description;//描述
	///private TeeZhiDaoCat cat;//所属分类
	private int catId;
	private String catName;
	//private TeePerson createUser;//创建人
	private int createUserId;
	private String createUserName;
	private String createUserInfo;//系统管理员[系统管理部]
	
	//private Calendar createTime;//创建时间
	private String createTimeStr;
	
	private int status;//状态  0=未解决  1=已解决
	private String label;//标签
	private int clickCount;//点击量
	//private Date handleTime;//问题处理时间
	private String handleTimeStr;
	
	private List<TeeAttachmentModel> attachMentModel;//附件组模型
	
	
	
	
	public String getCreateUserInfo() {
		return createUserInfo;
	}
	public void setCreateUserInfo(String createUserInfo) {
		this.createUserInfo = createUserInfo;
	}
	public List<TeeAttachmentModel> getAttachMentModel() {
		return attachMentModel;
	}
	public void setAttachMentModel(List<TeeAttachmentModel> attachMentModel) {
		this.attachMentModel = attachMentModel;
	}
	public String getHandleTimeStr() {
		return handleTimeStr;
	}
	public void setHandleTimeStr(String handleTimeStr) {
		this.handleTimeStr = handleTimeStr;
	}
	public int getSid() {
		return sid;
	}
	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return description;
	}
	public int getCatId() {
		return catId;
	}
	public String getCatName() {
		return catName;
	}
	public int getCreateUserId() {
		return createUserId;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public int getStatus() {
		return status;
	}
	public String getLabel() {
		return label;
	}
	
	public void setSid(int sid) {
		this.sid = sid;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setCatId(int catId) {
		this.catId = catId;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getClickCount() {
		return clickCount;
	}
	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}
	
	
	
	
}
