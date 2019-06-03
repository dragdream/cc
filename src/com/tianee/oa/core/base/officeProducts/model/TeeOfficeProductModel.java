package com.tianee.oa.core.base.officeProducts.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.tianee.oa.core.base.officeProducts.bean.TeeOfficeCategory;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;

public class TeeOfficeProductModel {
	private int sid;
	private String proName;//用品名称
	private String proCode;//用品编号
	private String proUnit;//计量单位
	private String norms;//规格
	private String proSupplier;//供应商
	private String price;//单价
	private int maxStock;//最高库存警戒
	private int minStock;//最低库存警戒
	private int curStock;//当前库存
	private int categoryId;
	private String categoryName;
	private int depositoryId;
	private String depositoryName;
	private String proDesc;//用品描述
	private String auditorsIds;
	private int regType;
	private String auditorsNames;
	private String regUsersIds;
	private String regUsersNames;
	private String regDeptsIds;
	private String regDeptsName;
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getProCode() {
		return proCode;
	}
	public void setProCode(String proCode) {
		this.proCode = proCode;
	}
	public String getProUnit() {
		return proUnit;
	}
	public void setProUnit(String proUnit) {
		this.proUnit = proUnit;
	}
	public String getNorms() {
		return norms;
	}
	public void setNorms(String norms) {
		this.norms = norms;
	}
	public String getProSupplier() {
		return proSupplier;
	}
	public void setProSupplier(String proSupplier) {
		this.proSupplier = proSupplier;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public int getMaxStock() {
		return maxStock;
	}
	public void setMaxStock(int maxStock) {
		this.maxStock = maxStock;
	}
	public int getMinStock() {
		return minStock;
	}
	public void setMinStock(int minStock) {
		this.minStock = minStock;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getProDesc() {
		return proDesc;
	}
	public void setProDesc(String proDesc) {
		this.proDesc = proDesc;
	}
	public String getAuditorsIds() {
		return auditorsIds;
	}
	public void setAuditorsIds(String auditorsIds) {
		this.auditorsIds = auditorsIds;
	}
	public String getAuditorsNames() {
		return auditorsNames;
	}
	public void setAuditorsNames(String auditorsNames) {
		this.auditorsNames = auditorsNames;
	}
	public String getRegUsersIds() {
		return regUsersIds;
	}
	public void setRegUsersIds(String regUsersIds) {
		this.regUsersIds = regUsersIds;
	}
	public String getRegUsersNames() {
		return regUsersNames;
	}
	public void setRegUsersNames(String regUsersNames) {
		this.regUsersNames = regUsersNames;
	}
	public String getRegDeptsIds() {
		return regDeptsIds;
	}
	public void setRegDeptsIds(String regDeptsIds) {
		this.regDeptsIds = regDeptsIds;
	}
	public String getRegDeptsName() {
		return regDeptsName;
	}
	public void setRegDeptsName(String regDeptsName) {
		this.regDeptsName = regDeptsName;
	}
	public void setRegType(int regType) {
		this.regType = regType;
	}
	public int getRegType() {
		return regType;
	}
	public int getDepositoryId() {
		return depositoryId;
	}
	public void setDepositoryId(int depositoryId) {
		this.depositoryId = depositoryId;
	}
	public String getDepositoryName() {
		return depositoryName;
	}
	public void setDepositoryName(String depositoryName) {
		this.depositoryName = depositoryName;
	}
	public void setCurStock(int curStock) {
		this.curStock = curStock;
	}
	public int getCurStock() {
		return curStock;
	}
	
	
}
