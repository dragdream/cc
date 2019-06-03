package com.tianee.oa.core.base.filepassround.model;

import java.util.List;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.base.filepassround.bean.TeeFilePassRoundRecord;
import com.tianee.oa.core.org.bean.TeePerson;

public class TeeFilePassRoundModel {
	private String id;//主键id
	private Integer proId;//发送人id
	private String proName;//发送人姓名
	private Integer depId;//部门id
	private String title;//标题
	private String sendTime;//发送时间
	private String conIdStr;//传阅人id(接收)
	private Integer conId;//传阅人id
	private String conName;//接收人姓名
	private Integer state;//传阅标志
	private String readTime;//传阅时间
	private String attaches;	//文件
	private List<TeeAttachmentModel> attachmentsModels;//文件
	private String startTime;//开始时间
	private String endTime;//结束时间
	private String passroundId;//主表id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getProId() {
		return proId;
	}
	public void setProId(Integer proId) {
		this.proId = proId;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public Integer getDepId() {
		return depId;
	}
	public void setDepId(Integer depId) {
		this.depId = depId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSendTime() {
		return sendTime;
	}
	
	public Integer getConId() {
		return conId;
	}
	public void setConId(Integer conId) {
		this.conId = conId;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getConName() {
		return conName;
	}
	public void setConName(String conName) {
		this.conName = conName;
	}
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getReadTime() {
		return readTime;
	}
	public void setReadTime(String readTime) {
		this.readTime = readTime;
	}
	public String getAttaches() {
		return attaches;
	}
	public void setAttaches(String attaches) {
		this.attaches = attaches;
	}
	public List<TeeAttachmentModel> getAttachmentsModels() {
		return attachmentsModels;
	}
	public void setAttachmentsModels(List<TeeAttachmentModel> attachmentsModels) {
		this.attachmentsModels = attachmentsModels;
	}
	
	public String getConIdStr() {
		return conIdStr;
	}
	public void setConIdStr(String conIdStr) {
		this.conIdStr = conIdStr;
	}
	
	
	
	
	public String getPassroundId() {
		return passroundId;
	}
	public void setPassroundId(String passroundId) {
		this.passroundId = passroundId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	@Override
	public String toString() {
		return "TeeFilePassRoundModel [id=" + id + ", proId=" + proId
				+ ", proName=" + proName + ", depId=" + depId + ", title="
				+ title + ", sendTime=" + sendTime + ", conIdStr=" + conIdStr
				+ ", conId=" + conId + ", conName=" + conName + ", state="
				+ state + ", readTime=" + readTime + ", attaches=" + attaches
				+ ", attachmentsModels=" + attachmentsModels + ", startTime="
				+ startTime + ", endTime=" + endTime + "]";
	}
	
	
	
}
