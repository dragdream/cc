package com.tianee.oa.subsys.booksManagement.bean;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 借还管理
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "BOOK_MANAGE")
public class TeeBookManage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="BOOK_MANAGE_seq_gen")
	@SequenceGenerator(name="BOOK_MANAGE_seq_gen", sequenceName="BOOK_MANAGE_seq")
	private int sid;//自增id
	
	@ManyToOne()
	@Index(name="IDX13ca3c4bc65440c4be002e2326f")
	@JoinColumn(name="BUSER_ID")
	private TeePerson buser;//借书人
	
	@ManyToOne()
	@Index(name="IDX4760a5ddc11f440a8697d976144")
	@JoinColumn(name="RUSER_ID")
	private TeePerson ruser;//审批人
	
	@Column(name="BORROW_DATE")
	private Date borrowDate;//借书日期
	
	@Column(name="RETURN_DATE")
	private Date returnDate;//还书日期
	
	@Column(name="REAL_RETURN_DATE")
	private Date realReturnDate;//实际还书日期
	
	@Column(name="BOOK_STATUS" ,columnDefinition="INT default 0" ,nullable=false)
	private int bookStatus ;//借书标记   
	
	@Column(name="DELETE_FLAG" ,columnDefinition="INT default 0" ,nullable=false)
	private int deleteFlag ;//删除标记 0-未删除；1-删除
	
	@Column(name="STATUS" ,columnDefinition="INT default 0" ,nullable=false)
	private int status ;//还书状态标记 0-借阅待批；1-已批借阅；2-未批准借阅
	
	@Column(name="REG_FLAG" ,columnDefinition="INT default 0" ,nullable=false)
	private int regFlag ;//管理员直接登记标记
	
	@Column(name="WHO_CONFIRM",length=300)
	private String whoConfirm;
	
	@Lob
	@Column(name="BORROW_REMARK")
	private String  borrowRemark;//借书备注
	
	@Column(name="BOOK_NO",length=300)
	private String bookNo;//图书变灰

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeePerson getBuser() {
		return buser;
	}

	public void setBuser(TeePerson buser) {
		this.buser = buser;
	}

	public TeePerson getRuser() {
		return ruser;
	}

	public void setRuser(TeePerson ruser) {
		this.ruser = ruser;
	}

	public Date getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(Date borrowDate) {
		this.borrowDate = borrowDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public Date getRealReturnDate() {
		return realReturnDate;
	}

	public void setRealReturnDate(Date realReturnDate) {
		this.realReturnDate = realReturnDate;
	}

	public int getBookStatus() {
		return bookStatus;
	}

	public void setBookStatus(int bookStatus) {
		this.bookStatus = bookStatus;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getRegFlag() {
		return regFlag;
	}

	public void setRegFlag(int regFlag) {
		this.regFlag = regFlag;
	}

	public String getWhoConfirm() {
		return whoConfirm;
	}

	public void setWhoConfirm(String whoConfirm) {
		this.whoConfirm = whoConfirm;
	}

	public String getBorrowRemark() {
		return borrowRemark;
	}

	public void setBorrowRemark(String borrowRemark) {
		this.borrowRemark = borrowRemark;
	}

	public String getBookNo() {
		return bookNo;
	}

	public void setBookNo(String bookNo) {
		this.bookNo = bookNo;
	}

	
}
