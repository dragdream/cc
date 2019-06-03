package com.tianee.oa.subsys.crm.core.customer.bean;
import org.hibernate.annotations.Index;
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
 * 跟单 产品明细报价表
 * @author syl
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "CRM_SALE_FOLLOW_PRODUCT_ITEM")
public class TeeCrmSaleFollowProductItem {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CRM_S_FO_PRO_I_seq_gen")
	@SequenceGenerator(name="CRM_S_FO_PRO_I_seq_gen", sequenceName="CRM_S_FO_PRO_I_seq")
	@Column(name = "SID")
	private int sid;// 自增id
	
	@ManyToOne()
	@Index(name="IDX8ecf703816934b0cb9e28f3710c")
	@JoinColumn(name = "SALE_FOLLOW")
	private TeeCrmSaleFollow saleFollow;// 跟单表
	
	@Column(name = "PRODUCTS_ID")
	private String productsId;//产品Id
	
	@Column(name = "PRODUCTS_NAME")
	private String productsName;// 产品名称
	
	@Column(name = "PRODUCTS_NO")
	private String productsNo;// 产品编号

	@Column(name = "PRODUCTS_MODEL")
	private String productsModel;// 产品规格型号

	@Column(name = "PRICE")
	private double price;// 成本价格
	
	@Column(name = "UNITS")
	private String units;// 计量单位（包/袋等）
	
	@Column(name = "PRODUCTS_NUMBER" , columnDefinition="INT default 0")
	private int productsNumber;// 数量

	@Column(name = "TOTAL_AMOUNT")
	private double totalAmount;// 合计

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeeCrmSaleFollow getSaleFollow() {
		return saleFollow;
	}

	public void setSaleFollow(TeeCrmSaleFollow saleFollow) {
		this.saleFollow = saleFollow;
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

	
}
