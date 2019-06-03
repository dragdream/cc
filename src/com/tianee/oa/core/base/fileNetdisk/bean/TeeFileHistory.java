package com.tianee.oa.core.base.fileNetdisk.bean;
import java.util.Date;

import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 
 * @author syl
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "file_history")
public class TeeFileHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="file_history_seq_gen")
	@SequenceGenerator(name="file_history_seq_gen", sequenceName="file_history_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private TeePerson user;
	
	@Column(name="CREATE_TIME")
	private Date createTime;
	
	
	@Column(name="FILE_ID")
	private int fileId;
	
	@Column(name="BANBEN")
	private int banben;
	
	@Column(name="ATTACH_ID")
	private int attachId;
	
	@Column(name="REASE")
	private String rease;

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public int getBanben() {
		return banben;
	}

	public void setBanben(int banben) {
		this.banben = banben;
	}

	public int getAttachId() {
		return attachId;
	}

	public void setAttachId(int attachId) {
		this.attachId = attachId;
	}

	public String getRease() {
		return rease;
	}

	public void setRease(String rease) {
		this.rease = rease;
	}

	
	
}



	
