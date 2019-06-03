package com.beidasoft.zfjd.inspection.inspList.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 执法检查单-检查项关联表
 */
@Entity
@Table(name="TBL_INSP_LIST_ITEM")
public class InspListItem {
    // 数据唯一标识
    @Id
    @Column(name = "ID")
    private String id;

    // 创建日期
    @Column(name = "CREATE_DATE")
    private Date createDate;

    // 检查模块id
    @Column(name = "INSP_MODULE_ID")
    private String inspModuleId;
    
    // 检查项id
    @Column(name = "INSP_ITEM_ID")
    private String inspItemId;
    
    // 检查单id
    @Column(name = "INSP_LIST_ID")
    private String inspListId;
    
    // 检查项名称
    @Column(name = "INSP_ITEM_NAME")
    private String inspItemName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getInspModuleId() {
        return inspModuleId;
    }

    public void setInspModuleId(String inspModuleId) {
        this.inspModuleId = inspModuleId;
    }

    public String getInspItemId() {
        return inspItemId;
    }

    public void setInspItemId(String inspItemId) {
        this.inspItemId = inspItemId;
    }

    public String getInspListId() {
        return inspListId;
    }

    public void setInspListId(String inspListId) {
        this.inspListId = inspListId;
    }

    public String getInspItemName() {
        return inspItemName;
    }

    public void setInspItemName(String inspItemName) {
        this.inspItemName = inspItemName;
    }
    
}
