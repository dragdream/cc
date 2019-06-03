package com.beidasoft.zfjd.law.bean;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 法条表实体类
 */
@Entity
@Table(name="TBL_BASE_LAW_DETAIL")
public class TblLawDetail {
    // 法律内容管理-条文id(依据id)
    @Id
//    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
//    @GeneratedValue(generator = "system-uuid")
    @Column(name = "ID")
    private String id;

    // 所属法律id
    @Column(name = "LAW_ID")
    private String lawId;

    // 所属法律名称
    @Column(name = "LAW_NAME")
    private String lawName;

    // 编
    @Column(name = "DETAIL_SERIES")
    private Integer detailSeries;

    // 章
    @Column(name = "DETAIL_CHAPTER")
    private Integer detailChapter;

    // 节
    @Column(name = "DETAIL_SECTION")
    private Integer detailSection;

    // 条
    @Column(name = "DETAIL_STRIP")
    private Integer detailStrip;

    // 款
    @Column(name = "DETAIL_FUND")
    private Integer detailFund;

    // 项
    @Column(name = "DETAIL_ITEM")
    private Integer detailItem;
    
    //目
    @Column(name = "DETAIL_CATALOG")
    private Integer detailCatalog;

    // 内容
    @Column(name = "CONTENT")
    private String content;

    // 0未删除，1已删除
    @Column(name = "IS_DELETE")
    private int isDelete;

    // 章标题
    @Column(name = "CHAPTER_CAPTION")
    private String chapterCaption;

    // 法律条文编号
    @Column(name = "LAW_DETAIL_INDEX")
    private String lawDetailIndex;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLawId() {
		return lawId;
	}

	public void setLawId(String lawId) {
		this.lawId = lawId;
	}

	public String getLawName() {
		return lawName;
	}

	public void setLawName(String lawName) {
		this.lawName = lawName;
	}

	public int getDetailSeries() {
		return detailSeries;
	}

	public void setDetailSeries(int detailSeries) {
		this.detailSeries = detailSeries;
	}

	public Integer getDetailChapter() {
		return detailChapter;
	}

	public void setDetailChapter(Integer detailChapter) {
		this.detailChapter = detailChapter;
	}

	public Integer getDetailSection() {
		return detailSection;
	}

	public void setDetailSection(Integer detailSection) {
		this.detailSection = detailSection;
	}

	public Integer getDetailStrip() {
		return detailStrip;
	}

	public void setDetailStrip(Integer detailStrip) {
		this.detailStrip = detailStrip;
	}

	public Integer getDetailFund() {
		return detailFund;
	}

	public void setDetailFund(Integer detailFund) {
		this.detailFund = detailFund;
	}

	public Integer getDetailItem() {
		return detailItem;
	}

	public void setDetailItem(Integer detailItem) {
		this.detailItem = detailItem;
	}

	public Integer getDetailCatalog() {
		return detailCatalog;
	}

	public void setDetailCatalog(Integer detailCatalog) {
		this.detailCatalog = detailCatalog;
	}

	public void setDetailSeries(Integer detailSeries) {
		this.detailSeries = detailSeries;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public String getChapterCaption() {
		return chapterCaption;
	}

	public void setChapterCaption(String chapterCaption) {
		this.chapterCaption = chapterCaption;
	}

	public String getLawDetailIndex() {
		return lawDetailIndex;
	}

	public void setLawDetailIndex(String lawDetailIndex) {
		this.lawDetailIndex = lawDetailIndex;
	}

    
    
   
    
    

}
