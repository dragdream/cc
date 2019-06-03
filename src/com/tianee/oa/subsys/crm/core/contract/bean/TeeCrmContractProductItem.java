package com.tianee.oa.subsys.crm.core.contract.bean;
import org.hibernate.annotations.Index;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "CRM_CONTRACT_PRODUCT_ITEM")
public class TeeCrmContractProductItem {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CRM_CON_PRO_ITEM_seq_gen")
	@SequenceGenerator(name="CRM_CON_PRO_ITEM_seq_gen", sequenceName="CRM_CON_PRO_ITEM_seq")
	@Column(name = "SID")
	private int sid;// 自增id
	
	@ManyToOne()
	@Index(name="IDX98f57ba8e97e4923bcf372ebea4")
	@JoinColumn(name = "CONTRACT_ID")
	private TeeCrmContract contract;// 合同
	
	@Column(name = "PRODUCTS_ID" , columnDefinition = "int  default 0")
	private int productsId ;//产品Id
	
	@Column(name = "PRODUCTS_NAME")
	private String productsName;// 产品名称
	
	@Column(name = "PRODUCTS_NO")
	private String productsNo;// 产品编号

	@Column(name = "PRODUCTS_MODEL")
	private String productsModel;// 产品规格型号

	@Column(name = "PRIVE")
	private double price;// 成本价格
	
	@Column(name = "UNITS")
	private String units;// 计量单位（包/袋等）
	
	@Column(name = "PRODUCTS_NUMBER" , columnDefinition="INT default 0")
	private int productsNumber;// 数量

	@Column(name = "TOTAL_AMOUNT")
	private double totalAmount;// 合计
	
	@Column(name = "CONTRACT_SIGN_DATE")
	private Date contractSignDate;//合同签订日期

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeeCrmContract getContract() {
		return contract;
	}

	public void setContract(TeeCrmContract contract) {
		this.contract = contract;
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

	public int getProductsId() {
		return productsId;
	}

	public void setProductsId(int productsId) {
		this.productsId = productsId;
	}

	public Date getContractSignDate() {
		return contractSignDate;
	}

	public void setContractSignDate(Date contractSignDate) {
		this.contractSignDate = contractSignDate;
	}



	
}
