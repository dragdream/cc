package com.beidasoft.xzzf.power.model;

import java.util.List;

import com.beidasoft.xzzf.power.bean.BasePowerDetail;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;

public class PowerSelectModel {

	private String id; // 主键ID

	private String code; // 职权编号

	private String name; // 职权名称

	private String powerType; // 职权类型

	private String powerLevel; // 职权层级

	private String subjectLaw; // 法定执法主体

	private String subjectDesc; // 执法主体描述

	private String department; // 所属部门ID

	private String subjectId; // 所属主体ID

	private int powerField; // 职权领域

	private int isCriminal; // 是否涉刑

	private String createDateStr; // 创建日期

	private String deleteDateStr; // 删除时间

	private int isDelete; // 是否删除

	private int isStop; // 是否停用

	private String flowPictureType; // 职权流程图类型

	private List<TeeAttachmentModel> attachments;

	private List<BasePowerDetail> powerDetail;

	private String detailName; // 权职分类名称
	
    private int commonlyUsed; //常用职权排序

	public String getDetailName() {
		return detailName;
	}

	public void setDetailName(String detailName) {
		this.detailName = detailName;
	}

	public List<BasePowerDetail> getPowerDetail() {
		return powerDetail;
	}

	public void setPowerDetail(List<BasePowerDetail> powerDetail) {
		this.powerDetail = powerDetail;
	}

	public List<TeeAttachmentModel> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<TeeAttachmentModel> attachments) {
		this.attachments = attachments;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPowerType() {
		return powerType;
	}

	public void setPowerType(String powerType) {
		this.powerType = powerType;
	}

	public String getPowerLevel() {
		return powerLevel;
	}

	public void setPowerLevel(String powerLevel) {
		this.powerLevel = powerLevel;
	}

	public String getSubjectLaw() {
		return subjectLaw;
	}

	public void setSubjectLaw(String subjectLaw) {
		this.subjectLaw = subjectLaw;
	}

	public String getSubjectDesc() {
		return subjectDesc;
	}

	public void setSubjectDesc(String subjectDesc) {
		this.subjectDesc = subjectDesc;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public int getPowerField() {
		return powerField;
	}

	public void setPowerField(int powerField) {
		this.powerField = powerField;
	}

	public int getIsCriminal() {
		return isCriminal;
	}

	public void setIsCriminal(int isCriminal) {
		this.isCriminal = isCriminal;
	}

	public String getCreateDateStr() {
		return createDateStr;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}

	public String getDeleteDateStr() {
		return deleteDateStr;
	}

	public void setDeleteDateStr(String deleteDateStr) {
		this.deleteDateStr = deleteDateStr;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public int getIsStop() {
		return isStop;
	}

	public void setIsStop(int isStop) {
		this.isStop = isStop;
	}

	public String getFlowPictureType() {
		return flowPictureType;
	}

	public void setFlowPictureType(String flowPictureType) {
		this.flowPictureType = flowPictureType;
	}

	public int getCommonlyUsed() {
		return commonlyUsed;
	}

	public void setCommonlyUsed(int commonlyUsed) {
		this.commonlyUsed = commonlyUsed;
	}
	
}
