package com.tianee.webframe.util.db;

/**
 * 数据源对象
 * @author kakalion
 *
 */
public class TeeDataSource {
	
	private String url;
	private String userName;
	private String passWord;
	private String driverClass;
	private String configModel = "";
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getDriverClass() {
		return driverClass;
	}
	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}
	public String getConfigModel() {
		return configModel;
	}
	public void setConfigModel(String configModel) {
		this.configModel = configModel;
	}
	
	
}
