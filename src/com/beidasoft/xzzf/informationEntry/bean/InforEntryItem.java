package com.beidasoft.xzzf.informationEntry.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 子表物品清单实体类
 */
@Entity
@Table(name="ZF_INFOR_ENTRY_ITEM")
public class InforEntryItem {
    // 主键ID
    @Id
    @Column(name = "ID")
    private String id;

    // 案件ID
    @Column(name = "CASE_ID")
    private String caseId;

    // 物品清单文号
    @Column(name = "ITEM_SYMBOL")
    private String itemSymbol;

    // 序号
    @Column(name = "ITEM_SERIAL")
    private String itemSerial;

    // 物品名称
    @Column(name = "ITEM_NAME")
    private String itemName;

    // 计量单位 (规格)
    @Column(name = "ITEM_SPEC")
    private String itemSpec;

    // 数量
    @Column(name = "ITEM_NUMBER")
    private String itemNumber;

    // 备注
    @Column(name = "NOTE")
    private String note;

    // 创建人
    @Column(name = "CREATE_NAME")
    private String createName;

    // 创建人ID
    @Column(name = "CREATE_ID")
    private String createId;

    // 创建时间
    @Column(name = "CREATE_DATE")
    private Date createDate;

    // 创建人部门
    @Column(name = "CREATE_DEPT")
    private String createDept;

    // 修改人
    @Column(name = "UPDATE_NAME")
    private String updateName;

    // 修改人ID
    @Column(name = "UPDATE_ID")
    private String updateId;

    // 修改时间
    @Column(name = "UPDATE_DATE")
    private Date updateDate;

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

}
