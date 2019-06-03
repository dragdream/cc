package com.tianee.oa.subsys.crm.core.invoice.bean;

import java.util.Calendar;

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
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomer;
import com.tianee.oa.subsys.crm.core.order.bean.TeeCrmOrder;


/**
 * 开票实体
 * @author zhaocaigen
 *
 */

@Entity
@Table(name="INVOICE")
public class TeeCrmInvoice{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="INVOICE_seq_gen")
	@SequenceGenerator(name="INVOICE_seq_gen", sequenceName="INVOICE_seq")
	@Column(name = "SID")
	private int sid;// 自增id
	
	@Column(name="INVOICE_NO")
	private String invoiceNo;//发票编号
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	private TeeCrmCustomer customer;//关联客户
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ORDER_ID")
	private TeeCrmOrder order;  //关联订单
	
	@Column(name="INVOICE_TIME")
    private Calendar invoiceTime;//开票日期
	
	@Column(name="INVOICE_AMOUNT")
	private double invoiceAmount;//开票金额
	
	@Column(name="INVOICE_TYPE")
	private String invoiceType;//开票类型
	
	@Column(name="INVOICE_STATUS")
	private int invoiceStatus;//开票状态（已开票、待开票、驳回）
	
	@Column(name="INVOICE_NUMBER") 
	private String invoiceNumber;//发票号码
	
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
	
	@Column(name="CONTACTS")
	private String contacts;//联系人
	
	@Column(name="CONTACT_NUMBER")
	private String contactNumber;//联系方式
	
	@Column(name="SEND_ADDRESS")
	private String sendAddress;//寄送地址
	
	@Column(name="HEADER_TYPE")
	private int headerType;//抬头类型
	
	@Column(name="INVOICE_HEADER")
	private String invoiceHeader;//开票抬头
	
	@Column(name="TELEPHONE")
	private String telephone;//电话
	
	@Column(name="LAST_EDIT_TIME")
	private Calendar lastEditTime;//最后一次修改日期
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="INVOICE_PERSON_ID")
	private TeePerson invoiceFinancial;//  开票财务
	
	@Column(name="REJECT_REASON")
	private String rejectReason;//驳回原因
	
	
	@Column(name="NSR_NUMBER")
	private String nsrNumber;//纳税人识别号
	
	
	@Column(name="KHH_NUMBER")
	private String khhNumber;//开户行账号
	
	@Column(name="KHH_ADDRESS")
	private String khhAddress;//开户行地址

	
	@Column(name="KHH_NAME")
	private String khhName;//开户行名称
	
	
	
	
	public String getNsrNumber() {
		return nsrNumber;
	}

	public String getKhhNumber() {
		return khhNumber;
	}

	public String getKhhAddress() {
		return khhAddress;
	}

	public String getKhhName() {
		return khhName;
	}

	public void setNsrNumber(String nsrNumber) {
		this.nsrNumber = nsrNumber;
	}

	public void setKhhNumber(String khhNumber) {
		this.khhNumber = khhNumber;
	}

	public void setKhhAddress(String khhAddress) {
		this.khhAddress = khhAddress;
	}

	public void setKhhName(String khhName) {
		this.khhName = khhName;
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

	public Calendar getInvoiceTime() {
		return invoiceTime;
	}

	public void setInvoiceTime(Calendar invoiceTime) {
		this.invoiceTime = invoiceTime;
	}

	public double getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public int getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(int invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
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

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getSendAddress() {
		return sendAddress;
	}

	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}

	public int getHeaderType() {
		return headerType;
	}

	public void setHeaderType(int headerType) {
		this.headerType = headerType;
	}

	public String getInvoiceHeader() {
		return invoiceHeader;
	}

	public void setInvoiceHeader(String invoiceHeader) {
		this.invoiceHeader = invoiceHeader;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Calendar getLastEditTime() {
		return lastEditTime;
	}

	public void setLastEditTime(Calendar lastEditTime) {
		this.lastEditTime = lastEditTime;
	}

	public TeePerson getInvoiceFinancial() {
		return invoiceFinancial;
	}

	public void setInvoiceFinancial(TeePerson invoiceFinancial) {
		this.invoiceFinancial = invoiceFinancial;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	
}
