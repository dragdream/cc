package com.tianee.oa.subsys.bisengin.model;

import javax.persistence.Column;

public class BisTableFieldModel {
	private int sid;
	private int bisTableId;
	private String fieldName;
	private String fieldDesc;
	private String alias;
	private String fieldType;
	private String fieldTypeExt;
	private String createTimeDesc;
	private String createUserId;
	private String createUserName;
	private int createUserUuid;
	private int primaryKeyFlag;
	private int isRequired;//是否必填
	private String sqlFilter;//sql过滤器
	private int generatedType;//1：本地自增序列  2：GUID  3：自定义生成算法
	public String getFieldDisplayType() {
		return fieldDisplayType;
	}
	public void setFieldDisplayType(String fieldDisplayType) {
		this.fieldDisplayType = fieldDisplayType;
	}
	public String getFieldControlModel() {
		return fieldControlModel;
	}
	public void setFieldControlModel(String fieldControlModel) {
		this.fieldControlModel = fieldControlModel;
	}
	private String generatePlugin;//生成策略类插件
	private String defaultVal;//默认值
	private String fieldDisplayType;//字段显示类型
	private String fieldControlModel;//字段控制模型
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getBisTableId() {
		return bisTableId;
	}
	public void setBisTableId(int bisTableId) {
		this.bisTableId = bisTableId;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldDesc() {
		return fieldDesc;
	}
	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public String getFieldTypeExt() {
		return fieldTypeExt;
	}
	public void setFieldTypeExt(String fieldTypeExt) {
		this.fieldTypeExt = fieldTypeExt;
	}
	public String getCreateTimeDesc() {
		return createTimeDesc;
	}
	public void setCreateTimeDesc(String createTimeDesc) {
		this.createTimeDesc = createTimeDesc;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public int getCreateUserUuid() {
		return createUserUuid;
	}
	public void setCreateUserUuid(int createUserUuid) {
		this.createUserUuid = createUserUuid;
	}
	public int getPrimaryKeyFlag() {
		return primaryKeyFlag;
	}
	public void setPrimaryKeyFlag(int primaryKeyFlag) {
		this.primaryKeyFlag = primaryKeyFlag;
	}
	public int getGeneratedType() {
		return generatedType;
	}
	public void setGeneratedType(int generatedType) {
		this.generatedType = generatedType;
	}
	public String getGeneratePlugin() {
		return generatePlugin;
	}
	public void setGeneratePlugin(String generatePlugin) {
		this.generatePlugin = generatePlugin;
	}
	public String getDefaultVal() {
		return defaultVal;
	}
	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}
	public int getIsRequired() {
		return isRequired;
	}
	public void setIsRequired(int isRequired) {
		this.isRequired = isRequired;
	}
	public String getSqlFilter() {
		return sqlFilter;
	}
	public void setSqlFilter(String sqlFilter) {
		this.sqlFilter = sqlFilter;
	}
	
}
