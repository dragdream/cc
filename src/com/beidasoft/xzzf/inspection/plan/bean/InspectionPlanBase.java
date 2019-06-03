package com.beidasoft.xzzf.inspection.plan.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ZF_INSPECTION_BASE")
public class InspectionPlanBase {
	
	@Id
	@Column(name = "ID")
	private String id;				//主键
	
	@Column(name = "TITLE")
	private String title;			//检查计划标题
	
	@Column(name = "PLAN_START_DATE")
	private Date planStartDate;		//计划开始日期
	
	@Column(name = "PLAN_CLOSED_DATE")
	private Date planClosedDate;	//计划结束日期
	
	@Column(name = "INSPECTION_TYPE")
	private int inspectionType;		//检查类型（日常、双随机）
	
	@Column(name = "INSPECTION_MODE")
	private int inspectionMode;		//检查方式
	
	@Column(name = "CREATE_TIME")
	private Date createTime;	//创建日期
	
	@Column(name = "UPDATE_TIME")
	private Date updateTime;	//更新日期
	
	@Column(name = "STATUS")
	private int status;   //当前状态   0未完成，1进行中，2已完成
	
	@Column(name = "INSPECTION_LEVEL")
	private int inspectionLevel;	//检查层级（市级下发、市级创建、区级创建）
	
	@Column(name = "EXECUTE_DEPARTMENT")
	private String executeDepartment;	//执行部门
	
	@Column(name = "CREATE_PERSON")
	private String creatPerson;	//创建者
	
	@Column(name = "INSPECTION_EXPLAIN")
	private String inspectionExplain;	//计划说明
	
	@Column(name = "IS_DELETED")
	private int isDeleted;	//是否删除，0未删除，1已删除，默认为0

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getPlanStartDate() {
		return planStartDate;
	}

	public void setPlanStartDate(Date planStartDate) {
		this.planStartDate = planStartDate;
	}

	public Date getPlanClosedDate() {
		return planClosedDate;
	}

	public void setPlanClosedDate(Date planClosedDate) {
		this.planClosedDate = planClosedDate;
	}

	public int getInspectionType() {
		return inspectionType;
	}

	public void setInspectionType(int inspectionType) {
		this.inspectionType = inspectionType;
	}

	public int getInspectionMode() {
		return inspectionMode;
	}

	public void setInspectionMode(int inspectionMode) {
		this.inspectionMode = inspectionMode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getInspectionLevel() {
		return inspectionLevel;
	}

	public void setInspectionLevel(int inspectionLevel) {
		this.inspectionLevel = inspectionLevel;
	}

	public String getExecuteDepartment() {
		return executeDepartment;
	}

	public void setExecuteDepartment(String executeDepartment) {
		this.executeDepartment = executeDepartment;
	}

	public String getCreatPerson() {
		return creatPerson;
	}

	public void setCreatPerson(String creatPerson) {
		this.creatPerson = creatPerson;
	}

	public String getInspectionExplain() {
		return inspectionExplain;
	}

	public void setInspectionExplain(String inspectionExplain) {
		this.inspectionExplain = inspectionExplain;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	
	
}
