package com.tianee.oa.core.base.dam.model;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;


public class TeeFileAttchModel {
	private int sid;
	
	private int sortNo;//顺序号
	
	private String manager;//责任人
	
	private String wjz;//文件字
	
	private String title;//标题
	
	private int pageNum;//页数
	
	//private Calendar pubTime;//附件生成日期
	private String pubTimeStr;//附件生成日期
	
	private String remark;//备注
	
	//private TeeFiles file;//所属档案
	private int fileId;//档案主键

	//private TeeAttachment attch;//所属附件
	private int attchId;//附件主键
	private String attchName;//附件名称
	
	private TeeAttachmentModel attModel;
	
	
	
	
	public TeeAttachmentModel getAttModel() {
		return attModel;
	}
	public void setAttModel(TeeAttachmentModel attModel) {
		this.attModel = attModel;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getSortNo() {
		return sortNo;
	}
	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getWjz() {
		return wjz;
	}
	public void setWjz(String wjz) {
		this.wjz = wjz;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public String getPubTimeStr() {
		return pubTimeStr;
	}
	public void setPubTimeStr(String pubTimeStr) {
		this.pubTimeStr = pubTimeStr;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getFileId() {
		return fileId;
	}
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	public int getAttchId() {
		return attchId;
	}
	public void setAttchId(int attchId) {
		this.attchId = attchId;
	}
	public String getAttchName() {
		return attchName;
	}
	public void setAttchName(String attchName) {
		this.attchName = attchName;
	}

	
	
}
