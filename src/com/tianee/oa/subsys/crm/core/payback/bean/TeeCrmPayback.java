package com.tianee.oa.subsys.crm.core.payback.bean;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomer;
import com.tianee.oa.subsys.crm.core.order.bean.TeeCrmOrder;

/**
 * 回款实体
 * @author zhaocaigen
 *
 */
@Entity
@Table(name="PAYBACK")
public class TeeCrmPayback {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="PAYBACK_seq_gen")
	@SequenceGenerator(name="PAYBACK_seq_gen", sequenceName="PAYBACK_seq")
	@Column(name = "SID")
	private int sid;// 自增id
	
	@Column(name="PAYBACK_NO")
	private String paybackNo;//回款编号
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	private TeeCrmCustomer customer;//关联客户
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ORDER_ID")
	private TeeCrmOrder order;  //关联订单
	
	@Column(name="REMIND_TIME")
	private Calendar remindTime;//提醒时间
	
	@Column(name="PAYBACK_TIME")
    private Calendar paybackTime;//回款日期
	
	@Column(name="PAYBACK_AMOUNT")
	private double paybackAmount;//回款金额
	
	@Column(name="PAYBACK_STYLE")
	private String paybackStyle;//回款方式  crmcode
	
	@Column(name="PAYBACK_STATUS")
	private int paybackStatus;//回款状态（待回款、已回款、驳回）
	
	@Column(name="REMARK")
	@Lob()
	private String remark;   //备注
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RESPONSIBLE_USER")
	private TeePerson responsibleUser;//负责人（系统用户）
	
	@Column(name = "CREATE_TIME")
	private Calendar createTime;	// 创建时间
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATE_USER_ID")
	private TeePerson createUser;//创建者
	
	@Column(name="LAST_EDIT_TIME")
	private Calendar lastEditTime;//最后一次修改日期
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PAYMENT_PERSON_ID")
	private TeePerson paymentFinancial;//  回款财务
	
	@Column(name="REJECT_REASON")
	private String rejectReason;//驳回原因
	
	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

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

	public Calendar getRemindTime() {
		return remindTime;
	}

	public void setRemindTime(Calendar remindTime) {
		this.remindTime = remindTime;
	}

	public Calendar getPaybackTime() {
		return paybackTime;
	}

	public void setPaybackTime(Calendar paybackTime) {
		this.paybackTime = paybackTime;
	}

	public double getPaybackAmount() {
		return paybackAmount;
	}

	public void setPaybackAmount(double paybackAmount) {
		this.paybackAmount = paybackAmount;
	}

	public String getPaybackStyle() {
		return paybackStyle;
	}

	public void setPaybackStyle(String paybackStyle) {
		this.paybackStyle = paybackStyle;
	}

	public int getPaybackStatus() {
		return paybackStatus;
	}

	public void setPaybackStatus(int paybackStatus) {
		this.paybackStatus = paybackStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public TeePerson getResponsibleUser() {
		return responsibleUser;
	}

	public void setResponsibleUser(TeePerson responsibleUser) {
		this.responsibleUser = responsibleUser;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public TeePerson getCreateUser() {
		return createUser;
	}

	public void setCreateUser(TeePerson createUser) {
		this.createUser = createUser;
	}

	public Calendar getLastEditTime() {
		return lastEditTime;
	}

	public void setLastEditTime(Calendar lastEditTime) {
		this.lastEditTime = lastEditTime;
	}

	public TeePerson getPaymentFinancial() {
		return paymentFinancial;
	}

	public void setPaymentFinancial(TeePerson paymentFinancial) {
		this.paymentFinancial = paymentFinancial;
	}

	public String getPaybackNo() {
		return paybackNo;
	}

	public void setPaybackNo(String paybackNo) {
		this.paybackNo = paybackNo;
	}

}
