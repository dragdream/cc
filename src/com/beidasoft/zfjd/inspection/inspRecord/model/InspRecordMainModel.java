package com.beidasoft.zfjd.inspection.inspRecord.model;

import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

/**
 * 执法检查记录基础数据
 */
@Entity

public class InspRecordMainModel {
    // 数据唯一标识
    private String id;

    // 检查单号
    private String inspectionNumber;

    // 检查日期
    private String inspectionDateStr;

    // 检查地点
    private String inspectionAddr;

    // 检查结果（1为合格，2为不合格）
    private Integer isInspectionPass;

    // 检查结果说明
    private String resultDiscribe;

    // 数据创建日期
    private String createDateStr;

    // 数据创建用户id
    private Integer createPersonId;

    // 数据更新时间
    private String updateDateStr;

    // 数据更新用户id
    private Integer updatePersonId;

    //所属执法主体id
    private String subjectId;
    
    //所属执法部门id
    private String departmentId;
    
    //所属执法系统
    private String orgSys;
    
    //所属监督部门id
    private String superviseDeptId;
    
    // 使用的检查单模版id
    private String inspectionListId;
    
    private List<Map<?,?>> inspItems;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInspectionNumber() {
        return inspectionNumber;
    }

    public void setInspectionNumber(String inspectionNumber) {
        this.inspectionNumber = inspectionNumber;
    }

    public String getInspectionDateStr() {
        return inspectionDateStr;
    }

    public void setInspectionDateStr(String inspectionDateStr) {
        this.inspectionDateStr = inspectionDateStr;
    }

    public String getInspectionAddr() {
        return inspectionAddr;
    }

    public void setInspectionAddr(String inspectionAddr) {
        this.inspectionAddr = inspectionAddr;
    }

    public Integer getIsInspectionPass() {
        return isInspectionPass;
    }

    public void setIsInspectionPass(Integer isInspectionPass) {
        this.isInspectionPass = isInspectionPass;
    }

    public String getResultDiscribe() {
        return resultDiscribe;
    }

    public void setResultDiscribe(String resultDiscribe) {
        this.resultDiscribe = resultDiscribe;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public Integer getCreatePersonId() {
        return createPersonId;
    }

    public void setCreatePersonId(Integer createPersonId) {
        this.createPersonId = createPersonId;
    }

    public String getUpdateDateStr() {
        return updateDateStr;
    }

    public void setUpdateDateStr(String updateDateStr) {
        this.updateDateStr = updateDateStr;
    }

    public Integer getUpdatePersonId() {
        return updatePersonId;
    }

    public void setUpdatePersonId(Integer updatePersonId) {
        this.updatePersonId = updatePersonId;
    }

    public String getInspectionListId() {
        return inspectionListId;
    }

    public void setInspectionListId(String inspectionListId) {
        this.inspectionListId = inspectionListId;
    }
    
    public List<Map<?, ?>> getInspItems() {
        return inspItems;
    }

    public void setInspItems(List<Map<?, ?>> inspItems) {
        this.inspItems = inspItems;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getSuperviseDeptId() {
        return superviseDeptId;
    }

    public void setSuperviseDeptId(String superviseDeptId) {
        this.superviseDeptId = superviseDeptId;
    }

    public String getOrgSys() {
        return orgSys;
    }

    public void setOrgSys(String orgSys) {
        this.orgSys = orgSys;
    }

}
