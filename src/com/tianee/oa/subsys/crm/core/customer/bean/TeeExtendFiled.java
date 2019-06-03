package com.tianee.oa.subsys.crm.core.customer.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 扩展字段实体
 * @author zhaocaigen
 *
 */
@Entity
@Table(name="EXTEND_FILED")
public class TeeExtendFiled {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="EXTEND_FILED_seq_gen")
	@SequenceGenerator(name="EXTEND_FILED_seq_gen", sequenceName="EXTEND_FILED_INFO_seq")
	@Column(name="SID")
	private int sid;//Sid	int	11	否	主键
	
	@Column(name="EXTEND_FILELD_NAME")
	private String extendFiledName;//扩展字段名称
	
	@Column(name="ORDERNUM")
	private String orderNum;//排序号
	
	@Column(name="FILED_TYPE")
	private String filedType;//扩展字段类型
	
	@Column(name="CODE_TYPE")
	private String codeType;//下拉列表-代码类型
	
    @Column(name="SYS_CODE")	
    private String sysCode;//下拉列表-CRM编码-系统代码
    
    @Column(name="OPTION_NAME")
    private String optionName;//下拉列表-自定义类型-选项名称
    
    @Column(name="OPTION_VALUE")
    private String optionValue;//下拉列表-自定义类型-选项值
    
	@Column(name="ISQUERY")
	private int isQuery;//是否作为查询字段
	
	@Column(name="ISSHOW")
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

	public String getFiledType() {
		return filedType;
	}

	public void setFiledType(String filedType) {
		this.filedType = filedType;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
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

}
