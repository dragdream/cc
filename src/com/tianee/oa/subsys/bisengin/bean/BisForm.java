package com.tianee.oa.subsys.bisengin.bean;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name="BIS_FORM")
public class BisForm {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="BIS_FORM_seq_gen")
	@SequenceGenerator(name="BIS_FORM_seq_gen", sequenceName="BIS_FORM_seq")
	private int sid;
	
	/**
	 * 表单名称
	 */
	@Column(name="FORM_NAME")
	private String formName;
	
	/**
	 * HTML
	 */
	@Lob
	@Column(name="content")
	private String content;
	
	/**
	 * HTML
	 */
	@Lob
	@Column(name="short_content")
	private String shortContent;
	
	/**
	 * HTML
	 */
	@Column(name="sort_No")
	private int sortNo;
	
	/**
	 * 关联的表格
	 */
	@ManyToOne
	@JoinColumn(name="BIS_TABLE_ID")
	private BisTable bisTable;
	
	

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getShortContent() {
		return shortContent;
	}

	public void setShortContent(String shortContent) {
		this.shortContent = shortContent;
	}

	public int getSortNo() {
		return sortNo;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	public BisTable getBisTable() {
		return bisTable;
	}

	public void setBisTable(BisTable bisTable) {
		this.bisTable = bisTable;
	}

}
