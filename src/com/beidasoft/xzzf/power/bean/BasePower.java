package com.beidasoft.xzzf.power.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 职权基础信息表实体类
 */
@Entity
@Table(name="FX_BASE_POWER")
public class BasePower {
    @Id
    @Column(name = "ID")
    private String id;// 职权ID

    @Column(name = "CODE")
    private String code; // 职权编号

    @Column(name = "NAME")
    private String name; // 职权名称

    @Column(name = "POWER_TYPE")
    private String powerType; // 职权类型

    @Column(name = "POWER_LEVEL")
    private String powerLevel; // 职权层级

    @Column(name = "SUBJECT_LAW")
    private String subjectLaw; // 法定执法主体

    @Column(name = "SUBJECT_DESC")
    private String subjectDesc; // 执法主体描述

    @Column(name = "DEPARTMENT")
    private String department; // 所属部门ID

    @Column(name = "SUBJECT_ID")
    private String subjectId; // 所属主体ID

    @Column(name = "POWER_FIELD")
    private int powerField; // 职权领域

    @Column(name = "IS_CRIMINAL")
    private int isCriminal; // 是否涉刑

    @Column(name = "CREATE_DATE")
    private Date createDate;  // 创建日期

    @Column(name = "DELETE_DATE")
    private Date deleteDate;  // 删除时间

    @Column(name = "IS_DELETE")
    private int isDelete; // 是否删除

    @Column(name = "IS_STOP")
    private int isStop;  // 是否停用

    @Column(name = "FLOW_PICTURE_TYPE")
    private String flowPictureType; // 职权流程图类型
    
    @Column(name = "POWER_MOLD")
    private String powerMold;
    
    @Column(name = "COMMONLY_USED")
    private int commonlyUsed; //常用职权排序
    
    
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

    public String getFlowPictureType() {
        return flowPictureType;
    }

    public void setFlowPictureType(String flowPictureType) {
        this.flowPictureType = flowPictureType;
    }

	public String getPowerMold() {
		return powerMold;
	}

	public void setPowerMold(String powerMold) {
		this.powerMold = powerMold;
	}

	public int getCommonlyUsed() {
		return commonlyUsed;
	}

	public void setCommonlyUsed(int commonlyUsed) {
		this.commonlyUsed = commonlyUsed;
	}
    
}
