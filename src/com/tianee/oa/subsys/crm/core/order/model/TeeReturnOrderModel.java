package com.tianee.oa.subsys.crm.core.order.model;

import com.tianee.oa.webframe.httpModel.TeeBaseModel;

public class TeeReturnOrderModel extends TeeBaseModel{
	private int sid;//主键
	private String returnOrderNo;//脱货单编号
	private String customerName; //客户姓名
	private int customerId; //客户id
	private int orderId;  //订单id
	private String orderNo;//订单编号
	private String returnTimeDesc;//退货日期
	private int addPersonId;//创建人id
	private String addPersonName;//创建人姓名
	private String createTimeDesc;//创建日期
	private String lastEditTimeDesc;//最后修改日期
	private String returnOrderStatusDesc;//退货单状态
	private int returnOrderStatus;
	private String returnRemark;   //退货备注
	private String returnReason;//退货原因
	private String returnReasonDesc;
	private int managePersonId;//负责人id
	private String managePersonName;//负责人姓名
	private int orderApprovalId;//订单管理员id
	private String orderApprovalName;
	private String returnProductsIds;//退货产品
	private String returnProductsNames;
	private String rejectReason;//驳回原因
	
	
	public int getReturnOrderStatus() {
		return returnOrderStatus;
	}
	public void setReturnOrderStatus(int returnOrderStatus) {
		this.returnOrderStatus = returnOrderStatus;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getReturnOrderNo() {
		return returnOrderNo;
	}
	public void setReturnOrderNo(String returnOrderNo) {
		this.returnOrderNo = returnOrderNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
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
	public String getReturnTimeDesc() {
		return returnTimeDesc;
	}
	public void setReturnTimeDesc(String returnTimeDesc) {
		this.returnTimeDesc = returnTimeDesc;
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
	public String getReturnOrderStatusDesc() {
		return returnOrderStatusDesc;
	}
	public void setReturnOrderStatusDesc(String returnOrderStatusDesc) {
		this.returnOrderStatusDesc = returnOrderStatusDesc;
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
	public int getOrderApprovalId() {
		return orderApprovalId;
	}
	public void setOrderApprovalId(int orderApprovalId) {
		this.orderApprovalId = orderApprovalId;
	}
	public String getOrderApprovalName() {
		return orderApprovalName;
	}
	public void setOrderApprovalName(String orderApprovalName) {
		this.orderApprovalName = orderApprovalName;
	}
	public String getReturnProductsIds() {
		return returnProductsIds;
	}
	public void setReturnProductsIds(String returnProductsIds) {
		this.returnProductsIds = returnProductsIds;
	}
	public String getReturnProductsNames() {
		return returnProductsNames;
	}
	public void setReturnProductsNames(String returnProductsNames) {
		this.returnProductsNames = returnProductsNames;
	}
	public String getLastEditTimeDesc() {
		return lastEditTimeDesc;
	}
	public void setLastEditTimeDesc(String lastEditTimeDesc) {
		this.lastEditTimeDesc = lastEditTimeDesc;
	}
	public String getReturnReasonDesc() {
		return returnReasonDesc;
	}
	public void setReturnReasonDesc(String returnReasonDesc) {
		this.returnReasonDesc = returnReasonDesc;
	}
	public String getRejectReason() {
		return rejectReason;
	}
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

}
