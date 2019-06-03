package com.beidasoft.zfjd.system.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 部门主体和登录账号关系表实体类
 */
@Entity
@Table(name="tbl_sys_department_user")
public class DepartmentUser {
    // 主键
	@Id
    @Column(name = "id")
    private String id;

    // 执法部门 id
    @Column(name = "department_id")
    private String departmentId;

    // 监督部门 id
    @Column(name = "supervise_id")
    private String superviseId;

    // 账号 uuid
    @Column(name = "user_uuid")
    private Integer userUuid;

    // 删除标识 0 未删除 1已删除
    @Column(name = "is_delete")
    private Integer isDelete;

    // 创建者id
    @Column(name = "creator_id")
    private String creatorId;

    // 创建时间
    @Column(name = "creator_time")
    private Date creatorTime;

    // 删除时间
    @Column(name = "delete_time")
    private Date deleteTime;

    // 执法主体 id
    @Column(name = "subject_id")
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
