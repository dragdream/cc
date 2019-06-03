package com.tianee.oa.core.base.fileNetdisk.bean;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Index;

import com.tianee.oa.core.org.bean.TeePerson;
/**
 * 文件签阅表
 * @author wyw
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "FILE_NETDISK_READER")
public class TeeFileNetdiskReader {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="FILE_NETDISK_READER_seq_gen")
	@SequenceGenerator(name="FILE_NETDISK_READER_seq_gen", sequenceName="FILE_NETDISK_READER_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@ManyToOne
	@Index(name="IDX_NETDISK_READER_NETDISK_ID")
	@JoinColumn(name="FILE_NETDISK_ID")
	private TeeFileNetdisk fileNetdisk;//文件Id
	
	@ManyToOne
	@Index(name="IDX_NETDISK_READER_USER_ID")
	@JoinColumn(name="USER_ID")//
	private TeePerson reader;//签阅人
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_TIME")
	private Date createTime;//创建时间


	public int getSid() {
		return sid;
	}


	public void setSid(int sid) {
		this.sid = sid;
	}


	public TeeFileNetdisk getFileNetdisk() {
		return fileNetdisk;
	}


	public void setFileNetdisk(TeeFileNetdisk fileNetdisk) {
		this.fileNetdisk = fileNetdisk;
	}


	public TeePerson getReader() {
		return reader;
	}


	public void setReader(TeePerson reader) {
		this.reader = reader;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	
	
	
}


