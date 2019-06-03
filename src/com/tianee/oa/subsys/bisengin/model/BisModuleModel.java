package com.tianee.oa.subsys.bisengin.model;

import javax.persistence.Column;
import javax.persistence.Lob;


public class BisModuleModel {
	private String uuid;//自增id
	private int bisTableId;//关联业务表
	private String bisTableName;//关联业务表
	private String bisViewId;//关联视图
	private String bisViewName;
	private int formId;//关联表单
	private String formName;
	private int pageSize;//分页大小
	private String mapping;//业务表单映射
	private String moduleName;//模块名称
	private String createPrivIds;//创建权限
	private String editPrivIds;//修改权限
	private String delPrivIds;//删除权限
	private String createPrivNames;//创建权限
	private String editPrivNames;//修改权限
	private String delPrivNames;//删除权限
	private int postPriv;//管理权限
	
	public int getBisTableId() {
		return bisTableId;
	}
	public void setBisTableId(int bisTableId) {
		this.bisTableId = bisTableId;
	}
	public String getBisTableName() {
		return bisTableName;
	}
	public void setBisTableName(String bisTableName) {
		this.bisTableName = bisTableName;
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
	public int getFormId() {
		return formId;
	}
	public void setFormId(int formId) {
		this.formId = formId;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getMapping() {
		return mapping;
	}
	public void setMapping(String mapping) {
		this.mapping = mapping;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getCreatePrivIds() {
		return createPrivIds;
	}
	public void setCreatePrivIds(String createPrivIds) {
		this.createPrivIds = createPrivIds;
	}
	public String getEditPrivIds() {
		return editPrivIds;
	}
	public void setEditPrivIds(String editPrivIds) {
		this.editPrivIds = editPrivIds;
	}
	public String getDelPrivIds() {
		return delPrivIds;
	}
	public void setDelPrivIds(String delPrivIds) {
		this.delPrivIds = delPrivIds;
	}
	public String getCreatePrivNames() {
		return createPrivNames;
	}
	public void setCreatePrivNames(String createPrivNames) {
		this.createPrivNames = createPrivNames;
	}
	public String getEditPrivNames() {
		return editPrivNames;
	}
	public void setEditPrivNames(String editPrivNames) {
		this.editPrivNames = editPrivNames;
	}
	public String getDelPrivNames() {
		return delPrivNames;
	}
	public void setDelPrivNames(String delPrivNames) {
		this.delPrivNames = delPrivNames;
	}
	public int getPostPriv() {
		return postPriv;
	}
	public void setPostPriv(int postPriv) {
		this.postPriv = postPriv;
	}
	
}
