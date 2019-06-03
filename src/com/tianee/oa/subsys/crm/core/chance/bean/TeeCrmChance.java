package com.tianee.oa.subsys.crm.core.chance.bean;

import java.util.Calendar;

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

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomerInfo;
/**
 * 客户机会类
 * @author xsy
 *
 */
@Entity
@Table(name = "CRM_CHANCE")
public class TeeCrmChance {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CRM_CHANCE_seq_gen")
	@SequenceGenerator(name="CRM_CHANCE_seq_gen", sequenceName="CRM_CHANCE_seq")
	@Column(name = "SID")
	private int sid;//主键
	
	@Column(name = "chance_name")
	private String chanceName;//机会名称
    
	@Column(name = "forcast_time")
	private Calendar forcastTime;//预计成交时间
	
	@Column(name = "create_time")
	private Calendar createTime;//创建时间
	
	@Column(name = "forcast_cost")
	private Double forcastCost;//预计成交金额
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cr_user_id")
	private TeePerson crUser;//创建人
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private TeeCrmCustomerInfo customer;//关联客户

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

	public TeeCrmCustomerInfo getCustomer() {
		return customer;
	}

	public void setCustomer(TeeCrmCustomerInfo customer) {
		this.customer = customer;
	}
}
