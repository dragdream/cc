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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Index;

import com.tianee.oa.core.org.bean.TeePerson;

@SuppressWarnings("serial")
@Entity
@Table(name = "CRM_CONTRACT_RECV_PAYMENTS")
public class TeeCrmContractRecvPayments  {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CRM_CO_RE_PAY_seq_gen")
	@SequenceGenerator(name="CRM_CO_RE_PAY_seq_gen", sequenceName="CRM_CO_RE_PAY_seq")
	@Column(name = "SID")
	private int sid;// 自增id
	
	@ManyToOne()
	@Index(name="IDX66f7642b3bdb4f6a853e3270ec8")
	@JoinColumn(name = "CONTRACT_ID")
	private TeeCrmContract contract;// 合同

	@ManyToOne()
	@Index(name="IDX30b604f8b67b477e9dd37277faf")
	@JoinColumn(name = "USER_ID")
	private TeePerson managerUser;// 合同回款负责人
	
	@Column(name = "PLAN_RECV_DATE")
	private Date planRecvDate;// 预计回款日期
	
	@Column(name = "RECV_PAY_AMOUNT")
	private double recvPayAmount;// 回款金额

		
	@Column(name = "RECV_PAY_PERCENT")
	private double recvPayParcent;//回款百分比
		
	@Column(name = "RECV_DATE")
	private Date recvDate;// 实际回款日期
	
	@Column(name = "RECV_PAY_PERSON")
	private String recvPayPerson;//收款人 --暂停用
	
	@Column(name = "RECV_PAYMENT_FLAG" , columnDefinition="char(1) default 0")
	private String recvPaymentFlag;// 是否已回款   0 -否 1-是

	@Column(name = "REMARK" , length=800)
	private String remark;// 备注
		
	@Column(name = "MAKE_INVICE" , columnDefinition="char(1) default 0")
	private String makeInvice;// 是否开发票 0 -否 1-是
		
	@Column(name = "inviceNumber" , length=100)
	private String inviceNumber;// 	发票号
	
	@Column(name = "INVICE_DATE")
	private Date inviceDate;// 发票日期

	@Column(name = "INVICE_SEND_DATE")
	private Date inviceSendDate;// 寄出发票日期
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME")
	private Date createTime;// 创建时间

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

	public Date getPlanRecvDate() {
		return planRecvDate;
	}

	public void setPlanRecvDate(Date planRecvDate) {
		this.planRecvDate = planRecvDate;
	}

	public double getRecvPayAmount() {
		return recvPayAmount;
	}

	public void setRecvPayAmount(double recvPayAmount) {
		this.recvPayAmount = recvPayAmount;
	}

	public double getRecvPayParcent() {
		return recvPayParcent;
	}

	public void setRecvPayParcent(double recvPayParcent) {
		this.recvPayParcent = recvPayParcent;
	}

	public Date getRecvDate() {
		return recvDate;
	}

	public void setRecvDate(Date recvDate) {
		this.recvDate = recvDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMakeInvice() {
		return makeInvice;
	}

	public void setMakeInvice(String makeInvice) {
		this.makeInvice = makeInvice;
	}

	public String getInviceNumber() {
		return inviceNumber;
	}

	public void setInviceNumber(String inviceNumber) {
		this.inviceNumber = inviceNumber;
	}

	public Date getInviceDate() {
		return inviceDate;
	}

	public void setInviceDate(Date inviceDate) {
		this.inviceDate = inviceDate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRecvPayPerson() {
		return recvPayPerson;
	}

	public void setRecvPayPerson(String recvPayPerson) {
		this.recvPayPerson = recvPayPerson;
	}

	public String getRecvPaymentFlag() {
		return recvPaymentFlag;
	}

	public void setRecvPaymentFlag(String recvPaymentFlag) {
		this.recvPaymentFlag = recvPaymentFlag;
	}

	public Date getInviceSendDate() {
		return inviceSendDate;
	}

	public void setInviceSendDate(Date inviceSendDate) {
		this.inviceSendDate = inviceSendDate;
	}

	public TeePerson getManagerUser() {
		return managerUser;
	}

	public void setManagerUser(TeePerson managerUser) {
		this.managerUser = managerUser;
	}
		

}
