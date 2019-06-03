package com.tianee.oa.subsys.report.model;

public class TeeTemplateItemModel {
	private int sid;
	private String item;
	private String defName;//自定义名称
	private int width;//宽度
	private String colModel;//列模型
	private int templateId;
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getDefName() {
		return defName;
	}
	public void setDefName(String defName) {
		this.defName = defName;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public String getColModel() {
		return colModel;
	}
	public void setColModel(String colModel) {
		this.colModel = colModel;
	}
	public int getTemplateId() {
		return templateId;
	}
	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}
	
}
