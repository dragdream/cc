package com.tianee.oa.subsys.bisengin.model;

public class BisViewListItemModel {
	private int sid;
	private String fieldType;
	private String title;//字段标题
	private String field;//字段原名
	private String searchField;//查询字段
	private String width;//宽度
	private String formatterScript;//字段数据格式化脚本
	private int isSearch;//是否作为查询字段
	private String bisViewId;
	private String bisViewName;
	private int orderNo;//排序号
	private String model;//字段模型
	private String searchFieldWrap;//查询字段包裹
	
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
	public String getBisViewId() {
		return bisViewId;
	}
	public void setBisViewId(String bisViewId) {
		this.bisViewId = bisViewId;
	}
	public String getBisViewName() {
		return bisViewName;
	}
	public void setBisViewName(String bisViewName) {
		this.bisViewName = bisViewName;
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
