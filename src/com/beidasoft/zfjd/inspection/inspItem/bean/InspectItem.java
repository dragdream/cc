package com.beidasoft.zfjd.inspection.inspItem.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 检查项实体类
 */
@Entity
@Table(name="TBL_INSP_MODULE_ITEM")
public class InspectItem {
    // 主键
    @Id
    @Column(name = "ID")
    private String id;

    // 模块ID
    @Column(name = "MODULE_ID")
    private String moduleId;

    // 创建者机构ID
    @Column(name = "CREATE_ORGANIZATION_ID")
    private String createOrganizationId;

    // 事项名称
    @Column(name = "ITEM_NAME")
    private String itemName;

    // 删除标志
    @Column(name = "IS_DELETE")
    private int isDelete;

    // 创建时间
    @Column(name = "CREATE_TIME")
    private Date createTime;

    // 执法系统
    @Column(name = "ORG_SYS")
    private String orgSys;

    // 创建者主体ID
    @Column(name = "CREATE_SUBJECT_ID")
    private String createSubjectId;

    // 项类型（10主管理创建  20子管理员创建）
    @Column(name = "CREATE_TYPE")
    private String createType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getCreateOrganizationId() {
        return createOrganizationId;
    }

    public void setCreateOrganizationId(String createOrganizationId) {
        this.createOrganizationId = createOrganizationId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOrgSys() {
        return orgSys;
    }

    public void setOrgSys(String orgSys) {
        this.orgSys = orgSys;
    }

    public String getCreateSubjectId() {
        return createSubjectId;
    }

    public void setCreateSubjectId(String createSubjectId) {
        this.createSubjectId = createSubjectId;
    }

    public String getCreateType() {
        return createType;
    }

    public void setCreateType(String craeteType) {
        this.createType = craeteType;
    }
}
