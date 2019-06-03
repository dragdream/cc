package com.beidasoft.zfjd.power.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
* 2018 
* @ClassName: PowerGist.java
* @Description: 职权依据
*
* @author: chenq
* @date: 2018年12月24日 下午3:20:57 
*
 */
@Entity
@Table(name="TBL_POWER_GIST")
public class PowerGist {
    // 主键
    @Id
    @Column(name = "ID")
    private String id;

    // 职权ID
    @ManyToOne
    @JoinColumn(name = "POWER_ID") // 这是双向的 要写JoinColumn 对面要写 mappedBy
    private Power powerGist;

    // 法律名称
    @Column(name = "LAW_NAME")
    private String lawName;

    // 法律条文ID
    @Column(name = "LAW_DETAIL_ID")
    private String lawDetailId;

    // 依据分类
    @Column(name = "GIST_TYPE")
    private String gistType;

    // 编
    @Column(name = "GIST_SERIES")
    private Integer gistSeries;

    // 章
    @Column(name = "GIST_CHAPTER")
    private Integer gistChapter;

    // 节
    @Column(name = "GIST_SECTION")
    private Integer gistSection;

    // 条
    @Column(name = "GIST_STRIP")
    private Integer gistStrip;

    // 款
    @Column(name = "GIST_FUND")
    private Integer gistFund;

    // 项
    @Column(name = "GIST_ITEM")
    private Integer gistItem;
    
    @Column(name = "GIST_CATALOG")
    private Integer gistCatalog;

    // 内容
    @Column(name = "CONTENT")
    private String content;

    // 依据编号
    @Column(name = "GIST_CODE")
    private String gistCode;

    // 执法部门ID
    @Column(name = "SUBJECT_ID")
    private String subjectId;

    // 创建日期
    @Column(name = "CREATE_TIME")
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Power getPowerGist() {
        return powerGist;
    }

    public void setPowerGist(Power powerGist) {
        this.powerGist = powerGist;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
