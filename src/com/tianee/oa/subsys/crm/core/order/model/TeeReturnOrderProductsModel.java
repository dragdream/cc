package com.tianee.oa.subsys.crm.core.order.model;

import com.tianee.oa.webframe.httpModel.TeeBaseModel;


public class TeeReturnOrderProductsModel extends TeeBaseModel {
	private int sid;//Sid	int	11	否	主键
	private int returnOrderId;//  退货单
	private String returnOrderName;//
	private String productsId;//产品Id
	private String productsName;// 产品名称
	private String productsNo;// 产品编号
	private double price;//产品价格
	private String productsModel;// 产品规格型号
	private String productsTypeName;
	private String units;// 计量单位（包/袋等）
	private String unitsDesc;
	private String unitsName;
	private int productsNumber;//数量
	private double totalAmount;//总价格
	
	private int maxNum=0;
	
	
	
	
	public int getMaxNum() {
		return maxNum;
	}
	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}
	public String getProductsTypeName() {
		return productsTypeName;
	}
	public String getUnitsName() {
		return unitsName;
	}
	public void setProductsTypeName(String productsTypeName) {
		this.productsTypeName = productsTypeName;
	}
	public void setUnitsName(String unitsName) {
		this.unitsName = unitsName;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getReturnOrderId() {
		return returnOrderId;
	}
	public void setReturnOrderId(int returnOrderId) {
		this.returnOrderId = returnOrderId;
	}
	public String getReturnOrderName() {
		return returnOrderName;
	}
	public void setReturnOrderName(String returnOrderName) {
		this.returnOrderName = returnOrderName;
	}
	public String getProductsId() {
		return productsId;
	}
	public void setProductsId(String productsId) {
		this.productsId = productsId;
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
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
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

}
