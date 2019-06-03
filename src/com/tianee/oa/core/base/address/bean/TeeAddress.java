package com.tianee.oa.core.base.address.bean;
import org.hibernate.annotations.Index;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Address")
public class TeeAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="Address_seq_gen")
	@SequenceGenerator(name="Address_seq_gen", sequenceName="Address_seq")
	@Column(name = "SID")
	private int sid;

	@Column(name = "USER_ID", nullable = true, length = 20)
	private String userId;
	// 为0代表“默认”分组
	@Column(name = "GROUP_ID", nullable = true)
	private int groupId;
	// 姓名
	@Column(name = "PSN_NAME", nullable = true, length = 120)
	private String psnName;
	// 性别 0 男 1 女
	@Column(name = "SEX", nullable = true, length = 1)
	private String sex;
	// 昵称
	@Column(name = "NICKNAME", nullable = true, length = 50)
	private String nickName;
	// 生日
	@Temporal(value = TemporalType.DATE)
	@Column(name = "BIRTHDAY", nullable = true)
	private Date birthday;
	// 职务
	@Column(name = "MINISTRATION", nullable = true, length = 50)
	private String ministration;
	// 配偶
	@Column(name = "mate", nullable = true, length = 20)
	private String mate;
	// 50个字符，内容不限
	@Column(name = "CHILD", nullable = true, length = 50)
	private String child;
	// 部门名称
	@Column(name = "DEPT_NAME", nullable = true, length = 50)
	private String deptName;
	// 单位地址
	@Column(name = "ADD_DEPT", nullable = true, length = 200)
	private String addDept;
	// 单位邮编
	@Column(name = "POST_NO_DEPT", nullable = true, length = 50)
	private String postNoDept;
	// 工作电话
	@Column(name = "TEL_NO_DEPT", nullable = true, length = 50)
	private String telNoDept;
	// 工作传真
	@Column(name = "FAX_NO_DEPT", nullable = true, length = 50)
	private String faxNoDept;
	// 家庭住址
	@Column(name = "ADD_HOME", nullable = true, length = 200)
	private String addHome;
	// 家庭邮编
	@Column(name = "POST_NO_HOME", nullable = true, length = 50)
	private String postNoHome;
	// 家庭电话
	@Column(name = "TEL_NO_HOME", nullable = true, length = 50)
	private String telNoHome;
	// 手机
	@Column(name = "MOBIL_NO", nullable = true, length = 50)
	private String mobilNo;
	// 小灵通
	@Column(name = "BP_NO", nullable = true, length = 20)
	private String bpNo;
	// 电子邮件
	@Column(name = "EMAIL", nullable = true, length = 100)
	private String email;
	// QQ号码
	@Column(name = "OICQ_NO", nullable = true, length = 50)
	private String oicqNo;
	// MSN号码
	@Column(name = "ICQ_NO", nullable = true, length = 50)
	private String icqNo;
	// 备注
	@Lob
	@Column(name = "NOTES", nullable = true)
	private String notes;
	// 排序号
	@Column(name = "PSN_NO", nullable = true, length = 20, columnDefinition = "INT default 0")
	private int psnNo = 0;

	@Column(name = "SMS_FLAG", nullable = true, length = 20)
	private String smsFlag;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getPsnName() {
		return psnName;
	}

	public void setPsnName(String psnName) {
		this.psnName = psnName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getMinistration() {
		return ministration;
	}

	public void setMinistration(String ministration) {
		this.ministration = ministration;
	}

	public String getMate() {
		return mate;
	}

	public void setMate(String mate) {
		this.mate = mate;
	}

	public String getChild() {
		return child;
	}

	public void setChild(String child) {
		this.child = child;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getAddDept() {
		return addDept;
	}

	public void setAddDept(String addDept) {
		this.addDept = addDept;
	}

	public String getPostNoDept() {
		return postNoDept;
	}

	public void setPostNoDept(String postNoDept) {
		this.postNoDept = postNoDept;
	}

	public String getTelNoDept() {
		return telNoDept;
	}

	public void setTelNoDept(String telNoDept) {
		this.telNoDept = telNoDept;
	}

	public String getFaxNoDept() {
		return faxNoDept;
	}

	public void setFaxNoDept(String faxNoDept) {
		this.faxNoDept = faxNoDept;
	}

	public String getAddHome() {
		return addHome;
	}

	public void setAddHome(String addHome) {
		this.addHome = addHome;
	}

	public String getPostNoHome() {
		return postNoHome;
	}

	public void setPostNoHome(String postNoHome) {
		this.postNoHome = postNoHome;
	}

	public String getTelNoHome() {
		return telNoHome;
	}

	public void setTelNoHome(String telNoHome) {
		this.telNoHome = telNoHome;
	}

	public String getMobilNo() {
		return mobilNo;
	}

	public void setMobilNo(String mobilNo) {
		this.mobilNo = mobilNo;
	}

	public String getBpNo() {
		return bpNo;
	}

	public void setBpNo(String bpNo) {
		this.bpNo = bpNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOicqNo() {
		return oicqNo;
	}

	public void setOicqNo(String oicqNo) {
		this.oicqNo = oicqNo;
	}

	public String getIcqNo() {
		return icqNo;
	}

	public void setIcqNo(String icqNo) {
		this.icqNo = icqNo;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public int getPsnNo() {
		return psnNo;
	}

	public void setPsnNo(int psnNo) {
		this.psnNo = psnNo;
	}

	public String getSmsFlag() {
		return smsFlag;
	}

	public void setSmsFlag(String smsFlag) {
		this.smsFlag = smsFlag;
	}

}
