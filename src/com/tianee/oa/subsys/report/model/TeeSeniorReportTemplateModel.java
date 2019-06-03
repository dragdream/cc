package com.tianee.oa.subsys.report.model;




public class TeeSeniorReportTemplateModel {
	private String uuid;//uuid
	private String tplName;
	private String header;
	private String body;
	private String footer;
	private String dataIdentity;
	private int pageSize;
	private String group;
	private String userPrivIds;
	private String userPrivNames;
	private String deptPrivIds;
	private String deptPrivNames;
	private String chartType;//图形
	private int reverse = 0;//是否行列转置
	private int catId;
	private String catName;
	private String color;
	private String params;
	private int status;
	private String model;//报表模型，用于对设计视图进行管控
	private int ctType;
	private String ctView;
	private String ctReport;
	private String ctParams;
	private int resId;
	private String resName;
	
	public int getCtType() {
		return ctType;
	}
	public void setCtType(int ctType) {
		this.ctType = ctType;
	}
	public String getCtView() {
		return ctView;
	}
	public void setCtView(String ctView) {
		this.ctView = ctView;
	}
	public String getCtReport() {
		return ctReport;
	}
	public void setCtReport(String ctReport) {
		this.ctReport = ctReport;
	}
	public String getCtParams() {
		return ctParams;
	}
	public void setCtParams(String ctParams) {
		this.ctParams = ctParams;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getTplName() {
		return tplName;
	}
	public void setTplName(String tplName) {
		this.tplName = tplName;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getFooter() {
		return footer;
	}
	public void setFooter(String footer) {
		this.footer = footer;
	}
	public String getDataIdentity() {
		return dataIdentity;
	}
	public void setDataIdentity(String dataIdentity) {
		this.dataIdentity = dataIdentity;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getUserPrivIds() {
		return userPrivIds;
	}
	public void setUserPrivIds(String userPrivIds) {
		this.userPrivIds = userPrivIds;
	}
	public String getUserPrivNames() {
		return userPrivNames;
	}
	public void setUserPrivNames(String userPrivNames) {
		this.userPrivNames = userPrivNames;
	}
	public String getDeptPrivIds() {
		return deptPrivIds;
	}
	public void setDeptPrivIds(String deptPrivIds) {
		this.deptPrivIds = deptPrivIds;
	}
	public String getDeptPrivNames() {
		return deptPrivNames;
	}
	public void setDeptPrivNames(String deptPrivNames) {
		this.deptPrivNames = deptPrivNames;
	}
	public String getChartType() {
		return chartType;
	}
	public void setChartType(String chartType) {
		this.chartType = chartType;
	}
	public int getReverse() {
		return reverse;
	}
	public void setReverse(int reverse) {
		this.reverse = reverse;
	}
	public int getCatId() {
		return catId;
	}
	public void setCatId(int catId) {
		this.catId = catId;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public int getResId() {
		return resId;
	}
	public void setResId(int resId) {
		this.resId = resId;
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	
}
