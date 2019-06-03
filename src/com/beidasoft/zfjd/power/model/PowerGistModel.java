package com.beidasoft.zfjd.power.model;

import java.util.Date;

public class PowerGistModel {
    // 主键
    private String id;

    // 职权ID
    private String powerId;
    
    // 职权类型
    private String powerType;
    
    // 职权名称
    private String powerName;

    // 法律名称
    private String lawName;

    // 法律条文ID
    private String lawDetailId;

    // 依据分类
    private String gistType;

    // 编
    private Integer gistSeries;

    // 章
    private Integer gistChapter;

    // 节
    private Integer gistSection;

    // 条
    private Integer gistStrip;

    // 款
    private Integer gistFund;

    // 项
    private Integer gistItem;

    //目
    private Integer gistCatalog;
    
    // 内容
    private String content;

    // 依据编号
    private String gistCode;

    // 执法部门ID
    private String subjectId;
    
    // 执法部门ID组
    private String subjectIds;

    // 创建日期
    private String createTimeStr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPowerId() {
        return powerId;
    }

    public void setPowerId(String powerId) {
        this.powerId = powerId;
    }

    public String getPowerType() {
        return powerType;
    }

    public void setPowerType(String powerType) {
        this.powerType = powerType;
    }

    public String getPowerName() {
        return powerName;
    }

    public void setPowerName(String powerName) {
        this.powerName = powerName;
    }

    public String getLawName() {
        return lawName;
    }

    public void setLawName(String lawName) {
        this.lawName = lawName;
    }

    public String getLawDetailId() {
        return lawDetailId;
    }

    public void setLawDetailId(String lawDetailId) {
        this.lawDetailId = lawDetailId;
    }

    public String getGistType() {
        return gistType;
    }

    public void setGistType(String gistType) {
        this.gistType = gistType;
    }

    public Integer getGistSeries() {
        return gistSeries;
    }

    public void setGistSeries(Integer gistSeries) {
        this.gistSeries = gistSeries;
    }

    public Integer getGistChapter() {
        return gistChapter;
    }

    public void setGistChapter(Integer gistChapter) {
        this.gistChapter = gistChapter;
    }

    public Integer getGistSection() {
        return gistSection;
    }

    public void setGistSection(Integer gistSection) {
        this.gistSection = gistSection;
    }

    public Integer getGistStrip() {
        return gistStrip;
    }

    public void setGistStrip(Integer gistStrip) {
        this.gistStrip = gistStrip;
    }

    public Integer getGistFund() {
        return gistFund;
    }

    public void setGistFund(Integer gistFund) {
        this.gistFund = gistFund;
    }

    public Integer getGistItem() {
        return gistItem;
    }

    public void setGistItem(Integer gistItem) {
        this.gistItem = gistItem;
    }

    public Integer getGistCatalog() {
        return gistCatalog;
    }

    public void setGistCatalog(Integer gistCatalog) {
        this.gistCatalog = gistCatalog;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGistCode() {
        return gistCode;
    }

    public void setGistCode(String gistCode) {
        this.gistCode = gistCode;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectIds() {
        return subjectIds;
    }

    public void setSubjectIds(String subjectIds) {
        this.subjectIds = subjectIds;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }
}
