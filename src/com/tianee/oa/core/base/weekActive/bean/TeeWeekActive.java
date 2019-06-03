package com.tianee.oa.core.base.weekActive.bean;
import org.hibernate.annotations.Index;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tianee.oa.core.org.bean.TeePerson;


/**
 * 周活动安排
 * @author 
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "WEEK_ACTIVE")
public class TeeWeekActive {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="WEEK_ACTIVE_seq_gen")
	@SequenceGenerator(name="WEEK_ACTIVE_seq_gen", sequenceName="WEEK_ACTIVE_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@Column(name="ACTIVE_USER",length=1000)
	private String  activeUser;//活动人员，以逗号分隔
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ACTIVE_START_TIME")
	private Date activeStartTime;//ACTIVE_TIME 活动开始时间
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ACTIVE_END_TIME")
	private Date activeEndTime;//ACTIVE_TIME 活动结束时间
	
	@Lob
	@Column(name="ACTIVE_CONTENT")
	private String  activeContent;//活动内容
	
	@Column(name="OVER_STATUS",columnDefinition="char(1) default 0")
	private String  overStatus;//活动状态 
	
	@ManyToOne()
	@Index(name="IDX87d01cd5f84a41ca80fe72a00a3")
	@JoinColumn(name="CREATE_USER_ID")
	private TeePerson createUser;//安排人
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_TIME")
	private Date createTime;//安排时间
	
	@Column(name="DEPT_AND_USER")
	private String deptAndUser;
	
	@Column(name="ADDERSS")
	private String adderss;
	
	@Column(name="SW_OR_XW")
	private int swOrxw;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getActiveUser() {
		return activeUser;
	}

	public void setActiveUser(String activeUser) {
		this.activeUser = activeUser;
	}

	public Date getActiveStartTime() {
		return activeStartTime;
	}

	public void setActiveStartTime(Date activeStartTime) {
		this.activeStartTime = activeStartTime;
	}

	public Date getActiveEndTime() {
		return activeEndTime;
	}

	public void setActiveEndTime(Date activeEndTime) {
		this.activeEndTime = activeEndTime;
	}

	public String getActiveContent() {
		return activeContent;
	}

	public void setActiveContent(String activeContent) {
		this.activeContent = activeContent;
	}

	public String getOverStatus() {
		return overStatus;
	}

	public void setOverStatus(String overStatus) {
		this.overStatus = overStatus;
	}



	public TeePerson getCreateUser() {
		return createUser;
	}

	public void setCreateUser(TeePerson createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDeptAndUser() {
		return deptAndUser;
	}

	public void setDeptAndUser(String deptAndUser) {
		this.deptAndUser = deptAndUser;
	}

	public String getAdderss() {
		return adderss;
	}

	public void setAdderss(String adderss) {
		this.adderss = adderss;
	}

	public int getSwOrxw() {
		return swOrxw;
	}

	public void setSwOrxw(int swOrxw) {
		this.swOrxw = swOrxw;
	}
	
	
	

}
