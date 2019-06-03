package com.beidasoft.zfjd.system.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 人员和登录账号关系表实体类
 */
@Entity
@Table(name="TBL_SYS_PERSON_USER")
public class PersonUser {
    // 主键id
	@Id
    @Column(name = "id")
    private String id;

    // 执法人员id
    @Column(name = "person_id")
    private String personId;

    // 监督人员id
    @Column(name = "supperson_id")
    private String suppersonId;

    // 登陆账号uuid
    @Column(name = "user_uuid")
    private Integer userUuid;

    // 删除标志0未删除 1已删除
    @Column(name = "is_delete")
    private Integer isDelete;

    // 创建者id
    @Column(name = "creator_id")
    private String creatorId;

    // 创建日期
    @Column(name = "creator_time")
    private Date creatorTime;

    // 删除日期
    @Column(name = "delete_time")
    private Date deleteTime;

    // 创建者名称
    @Column(name = "creator_name")
    private String creatorName;
    
    public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getSuppersonId() {
        return suppersonId;
    }

    public void setSuppersonId(String suppersonId) {
        this.suppersonId = suppersonId;
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
}
