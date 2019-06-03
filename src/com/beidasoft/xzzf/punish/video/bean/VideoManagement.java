package com.beidasoft.xzzf.punish.video.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 执法视频管理
 */
@Entity
@Table(name="ZF_VIDEO_MANAGEMENT")
public class VideoManagement {
    @Id
    @Column(name = "ID")
    private String id; // 执法视频ID

    @Column(name = "BASE_ID")
    private String baseId; // 案件主表ID

    @Column(name = "ATTACH_IDS")
    private String attachIds; // 执法视频IDS

    @Column(name = "ATTACH_SUM")
    private int attachSum; // 视频数

    @Column(name = "VIDEO_NAME")
    private String videoName; // 视频名称

    @Column(name = "REMARK")
    private String remark; // 备注

    @Column(name = "CREATE_DATE")
    private Date createDate; // 创建时间
    
    @Column(name = "UPDATE_DATE")
    private Date updateDate; // 更新时间
    
    @Column(name = "CREATE_PERSON_ID")
    private int createPersonId; // 创建人id
    
    @Column(name = "MODEL")
    private String model; // 附件model

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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}


	public int getCreatePersonId() {
		return createPersonId;
	}

	public void setCreatePersonId(int createPersonId) {
		this.createPersonId = createPersonId;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
    
    
}