package com.tianee.oa.core.base.email.bean;
import org.hibernate.annotations.Index;

import java.io.Serializable;

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

@Entity
@Table(name = "MAIL_BOX")
public class TeeMailBox implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="MAIL_BOX_seq_gen")
	@SequenceGenerator(name="MAIL_BOX_seq_gen", sequenceName="MAIL_BOX_seq")
	private int sid;//自增id
	
	@Column(name = "BOX_NO")
	private int boxNo;//文件夹排序号
	
	@Column(name = "BOX_NAME")
	private String boxName;//文件夹名称
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDX7c7794bb7d5242f2ad2828a220e")
	@JoinColumn(name = "USER_ID")
	private TeePerson userManager;//文件夹主人的ID
	
	@Column(name = "DEFAULT_COUNT")
	private int defaultCount;//每页显示的记录条数 默认10
	

	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getBoxNo() {
		return boxNo;
	}
	public void setBoxNo(int boxNo) {
		this.boxNo = boxNo;
	}
	public String getBoxName() {
		return boxName;
	}
	public void setBoxName(String boxName) {
		this.boxName = boxName;
	}

	public TeePerson getUserManager() {
		return userManager;
	}
	public void setUserManager(TeePerson userManager) {
		this.userManager = userManager;
	}
	public int getDefaultCount() {
		return defaultCount;
	}
	public void setDefaultCount(int defaultCount) {
		this.defaultCount = defaultCount;
	}
	
	
}
