package com.tianee.oa.subsys.salManage.bean;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * 项目基本项目
 * @author think
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "HR_SAL_ITEM")
public class TeeSalItem implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="HR_SAL_ITEM_seq_gen")
	@SequenceGenerator(name="HR_SAL_ITEM_seq_gen", sequenceName="HR_SAL_ITEM_seq")
	private int sid;//自增id薪酬项目编号
	
	@Column(name="ACCOUNT_ID")
	private int accountId;//工资账套Id
	
	@Column(name="ITEM_NAME")
	private String itemName;//薪酬项目名称
	
	@Column(name="ITEM_FLAG" , columnDefinition = ("char(1) default '0'"))
	private String itemFlag ;//是否显示  0-正常  ； 1-关闭
	
	@Column(name="ITEM_COLUMN" , length=50)
	private String itemColumn;//对应工资数据  和工资数据设置  字段名称，  例如S1 ， S2
	
	@Column(name="ITEM_TYPE" ,columnDefinition="INT default 0" ,nullable=false)
	private int itemType ;//项目类型(0-输入项 1-计算项 2-提取项 3-SQL项)
	
	
	@Column(name="FORMULA" ,length=1000)
	private String formula;//计算公式
	
	@Column(name="FORMULANAME",length=1000)
	private String formulaName;//计算公式说明
	
	@Column(name="NUMBER_POINT", columnDefinition = "INT default 0")
	private int numberPoint;//小数点
	
	
	@Column(name="DEFAULT_VALUE")
	private double defaultValue;//默认值
	
	@Column(name="BIS_VIEW_ID")
	private String bisViewId;//所属视图ID
	
	@Column(name="GET_TYPE")
	private int getType;//提取项类型
	
	@Column(name="P_INCOME")
	private int pIncome;//是否纳入个人所得税计算
	
	@Column(name="SORT_NO")
	private int sortNo;//排序号

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getFormulaName() {
		return formulaName;
	}

	public void setFormulaName(String formulaName) {
		this.formulaName = formulaName;
	}

	public String getItemFlag() {
		return itemFlag;
	}

	public void setItemFlag(String itemFlag) {
		this.itemFlag = itemFlag;
	}

	public String getItemColumn() {
		return itemColumn;
	}

	public void setItemColumn(String itemColumn) {
		this.itemColumn = itemColumn;
	}

	public int getItemType() {
		return itemType;
	}

	public void setItemType(int itemType) {
		this.itemType = itemType;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getNumberPoint() {
		return numberPoint;
	}

	public void setNumberPoint(int numberPoint) {
		this.numberPoint = numberPoint;
	}

	public double getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(double defaultValue) {
		this.defaultValue = defaultValue;
	}


	public int getGetType() {
		return getType;
	}

	public void setGetType(int getType) {
		this.getType = getType;
	}

	public String getBisViewId() {
		return bisViewId;
	}

	public void setBisViewId(String bisViewId) {
		this.bisViewId = bisViewId;
	}

	public int getpIncome() {
		return pIncome;
	}

	public void setpIncome(int pIncome) {
		this.pIncome = pIncome;
	}

	public int getSortNo() {
		return sortNo;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}
	
	
	
}
