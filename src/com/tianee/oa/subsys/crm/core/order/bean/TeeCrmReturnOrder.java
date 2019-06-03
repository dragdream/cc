package com.tianee.oa.subsys.crm.core.order.bean;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomer;
import com.tianee.oa.subsys.crm.core.product.bean.TeeCrmProducts;

/**
 * 退货单实体
 * @author zhaocaigen
 *
 */
@Entity
@Table(name = "CRM_RETURN_ORDER")
public class TeeCrmReturnOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CRM_RETURN_ORDER_seq_gen")
	@SequenceGenerator(name="CRM_RETURN_ORDER_seq_gen", sequenceName="CRM_RETURN_ORDER_seq")
	@Column(name = "SID")
	private int sid;//主键
	
	@Column(name="RETURN_ORDER_NO")
	private String returnOrderNo;//退货单号
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	private TeeCrmCustomer customer;//关联客户
	
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="ORDER_ID")
	private TeeCrmOrder order;  //关联订单
	
	@Column(name="RETURN_TIME")
	private Calendar returnTime;//退货日期
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ADD_PERSON_ID")
	private TeePerson addPerson;    //创建人
	
	@Column(name="CREATE_TIME")
	private Calendar createTime;//创建日期
	
	@Column(name="LAST_EDIT_TIME")
	private Calendar lastEditTime;//最后修改日期
	
	@Column(name="RETURN_ORDER_STATUS")
	private int returnOrderStatus;//退货单状态
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MANAGE_PERSON_ID")
	private TeePerson retOrderManagePerson;//  负责人
	
	@Column(name="RETURN_REMARK")
	@Lob()
	private String returnRemark;   //退货备注
	
	@Column(name="RETURN_REASON")
	private String returnReason;//退货原因
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ORDER_APPROVAL_PERSON_ID")
	private TeePerson orderApproval;//  订单管理员
	
	@Column(name="REJECT_REASON")
	private String rejectReason;//驳回原因
	
	/*@ManyToMany(targetEntity=TeeCrmProducts.class,fetch = FetchType.LAZY  ) 
	@JoinTable(name="RETORDER_PRODUCTS",
			joinColumns={@JoinColumn(name="RETURN_ORDER_ID")},       
        inverseJoinColumns={@JoinColumn(name="PRODUCTS_ID")}  ) 	
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	private Set<TeeCrmProducts> returnProducts = new HashSet<TeeCrmProducts>(0);*/

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeeCrmCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(TeeCrmCustomer customer) {
		this.customer = customer;
	}

	public TeeCrmOrder getOrder() {
		return order;
	}

	public void setOrder(TeeCrmOrder order) {
		this.order = order;
	}

	public Calendar getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Calendar returnTime) {
		this.returnTime = returnTime;
	}

	public TeePerson getAddPerson() {
		return addPerson;
	}

	public void setAddPerson(TeePerson addPerson) {
		this.addPerson = addPerson;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public int getReturnOrderStatus() {
		return returnOrderStatus;
	}

	public void setReturnOrderStatus(int returnOrderStatus) {
		this.returnOrderStatus = returnOrderStatus;
	}

	public TeePerson getRetOrderManagePerson() {
		return retOrderManagePerson;
	}

	public void setRetOrderManagePerson(TeePerson retOrderManagePerson) {
		this.retOrderManagePerson = retOrderManagePerson;
	}

	public String getReturnRemark() {
		return returnRemark;
	}

	public void setReturnRemark(String returnRemark) {
		this.returnRemark = returnRemark;
	}

	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	public String getReturnOrderNo() {
		return returnOrderNo;
	}

	public void setReturnOrderNo(String returnOrderNo) {
		this.returnOrderNo = returnOrderNo;
	}

	public TeePerson getOrderApproval() {
		return orderApproval;
	}

	public void setOrderApproval(TeePerson orderApproval) {
		this.orderApproval = orderApproval;
	}

/*	public Set<TeeCrmProducts> getReturnProducts() {
		return returnProducts;
	}

	public void setReturnProducts(Set<TeeCrmProducts> returnProducts) {
		this.returnProducts = returnProducts;
	}*/

	public Calendar getLastEditTime() {
		return lastEditTime;
	}

	public void setLastEditTime(Calendar lastEditTime) {
		this.lastEditTime = lastEditTime;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	
}
