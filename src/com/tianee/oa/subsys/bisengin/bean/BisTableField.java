package com.tianee.oa.subsys.bisengin.bean;
import org.hibernate.annotations.Index;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 业务表字段
 * @author kakalion
 *
 */
@Entity
@Table(name="BIS_TABLE_FIELD")
public class BisTableField {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="BIS_CONFIG_seq_gen")
	@SequenceGenerator(name="BIS_CONFIG_seq_gen", sequenceName="BIS_CONFIG_seq")
	private int sid;
	
	/**
	 * 所属业务表
	 */
	@ManyToOne()
	@Index(name="IDXb564378cf5e34cafb6cd8d2f7c8")
	@JoinColumn(name="BIS_TABLE_ID")
	private BisTable bisTable;
	
	@Column(name="FIELD_NAME")
	private String fieldName;
	
	@Column(name="FIELD_DESC")
	private String fieldDesc;
	
	/**
	 * 字段别名
	 */
	@Column(name="ALIAS")
	private String alias;
	
	/**
	 * 字段类型
	 * NUMBER、数字类型
	 * DATE、日期时间类型
	 * CHAR、字符类型
	 * VARCHAR、字符串类型
	 * TEXT、文本类型
	 */
	@Column(name="FIELD_TYPE")
	private String fieldType;
	
	/**
	 * 字段类型的扩展（记录详细）
	 */
	@Column(name="FIELD_TYPE_EXT")
	private String fieldTypeExt;
	
	@Column(name="CREATE_TIME")
	private Calendar createTime;
	
	@Column(name="USER_ID")
	private String createUserId;
	
	@Column(name="USER_NAME")
	private String createUserName;
	
	@Column(name="USER_UUID")
	private int createUserUuid;
	
	@Column(name="PRIM_KEY_FLAG",nullable=false)
	private int primaryKeyFlag;
	
	@Column(name="IS_REQUIRED",nullable=false)
	private int isRequired;
	
	
	public int getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(int isRequired) {
		this.isRequired = isRequired;
	}

	@Column(name="GEN_TYPE",nullable=false)
	private int generatedType;//1：本地自增序列  2：GUID  3：自定义生成算法  4：ORACLE序列
	
	@Column(name="GEN_PLUGIN")
	private String generatePlugin;//生成策略类插件
	
	@Column(name="DEFAULT_VAL")
	private String defaultVal;//默认值
	
	@Column(name="FIELD_DISPLAY_TYPE")
	private String fieldDisplayType;//字段显示类型
	
	@Column(name="FIELD_CONTROL_MODEL")
	private String fieldControlModel;//字段控制模型
	
	
	@Column(name="SQL_FILTER")
	private String sqlFilter;//sql过滤条件

	public String getSqlFilter() {
		return sqlFilter;
	}

	public void setSqlFilter(String sqlFilter) {
		this.sqlFilter = sqlFilter;
	}

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

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public BisTable getBisTable() {
		return bisTable;
	}

	public void setBisTable(BisTable bisTable) {
		this.bisTable = bisTable;
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

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
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

	public void setFieldTypeExt(String fieldTypeExt) {
		this.fieldTypeExt = fieldTypeExt;
	}

	public String getFieldTypeExt() {
		return fieldTypeExt;
	}
	
	/**
	 * 字段类型
	 * 1、数字类型
	 * 2、日期时间类型
	 * 3、字符类型
	 * 4、字符串类型
	 * 5、大文本类型
	 */
	/**
	 * 根据数据库类型获取基础字段类型描述
	 * @param dbType
	 * @param fieldType
	 * @return
	 */
	public static String getBasicFieldExt(String dbType,String fieldType){
		String ext = "";
		if("MYSQL".equals(dbType)){//MYSQL
			if("NUMBER".equals(fieldType)){
				ext = "int";
			}else if("DATE".equals(fieldType)){
				ext = "date";
			}else if("DATETIME".equals(fieldType)){
				ext = "datetime";
			}else if("CHAR".equals(fieldType)){
				ext = "char(100)";
			}else if("VARCHAR".equals(fieldType)){
				ext = "varchar(255)";
			}else if("TEXT".equals(fieldType)){
				ext = "text";
			}
			
		}else if("SQLSERVER".equals(dbType)){//SQLSERVER
			if("NUMBER".equals(fieldType)){
				ext = "int";
			}else if("DATE".equals(fieldType)){
				ext = "date";
			}else if("DATETIME".equals(fieldType)){
				ext = "datetime";
			}else if("CHAR".equals(fieldType)){
				ext = "char(100)";
			}else if("VARCHAR".equals(fieldType)){
				ext = "varchar(255)";
			}else if("TEXT".equals(fieldType)){
				ext = "text";
			}
		}else if("ORACLE".equals(dbType)){//ORACLE
			if("NUMBER".equals(fieldType)){
				ext = "number";
			}else if("DATE".equals(fieldType)){
				ext = "date";
			}else if("DATETIME".equals(fieldType)){
				ext = "date";
			}else if("CHAR".equals(fieldType)){
				ext = "char(100)";
			}else if("VARCHAR".equals(fieldType)){
				ext = "varchar(255)";
			}else if("TEXT".equals(fieldType)){
				ext = "clob";
			}
		}else if("DAMENG".equals(dbType)){//DAMENG
			if("NUMBER".equals(fieldType)){
				ext = "int";
			}else if("DATE".equals(fieldType)){
				ext = "datetime";
			}else if("DATETIME".equals(fieldType)){
				ext = "datetime";
			}else if("CHAR".equals(fieldType)){
				ext = "char(100)";
			}else if("VARCHAR".equals(fieldType)){
				ext = "varchar(255)";
			}else if("TEXT".equals(fieldType)){
				ext = "clob";
			}
		}else if("KINGBASE".equals(dbType)){//DAMENG
			if("NUMBER".equals(fieldType)){
				ext = "int";
			}else if("DATE".equals(fieldType)){
				ext = "TIMESTAMP (6) WITH TIME ZONE";
			}else if("DATETIME".equals(fieldType)){
				ext = "TIMESTAMP (6) WITH TIME ZONE";
			}else if("CHAR".equals(fieldType)){
				ext = "char(100)";
			}else if("VARCHAR".equals(fieldType)){
				ext = "varchar(255)";
			}else if("TEXT".equals(fieldType)){
				ext = "clob";
			}
		}
		return ext;
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
	
	
}
