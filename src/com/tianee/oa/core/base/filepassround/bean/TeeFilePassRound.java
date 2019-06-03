package com.tianee.oa.core.base.filepassround.bean;


import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "file_pass_round")
public class TeeFilePassRound {
	
	@Id
	@Column(name="ID")
	private String id;		//主键
	
	@Column(name="PRO_ID")
	private Integer proId;	//发送人id
	
	@Column(name="DEP_ID")
	private Integer depId;		//部门id
	
	@Column(name="TITLE")
	private String title;		//标题
	
	@Column(name="SEND_TIME")
	private Calendar sendTime;	//发送时间


	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getProId() {
		return proId;
	}

	public void setProId(Integer proId) {
		this.proId = proId;
	}
	public Integer getDepId() {
		return depId;
	}

	public void setDepId(Integer depId) {
		this.depId = depId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Calendar getSendTime() {
		return sendTime;
	}

	public void setSendTime(Calendar sendTime) {
		this.sendTime = sendTime;
	}

	@Override
	public String toString() {
		return "TeeFilePassRound [id=" + id + ", proId=" + proId
				 + ", depId=" + depId + ", title="
				+ title + ", sendTime=" + sendTime + "]";
	}

	
	
	

}
