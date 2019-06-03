package com.tianee.oa.subsys.bisengin.model;

import java.util.Calendar;

import javax.persistence.Column;

public class BisTableModel {
	private int sid;
	private String tableName;
	private String tableDesc;
	private int bisCatId;
	private int gen;
	private String entityClass;
	private String dbType;
	private int dataSource;
	private String driverClass;
	private String driverUrl;
	private String driverUsername;
	private String driverPwd;
	private String alias;
	private int sortNo;
	private Calendar createTime;
	private String userId;
	private String userName;
	private String userUuid;
	private String flowName;
	private int flowId;
	private String bisModel;
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableDesc() {
		return tableDesc;
	}
	public void setTableDesc(String tableDesc) {
		this.tableDesc = tableDesc;
	}
	public int getBisCatId() {
		return bisCatId;
	}
	public void setBisCatId(int bisCatId) {
		this.bisCatId = bisCatId;
	}
	public int getGen() {
		return gen;
	}
	public void setGen(int gen) {
		this.gen = gen;
	}
	public String getEntityClass() {
		return entityClass;
	}
	public void setEntityClass(String entityClass) {
		this.entityClass = entityClass;
	}
	public String getDbType() {
		return dbType;
	}
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	
	public int getDataSource() {
		return dataSource;
	}
	public void setDataSource(int dataSource) {
		this.dataSource = dataSource;
	}
	public String getDriverClass() {
		return driverClass;
	}
	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}
	public String getDriverUrl() {
		return driverUrl;
	}
	public void setDriverUrl(String driverUrl) {
		this.driverUrl = driverUrl;
	}
	public String getDriverUsername() {
		return driverUsername;
	}
	public void setDriverUsername(String driverUsername) {
		this.driverUsername = driverUsername;
	}
	public String getDriverPwd() {
		return driverPwd;
	}
	public void setDriverPwd(String driverPwd) {
		this.driverPwd = driverPwd;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public int getSortNo() {
		return sortNo;
	}
	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}
	public Calendar getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserUuid() {
		return userUuid;
	}
	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}
	public String getFlowName() {
		return flowName;
	}
	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}
	public int getFlowId() {
		return flowId;
	}
	public void setFlowId(int flowId) {
		this.flowId = flowId;
	}
	public String getBisModel() {
		return bisModel;
	}
	public void setBisModel(String bisModel) {
		this.bisModel = bisModel;
	}
}
