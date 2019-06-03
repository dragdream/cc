package com.tianee.oa.subsys.timeline.model;

import java.util.List;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;




public class TeeTimelineEventModel {
	
	private String uuid;//自增id
	
	private String timelineUuid;
	
	private String title;//事件标题
	
	private String startTimeDesc;//开始日期    格式为 yyyy-MM-dd 00:00:00
	
	private String endTimeDesc;//结束日期   格式为yyyy-MM-dd 23:59:59
	
	private String crTimeDesc;//创建时间
	
	private String lastEditTimeDesc;//最后修改时间
	
	private int crUserId;
	
	private String crUserName;//创建人
	
	private int updateUserId;
	
	private String updateUserName;//更新人
	
	private String content;//事件内容

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

	public String getLastEditTimeDesc() {
		return lastEditTimeDesc;
	}

	public void setLastEditTimeDesc(String lastEditTimeDesc) {
		this.lastEditTimeDesc = lastEditTimeDesc;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTimelineUuid() {
		return timelineUuid;
	}

	public void setTimelineUuid(String timelineUuid) {
		this.timelineUuid = timelineUuid;
	}

	
}
