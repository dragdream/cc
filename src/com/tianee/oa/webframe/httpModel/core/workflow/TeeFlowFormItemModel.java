package com.tianee.oa.webframe.httpModel.core.workflow;


public class TeeFlowFormItemModel {
	private int sid;//表单控件自增ID
	
	private int itemId;//项目ID
	
	private int formId;//表单
	
	private String tag;//控件标签名称
	
	private String title;//控件标题
	
	private String content;//控件整体内容
	
	private String model;//数据模型
	
	private String name;//控件名称
	
	private String xtype;//控件类型
	
	private String xtypeDesc;//控件类型描述
	
	private int sortNo;//排序顺序
	
	private int columnType;//列类型

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getFormId() {
		return formId;
	}

	public void setFormId(int formId) {
		this.formId = formId;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getXtype() {
		return xtype;
	}

	public void setXtype(String xtype) {
		this.xtype = xtype;
	}

	public int getColumnType() {
		return columnType;
	}

	public void setColumnType(int columnType) {
		this.columnType = columnType;
	}

	public void setXtypeDesc(String xtypeDesc) {
		this.xtypeDesc = xtypeDesc;
	}

	public String getXtypeDesc() {
		return xtypeDesc;
	}

	public int getSortNo() {
		return sortNo;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}
	
	
}
