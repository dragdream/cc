package com.beidasoft.xzzf.punish.video.model;

import java.util.List;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.webservice.model.Person;
/**
 * 执法视频管理model
 */
public class VideoManagementModel {

	private String id; // 执法视频ID

    private String baseId; // 案件主表ID

    private String attachIds; // 执法视频IDS

    private int attachSum; // 视频数

    private String videoName; // 视频名称

    private String remark; // 备注

    private String createDateStr; // 创建时间
    
    private String updateDateStr; // 更新时间
    
    private int createPersonId; // 创建人id
    
    private String createPersonName; // 创建人
    
    private String model; // 附件model
    
    private List<TeeAttachmentModel> attachModels;//附件

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}

	public String getAttachIds() {
		return attachIds;
	}

	public void setAttachIds(String attachIds) {
		this.attachIds = attachIds;
	}

	public int getAttachSum() {
		return attachSum;
	}

	public void setAttachSum(int attachSum) {
		this.attachSum = attachSum;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getUpdateDateStr() {
		return updateDateStr;
	}

	public void setUpdateDateStr(String updateDateStr) {
		this.updateDateStr = updateDateStr;
	}


	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<TeeAttachmentModel> getAttachModels() {
		return attachModels;
	}

	public void setAttachModels(List<TeeAttachmentModel> attachModels) {
		this.attachModels = attachModels;
	}

	public String getCreateDateStr() {
		return createDateStr;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}

	public int getCreatePersonId() {
		return createPersonId;
	}

	public void setCreatePersonId(int createPersonId) {
		this.createPersonId = createPersonId;
	}

	public String getCreatePersonName() {
		return createPersonName;
	}

	public void setCreatePersonName(String createPersonName) {
		this.createPersonName = createPersonName;
	}

	
    
}