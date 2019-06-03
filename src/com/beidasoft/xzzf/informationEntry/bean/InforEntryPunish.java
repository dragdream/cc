package com.beidasoft.xzzf.informationEntry.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 字表处罚决定实体类
 */
@Entity
@Table(name="ZF_INFOR_ENTRY_PUNISH")
public class InforEntryPunish {
    // 主键ID
    @Id
    @Column(name = "ID")
    private String id;

    // 案件ID
    @Column(name = "CASE_ID")
    private String caseId;
    
    // 处理呈批表文号
    @Column(name = "BATCH_SYMBOL")
    private String batchSymbol;

    // 检查日期
    @Column(name = "INSPECT_DATE")
    private Date inspectDate;

    // 立案日期
    @Column(name = "FILING_DATE")
    private Date filingDate;

    // 现场检查及查实情况、证据内容  
    @Column(name = "EVIDENCE_CONTENT")
    private String evidenceContent;

    // 告知及当事人申辩情况
    @Column(name = "INFORM")
    private String inform;

    // 有无从轻、减轻或者从重情形
    @Column(name = "SITUATION")
    private String situation;

    // 处罚决定书文号
    @Column(name = "PUNISH_SYMBOL")
    private String punishSymbol;
    
    // 是否集体讨论(0.否1.是)
    @Column(name = "DISCUSS")
    private String discuss;

    // 是否警告(0.否1.是)
    @Column(name = "WARNING")
    private String warning;

    // 是否没收所得钱款(0.否1.是)
    @Column(name = "CONFISCATE")
    private String confiscate;

    // 没收钱款数
    @Column(name = "CONFISCATE_MONEY")
    private String confiscateMoney;

    // 是否没收非法财务(0.否1.是)
    @Column(name = "CONFISCATION")
    private String confiscation;

    // 物品名称
    @Column(name = "CONFISCATION_NAME")
    private String confiscationName;

    // 规格
    @Column(name = "CONFISCATION_SPEC")
    private String confiscationSpec;

    // 数量
    @Column(name = "CONFISCATION_NUMBER")
    private String confiscationNumber;

    // 是否罚款(0.否1.是)
    @Column(name = "FINE")
    private String fine;

    // 罚款金额
    @Column(name = "FINE_MONEY")
    private String fineMoney;

    // 是否责令停业整顿(0.否1.是)
    @Column(name = "SUSPEND")
    private String suspend;

    // 停业整顿天数
    @Column(name = "SUSPEND_DAY")
    private String suspendDay;

    // 是否吊销许可证(0.否1.是)
    @Column(name = "REVOKE")
    private String revoke;

    // 许可证
    @Column(name = "REVOKE_LICENCE")
    private String revokeLicence;
    
    //是否其他意见(0.否1.是)
    @Column(name = "OTHER")
    private String other;
    
    //其他意见
    @Column(name = "OTHER_OPINION")
    private String otherOpinion;

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

    public Date getInspectDate() {
        return inspectDate;
    }

    public void setInspectDate(Date inspectDate) {
        this.inspectDate = inspectDate;
    }

    public Date getFilingDate() {
        return filingDate;
    }

    public void setFilingDate(Date filingDate) {
        this.filingDate = filingDate;
    }

    public String getEvidenceContent() {
        return evidenceContent;
    }

    public void setEvidenceContent(String evidenceContent) {
        this.evidenceContent = evidenceContent;
    }

    public String getInform() {
        return inform;
    }

    public void setInform(String inform) {
        this.inform = inform;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public String getDiscuss() {
        return discuss;
    }

    public void setDiscuss(String discuss) {
        this.discuss = discuss;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public String getConfiscate() {
        return confiscate;
    }

    public void setConfiscate(String confiscate) {
        this.confiscate = confiscate;
    }

    public String getConfiscateMoney() {
        return confiscateMoney;
    }

    public void setConfiscateMoney(String confiscateMoney) {
        this.confiscateMoney = confiscateMoney;
    }

    public String getConfiscation() {
        return confiscation;
    }

    public void setConfiscation(String confiscation) {
        this.confiscation = confiscation;
    }

    public String getConfiscationName() {
        return confiscationName;
    }

    public void setConfiscationName(String confiscationName) {
        this.confiscationName = confiscationName;
    }

    public String getConfiscationSpec() {
        return confiscationSpec;
    }

    public void setConfiscationSpec(String confiscationSpec) {
        this.confiscationSpec = confiscationSpec;
    }

    public String getConfiscationNumber() {
        return confiscationNumber;
    }

    public void setConfiscationNumber(String confiscationNumber) {
        this.confiscationNumber = confiscationNumber;
    }

    public String getFine() {
        return fine;
    }

    public void setFine(String fine) {
        this.fine = fine;
    }

    public String getFineMoney() {
        return fineMoney;
    }

    public void setFineMoney(String fineMoney) {
        this.fineMoney = fineMoney;
    }

    public String getSuspend() {
        return suspend;
    }

    public void setSuspend(String suspend) {
        this.suspend = suspend;
    }

    public String getSuspendDay() {
        return suspendDay;
    }

    public void setSuspendDay(String suspendDay) {
        this.suspendDay = suspendDay;
    }

    public String getRevoke() {
        return revoke;
    }

    public void setRevoke(String revoke) {
        this.revoke = revoke;
    }

    public String getRevokeLicence() {
        return revokeLicence;
    }

    public void setRevokeLicence(String revokeLicence) {
        this.revokeLicence = revokeLicence;
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

	public String getBatchSymbol() {
		return batchSymbol;
	}

	public void setBatchSymbol(String batchSymbol) {
		this.batchSymbol = batchSymbol;
	}

	public String getPunishSymbol() {
		return punishSymbol;
	}

	public void setPunishSymbol(String punishSymbol) {
		this.punishSymbol = punishSymbol;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getOtherOpinion() {
		return otherOpinion;
	}

	public void setOtherOpinion(String otherOpinion) {
		this.otherOpinion = otherOpinion;
	}

}
