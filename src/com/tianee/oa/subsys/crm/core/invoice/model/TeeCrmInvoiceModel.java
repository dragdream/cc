package com.tianee.oa.subsys.crm.core.invoice.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.webframe.httpModel.TeeBaseModel;

public class TeeCrmInvoiceModel extends TeeBaseModel {
	private int sid;// 自增id
	
	private String invoiceNo;//发票编号
	
	private int customerId;//Customer_ID	Int	11	否	客户表外键
	
	private String customerName;//客户名称
	
	private int orderId;  //订单id
	
	private String orderNo;//订单编号
	
	private String invoiceTimeDesc;//开票日期
	
	private double invoiceAmount;//开票金额
	
	private String invoiceType;//开票类型
	
	private String invoiceTypeDesc;//
	
	private int invoiceStatus;//开票状态（已开票、待开票、驳回）
	
	private String invoiceStatusDesc;
	
	private String invoiceNumber;//发票号
	
	private String remark;   //备注
	
    private int addPersonId;//创建人Id
	
	private String addPersonName;// 创建人名称
	
	private String createTimeDesc;//创建时间
	
	private int managePersonId;//  负责人
	
	private String managePersonName;//  负责人
	
	private String contacts;//联系人
	
	private String contactNumber;//联系方式
	
	private String sendAddress;//寄送地址
	
	private int headerType;//抬头类型
	
	private String headerTypeDesc;//抬头类型
	
	private String invoiceHeader;//开票抬头
	
	private String telephone;//电话
	
	private String lastEditTimeDesc;//最后一次修改日期
	
	private int invoiceFinancialId;//开票财务
	
	private String invoiceFinancialName;
	
	private String rejectReason;//驳回原因
	
	
	private String nsrNumber;//纳税人识别号
	
	private String khhNumber;//开户行账号
	
	private String khhAddress;//开户行地址

	private String khhName;//开户行名称
	
	
	List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>(); //附件集合

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getInvoiceTimeDesc() {
		return invoiceTimeDesc;
	}

	public void setInvoiceTimeDesc(String invoiceTimeDesc) {
		this.invoiceTimeDesc = invoiceTimeDesc;
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

	public String getInvoiceTypeDesc() {
		return invoiceTypeDesc;
	}

	public void setInvoiceTypeDesc(String invoiceTypeDesc) {
		this.invoiceTypeDesc = invoiceTypeDesc;
	}

	public int getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(int invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getInvoiceStatusDesc() {
		return invoiceStatusDesc;
	}

	public void setInvoiceStatusDesc(String invoiceStatusDesc) {
		this.invoiceStatusDesc = invoiceStatusDesc;
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

	public int getAddPersonId() {
		return addPersonId;
	}

	public void setAddPersonId(int addPersonId) {
		this.addPersonId = addPersonId;
	}

	public String getAddPersonName() {
		return addPersonName;
	}

	public void setAddPersonName(String addPersonName) {
		this.addPersonName = addPersonName;
	}

	public String getCreateTimeDesc() {
		return createTimeDesc;
	}

	public void setCreateTimeDesc(String createTimeDesc) {
		this.createTimeDesc = createTimeDesc;
	}

	public int getManagePersonId() {
		return managePersonId;
	}

	public void setManagePersonId(int managePersonId) {
		this.managePersonId = managePersonId;
	}

	public String getManagePersonName() {
		return managePersonName;
	}

	public void setManagePersonName(String managePersonName) {
		this.managePersonName = managePersonName;
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

	public String getHeaderTypeDesc() {
		return headerTypeDesc;
	}

	public void setHeaderTypeDesc(String headerTypeDesc) {
		this.headerTypeDesc = headerTypeDesc;
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

	public String getLastEditTimeDesc() {
		return lastEditTimeDesc;
	}

	public void setLastEditTimeDesc(String lastEditTimeDesc) {
		this.lastEditTimeDesc = lastEditTimeDesc;
	}

	public int getInvoiceFinancialId() {
		return invoiceFinancialId;
	}

	public void setInvoiceFinancialId(int invoiceFinancialId) {
		this.invoiceFinancialId = invoiceFinancialId;
	}

	public String getInvoiceFinancialName() {
		return invoiceFinancialName;
	}

	public void setInvoiceFinancialName(String invoiceFinancialName) {
		this.invoiceFinancialName = invoiceFinancialName;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public List<TeeAttachmentModel> getAttachmodels() {
		return attachmodels;
	}

	public void setAttachmodels(List<TeeAttachmentModel> attachmodels) {
		this.attachmodels = attachmodels;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

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
	
	
}
