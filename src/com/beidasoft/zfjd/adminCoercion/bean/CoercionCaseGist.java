package com.beidasoft.zfjd.adminCoercion.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 强制案件依据表实体类
 */
@Entity
@Table(name = "TBL_COERCION_CASE_GIST")
public class CoercionCaseGist {
    // 唯一标识
    @Id
    @Column(name = "ID")
    private String id;

    // 强制行为表id
    @ManyToOne
    @JoinColumn(name = "COERCION_MEASURE_ID") // 这是双向的 要写JoinColumn 对面要写 mappedBy
    private CoercionMeasure coercionMeasureGist;

    // 强制行为表id
    @ManyToOne
    @JoinColumn(name = "COERCION_PERFORM_ID") // 这是双向的 要写JoinColumn 对面要写 mappedBy
    private CoercionPerform coercionPerformGist;
    
    // 依据表id
    @Column(name = "COERCION_CASE_ID")
    private String coercionCaseId;
    
    // 依据表id
    @Column(name = "GIST_ID")
    private String gistId;

    // 法律名称
    @Column(name = "LAW_NAME")
    private String lawName;

    // 条
    @Column(name = "STRIP")
    private Integer strip;

    // 款
    @Column(name = "FUND")
    private Integer fund;

    // 项
    @Column(name = "ITEM")
    private Integer item;

    // 目
    @Column(name = "CATALOG")
    private Integer catalog;

    // 内容
    @Column(name = "CONTENT")
    private String content;

    // 入库日期
    @Column(name = "CREATE_DATE")
    private Date createDate;

    // 强制行为种类
    @Column(name = "COERCION_TYPE")
    private Integer coercionType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CoercionMeasure getCoercionMeasureGist() {
        return coercionMeasureGist;
    }

    public void setCoercionMeasureGist(CoercionMeasure coercionMeasureGist) {
        this.coercionMeasureGist = coercionMeasureGist;
    }

    public CoercionPerform getCoercionPerformGist() {
        return coercionPerformGist;
    }

    public void setCoercionPerformGist(CoercionPerform coercionPerformGist) {
        this.coercionPerformGist = coercionPerformGist;
    }

    public String getCoercionCaseId() {
        return coercionCaseId;
    }

    public void setCoercionCaseId(String coercionCaseId) {
        this.coercionCaseId = coercionCaseId;
    }

    public String getGistId() {
        return gistId;
    }

    public void setGistId(String gistId) {
        this.gistId = gistId;
    }

    public String getLawName() {
        return lawName;
    }

    public void setLawName(String lawName) {
        this.lawName = lawName;
    }

    public Integer getStrip() {
        return strip;
    }

    public void setStrip(Integer strip) {
        this.strip = strip;
    }

    public Integer getFund() {
        return fund;
    }

    public void setFund(Integer fund) {
        this.fund = fund;
    }

    public Integer getItem() {
        return item;
    }

    public void setItem(Integer item) {
        this.item = item;
    }

    public Integer getCatalog() {
        return catalog;
    }

    public void setCatalog(Integer catalog) {
        this.catalog = catalog;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getCoercionType() {
        return coercionType;
    }

    public void setCoercionType(Integer coercionType) {
        this.coercionType = coercionType;
    }
}
