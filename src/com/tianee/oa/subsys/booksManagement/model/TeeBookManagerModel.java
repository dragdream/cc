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


public class TeeBookManagerModel extends TeeBaseModel{

	private String postDeptIds ;
	private String postDeptNames;//借阅权限部门
	private String postUserIds;
	private String postUserNames;//借阅权限人员
	private int sid;
	
	
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
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	
	
}
