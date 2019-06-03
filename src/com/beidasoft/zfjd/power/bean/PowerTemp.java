package com.beidasoft.zfjd.power.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *   职权列表实体类
* 2018 
* @ClassName: PowerListTemp.java
* @Description: 该类的功能描述
*
* @author: chenq
* @date: 2018年12月24日 下午3:21:12 
*
 */
@Entity
@Table(name="TBL_POWER_LIST_TEMP")
public class PowerTemp {
    // 主键
    @Id
    @Column(name = "ID")
    private String id;

    // 职权名称
    @Column(name = "NAME")
    private String name;

    // 职权类型
    @Column(name = "POWER_TYPE")
    private String powerType;

    // 部门ID
    @Column(name = "DEPARTMENT_ID")
    private String departmentId;

    // 
    @Column(name = "IS_DELETE")
    private Integer isDelete;

    // 职权编号
    @Column(name = "CODE")
    private String code;

    // 法定执法主体
    @Column(name = "SUBJECT_DESC")
    private String subjectDesc;

    // 主体ID
    @Column(name = "SUBJECT_ID")
    private String subjectId;

    // 创建日期
    @Column(name = "CREATE_DATE")
    private Date createDate;

    // 流程图类型
    @Column(name = "FLOW_PICTURE_TYPE")
    private String flowPictureType;

    // 是否涉刑，0 否，1 是
    @Column(name = "IS_CRIMINAL")
    private Integer isCriminal;

    // 撤销时间
    @Column(name = "REVOKE_DATE")
    private Date revokeDate;

    // 批次号
    @Column(name = "BATCH_CODE")
    private String batchCode;

    // 删除时间
    @Column(name = "DELETE_DATE")
    private Date deleteDate;

    // 职权领域
    @Column(name = "POWER_MOLD")
    private String powerMold;

    // 职权当前状态（10：保存，20：提交，90：审核通过，99：审核未通过）
    @Column(name = "CURRENT_STATE")
    private String currentState;
    
    @OneToMany(mappedBy = "powerGistTemp", fetch = FetchType.LAZY) // 双向,  目标写@JoinColumn 
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OrderBy("gistType ASC")
    private List<PowerGistTemp> gists;// 按模块设置权限
    
    @OneToMany(mappedBy = "powerDetailTemp", fetch = FetchType.LAZY) // 双向,  目标写@JoinColumn 
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OrderBy("code ASC")
    private List<PowerDetailTemp> details;// 按模块设置权限
    
    @OneToMany(mappedBy = "powerFlowsheetTemp", fetch = FetchType.LAZY) // 双向,  目标写@JoinColumn 
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<PowerFlowsheetTemp> flows;// 按模块设置权限
    
    @OneToMany(mappedBy = "powerLevelTemp", fetch = FetchType.LAZY) // 双向,  目标写@JoinColumn 
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OrderBy("powerStage ASC")
    private List<PowerLevelTemp> levels;// 按模块设置权限
    
    @OneToMany(mappedBy = "powerSubjectTemp", fetch = FetchType.LAZY) // 双向,  目标写@JoinColumn 
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<PowerSubjectTemp> subjects;// 按模块设置权限
    
    @OneToMany(mappedBy = "powerOptTemp", fetch = FetchType.LAZY) // 双向,  目标写@JoinColumn 
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<PowerOperation> operationTemps;// 按模块设置权限
    
    @Column(name = "UPDATE_DATE")
    private Date updateDate;

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

    public String getPowerType() {
        return powerType;
    }

    public void setPowerType(String powerType) {
        this.powerType = powerType;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSubjectDesc() {
        return subjectDesc;
    }

    public void setSubjectDesc(String subjectDesc) {
        this.subjectDesc = subjectDesc;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getFlowPictureType() {
        return flowPictureType;
    }

    public void setFlowPictureType(String flowPictureType) {
        this.flowPictureType = flowPictureType;
    }

    public Integer getIsCriminal() {
        return isCriminal;
    }

    public void setIsCriminal(Integer isCriminal) {
        this.isCriminal = isCriminal;
    }

    public Date getRevokeDate() {
        return revokeDate;
    }

    public void setRevokeDate(Date revokeDate) {
        this.revokeDate = revokeDate;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public String getPowerMold() {
        return powerMold;
    }

    public void setPowerMold(String powerMold) {
        this.powerMold = powerMold;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public List<PowerGistTemp> getGists() {
        return gists;
    }

    public void setGists(List<PowerGistTemp> gists) {
        this.gists = gists;
    }

    public List<PowerDetailTemp> getDetails() {
        return details;
    }

    public void setDetails(List<PowerDetailTemp> details) {
        this.details = details;
    }

    public List<PowerFlowsheetTemp> getFlows() {
        return flows;
    }

    public void setFlows(List<PowerFlowsheetTemp> flows) {
        this.flows = flows;
    }

    public List<PowerLevelTemp> getLevels() {
        return levels;
    }

    public void setLevels(List<PowerLevelTemp> levels) {
        this.levels = levels;
    }

    public List<PowerSubjectTemp> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<PowerSubjectTemp> subjects) {
        this.subjects = subjects;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public List<PowerOperation> getOperationTemps() {
        return operationTemps;
    }

    public void setOperationTemps(List<PowerOperation> operationTemps) {
        this.operationTemps = operationTemps;
    }
    
    
}
