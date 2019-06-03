package com.tianee.oa.subsys.crm.core.drawback.model;


import com.tianee.oa.webframe.httpModel.TeeBaseModel;

public class TeeCrmDrawbackModel extends TeeBaseModel {
	
	private int sid;//主键
	
	private String drawbackNo;//退款编号
	
	private int customerId;//Customer_ID	Int	11	否	客户表外键
	
	private String customerName;//客户名称
	
	private int orderId;  //订单id
	
	private String orderNo;//订单编号
	
	private String drawbackTimeDesc;//退款日期
	
	private double drawbackAmount;//退款金额
	
	private String drawbackStyle;//退款方式  crmdode
	
	private String drawbackStyleDesc;//
	
	private int drawbackStatus;//退款状态（待回款、已回款、驳回）
	
	private String drawbackStatusDesc;//回款状态
	
	private String remark;   //备注
	
    private int addPersonId;//创建人Id
	
	private String addPersonName;// 创建人名称
	
	private String createTimeDesc;//创建时间
	
	private int managePersonId;//  负责人
	
	private String managePersonName;//  负责人
	
	private String lastEditTimeDesc;//最后一次修改日期
	
	private int refundFinancialId;//退款财务id
	
	private String refundFinancialName;
	private String rejectReason;//驳回原因

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getDrawbackNo() {
		return drawbackNo;
	}

	public void setDrawbackNo(String drawbackNo) {
		this.drawbackNo = drawbackNo;
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

	public String getDrawbackTimeDesc() {
		return drawbackTimeDesc;
	}

	public void setDrawbackTimeDesc(String drawbackTimeDesc) {
		this.drawbackTimeDesc = drawbackTimeDesc;
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

	public String getDrawbackStyleDesc() {
		return drawbackStyleDesc;
	}

	public void setDrawbackStyleDesc(String drawbackStyleDesc) {
		this.drawbackStyleDesc = drawbackStyleDesc;
	}

	public int getDrawbackStatus() {
		return drawbackStatus;
	}

	public void setDrawbackStatus(int drawbackStatus) {
		this.drawbackStatus = drawbackStatus;
	}

	public String getDrawbackStatusDesc() {
		return drawbackStatusDesc;
	}

	public void setDrawbackStatusDesc(String drawbackStatusDesc) {
		this.drawbackStatusDesc = drawbackStatusDesc;
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

	public int getRefundFinancialId() {
		return refundFinancialId;
	}

	public void setRefundFinancialId(int refundFinancialId) {
		this.refundFinancialId = refundFinancialId;
	}

	public String getRefundFinancialName() {
		return refundFinancialName;
	}

	public void setRefundFinancialName(String refundFinancialName) {
		this.refundFinancialName = refundFinancialName;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	
	
}
