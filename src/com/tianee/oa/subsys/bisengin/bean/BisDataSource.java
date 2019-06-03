package com.tianee.oa.subsys.bisengin.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 数据源
 * @author kakalion
 *
 */
@Entity
@Table(name="BIS_DATA_SOURCE")
public class BisDataSource {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="BIS_DATA_SOURCE_SEQ_GEN")
	@SequenceGenerator(name="BIS_DATA_SOURCE_SEQ_GEN", sequenceName="BIS_DATA_SOURCE_SEQ")
	@Column(name="SID")
	private int sid;
	
	@Column(name="DS_NAME")
	private String dsName;//数据源名称
	
	/**
	 * 所属数据库类型
	 * MYSQL(本地支持)
	 * SQLSERVER
	 * ORACLE
	 * DAMENG
	 * KINGBASE
	 */
	@Column(name="DB_TYPE")
	private String dbType;
	
	/**
	 * 数据源类型
	 * 1、内部数据源
	 * 2、外部数据源
	 */
	@Column(name="DATA_SOURCE")
	private int dataSource;
	
	/**
	 * 连接驱动类
	 */
	@Column(name="DRIVER_CLASS")
	private String driverClass;
	
	/**
	 * 驱动连接字符串
	 */
	@Column(name="DRIVER_URL")
	private String driverUrl;
	
	/**
	 * 驱动连接用户名
	 */
	@Column(name="DRIVER_USERNAME")
	private String driverUsername;
	
	/**
	 * 驱动连接密码
	 */
	@Column(name="DRIVER_PWD")
	private String driverPwd;
	
	/**
	 * 配置模型
	 */
	@Lob
	@Column(name="CONFIG_MODEL")
	private String configModel;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
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

	public String getDsName() {
		return dsName;
	}

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}

	public String getConfigModel() {
		return configModel;
	}

	public void setConfigModel(String configModel) {
		this.configModel = configModel;
	}
	
}
