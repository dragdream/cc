package com.beidasoft.zfjd.law.model;

import java.util.Date;
import java.util.List;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;


public class TblLawModel {
	// 法律法规id
    private String id;

    // 名称
    private String name;

    // 时效性 01现行有效 02失效
    private String timeliness;

    // 法律法规类别
    private String submitlawLevel;

    // 发文字号
    private String word;

    // 发布机关
    private String organ;

    // 颁布日期
    private String promulgationStr;

    // 实施日期
    private String implementationStr;

    // 备注
    private String remark;

    // 审核状态
    private Integer examine;
    
    // 是否删除（停用）
    private Integer isDelete;

    // 创建者
    private String creatorId;

    // 创建时间
    private Date createDate;

    // 创建时间
    private String createDateStr;
    
    // 法律原文文件存储路径
    private String lawPath;

    // 法律编号
    private String lawNum;
    
    // 法律编号
    private String orderInedx;
    
    // 操作方法
    private String controlType;
    
    // 更新法律ID
    private String updateLawId;
    
    //文件路径
    private String filename;

	List<TeeAttachmentModel> attachModels;

    
	public List<TeeAttachmentModel> getAttachModels() {
		return attachModels;
	}

	public void setAttachModels(List<TeeAttachmentModel> attachModels) {
		this.attachModels = attachModels;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTimeliness() {
		return timeliness;
	}

	public void setTimeliness(String timeliness) {
		this.timeliness = timeliness;
	}

	public String getSubmitlawLevel() {
		return submitlawLevel;
	}

	public void setSubmitlawLevel(String submitlawLevel) {
		this.submitlawLevel = submitlawLevel;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getOrgan() {
		return organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}

	public String getPromulgationStr() {
		return promulgationStr;
	}

	public void setPromulgationStr(String promulgationStr) {
		this.promulgationStr = promulgationStr;
	}

	public String getImplementationStr() {
		return implementationStr;
	}

	public void setImplementationStr(String implementationStr) {
		this.implementationStr = implementationStr;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getLawPath() {
		return lawPath;
	}

	public void setLawPath(String lawPath) {
		this.lawPath = lawPath;
	}

	public String getLawNum() {
		return lawNum;
	}

	public void setLawNum(String lawNum) {
		this.lawNum = lawNum;
	}

	public String getOrderInedx() {
		return orderInedx;
	}

	public void setOrderInedx(String orderInedx) {
		this.orderInedx = orderInedx;
	}

	public String getControlType() {
		return controlType;
	}

	public void setControlType(String controlType) {
		this.controlType = controlType;
	}

	public String getUpdateLawId() {
		return updateLawId;
	}

	public void setUpdateLawId(String updateLawId) {
		this.updateLawId = updateLawId;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Integer getExamine() {
		return examine;
	}

	public void setExamine(Integer examine) {
		this.examine = examine;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getCreateDateStr() {
		return createDateStr;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}

}
