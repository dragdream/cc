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
 * 话题回复
 * @author kakalion
 *
 */
@Entity
@Table(name="TOPIC_REPLY")
public class TeeTopicReply {
	@Id
	@GeneratedValue(generator = "system-uuid")  
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(columnDefinition = "char(32)",name="UUID")
	private String uuid;//自增id
	
	@Lob
	@Column(name="CONTENT_")
	private String content;//回复内容
	
	@Column(name="CR_TIME")
	private Calendar crTime;//创建时间
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID")
	@Index(name="TOPIC_REPLY_CR_UID")
	private TeePerson crUser;//创建人
	
	@ManyToOne()
	@JoinColumn(name="TOPIC_ID")
	@Index(name="TOPIC_REPLY_TOPIC_ID")
	private TeeTopic topic;//所属话题
	
	@ManyToOne()
	@JoinColumn(name="SECTION_ID")
	@Index(name="TOPIC_REPLY_SEC_ID")
	private TeeTopicSection section;//所属版块
	
	
	@Column(name="CLICK_ACCOUNT")
	private long clickAccount;//点击数量（预留字段，先不用管）
	
	@Column(name="ANONYMOUS")
	private int anonymous;//是否匿名发布1为匿名0为不匿名
	
	public int getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(int anonymous) {
		this.anonymous = anonymous;
	}

	@Column(name="FLAG_")
	private int flag;//回复状态   0：已屏蔽  1：有效

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public TeeTopic getTopic() {
		return topic;
	}

	public void setTopic(TeeTopic topic) {
		this.topic = topic;
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

	public TeeTopicSection getSection() {
		return section;
	}

	public void setSection(TeeTopicSection section) {
		this.section = section;
	}
	
	
	
}
