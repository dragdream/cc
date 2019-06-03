package com.tianee.oa.subsys.bisengin.model;

import java.util.ArrayList;
import java.util.List;

public class BisViewModel {
	private String identity;//视图标识
	private String sql;//SQL语句
	private List<BisViewListItemModel> viewListItemsModels = new ArrayList();
	private String name;//视图名
	private int bisDataSourceId;
	private String countSql;//sql的count语句
	private String selectSql;
	private String fromSql;
	private String whereSql;
	private String orderBySql;
	private int type ;
	private String designModel;//设计模型  json形式
	
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<BisViewListItemModel> getViewListItemsModels() {
		return viewListItemsModels;
	}
	public void setViewListItemsModels(
			List<BisViewListItemModel> viewListItemsModels) {
		this.viewListItemsModels = viewListItemsModels;
	}
	public int getBisDataSourceId() {
		return bisDataSourceId;
	}
	public void setBisDataSourceId(int bisDataSourceId) {
		this.bisDataSourceId = bisDataSourceId;
	}
	public String getCountSql() {
		return countSql;
	}
	public void setCountSql(String countSql) {
		this.countSql = countSql;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getDesignModel() {
		return designModel;
	}
	public void setDesignModel(String designModel) {
		this.designModel = designModel;
	}
	public String getSelectSql() {
		return selectSql;
	}
	public void setSelectSql(String selectSql) {
		this.selectSql = selectSql;
	}
	public String getFromSql() {
		return fromSql;
	}
	public void setFromSql(String fromSql) {
		this.fromSql = fromSql;
	}
	public String getWhereSql() {
		return whereSql;
	}
	public void setWhereSql(String whereSql) {
		this.whereSql = whereSql;
	}
	public String getOrderBySql() {
		return orderBySql;
	}
	public void setOrderBySql(String orderBySql) {
		this.orderBySql = orderBySql;
	}
	
	
}
