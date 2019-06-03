package com.tianee.oa.core.base.onduty.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name="pb_duty")
public class TeePbOnDuty {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="pb_duty_seq_gen")
	@SequenceGenerator(name="pb_duty_seq_gen", sequenceName="pb_duty_seq")
	@Column(name = "SID")
	private int sid;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="BZ_USER_ID")
	private TeePerson bzUser;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="BD_USER_ID")
	private TeePerson bdUser;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="YZ_USER_ID")
	private TeePerson yzUser;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="YD_USER_ID")
	private TeePerson ydUser;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ZD_USER_ID")
	private TeePerson zdUser;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Add_USER_ID")
	private TeePerson addUser;
	
	@Column(name="MOBILE_NO")
	private String mobileNo;
	
	@Column(name="CRE_TIME")
	private Date creTime;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeePerson getBzUser() {
		return bzUser;
	}

	public void setBzUser(TeePerson bzUser) {
		this.bzUser = bzUser;
	}

	public TeePerson getBdUser() {
		return bdUser;
	}

	public void setBdUser(TeePerson bdUser) {
		this.bdUser = bdUser;
	}

	public TeePerson getYzUser() {
		return yzUser;
	}

	public void setYzUser(TeePerson yzUser) {
		this.yzUser = yzUser;
	}

	public TeePerson getYdUser() {
		return ydUser;
	}

	public void setYdUser(TeePerson ydUser) {
		this.ydUser = ydUser;
	}

	public TeePerson getZdUser() {
		return zdUser;
	}

	public void setZdUser(TeePerson zdUser) {
		this.zdUser = zdUser;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public Date getCreTime() {
		return creTime;
	}

	public void setCreTime(Date creTime) {
		this.creTime = creTime;
	}

	public TeePerson getAddUser() {
		return addUser;
	}

	public void setAddUser(TeePerson addUser) {
		this.addUser = addUser;
	}
	
}
