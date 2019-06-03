package com.beidasoft.xzzf.punish.common.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ZF_PUNISH_OPERATION")
public class PunishOperation {
	// 操作记录主键
    @Id
    @Column(name = "PUNISH_OPERATION_ID")
    private String punishOperationId;

    // 案件唯一标识
    @Column(name = "BASE_ID")
    private String baseId;

    // 操作记录排序
    @Column(name = "OPERATION_INDEX")
    private int operationIndex;

    // 操作环节CODE
    @Column(name = "CONF_TACHE_CODE")
    private String confTacheCode;
    
    // 操作环节名称
    @Column(name = "CONF_TACHE_NAME")
    private String confTacheName;
    
    // 操作人员
    @Column(name = "OPEARTION_PERSON")
    private int opeartionPerson;
    
    // 操作时间
    @Column(name = "OPEARTION_TIME")
    private Date opeartionTime;
    
    // 操作流程ID
    @Column(name = "OPERATION_RUN_ID")
    private int operationRunId;
    
    // 操作说明
    @Column(name = "OPERATION_CONTENT")
    private String operationContent;
    
    // 操作人员姓名
    @Column(name = "OPEARTION_PERSON_NAME")
    private String opeartionPersonName;

	public String getPunishOperationId() {
		return punishOperationId;
	}

	public void setPunishOperationId(String punishOperationId) {
		this.punishOperationId = punishOperationId;
	}

	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}

	public int getOperationIndex() {
		return operationIndex;
	}

	public void setOperationIndex(int operationIndex) {
		this.operationIndex = operationIndex;
	}

	public String getConfTacheCode() {
		return confTacheCode;
	}

	public void setConfTacheCode(String confTacheCode) {
		this.confTacheCode = confTacheCode;
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

	public Date getOpeartionTime() {
		return opeartionTime;
	}

	public void setOpeartionTime(Date opeartionTime) {
		this.opeartionTime = opeartionTime;
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
