package com.tianee.oa.core.general.bean;
import org.hibernate.annotations.Index;

import java.util.Calendar;

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

import com.tianee.oa.core.org.bean.TeeDepartment;

@SuppressWarnings("serial")
@Entity
@Table(name = "SEAL")
public class TeeSeal {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="SEAL_seq_gen")
	@SequenceGenerator(name="SEAL_seq_gen", sequenceName="SEAL_seq")
	private int sid;//自增id
	
	@Column(name="SEAL_ID")
	private String sealId;
	
	@ManyToOne()
	@Index(name="IDX1f53e70969754339a3764124d20")
	@JoinColumn(name="DEPT_ID")
	private TeeDepartment dept;//所属部门
	
	@Column(name="SEAL_NAME")
	private String sealName;//印章名称
	
	@Lob
	@Column(name="SEAL_DATA")
	private String sealData;
	
	@Column(name="CREATE_TIME")
	private Calendar createTime;
	
	@Column(name="IS_FLAG")//是否有效 1-停止  0 -正常
	private int isFlag;
	
	@Column(name="CERT_STR")
	private String certStr;//印章相关

	@Column(name="USER_STR" , length = 400)
	private String userStr;//人员权限Id字符串
	
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getSealId() {
		return sealId;
	}

	public void setSealId(String sealId) {
		this.sealId = sealId;
	}

	public TeeDepartment getDept() {
		return dept;
	}

	public void setDept(TeeDepartment dept) {
		this.dept = dept;
	}


	public String getSealName() {
		return sealName;
	}

	public void setSealName(String sealName) {
		this.sealName = sealName;
	}

	public String getSealData() {
		return sealData;
	}

	public void setSealData(String sealData) {
		this.sealData = sealData;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public int getIsFlag() {
		return isFlag;
	}

	public void setIsFlag(int isFlag) {
		this.isFlag = isFlag;
	}

	public String getCertStr() {
		return certStr;
	}

	public void setCertStr(String certStr) {
		this.certStr = certStr;
	}

	public String getUserStr() {
		return userStr;
	}

	public void setUserStr(String userStr) {
		this.userStr = userStr;
	}
	
	
	
}
