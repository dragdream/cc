package com.beidasoft.xzzf.informationEntry.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 历史信息录入主表实体类
 */
@Entity
@Table(name="ZF_INFOR_ENTRY_BASE")
public class InforEntryBase {
    // 主键ID
    @Id
    @Column(name = "ID")
    private String id;
 
    // 案件名称
    @Column(name = "BASE_NAME")
    private String baseName;

    // 立案审批表文号
    @Column(name = "BASE_SYMBOL")
    private String baseSymbol;

    // 当事人名称
    @Column(name = "PARTY_NAME")
    private String partyName;

    // 当事人证件类型(1.统一社会信用代码，2.注册号，3.其他)
    @Column(name = "PSN_TYPE")
    private String psnType;

    // 当事人证件号码
    @Column(name = "PSN_ID_NO")
    private String psnIdNo;

    // 当事人类型(1.法定代表人2.负责人3.投资人4.其他)
    @Column(name = "PARTY_TYPE")
    private String partyType;

    // 其他
    @Column(name = "PARTY_TYPE_NAME")
    private String partyTypeName;

    // 联系电话
    @Column(name = "PSN_TEL")
    private String psnTel;

    // 住所（住址）
    @Column(name = "PSN_ADDRESS")
    private String psnAddress;

    // 案件来源(1.日常检查、2.群众举报、3.上级交办、4.其他部门转办、5.其他)
    @Column(name = "CASE_SOURCE")
    private String caseSource;

    // 案由
    @Column(name = "CAUSE_ACTION")
    private String causeAction;

    // 案情概要
    @Column(name = "CASE_OVERVIEW")
    private String caseOverview;

    // 备注
    @Column(name = "NOTE")
    private String note;

    // 营业执照字号
    @Column(name = "SELFEMPLOYED_CODE")
    private String selfemployedCode;

    // 营业执照证号
    @Column(name = "COMPNY_CODE")
    private String compnyCode;

    // 营业地址
    @Column(name = "COMPNY_ADDRESS")
    private String compnyAddress;

    // 企业名称
    @Column(name = "COMPNY_NAME")
    private String compnyName;

    // 注册号
    @Column(name = "COMPNY_NUMBER")
    private String compnyNumber;

    // 许可内容
    @Column(name = "PERMIT_CONTENT")
    private String permitContent;

    // 许可决定日期
    @Column(name = "PERMIT_DECISION_DATE")
    private Date permitDecisionDate;

    // 许可截至日期
    @Column(name = "PERMIT_CLOSING_DATE")
    private Date permitClosingDate;

    // 许可机关
    @Column(name = "PERMIT_OFFICE")
    private String permitOffice;

    // 许可状态(1.正常 2.撤销 3.异议 4.其他)
    @Column(name = "PERMIT_STATE")
    private String permitState;

    // 审批类型(1.普通  2.特许 3.认可 4.核准 5.登记 6.其他 )
    @Column(name = "APPROVAL_TYPE")
    private String approvalType;

    // 行政许可决定书文号
    @Column(name = "PERMIT_SYMBOL")
    private String permitSymbol;

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
    
    // 流程步骤标识
    @Column(name = "IS_NEXT")
    private Integer isNext;
    
    // 删除标识
    @Column(name = "IS_DELETE")
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

    public Date getPermitDecisionDate() {
        return permitDecisionDate;
    }

    public void setPermitDecisionDate(Date permitDecisionDate) {
        this.permitDecisionDate = permitDecisionDate;
    }

    public Date getPermitClosingDate() {
        return permitClosingDate;
    }

    public void setPermitClosingDate(Date permitClosingDate) {
        this.permitClosingDate = permitClosingDate;
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
