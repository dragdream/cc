package com.tianee.oa.subsys.bisengin.bean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

/**
 * 视图
 * @author kakalion
 *
 */
@Entity
@Table(name="BIS_VIEW")
public class BisView {
	@Id
	@Column(name="IDENTITY0")
	private String identity;//视图标识
	
	@Column(name="NAME_")
	private String name;//视图名
	
	@Lob
	@Column(name="SQL_")
	private String sql;//SQL语句
	
	@Lob
	@Column(name="COUNT_SQL_")
	private String countSql;//sql的count语句
	
	@Lob
	@Column(name="SELECT_SQL_")
	private String selectSql;
	
	@Lob
	@Column(name="FROM_SQL_")
	private String fromSql;
	
	@Lob
	@Column(name="WHERE_SQL_")
	private String whereSql;
	
	@Lob
	@Column(name="ORDER_BY_SQL_")
	private String orderBySql;
	
	@ManyToOne()
	@JoinColumn(name="DATA_SOURCE_ID")
	@Index(name="BIS_VIEW_DATA_SOURCE_ID")
	private BisDataSource dataSource;
	
	/**
	 * 视图类型  1：设计模式   2：SQL开发模式
	 */
	@Column(name="TYPE_")
	private int type ;
	
	/**
	 * [{t1:表1|1,t2:表2|2,l1:表1字段,l2:表2字段,desc:xxxxxxxxx}]
	 */
	@Lob
	@Column(name="DESIGN_MODEL")
	private String designModel;//设计模型  json形式
	
	/**
	 * 视图项列表
	 */
	@OneToMany(fetch=FetchType.LAZY,mappedBy="bisView",cascade=CascadeType.ALL)
	private List<BisViewListItem> viewListItems = new ArrayList();

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

	public List<BisViewListItem> getViewListItems() {
		return viewListItems;
	}

	public void setViewListItems(List<BisViewListItem> viewListItems) {
		this.viewListItems = viewListItems;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BisDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(BisDataSource dataSource) {
		this.dataSource = dataSource;
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
