package com.tianee.oa.core.xt.model;

import java.util.ArrayList;
import java.util.List;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;

public class TeeXTReplyModel {
	private int sid;//主键
	//private Calendar createTime;//创建时间
	private String createTimeStr;
	
	//private TeePerson createUser;//创建人
	private int createUserId;
	private String createUserName;
	
	private String content;//回复内容

	//private TeeXTRunPrcs xtRunPrcs;//关联的办理过程  
	private int xtRunPrcsId;
	
	//附件列表
	private List<TeeAttachmentModel> attList=new ArrayList<TeeAttachmentModel>();

	
	
	public List<TeeAttachmentModel> getAttList() {
		return attList;
	}

	public void setAttList(List<TeeAttachmentModel> attList) {
		this.attList = attList;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getXtRunPrcsId() {
		return xtRunPrcsId;
	}

	public void setXtRunPrcsId(int xtRunPrcsId) {
		this.xtRunPrcsId = xtRunPrcsId;
	}
	
	
	
	
}
