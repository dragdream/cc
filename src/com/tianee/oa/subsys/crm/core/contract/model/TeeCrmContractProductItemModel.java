package com.tianee.oa.subsys.crm.core.contract.model;

public class TeeCrmContractProductItemModel {
	private int sid;// 自增id
	private String contractId;// 合同ID
	private String contractName;//合同名称
	
	private int productsId ;//产品Id
	private String productsName;// 产品名称
	
	private String productsNo;// 产品编号

	private String productsModel;// 产品规格型号

	private double price;// 成本价格

	private String units;// 计量单位（包/袋等）
	private String unitsDesc;
	
	private int productsNumber;// 数量

	private double totalAmount;// 合计

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getProductsName() {
		return productsName;
	}

	public void setProductsName(String productsName) {
		this.productsName = productsName;
	}



	public String getProductsNo() {
		return productsNo;
	}

	public void setProductsNo(String productsNo) {
		this.productsNo = productsNo;
	}

	public String getProductsModel() {
		return productsModel;
	}

	public void setProductsModel(String productsModel) {
		this.productsModel = productsModel;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public int getProductsNumber() {
		return productsNumber;
	}

	public void setProductsNumber(int productsNumber) {
		this.productsNumber = productsNumber;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getUnitsDesc() {
		return unitsDesc;
	}

	public void setUnitsDesc(String unitsDesc) {
		this.unitsDesc = unitsDesc;
	}

	public int getProductsId() {
		return productsId;
	}

	public void setProductsId(int productsId) {
		this.productsId = productsId;
	}

	
}
