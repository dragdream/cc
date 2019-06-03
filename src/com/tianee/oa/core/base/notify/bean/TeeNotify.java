package com.tianee.oa.core.base.notify.bean;
import org.hibernate.annotations.Index;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Index;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;

@Entity
@Table(name = "Notify")
public class TeeNotify {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="Notify_seq_gen")
	@SequenceGenerator(name="Notify_seq_gen", sequenceName="Notify_seq")
	@Column(name = "SID")
	private int sid ; // 自增ID

	@Column(name = "FROM_DEPT", nullable = true)
	private int fromDept;// 发布部门ID

	@ManyToOne
	@Index(name="IDX16c62fb673d841e0846be0bb1a6")
	private TeePerson fromPerson ;// 发布用户ID
	
	@Column(name = "SUBJECT", nullable = true, length = 400)
	private String subject;// 公告标题

	@Lob
	@Column(name = "CONTENT", nullable = true)
	private String content;// 公告通知内容
	
	@Lob
	@Column(name = "SHORT_CONTENT", nullable = true)
	private String shortContent = null;// 新闻简短内容，无HTML格式

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SEND_TIME", nullable = true)
	private Date sendTime;// 发布时间

	@Temporal(TemporalType.DATE)
	@Column(name = "BEGIN_DATE", nullable = true)
	private Date beginDate;// 开始日期(有效期)

	@Temporal(TemporalType.DATE)
	@Column(name = "END_DATE", nullable = true)
	private Date endDate;// 结束日期(有效期)

	@OneToMany(mappedBy="notify")//这是双向的 要写JoinColumn 对面要写 mappedBy  ,在对方的
	private List<TeeNotifyInfo> infos = new ArrayList<TeeNotifyInfo>();// 阅读人员用户ID串

	/*private List<TeeNotifyInfo> infos = new ArrayList<TeeNotifyInfo>();// 阅读人员用户ID串
*/	
	@Column(name = "PRINT_", nullable = true)
	private String print;// 是否允许打印office附件


	@ManyToMany(targetEntity=TeeDepartment.class,   //多对多     
			fetch = FetchType.LAZY  ) 
			@JoinTable( name="NOTIFY_DEPT_PRIV",        
			joinColumns={@JoinColumn(name="NOTIFY_ID")},       
			inverseJoinColumns={@JoinColumn(name="DEPT_UUID")}  ) 	
	@Index(name="NOTIFY_DEPT_PRIV_INDEX")
	private List<TeeDepartment> postDept;//发布权限 -部门
	
	
	@ManyToMany(targetEntity=TeePerson.class,   //多对多     
			fetch = FetchType.LAZY  ) 
			@JoinTable( name="NOTIFY_PERSON_PRIV",        
			joinColumns={@JoinColumn(name="NOTIFY_ID")},       
			inverseJoinColumns={@JoinColumn(name="USER_UUID")}  ) 	
	@Index(name="NOTIFY_PERSON_PRIV_INDEX")
	private List<TeePerson> postUser;//发布权限--人员
	
	
	@ManyToMany(targetEntity=TeeUserRole.class,   //多对多     
			fetch = FetchType.LAZY  ) 
			@JoinTable( name="NOTIFY_USER_ROLE_PRIV",        
			joinColumns={@JoinColumn(name="NOTIFY_ID")},       
			inverseJoinColumns={@JoinColumn(name="ROLE_UUID")}  ) 	
	@Index(name="NOTIFY_USER_ROLE_PRIV_INDEX")
	private List<TeeUserRole> postUserRole;//发布权限--角色
	

	
	@Column(name = "TYPE_ID", nullable = true, length = 200)
	private String typeId;// 公告类型

	@Column(name = "IS_TOP", columnDefinition="char(1) default 0")
	private String top;// 是否置顶  0-不置顶 1-置顶

	@Column(name = "TOP_DAYS", nullable = true, length = 4)
	private int topDays;// 置顶天数

	@Column(name = "FORMAT", nullable = true, length = 20)
	private String format;// 公告通知格式
	//0-未发布
	//1-已发布（状态列显示为“生效”）
	//2-待审批
	//3-未通过
	@Column(name = "PUBLISH",columnDefinition="char(1) default 0")
	private String publish;// 发布标识
	
	@ManyToOne
	@Index(name="IDXc8685be2d60c4d64a76ad7a8d12")
	private TeePerson auditer;// 审核人用户ID

	@Lob
	@Column(name = "REASON", nullable = true)
	private String reason;// 审核人不同意的原因

	@Lob
	@Column(name = "AUDIT_DATE", nullable = true)
	private Date auditDate ;// 通过/不通过的日期

	@Lob
	@Column(name = "COMPRESS_CONTENT", nullable = true)
	private byte[] compressContent ;// 压缩后的公告通知内容

	@Column(name = "DOWNLOAD", columnDefinition="char(1) default 0")
	private String download;// 是否允许下载office附件

	@Column(name = "SUBJECT_FONT", nullable = true)
	private String subjectFont;
	
	@Column(name="ALL_PRIV")
	private int allPriv;//是否是全发布权限
	
	
	@Column(name="create_time")
	private Date createDate;//创建时间
	
	@Column(name="DOCCONTENT_SID")
	private int docContentSid;//电子文档内容附件id
	
	@Column(name="CONTENT_STYLE", columnDefinition="char(1) default 0")
	private String contentStyle;//内容填写方式  0为html 1为电子文档
	
	@Column(name="SYNCHRONIZE_CMS", columnDefinition="char(1) default 0")
	private String synchronizeCMS;//是否同步到cms网站  0为否 1为是
	
	@Column(name="IS_ADD_FLAG", columnDefinition="char(1) default 0")
	private String isAddFlag;//判断cms文档是否已经创建
	
	@Column(name="CMS_DOCUMENT_ID")
	private int cmsDocumentId;//cms网站文档id
	


	public String getIsAddFlag() {
		return isAddFlag;
	}

	public void setIsAddFlag(String isAddFlag) {
		this.isAddFlag = isAddFlag;
	}

	public int getCmsDocumentId() {
		return cmsDocumentId;
	}

	public void setCmsDocumentId(int cmsDocumentId) {
		this.cmsDocumentId = cmsDocumentId;
	}

	public String getSynchronizeCMS() {
		return synchronizeCMS;
	}

	public void setSynchronizeCMS(String synchronizeCMS) {
		this.synchronizeCMS = synchronizeCMS;
	}

	public String getContentStyle() {
		return contentStyle;
	}

	public void setContentStyle(String contentStyle) {
		this.contentStyle = contentStyle;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public TeePerson getFromPerson() {
		return fromPerson;
	}

	public void setFromPerson(TeePerson fromPerson) {
		this.fromPerson = fromPerson;
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

	public List<TeeNotifyInfo> getInfos() {
		return infos;
	}

	public void setInfos(List<TeeNotifyInfo> infos) {
		this.infos = infos;
	}

	public String getPrint() {
		return print;
	}

	public void setPrint(String print) {
		this.print = print;
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

	public TeePerson getAuditer() {
		return auditer;
	}

	public void setAuditer(TeePerson auditer) {
		this.auditer = auditer;
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

	public List<TeeDepartment> getPostDept() {
		return postDept;
	}

	public void setPostDept(List<TeeDepartment> postDept) {
		this.postDept = postDept;
	}

	public List<TeePerson> getPostUser() {
		return postUser;
	}

	public void setPostUser(List<TeePerson> postUser) {
		this.postUser = postUser;
	}

	public List<TeeUserRole> getPostUserRole() {
		return postUserRole;
	}

	public void setPostUserRole(List<TeeUserRole> postUserRole) {
		this.postUserRole = postUserRole;
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

	public int getDocContentSid() {
		return docContentSid;
	}

	public void setDocContentSid(int docContentSid) {
		this.docContentSid = docContentSid;
	}

}
