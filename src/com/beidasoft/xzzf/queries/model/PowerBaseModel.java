package com.beidasoft.xzzf.queries.model;

import java.util.Date;
import java.util.List;

import com.beidasoft.xzzf.queries.bean.PowerBaseGist;

public class PowerBaseModel {
    private String id;// 职权ID

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

    private Date createDate;  // 创建日期

    private Date deleteDate;  // 删除时间

    private int isDelete; // 是否删除

    private int isStop;  // 是否停用
    
    private List<PowerBaseGist>  powerGist;
    
    private String lawName;  // 法律名称
    
    private String gistType; // 依据分类
    
    private String lawStrip;  // 条
    
    private String fund; // 款
    
    private String item; // 项
    
    private String content; // 内容

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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
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

	public List<PowerBaseGist> getPowerGist() {
		return powerGist;
	}

	public void setPowerGist(List<PowerBaseGist> powerGist) {
		this.powerGist = powerGist;
	}

	public String getLawName() {
		return lawName;
	}

	public void setLawName(String lawName) {
		this.lawName = lawName;
	}

	public String getGistType() {
		return gistType;
	}

	public void setGistType(String gistType) {
		this.gistType = gistType;
	}

	public String getLawStrip() {
		return lawStrip;
	}

	public void setLawStrip(String lawStrip) {
		this.lawStrip = lawStrip;
	}

	public String getFund() {
		return fund;
	}

	public void setFund(String fund) {
		this.fund = fund;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
}
