package com.beidasoft.xzzf.punish.common.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name = "ZF_CALENDAR")
public class PunishCalendar {
	
	//主键
	@Id
	@Column(name = "ID")
	private String id; 

	//案件唯一ID
	@Column(name = "BASE_ID")
	private String baseId;

	//跳转功能路径
	@Column(name = "REMIND_URL")
	private String remindUrl;
	
	//开始日期
	@Column(name = "START_TIME")
	private long startTime;

	//结束日期
	@Column(name = "CLOSED_TIME")
	private long closedTime;

	//倒计时天数（每天上午8点更新）
	@Column(name = "COUNT_DOWN")
	private int countDown;

	//提示开始日期（大于等于倒计时天数开始提示）
	@Column(name = "REMIND_DAILY")
	private int remindDaily;

	//提示形式（半角逗号“,”分隔）
	@Column(name = "REMIND_TYPE")
	private String remindType;

	//提示模板
	@Column(name = "REMIND_TEMPLET")
	private String remindTemplet;
	
	//提示模板
	@Column(name = "REMIND_TITLE")
	private String remindTitle;

	//当前状态（100：正常， 200：暂停提示， 300：完成）
	@Column(name = "STATUS")
	private String status;
	
	@ManyToMany(targetEntity=TeePerson.class,   //一对多     
			fetch = FetchType.LAZY  ) 
			@JoinTable( name="ZF_CALENDAR_USER",        
			joinColumns={@JoinColumn(name="CALENDAR_ID")},       
			inverseJoinColumns={@JoinColumn(name="PERSON_ID")}  ) 	
	private List<TeePerson> actor;			//参与者
	
	@ManyToOne()
	@Index(name="IDXb7e5405856824c3ab5edea729b1")
	@JoinColumn(name="USER_ID")
	private TeePerson user;					//创建人
	
	
	@Column(name="PRIMARY_ID")
	private String primaryId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}


	public String getRemindUrl() {
		return remindUrl;
	}

	public void setRemindUrl(String remindUrl) {
		this.remindUrl = remindUrl;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getClosedTime() {
		return closedTime;
	}

	public void setClosedTime(long closedTime) {
		this.closedTime = closedTime;
	}

	public int getCountDown() {
		return countDown;
	}

	public void setCountDown(int countDown) {
		this.countDown = countDown;
	}

	public int getRemindDaily() {
		return remindDaily;
	}

	public void setRemindDaily(int remindDaily) {
		this.remindDaily = remindDaily;
	}

	public String getRemindType() {
		return remindType;
	}

	public void setRemindType(String remindType) {
		this.remindType = remindType;
	}

	public String getRemindTemplet() {
		return remindTemplet;
	}

	public void setRemindTemplet(String remindTemplet) {
		this.remindTemplet = remindTemplet;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<TeePerson> getActor() {
		return actor;
	}

	public void setActor(List<TeePerson> actor) {
		this.actor = actor;
	}

	public TeePerson getUser() {
		return user;
	}

	public void setUser(TeePerson user) {
		this.user = user;
	}

	public String getPrimaryId() {
		return primaryId;
	}

	public void setPrimaryId(String primaryId) {
		this.primaryId = primaryId;
	}

	public String getRemindTitle() {
		return remindTitle;
	}

	public void setRemindTitle(String remindTitle) {
		this.remindTitle = remindTitle;
	}

	

}
