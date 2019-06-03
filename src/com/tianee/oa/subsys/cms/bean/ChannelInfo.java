package com.tianee.oa.subsys.cms.bean;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="CMS_CHANNEL_INFO")
public class ChannelInfo {
	@Id
	@Column(name="SID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CMS_CHANNEL_INFO_seq_gen")
	@SequenceGenerator(name="CMS_CHANNEL_INFO_seq_gen", sequenceName="CMS_CHANNEL_INFO_seq")
	private int sid;
	
	@Column(name="SITE_ID")
	private int siteId;//站点ID
	
	@Column(name="SORT_NO")
	private int sortNo;//排序号
	
	@Column(name="CHNL_IDENTITY")
	private String chnlIdentity;//栏目标识
	
	@Column(name="CHNL_NAME")
	private String chnlName;//显示名称
	
	@Column(name="PATH")
	private String path;//栏目层级路径
	
	@Column(name="PARENT_CHNL")
	private int parentChnl;//父栏目ID
	
	@Column(name="FOLDER")
	private String folder;//存放位置
	
	@Column(name="INDEX_TPL")
	private int indexTpl;//首页模板ID
	
	@Column(name="DETAIL_TPL")
	private int detailTpl;//细览模板ID
	
	@Column(name="STATUS")
	private int status;//1：允许发布 2：禁止发布
	
	@Column(name="DEL_FLAG")
	private int delFlag;//1：回收站  0：正常
	
	@Column(name="CR_USERID")
	private int createUserId;//创建人id
	
	@Column(name="CR_USERNAME")
	private String createUserName;//创建人姓名
	
	@Column(name="CR_TIME")
	private Calendar createTime;//创建时间

	@Column(name="PAGE_SIZE")
	private int pageSize;//分页大小

	@Column(name="CHECK_PUB")
	private int checkPub;//是否审核发布  1是  0否
	
	@Column(name="CHECK_USER_ID")
	private int checkUserId;//审核人主键
	
	@Column(name="CHECK_USER_NAME")
	private String checkUserName;//审核 人姓名
	
	@Column(name="PRIV_USER_IDS")
	private String privUserIds;//发布权限人员id
	
	@Column(name="PRIV_USER_NAMES")
	private String privUserNames;//发布权限人员姓名
	
	@Column(name="HTML_CONTENT",updatable=false)
	private String htmlContent;//栏目内容
	
	@Column(name="TYPE_ID")
	private String typeId;//公告类型
	
	public ChannelInfo(){}
	
	public ChannelInfo(int sid,String chnlName){
		this.sid = sid;
		this.chnlName = chnlName;
	}
	
	public int getCheckPub() {
		return checkPub;
	}

	public void setCheckPub(int checkPub) {
		this.checkPub = checkPub;
	}

	public int getCheckUserId() {
		return checkUserId;
	}

	public void setCheckUserId(int checkUserId) {
		this.checkUserId = checkUserId;
	}

	public String getCheckUserName() {
		return checkUserName;
	}

	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}

	public String getPrivUserIds() {
		return privUserIds;
	}

	public void setPrivUserIds(String privUserIds) {
		this.privUserIds = privUserIds;
	}

	public String getPrivUserNames() {
		return privUserNames;
	}

	public void setPrivUserNames(String privUserNames) {
		this.privUserNames = privUserNames;
	}

	
	
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getSortNo() {
		return sortNo;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	public String getChnlIdentity() {
		return chnlIdentity;
	}

	public void setChnlIdentity(String chnlIdentity) {
		this.chnlIdentity = chnlIdentity;
	}

	public String getChnlName() {
		return chnlName;
	}

	public void setChnlName(String chnlName) {
		this.chnlName = chnlName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getParentChnl() {
		return parentChnl;
	}

	public void setParentChnl(int parentChnl) {
		this.parentChnl = parentChnl;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	
}
