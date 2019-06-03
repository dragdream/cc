package com.tianee.oa.core.attachment.model;

import java.io.Serializable;
import java.util.Calendar;

/**
 * 附件模型
 * @author kakalion
 *
 */
public class TeeAttachmentModel implements Serializable{
	private int sid; //主键
	private int priority;//附件优先级
	private String attachmentPath;//上传路径
	private String attachmentName;//修改后的名字 后台的名字 uuid
	private String fileName;//文件名称
	private String model;//模块名称
	private Calendar createTime;//上传时间
	private String createTimeDesc;//上传时间
	private String ext;//文件后缀
	private int version;//附件版本
	private int priv;//附件权限1-阅 读      2-下载         4-删除        8-编辑   16-转储

	private String userId;//用户id
	private String userName;//用户名
	private long size;//附件大小
	private String sizeDesc;//附件大小描述
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getAttachmentPath() {
		return attachmentPath;
	}
	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}
	public String getAttachmentName() {
		return attachmentName;
	}
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public Calendar getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}
	public String getCreateTimeDesc() {
		return createTimeDesc;
	}
	public void setCreateTimeDesc(String createTimeDesc) {
		this.createTimeDesc = createTimeDesc;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public int getPriv() {
		return priv;
	}
	public void setPriv(int priv) {
		this.priv = priv;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public long getSize() {
		return size;
	}
	public void setSizeDesc(String sizeDesc) {
		this.sizeDesc = sizeDesc;
	}
	public String getSizeDesc() {
		return sizeDesc;
	}
}
