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

import org.hibernate.annotations.Index;

/**
 * 视图项目
 * @author kakalion
 *
 */
@Entity
@Table(name="BIS_VIEW_LIST_ITEM")
public class BisViewListItem {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="BIS_VIEW_LIST_ITEM_SEQ_GEN")
	@SequenceGenerator(name="BIS_VIEW_LIST_ITEM_SEQ_GEN", sequenceName="BIS_VIEW_LIST_ITEM_SEQ")
	@Column(name="SID")
	private int sid;
	
	/**
	 * 字段类型
	 * NUMBER、数字类型
	 * DATE、时间类型
	 * DATETIME、日期和时间类型
	 * TEXT、文本类型
	 */
	@Column(name="FIELD_TYPE")
	private String fieldType;
	
	@Column(name="TITLE_")
	private String title;//字段标题
	
	@Column(name="FIELD_")
	private String field;//字段原名
	
	@Column(name="SEARCH_FIELD")
	private String searchField;//查询字段
	
	@Column(name="SEARCH_FIELD_WRAP")
	private String searchFieldWrap;//查询字段格式化包裹
	
	@Column(name="WIDTH_")
	private String width;//宽度
	
	@Lob
	@Column(name="FORMATTER_")
	private String formatterScript;//字段数据格式化脚本
	
	@Column(name="IS_SEARCH_")
	private int isSearch;//是否作为查询字段
	
	@Column(name="ORDER_NO_")
	private int orderNo;//排序号
	
	@Lob
	@Column(name="MODEL_")
	private String model;//字段模型
	
	@ManyToOne()
	@JoinColumn(name="BIS_VIEW_ID")
	@Index(name="BIS_VIEW_LIST_ITEM_BIS_VIEW")
	private BisView bisView;//视图

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getFormatterScript() {
		return formatterScript;
	}

	public void setFormatterScript(String formatterScript) {
		this.formatterScript = formatterScript;
	}

	public int getIsSearch() {
		return isSearch;
	}

	public void setIsSearch(int isSearch) {
		this.isSearch = isSearch;
	}

	public BisView getBisView() {
		return bisView;
	}

	public void setBisView(BisView bisView) {
		this.bisView = bisView;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSearchFieldWrap() {
		return searchFieldWrap;
	}

	public void setSearchFieldWrap(String searchFieldWrap) {
		this.searchFieldWrap = searchFieldWrap;
	}
	
}
