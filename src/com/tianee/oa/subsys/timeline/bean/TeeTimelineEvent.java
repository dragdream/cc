package com.tianee.oa.subsys.timeline.bean;

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

@Entity
@Table(name="TIMELINE_EVENT")
public class TeeTimelineEvent {
	@Id
	@GeneratedValue(generator = "system-uuid")  
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(columnDefinition = "char(32)",name="UUID")
	private String uuid;//自增id
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="TIMELINE_EVENT_TIMELINE_UUID")
	@JoinColumn(name="TIMELINE_UUID")
	private TeeTimeline timeline;
	
	@Column(name="TITLE_")
	private String title;//事件标题
	
	@Column(name="START_TIME")
	private Calendar startTime;//开始日期    格式为 yyyy-MM-dd 00:00:00
	
	@Column(name="END_TIME")
	private Calendar endTime;//结束日期   格式为yyyy-MM-dd 23:59:59
	
	@Column(name="CR_TIME")
	private Calendar crTime;//创建时间
	
	@Column(name="LAST_EDIT_TIME")
	private Calendar lastEditTime;//最后修改时间
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="TIMELINE_EVENT_CR_UID")
	@JoinColumn(name="CR_USER_ID")
	private TeePerson crUser;//创建人
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="TIMELINE_EVENT_UPDATE_UID")
	@JoinColumn(name="UPDATE_USER_ID")
	private TeePerson updateUser;//更新人
	
	@Lob
	@Column(name="CONTENT_")
	private String content;//事件内容

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Calendar getStartTime() {
		return startTime;
	}

	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	public Calendar getEndTime() {
		return endTime;
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
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

	public Calendar getLastEditTime() {
		return lastEditTime;
	}

	public void setLastEditTime(Calendar lastEditTime) {
		this.lastEditTime = lastEditTime;
	}

	public TeePerson getCrUser() {
		return crUser;
	}

	public void setCrUser(TeePerson crUser) {
		this.crUser = crUser;
	}

	public TeePerson getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(TeePerson updateUser) {
		this.updateUser = updateUser;
	}

	public TeeTimeline getTimeline() {
		return timeline;
	}

	public void setTimeline(TeeTimeline timeline) {
		this.timeline = timeline;
	}
	
}
