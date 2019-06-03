package com.beidasoft.xzzf.informationEntry.model;

/**
 * 子表查封扣押决定书MODEL类
 */
public class InforEntryDecisionModel {
	// 状态值判断是新增还是修改
	private String editFlag;
	
    // 主键ID
    private String id;

    // 案件ID
    private String caseId;

    // 决定书类型(1.查封2.扣押)
    private String decisionType;

    // 查封扣押决定书文号
    private String decisionSymbol;

    // 物品保存地点
    private String address;

    // 物品保存期限开始时间
    private String startDateStr;

    // 物品保存期限结束时间
    private String endDateStr;

    // 物品清单文号
    private String itemSymbol;

    // 备注
    private String note;

    // 送达方式(1.直接送达2.留置送达3.委托送达4.邮寄送达5.公告送达)
    private String arrvelType;

    // 送达地点
    private String arrvelAddress;

    // 送达时间
    private String arrvelDateStr;

    // 送达人
    private String arrvelPerson;

    // 签收人
    private String receiptPerson;

    // 签收时间
    private String receiptDateStr;

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

    public String getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
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

    public String getArrvelDateStr() {
        return arrvelDateStr;
    }

    public void setArrvelDateStr(String arrvelDateStr) {
        this.arrvelDateStr = arrvelDateStr;
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

    public String getReceiptDateStr() {
        return receiptDateStr;
    }

    public void setReceiptDateStr(String receiptDateStr) {
        this.receiptDateStr = receiptDateStr;
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

	public String getEditFlag() {
		return editFlag;
	}

	public void setEditFlag(String editFlag) {
		this.editFlag = editFlag;
	}

}
