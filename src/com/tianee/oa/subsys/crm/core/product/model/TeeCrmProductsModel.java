package com.tianee.oa.subsys.crm.core.product.model;

import java.util.List;

import javax.persistence.Column;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;


public class TeeCrmProductsModel {

	private int sid;// 自增id
	private String productsNo;// 产品编号
	private String productsName;//产品名称
	private int productsType ; //上级分类ID(产品类别)
	private String productsTypeName ; //产品类别名称
	private String productsModel;// 产品规格型号
	private String units;// 计量单位（包/袋等）
	private String unitsName;// 计量单位名称
	
	private String productsWide;// 产品宽
	private String manufacturer;// 生产厂商
	private String manufacturerAdress;// 生产地
	private double price;// 成本价格
	private double salePrice;// 销售价格
	private String remark;// 产品说明（备注）
	private int createUserId;//创建者id
	private String createUserName;//创建者名称
	private String createTimeStr;//创建时间
	
	private int lastModifyUserId;//最后修改人id
	private String lastModifyUserName;//最后修改人名称
	private String lastModifyTimeStr;//最后修改时间
	
	private String status;//状态 	1-可用; 0-不可用
	
	private List<TeeAttachmentModel> attacheModels;//附件
	private String attacheIds;//附件Ids字符串  以逗号分隔 
	private int minStock;//最小存货
	private int maxStock;//最大存货
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getProductsNo() {
		return productsNo;
	}

	public void setProductsNo(String productsNo) {
		this.productsNo = productsNo;
	}

	public String getProductsName() {
		return productsName;
	}

	public void setProductsName(String productsName) {
		this.productsName = productsName;
	}



	public int getProductsType() {
		return productsType;
	}

	public void setProductsType(int productsType) {
		this.productsType = productsType;
	}

	public String getProductsModel() {
		return productsModel;
	}

	public void setProductsModel(String productsModel) {
		this.productsModel = productsModel;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public String getProductsWide() {
		return productsWide;
	}

	public void setProductsWide(String productsWide) {
		this.productsWide = productsWide;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getManufacturerAdress() {
		return manufacturerAdress;
	}

	public void setManufacturerAdress(String manufacturerAdress) {
		this.manufacturerAdress = manufacturerAdress;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public int getLastModifyUserId() {
		return lastModifyUserId;
	}

	public void setLastModifyUserId(int lastModifyUserId) {
		this.lastModifyUserId = lastModifyUserId;
	}

	public String getLastModifyUserName() {
		return lastModifyUserName;
	}

	public void setLastModifyUserName(String lastModifyUserName) {
		this.lastModifyUserName = lastModifyUserName;
	}

	public String getLastModifyTimeStr() {
		return lastModifyTimeStr;
	}

	public void setLastModifyTimeStr(String lastModifyTimeStr) {
		this.lastModifyTimeStr = lastModifyTimeStr;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<TeeAttachmentModel> getAttacheModels() {
		return attacheModels;
	}

	public void setAttacheModels(List<TeeAttachmentModel> attacheModels) {
		this.attacheModels = attacheModels;
	}

	public String getAttacheIds() {
		return attacheIds;
	}

	public void setAttacheIds(String attacheIds) {
		this.attacheIds = attacheIds;
	}

	public String getProductsTypeName() {
		return productsTypeName;
	}

	public void setProductsTypeName(String productsTypeName) {
		this.productsTypeName = productsTypeName;
	}

	public String getUnitsName() {
		return unitsName;
	}

	public void setUnitsName(String unitsName) {
		this.unitsName = unitsName;
	}

	public int getMinStock() {
		return minStock;
	}

	public void setMinStock(int minStock) {
		this.minStock = minStock;
	}

	public int getMaxStock() {
		return maxStock;
	}

	public void setMaxStock(int maxStock) {
		this.maxStock = maxStock;
	}
	
	
}
