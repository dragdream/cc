package com.tianee.oa.subsys.timeline.model;

import java.util.List;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;


public class TeeTimelineModel {
	private String uuid;//自增id
	
	private String title;//标题
	
	private String type;//大事记分类     参考系统代码表sys_code中的“大事记分类”
	
	private String startTimeDesc;//开始时间  时间部分默认为00:00:00
	
	private String endTimeDesc;//开始时间  时间部分默认为23:59:59
	
	private String crTimeDesc;//创建时间
	
	private String updateTimeDesc;//最后修改时间
	
	private int crUserId;
	
	private String crUserName;//创建人
	
	private int updateUserId;
	
	private String updateUserName;//更新人
	
	private String tag;//标签    标签格式    /标签1/标签2/标签3/     模糊查询的话直接就可以用   like '%/标签1/%'
	
	private String content;//大事记内容
	
	private String viewUserIds;
	
	private String viewUserNames;
	
	private String viewDeptIds;//部门查看权限
	
	private String viewDeptNames;
	
	private String viewRoleIds;//角色查看权限
	
	private String viewRoleNames;
	
	private String postUserIds;
	
	private String postUserNames;
	
	private String postDeptIds;
	
	private String postDeptNames;
	
	private String postRoleIds;
	
	private String postRoleNames;
	
	private String typeDesc;
	
	private List<TeeAttachmentModel> attacheModels;//附件

	public List<TeeAttachmentModel> getAttacheModels() {
		return attacheModels;
	}

	public void setAttacheModels(List<TeeAttachmentModel> attacheModels) {
		this.attacheModels = attacheModels;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStartTimeDesc() {
		return startTimeDesc;
	}

	public void setStartTimeDesc(String startTimeDesc) {
		this.startTimeDesc = startTimeDesc;
	}

	public String getEndTimeDesc() {
		return endTimeDesc;
	}

	public void setEndTimeDesc(String endTimeDesc) {
		this.endTimeDesc = endTimeDesc;
	}

	public String getCrTimeDesc() {
		return crTimeDesc;
	}

	public void setCrTimeDesc(String crTimeDesc) {
		this.crTimeDesc = crTimeDesc;
	}

	public String getUpdateTimeDesc() {
		return updateTimeDesc;
	}

	public void setUpdateTimeDesc(String updateTimeDesc) {
		this.updateTimeDesc = updateTimeDesc;
	}

	public int getCrUserId() {
		return crUserId;
	}

	public void setCrUserId(int crUserId) {
		this.crUserId = crUserId;
	}

	public String getCrUserName() {
		return crUserName;
	}

	public void setCrUserName(String crUserName) {
		this.crUserName = crUserName;
	}

	public int getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(int updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getViewUserIds() {
		return viewUserIds;
	}

	public void setViewUserIds(String viewUserIds) {
		this.viewUserIds = viewUserIds;
	}

	public String getViewUserNames() {
		return viewUserNames;
	}

	public void setViewUserNames(String viewUserNames) {
		this.viewUserNames = viewUserNames;
	}

	public String getViewDeptIds() {
		return viewDeptIds;
	}

	public void setViewDeptIds(String viewDeptIds) {
		this.viewDeptIds = viewDeptIds;
	}

	public String getViewDeptNames() {
		return viewDeptNames;
	}

	public void setViewDeptNames(String viewDeptNames) {
		this.viewDeptNames = viewDeptNames;
	}

	public String getViewRoleIds() {
		return viewRoleIds;
	}

	public void setViewRoleIds(String viewRoleIds) {
		this.viewRoleIds = viewRoleIds;
	}

	public String getViewRoleNames() {
		return viewRoleNames;
	}

	public void setViewRoleNames(String viewRoleNames) {
		this.viewRoleNames = viewRoleNames;
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

	public String getPostRoleIds() {
		return postRoleIds;
	}

	public void setPostRoleIds(String postRoleIds) {
		this.postRoleIds = postRoleIds;
	}

	public String getPostRoleNames() {
		return postRoleNames;
	}

	public void setPostRoleNames(String postRoleNames) {
		this.postRoleNames = postRoleNames;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}
	
	
}
