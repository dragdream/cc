package com.tianee.oa.subsys.topic.bean;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;

import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 讨论区版块
 * 
 * @author kakalion
 * 
 */
@Entity
@Table(name = "TOPIC_SECTION")
public class TeeTopicSection {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(columnDefinition = "char(32)", name = "UUID")
	private String uuid;// 自增id

	@Column(name = "SECTION_NAME")
	private String sectionName;// 版块名称

	@Column(name = "MANAGER_")
	private String manager;// 版主

	@Lob
	@Column(name = "CR_PRIV")
	private String crPriv;// 发帖权限 ALL：全体人员 非ALL（即/1/32/42/23/201/1/）逗号分隔

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "SECTION_VIEWPRIV", joinColumns = { @JoinColumn(name = "SECTION_ID") }, inverseJoinColumns = { @JoinColumn(name = "USER_ID") })
	private Set<TeePerson> viewPriv = new HashSet();// 版块可见人员

	@Column(name = "VIEW_PRIV_FLAG")
	private int viewPrivAllFlag;// 版块可见人员标记 1：全部 0：非全部

	@Column(name = "NEW_SMS_PRIV")
	private int newTopicSmsPriv = 0;// 新发贴时给可见人员发送系统消息
	
	@Column(name="ANONYMOUS")
	private int anonymous;//是否匿名发布1为匿名0为不匿名

	public int getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(int anonymous) {
		this.anonymous = anonymous;
	}

	@Column(name = "REPLY_SMS_PRIV")
	private int replySmsPriv = 0;// 有跟贴时给楼主发送系统消息

	@Column(name = "ORDER_NO_")
	private int orderNo = 0;// 版块排序

	@Column(name = "REMARK_")
	private String remark;// 备注

	// 创建人
	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name = "IDX_TOPIC_SECTION_CREATE_USER")
	@JoinColumn(name = "CREATE_USER")
	private TeePerson createUser;

	// 创建时间
	@Column(name = "CREATE_TIME")
	private Calendar createTime;// 录入时间

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getCrPriv() {
		return crPriv;
	}

	public void setCrPriv(String crPriv) {
		this.crPriv = crPriv;
	}

	public Set<TeePerson> getViewPriv() {
		return viewPriv;
	}

	public void setViewPriv(Set<TeePerson> viewPriv) {
		this.viewPriv = viewPriv;
	}

	public int getNewTopicSmsPriv() {
		return newTopicSmsPriv;
	}

	public void setNewTopicSmsPriv(int newTopicSmsPriv) {
		this.newTopicSmsPriv = newTopicSmsPriv;
	}

	public int getReplySmsPriv() {
		return replySmsPriv;
	}

	public void setReplySmsPriv(int replySmsPriv) {
		this.replySmsPriv = replySmsPriv;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getViewPrivAllFlag() {
		return viewPrivAllFlag;
	}

	public void setViewPrivAllFlag(int viewPrivAllFlag) {
		this.viewPrivAllFlag = viewPrivAllFlag;
	}

	public TeePerson getCreateUser() {
		return createUser;
	}

	public void setCreateUser(TeePerson createUser) {
		this.createUser = createUser;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	
	
	
}
