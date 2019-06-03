package com.tianee.oa.core.base.dam.bean;

import java.util.Calendar;

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
 * 档案操作日志
 * @author xsy
 *
 */
@Entity
@Table(name="dam_oper_records")
public class TeeOperationRecords {
   
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO, generator="dam_ope_records_seq_gen")
	@SequenceGenerator(name="dam_ope_records_seq_gen", sequenceName="dam_ope_records_seq")
	private int sid;
	
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="file_id")
	private TeeFiles file;//关联的档案
	
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="oper_user_id")
	private TeePerson operUser;//操作人
	
	
	@Column(name="oper_time")
	private Calendar operTime;//操作时间
	
	
	@Column(name="content")
	private String content;//内容


	public int getSid() {
		return sid;
	}


	public void setSid(int sid) {
		this.sid = sid;
	}


	public TeeFiles getFile() {
		return file;
	}


	public void setFile(TeeFiles file) {
		this.file = file;
	}


	public TeePerson getOperUser() {
		return operUser;
	}


	public void setOperUser(TeePerson operUser) {
		this.operUser = operUser;
	}


	public Calendar getOperTime() {
		return operTime;
	}


	public void setOperTime(Calendar operTime) {
		this.operTime = operTime;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}
	
	
	
	
	
	
	
}
