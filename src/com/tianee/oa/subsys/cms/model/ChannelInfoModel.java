package com.tianee.oa.subsys.cms.model;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;

public class ChannelInfoModel {
	private int sid;
	
	private int siteId;//站点ID
	
	private int sortNo;//排序号
	
	private String chnlIdentity;//栏目标识
	
	private String chnlName;//显示名称
	
	private String path;//栏目层级路径
	
	private int parentChnl;//父栏目ID
	
	private String folder;//存放位置
	
	private int indexTpl;//首页模板ID
	
	private int detailTpl;//细览模板ID
	
	private int status;//1：允许发布 2：禁止发布
	
	private int delFlag;//1：回收站  0：正常
	
	private int createUserId;//创建人id
	
	private String createUserName;//创建人姓名
	
	private String createTimeDesc;//创建时间

	private List<TeeAttachmentModel> attachMentModel;
	
	private int pageSize;//分页大小
	
	private int checkPub;//是否审核发布  1是  0否
	
	private int checkUserId;//审核人主键
	
	private String checkUserName;//审核 人姓名
	
	private String privUserIds;//发布权限人员id
	
	private String htmlContent;//栏目内容
	
	private String typeId;//公告类型
	
	private Map<String,String> ext = new HashMap();
	
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

	private String privUserNames;//发布权限人员姓名
	
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

	public String getCreateTimeDesc() {
		return createTimeDesc;
	}

	public void setCreateTimeDesc(String createTimeDesc) {
		this.createTimeDesc = createTimeDesc;
	}

	public List<TeeAttachmentModel> getAttachMentModel() {
		return attachMentModel;
	}

	public void setAttachMentModel(List<TeeAttachmentModel> attachMentModel) {
		this.attachMentModel = attachMentModel;
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

	public Map<String, String> getExt() {
		return ext;
	}

	public void setExt(Map<String, String> ext) {
		this.ext = ext;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	
}
