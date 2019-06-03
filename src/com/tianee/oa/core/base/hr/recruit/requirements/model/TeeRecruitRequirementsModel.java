package com.tianee.oa.core.base.hr.recruit.requirements.model;

import java.util.List;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.base.hr.recruit.model.TeeHrPoolModel;

public class TeeRecruitRequirementsModel {
	private int sid;
	private String requNo;// 需求编号
	private int createUserId;// 创建者id
	private String createUserName;// 创建者名称
	private int createDeptId;// 创建者部门id
	private String createDeptName;// 创建者部门名称
	private String requDeptStr;// 需求部门,sid串
	private String requDeptStrName;// 需求部门名称串
	private String createTimeStr;// 登记时间(创建时间)
	private String requJob;// 需求岗位
	private int requNum;// 需求人数
	private String requRequires;// 岗位要求
	private String requTimeStr;// 用工日期
	private String remark;// 备注
	
	private int requStatus;//需求状态
	private List<TeeHrPoolModel> hrPoolsModel;//人才库模型
	
	private List<TeeAttachmentModel> attacheModels;//附件
	private String attacheIds;//附件Ids字符串  以逗号分隔 
	
	private String recruirementsPriv;//权限 1-人事专员权限 0- 普通
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getRequNo() {
		return requNo;
	}
	public void setRequNo(String requNo) {
		this.requNo = requNo;
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
	public int getCreateDeptId() {
		return createDeptId;
	}
	public void setCreateDeptId(int createDeptId) {
		this.createDeptId = createDeptId;
	}
	public String getCreateDeptName() {
		return createDeptName;
	}
	public void setCreateDeptName(String createDeptName) {
		this.createDeptName = createDeptName;
	}
	public String getRequDeptStr() {
		return requDeptStr;
	}
	public void setRequDeptStr(String requDeptStr) {
		this.requDeptStr = requDeptStr;
	}
	public String getRequDeptStrName() {
		return requDeptStrName;
	}
	public void setRequDeptStrName(String requDeptStrName) {
		this.requDeptStrName = requDeptStrName;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public String getRequJob() {
		return requJob;
	}
	public void setRequJob(String requJob) {
		this.requJob = requJob;
	}
	public int getRequNum() {
		return requNum;
	}
	public void setRequNum(int requNum) {
		this.requNum = requNum;
	}
	public String getRequRequires() {
		return requRequires;
	}
	public void setRequRequires(String requRequires) {
		this.requRequires = requRequires;
	}
	public String getRequTimeStr() {
		return requTimeStr;
	}
	public void setRequTimeStr(String requTimeStr) {
		this.requTimeStr = requTimeStr;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<TeeAttachmentModel> getAttacheModels() {
		return attacheModels;
	}
	public void setAttacheModels(List<TeeAttachmentModel> attacheModels) {
		this.attacheModels = attacheModels;
	}
	public String getAttacheIds() {
		return attacheIds;
	}
	public void setAttacheIds(String attacheIds) {
		this.attacheIds = attacheIds;
	}
	public int getRequStatus() {
		return requStatus;
	}
	public void setRequStatus(int requStatus) {
		this.requStatus = requStatus;
	}
	public List<TeeHrPoolModel> getHrPoolsModel() {
		return hrPoolsModel;
	}
	public void setHrPoolsModel(List<TeeHrPoolModel> hrPoolsModel) {
		this.hrPoolsModel = hrPoolsModel;
	}
	public String getRecruirementsPriv() {
		return recruirementsPriv;
	}
	public void setRecruirementsPriv(String recruirementsPriv) {
		this.recruirementsPriv = recruirementsPriv;
	}
	

}
