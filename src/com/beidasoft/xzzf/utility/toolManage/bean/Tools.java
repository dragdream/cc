package com.beidasoft.xzzf.utility.toolManage.bean;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
/**
 * 实用工具实体类
 */
@Entity
@Table(name="ZF_TOOLS")
public class Tools {
    @Id
    @Column(name = "ID")
    private String id; // 实用工具主键

    @Column(name = "TOOL_NAME")
    private String toolsName; // 工具名称

    @Column(name = "TOOL_INTRODUCTION")
    private String toolsIntroduction; // 工具介绍

    @Column(name = "TOOL_VERSION")
    private String toolsVersion; // 工具版本

    @Column(name = "TOOL_SIZE")
    private String toolsSize; // 工具大小

    @Column(name = "TUTORIAL_NAME")
    private String tutorialName; // 教程名称

    @OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="TOOL_ATTACH_ID")
	private TeeAttachment toolsAttach; //工具附件
    
    @OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name="TUTORIAL_ATTACH_ID")
    private TeeAttachment tutorialAttach;  //教程附件
    
    @Column(name = "UPLOAD_DATE")
    private Date uploadDate; //上传时间
    
    @Column(name = "UPLOAD_PERSON_ID")
    private int uploadPersonId; // 上传人

    @Column(name = "UPDATE_DATE")
    private Date updateDate; // 更新时间

    @Column(name = "UPDATE_PERSON_ID")
    private int updatePersonId; // 更新人

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getToolsName() {
		return toolsName;
	}

	public void setToolsName(String toolsName) {
		this.toolsName = toolsName;
	}

	public String getToolsIntroduction() {
		return toolsIntroduction;
	}

	public void setToolsIntroduction(String toolsIntroduction) {
		this.toolsIntroduction = toolsIntroduction;
	}

	public String getToolsVersion() {
		return toolsVersion;
	}

	public void setToolsVersion(String toolsVersion) {
		this.toolsVersion = toolsVersion;
	}

	public String getToolsSize() {
		return toolsSize;
	}

	public void setToolsSize(String toolsSize) {
		this.toolsSize = toolsSize;
	}

	public String getTutorialName() {
		return tutorialName;
	}

	public void setTutorialName(String tutorialName) {
		this.tutorialName = tutorialName;
	}

	public TeeAttachment getToolsAttach() {
		return toolsAttach;
	}

	public void setToolsAttach(TeeAttachment toolsAttach) {
		this.toolsAttach = toolsAttach;
	}

	public TeeAttachment getTutorialAttach() {
		return tutorialAttach;
	}

	public void setTutorialAttach(TeeAttachment tutorialAttach) {
		this.tutorialAttach = tutorialAttach;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public int getUploadPersonId() {
		return uploadPersonId;
	}

	public void setUploadPersonId(int uploadPersonId) {
		this.uploadPersonId = uploadPersonId;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public int getUpdatePersonId() {
		return updatePersonId;
	}

	public void setUpdatePersonId(int updatePersonId) {
		this.updatePersonId = updatePersonId;
	}

	
}