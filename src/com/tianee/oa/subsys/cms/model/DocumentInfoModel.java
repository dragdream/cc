package com.tianee.oa.subsys.cms.model;

import java.util.List;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;

public class DocumentInfoModel {
	private int chnlDocId;
	private int docId;
	private String docTitle;
	private String crTimeDesc;//创建时间
	private String pubTimeDesc;//发布时间
	private String writeTimeDesc;//撰写时间
	private int crUserId;
	private String crUserName;
	private int chnlId;
	private int siteId;
	private int status;//1、新稿 2、已编 3、返工 4、已否 5、已签 6、正审
	private String mainTitle;//主标题
	private String subTitle;//子标题
	private String keyWords;//关键词
	private String abstracts;//摘要
	private String source;//来源
	private String author;//作者
	private String htmlContent;//html内容
	private String content;//内容
	private int category;//自定义分类
	private int top;//置顶  0：不置顶  1：置顶  2：限时置顶
	private int thumbnail;//缩略图附件ID
	private int docAttachmentId;//文档内容附件ID
	private String typeId;//公告类型
	
	
	private List<TeeAttachmentModel> attachMentModel;
	
	private TeeAttachmentModel docAttachmentModel;
	
	
	
	public TeeAttachmentModel getDocAttachmentModel() {
		return docAttachmentModel;
	}
	public void setDocAttachmentModel(TeeAttachmentModel docAttachmentModel) {
		this.docAttachmentModel = docAttachmentModel;
	}
	public int getDocAttachmentId() {
		return docAttachmentId;
	}
	public void setDocAttachmentId(int docAttachmentId) {
		this.docAttachmentId = docAttachmentId;
	}
	public int getChnlDocId() {
		return chnlDocId;
	}
	public void setChnlDocId(int chnlDocId) {
		this.chnlDocId = chnlDocId;
	}
	public int getDocId() {
		return docId;
	}
	public void setDocId(int docId) {
		this.docId = docId;
	}
	public String getDocTitle() {
		return docTitle;
	}
	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}
	public String getCrTimeDesc() {
		return crTimeDesc;
	}
	public void setCrTimeDesc(String crTimeDesc) {
		this.crTimeDesc = crTimeDesc;
	}
	public String getPubTimeDesc() {
		return pubTimeDesc;
	}
	public void setPubTimeDesc(String pubTimeDesc) {
		this.pubTimeDesc = pubTimeDesc;
	}
	public String getWriteTimeDesc() {
		return writeTimeDesc;
	}
	public void setWriteTimeDesc(String writeTimeDesc) {
		this.writeTimeDesc = writeTimeDesc;
	}
	public int getCrUserId() {
		return crUserId;
	}
	public void setCrUserId(int crUserId) {
		this.crUserId = crUserId;
	}
	public String getCrUserName() {
		return crUserName;
	}
	public void setCrUserName(String crUserName) {
		this.crUserName = crUserName;
	}
	public int getChnlId() {
		return chnlId;
	}
	public void setChnlId(int chnlId) {
		this.chnlId = chnlId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMainTitle() {
		return mainTitle;
	}
	public void setMainTitle(String mainTitle) {
		this.mainTitle = mainTitle;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public String getAbstracts() {
		return abstracts;
	}
	public void setAbstracts(String abstracts) {
		this.abstracts = abstracts;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getHtmlContent() {
		return htmlContent;
	}
	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}
	public List<TeeAttachmentModel> getAttachMentModel() {
		return attachMentModel;
	}
	public void setAttachMentModel(List<TeeAttachmentModel> attachMentModel) {
		this.attachMentModel = attachMentModel;
	}
	public int getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(int thumbnail) {
		this.thumbnail = thumbnail;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public int getSiteId() {
		return siteId;
	}
	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	
	
}
