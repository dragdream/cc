package com.tianee.oa.core.workflow.flowrun.model;

import javax.persistence.Column;


public class TeeFlowArchiveModel {

	private int sid;
	private String archiveDesc;//归档描述
	//private Date crTime;//创建时间
	private String crTimeStr;//创建时间
    private String version;//归档版本
	private int status;//1.归档成功！  0 归档失败
	//private Date archiveDate;//归档日期范围
	private  String archiveDateStr;//归档日期范围
	private int deleteStatus;//1.已删除！  0 未删除！
	
	
	public int getDeleteStatus() {
		return deleteStatus;
	}
	public void setDeleteStatus(int deleteStatus) {
		this.deleteStatus = deleteStatus;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	
	public String getArchiveDesc() {
		return archiveDesc;
	}
	public void setArchiveDesc(String archiveDesc) {
		this.archiveDesc = archiveDesc;
	}
	public String getCrTimeStr() {
		return crTimeStr;
	}
	public void setCrTimeStr(String crTimeStr) {
		this.crTimeStr = crTimeStr;
	}
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getArchiveDateStr() {
		return archiveDateStr;
	}
	public void setArchiveDateStr(String archiveDateStr) {
		this.archiveDateStr = archiveDateStr;
	}
}
