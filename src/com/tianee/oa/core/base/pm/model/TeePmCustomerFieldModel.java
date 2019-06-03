package com.tianee.oa.core.base.pm.model;

public class TeePmCustomerFieldModel {
    private int sid;//Sid	主键
	
	private String extendFiledName;//扩展字段名称
	
	private String orderNum;//排序号
	
	private String filedType;//扩展字段类型
	
	private String codeType;//下拉列表-代码类型
	
	private String sysCode;//下拉列表-HR编码-系统代码
	
	private String optionName;//下拉列表-自定义类型-选项名称
	
	private String optionValue;//下拉列表-自定义类型-选项值
	
	private int isQuery;//是否作为查询字段
	
	private int isShow;//是否作为显示字段

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getExtendFiledName() {
		return extendFiledName;
	}

	public void setExtendFiledName(String extendFiledName) {
		this.extendFiledName = extendFiledName;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getFiledType() {
		return filedType;
	}

	public void setFiledType(String filedType) {
		this.filedType = filedType;
	}

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
	

}
