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


public class TeeBookModel extends TeeBaseModel{

	private String postDeptIds ;
	private String postDeptNames;//借阅权限部门
	private String postUserIds;
	private String postUserNames;//借阅权限人员
	private String postUserRoleIds;
	private String postUserRoleNames;//借阅权限角色
	private int sid;//自增id
	private String ISBN;
	private int createUserId;
	private String createUserName;
	private int createDeptId;
	private String createDeptName;
	private String bookTypeId;//图书类型
	private String bookTypeName;//图书类型名称
	private String bookName;//书名
	private String author;//作者
	private String pubDateStr;//出版日期
	private String pubHouse;//出版社
	private int amt ;//数量
	private int borrowCount ;//已借出数量
	private String price;//单价
	private String area;//存放地点
	private String  brief;//内容简介
	private int lend;//是否借出 0 否 1是
	private String lendDesc;//借阅描述
	private String  memo;//备注
	private String bookNo;//图书编号
	private int avatarId;//封面id
	private List<TeeAttachment> attacheModels;//附件
	
	
	public int getAvatarId() {
		return avatarId;
	}
	public void setAvatarId(int avatarId) {
		this.avatarId = avatarId;
	}
	public int getBorrowCount() {
		return borrowCount;
	}
	public void setBorrowCount(int borrowCount) {
		this.borrowCount = borrowCount;
	}
	public String getLendDesc() {
		return lendDesc;
	}
	public void setLendDesc(String lendDesc) {
		this.lendDesc = lendDesc;
	}
	public String getBookTypeName() {
		return bookTypeName;
	}
	public void setBookTypeName(String bookTypeName) {
		this.bookTypeName = bookTypeName;
	}
	public int getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public int getCreateDeptId() {
		return createDeptId;
	}
	public void setCreateDeptId(int createDeptId) {
		this.createDeptId = createDeptId;
	}
	public String getCreateDeptName() {
		return createDeptName;
	}
	public void setCreateDeptName(String createDeptName) {
		this.createDeptName = createDeptName;
	}
	public String getPostDeptIds() {
		return postDeptIds;
	}
	public void setPostDeptIds(String postDeptIds) {
		this.postDeptIds = postDeptIds;
	}
	public String getPostDeptNames() {
		return postDeptNames;
	}
	public void setPostDeptNames(String postDeptNames) {
		this.postDeptNames = postDeptNames;
	}
	public String getPostUserIds() {
		return postUserIds;
	}
	public void setPostUserIds(String postUserIds) {
		this.postUserIds = postUserIds;
	}
	public String getPostUserNames() {
		return postUserNames;
	}
	public void setPostUserNames(String postUserNames) {
		this.postUserNames = postUserNames;
	}
	public String getPostUserRoleIds() {
		return postUserRoleIds;
	}
	public void setPostUserRoleIds(String postUserRoleIds) {
		this.postUserRoleIds = postUserRoleIds;
	}
	public String getPostUserRoleNames() {
		return postUserRoleNames;
	}
	public void setPostUserRoleNames(String postUserRoleNames) {
		this.postUserRoleNames = postUserRoleNames;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getBookTypeId() {
		return bookTypeId;
	}
	public void setBookTypeId(String bookTypeId) {
		this.bookTypeId = bookTypeId;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPubDateStr() {
		return pubDateStr;
	}
	public void setPubDateStr(String pubDateStr) {
		this.pubDateStr = pubDateStr;
	}
	public String getPubHouse() {
		return pubHouse;
	}
	public void setPubHouse(String pubHouse) {
		this.pubHouse = pubHouse;
	}
	public int getAmt() {
		return amt;
	}
	public void setAmt(int amt) {
		this.amt = amt;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	public int getLend() {
		return lend;
	}
	public void setLend(int lend) {
		this.lend = lend;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getBookNo() {
		return bookNo;
	}
	public void setBookNo(String bookNo) {
		this.bookNo = bookNo;
	}
	public List<TeeAttachment> getAttacheModels() {
		return attacheModels;
	}
	public void setAttacheModels(List<TeeAttachment> attacheModels) {
		this.attacheModels = attacheModels;
	}
	
	
}
