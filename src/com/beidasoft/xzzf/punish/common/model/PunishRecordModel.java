package com.beidasoft.xzzf.punish.common.model;


public class PunishRecordModel {
	// 借还记录主键
    private String punishRecordId;

    // 案件唯一标识
    private String baseId;
    
    // 操作环节名称
    private String confTacheName;
    
    // 操作人员
    private int opeartionPerson;
    
    // 操作时间
    private String opeartionTimeStr;
    
    // 操作流程ID
    private int operationRunId;
    
    // 操作说明
    private String operationContent;
    
    // 操作人员姓名
    private String opeartionPersonName;

	public String getPunishRecordId() {
		return punishRecordId;
	}

	public void setPunishRecordId(String punishRecordId) {
		this.punishRecordId = punishRecordId;
	}

	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}

	public String getConfTacheName() {
		return confTacheName;
	}

	public void setConfTacheName(String confTacheName) {
		this.confTacheName = confTacheName;
	}

	public int getOpeartionPerson() {
		return opeartionPerson;
	}

	public void setOpeartionPerson(int opeartionPerson) {
		this.opeartionPerson = opeartionPerson;
	}

	public String getOpeartionTimeStr() {
		return opeartionTimeStr;
	}

	public void setOpeartionTimeStr(String opeartionTimeStr) {
		this.opeartionTimeStr = opeartionTimeStr;
	}

	public int getOperationRunId() {
		return operationRunId;
	}

	public void setOperationRunId(int operationRunId) {
		this.operationRunId = operationRunId;
	}

	public String getOperationContent() {
		return operationContent;
	}

	public void setOperationContent(String operationContent) {
		this.operationContent = operationContent;
	}

	public String getOpeartionPersonName() {
		return opeartionPersonName;
	}

	public void setOpeartionPersonName(String opeartionPersonName) {
		this.opeartionPersonName = opeartionPersonName;
	}
    
}
