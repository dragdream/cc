package com.beidasoft.zfjd.system.model;

import java.util.Date;

/**
 * 人员和登录账号关系表MODEL类
 */
public class PersonUserModel {
    // 主键id
    private String id;

    // 执法人员id
    private String personId;

    // 监督人员id
    private String suppersonId;

    // 登陆账号uuid
    private int userUuid;

    // 删除标志0未删除 1已删除
    private int isDelete;

    // 创建者id
    private String creatorId;

    // 创建日期
    private Date creatorTime;

    // 删除日期
    private Date deleteTime;

    //创建者名称
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

    public int getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(int userUuid) {
        this.userUuid = userUuid;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
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
