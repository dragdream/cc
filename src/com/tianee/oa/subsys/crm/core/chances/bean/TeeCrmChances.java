package com.tianee.oa.subsys.crm.core.chances.bean;

import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.subsys.crm.core.clue.bean.TeeCrmClue;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomer;
import com.tianee.oa.subsys.crm.core.product.bean.TeeCrmProducts;

/**
 * 商机实体
 * @author zhaocaigen
 *
 */
@Entity
@Table(name = "CRM_CHANCES")
public class TeeCrmChances {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CRM_CHANCE_seq_gen")
	@SequenceGenerator(name="CRM_CHANCE_seq_gen", sequenceName="CRM_CHANCE_seq")
	@Column(name = "SID")
	private int sid;//主键
	
	@Column(name = "CHANCE_NAME")
	private String chanceName;//机会名称
    
	@Column(name = "FORCAST_TIME")
	private Calendar forcastTime;//预计成交时间
	
	@Column(name = "CREATE_TIME")
	private Calendar createTime;//创建时间
	
	@Column(name = "FORCAST_COST")
	private Double forcastCost;//预计成交金额
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CR_USER_ID")
	private TeePerson crUser;//创建人
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	private TeeCrmCustomer customer;//关联客户
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MANAGE_USER_ID")
	private TeePerson chanceManagePerson;//  负责人
	
	@Column(name="REMARK")
	@Lob()
	private String remark;   //备注
	
	@Column(name="CHANCE_STATUS")
	private int chanceStatus;   //商机状态（进行中、赢单、输单）
	
	@ManyToMany(targetEntity=TeeCrmProducts.class,fetch = FetchType.LAZY  ) 
	@JoinTable(name="CHANCE_PRODUCTS",
			joinColumns={@JoinColumn(name="CHANCE_ID")},       
        inverseJoinColumns={@JoinColumn(name="PRODUCTS_ID")}  ) 	
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	private List<TeeCrmProducts> products;   // 产品
	
	@Column(name="LAST_EDIT_TIME")
	private Calendar lastEditTime;  //最后一次修改时间
	
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="CLUE_ID") //线索外键
	private TeeCrmClue clue;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getChanceName() {
		return chanceName;
	}

	public void setChanceName(String chanceName) {
		this.chanceName = chanceName;
	}

	public Calendar getForcastTime() {
		return forcastTime;
	}

	public void setForcastTime(Calendar forcastTime) {
		this.forcastTime = forcastTime;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public Double getForcastCost() {
		return forcastCost;
	}

	public void setForcastCost(Double forcastCost) {
		this.forcastCost = forcastCost;
	}

	public TeePerson getCrUser() {
		return crUser;
	}

	public void setCrUser(TeePerson crUser) {
		this.crUser = crUser;
	}

	public TeeCrmCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(TeeCrmCustomer customer) {
		this.customer = customer;
	}

	public TeePerson getChanceManagePerson() {
		return chanceManagePerson;
	}

	public void setChanceManagePerson(TeePerson chanceManagePerson) {
		this.chanceManagePerson = chanceManagePerson;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getChanceStatus() {
		return chanceStatus;
	}

	public void setChanceStatus(int chanceStatus) {
		this.chanceStatus = chanceStatus;
	}

	public List<TeeCrmProducts> getProducts() {
		return products;
	}

	public void setProducts(List<TeeCrmProducts> products) {
		this.products = products;
	}

	public Calendar getLastEditTime() {
		return lastEditTime;
	}

	public void setLastEditTime(Calendar lastEditTime) {
		this.lastEditTime = lastEditTime;
	}

	public TeeCrmClue getClue() {
		return clue;
	}

	public void setClue(TeeCrmClue clue) {
		this.clue = clue;
	}
	
}
