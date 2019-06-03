package com.beidasoft.xzzf.law.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 法律法规条目表实体类
 */
@Entity
@Table(name = "FX_BASE_LAW_DETAIL")
public class BaseLawDetail {
	@Id
	@Column(name = "ID")
	private String id; // 依据ID

	@Column(name = "LAW_ID")
	private String lawId; // 法律法规ID

	@Column(name = "LAW_NAME")
	private String lawName; // 法律名称

	@Column(name = "SERIES")
	private String series; // 编

	@Column(name = "CHAPTER")
	private String chapter; // 章

	@Column(name = "SECTION")
	private String section; // 节

	@Column(name = "LAW_STRIP")
	private String law_strip; // 条

	@Column(name = "FUND")
	private String fund; // 款

	@Column(name = "ITEM")
	private String item; // 项

	@Column(name = "CONTENT")
	private String content; // 内容

	@Column(name = "IS_DELETE")
	private String isDelete; // 是否删除

	@Column(name = "CHAPTER_CAPTION")
	private String chapterCaption; // 章标题

	@Column(name = "LAW_DETAIL_CODE")
	private String lawDetailCode; // 法律条文编号

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

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getChapter() {
		return chapter;
	}

	public void setChapter(String chapter) {
		this.chapter = chapter;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getLaw_strip() {
		return law_strip;
	}

	public void setLaw_strip(String law_strip) {
		this.law_strip = law_strip;
	}

	public String getFund() {
		return fund;
	}

	public void setFund(String fund) {
		this.fund = fund;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
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

	public String getChapterCaption() {
		return chapterCaption;
	}

	public void setChapterCaption(String chapterCaption) {
		this.chapterCaption = chapterCaption;
	}

	public String getLawDetailCode() {
		return lawDetailCode;
	}

	public void setLawDetailCode(String lawDetailCode) {
		this.lawDetailCode = lawDetailCode;
	}
}
