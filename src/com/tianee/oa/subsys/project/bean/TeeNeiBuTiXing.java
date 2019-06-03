package com.tianee.oa.subsys.project.bean;

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
@Table(name = "NEIBU_TIXING")
//消息提醒类
public class TeeNeiBuTiXing {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="NEIBU_TIXING_seq_gen")
	@SequenceGenerator(name="NEIBU_TIXING_seq_gen", sequenceName="NEIBU_TIXING_seq")
	@Column(name="SID")
	private int sid;//自增id

	@Column(name="CONTENT")
	private String content;//提示内容

	@Column(name="USER_IDS")
	private String userIds;//提示人员
	
	@Column(name="CRE_TIME")
	private Date creTime;//提示时间
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="CREATE_USER")
	private TeePerson createUser;//

	public int getSid() {
		return sid;
	}

	public String getContent() {
		return content;
	}

	public String getUserIds() {
		return userIds;
	}

	public Date getCreTime() {
		return creTime;
	}

	public TeePerson getCreateUser() {
		return createUser;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public void setCreTime(Date creTime) {
		this.creTime = creTime;
	}

	public void setCreateUser(TeePerson createUser) {
		this.createUser = createUser;
	}
	
	
	
}
