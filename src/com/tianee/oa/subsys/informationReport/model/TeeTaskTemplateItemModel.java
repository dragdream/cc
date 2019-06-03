package com.tianee.oa.subsys.informationReport.model;



public class TeeTaskTemplateItemModel {

	private int sid;//自增id
	
	private String fieldName;//字段名称

	private int fieldType;//字段类型   1=单行文本    2=多行文本   3=数字文本   4=日期时间   5=下拉菜单

	private String  showType;//显示方式         当field_type=4时，该字段值为yyyy-MM-dd类似格式         当field_type=2时，该字段值可为 "普通 文本"或 "富文本"

	private int showAtList;//表头列显示   0=不显示      1=显示
	
	private String  cal;//是否为计算项   值为SUM，表示合计
	
	//private TeeTaskTemplate taskTemplate;//所属任务模板
    private int taskTemplateSid;
    private String taskTemplateName;
	 
	
	
    
    
    
	public int getTaskTemplateSid() {
		return taskTemplateSid;
	}

	public void setTaskTemplateSid(int taskTemplateSid) {
		this.taskTemplateSid = taskTemplateSid;
	}

	public String getTaskTemplateName() {
		return taskTemplateName;
	}

	public void setTaskTemplateName(String taskTemplateName) {
		this.taskTemplateName = taskTemplateName;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	

	public int getFieldType() {
		return fieldType;
	}

	public void setFieldType(int fieldType) {
		this.fieldType = fieldType;
	}

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	public int getShowAtList() {
		return showAtList;
	}

	public void setShowAtList(int showAtList) {
		this.showAtList = showAtList;
	}

	public String getCal() {
		return cal;
	}

	public void setCal(String cal) {
		this.cal = cal;
	}
	
	
	
}
