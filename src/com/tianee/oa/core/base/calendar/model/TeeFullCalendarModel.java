package com.tianee.oa.core.base.calendar.model;


public class TeeFullCalendarModel {
	private int id ;
	private String title;
	private boolean allDay;
	private String start;//日程插件开始时间
	private String startTime;//真实开始时间
	private String end;//日程插件结束时间
	private String endTime;//真实结束时间
	private String url;
	private boolean editable ;//是否允许编辑
	private boolean deleteable;//是否允许删除
	private String className; //样式----建议采用样式
	private String color;//所有颜色
	private String backgroundColor;//背景颜色
	private String borderColor;//边框颜色
	private String textColor;//文本颜色
	
	private String remindTypeDesc;
	
	private String type;//类型
	private String status;//状态 0-未完成 1-已完成  2-已超时
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isAllDay() {
		return allDay;
	}
	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}
	
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public boolean isDeleteable() {
		return deleteable;
	}
	public void setDeleteable(boolean deleteable) {
		this.deleteable = deleteable;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public String getBorderColor() {
		return borderColor;
	}
	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}
	public String getTextColor() {
		return textColor;
	}
	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getRemindTypeDesc() {
		return remindTypeDesc;
	}
	public void setRemindTypeDesc(String remindTypeDesc) {
		this.remindTypeDesc = remindTypeDesc;
	}
	
}
