package com.tianee.oa.subsys.crm.core.payback.model;

import java.util.ArrayList;
import java.util.List;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.webframe.httpModel.TeeBaseModel;

public class TeeCrmPaybackModel extends TeeBaseModel{
	
	private int sid;//主键
	
	private String paybackNo;//回款编号
	
	private int customerId;//Customer_ID	Int	11	否	客户表外键
	
	private String customerName;//客户名称
	
	private int orderId;  //订单id
	
	private String orderNo;//订单编号
	
	private String remindTimeDesc;//提醒时间
	
	private String paybackTimeDesc;//回款日期
	
	private double paybackAmount;//回款金额
	
	private String paybackStyle;//回款方式  crmdode
	
	private String paybackStyleDesc;//回款方式
	
	private int paybackStatus;//回款状态（待回款、已回款、驳回）
	
	private String paybackStatusDesc;//回款状态
	
	private String remark;   //备注
	
    private int addPersonId;//创建人Id
	
	private String addPersonName;// 创建人名称
	
	private String createTimeDesc;//创建时间
	
	private int managePersonId;//  负责人
	
	private String managePersonName;//  负责人
	
	private String lastEditTimeDesc;//最后一次修改日期
	
	private int paymentFinancialId;//回款财务id
	
	private String paymentFinancialName;
	
	private String rejectReason;//驳回原因
	
	List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>(); //附件集合

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getPaybackNo() {
		return paybackNo;
	}

	public void setPaybackNo(String paybackNo) {
		this.paybackNo = paybackNo;
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

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public String getRemindTimeDesc() {
		return remindTimeDesc;
	}

	public void setRemindTimeDesc(String remindTimeDesc) {
		this.remindTimeDesc = remindTimeDesc;
	}

	public String getPaybackTimeDesc() {
		return paybackTimeDesc;
	}

	public void setPaybackTimeDesc(String paybackTimeDesc) {
		this.paybackTimeDesc = paybackTimeDesc;
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

	public String getPaybackStyleDesc() {
		return paybackStyleDesc;
	}

	public void setPaybackStyleDesc(String paybackStyleDesc) {
		this.paybackStyleDesc = paybackStyleDesc;
	}

	public int getPaybackStatus() {
		return paybackStatus;
	}

	public void setPaybackStatus(int paybackStatus) {
		this.paybackStatus = paybackStatus;
	}

	public String getPaybackStatusDesc() {
		return paybackStatusDesc;
	}

	public void setPaybackStatusDesc(String paybackStatusDesc) {
		this.paybackStatusDesc = paybackStatusDesc;
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

	public String getLastEditTimeDesc() {
		return lastEditTimeDesc;
	}

	public void setLastEditTimeDesc(String lastEditTimeDesc) {
		this.lastEditTimeDesc = lastEditTimeDesc;
	}

	public int getPaymentFinancialId() {
		return paymentFinancialId;
	}

	public void setPaymentFinancialId(int paymentFinancialId) {
		this.paymentFinancialId = paymentFinancialId;
	}

	public String getPaymentFinancialName() {
		return paymentFinancialName;
	}

	public void setPaymentFinancialName(String paymentFinancialName) {
		this.paymentFinancialName = paymentFinancialName;
	}

	public List<TeeAttachmentModel> getAttachmodels() {
		return attachmodels;
	}

	public void setAttachmodels(List<TeeAttachmentModel> attachmodels) {
		this.attachmodels = attachmodels;
	}
	
}
