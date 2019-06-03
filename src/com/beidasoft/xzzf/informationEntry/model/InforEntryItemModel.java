package com.beidasoft.xzzf.informationEntry.model;

/**
 * 子表物品清单MODEL类
 */
public class InforEntryItemModel {
    // 主键ID
    private String id;

    // 案件ID
    private String caseId;

    // 物品清单文号
    private String itemSymbol;

    // 序号
    private String itemSerial;

    // 物品名称
    private String itemName;

    // 计量单位 (规格)
    private String itemSpec;

    // 数量
    private String itemNumber;

    // 备注
    private String note;

    // 创建人
    private String createName;

    // 创建人ID
    private String createId;

    // 创建时间
    private String createDateStr;

    // 创建人部门
    private String createDept;

    // 修改人
    private String updateName;

    // 修改人ID
    private String updateId;

    // 修改时间
    private String updateDateStr;

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

    public String getItemSymbol() {
        return itemSymbol;
    }

    public void setItemSymbol(String itemSymbol) {
        this.itemSymbol = itemSymbol;
    }

    public String getItemSerial() {
        return itemSerial;
    }

    public void setItemSerial(String itemSerial) {
        this.itemSerial = itemSerial;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemSpec() {
        return itemSpec;
    }

    public void setItemSpec(String itemSpec) {
        this.itemSpec = itemSpec;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public String getCreateDept() {
        return createDept;
    }

    public void setCreateDept(String createDept) {
        this.createDept = createDept;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public String getUpdateDateStr() {
        return updateDateStr;
    }

    public void setUpdateDateStr(String updateDateStr) {
        this.updateDateStr = updateDateStr;
    }

}
