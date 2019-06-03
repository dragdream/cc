package com.tianee.oa.core.base.notify.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.base.notify.bean.TeeNotifyInfo;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;

public class TeeNotifyModel {

	private int sid ; // 自增ID
	private int fromDept;// 发布部门ID
	private String fromDeptName;// 发布部门x
	private String fromPersonName ;// 发布用户ID
	private String subject;// 公告标题
	private String content;// 公告通知内容
	private String shortContent = null;
	private Date sendTime;// 发布时间
	private String sendTimeDesc;// 发布时间
	private Date createDate;//创建时间
	private Date beginDate;// 开始日期(有效期)
	private Date endDate;// 结束日期(有效期)
	private String print;// 是否允许打印office附件
	private String toRolesIds;//发布部门的id串
	private String toRolesNames;//发布部门的名字
	private String toDeptIds;//发布部门的id串
	private String toDeptNames;//发布部门的id串
	private String toUserNames;//发布人的名字
	private String toUserIds;//发布人的id串
	private String typeId;// 公告类型
	private String typeDesc ; //类型描述
	private String top;// 是否置顶
	private int topDays;// 置顶天数
	private String format;// 公告通知格式
	private String publish;// 发布标识
	private String auditerName;// 审核人用户Name
	private String auditerId;// 审核人用户ID
	private String reason;// 审核人不同意的原因
	private Date auditDate ;// 通过/不通过的日期
	private byte[] compressContent ;// 压缩后的公告通知内容
	private String download;// 是否允许下载office附件
	private String subjectFont;
	private List<Map> attachmentsMode;
	private long attachmentCount = 0;//附件数量
	private int readType = 0;//0-未读 1-已读   -1 全部
	private int allPriv;//是否是全发布权限
	private String keywords;// 关键字
	private Map contentMap;//电子文档形式的内容
	private String contentStyle;//内容填写形式
	private String attachmentSidStr;//文档附件ID
	private String synchronizeCMS;//是否同步cms
	
	
	public String getSynchronizeCMS() {
		return synchronizeCMS;
	}
	public void setSynchronizeCMS(String synchronizeCMS) {
		this.synchronizeCMS = synchronizeCMS;
	}
	public String getAttachmentSidStr() {
		return attachmentSidStr;
	}
	public void setAttachmentSidStr(String attachmentSidStr) {
		this.attachmentSidStr = attachmentSidStr;
	}
	public String getContentStyle() {
		return contentStyle;
	}
	public void setContentStyle(String contentStyle) {
		this.contentStyle = contentStyle;
	}
	public Map getContentMap() {
		return contentMap;
	}
	public void setContentMap(Map contentMap) {
		this.contentMap = contentMap;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getFromDept() {
		return fromDept;
	}
	public void setFromDept(int fromDept) {
		this.fromDept = fromDept;
	}
	public String getFromPersonName() {
		return fromPersonName;
	}
	public void setFromPersonName(String fromPersonName) {
		this.fromPersonName = fromPersonName;
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
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getPrint() {
		return print;
	}
	public void setPrint(String print) {
		this.print = print;
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
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getTop() {
		return top;
	}
	public void setTop(String top) {
		this.top = top;
	}
	public int getTopDays() {
		return topDays;
	}
	public void setTopDays(int topDays) {
		this.topDays = topDays;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getPublish() {
		return publish;
	}
	public void setPublish(String publish) {
		this.publish = publish;
	}
	
	public String getAuditerName() {
		return auditerName;
	}
	public void setAuditerName(String auditerName) {
		this.auditerName = auditerName;
	}
	public String getAuditerId() {
		return auditerId;
	}
	public void setAuditerId(String auditerId) {
		this.auditerId = auditerId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Date getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	public byte[] getCompressContent() {
		return compressContent;
	}
	public void setCompressContent(byte[] compressContent) {
		this.compressContent = compressContent;
	}
	public String getDownload() {
		return download;
	}
	public void setDownload(String download) {
		this.download = download;
	}
	public String getSubjectFont() {
		return subjectFont;
	}
	public void setSubjectFont(String subjectFont) {
		this.subjectFont = subjectFont;
	}
	public List<Map> getAttachmentsMode() {
		return attachmentsMode;
	}
	public void setAttachmentsMode(List<Map> attachmentsMode) {
		this.attachmentsMode = attachmentsMode;
	}
	public String getTypeDesc() {
		return typeDesc;
	}
	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}
	public String getFromDeptName() {
		return fromDeptName;
	}
	public void setFromDeptName(String fromDeptName) {
		this.fromDeptName = fromDeptName;
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
	public String getSendTimeDesc() {
		return sendTimeDesc;
	}
	public void setSendTimeDesc(String sendTimeDesc) {
		this.sendTimeDesc = sendTimeDesc;
	}
	public String getShortContent() {
		return shortContent;
	}
	public void setShortContent(String shortContent) {
		this.shortContent = shortContent;
	}
	public int getAllPriv() {
		return allPriv;
	}
	public void setAllPriv(int allPriv) {
		this.allPriv = allPriv;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
}
