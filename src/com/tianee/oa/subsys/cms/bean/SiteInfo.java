package com.tianee.oa.subsys.cms.bean;
import org.hibernate.annotations.Index;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="CMS_SITE_INFO")
public class SiteInfo{
	@Id
	@Column(name="SID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CMS_SITE_INFO_seq_gen")
	@SequenceGenerator(name="CMS_SITE_INFO_seq_gen", sequenceName="CMS_SITE_INFO_seq")
	private int sid;
	
	@Column(name="SITE_IDENTITY")
	private String siteIdentity;//站点标识
	
	@Column(name="SITE_NAME")
	private String siteName;//站点名称
	
	@Column(name="SORT_NO")
	private int sortNo;//排序号
	
	@Column(name="FOLDER")
	private String folder;//存放位置
	
	@Column(name="INDEX_TPL")
	private int indexTpl;//首页模版，关联模版id
	
	@Column(name="DETAIL_TPL")
	private int detailTpl;//细览模版
	
	@Column(name="PUB_STATUS")
	private int pubStatus;//发布状态
	
	@Column(name="CR_USERID")
	private int createUser;//创建人id
	
	@Column(name="CR_USERNAME")
	private String createUserName;//创建人名称
	
	@Column(name="CR_TIME")
	private Calendar crTime;//创建时间
	
	@Column(name="PUB_PATH")
	private String pubPath;//发布路径
	
	@Column(name="CONTEXT_PATH")
	private String contextPath;//上下文路径
	
	@Column(name="PUB_FILE_EXT")
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

	public Calendar getCrTime() {
		return crTime;
	}

	public void setCrTime(Calendar crTime) {
		this.crTime = crTime;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
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
