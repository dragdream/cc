package com.tianee.oa.subsys.cowork.model;

import java.util.ArrayList;
import java.util.List;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;

public class TeeCoWorkTaskDocModel {
	private int sid;
	private List<TeeAttachmentModel> attaches = new ArrayList();
	private String attachIds;
	private String remark;
	private int createUserId;
	private String createUserName;
	private String createTimeDesc;
	private int taskId;
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public List<TeeAttachmentModel> getAttaches() {
		return attaches;
	}
	public void setAttaches(List<TeeAttachmentModel> attaches) {
		this.attaches = attaches;
	}
	public String getAttachIds() {
		return attachIds;
	}
	public void setAttachIds(String attachIds) {
		this.attachIds = attachIds;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getCreateTimeDesc() {
		return createTimeDesc;
	}
	public void setCreateTimeDesc(String createTimeDesc) {
		this.createTimeDesc = createTimeDesc;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	
	
}
