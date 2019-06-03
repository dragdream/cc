package com.tianee.oa.subsys.crm.core.order.bean;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.subsys.crm.core.chances.bean.TeeCrmChances;
import com.tianee.oa.subsys.crm.core.contacts.bean.TeeCrmContacts;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomer;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomerInfo;

/**
 * 订单实体
 * @author zhaocaigen
 *
 */

@Entity
@Table(name = "CRM_ORDER")
public class TeeCrmOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CRM_ORDER_seq_gen")
	@SequenceGenerator(name="CRM_ORDER_seq_gen", sequenceName="CRM_ORDER_seq")
	@Column(name = "SID")
	private int sid;//主键
	
	@Column(name="ORDER_NO")
	private String orderNo;//订单编号
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	private TeeCrmCustomer customer;//关联客户
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CHANCES_ID")
	private TeeCrmChances chance;//关联商机
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="RECEIVER_ID")
	private TeeCrmContacts receivePerson;    //收货人
	
	@Column(name="RECEIVER_TELEPHONE")
	private String receiverTelephone;//收货人电话
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ADD_PERSON_ID")
	private TeePerson addPerson;    //创建人
	
	@Column(name="CREATE_TIME")
	private Calendar createTime;//创建日期
	
	@Column(name="LAST_EDIT_TIME")
	private Calendar lastEditTime;//最后一次修改日期
	
	@Column(name="ORDERED_TIME")
	private Calendar orderedTime;//下单日期
	
	@Column(name="TRANSACTIONS_TIMES")
	private Calendar transactionsTime;//交货日期
	
	@Column(name="ORDER_STATUS")
	private int orderStatus;//订单状态(确认中、已确认、驳回)
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MANAGER_USER_ID")
	private TeePerson orderManagePerson;//  负责人
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ORDER_APPROVAL_PERSON_ID")
	private TeePerson orderApproval;//  订单管理员
	
	@Column(name="REMARK")
	@Lob()
	private String remark;   //备注
	
	@Column(name="REJECT_REASON")
	private String rejectReason;//驳回原因

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

	public TeeCrmChances getChance() {
		return chance;
	}

	public void setChance(TeeCrmChances chance) {
		this.chance = chance;
	}

	public TeeCrmContacts getReceivePerson() {
		return receivePerson;
	}

	public void setReceivePerson(TeeCrmContacts receivePerson) {
		this.receivePerson = receivePerson;
	}

	public String getReceiverTelephone() {
		return receiverTelephone;
	}

	public void setReceiverTelephone(String receiverTelephone) {
		this.receiverTelephone = receiverTelephone;
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

	public Calendar getOrderedTime() {
		return orderedTime;
	}

	public void setOrderedTime(Calendar orderedTime) {
		this.orderedTime = orderedTime;
	}

	public Calendar getTransactionsTime() {
		return transactionsTime;
	}

	public void setTransactionsTime(Calendar transactionsTime) {
		this.transactionsTime = transactionsTime;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public TeePerson getOrderManagePerson() {
		return orderManagePerson;
	}

	public void setOrderManagePerson(TeePerson orderManagePerson) {
		this.orderManagePerson = orderManagePerson;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public TeePerson getOrderApproval() {
		return orderApproval;
	}

	public void setOrderApproval(TeePerson orderApproval) {
		this.orderApproval = orderApproval;
	}

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
