package com.tianee.oa.subsys.cms.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class SiteInfoModel {
	private int sid;
	private String siteIdentity;//站点标识
	private String siteName;//站点名称
	private int sortNo;//排序号
	private String folder;//存放位置
	private int indexTpl;//首页模版，关联模版id
	private int detailTpl;//细览模版
	private int pubStatus;//发布状态
	private int createUser;//创建人id
	private String createUserName;//创建人名称
	private String pubPath;//发布路径
	private String crTimeDesc;//创建时间
	private String contextPath;//上下文路径
	private String pubFileExt;//发布页面后缀名   html  jsp  php  aspx
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getSiteIdentity() {
		return siteIdentity;
	}
	public void setSiteIdentity(String siteIdentity) {
		this.siteIdentity = siteIdentity;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public int getSortNo() {
		return sortNo;
	}
	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}
	public String getFolder() {
		return folder;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
	public int getIndexTpl() {
		return indexTpl;
	}
	public void setIndexTpl(int indexTpl) {
		this.indexTpl = indexTpl;
	}
	public int getDetailTpl() {
		return detailTpl;
	}
	public void setDetailTpl(int detailTpl) {
		this.detailTpl = detailTpl;
	}
	public int getPubStatus() {
		return pubStatus;
	}
	public void setPubStatus(int pubStatus) {
		this.pubStatus = pubStatus;
	}
	public int getCreateUser() {
		return createUser;
	}
	public void setCreateUser(int createUser) {
		this.createUser = createUser;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getCrTimeDesc() {
		return crTimeDesc;
	}
	public void setCrTimeDesc(String crTimeDesc) {
		this.crTimeDesc = crTimeDesc;
	}
	public String getPubPath() {
		return pubPath;
	}
	public void setPubPath(String pubPath) {
		this.pubPath = pubPath;
	}
	public String getPubFileExt() {
		return pubFileExt;
	}
	public void setPubFileExt(String pubFileExt) {
		this.pubFileExt = pubFileExt;
	}
	public String getContextPath() {
		return contextPath;
	}
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	
	
}
