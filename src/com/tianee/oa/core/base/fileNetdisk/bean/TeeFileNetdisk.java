package com.tianee.oa.core.base.fileNetdisk.bean;
import org.hibernate.annotations.Index;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Index;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeePerson;
/**
 * @author - syl
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "FILE_NETDISK")
public class TeeFileNetdisk {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="FILE_NETDISK_seq_gen")
	@SequenceGenerator(name="FILE_NETDISK_seq_gen", sequenceName="FILE_NETDISK_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@ManyToOne
	@Index(name="IDX78da05bdc0954edca18dde6c2fa")
	@JoinColumn(name="FILE_PARENT")//
	private TeeFileNetdisk fileParent;
	
	@Column(name="fileNo", columnDefinition="INT default 0")
	private int fileNo;//
	
	@Column(name="FILE_NAME")
	private String fileName;//文件名称
	
	
	@Column(name="FILE_TYPE",columnDefinition="INT default 0")
	private int filetype;//文件类型，0-文件夹；1-文件
	
	@Column(name="FILE_NETDISK_TYPE",columnDefinition="INT default 0")
    private int fileNetdiskType;//网盘类型，0-公共网盘；1-个人网盘

	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_TIME")
	private Date createTime;//创建时间
	
	@Lob 
	@JoinColumn(name="CONTENT")//
	private String content;
	
	@ManyToOne
	@Index(name="IDX5505329835b14832a787fb6af99")
	@JoinColumn(name="CREATER")//
	private TeePerson creater;//创建人
	
	@Column(name="FILE_FULL_PATH")
	private String fileFullPath;//文件存在全路径
	
	@OneToOne
	private TeeAttachment attachemnt;

	@OneToMany(mappedBy="fileNetdisk",fetch=FetchType.LAZY,cascade=CascadeType.ALL,orphanRemoval=true)//因为这里是双向的 一对多 所以要写mappedBy	
	private List<TeeFileUserPriv> fileUserPriv = new ArrayList(0);//用户文件权限
	
	
	@OneToMany(mappedBy="fileNetdisk",fetch=FetchType.LAZY,cascade=CascadeType.ALL,orphanRemoval=true)//因为这里是双向的 一对多 所以要写mappedBy	
	private List<TeeFileDeptPriv> fileDeptPriv = new ArrayList(0);//部门文件权限
	
	@OneToMany(mappedBy="fileNetdisk",fetch=FetchType.LAZY,cascade=CascadeType.ALL,orphanRemoval=true)//因为这里是双向的 一对多 所以要写mappedBy	
	private List<TeeFileRolePriv> fileRolePriv = new ArrayList(0);//角色文件权限
	
	@Column(name="AUTO_INDEX")//是否开启1为开启 0为未开启
	private int autoIndex;
	
	@Column(name="PIC_COUNT")
	private int picCount;//评分数
	
	@Column(name="HUI_FU_COUNT")
	private int huiFuCount;//回复数
	
	@Column(name="READ_COUNT")
	private int readCount;//阅读数
	
	@Column(name="XIA_ZAI_COUNT")
	private int xiaZaiCount;//下载数
	
	@Column(name="ADVISE_FILE")
	private String adviseFile;
	
	
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

	public TeeFileNetdisk getFileParent() {
		return fileParent;
	}

	public void setFileParent(TeeFileNetdisk fileParent) {
		this.fileParent = fileParent;
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

	public int getFiletype() {
		return filetype;
	}

	public void setFiletype(int filetype) {
		this.filetype = filetype;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public TeePerson getCreater() {
		return creater;
	}

	public void setCreater(TeePerson creater) {
		this.creater = creater;
	}

	public String getFileFullPath() {
		return fileFullPath;
	}

	public void setFileFullPath(String fileFullPath) {
		this.fileFullPath = fileFullPath;
	}

	public TeeAttachment getAttachemnt() {
		return attachemnt;
	}

	public void setAttachemnt(TeeAttachment attachemnt) {
		this.attachemnt = attachemnt;
	}

	public List<TeeFileUserPriv> getFileUserPriv() {
		return fileUserPriv;
	}

	public void setFileUserPriv(List<TeeFileUserPriv> fileUserPriv) {
		this.fileUserPriv = fileUserPriv;
	}

	public List<TeeFileDeptPriv> getFileDeptPriv() {
		return fileDeptPriv;
	}

	public void setFileDeptPriv(List<TeeFileDeptPriv> fileDeptPriv) {
		this.fileDeptPriv = fileDeptPriv;
	}

	public List<TeeFileRolePriv> getFileRolePriv() {
		return fileRolePriv;
	}

	public void setFileRolePriv(List<TeeFileRolePriv> fileRolePriv) {
		this.fileRolePriv = fileRolePriv;
	}

    public int getFileNetdiskType() {
        return fileNetdiskType;
    }

    public void setFileNetdiskType(int fileNetdiskType) {
        this.fileNetdiskType = fileNetdiskType;
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
	
	
}


