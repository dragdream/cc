package com.tianee.oa.webframe.httpModel.core.workflow;

import java.util.Calendar;

public class TeeFlowFormModel {
	private int sid;
	private String formName;//表单名称
	private String printModel;//打印模板
	private String printModelShort;//快速模板
	private String script;//javascript脚本
	private String css;//css样式
	private int itemMax;//当前最大控件数
	private int versionNo;//版本号
	private Calendar versionTime;//生成版本时间
	private int locked;//表单使用锁
	private int formGroup;//所属父表单ID（版本）
	private int formSortId;
	private String bundledFlowType="";//绑定的流程
	private int deptId;//所属部门主键
	private String deptName;//所属部门名称
	
	public int getDeptId() {
		return deptId;
	}
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
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
	public String getPrintModel() {
		return printModel;
	}
	public void setPrintModel(String printModel) {
		this.printModel = printModel;
	}
	public String getPrintModelShort() {
		return printModelShort;
	}
	public void setPrintModelShort(String printModelShort) {
		this.printModelShort = printModelShort;
	}
	public String getScript() {
		return script;
	}
	public void setScript(String script) {
		this.script = script;
	}
	public String getCss() {
		return css;
	}
	public void setCss(String css) {
		this.css = css;
	}
	public int getItemMax() {
		return itemMax;
	}
	public void setItemMax(int itemMax) {
		this.itemMax = itemMax;
	}
	public int getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(int versionNo) {
		this.versionNo = versionNo;
	}
	public Calendar getVersionTime() {
		return versionTime;
	}
	public void setVersionTime(Calendar versionTime) {
		this.versionTime = versionTime;
	}
	public int getLocked() {
		return locked;
	}
	public void setLocked(int locked) {
		this.locked = locked;
	}
	public int getFormSortId() {
		return formSortId;
	}
	public void setFormSortId(int formSortId) {
		this.formSortId = formSortId;
	}
	public int getFormGroup() {
		return formGroup;
	}
	public void setFormGroup(int formGroup) {
		this.formGroup = formGroup;
	}
	public void setBundledFlowType(String bundledFlowType) {
		this.bundledFlowType = bundledFlowType;
	}
	public String getBundledFlowType() {
		return bundledFlowType;
	}
	
}
