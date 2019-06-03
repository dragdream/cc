package com.tianee.oa.core.base.attend.bean;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;



import com.tianee.oa.core.org.bean.TeePerson;
@Entity
@Table(name = "attend_assign")
public class TeeAttendAssign {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="attend_assign_seq_gen")
	@SequenceGenerator(name="attend_assign_seq_gen", sequenceName="attend_assign_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@OneToOne()
	@JoinColumn(name="USER_ID")
	private TeePerson user;//申请人
	
	@Column(name="CREATE_TIME")
	private Calendar createTime;//上报时间
	
	
	@Column(name="remark")
	private String remark;//备注
	
	
	@Column(name="address")
	private String address;//上报地点
	
	
	@Column(name="addr_Point")
	private String addrPoint;//上报点


	@Column(name="attach_ids")
	private String attachIds;//附件id字符串   例如：12,22
	
	
	
	public String getAttachIds() {
		return attachIds;
	}


	public void setAttachIds(String attachIds) {
		this.attachIds = attachIds;
	}


	public int getSid() {
		return sid;
	}


	public TeePerson getUser() {
		return user;
	}


	public Calendar getCreateTime() {
		return createTime;
	}


	public String getRemark() {
		return remark;
	}


	public String getAddress() {
		return address;
	}


	public String getAddrPoint() {
		return addrPoint;
	}


	public void setSid(int sid) {
		this.sid = sid;
	}


	public void setUser(TeePerson user) {
		this.user = user;
	}


	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public void setAddrPoint(String addrPoint) {
		this.addrPoint = addrPoint;
	}
	
	
	
}
