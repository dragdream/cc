package com.tianee.oa.core.attachment.bean;
import org.hibernate.annotations.Index;

import java.io.File;
import java.io.Serializable;
import java.util.Calendar;

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

import org.hibernate.annotations.Index;

import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name = "attachment")
public class TeeAttachment implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="attachment_seq_gen")
	@SequenceGenerator(name="attachment_seq_gen", sequenceName="attachment_seq")
	private int sid; //主键
	
	@Column(name="priority")
	private int priority;//附件优先级
	
	@Column(name="attachment_path",length=200)
	private String attachmentPath;//上传相对路径
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDXae3eefbcfd3e43ff834e0a8ad11")
	@JoinColumn(name="ATTACH_SPACE")
	private TeeAttachmentSpace attachSpace;//附件所属空间
	
	@Column(name="attachment_name",length=200)
	private String attachmentName;//修改后的名字 后台的名字 uuid
	
	@Column(name="file_name",length=200)
	private String fileName;//文件名称
	
	@Column(name="model",length=100)
	@Index(name="ATTACH_MODEL_INDEX")
	private String model;//模块名称
	
	@Column(name="MODEL_ID")
	@Index(name="ATTACH_MODEL_ID_INDEX")
	private String modelId;//模块ID

	private Calendar createTime;//上传时间
	
	@Column(name="ext",length=20)
	private String ext;//文件后缀
	
	@Column(name="version")
	private int version;//附件版本
	
	@Column(name="attach_size")
	private long size;//附件大小
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDX2f2c622436ab4c05a84bea40249")
	@JoinColumn(name="user_id")
	private TeePerson user;//上传者
	
	@Column(name="encry_algo")
	private String encryAlgo;
	
	@Column(name="MD5_")
	private String md5;
	
	@Column(name="Path",length=200)
	private String path;//文件路径  //新加
	

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

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

	public void setUser(TeePerson user) {
		this.user = user;
	}

	public TeePerson getUser() {
		return user;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public void setAttachSpace(TeeAttachmentSpace attachSpace) {
		this.attachSpace = attachSpace;
	}

	public TeeAttachmentSpace getAttachSpace() {
		return attachSpace;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	
	public String getEncryAlgo() {
		return encryAlgo;
	}

	public void setEncryAlgo(String encryAlgo) {
		this.encryAlgo = encryAlgo;
	}
	
	

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	/**
	 * 获取文件实际路径
	 * @return
	 */
	public String getFilePath(){
		return this.getAttachSpace().getSpacePath()+File.separator+model+File.separator+this.getAttachmentPath()+File.separator+this.getAttachmentName();
	}
}
