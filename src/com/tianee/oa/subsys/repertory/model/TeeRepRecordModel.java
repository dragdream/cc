package com.tianee.oa.subsys.repertory.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomerInfo;
import com.tianee.oa.subsys.crm.core.product.bean.TeeCrmProducts;
import com.tianee.oa.subsys.repertory.bean.TeeRepDepository;

public class TeeRepRecordModel {
	private int sid;
	private String billNo;//库存单据号
	private int type;//类型 1：出库  2：入库
	private String customerName;//关联客户
	private int customerId;
	private String depositoryName;//所属仓库
	private int depositoryId;
	private TeePerson crUser;//创建人
	private String crUserName;
	private int crUserId;
	private String handlerName;//经手人
	private int handlerId;//经手人
	private String deptName;//部门
	private String deptId;
	private String remark;//单据附记
	private String crTimeDesc;//创建时间
	private String productsName;//所属产品
	private int productsId;
	private String unit;//单位
	private String productsModel;//规格
	private int count;//数量
	private double price;//单价
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public TeePerson getCrUser() {
		return crUser;
	}
	public void setCrUser(TeePerson crUser) {
		this.crUser = crUser;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getDepositoryName() {
		return depositoryName;
	}
	public void setDepositoryName(String depositoryName) {
		this.depositoryName = depositoryName;
	}
	public int getDepositoryId() {
		return depositoryId;
	}
	public void setDepositoryId(int depositoryId) {
		this.depositoryId = depositoryId;
	}
	public String getCrUserName() {
		return crUserName;
	}
	public void setCrUserName(String crUserName) {
		this.crUserName = crUserName;
	}
	public int getCrUserId() {
		return crUserId;
	}
	public void setCrUserId(int crUserId) {
		this.crUserId = crUserId;
	}
	public String getHandlerName() {
		return handlerName;
	}
	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}
	public int getHandlerId() {
		return handlerId;
	}
	public void setHandlerId(int handlerId) {
		this.handlerId = handlerId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getCrTimeDesc() {
		return crTimeDesc;
	}
	public void setCrTimeDesc(String crTimeDesc) {
		this.crTimeDesc = crTimeDesc;
	}
	public String getProductsName() {
		return productsName;
	}
	public void setProductsName(String productsName) {
		this.productsName = productsName;
	}
	public int getProductsId() {
		return productsId;
	}
	public void setProductsId(int productsId) {
		this.productsId = productsId;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getProductsModel() {
		return productsModel;
	}
	public void setProductsModel(String productsModel) {
		this.productsModel = productsModel;
	}
	
}
