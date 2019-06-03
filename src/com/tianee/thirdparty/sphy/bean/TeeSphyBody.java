package com.tianee.thirdparty.sphy.bean;

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
/**
 * 视频会议
 * */
@Entity
@Table(name="SPHY_BODY")
public class TeeSphyBody {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="SPHY_BODY_seq_gen")
	@SequenceGenerator(name="SPHY_BODY_seq_gen", sequenceName="SPHY_BODY_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SPHY_ID")
	private TeeSphy sphy;//会议
	
	@Column(name="STATUS")
	private int status;//参加人的类型 1主持人2普通用户3助理
	
	@Column(name="CJ_TIME")
	private Date cjTime;//进入会议时间
	
	@Column(name="CJ_STATUS")
	private int cjStatus;//是否参加会议
	
	//参加人
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CJ_USER")
	private TeePerson cjUser;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}


	public TeeSphy getSphy() {
		return sphy;
	}

	public void setSphy(TeeSphy sphy) {
		this.sphy = sphy;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCjTime() {
		return cjTime;
	}

	public void setCjTime(Date cjTime) {
		this.cjTime = cjTime;
	}

	public int getCjStatus() {
		return cjStatus;
	}

	public void setCjStatus(int cjStatus) {
		this.cjStatus = cjStatus;
	}

	public TeePerson getCjUser() {
		return cjUser;
	}

	public void setCjUser(TeePerson cjUser) {
		this.cjUser = cjUser;
	}
	
	
	
}
