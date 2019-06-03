package com.beidasoft.xzzf.queries.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 职权依据表实体类
 */
@Entity
@Table(name="FX_BASE_POWER_GIST ")
public class PowerBaseGist {
	@Id
    @Column(name = "ID")
    private String id; // 主键

    @Column(name = "POWER_ID")
    private String powerId; // 职权ID

    @Column(name = "LAW_NAME")
    private String lawName;  // 法律名称

    @Column(name = "LAW_DETAIL_ID")
    private String lawDetailId; // 法律条文ID

    @Column(name = "GIST_TYPE")
    private String gistType; // 依据分类

    @Column(name = "SERIES")
    private int series; // 编

    @Column(name = "CHAPTER")
    private int chapter; // 章

    @Column(name = "SECTION")
    private int section; // 节

    @Column(name = "LAW_STRIP")
    private int lawStrip;  // 条

    @Column(name = "FUND")
    private int fund; // 款

    @Column(name = "ITEM")
    private int item; // 项

    @Column(name = "CONTENT")
    private String content; // 内容

    @Column(name = "IS_DELETE")
    private String isDelete; // 删除标志

    @Column(name = "GIST_CODE")
    private String gistCode; // 依据编号

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

	public int getSeries() {
		return series;
	}

	public void setSeries(int series) {
		this.series = series;
	}

	public int getChapter() {
		return chapter;
	}

	public void setChapter(int chapter) {
		this.chapter = chapter;
	}

	public int getSection() {
		return section;
	}

	public void setSection(int section) {
		this.section = section;
	}

	public int getLawStrip() {
		return lawStrip;
	}

	public void setLawStrip(int lawStrip) {
		this.lawStrip = lawStrip;
	}

	public int getFund() {
		return fund;
	}

	public void setFund(int fund) {
		this.fund = fund;
	}

	public int getItem() {
		return item;
	}

	public void setItem(int item) {
		this.item = item;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public String getGistCode() {
		return gistCode;
	}

	public void setGistCode(String gistCode) {
		this.gistCode = gistCode;
	}
}
