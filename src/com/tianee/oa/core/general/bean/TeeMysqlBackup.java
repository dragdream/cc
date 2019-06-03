package com.tianee.oa.core.general.bean;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@SuppressWarnings("serial")
@Entity
@Table(name = "mysql_backup")
public class TeeMysqlBackup {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="mysql_backup_seq_gen")
	@SequenceGenerator(name="mysql_backup_seq_gen", sequenceName="mysql_backup_seq")
	@Column(name="SID")
	private int sid;
	
	@Column(name="TIME")
	private Calendar time;//操作时间
	

	@Column(name="PATH")
	private String path;//路径


	public int getSid() {
		return sid;
	}


	public void setSid(int sid) {
		this.sid = sid;
	}


	public Calendar getTime() {
		return time;
	}


	public void setTime(Calendar time) {
		this.time = time;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}
	
}
