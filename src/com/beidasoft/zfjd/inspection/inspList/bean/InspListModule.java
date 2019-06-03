package com.beidasoft.zfjd.inspection.inspList.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 执法检查实体类
 */
@Entity
@Table(name="TBL_INSP_LIST_MODULE")
public class InspListModule {
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
  
    // 检查单id
    @Column(name = "INSP_LIST_ID")
    private String inspListId;

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

    public String getInspListId() {
        return inspListId;
    }

    public void setInspListId(String inspListId) {
        this.inspListId = inspListId;
    }
    
    
}
