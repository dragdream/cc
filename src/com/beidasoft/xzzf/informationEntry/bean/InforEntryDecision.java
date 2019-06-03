package com.beidasoft.xzzf.informationEntry.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 子表查封扣押决定书实体类
 */
@Entity
@Table(name="ZF_INFOR_ENTRY_DECISION")
public class InforEntryDecision {
    // 主键ID
    @Id
    @Column(name = "ID")
    private String id;

    // 案件ID
    @Column(name = "CASE_ID")
    private String caseId;

    // 决定书类型(1.查封2.扣押)
    @Column(name = "DECISION_TYPE")
    private String decisionType;

    // 查封扣押决定书文号
    @Column(name = "DECISION_SYMBOL")
    private String decisionSymbol;

    // 物品保存地点
    @Column(name = "ADDRESS")
    private String address;

    // 物品保存期限开始时间
    @Column(name = "START_DATE")
    private Date startDate;

    // 物品保存期限结束时间
    @Column(name = "END_DATE")
    private Date endDate;

    // 物品清单文号
    @Column(name = "ITEM_SYMBOL")
    private String itemSymbol;

    // 备注
    @Column(name = "NOTE")
    private String note;

    // 送达方式(1.直接送达2.留置送达3.委托送达4.邮寄送达5.公告送达)
    @Column(name = "ARRVEL_TYPE")
    private String arrvelType;

    // 送达地点
    @Column(name = "ARRVEL_ADDRESS")
    private String arrvelAddress;

    // 送达时间
    @Column(name = "ARRVEL_DATE")
    private Date arrvelDate;

    // 送达人
    @Column(name = "ARRVEL_PERSON")
    private String arrvelPerson;

    // 签收人
    @Column(name = "RECEIPT_PERSON")
    private String receiptPerson;

    // 签收时间
    @Column(name = "RECEIPT_DATE")
    private Date receiptDate;

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

    public String getDecisionType() {
        return decisionType;
    }

    public void setDecisionType(String decisionType) {
        this.decisionType = decisionType;
    }

    public String getDecisionSymbol() {
        return decisionSymbol;
    }

    public void setDecisionSymbol(String decisionSymbol) {
        this.decisionSymbol = decisionSymbol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getItemSymbol() {
        return itemSymbol;
    }

    public void setItemSymbol(String itemSymbol) {
        this.itemSymbol = itemSymbol;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getArrvelType() {
        return arrvelType;
    }

    public void setArrvelType(String arrvelType) {
        this.arrvelType = arrvelType;
    }

    public String getArrvelAddress() {
        return arrvelAddress;
    }

    public void setArrvelAddress(String arrvelAddress) {
        this.arrvelAddress = arrvelAddress;
    }

    public Date getArrvelDate() {
        return arrvelDate;
    }

    public void setArrvelDate(Date arrvelDate) {
        this.arrvelDate = arrvelDate;
    }

    public String getArrvelPerson() {
        return arrvelPerson;
    }

    public void setArrvelPerson(String arrvelPerson) {
        this.arrvelPerson = arrvelPerson;
    }

    public String getReceiptPerson() {
        return receiptPerson;
    }

    public void setReceiptPerson(String receiptPerson) {
        this.receiptPerson = receiptPerson;
    }

    public Date getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(Date receiptDate) {
        this.receiptDate = receiptDate;
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
