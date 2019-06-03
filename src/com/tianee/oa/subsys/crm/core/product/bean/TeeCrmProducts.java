package com.tianee.oa.subsys.crm.core.product.bean;
import org.hibernate.annotations.Index;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 产品表
 * 
 * @author think
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "CRM_PRODUCTS")
public class TeeCrmProducts {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CRM_PRODUCTS_seq_gen")
	@SequenceGenerator(name="CRM_PRODUCTS_seq_gen", sequenceName="CRM_PRODUCTS_seq")
	@Column(name = "SID")
	private int sid;// 自增id

	@Column(name = "TYPE_ID")
	private int productsType;// 产品类型ID

	@Column(name = "PRODUCTS_NAME")
	private String productsName;// 产品名称
	
	@Column(name = "PRODUCTS_NO")
	private String productsNo;// 产品编号
	

	@Column(name = "PRODUCTS_MODEL")
	private String productsModel;// 产品规格型号

	@Column(name = "STATUS", columnDefinition = "char(1) default 0")
	private String status;// 1-可用; 0-不可用

	@Column(name = "PRIVE")
	private double price;// 成本价格

	@Column(name = "SALE_PRICE")
	private double salePrice;// 销售价格
	
	@Column(name = "PRODUCTS_WIDE")
	private String productsWide;// 产品宽
	

	@Column(name = "UNITS")
	private String units;// 计量单位（包/袋等） CRM代码 PRODUCTS_UNITS_TYPE

	@Column(name = "MANUFACTURER")
	private String manufacturer;// 生产厂商

	@Column(name = "MANUFACTURER_ADRESS")
	private String manufacturerAdress;// 生产地

	@Lob
	@Column(name = "REMARK")
	private String remark;// 产品说明（备注）

	// 创建者id
	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name="IDX67ab67a17ca84587a09e122dce2")
	@JoinColumn(name = "CREATE_USER")
	private TeePerson createUser;

	// 创建时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME")
	private Date createTime;

	// 最后修改人
	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name="IDXde5922d700b245aaaed5c3d4e44")
	@JoinColumn(name = "LAST_MODIFY_USER")
	private TeePerson lastModifyUser;

	// 最后修改时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_MODIFY_TIME")
	private Date lastModifyTime;
	
	@Column(name = "MINSTOCK")
	private int minStock;//最小存货
	
	@Column(name = "MAXSTOCK")
	private int maxStock;//最大存货

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getProductsType() {
		return productsType;
	}

	public void setProductsType(int productsType) {
		this.productsType = productsType;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getProductsWide() {
		return productsWide;
	}

	public void setProductsWide(String productsWide) {
		this.productsWide = productsWide;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public TeePerson getCreateUser() {
		return createUser;
	}

	public void setCreateUser(TeePerson createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public TeePerson getLastModifyUser() {
		return lastModifyUser;
	}

	public void setLastModifyUser(TeePerson lastModifyUser) {
		this.lastModifyUser = lastModifyUser;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
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
