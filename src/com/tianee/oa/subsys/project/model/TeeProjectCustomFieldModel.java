package com.tianee.oa.subsys.project.model;


public class TeeProjectCustomFieldModel {
	
	private int sid;//自增id

	private String fieldName ;//字段名称
	
	private int orderNum ;//排序号
	
	private String fieldType ;//字段类型
	
	private int isQuery ;//是否作为查询字段    0--否  1--是
	
	private int isShow ;//是否显示在列表   0--否  1--是
	
	//private TeeProjectType projectType;//所属项目类型
	private String projectTypeName;
	
	private int projectTypeId;
	
	private int projectTask;
	
	private String projectTaskName;
	
	//private String fieldCtrModel ;//字段控制模型
	private String  codeType;//编码类型     系统编码/自定义选项
	
	private String sysCode;//系统代码值    
	  
	private String  optionName;//选项名称
	
	private String  optionValue;//选项的值
	
	
	
	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	public String getOptionValue() {
		return optionValue;
	}

	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
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

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public int getIsQuery() {
		return isQuery;
	}

	public void setIsQuery(int isQuery) {
		this.isQuery = isQuery;
	}

	public int getIsShow() {
		return isShow;
	}

	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}

	public String getProjectTypeName() {
		return projectTypeName;
	}

	public void setProjectTypeName(String projectTypeName) {
		this.projectTypeName = projectTypeName;
	}

	public int getProjectTypeId() {
		return projectTypeId;
	}

	public void setProjectTypeId(int projectTypeId) {
		this.projectTypeId = projectTypeId;
	}

	public int getProjectTask() {
		return projectTask;
	}

	public void setProjectTask(int projectTask) {
		this.projectTask = projectTask;
	}

	public String getProjectTaskName() {
		return projectTaskName;
	}

	public void setProjectTaskName(String projectTaskName) {
		this.projectTaskName = projectTaskName;
	}

	

	
	

}
