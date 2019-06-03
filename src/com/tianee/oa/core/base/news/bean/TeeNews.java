package com.tianee.oa.core.base.news.bean;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;

/**
 * 新闻实体类
 * 
 * @author zhp
 * @createTime 2013-12-26
 * @desc
 */
@Entity
@Table(name="News")
public class TeeNews {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="News_seq_gen")
	@SequenceGenerator(name="News_seq_gen", sequenceName="News_seq")
	@Column(name = "SID")
	private int sid ; // 自增ID
	
	@Column(name = "SUBJECT", nullable = true, length = 200)
	private String subject = null;// 新闻标题
	
	@Lob
	@Column(name = "CONTENT", nullable = true)
	private String content = null;// 新闻内容
	
	@Lob
	@Column(name = "SHORT_CONTENT", nullable = true)
	private String shortContent = null;// 新闻简短内容，无HTML格式
	
	@Column(name = "ABSTRACTS_", nullable = true)
	private String abstracts = null;//摘要
	
	@OneToOne
	private TeePerson provider = null;// 发布者
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "NEWS_TIME", nullable = true)
	private Date newsTime = null;// 发布时间
	
	@Column(name = "CLICK_COUNT", nullable = true)
	private int clickCount = 0;// 点击数
	
	@Column(name = "ANONYMITY_YN", nullable = true, length = 20)
	private String anonymityYn = "0";// 是否允许匿名评论 //0 实名 1 匿名 2 进制评论
	
	@Column(name = "FORMAT", nullable = true, length = 20)
	private String format = "1";// 新闻格式   1：普通格式   2：超链接
	
	@Column(name="URL_")
	private String url = "";//超链接地址
	
	@Column(name = "TYPE_ID", nullable = true, length = 200)
	private String typeId = null;// 新闻类型
	
	@Column(name = "PUBLISH", nullable = true, length = 20)
	private String publish = null;// 发布标识 0 为发布 1 已发布 2 终止
	
	@Lob
	@Column(name = "COMPRESS_CONTENT", nullable = true)
	private byte[] compressContent = null;// 压缩后的公告通知内容
	
	@Column(name = "IS_TOP",columnDefinition="char(1) default 0", nullable = true)
	private String top = null;// 是否置顶
	
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinTable(name="NEWS_INFO",         
	joinColumns={@JoinColumn(name="NEWS_ID")},       
	inverseJoinColumns={@JoinColumn(name="INFO_ID")}  ) 
	private List<TeeNewsInfo> infos = new ArrayList<TeeNewsInfo>();// 阅读人员用户ID串
	
	@Column(name = "THUMBNAIL")
	private String thumbnail;//缩略图路径
	
	@ManyToMany(targetEntity=TeeDepartment.class,   //多对多     
			fetch = FetchType.LAZY  ) 
			@JoinTable( name="NEWS_DEPT_PRIV",        
			joinColumns={@JoinColumn(name="NEWS_ID")},       
			inverseJoinColumns={@JoinColumn(name="DEPT_UUID")}  ) 	
	@Index(name="NEWS_DEPT_PRIV_INDEX")
	private List<TeeDepartment> postDept;//发布权限 -部门
	
	
	@ManyToMany(targetEntity=TeePerson.class,   //多对多     
			fetch = FetchType.LAZY  ) 
			@JoinTable( name="NEWS_PERSON_PRIV",        
			joinColumns={@JoinColumn(name="NEWS_ID")},       
			inverseJoinColumns={@JoinColumn(name="USER_UUID")}  ) 	
	@Index(name="NEWS_PERSON_PRIV_INDEX")
	private List<TeePerson> postUser;//发布权限--人员
	
	
	@ManyToMany(targetEntity=TeeUserRole.class,   //多对多     
			fetch = FetchType.LAZY  ) 
			@JoinTable( name="NEWS_USER_ROLE_PRIV",        
			joinColumns={@JoinColumn(name="NEWS_ID")},       
			inverseJoinColumns={@JoinColumn(name="ROLE_UUID")}  ) 	
	@Index(name="NEWS_USER_ROLE_PRIV_INDEX")
	private List<TeeUserRole> postUserRole;//发布权限--角色
	
	@Column(name="ALL_PRIV")
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

	public TeePerson getProvider() {
		return provider;
	}

	public void setProvider(TeePerson provider) {
		this.provider = provider;
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

	public String getPublish() {
		return publish;
	}

	public void setPublish(String publish) {
		this.publish = publish;
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

	public List<TeeNewsInfo> getInfos() {
		return infos;
	}

	public void setInfos(List<TeeNewsInfo> infos) {
		this.infos = infos;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
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
