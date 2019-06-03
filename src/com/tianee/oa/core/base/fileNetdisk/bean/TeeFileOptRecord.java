package com.tianee.oa.core.base.fileNetdisk.bean;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "FILE_NETDISK_OPT_RECORD")
public class TeeFileOptRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="FILE_NETDISK_OPT_RECORD_seq_gen")
	@SequenceGenerator(name="FILE_NETDISK_OPT_RECORD_seq_gen", sequenceName="FILE_NETDISK_OPT_RECORD_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@Column(name="FILE_ID")
	private int fileId;//文件id
	
	@Column(name="FILE_NAME")
	private String fileName;//文件名
	
	@Column(name="USER_ID")
	private int userId;//用户id
	
	@Column(name="USER_NAME")
	private String userName;//用户姓名
	
	@Column(name="OPT_TYPE")
	private int optType;//操作类型 1：新建（上传） 2：下载   3：重命名  4：删除  5：复制  6：移动 7:签阅   8：编辑(更新文件备注)
	
	@Column(name="OPT_CONTENT")
	private String optContent;//操作内容

	@Column(name="IP_")
	private String ip;//操作ip
	
	@Column(name="CREATE_TIME")
	private Calendar createTime;//创建时间

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getOptType() {
		return optType;
	}

	public void setOptType(int optType) {
		this.optType = optType;
	}

	public String getOptContent() {
		return optContent;
	}

	public void setOptContent(String optContent) {
		this.optContent = optContent;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}
	
	
}
