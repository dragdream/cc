package com.tianee.oa.core.base.fileNetdisk.model;
/**
 * 文件签阅表
 * @author wyw
 *
 */
public class TeeFileNetdiskReaderModel {
	private int sid;//自增id
	
	private int fileNetdiskId;//文件Id
	private int readerId;//签阅人Id
	private String readerName;//签阅人
	private String createTimeStr;//创建时间
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getFileNetdiskId() {
		return fileNetdiskId;
	}
	public void setFileNetdiskId(int fileNetdiskId) {
		this.fileNetdiskId = fileNetdiskId;
	}
	public int getReaderId() {
		return readerId;
	}
	public void setReaderId(int readerId) {
		this.readerId = readerId;
	}
	public String getReaderName() {
		return readerName;
	}
	public void setReaderName(String readerName) {
		this.readerName = readerName;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	
	

	
}


