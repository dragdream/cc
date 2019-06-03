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
 * 档案借阅记录表
 * @author xsy
 *
 */
@Entity
@Table(name="dam_file_borrow")
public class TeeFileBorrow{
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO, generator="dam_file_borrow_seq_gen")
	@SequenceGenerator(name="dam_file_borrow_seq_gen", sequenceName="dam_file_borrow_seq")
	public int sid;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="file_id")
	private TeeFiles file;//档案
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="view_user_id")
	private TeePerson viewUser;//借阅人
	
	@Column(name="view_time")
	private Calendar viewTime;//借阅时间
	
	
	@Column(name="return_time")
	private Calendar returnTime;//归还时间
	
	
	@Column(name="return_flag")
	private int returnFlag;//归还状态   0=未归还   1=归还中   2=已归还


	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="approver_id")
	private TeePerson approver;//审批人
	
	@Column(name="approve_time")
	private Calendar approveTime;//审批时间
	
	@Column(name="approve_flag")
	private int approveFlag;//审批状态   0=待审批  1=已批准   2=未批准
	
	
	
	
	
	public TeePerson getApprover() {
		return approver;
	}


	public void setApprover(TeePerson approver) {
		this.approver = approver;
	}


	public Calendar getApproveTime() {
		return approveTime;
	}


	public void setApproveTime(Calendar approveTime) {
		this.approveTime = approveTime;
	}


	public int getApproveFlag() {
		return approveFlag;
	}


	public void setApproveFlag(int approveFlag) {
		this.approveFlag = approveFlag;
	}


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


	public TeePerson getViewUser() {
		return viewUser;
	}


	public void setViewUser(TeePerson viewUser) {
		this.viewUser = viewUser;
	}


	public Calendar getViewTime() {
		return viewTime;
	}


	public void setViewTime(Calendar viewTime) {
		this.viewTime = viewTime;
	}


	public Calendar getReturnTime() {
		return returnTime;
	}


	public void setReturnTime(Calendar returnTime) {
		this.returnTime = returnTime;
	}


	public int getReturnFlag() {
		return returnFlag;
	}


	public void setReturnFlag(int returnFlag) {
		this.returnFlag = returnFlag;
	}
	
	
	
	
}
