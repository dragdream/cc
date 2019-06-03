package com.beidasoft.xzzf.informationEntry.model;

import javax.persistence.Column;

/**
 * 字表处罚决定MODEL类
 */
public class InforEntryPunishModel {
    // 主键ID
    private String id;

    // 案件ID
    private String caseId;
    
    //判断
    private String editFlag;
    
    // 处理呈批表文号
    private String batchSymbol;

    // 检查日期
    private String inspectDateStr;

    // 立案日期
    private String filingDateStr;

    // 现场检查及查实情况、证据内容  
    private String evidenceContent;

    // 告知及当事人申辩情况
    private String inform;

    // 有无从轻、减轻或者从重情形
    private String situation;
    
    // 处罚决定书文号
    @Column(name = "PUNISH_SYMBOL")
    private String punishSymbol;

    // 是否集体讨论(0.否1.是)
    private String discuss;

    // 是否警告(0.否1.是)
    private String warning;

    // 是否没收所得钱款(0.否1.是)
    private String confiscate;

    // 没收钱款数
    private String confiscateMoney;

    // 是否没收非法财务(0.否1.是)
    private String confiscation;

    // 物品名称
    private String confiscationName;

    // 规格
    private String confiscationSpec;

    // 数量
    private String confiscationNumber;

    // 是否罚款(0.否1.是)
    private String fine;

    // 罚款金额
    private String fineMoney;

    // 是否责令停业整顿(0.否1.是)
    private String suspend;

    // 停业整顿天数
    private String suspendDay;

    // 是否吊销许可证(0.否1.是)
    private String revoke;

    // 许可证
    private String revokeLicence;
    
    //是否其他意见(0.否1.是)
    private String other;
    
    //其他意见
    private String otherOpinion;

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

    public String getInspectDateStr() {
        return inspectDateStr;
    }

    public void setInspectDateStr(String inspectDateStr) {
        this.inspectDateStr = inspectDateStr;
    }

    public String getFilingDateStr() {
        return filingDateStr;
    }

    public void setFilingDateStr(String filingDateStr) {
        this.filingDateStr = filingDateStr;
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

	public String getEditFlag() {
		return editFlag;
	}

	public void setEditFlag(String editFlag) {
		this.editFlag = editFlag;
	}
	
}
