package com.beidasoft.zfjd.inspection.inspRecord.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 执法检查记录基础数据
 */
@Entity
@Table(name = "TBL_INSP_RECORD_MAIN")
public class InspRecordMain {
    // 数据唯一标识
    @Id
    @Column(name = "ID")
    private String id;

    // 检查单号
    @Column(name = "INSPECTION_NUMBER")
    private String inspectionNumber;

    // 检查日期
    @Column(name = "INSPECTION_DATE")
    private Date inspectionDate;

    // 检查地点
    @Column(name = "INSPECTION_ADDR")
    private String inspectionAddr;

    // 检查结果（1为合格，2为不合格）
    @Column(name = "IS_INSPECTION_PASS")
    private Integer isInspectionPass;

    // 检查结果说明
    @Column(name = "RESULT_DISCRIBE")
    private String resultDiscribe;

    // 数据创建日期
    @Column(name = "CREATE_DATE")
    private Date createDate;

    // 数据创建用户id
    @Column(name = "CREATE_PERSON_ID")
    private Integer createPersonId;

    // 数据更新时间
    @Column(name = "UPDATE_DATE")
    private Date updateDate;

    // 数据更新用户id
    @Column(name = "UPDATE_PERSOIN_ID")
    private Integer updatePersonId;

    // 使用的检查单模版id
    @Column(name = "INSPECTION_LIST_ID")
    private String inspectionListId;

    // 数据删除标识
    @Column(name = "IS_DELETE")
    private Integer isDelete;
    
    // 数据删除日期
    @Column(name = "DELETE_DATE")
    private Date deleteDate;
    
    // 删除数据人员用户id
    @Column(name = "DELETE_PERSON_ID")
    private Integer deletePersonId;
    
    // 所属执法主体id
    @Column(name = "SUBJECT_ID")
    private String subjectId;
    
    // 所属执法部门id
    @Column(name = "DEPARTMENT_ID")
    private String departmentId;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(Date inspectionDate) {
        this.inspectionDate = inspectionDate;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getCreatePersonId() {
        return createPersonId;
    }

    public void setCreatePersonId(Integer createPersonId) {
        this.createPersonId = createPersonId;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getUpdatePersonId() {
        return updatePersonId;
    }

    public void setUpdatePersonId(Integer updatePersonId) {
        this.updatePersonId = updatePersonId;
    }

    public String getInspectionNumber() {
        return inspectionNumber;
    }

    public void setInspectionNumber(String inspectionNumber) {
        this.inspectionNumber = inspectionNumber;
    }

    public String getInspectionListId() {
        return inspectionListId;
    }

    public void setInspectionListId(String inspectionListId) {
        this.inspectionListId = inspectionListId;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public Integer getDeletePersonId() {
        return deletePersonId;
    }

    public void setDeletePersonId(Integer deletePersonId) {
        this.deletePersonId = deletePersonId;
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

}
