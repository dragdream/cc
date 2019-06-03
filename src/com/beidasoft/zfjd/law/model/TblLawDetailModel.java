package com.beidasoft.zfjd.law.model;

/**
 * 职权基础信息表MODEL类
 */
public class TblLawDetailModel {
    // 法律内容管理-条文id(依据id)
    private String id;

    // 所属法律id
    private String lawId;

    // 所属法律名称
    private String lawName;

    // 编
    private int detailSeries;

    // 章
    private Integer detailChapter;

    // 节
    private Integer detailSection;

    // 条
    private Integer detailStrip;

    // 款
    private Integer detailFund;

    // 项
    private Integer detailItem;
    
    //目
    private Integer detailCatalog;

    // 内容
    private String content;

    // 0未删除，1已删除
    private Integer isDelete;

    // 章标题
    private String chapterCaption;

    // 法律条文编号
    private String lawDetailIndex;
    
    private String ids;

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

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
    
    

    
}
