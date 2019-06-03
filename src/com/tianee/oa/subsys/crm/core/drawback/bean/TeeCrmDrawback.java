package com.tianee.oa.subsys.crm.core.drawback.bean;

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
 * 退款实体
 * @author zhaocaigen
 *
 */

@Entity
@Table(name="DRAWBACK")
public class TeeCrmDrawback {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="DRAWBACK_seq_gen")
	@SequenceGenerator(name="DRAWBACK_seq_gen", sequenceName="DRAWBACK_seq")
	@Column(name = "SID")
	private int sid;// 自增id
	
	@Column(name="DRAWBACK_NO")
	private String drawbackNo;//退款编号
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	private TeeCrmCustomer customer;//关联客户
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ORDER_ID")
	private TeeCrmOrder order;  //关联订单
	
	@Column(name="DRAWBACK_TIME")
    private Calendar drawbackTime;//退款日期
	
	@Column(name="DRAWBACK_AMOUNT")
	private double drawbackAmount;//退款金额
	
	@Column(name="DRAWBACK_STYLE")
	private String drawbackStyle;//退款方式
	
	@Column(name="DRAWBACK_STATUS")
	private int drawbackStatus;//退款状态（待退款、已退款、驳回）
	
	@Column(name="REMARK")
	@Lob()
	private String remark;   //备注
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RESPONSIBLE_USER_ID")
	private TeePerson responsibleUser;//负责人（系统用户）
	
	@Column(name = "CREATE_TIME")
	private Calendar createTime;	// 创建时间
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATE_USER_ID")
	private TeePerson createUser;//创建者
	
	@Column(name="LAST_EDIT_TIME")
	private Calendar lastEditTime;//最后一次修改日期
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REFUND_PERSON_ID")
	private TeePerson refundFinance;//  退款财务
	
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

	public TeeCrmOrder getOrder() {
		return order;
	}

	public void setOrder(TeeCrmOrder order) {
		this.order = order;
	}

	public int getDrawbackStatus() {
		return drawbackStatus;
	}

	public void setDrawbackStatus(int drawbackStatus) {
		this.drawbackStatus = drawbackStatus;
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

	public TeePerson getCreateUser() {
		return createUser;
	}

	public void setCreateUser(TeePerson createUser) {
		this.createUser = createUser;
	}

	public String getDrawbackNo() {
		return drawbackNo;
	}

	public void setDrawbackNo(String drawbackNo) {
		this.drawbackNo = drawbackNo;
	}

	public Calendar getDrawbackTime() {
		return drawbackTime;
	}

	public void setDrawbackTime(Calendar drawbackTime) {
		this.drawbackTime = drawbackTime;
	}

	public double getDrawbackAmount() {
		return drawbackAmount;
	}

	public void setDrawbackAmount(double drawbackAmount) {
		this.drawbackAmount = drawbackAmount;
	}

	public String getDrawbackStyle() {
		return drawbackStyle;
	}

	public void setDrawbackStyle(String drawbackStyle) {
		this.drawbackStyle = drawbackStyle;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public Calendar getLastEditTime() {
		return lastEditTime;
	}

	public void setLastEditTime(Calendar lastEditTime) {
		this.lastEditTime = lastEditTime;
	}

	public TeePerson getRefundFinance() {
		return refundFinance;
	}

	public void setRefundFinance(TeePerson refundFinance) {
		this.refundFinance = refundFinance;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	
}
