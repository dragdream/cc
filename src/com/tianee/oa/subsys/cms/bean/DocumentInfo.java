package com.tianee.oa.subsys.cms.bean;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="CMS_DOCUMENT_INFO")
public class DocumentInfo {
	@Id
	@Column(name="SID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CMS_DOCUMENT_INFO_seq_gen")
	@SequenceGenerator(name="CMS_DOCUMENT_INFO_seq_gen", sequenceName="CMS_DOCUMENT_INFO_seq")
	private int sid;
	
	@Column(name="DOC_TITLE")
	private String docTitle;
	
	@Column(name="CR_TIME")
	private Calendar crTime;//创建时间
	
	@Column(name="PUB_TIME")
	private Calendar pubTime;//发布时间
	
	@Column(name="WRITE_TIME")
	private Calendar writeTime;//撰写时间
	
	@Column(name="CR_USERID")
	private int crUserId;
	
	@Column(name="CR_USERNAME")
	private String crUserName;
	
	@Column(name="CHNL_ID")
	private int chnlId;
	
	@Column(name="SITE_ID")
	private int siteId;
	
	@Column(name="STATUS")
	private int status;//1、新稿  2、待审 3、已审 4、已发  5、已拒
	
	@Column(name="MAIN_TITLE")
	private String mainTitle;//主标题
	
	@Column(name="SUB_TITLE")
	private String subTitle;//子标题
	
	@Column(name="KEY_WORDS")
	private String keyWords;//关键词
	
	@Column(name="ABSTRACTS")
	private String abstracts;//摘要
	
	@Column(name="SOURCE")
	private String source;//来源
	
	@Column(name="AUTHOR")
	private String author;//作者
	
	@Lob
	@Column(name="HTML_CONTENT")
	private String htmlContent;//html内容
	
	@Lob
	@Column(name="CONTENT")
	private String content;//内容
	
	@Column(name="THUMBNAIL_")
	private int thumbnail;//缩略图附件ID
	
	@Column(name="CATEGORY_")
	private int category;//自定义分类
	
	@Column(name="PATH_")
	private String path;
	
	@Column(name="SORT_NO")
	private int sortNo;
	
	@Column(name="TOP_")
	private int top;
	
	@Column(name="DEL_FLAG")
	private int delFlag;
	
	@Column(name="DEPT_ID")
	private int deptId;
	
	@Column(name="DEPT_NAME")
	private String deptName;
	
	@Column(name="READ_TIME")
	private int readTime;
	
	@Column(name="DOC_ATTACHMENT_ID")
	private int docAttachmentId;
	
	public DocumentInfo(){}
	
	
	public int getDocAttachmentId() {
		return docAttachmentId;
	}


	public void setDocAttachmentId(int docAttachmentId) {
		this.docAttachmentId = docAttachmentId;
	}


	public DocumentInfo(int sid,String docTitle){
		this.sid = sid;
		this.docTitle = docTitle;
	}
	
	
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getDocTitle() {
		return docTitle;
	}

	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}

	public Calendar getCrTime() {
		return crTime;
	}

	public void setCrTime(Calendar crTime) {
		this.crTime = crTime;
	}

	public Calendar getPubTime() {
		return pubTime;
	}

	public void setPubTime(Calendar pubTime) {
		this.pubTime = pubTime;
	}

	public Calendar getWriteTime() {
		return writeTime;
	}

	public void setWriteTime(Calendar writeTime) {
		this.writeTime = writeTime;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getSortNo() {
		return sortNo;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public int getReadTime() {
		return readTime;
	}

	public void setReadTime(int readTime) {
		this.readTime = readTime;
	}
	
}
