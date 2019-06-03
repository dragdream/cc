package com.tianee.oa.subsys.crm.core.order.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 退货单-产品中间表
 * @author zhaocaigen
 *
 */

@Entity
@Table(name="RETURN_ORDER_PRODUCTS")
public class TeeReturnOrderProducts {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="RETURN_ORDER_PRODUCTS_seq_gen")
	@SequenceGenerator(name="RETURN_ORDER_PRODUCTS_seq_gen", sequenceName="RETURN_ORDER_PRODUCTS_seq")
	@Column(name="SID")
	private int sid;//Sid	int	11	否	主键
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="RETURN_ORDER_ID")
	private TeeCrmReturnOrder returnOrder;//  退货单
	
	@Column(name = "PRODUCTS_ID")
	private String productsId;//产品Id
	
	@Column(name = "PRODUCTS_NAME")
	private String productsName;// 产品名称
	
	@Column(name = "PRODUCTS_NO")
	private String productsNo;// 产品编号
	
	@Column(name="PRICE")
	private double price;//产品价格
	
	@Column(name = "PRODUCTS_MODEL")
	private String productsModel;// 产品规格型号
	
	@Column(name = "UNITS")
	private String units;// 计量单位（包/袋等）
	
	@Column(name="COUNT")
	private int productsNumber;//数量
	
	@Column(name="TOTAL_PRICE")
	private double totalAmount;//总价格

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeeCrmReturnOrder getReturnOrder() {
		return returnOrder;
	}

	public void setReturnOrder(TeeCrmReturnOrder returnOrder) {
		this.returnOrder = returnOrder;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
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

}
