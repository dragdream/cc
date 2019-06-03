package com.tianee.oa.core.base.news.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;

/**
 * 
 * @author zhp
 * @createTime 2014-1-5
 * @desc
 */
public class TeeNewsModel {


	private int sid ; // 自增ID
	private String subject = null;// 新闻标题
	private String content = null;// 新闻内容
	private String shortContent = null;// 新闻简短内容，无HTML格式
	private String abstracts = null;//摘要
	private String provider1 = null;// 发布者
	private String fromDeptName = null;// 发布者
	private Date newsTime = null;// 发布时间
	private String newsTimeStr = "";
	private int clickCount = 0;// 点击数
	private String attachmentId = null;// 附件ID串(逗号分隔)
	private String attachmentName = null;// 附件名称串
	private String anonymityYn = null;// 是否允许匿名评论
	private String format = null;// 新闻格式
	private String typeId = null;// 新闻类型
	private String typeDesc = "";//新闻类型描述，系统代码
	private String toRolesIds;//发布部门的id串
	private String toRolesNames;//发布部门的名字
	private String toDeptIds;//发布部门的id串
	private String toDeptNames;//发布部门的id串
	private String toUserNames;//发布人的名字
	private String toUserIds;//发布人的id串
	private byte[] compressContent = null;// 压缩后的公告通知内容
	private String publish = null;// 发布标识
	private List<Map> attachmentsMode;
	private List<TeeAttachmentModel> attachmentsModel = new ArrayList<TeeAttachmentModel>();
	private long attachmentCount = 0;//附件数量
	private int readType = 0;//0-未读 1-已读
	private String top = null;// 是否置顶
	private String thumbnail;//缩略图
	private String url = "";//超链接地址
	
	private int allPriv;//是否是全发布权限
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getProvider1() {
		return provider1;
	}
	public void setProvider1(String provider1) {
		this.provider1 = provider1;
	}
	public Date getNewsTime() {
		return newsTime;
	}
	public void setNewsTime(Date newsTime) {
		this.newsTime = newsTime;
	}
	public int getClickCount() {
		return clickCount;
	}
	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}
	public String getAttachmentId() {
		return attachmentId;
	}
	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}
	public String getAttachmentName() {
		return attachmentName;
	}
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}
	public String getAnonymityYn() {
		return anonymityYn;
	}
	public void setAnonymityYn(String anonymityYn) {
		this.anonymityYn = anonymityYn;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getToRolesIds() {
		return toRolesIds;
	}
	public void setToRolesIds(String toRolesIds) {
		this.toRolesIds = toRolesIds;
	}
	public String getToRolesNames() {
		return toRolesNames;
	}
	public void setToRolesNames(String toRolesNames) {
		this.toRolesNames = toRolesNames;
	}
	public String getToDeptIds() {
		return toDeptIds;
	}
	public void setToDeptIds(String toDeptIds) {
		this.toDeptIds = toDeptIds;
	}
	public String getToDeptNames() {
		return toDeptNames;
	}
	public void setToDeptNames(String toDeptNames) {
		this.toDeptNames = toDeptNames;
	}
	public String getToUserNames() {
		return toUserNames;
	}
	public void setToUserNames(String toUserNames) {
		this.toUserNames = toUserNames;
	}
	public String getToUserIds() {
		return toUserIds;
	}
	public void setToUserIds(String toUserIds) {
		this.toUserIds = toUserIds;
	}
	public byte[] getCompressContent() {
		return compressContent;
	}
	public void setCompressContent(byte[] compressContent) {
		this.compressContent = compressContent;
	}
	public String getTop() {
		return top;
	}
	public void setTop(String top) {
		this.top = top;
	}
	public List<Map> getAttachmentsMode() {
		return attachmentsMode;
	}
	public void setAttachmentsMode(List<Map> attachmentsMode) {
		this.attachmentsMode = attachmentsMode;
	}
	public String getPublish() {
		return publish;
	}
	public void setPublish(String publish) {
		this.publish = publish;
	}
	public String getTypeDesc() {
		return typeDesc;
	}
	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}
	public String getNewsTimeStr() {
		return newsTimeStr;
	}
	public void setNewsTimeStr(String newsTimeStr) {
		this.newsTimeStr = newsTimeStr;
	}
	public List<TeeAttachmentModel> getAttachmentsModel() {
		return attachmentsModel;
	}
	public void setAttachmentsModel(List<TeeAttachmentModel> attachmentsModel) {
		this.attachmentsModel = attachmentsModel;
	}
	public String getFromDeptName() {
		return fromDeptName;
	}
	public void setFromDeptName(String fromDeptName) {
		this.fromDeptName = fromDeptName;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public long getAttachmentCount() {
		return attachmentCount;
	}
	public void setAttachmentCount(long attachmentCount) {
		this.attachmentCount = attachmentCount;
	}
	public int getReadType() {
		return readType;
	}
	public void setReadType(int readType) {
		this.readType = readType;
	}
	public String getShortContent() {
		return shortContent;
	}
	public void setShortContent(String shortContent) {
		this.shortContent = shortContent;
	}
	public String getAbstracts() {
		return abstracts;
	}
	public void setAbstracts(String abstracts) {
		this.abstracts = abstracts;
	}
	public int getAllPriv() {
		return allPriv;
	}
	public void setAllPriv(int allPriv) {
		this.allPriv = allPriv;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
