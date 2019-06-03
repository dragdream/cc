package com.tianee.oa.subsys.project.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name = "project")
//项目类
public class TeeProject {
	@Id
	private String uuid;//主键
	

	@Column(name="project_num")
	private String projectNum;//项目编码
	
	
	
	
	@Column(name="project_name")
	private String projectName;//项目名称
	
	@Column(name="begin_time")
	private Date begiTime;//开始时间
	
	@Column(name="end_time")
	private Date endTime;//结束时间
	
	@Column(name="province")
	private String province;//省份  
	
	
	@Column(name="city")
	private String  city;//市
	
	@Column(name="district")
	private String district;//市
	
	@Column(name="address_desc")
	private String  addressDesc;//项目所在位置  
	
	@Column(name="address_coordinate")
	private String addressCoordinate;//地址坐标{x:123,y:234}
	
	@Column(name="project_level")
	private String projectLevel;//项目级别
	
	@Column(name="project_file_netdisk_ids")
	private String projectFileNetdiskIds;//项目文档目录的主键
	
	@Column(name="project_budget")
	private double projectBudget;//项目预算
	
	
	@Column(name="project_approver_id")
	private int approverId;//项目审批人
	
/*	private  int  project_manager_id;//项目负责人主键
	private int  project_approver_id;//项目审批人主键
	private int project_type_id;//项目类型主键
	private int project_creater_id;//项目创建人主键
*/	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="project_creater_id")
	private TeePerson projectCreateId;
	
	@Column(name="project_type_id")
	private int projectTypeId;
	
	@Column(name="project_manager_id")
	private int projectManageId;
	/* 项目成员  中间表  project_memeber
	 共享人员  中间表   project_share
	 项目观察者  中间表*/
	
	@Column(name="status")
	 private int status;//项目状态  立项中1       审批中2      办理中3        挂起中4       已办结5     已拒绝6
	 
	 
	 
	 @Column(name="create_time")
	 private  Date  createTime;//创建时间
	 
	 @Column(name="progress")
	 private int progress;//项目进度
	 
	 
	 @Column(name="refused_reason")
	 private String refusedReason;//拒绝原因
	 
	 @Column(name="file_id")
	 private int fileId;
	 
	//新添加的字段
	 @Column(name="PROJECT_PF_TIME")
	 private Date projectPFTime;//项目批复时间
	 
	 @Column(name="ATTACH_SIDS")
	 private String attachSids;//项目申报文件
	 
	 @Column(name="JXW_PF_MONEY")
	 private String jxwPFMoney;//经信委批复金额
	 
	 @Column(name="JXW_PF_H")
	 private String jxwPFH;//经信委项目批复函
	 
	 @Column(name="CZJ_PF_MONEY")
	 private String czjPFMoney;//财政局批复金额
	 
	 @ManyToOne(fetch=FetchType.LAZY)
	 @JoinColumn(name="CZJ_PF_PERSON")
	 private TeePerson czjPFPerson;//财政局批复负责人
	 
	 @Column(name="CZJ_ATTACH_SIDS")
	 private String czjAttachSids;//财政局批复附件
	 
	 @Column(name="CZJ_PF_CONTENT")
	 private String czjPFContent;//财政局批复内容
	
	 public String getRefusedReason() {
		return refusedReason;
	}

	public void setRefusedReason(String refusedReason) {
		this.refusedReason = refusedReason;
	}

	public int getApproverId() {
		return approverId;
	}

	public void setApproverId(int approverId) {
		this.approverId = approverId;
	}

	@Column(name="project_content")
	 private String projectContent; //项目内容
	 
	 public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getProjectNum() {
		return projectNum;
	}

	public void setProjectNum(String projectNum) {
		this.projectNum = projectNum;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Date getBegiTime() {
		return begiTime;
	}

	public void setBegiTime(Date begiTime) {
		this.begiTime = begiTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getAddressDesc() {
		return addressDesc;
	}

	public void setAddressDesc(String addressDesc) {
		this.addressDesc = addressDesc;
	}

	public String getAddressCoordinate() {
		return addressCoordinate;
	}

	public void setAddressCoordinate(String addressCoordinate) {
		this.addressCoordinate = addressCoordinate;
	}

	public String getProjectLevel() {
		return projectLevel;
	}

	public void setProjectLevel(String projectLevel) {
		this.projectLevel = projectLevel;
	}

	public String getProjectFileNetdiskIds() {
		return projectFileNetdiskIds;
	}

	public void setProjectFileNetdiskIds(String projectFileNetdiskIds) {
		this.projectFileNetdiskIds = projectFileNetdiskIds;
	}

	public double getProjectBudget() {
		return projectBudget;
	}

	public void setProjectBudget(double projectBudget) {
		this.projectBudget = projectBudget;
	}

	public String getProjectContent() {
		return projectContent;
	}

	public void setProjectContent(String projectContent) {
		this.projectContent = projectContent;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public int getProjectTypeId() {
		return projectTypeId;
	}

	public void setProjectTypeId(int projectTypeId) {
		this.projectTypeId = projectTypeId;
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public TeePerson getProjectCreateId() {
		return projectCreateId;
	}

	public void setProjectCreateId(TeePerson projectCreateId) {
		this.projectCreateId = projectCreateId;
	}

	public int getProjectManageId() {
		return projectManageId;
	}

	public void setProjectManageId(int projectManageId) {
		this.projectManageId = projectManageId;
	}

	public Date getProjectPFTime() {
		return projectPFTime;
	}

	public String getAttachSids() {
		return attachSids;
	}

	public String getJxwPFMoney() {
		return jxwPFMoney;
	}

	public String getJxwPFH() {
		return jxwPFH;
	}

	public String getCzjPFMoney() {
		return czjPFMoney;
	}


	public String getCzjAttachSids() {
		return czjAttachSids;
	}

	public String getCzjPFContent() {
		return czjPFContent;
	}

	public void setProjectPFTime(Date projectPFTime) {
		this.projectPFTime = projectPFTime;
	}

	public void setAttachSids(String attachSids) {
		this.attachSids = attachSids;
	}

	public void setJxwPFMoney(String jxwPFMoney) {
		this.jxwPFMoney = jxwPFMoney;
	}

	public void setJxwPFH(String jxwPFH) {
		this.jxwPFH = jxwPFH;
	}

	public void setCzjPFMoney(String czjPFMoney) {
		this.czjPFMoney = czjPFMoney;
	}

	

	public void setCzjAttachSids(String czjAttachSids) {
		this.czjAttachSids = czjAttachSids;
	}

	public void setCzjPFContent(String czjPFContent) {
		this.czjPFContent = czjPFContent;
	}

	public TeePerson getCzjPFPerson() {
		return czjPFPerson;
	}

	public void setCzjPFPerson(TeePerson czjPFPerson) {
		this.czjPFPerson = czjPFPerson;
	}

	

	
}
