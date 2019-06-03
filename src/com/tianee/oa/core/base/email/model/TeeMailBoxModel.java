package com.tianee.oa.core.base.email.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.base.email.bean.TeeMailBody;
import com.tianee.oa.core.base.email.bean.TeeMailBox;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.webframe.httpModel.TeeBaseModel;


public class TeeMailBoxModel  extends TeeBaseModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int sid;//邮件body Id
	private int boxNo;//文件夹排序号
	private String boxName;//文件夹名称
	private TeePerson userManager;//文件夹主人的ID
	private int defaultCount;//每页显示的记录条数 默认10
	private long mailCount;//该邮箱分类下有多少封邮件
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
	public long getMailCount() {
		return mailCount;
	}
	public void setMailCount(long mailCount) {
		this.mailCount = mailCount;
	}
	
	

}
