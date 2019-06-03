package com.tianee.oa.core.base.calendar.bean;
import org.hibernate.annotations.Index;

import java.util.List;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;

/**
 * @author - syl
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "CALENDAR_AFFAIR")
public class TeeCalendarAffair {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CALENDAR_AFFAIR_seq_gen")
	@SequenceGenerator(name="CALENDAR_AFFAIR_seq_gen", sequenceName="CALENDAR_AFFAIR_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@ManyToOne()
	@Index(name="IDXb7e5405856824c3ab5edea729b1")
	@JoinColumn(name="USER_ID")
	private TeePerson user;
	
	@Column(name="START_TIME")
	private long startTime;//STRAT_TIME

	@Column(name="END_TIME")
	private long endTime;//END_TIME
	
	@Column(name="CAL_TYPE",columnDefinition="int  default 1")
	private int calType;//
	

	@Column(name="CAL_LEVEL",columnDefinition="char(1)  default '0'")
	private String calLevel;//CAL_LEVEL 级别
	
	@Column(name="CAL_AFF_TYPE",columnDefinition="int  default 0")
	private int calAffType;//日程或者--周期性事务  0-日程    1-周期性事务
	
	@Column(name="OVER_STATUS",columnDefinition="INT default 0" ,nullable=false)
	private int overStatus;//OVER_STATUS 0-未完成  1-已完成
	
	@Lob 
	@Column(name="CONTENT")
	private String content;//CONTENT
	
	@ManyToOne()
	@Index(name="IDXe69714bb26f94aa3a7b3128d112")
	@JoinColumn(name="MANAGER_ID")
	private TeePerson manager;//MANAGER_ID

	@ManyToMany(targetEntity=TeePerson.class,   //一对多     
			fetch = FetchType.LAZY  ) 
			@JoinTable( name="CAL_AFF_PERSON_ACTOR",        
			joinColumns={@JoinColumn(name="CAL_ID")},       
			inverseJoinColumns={@JoinColumn(name="PERSON_ID")}  ) 	
	private List<TeePerson> actor;//参与者
	
	/*以下为周期性事务字段*/
	
	@Column(name="REMIND_TYPE",columnDefinition="int  default 1")
	private int remindType;//提醒类型

	@Column(name="REMIND_DATE",length=100)
	private String remindDate;//提醒日期
	
	@Column(name="IS_WEEKEND",columnDefinition="int  default 0")
	private int isWeekend;
	
	@Column(name="REMIND_TIME")
	private long remindTime;//提醒时间
	
	@Column(name="LAST_REMIND")
	private long lastRemind;//最后一次内部提醒时间
	
	@Column(name="SMS2_REMIND",columnDefinition="int  default 0")
	private int SMS2_REMIND;//是否使用手机短信提醒0-否 1-是

	@Column(name="LAST_SMS2_REMIND")
	private long lastSms2Remind;//最近一次手机短信提醒的时间
	

	

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeePerson getUser() {
		return user;
	}

	public void setUser(TeePerson user) {
		this.user = user;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}


	public int getCalType() {
		return calType;
	}

	public void setCalType(int calType) {
		this.calType = calType;
	}

	public String getRemindDate() {
		return remindDate;
	}

	public void setRemindDate(String remindDate) {
		this.remindDate = remindDate;
	}

	public long getRemindTime() {
		return remindTime;
	}

	public void setRemindTime(long remindTime) {
		this.remindTime = remindTime;
	}

	public long getLastRemind() {
		return lastRemind;
	}

	public void setLastRemind(long lastRemind) {
		this.lastRemind = lastRemind;
	}

	public int getSMS2_REMIND() {
		return SMS2_REMIND;
	}

	public void setSMS2_REMIND(int sMS2_REMIND) {
		SMS2_REMIND = sMS2_REMIND;
	}

	public long getLastSms2Remind() {
		return lastSms2Remind;
	}

	public void setLastSms2Remind(long lastSms2Remind) {
		this.lastSms2Remind = lastSms2Remind;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public TeePerson getManager() {
		return manager;
	}

	public void setManager(TeePerson manager) {
		this.manager = manager;
	}

	public List<TeePerson> getActor() {
		return actor;
	}

	public void setActor(List<TeePerson> actor) {
		this.actor = actor;
	}

	public int getIsWeekend() {
		return isWeekend;
	}

	public void setIsWeekend(int isWeekend) {
		this.isWeekend = isWeekend;
	}

	public int getRemindType() {
		return remindType;
	}

	public void setRemindType(int remindType) {
		this.remindType = remindType;
	}

	public String getCalLevel() {
		return calLevel;
	}

	public void setCalLevel(String calLevel) {
		this.calLevel = calLevel;
	}

	public int getCalAffType() {
		return calAffType;
	}

	public void setCalAffType(int calAffType) {
		this.calAffType = calAffType;
	}

	public int getOverStatus() {
		return overStatus;
	}

	public void setOverStatus(int overStatus) {
		this.overStatus = overStatus;
	}
	

}
