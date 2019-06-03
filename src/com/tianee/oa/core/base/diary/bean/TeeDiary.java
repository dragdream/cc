package com.tianee.oa.core.base.diary.bean;
import org.hibernate.annotations.Index;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name="DIARY")
public class TeeDiary implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="DIARY_seq_gen")
	@SequenceGenerator(name="DIARY_seq_gen", sequenceName="DIARY_seq")
	@Column(name="SID")
	private int sid;
	
	@Column(name="TITLE")
	private String title;
	
	@Column(name="CONTENT")
	@Lob()
	private String content;
	
	/**
	 * 日志类型，1：个人日志   2、工作日志
	 */
	@Column(name="TYPE")
	private int type = 1;
	
	@Column(name="WRITE_TIME")
	private Calendar writeTime;//撰写日期
	
	//创建时间
	@Column(name="CREATE_TIME")
	private Calendar createTime;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDX7d65df4cf9bd4115aa7dc7536e2")
	@JoinColumn(name="CREATE_USER")
	private TeePerson createUser;
	
	@ManyToMany(fetch=FetchType.LAZY)
	private Set<TeePerson> shareRanges = new HashSet<TeePerson>(0);

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public TeePerson getCreateUser() {
		return createUser;
	}

	public void setCreateUser(TeePerson createUser) {
		this.createUser = createUser;
	}

	public Set<TeePerson> getShareRanges() {
		return shareRanges;
	}

	public void setShareRanges(Set<TeePerson> shareRanges) {
		this.shareRanges = shareRanges;
	}

	public void setWriteTime(Calendar writeTime) {
		this.writeTime = writeTime;
	}

	public Calendar getWriteTime() {
		return writeTime;
	}
	
	
}
