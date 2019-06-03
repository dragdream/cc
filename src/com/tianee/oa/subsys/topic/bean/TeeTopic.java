package com.tianee.oa.subsys.topic.bean;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;

import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 讨论区话题
 * @author kakalion
 *
 */
@Entity
@Table(name="TOPIC")
public class TeeTopic {
	@Id
	@GeneratedValue(generator = "system-uuid")  
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(columnDefinition = "char(32)",name="UUID")
	private String uuid;//自增id
	
	@Column(name="SUBJECT_")
	private String subject;//主题
	
	@Lob
	@Column(name="CONTENT_")
	private String content;//内容
	
	@Column(name="ANONYMOUS")
	private int anonymous;//是否匿名发布1为匿名0为不匿名
	
	
	public int getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(int anonymous) {
		this.anonymous = anonymous;
	}

	@Column(name="CR_TIME")
	private Calendar crTime;//创建时间
	
	@ManyToOne()
	@JoinColumn(name="SECTION_ID")
	@Index(name="TOPIC_SEC_ID")
	private TeeTopicSection section;//所属版块
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CR_USER")
	private TeePerson crUser;//创建人
	
	@ManyToOne()
	@JoinColumn(name="LAST_REPLY_USER_ID")
	@Index(name="TOPIC_LAST_REPLY_UID")
	private TeePerson lastReplyUser;//最后回复的用户
	
	@Column(name="LAST_REPLY_TIME")
	private Calendar lastReplyTime;//最后回复时间
	
	@Column(name="REPLY_AMOUNT")
	private long replyAmount;//回复数量
	
	@Column(name="IS_TOP")
	private int isTop;//是否置顶  0-否；1-是
	
	@Column(name="CLICK_ACCOUNT")
	private long clickAccount;//点击数量
	
	@Column(name="FLAG_")
	private int flag;//帖子状态   0：已删除至回收站  1：已发布（有效）
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public Calendar getCrTime() {
		return crTime;
	}

	public void setCrTime(Calendar crTime) {
		this.crTime = crTime;
	}

	public TeePerson getCrUser() {
		return crUser;
	}

	public void setCrUser(TeePerson crUser) {
		this.crUser = crUser;
	}

	public int getIsTop() {
		return isTop;
	}

	public void setIsTop(int isTop) {
		this.isTop = isTop;
	}

	public TeeTopicSection getSection() {
		return section;
	}

	public void setSection(TeeTopicSection section) {
		this.section = section;
	}

	public TeePerson getLastReplyUser() {
		return lastReplyUser;
	}

	public void setLastReplyUser(TeePerson lastReplyUser) {
		this.lastReplyUser = lastReplyUser;
	}

	public Calendar getLastReplyTime() {
		return lastReplyTime;
	}

	public void setLastReplyTime(Calendar lastReplyTime) {
		this.lastReplyTime = lastReplyTime;
	}

	public long getReplyAmount() {
		return replyAmount;
	}

	public void setReplyAmount(long replyAmount) {
		this.replyAmount = replyAmount;
	}

	public long getClickAccount() {
		return clickAccount;
	}

	public void setClickAccount(long clickAccount) {
		this.clickAccount = clickAccount;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	
}
