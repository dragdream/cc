package com.beidasoft.xzzf.utility.toolManage.model;


/**
 * 实用工具model
 */
public class ToolsModel {
    private String id; // 实用工具主键

    private String toolsName; // 工具名称
    
    private String toolsAttachName; // 工具附件名称

    private String toolsIntroduction; // 工具介绍

    private String toolsVersion; // 工具版本

    private String toolsSize; // 工具大小

    private String tutorialName; // 教程名称
    
    private String tutorialAttachName; // 教程名称

	private int toolsAttachId; //工具附件id
    
    private int tutorialAttachId;  //教程附件id
    
    private String uploadDateStr; //上传时间
    
    private int uploadPersonId; // 上传人

    private String updateDateStr; // 更新时间

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

	public int getToolsAttachId() {
		return toolsAttachId;
	}

	public void setToolsAttachId(int toolsAttachId) {
		this.toolsAttachId = toolsAttachId;
	}

	public int getTutorialAttachId() {
		return tutorialAttachId;
	}

	public void setTutorialAttachId(int tutorialAttachId) {
		this.tutorialAttachId = tutorialAttachId;
	}

	public String getUploadDateStr() {
		return uploadDateStr;
	}

	public void setUploadDateStr(String uploadDateStr) {
		this.uploadDateStr = uploadDateStr;
	}

	public int getUploadPersonId() {
		return uploadPersonId;
	}

	public void setUploadPersonId(int uploadPersonId) {
		this.uploadPersonId = uploadPersonId;
	}

	public String getUpdateDateStr() {
		return updateDateStr;
	}

	public void setUpdateDateStr(String updateDateStr) {
		this.updateDateStr = updateDateStr;
	}

	public int getUpdatePersonId() {
		return updatePersonId;
	}

	public void setUpdatePersonId(int updatePersonId) {
		this.updatePersonId = updatePersonId;
	}

	public String getToolsAttachName() {
		return toolsAttachName;
	}

	public void setToolsAttachName(String toolsAttachName) {
		this.toolsAttachName = toolsAttachName;
	}

	public String getTutorialAttachName() {
		return tutorialAttachName;
	}

	public void setTutorialAttachName(String tutorialAttachName) {
		this.tutorialAttachName = tutorialAttachName;
	}

    
}