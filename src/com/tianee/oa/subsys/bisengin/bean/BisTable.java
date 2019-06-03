package com.tianee.oa.subsys.bisengin.bean;
import org.hibernate.annotations.Index;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Index;

import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name="BIS_TABLE")
public class BisTable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="BIS_CONFIG_seq_gen")
	@SequenceGenerator(name="BIS_CONFIG_seq_gen", sequenceName="BIS_CONFIG_seq")
	private int sid;
	
	/**
	 * 业务表名
	 */
	@Column(name="TABLE_NAME")
	private String tableName;
	
	/**
	 * 业务表名描述
	 */
	@Column(name="TABLE_DESC")
	private String tableDesc;
	
	@ManyToOne
	@Index(name="IDXaa5fa5d1c49d4794ac95cf26b0a")
	@JoinColumn(name="BIS_CAT_ID")
	private BisCategory bisCat;
	
	/**
	 * 是否已经生成
	 * 0：未生成
	 * 1：已生成
	 */
	@Column(name="gen")
	private int gen;
	
	/**
	 * 数据源
	 */
	@ManyToOne()
	@JoinColumn(name="BIS_DATA_SOURCE")
	@Index(name="BIS_TABLE_BIS_DATA_SOURCE")
	private BisDataSource bisDataSource;
	
	/**
	 * 实体数据结构类路径
	 */
	@Column(name="ENTITY_CLASS")
	private String entityClass;
	
	/**
	 * 别名
	 */
	@Column(name="ALIAS")
	private String alias;
	
	/**
	 * 排序号
	 */
	@Column(name="SORT_NO")
	private int sortNo;
	
	@Column(name="CREATE_TIME")
	private Calendar createTime;
	
	/**
	 * 创建人ID
	 */
	@Column(name="USER_ID")
	private String userId;
	
	/**
	 * 创建人中文名
	 */
	@Column(name="USER_NAME")
	private String userName;
	
	/**
	 * 创建人uuid
	 */
	@Column(name="USER_UUID")
	private String userUuid;
	
	@Column(name="FLOW_ID")
	private int flowId;
	
	@Lob
	@Column(name="BIS_MODEL")
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

	public void setGen(int gen) {
		this.gen = gen;
	}

	public int getGen() {
		return gen;
	}

	public void setEntityClass(String entityClass) {
		this.entityClass = entityClass;
	}

	public String getEntityClass() {
		return entityClass;
	}

	public void setTableDesc(String tableDesc) {
		this.tableDesc = tableDesc;
	}

	public String getTableDesc() {
		return tableDesc;
	}


	public void setBisCat(BisCategory bisCat) {
		this.bisCat = bisCat;
	}

	public BisCategory getBisCat() {
		return bisCat;
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

	public BisDataSource getBisDataSource() {
		return bisDataSource;
	}

	public void setBisDataSource(BisDataSource bisDataSource) {
		this.bisDataSource = bisDataSource;
	}
	
}
