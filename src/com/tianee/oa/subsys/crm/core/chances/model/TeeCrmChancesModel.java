package com.tianee.oa.subsys.crm.core.chances.model;


public class TeeCrmChancesModel {
	
	private int sid; //主键
	
	private String chanceName;//机会名称
	
	private String forcastTimeDesc;//预计成交时间
	
	private String createTimeDesc;//创建时间
	
	private Double forcastCost;//预计成交金额
	
    private int customerId;//Customer_ID	Int	11	否	客户表外键
	
	private String customerName;//客户名称
	
    private int addPersonId;//创建人Id
	
	private String addPersonName;// 创建人名称
	
    private int managePersonId;//  负责人
	
	private String managePersonName;//  负责人
	
	private String remark;   //备注
	
	private String chanceStatusDesc;   //商机状态（未处理、赢单、输单）
	
	private int clueId;// 线索外键
	
	private String productsIds;  //产品
	
	private String productsNames; //产品
	
	private String lastEditTimeDesc; //最后一次修改时间

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getChanceName() {
		return chanceName;
	}

	public void setChanceName(String chanceName) {
		this.chanceName = chanceName;
	}

	public String getForcastTimeDesc() {
		return forcastTimeDesc;
	}

	public void setForcastTimeDesc(String forcastTimeDesc) {
		this.forcastTimeDesc = forcastTimeDesc;
	}

	public String getCreateTimeDesc() {
		return createTimeDesc;
	}

	public void setCreateTimeDesc(String createTimeDesc) {
		this.createTimeDesc = createTimeDesc;
	}

	public Double getForcastCost() {
		return forcastCost;
	}

	public void setForcastCost(Double forcastCost) {
		this.forcastCost = forcastCost;
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

	public String getChanceStatusDesc() {
		return chanceStatusDesc;
	}

	public void setChanceStatusDesc(String chanceStatusDesc) {
		this.chanceStatusDesc = chanceStatusDesc;
	}

	public int getClueId() {
		return clueId;
	}

	public void setClueId(int clueId) {
		this.clueId = clueId;
	}

	public String getProductsIds() {
		return productsIds;
	}

	public void setProductsIds(String productsIds) {
		this.productsIds = productsIds;
	}

	public String getProductsNames() {
		return productsNames;
	}

	public void setProductsNames(String productsNames) {
		this.productsNames = productsNames;
	}

	public String getLastEditTimeDesc() {
		return lastEditTimeDesc;
	}

	public void setLastEditTimeDesc(String lastEditTimeDesc) {
		this.lastEditTimeDesc = lastEditTimeDesc;
	}
	
}
