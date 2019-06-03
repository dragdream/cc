package com.beidasoft.zfjd.adminCoercion.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 强制措施管理实体类
 */
@Entity
@Table(name = "TBL_COERCION_CASE_POWER")
public class CoercionCasePower {
    // 唯一标识
    @Id
    @Column(name = "ID")
    private String id;

    // 采取强制方式
    @Column(name = "COERCION_TYPE")
    private Integer coercionType;

    // 强制行为表id
    @ManyToOne
    @JoinColumn(name = "COERCION_MEASURE_ID") // 这是双向的 要写JoinColumn 对面要写 mappedBy
    private CoercionMeasure coercionMeasurePower;

    // 强制行为表id
    @ManyToOne
    @JoinColumn(name = "COERCION_PERFORM_ID") // 这是双向的 要写JoinColumn 对面要写 mappedBy
    private CoercionPerform coercionPerformPower;

    // 强制职权ID
    @Column(name = "POWER_ID")
    private String powerId;

    // 强制案件主表id
    @Column(name = "COERCION_CASE_ID")
    private String coercionCaseId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCoercionType() {
        return coercionType;
    }

    public void setCoercionType(Integer coercionType) {
        this.coercionType = coercionType;
    }

    public CoercionMeasure getCoercionMeasurePower() {
        return coercionMeasurePower;
    }

    public void setCoercionMeasurePower(CoercionMeasure coercionMeasurePower) {
        this.coercionMeasurePower = coercionMeasurePower;
    }

    public CoercionPerform getCoercionPerformPower() {
        return coercionPerformPower;
    }

    public void setCoercionPerformPower(CoercionPerform coercionPerformPower) {
        this.coercionPerformPower = coercionPerformPower;
    }

    public String getPowerId() {
        return powerId;
    }

    public void setPowerId(String powerId) {
        this.powerId = powerId;
    }

    public String getCoercionCaseId() {
        return coercionCaseId;
    }

    public void setCoercionCaseId(String coercionCaseId) {
        this.coercionCaseId = coercionCaseId;
    }

    
}
