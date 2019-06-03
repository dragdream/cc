package com.beidasoft.xzfy.common.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FY_DICT")
public class DictionaryInfo {

	//类型
	@Id
	@Column(name="column_name")
	private String type;
	
	//编码
	@Column(name="CODE")
	private String code;
	
	//描述
	@Column(name="CODE_DESC")
	private String codeDesc;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeDesc() {
		return codeDesc;
	}

	public void setCodeDesc(String codeDesc) {
		this.codeDesc = codeDesc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
