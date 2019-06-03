package com.tianee.oa.subsys.crm.core.order.model;


import com.tianee.oa.webframe.httpModel.TeeBaseModel;

/**
 * 订单
 * @author zhaocaigen
 *
 */
public class TeeCrmOrderModel extends TeeBaseModel{
	private int sid;//主键
	private String orderNo;//订单编号
	private String customerName; //客户姓名
	private int customerId; //客户id
	private String chanceName; //商机名称
	private int chanceId; //商机Id
	private int receiverId;//收货人
	private String receiverName;//收货人姓名
    private String receiverTelephone;//收货人电话
    private int addPersonId;//创建人id
    private String addPersonName;//创建人姓名
    private String createTimeDesc;//创建日期
    private String lastEditTimeDesc;//最后一次修改日期
    private String orderTimeDesc;//下单日期
    private String transactionsTimeDesc;//交货日期
    private int orderStatus;
    private String orderStatusDesc;//订单状态
    private int managePersonId;//负责人id
    private String managePersonName;//负责人姓名
    private String remark;//备注
    private int orderApprovalId;//订单管理员id
    private String orderApprovalName;
	private String rejectReason;//驳回原因
   // private String orderManageUserName;//订单管理员名称
	
	
	
	public int getSid() {
		return sid;
	}
	
	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public void setSid(int sid) {
		this.sid = sid;
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
	public String getChanceName() {
		return chanceName;
	}
	public void setChanceName(String chanceName) {
		this.chanceName = chanceName;
	}
	public int getChanceId() {
		return chanceId;
	}
	public void setChanceId(int chanceId) {
		this.chanceId = chanceId;
	}
	public int getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverTelephone() {
		return receiverTelephone;
	}
	public void setReceiverTelephone(String receiverTelephone) {
		this.receiverTelephone = receiverTelephone;
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
	public String getOrderTimeDesc() {
		return orderTimeDesc;
	}
	public void setOrderTimeDesc(String orderTimeDesc) {
		this.orderTimeDesc = orderTimeDesc;
	}
	public String getTransactionsTimeDesc() {
		return transactionsTimeDesc;
	}
	public void setTransactionsTimeDesc(String transactionsTimeDesc) {
		this.transactionsTimeDesc = transactionsTimeDesc;
	}
	public String getOrderStatusDesc() {
		return orderStatusDesc;
	}
	public void setOrderStatusDesc(String orderStatusDesc) {
		this.orderStatusDesc = orderStatusDesc;
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
	public int getOrderApprovalId() {
		return orderApprovalId;
	}
	public void setOrderApprovalId(int orderApprovalId) {
		this.orderApprovalId = orderApprovalId;
	}
	public String getLastEditTimeDesc() {
		return lastEditTimeDesc;
	}
	public void setLastEditTimeDesc(String lastEditTimeDesc) {
		this.lastEditTimeDesc = lastEditTimeDesc;
	}
	public String getOrderApprovalName() {
		return orderApprovalName;
	}
	public void setOrderApprovalName(String orderApprovalName) {
		this.orderApprovalName = orderApprovalName;
	}
	public String getRejectReason() {
		return rejectReason;
	}
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

}
