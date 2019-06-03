package com.beidasoft.xzzf.informationEntry.model;

/**
 * 历史信息录入主表MODEL类
 */
public class InforEntryBaseModel {
	//执法人员id串
	private String personJsonStr;
	
	// 状态值判断是新增还是修改
	private String editFlag;
	
    // 主键ID
    private String id;

    // 案件名称
    private String baseName;

    // 立案审批表文号
    private String baseSymbol;

    // 当事人名称
    private String partyName;

    // 当事人证件类型(1.统一社会信用代码，2.注册号，3.其他)
    private String psnType;

    // 当事人证件号码
    private String psnIdNo;

    // 当事人类型(1.法定代表人2.负责人3.投资人4.其他)
    private String partyType;

    // 其他
    private String partyTypeName;

    // 联系电话
    private String psnTel;

    // 住所（住址）
    private String psnAddress;

    // 案件来源
    private String caseSource;

    // 案由
    private String causeAction;

    // 案情概要
    private String caseOverview;

    // 备注
    private String note;

    // 营业执照字号
    private String selfemployedCode;

    // 营业执照证号
    private String compnyCode;

    // 营业地址
    private String compnyAddress;

    // 企业名称
    private String compnyName;

    // 注册号
    private String compnyNumber;

    // 许可内容
    private String permitContent;

    // 许可决定日期
    private String permitDecisionDateStr;

    // 许可截至日期
    private String permitClosingDateStr;

    // 许可机关
    private String permitOffice;

    // 许可状态(1.正常 2.撤销 3.异议 4.其他)
    private String permitState;

    // 审批类型(1.普通  2.特许 3.认可 4.核准 5.登记 6.其他 )
    private String approvalType;

    // 行政许可决定书文号
    private String permitSymbol;

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
    
    // 流程步骤标识
    private Integer isNext;
    
    // 删除标识
    private Integer isDelete;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public String getBaseSymbol() {
        return baseSymbol;
    }

    public void setBaseSymbol(String baseSymbol) {
        this.baseSymbol = baseSymbol;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getPsnType() {
        return psnType;
    }

    public void setPsnType(String psnType) {
        this.psnType = psnType;
    }

    public String getPsnIdNo() {
        return psnIdNo;
    }

    public void setPsnIdNo(String psnIdNo) {
        this.psnIdNo = psnIdNo;
    }

    public String getPartyType() {
        return partyType;
    }

    public void setPartyType(String partyType) {
        this.partyType = partyType;
    }

    public String getPartyTypeName() {
        return partyTypeName;
    }

    public void setPartyTypeName(String partyTypeName) {
        this.partyTypeName = partyTypeName;
    }

    public String getPsnTel() {
        return psnTel;
    }

    public void setPsnTel(String psnTel) {
        this.psnTel = psnTel;
    }

    public String getPsnAddress() {
        return psnAddress;
    }

    public void setPsnAddress(String psnAddress) {
        this.psnAddress = psnAddress;
    }

    public String getCaseSource() {
        return caseSource;
    }

    public void setCaseSource(String caseSource) {
        this.caseSource = caseSource;
    }

    public String getCauseAction() {
        return causeAction;
    }

    public void setCauseAction(String causeAction) {
        this.causeAction = causeAction;
    }

    public String getCaseOverview() {
        return caseOverview;
    }

    public void setCaseOverview(String caseOverview) {
        this.caseOverview = caseOverview;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSelfemployedCode() {
        return selfemployedCode;
    }

    public void setSelfemployedCode(String selfemployedCode) {
        this.selfemployedCode = selfemployedCode;
    }

    public String getCompnyCode() {
        return compnyCode;
    }

    public void setCompnyCode(String compnyCode) {
        this.compnyCode = compnyCode;
    }

    public String getCompnyAddress() {
        return compnyAddress;
    }

    public void setCompnyAddress(String compnyAddress) {
        this.compnyAddress = compnyAddress;
    }

    public String getCompnyName() {
        return compnyName;
    }

    public void setCompnyName(String compnyName) {
        this.compnyName = compnyName;
    }

    public String getCompnyNumber() {
        return compnyNumber;
    }

    public void setCompnyNumber(String compnyNumber) {
        this.compnyNumber = compnyNumber;
    }

    public String getPermitContent() {
        return permitContent;
    }

    public void setPermitContent(String permitContent) {
        this.permitContent = permitContent;
    }

    public String getPermitDecisionDateStr() {
        return permitDecisionDateStr;
    }

    public void setPermitDecisionDateStr(String permitDecisionDateStr) {
        this.permitDecisionDateStr = permitDecisionDateStr;
    }

    public String getPermitClosingDateStr() {
        return permitClosingDateStr;
    }

    public void setPermitClosingDateStr(String permitClosingDateStr) {
        this.permitClosingDateStr = permitClosingDateStr;
    }

    public String getPermitOffice() {
        return permitOffice;
    }

    public void setPermitOffice(String permitOffice) {
        this.permitOffice = permitOffice;
    }

    public String getPermitState() {
        return permitState;
    }

    public void setPermitState(String permitState) {
        this.permitState = permitState;
    }

    public String getApprovalType() {
        return approvalType;
    }

    public void setApprovalType(String approvalType) {
        this.approvalType = approvalType;
    }

    public String getPermitSymbol() {
        return permitSymbol;
    }

    public void setPermitSymbol(String permitSymbol) {
        this.permitSymbol = permitSymbol;
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

	public String getPersonJsonStr() {
		return personJsonStr;
	}

	public void setPersonJsonStr(String personJsonStr) {
		this.personJsonStr = personJsonStr;
	}

	public Integer getIsNext() {
		return isNext;
	}

	public void setIsNext(Integer isNext) {
		this.isNext = isNext;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	
}
