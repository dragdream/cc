package com.tianee.oa.core.base.fileNetdisk.model;

import java.io.Serializable;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;

public class TeeFileNetdiskModel implements Serializable {
	private int sid;// 自增id
	private int fileNo;//
	private String fileName;// 文件名称
	private TeeAttachmentModel attacheModels;
	private int filetype;// 文件类型
	private String createTimeStr;// 创建时间
	private String createrStr;// 创建人
	private int createrId;// 创建人id
	private String fileFullPath;// 文件存在全路径
	private String fileSize;// 文件大小
	private String fileTypeExt;// 文件类型扩展名 0-
	private int attachSid;// 附件sid
	private int priv;// 附件权限
	private String content;// 文件说明

	private int parentFileSid = 0;
	private String parentFileName;
	private int isSignRead;// 是否已签阅 0-未签阅；1-已签约
	private int autoIndex;//是否点开启1：开启0：未开启
	
    private int huiFuCount;//回复数
	
	private int readCount;//阅读数
	
	private int xiaZaiCount;//下载数
	
	private int countNum;
	
	private int picCount;
	
	private String adviseFile;
	
	//1=是  0=否
	private int hasTodayCreated=0;//如果是文件夹  则判断文件夹中是否有当天新建的文件   如果是文件   则判断该文件是否是当天新建的
	
	
	
	
	
	public int getHasTodayCreated() {
		return hasTodayCreated;
	}
	public void setHasTodayCreated(int hasTodayCreated) {
		this.hasTodayCreated = hasTodayCreated;
	}
	public String getAdviseFile() {
		return adviseFile;
	}
	public void setAdviseFile(String adviseFile) {
		this.adviseFile = adviseFile;
	}
	public int getPicCount() {
		return picCount;
	}
	public void setPicCount(int picCount) {
		this.picCount = picCount;
	}
	public int getAutoIndex() {
		return autoIndex;
	}
	public void setAutoIndex(int autoIndex) {
		this.autoIndex = autoIndex;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getFileNo() {
		return fileNo;
	}
	public void setFileNo(int fileNo) {
		this.fileNo = fileNo;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public TeeAttachmentModel getAttacheModels() {
		return attacheModels;
	}
	public void setAttacheModels(TeeAttachmentModel attacheModels) {
		this.attacheModels = attacheModels;
	}
	public int getFiletype() {
		return filetype;
	}
	public void setFiletype(int filetype) {
		this.filetype = filetype;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public String getCreaterStr() {
		return createrStr;
	}
	public void setCreaterStr(String createrStr) {
		this.createrStr = createrStr;
	}
	public int getCreaterId() {
		return createrId;
	}
	public void setCreaterId(int createrId) {
		this.createrId = createrId;
	}
	public String getFileFullPath() {
		return fileFullPath;
	}
	public void setFileFullPath(String fileFullPath) {
		this.fileFullPath = fileFullPath;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileTypeExt() {
		return fileTypeExt;
	}
	public void setFileTypeExt(String fileTypeExt) {
		this.fileTypeExt = fileTypeExt;
	}
	public int getAttachSid() {
		return attachSid;
	}
	public void setAttachSid(int attachSid) {
		this.attachSid = attachSid;
	}
	public int getPriv() {
		return priv;
	}
	public void setPriv(int priv) {
		this.priv = priv;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getParentFileSid() {
		return parentFileSid;
	}
	public void setParentFileSid(int parentFileSid) {
		this.parentFileSid = parentFileSid;
	}
	public String getParentFileName() {
		return parentFileName;
	}
	public void setParentFileName(String parentFileName) {
		this.parentFileName = parentFileName;
	}
	public int getIsSignRead() {
		return isSignRead;
	}
	public void setIsSignRead(int isSignRead) {
		this.isSignRead = isSignRead;
	}
	public int getHuiFuCount() {
		return huiFuCount;
	}
	public void setHuiFuCount(int huiFuCount) {
		this.huiFuCount = huiFuCount;
	}
	public int getReadCount() {
		return readCount;
	}
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	public int getXiaZaiCount() {
		return xiaZaiCount;
	}
	public void setXiaZaiCount(int xiaZaiCount) {
		this.xiaZaiCount = xiaZaiCount;
	}
	public int getCountNum() {
		return countNum;
	}
	public void setCountNum(int countNum) {
		this.countNum = countNum;
	}
    

}
