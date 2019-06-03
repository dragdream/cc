package com.beidasoft.xzfy.caseRegister.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 申请人信息
 * 
 * @author cc
 * 
 */
@Entity
@Table(name = "FY_AGENT_RELATION")
public class FyAgentRelation {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "id")
    @GenericGenerator(name = "id", strategy = "uuid")
    private String id; // 主键id

    @Column(name = "CASE_ID")
    private String caseId; // 案件ID

    @Column(name = "AGENT_ID")
    private String agentId; // 代理人Id

    @Column(name = "RELATION_ID")
    private String relationId; // 关联ID

    @Column(name = "CREATED_USER_ID")
    private String createdUserId; // 创建人Id

    @Column(name = "CREATED_USER")
    private String createdUser; // 创建人

    @Column(name = "CREATED_TIME")
    private String createTime; // 创建时间

    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId; // 修改人ID

    @Column(name = "MODIFIED_USER")
    private String modifiedUser; // 修改人

    @Column(name = "MODIFIED_TIME")
    private String modifiedTime; // 修改时间

    @Column(name = "IS_DELETE")
    private int isDelete; // 是否删除

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(String createdUserId) {
        this.createdUserId = createdUserId;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifiedUserId() {
        return modifiedUserId;
    }

    public void setModifiedUserId(String modifiedUserId) {
        this.modifiedUserId = modifiedUserId;
    }

    public String getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }
}
