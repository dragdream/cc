package com.beidasoft.zfjd.inspection.inspRecord.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 执法检查记录检查项数据
 */
@Entity
@Table(name = "TBL_INSP_RECORD_DETAIL")
public class InspRecordDetail {
    // 数据唯一标识
    @Id
    @Column(name = "ID")
    private String id;

    // 执法检查单记录基础表id
    @Column(name = "RECORD_MAIN_ID")
    private String recordMainId;

    // 该检查项来源类型（1源自模版，2源自临时添加）
    @Column(name = "ITEM_SRC_TYPE")
    private Integer itemSrcType;

    // 检查项id
    @Column(name = "INSP_ITEM_ID")
    private String inspItemId;

    // 检查结果（1为合格，2为不合格）
    @Column(name = "IS_INSPECTION_PASS")
    private Integer isInspectionPass;

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecordMainId() {
        return recordMainId;
    }

    public void setRecordMainId(String recordMainId) {
        this.recordMainId = recordMainId;
    }

    public Integer getItemSrcType() {
        return itemSrcType;
    }

    public void setItemSrcType(Integer itemSrcType) {
        this.itemSrcType = itemSrcType;
    }

    public String getInspItemId() {
        return inspItemId;
    }

    public void setInspItemId(String inspItemId) {
        this.inspItemId = inspItemId;
    }

    public Integer getIsInspectionPass() {
        return isInspectionPass;
    }

    public void setIsInspectionPass(Integer isInspectionPass) {
        this.isInspectionPass = isInspectionPass;
    }
}
