package com.tianee.oa.subsys.repertory.bean;
import org.hibernate.annotations.Index;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomerInfo;
import com.tianee.oa.subsys.crm.core.product.bean.TeeCrmProducts;

@Entity
@Table(name="REP_RECORD")
public class TeeRepRecord {
	@Id
	@Column(name="SID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator="REP_RECORD_seq_gen")
	@SequenceGenerator(name="REP_RECORD_seq_gen", sequenceName="REP_RECORD_seq")
	private int sid;
	
	@Column(name="BILL_NO")
	private String billNo;//库存单据号
	
	@Column(name="TYPE")
	private int type;//类型 1：入库  2：出库
	
	@ManyToOne
	@Index(name="IDX572ece7a963b40208aacd4cdadb")
	@JoinColumn(name="CUSTOMER_ID")
	private TeeCrmCustomerInfo customerInfo;//关联客户
	
	@ManyToOne
	@Index(name="IDX2e9e6611da2242a8944ff912e0a")
	@JoinColumn(name="DEPOSITORY_ID")
	private TeeRepDepository depository;//所属仓库
	
	@ManyToOne
	@Index(name="IDX57eea267bc1f4fa6a256b2c1327")
	@JoinColumn(name="CR_USER")
	private TeePerson crUser;//创建人
	
	@ManyToOne
	@Index(name="IDX88ee6e0e5efe4ba8a7088b1d46b")
	@JoinColumn(name="HANDLER")
	private TeePerson handleUser;//经手人
	
	@ManyToOne
	@Index(name="IDXd8ac86bb1614487fad08cb77c2b")
	@JoinColumn(name="DEPT_ID")
	private TeeDepartment dept;//部门
	
	@Column(name="REMARK")
	private String remark;//单据附记
	
	@Column(name="CR_TIME")
	private Calendar crTime;//创建时间
	
	@ManyToOne
	@Index(name="IDX7b8168cc88bb4e84ae6194954e5")
	@JoinColumn(name="PRODUCT_ID")
	private TeeCrmProducts products;//所属产品
	
	@Column(name="COUNT_")
	private int count;//数量
	
	@Column(name="PRICE")
	private double price;//单价
	
	@Column(name="UNIT")
	private String unit;//单位
	
	@Column(name="PRO_MODEL")
	private String productsModel;//规格
	
	@Column(name="SUM_")
	private double sum;//小计
	
	@Column(name="RUN_ID")
	private int runId;//流程ID

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

	public TeeCrmCustomerInfo getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(TeeCrmCustomerInfo customerInfo) {
		this.customerInfo = customerInfo;
	}

	public TeeRepDepository getDepository() {
		return depository;
	}

	public void setDepository(TeeRepDepository depository) {
		this.depository = depository;
	}

	public TeePerson getCrUser() {
		return crUser;
	}

	public void setCrUser(TeePerson crUser) {
		this.crUser = crUser;
	}

	public TeeDepartment getDept() {
		return dept;
	}

	public void setDept(TeeDepartment dept) {
		this.dept = dept;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Calendar getCrTime() {
		return crTime;
	}

	public void setCrTime(Calendar crTime) {
		this.crTime = crTime;
	}

	public TeeCrmProducts getProducts() {
		return products;
	}

	public void setProducts(TeeCrmProducts products) {
		this.products = products;
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

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	public TeePerson getHandleUser() {
		return handleUser;
	}

	public void setHandleUser(TeePerson handleUser) {
		this.handleUser = handleUser;
	}

	public int getRunId() {
		return runId;
	}

	public void setRunId(int runId) {
		this.runId = runId;
	}
	
}
