package com.tianee.oa.subsys.booksManagement.model;


import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Index;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.subsys.booksManagement.bean.TeeBookType;
import com.tianee.oa.webframe.httpModel.TeeBaseModel;


public class TeeBookManageModel extends TeeBaseModel{

	private int sid;//自增id
	
	private int buserId;//借书人id
	
	private String buserName;//借书人name
	
	private int ruserId;//审批人id
	
	private String ruserName;//审批人name
	
	private String borrowDateStr;//借书日期
	
	private String returnDateStr;//还书日期
	
	private String realReturnDateStr;//实际还书日期
	
	private int bookStatus ;//借书标记
	
	private int deleteFlag ;//删除标记
	
	private int status ;//还书状态标记
	
	private int regFlag ;//管理员直接登记标记
	
	private String whoConfirm;
	
	private String  borrowRemark;//借书备注
	
	private String bookNo;//图书变灰
	
	private String bookName;//图书名
	
	private String desc;
	
	

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}


	public int getBuserId() {
		return buserId;
	}

	public void setBuserId(int buserId) {
		this.buserId = buserId;
	}

	public String getBuserName() {
		return buserName;
	}

	public void setBuserName(String buserName) {
		this.buserName = buserName;
	}

	public int getRuserId() {
		return ruserId;
	}

	public void setRuserId(int ruserId) {
		this.ruserId = ruserId;
	}

	public String getRuserName() {
		return ruserName;
	}

	public void setRuserName(String ruserName) {
		this.ruserName = ruserName;
	}


	public String getBorrowDateStr() {
		return borrowDateStr;
	}

	public void setBorrowDateStr(String borrowDateStr) {
		this.borrowDateStr = borrowDateStr;
	}

	public String getReturnDateStr() {
		return returnDateStr;
	}

	public void setReturnDateStr(String returnDateStr) {
		this.returnDateStr = returnDateStr;
	}

	public String getRealReturnDateStr() {
		return realReturnDateStr;
	}

	public void setRealReturnDateStr(String realReturnDateStr) {
		this.realReturnDateStr = realReturnDateStr;
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
