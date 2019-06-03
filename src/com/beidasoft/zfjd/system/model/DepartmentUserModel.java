package com.beidasoft.zfjd.system.model;

import java.util.Date;

/**
 * 部门主体和登录账号关系表MODEL类
 */
public class DepartmentUserModel {
    // 主键
    private String id;

    // 执法部门 id
    private String departmentId;

    // 监督部门 id
    private String superviseId;

    // 账号 uuid
    private Integer userUuid;

    // 删除标识 0 未删除 1已删除
    private Integer isDelete;

    // 创建者id
    private String creatorId;

    // 创建时间
    private Date creatorTime;

    // 删除时间
    private Date deleteTime;

    // 执法主体 id
    private String subjectId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getSuperviseId() {
        return superviseId;
    }

    public void setSuperviseId(String superviseId) {
        this.superviseId = superviseId;
    }

    public Integer getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(Integer userUuid) {
        this.userUuid = userUuid;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreatorTime() {
        return creatorTime;
    }

    public void setCreatorTime(Date creatorTime) {
        this.creatorTime = creatorTime;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

}
