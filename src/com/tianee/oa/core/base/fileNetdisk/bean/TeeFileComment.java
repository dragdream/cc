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
@Table(name = "file_comment")
public class TeeFileComment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="file_comment_seq_gen")
	@SequenceGenerator(name="file_comment_seq_gen", sequenceName="file_comment_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private TeePerson user;
	
	@Column(name="CREATE_TIME")
	private Date createTime;
	
	@Column(name="CONTENT")
	private String content;
	
	@Column(name="FILE_ID")
	private int fileId;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	
	
}



	
