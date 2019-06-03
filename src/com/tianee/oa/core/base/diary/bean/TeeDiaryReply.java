package com.tianee.oa.core.base.diary.bean;
import org.hibernate.annotations.Index;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.base.diary.model.TeeDiaryReplyModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.annotation.TeeVoClassMappingAnt;
import com.tianee.webframe.annotation.TeeVoFieldMappingAnt;

@Entity
@Table(name="DIARY_REPLY")
@TeeVoClassMappingAnt(target=TeeDiaryReplyModel.class)
public class TeeDiaryReply {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="DIARY_REPLY_seq_gen")
	@SequenceGenerator(name="DIARY_REPLY_seq_gen", sequenceName="DIARY_REPLY_seq")
	@Column(name="SID")
	private int sid;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDX23239fa0e2fc4111bb2fd7ac32d")
	@JoinColumn(name="DIARY_ID")
	@TeeVoFieldMappingAnt(currentFields={"sid"}, targetFields = {"diaryId"})
	private TeeDiary diary;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDX49e4e8b87ba3439da1982a1789d")
	@JoinColumn(name="REPLY_USER_ID")
	@TeeVoFieldMappingAnt(currentFields={"uuid"}, targetFields = {"diaryId"})
	private TeePerson replyUser;
	
	@Column(name="CONTENT")
	@Lob()
	private String content;
	
	@Column(name="CR_TIME")
	private Calendar createTime = Calendar.getInstance();

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeeDiary getDiary() {
		return diary;
	}

	public void setDiary(TeeDiary diary) {
		this.diary = diary;
	}

	public TeePerson getReplyUser() {
		return replyUser;
	}

	public void setReplyUser(TeePerson replyUser) {
		this.replyUser = replyUser;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}
	
	
}
