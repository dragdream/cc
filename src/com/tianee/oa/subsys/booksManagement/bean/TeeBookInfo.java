package com.tianee.oa.subsys.booksManagement.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;

/**
 * 图书表
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "BOOK_INFO")
public class TeeBookInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "BOOK_INFO_seq_gen")
	@SequenceGenerator(name = "BOOK_INFO_seq_gen", sequenceName = "BOOK_INFO_seq")
	private int sid;// 自增id

	@ManyToOne()
	@Index(name = "IDX39d7ceab4bcf4dc4bc0e596f8c3")
	@JoinColumn(name = "CREATE_USER_ID")
	private TeePerson createUser;// 录入人

	@ManyToOne()
	@Index(name = "IDXee8fe93e9aad4893992eb7fecab")
	@JoinColumn(name = "CREATE_DEPT_ID")
	private TeeDepartment createDept;// 所在部门

	@OneToOne()
	@JoinColumn(name = "AVATAR_ID")
	private TeeAttachment avatar;// 封面

	@ManyToMany(targetEntity = TeeDepartment.class, // 多对多
	fetch = FetchType.LAZY)
	@JoinTable(name = "BOOK_DEPT_PRIV", joinColumns = { @JoinColumn(name = "BOOK_ID") }, inverseJoinColumns = { @JoinColumn(name = "DEPT_UUID") })
	@Index(name = "BOOK_DEPT_PRIV_INDEX")
	private List<TeeDepartment> postDept;// 借阅权限 -部门

	@ManyToMany(targetEntity = TeePerson.class, // 多对多
	fetch = FetchType.LAZY)
	@JoinTable(name = "BOOK_PERSON_PRIV", joinColumns = { @JoinColumn(name = "BOOK_ID") }, inverseJoinColumns = { @JoinColumn(name = "USER_UUID") })
	@Index(name = "BOOK_PERSON_PRIV_INDEX")
	private List<TeePerson> postUser;// 借阅权限--人员

	@ManyToMany(targetEntity = TeeUserRole.class, // 多对多
	fetch = FetchType.LAZY)
	@JoinTable(name = "BOOK_USER_ROLE_PRIV", joinColumns = { @JoinColumn(name = "BOOK_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_UUID") })
	@Index(name = "BOOK_USER_ROLE_PRIV_INDEX")
	private List<TeeUserRole> postUserRole;// 借阅权限--角色

	@Column(name = "ISBN", length = 300)
	private String ISBN;

	@ManyToOne()
	@Index(name = "IDXfae799e14d1347a58ed2cd88c80")
	@JoinColumn(name = "TYPE_ID")
	private TeeBookType bookType;// 图书类型

	@Column(name = "BOOK_NAME", length = 300)
	private String bookName;// 书名

	@Column(name = "AUTHOR", length = 300)
	private String author;// 作者

	@Column(name = "PUB_DATE")
	private Date pubDate;// 出版日期

	@Column(name = "PUB_HOUSE", length = 300)
	private String pubHouse;// 出版社

	@Column(name = "AMT", columnDefinition = "INT default 0", nullable = false)
	private int amt;// 数量

	private String price;// 单价

	@Column(name = "AREA", length = 300)
	private String area;// 存放地点

	@Lob
	@Column(name = "BRIEF")
	private String brief;// 内容简介

	@Column(name = "LEND", columnDefinition = "INT default 0", nullable = false)
	private int lend;// 是否借出 0 否 1是

	@Lob
	@Column(name = "MEMO")
	private String memo;// 备注

	@Column(name = "BOOK_NO", length = 300)
	private String bookNo;// 图书编号

	public TeeAttachment getAvatar() {
		return avatar;
	}

	public void setAvatar(TeeAttachment avatar) {
		this.avatar = avatar;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeePerson getCreateUser() {
		return createUser;
	}

	public void setCreateUser(TeePerson createUser) {
		this.createUser = createUser;
	}

	public TeeDepartment getCreateDept() {
		return createDept;
	}

	public void setCreateDept(TeeDepartment createDept) {
		this.createDept = createDept;
	}

	public List<TeeDepartment> getPostDept() {
		return postDept;
	}

	public void setPostDept(List<TeeDepartment> postDept) {
		this.postDept = postDept;
	}

	public List<TeePerson> getPostUser() {
		return postUser;
	}

	public void setPostUser(List<TeePerson> postUser) {
		this.postUser = postUser;
	}

	public List<TeeUserRole> getPostUserRole() {
		return postUserRole;
	}

	public void setPostUserRole(List<TeeUserRole> postUserRole) {
		this.postUserRole = postUserRole;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public TeeBookType getBookType() {
		return bookType;
	}

	public void setBookType(TeeBookType bookType) {
		this.bookType = bookType;
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

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
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

}
